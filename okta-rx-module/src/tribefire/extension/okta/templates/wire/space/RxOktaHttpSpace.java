// ============================================================================
// Copyright BRAINTRIBE TECHNOLOGY GMBH, Austria, 2002-2022
//
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

import java.util.concurrent.atomic.AtomicInteger;

import com.braintribe.logging.Logger;
import com.braintribe.model.meta.selector.DeclaredPropertySelector;
import com.braintribe.model.processing.service.api.ServiceProcessor;
import com.braintribe.model.service.api.ServiceRequest;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.space.WireSpace;

import hiconic.rx.webapi.client.api.HttpClient;
import hiconic.rx.webapi.client.model.configuration.GmHttpClient;
import hiconic.rx.webapi.client.model.meta.HttpConsumes;
import hiconic.rx.webapi.client.model.meta.HttpDefaultFailureResponseType;
import hiconic.rx.webapi.client.model.meta.HttpPath;
import hiconic.rx.webapi.client.model.meta.methods.HttpGet;
import hiconic.rx.webapi.client.model.meta.methods.HttpPost;
import hiconic.rx.webapi.client.model.meta.params.HttpBodyParam;
import hiconic.rx.webapi.client.model.meta.params.HttpHeaderParam;
import hiconic.rx.webapi.client.model.meta.params.HttpQueryParam;
import io.jsonwebtoken.lang.Collections;
import tribefire.extension.okta.config.RxOktaAuthenticationSupplier;
import tribefire.extension.okta.config.RxOktaClientSecretTokenAuthenticationSupplier;
import tribefire.extension.okta.model.OktaError;
import tribefire.extension.okta.templates.api.RxOktaTemplateContext;
import tribrefire.extension.okta.common.OktaCommons;

@Managed
public class RxOktaHttpSpace implements WireSpace, OktaCommons {

	private static final Logger logger = Logger.getLogger(RxOktaHttpSpace.class);

	private static AtomicInteger counter = new AtomicInteger(0);

	@Managed
	public ServiceProcessor<ServiceRequest, Object> dynamicHttpProcessor(RxOktaTemplateContext context) {
		return context.getClientsFactory().createMdBasedWebApiClientProcessor(Collections.emptySet());
	}

	@Managed
	public HttpGet httpGet() {
		HttpGet bean = HttpGet.T.create();
		return bean;
	}

	@Managed
	public HttpPost httpPost() {
		HttpPost bean = HttpPost.T.create();
		return bean;
	}

	@Managed
	public HttpPath httpPathForListUsers() {
		HttpPath bean = HttpPath.T.create();
		bean.setPath(OKTA_HTTP_PATH_USERS);
		return bean;
	}

	@Managed
	public HttpPath httpPathForGetUser() {
		HttpPath bean = HttpPath.T.create();
		bean.setPath(OKTA_HTTP_PATH_USER);
		return bean;
	}

	@Managed
	public HttpPath httpPathForGetUserGroups() {
		HttpPath bean = HttpPath.T.create();
		bean.setPath(OKTA_HTTP_PATH_USER_GROUPS);
		return bean;
	}

	@Managed
	public HttpPath httpPathForListGroups() {
		HttpPath bean = HttpPath.T.create();
		bean.setPath(OKTA_HTTP_PATH_GROUPS);
		return bean;
	}

	@Managed
	public HttpPath httpPathForListGroupMembers() {
		HttpPath bean = HttpPath.T.create();
		bean.setPath(OKTA_HTTP_PATH_LIST_GROUP_MEMBERS);
		return bean;
	}

	@Managed
	public HttpPath httpPathForGetGroup() {
		HttpPath bean = HttpPath.T.create();
		bean.setPath(OKTA_HTTP_PATH_GROUP);
		return bean;
	}

	@Managed
	public HttpPath httpPathForListAppUsers() {
		HttpPath bean = HttpPath.T.create();
		bean.setPath(OKTA_HTTP_PATH_LIST_APP_USERS);
		return bean;
	}

	@Managed
	public HttpPath httpPathForListAppGroups() {
		HttpPath bean = HttpPath.T.create();
		bean.setPath(OKTA_HTTP_PATH_LIST_APP_GROUPS);
		return bean;
	}

	@Managed
	public HttpPath httpPathForGetOauthAccessToken() {
		HttpPath bean = HttpPath.T.create();
		bean.setPath(OKTA_HTTP_PATH_GET_OAUTH_ACCESS_TOKEN);
		return bean;
	}

	@Managed
	public HttpPath httpPathForGetClientSecretAccessToken() {
		HttpPath bean = HttpPath.T.create();
		bean.setPath("");
		return bean;
	}

	@Managed
	public HttpQueryParam httpQueryParamAsIsIfDeclared() {
		HttpQueryParam bean = HttpQueryParam.T.create();
		bean.setSelector(declaredPropertySelector());
		bean.setConflictPriority(0d);
		return bean;
	}

	@Managed
	private DeclaredPropertySelector declaredPropertySelector() {
		DeclaredPropertySelector bean = DeclaredPropertySelector.T.create();
		return bean;
	}

	@Managed
	public HttpQueryParam httpQueryParamForQuery() {
		HttpQueryParam bean = HttpQueryParam.T.create();
		bean.setParamName("q");
		bean.setConflictPriority(1d);
		return bean;
	}

	@Managed
	public HttpQueryParam httpQueryParamForGrantType() {
		HttpQueryParam bean = HttpQueryParam.T.create();
		bean.setParamName("grant_type");
		bean.setConflictPriority(1d);
		return bean;
	}

	@Managed
	public HttpQueryParam httpQueryParamForScope() {
		HttpQueryParam bean = HttpQueryParam.T.create();
		bean.setParamName("scope");
		bean.setConflictPriority(1d);
		return bean;
	}

	@Managed
	public HttpQueryParam httpQueryParamForClientAssertionType() {
		HttpQueryParam bean = HttpQueryParam.T.create();
		bean.setParamName("client_assertion_type");
		bean.setConflictPriority(1d);
		return bean;
	}

	@Managed
	public HttpQueryParam httpQueryParamForClientAssertion() {
		HttpQueryParam bean = HttpQueryParam.T.create();
		bean.setParamName("client_assertion");
		bean.setConflictPriority(1d);
		return bean;
	}

	@Managed
	public HttpQueryParam httpQueryParamForClientId() {
		HttpQueryParam bean = HttpQueryParam.T.create();
		bean.setParamName("client_id");
		bean.setConflictPriority(1d);
		return bean;
	}

	@Managed
	public HttpQueryParam httpQueryParamForClientSecret() {
		HttpQueryParam bean = HttpQueryParam.T.create();
		bean.setParamName("client_secret");
		bean.setConflictPriority(1d);
		return bean;
	}

	@Managed
	public HttpConsumes httpConsumesXWwwFormUrlEncoded() {
		HttpConsumes bean = HttpConsumes.T.create();
		bean.setMimeType("application/x-www-form-urlencoded");
		bean.setConflictPriority(1d);
		return bean;
	}

	@Managed
	public HttpHeaderParam httpHeaderParamForAuthorization() {
		HttpHeaderParam bean = HttpHeaderParam.T.create();
		bean.setParamName(OKTA_HTTP_PARAM_AUTHORIZATION);
		return bean;
	}

	@Managed
	public HttpDefaultFailureResponseType httpDefaultFailureResponseType() {
		HttpDefaultFailureResponseType bean = HttpDefaultFailureResponseType.T.create();
		bean.setResponseTypeSignature(OktaError.T.getTypeSignature());
		return bean;
	}

	@Managed
	// also referenced from TangramSpace, as value for HttpProcessWith.client
	public HttpClient oktaHttpClient(RxOktaTemplateContext context, RxOktaAuthenticationSupplier oktaAuthenticationSupplier) {
		return context.getClientsFactory().createGmHttpClient(oktaHttpClientDenotation(context, oktaAuthenticationSupplier));
	}

	private GmHttpClient oktaHttpClientDenotation(RxOktaTemplateContext context, RxOktaAuthenticationSupplier oktaAuthenticationSupplier) {
		GmHttpClient bean = GmHttpClient.T.create();
		int count = counter.incrementAndGet();
		bean.setName(OKTA_HTTP_CONNECTOR_NAME + " " + context.getName() + " (" + count + ")");

		String oktaUrl = null;
		if (oktaAuthenticationSupplier instanceof RxOktaClientSecretTokenAuthenticationSupplier cs) {
			oktaUrl = cs.getTokenUrl();
		} else {
			oktaUrl = context.getServiceBaseUrl();
		}

		if (oktaUrl == null) {
			logger.warn("OKTA_CLIENT_SECRET_TOKEN_URL is not configured which is required to get the Okta HTTP Client functional.");
		}
		bean.setBaseUrl(oktaUrl);
		return bean;
	}

	@Managed
	public HttpBodyParam httpBodyParamForGrantType() {
		HttpBodyParam bean = HttpBodyParam.T.create();
		bean.setParamName("grant_type");
		bean.setConflictPriority(1d);
		return bean;
	}

	@Managed
	public HttpBodyParam httpBodyParamForClientSecret() {
		HttpBodyParam bean = HttpBodyParam.T.create();
		bean.setParamName("client_secret");
		bean.setConflictPriority(1d);
		return bean;
	}

	@Managed
	public HttpBodyParam httpBodyParamForScope() {
		HttpBodyParam bean = HttpBodyParam.T.create();
		bean.setParamName("scope");
		bean.setConflictPriority(1d);
		return bean;
	}

	@Managed
	public HttpBodyParam httpBodyParamForClientId() {
		HttpBodyParam bean = HttpBodyParam.T.create();
		bean.setParamName("client_id");
		bean.setConflictPriority(1d);
		return bean;
	}

}
