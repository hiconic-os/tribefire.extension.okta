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

import java.util.Set;

import com.braintribe.logging.Logger;
import com.braintribe.utils.lcd.StringTools;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.space.WireSpace;

import hiconic.rx.module.api.service.ModelConfigurations;
import hiconic.rx.module.api.wire.RxPlatformContract;
import tribefire.extension.okta.config.RxOktaAccess;
import tribefire.extension.okta.config.RxOktaAuthenticationSupplier;
import tribefire.extension.okta.config.RxOktaAuthorizationPreProcessor;
import tribefire.extension.okta.config.RxOktaClientSecretTokenAuthenticationSupplier;
import tribefire.extension.okta.config.RxOktaConfiguredTokenAuthenticationSupplier;
import tribefire.extension.okta.config.RxOktaOauthTokenAuthenticationSupplier;
import tribefire.extension.okta.processing.service.OktaAuthorizationPreProcessor;
import tribefire.extension.okta.templates.api.RxOktaTemplateContext;
import tribefire.extension.okta.wire.contract.RxOktaRuntimePropertiesContract;
import tribrefire.extension.okta.common.OktaCommons;

/**
 * This module's javadoc is yet to be written.
 */
@Managed
public class RxOktaTemplatesSpace implements WireSpace, OktaCommons {

	@Import
	private RxPlatformContract platform;

	@Import
	private RxOktaMetaDataSpace metadata;

	@Import
	private RxOktaDeployablesSpace deployables;

	@Import
	private RxOktaModelsSpace models;

	@Import
	private RxOktaRuntimePropertiesContract runtime;

	private static final Logger log = Logger.getLogger(RxOktaTemplatesSpace.class);

	public void configure(RxOktaTemplateContext context, ModelConfigurations configurations) {
		metadata.configureMetaData(context, configurations);
	}

	//

	@Managed
	public RxOktaAccess oktaAccess(RxOktaTemplateContext context, ModelConfigurations configurations) {
		RxOktaAccess bean = RxOktaAccess.T.create();
		bean.setAccessId(context.getIdPrefix() + "." + DEFAULT_OKTA_ACCESS_EXTERNALID);
		bean.getDataModelNames().add(models.configuredOktaAccessModel(configurations).name());
		bean.getServiceModelNames().add(models.configuredOktaApiModel(configurations).name());
		return bean;
	}

	@Managed
	public OktaAuthorizationPreProcessor authorizationPreProcessor(RxOktaTemplateContext context) {
		return deployables.oktaAuthorizationPreProcessor(context, authorizationPreProcessorDenotation(context));
	}

	private RxOktaAuthorizationPreProcessor authorizationPreProcessorDenotation(RxOktaTemplateContext context) {
		RxOktaAuthorizationPreProcessor bean = RxOktaAuthorizationPreProcessor.T.create();
		RxOktaAuthenticationSupplier oktaAuthenticationSupplier = context.getAccessAuthenticationSupplier();
		if (useOauthToken(oktaAuthenticationSupplier))
			bean.setAuthenticationSupplier(oauthAuthenticationSupplier(context, (RxOktaOauthTokenAuthenticationSupplier) oktaAuthenticationSupplier));

		else if (useConfiguredToken(oktaAuthenticationSupplier))
			bean.setAuthenticationSupplier(
					configuredTokenAuthenticationSupplier((RxOktaConfiguredTokenAuthenticationSupplier) oktaAuthenticationSupplier));

		else if (useClientSecret(oktaAuthenticationSupplier))
			bean.setAuthenticationSupplier(
					clientSecretTokenAuthenticationSupplier((RxOktaClientSecretTokenAuthenticationSupplier) oktaAuthenticationSupplier));

		else
			log.error("Missing configuration.");

		return bean;
	}

	@Managed
	public OktaAuthorizationPreProcessor configuredAuthorizationPreProcessor(RxOktaTemplateContext context,
			RxOktaAuthenticationSupplier oktaAuthenticationSupplier) {

		return deployables.oktaAuthorizationPreProcessor(context, configuredAuthorizationPreProcessorDenotation(context, oktaAuthenticationSupplier));
	}

	private RxOktaAuthorizationPreProcessor configuredAuthorizationPreProcessorDenotation(RxOktaTemplateContext context,
			RxOktaAuthenticationSupplier oktaAuthenticationSupplier) {

		RxOktaAuthorizationPreProcessor bean = RxOktaAuthorizationPreProcessor.T.create();

		if (useOauthToken(oktaAuthenticationSupplier))
			bean.setAuthenticationSupplier(oauthAuthenticationSupplier(context, (RxOktaOauthTokenAuthenticationSupplier) oktaAuthenticationSupplier));

		else if (useConfiguredToken(oktaAuthenticationSupplier))
			bean.setAuthenticationSupplier(
					configuredTokenAuthenticationSupplier((RxOktaConfiguredTokenAuthenticationSupplier) oktaAuthenticationSupplier));

		else if (useClientSecret(oktaAuthenticationSupplier))
			bean.setAuthenticationSupplier(
					clientSecretTokenAuthenticationSupplier((RxOktaClientSecretTokenAuthenticationSupplier) oktaAuthenticationSupplier));

		else
			log.error("Missing auth configuration.");

		return bean;
	}

	@Managed
	private RxOktaOauthTokenAuthenticationSupplier oauthAuthenticationSupplier(RxOktaTemplateContext context,
			RxOktaOauthTokenAuthenticationSupplier authConfig) {
		RxOktaOauthTokenAuthenticationSupplier bean = RxOktaOauthTokenAuthenticationSupplier.T.create();

		String clientId = authConfig.getClientId();
		String audience = authConfig.getAudience();
		if (StringTools.isBlank(audience)) {
			String serviceBaseUrl = context.getServiceBaseUrl();
			if (!StringTools.isBlank(serviceBaseUrl)) {
				audience = serviceBaseUrl.replace("/api/v1", "/oauth2/v1/token");
			}
		}
		Integer expirationInS = authConfig.getExpirationDurationInSeconds();
		Set<String> scopes = authConfig.getScopes();

		bean.setKeyModulusN(authConfig.getKeyModulusN());
		bean.setPrivateExponentD(authConfig.getPrivateExponentD());
		bean.setClientId(clientId);
		bean.setAudience(audience);
		bean.setOktaDomainId(authConfig.getOktaDomainId());
		if (expirationInS != null && expirationInS >= 0) {
			bean.setExpirationDurationInSeconds(expirationInS);
		}
		if (scopes != null && !scopes.isEmpty()) {
			bean.setScopes(scopes);
		}

		return bean;
	}

	@Managed
	private RxOktaClientSecretTokenAuthenticationSupplier clientSecretTokenAuthenticationSupplier(
			RxOktaClientSecretTokenAuthenticationSupplier authConfig) {
		RxOktaClientSecretTokenAuthenticationSupplier bean = RxOktaClientSecretTokenAuthenticationSupplier.T.create();

		String clientId = authConfig.getClientId();
		String clientSecret = authConfig.getClientSecret();
		Set<String> scopes = authConfig.getScopes();

		bean.setClientId(clientId);
		bean.setClientSecret(clientSecret);
		bean.setOktaDomainId(authConfig.getOktaDomainId());
		if (scopes != null && !scopes.isEmpty()) {
			bean.setScopes(scopes);
		}
		bean.setTokenUrl(authConfig.getTokenUrl());

		return bean;
	}

	@Managed
	private RxOktaConfiguredTokenAuthenticationSupplier configuredTokenAuthenticationSupplier(
			RxOktaConfiguredTokenAuthenticationSupplier authConfig) {
		RxOktaConfiguredTokenAuthenticationSupplier bean = RxOktaConfiguredTokenAuthenticationSupplier.T.create();
		bean.setAuthorizationScheme(authConfig.getAuthorizationScheme());
		bean.setAuthorizationToken(authConfig.getAuthorizationToken());
		return bean;
	}

	public boolean useOauthToken(RxOktaAuthenticationSupplier supplier) {
		if (supplier instanceof RxOktaOauthTokenAuthenticationSupplier oauth) {
			if (!StringTools.isAnyBlank(oauth.getKeyModulusN(), oauth.getPrivateExponentD())) {
				return true;
			}
		}
		return false;
	}
	public boolean useConfiguredToken(RxOktaAuthenticationSupplier supplier) {
		if (supplier instanceof RxOktaConfiguredTokenAuthenticationSupplier token) {
			if (!StringTools.isBlank(token.getAuthorizationToken())) {
				return true;
			}
		}
		return false;
	}
	public boolean useClientSecret(RxOktaAuthenticationSupplier supplier) {
		if (supplier instanceof RxOktaClientSecretTokenAuthenticationSupplier client) {
			if (!StringTools.isAnyBlank(client.getTokenUrl(), client.getClientId(), client.getClientSecret())) {
				return true;
			}
		}
		return false;
	}

}