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
package tribefire.extension.okta.templates.wire.contract;

import com.braintribe.model.deployment.http.client.HttpClient;
import com.braintribe.model.deployment.http.meta.HttpConsumes;
import com.braintribe.model.deployment.http.meta.HttpDefaultFailureResponseType;
import com.braintribe.model.deployment.http.meta.HttpPath;
import com.braintribe.model.deployment.http.meta.methods.HttpGet;
import com.braintribe.model.deployment.http.meta.methods.HttpPost;
import com.braintribe.model.deployment.http.meta.params.HttpBodyParam;
import com.braintribe.model.deployment.http.meta.params.HttpHeaderParam;
import com.braintribe.model.deployment.http.meta.params.HttpQueryParam;
import com.braintribe.model.deployment.http.processor.DynamicHttpServiceProcessor;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.extension.okta.deployment.model.OktaAuthenticationSupplier;
import tribefire.extension.okta.templates.api.OktaTemplateContext;

public interface OktaHttpContract extends WireSpace {

	DynamicHttpServiceProcessor dynamicHttpProcessor(OktaTemplateContext context);

	HttpGet httpGet(OktaTemplateContext context);

	HttpPost httpPost(OktaTemplateContext context);

	HttpPath httpPathForListUsers(OktaTemplateContext context);

	HttpPath httpPathForGetUser(OktaTemplateContext context);

	HttpPath httpPathForGetUserGroups(OktaTemplateContext context);

	HttpPath httpPathForListGroups(OktaTemplateContext context);

	HttpPath httpPathForListGroupMembers(OktaTemplateContext context);

	HttpPath httpPathForGetGroup(OktaTemplateContext context);

	HttpPath httpPathForListAppUsers(OktaTemplateContext context);

	HttpPath httpPathForListAppGroups(OktaTemplateContext context);

	HttpPath httpPathForGetOauthAccessToken(OktaTemplateContext context);

	HttpPath httpPathForGetClientSecretAccessToken(OktaTemplateContext context);

	HttpQueryParam httpQueryParamAsIsIfDeclared(OktaTemplateContext context);

	HttpQueryParam httpQueryParamForQuery(OktaTemplateContext context);

	HttpQueryParam httpQueryParamForGrantType(OktaTemplateContext context);

	HttpQueryParam httpQueryParamForScope(OktaTemplateContext context);

	HttpQueryParam httpQueryParamForClientAssertionType(OktaTemplateContext context);

	HttpQueryParam httpQueryParamForClientAssertion(OktaTemplateContext context);

	HttpQueryParam httpQueryParamForClientId(OktaTemplateContext context);

	HttpQueryParam httpQueryParamForClientSecret(OktaTemplateContext context);

	HttpConsumes httpConsumesXWwwFormUrlEncoded(OktaTemplateContext context);

	HttpHeaderParam httpHeaderParamForAuthorization(OktaTemplateContext context);

	HttpDefaultFailureResponseType httpDefaultFailureResponseType(OktaTemplateContext context);

	HttpClient oktaHttpClient(OktaTemplateContext context, OktaAuthenticationSupplier oktaAuthenticationSupplier);

	HttpBodyParam httpBodyParamForGrantType(OktaTemplateContext context);

	HttpBodyParam httpBodyParamForClientSecret(OktaTemplateContext context);

	HttpBodyParam httpBodyParamForScope(OktaTemplateContext context);

	HttpBodyParam httpBodyParamForClientId(OktaTemplateContext context);

}
