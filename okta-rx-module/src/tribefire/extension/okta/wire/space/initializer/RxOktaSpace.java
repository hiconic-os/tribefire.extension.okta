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
package tribefire.extension.okta.wire.space.initializer;

import java.util.Set;

import com.braintribe.logging.Logger;
import com.braintribe.utils.lcd.StringTools;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.space.WireSpace;

import hiconic.rx.module.api.service.ModelConfigurations;
import hiconic.rx.module.api.wire.RxPlatformContract;
import hiconic.rx.module.api.wire.RxServiceProcessingContract;
import hiconic.rx.webapi.client.api.WebApiClientContract;
import tribefire.extension.okta.config.RxOktaAccess;
import tribefire.extension.okta.config.RxOktaAuthenticationSupplier;
import tribefire.extension.okta.config.RxOktaClientSecretTokenAuthenticationSupplier;
import tribefire.extension.okta.config.RxOktaConfiguredTokenAuthenticationSupplier;
import tribefire.extension.okta.config.RxOktaOauthTokenAuthenticationSupplier;
import tribefire.extension.okta.templates.api.RxOktaTemplateContext;
import tribefire.extension.okta.templates.wire.space.RxOktaTemplatesSpace;
import tribefire.extension.okta.wire.contract.RxOktaRuntimePropertiesContract;

// original: OktaSpace
@Managed
public class RxOktaSpace implements WireSpace {

	@Import
	private RxPlatformContract platform;

	@Import
	private RxServiceProcessingContract serviceProcessing;

	@Import
	private WebApiClientContract webApiClient;

	@Import
	private RxOktaTemplatesSpace templates;

	@Import
	private RxOktaRuntimePropertiesContract runtime;

	private static final Logger log = Logger.getLogger(RxOktaSpace.class);

	@Managed
	public RxOktaAccess oktaAccess(ModelConfigurations configurations) {
		RxOktaTemplateContext context = defaultContext();
		return templates.oktaAccess(context, configurations);
	}

	public void configure(ModelConfigurations configurations) {
		templates.configure(defaultContext(), configurations);
	}

	@Managed
	private RxOktaTemplateContext defaultContext() {

		//@formatter:off
		RxOktaTemplateContext context = RxOktaTemplateContext.builder()
			.setContext("Default")
			.setServiceBaseUrl(runtime.OKTA_SERVICE_BASE_URL())
			.setAccessAuthenticationSupplier(accessAuthSupplier())
			.setDefaultAuthenticationSupplier(defaultAuthSupplier())
			.setClientsFactory(webApiClient.clientsFactory())
			.setServiceProcessingContract(serviceProcessing)
			.setName("Okta")
			.setIdPrefix("default")
			.build();
		//@formatter:on
		return context;
	}

	private RxOktaAuthenticationSupplier accessAuthSupplier() {
		if (useOauthToken()) {
			return oauthAuthenticationSupplier();
		} else if (useConfiguredToken()) {
			return configuredTokenAuthenticationSupplier();
		} else {
			log.error("Missing configuration: Neither OAuth or SSWS token is configured.");
		}
		return null;
	}

	private RxOktaAuthenticationSupplier defaultAuthSupplier() {
		if (useClientSecret()) {
			return clientSecretTokenAuthenticationSupplier();
		} else {
			log.error("Missing configuration: No Client Secret is configured.");
		}
		return null;
	}

	private boolean useOauthToken() {
		if (!StringTools.isAllBlank(runtime.OKTA_HTTP_OAUTH_KEY_MODULUS_N_ENCRYPTED(), runtime.OKTA_HTTP_OAUTH_PRIVATE_EXPONENT_D_ENCRYPTED())) {
			return true;
		}
		return false;
	}

	private boolean useConfiguredToken() {
		if (!StringTools.isBlank(runtime.OKTA_HTTP_AUTHORIZATION_TOKEN())) {
			return true;
		}
		return false;
	}

	private boolean useClientSecret() {
		if (!StringTools.isAnyBlank(runtime.OKTA_CLIENT_SECRET_TOKEN_URL(), runtime.OKTA_CLIENT_ID(), runtime.OKTA_CLIENT_SECRET())) {
			return true;
		}
		return false;
	}

	private RxOktaOauthTokenAuthenticationSupplier oauthAuthenticationSupplier() {
		RxOktaOauthTokenAuthenticationSupplier bean = RxOktaOauthTokenAuthenticationSupplier.T.create();

		String clientId = runtime.OKTA_HTTP_OAUTH_CLIENT_ID();
		String audience = runtime.OKTA_HTTP_OAUTH_AUDIENCE();
		if (StringTools.isBlank(audience)) {
			String serviceBaseUrl = runtime.OKTA_SERVICE_BASE_URL();
			if (!StringTools.isBlank(serviceBaseUrl)) {
				audience = serviceBaseUrl.replace("/api/v1", "/oauth2/v1/token");
			}
		}
		Integer expirationInS = runtime.OKTA_HTTP_OAUTH_EXPIRATION_S();
		Set<String> scopes = runtime.OKTA_HTTP_OAUTH_SCOPES();

		bean.setKeyModulusN(runtime.OKTA_HTTP_OAUTH_KEY_MODULUS_N_ENCRYPTED());
		bean.setPrivateExponentD(runtime.OKTA_HTTP_OAUTH_PRIVATE_EXPONENT_D_ENCRYPTED());
		bean.setClientId(clientId);
		bean.setAudience(audience);
		bean.setOktaDomainId("default.access.okta");

		if (expirationInS != null && expirationInS >= 0) {
			bean.setExpirationDurationInSeconds(expirationInS);
		}
		if (scopes != null && !scopes.isEmpty()) {
			bean.setScopes(scopes);
		}

		return bean;
	}

	private RxOktaClientSecretTokenAuthenticationSupplier clientSecretTokenAuthenticationSupplier() {
		RxOktaClientSecretTokenAuthenticationSupplier bean = RxOktaClientSecretTokenAuthenticationSupplier.T.create();

		String clientId = runtime.OKTA_CLIENT_ID();
		String clientSecret = runtime.OKTA_CLIENT_SECRET();
		Set<String> scopes = runtime.OKTA_CLIENT_SCOPES();

		bean.setClientId(clientId);
		bean.setClientSecret(clientSecret);
		if (scopes != null && !scopes.isEmpty()) {
			bean.setScopes(scopes);
		}
		bean.setTokenUrl(runtime.OKTA_CLIENT_SECRET_TOKEN_URL());

		return bean;
	}

	private RxOktaConfiguredTokenAuthenticationSupplier configuredTokenAuthenticationSupplier() {
		RxOktaConfiguredTokenAuthenticationSupplier bean = RxOktaConfiguredTokenAuthenticationSupplier.T.create();
		bean.setAuthorizationScheme(runtime.OKTA_HTTP_AUTHORIZATION_SCHEME());
		bean.setAuthorizationToken(runtime.OKTA_HTTP_AUTHORIZATION_TOKEN());
		return bean;
	}

}