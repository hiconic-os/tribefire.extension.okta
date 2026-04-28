// ============================================================================
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// ============================================================================
package tribefire.extension.okta.templates.wire.space;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.RSAPrivateKeySpec;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Set;

import com.braintribe.logging.Logger;
import com.braintribe.utils.lcd.StringTools;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.extension.okta.config.RxOktaAuthenticationSupplier;
import tribefire.extension.okta.config.RxOktaAuthorizationPreProcessor;
import tribefire.extension.okta.config.RxOktaClientSecretTokenAuthenticationSupplier;
import tribefire.extension.okta.config.RxOktaConfiguredTokenAuthenticationSupplier;
import tribefire.extension.okta.config.RxOktaOauthTokenAuthenticationSupplier;
import tribefire.extension.okta.processing.auth.ClientSecretTokenAuthenticationSupplier;
import tribefire.extension.okta.processing.auth.ConfiguredTokenAuthenticationSupplier;
import tribefire.extension.okta.processing.auth.OAuthTokenAuthenticationSupplier;
import tribefire.extension.okta.processing.service.OktaAuthorizationPreProcessor;
import tribefire.extension.okta.templates.api.RxOktaTemplateContext;
import tribrefire.extension.okta.common.OktaCommons;

@Managed
public class RxOktaDeployablesSpace implements WireSpace, OktaCommons {

	private static final Logger logger = Logger.getLogger(RxOktaDeployablesSpace.class);

	// ***************************************************************************************************
	// Public Managed Beans
	// ***************************************************************************************************

	// This was moved away, doesn't make sense here
	// @Managed
	// public CrudExpertAccess oktaAccess(RxOktaAccess deployable) {
	// }

	@Managed
	public OktaAuthorizationPreProcessor oktaAuthorizationPreProcessor(RxOktaTemplateContext context, RxOktaAuthorizationPreProcessor deployable) {
		OktaAuthorizationPreProcessor bean = new OktaAuthorizationPreProcessor();

		RxOktaAuthenticationSupplier authenticationSupplier = deployable.getAuthenticationSupplier();
		if (authenticationSupplier instanceof RxOktaConfiguredTokenAuthenticationSupplier tokenSupplier)
			bean.setAuthenticationSupplier(configuredTokenSupplier(tokenSupplier));

		else if (authenticationSupplier instanceof RxOktaOauthTokenAuthenticationSupplier oauthSupplier)
			bean.setAuthenticationSupplier(configuredOAuthSupplier(context, oauthSupplier));

		else if (authenticationSupplier instanceof RxOktaClientSecretTokenAuthenticationSupplier secretSupplier)
			bean.setAuthenticationSupplier(configuredClientSecretSupplier(context, secretSupplier));

		else
			logger.error("Configuration issue: the authentication supplier: " + authenticationSupplier + " is not supported.");

		return bean;
	}

	private ClientSecretTokenAuthenticationSupplier configuredClientSecretSupplier(RxOktaTemplateContext context,
			RxOktaClientSecretTokenAuthenticationSupplier secretSupplier) {
		ClientSecretTokenAuthenticationSupplier bean = new ClientSecretTokenAuthenticationSupplier();
		bean.setClientId(secretSupplier.getClientId());
		bean.setClientSecret(secretSupplier.getClientSecret());
		bean.setOktaDomainId(secretSupplier.getOktaDomainId());

		Set<String> scopes = secretSupplier.getScopes();
		if (scopes != null && !scopes.isEmpty()) {
			bean.setScopes(scopes);
		}
		bean.setEvaluator(context.serviceProcessingContract().systemEvaluator());

		return bean;
	}

	private OAuthTokenAuthenticationSupplier configuredOAuthSupplier(RxOktaTemplateContext context,
			RxOktaOauthTokenAuthenticationSupplier oauthSupplier) {
		OAuthTokenAuthenticationSupplier bean = new OAuthTokenAuthenticationSupplier();
		bean.setClientId(oauthSupplier.getClientId());
		Integer expirationDurationInSeconds = oauthSupplier.getExpirationDurationInSeconds();
		if (expirationDurationInSeconds != null && expirationDurationInSeconds >= 0) {
			bean.setExpirationDuration(Duration.of(expirationDurationInSeconds.longValue(), ChronoUnit.SECONDS));
		}
		bean.setAudience(oauthSupplier.getAudience());
		Set<String> scopes = oauthSupplier.getScopes();
		if (scopes != null && !scopes.isEmpty()) {
			bean.setScopes(scopes);
		}
		bean.setEvaluator(context.serviceProcessingContract().systemEvaluator());
		bean.setOktaDomainId(oauthSupplier.getOktaDomainId());

		String keyModulusN = oauthSupplier.getKeyModulusN();
		String privateExponentD = oauthSupplier.getPrivateExponentD();
		try {
			//@formatter:off
			RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(
					new BigInteger(1, Base64.getUrlDecoder().decode(keyModulusN)),
					new BigInteger(1, Base64.getUrlDecoder().decode(privateExponentD))
			);
			//@formatter:on
			KeyFactory factory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = factory.generatePrivate(rsaPrivateKeySpec);

			bean.setPrivateKey(privateKey);
		} catch (Exception e) {
			logger.error("Error while trying to get private key from N " + StringTools.simpleObfuscatePassword(keyModulusN) + " and D "
					+ StringTools.simpleObfuscatePassword(privateExponentD), e);
		}
		return bean;
	}

	private ConfiguredTokenAuthenticationSupplier configuredTokenSupplier(RxOktaConfiguredTokenAuthenticationSupplier tokenSupplier) {
		ConfiguredTokenAuthenticationSupplier bean = new ConfiguredTokenAuthenticationSupplier();
		bean.setAuthenticationScheme(tokenSupplier.getAuthorizationScheme());
		final String token = tokenSupplier.getAuthorizationToken();
		bean.setAuthenticationTokenSupplier(() -> token);
		return bean;
	}

}
