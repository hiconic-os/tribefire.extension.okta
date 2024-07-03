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
package tribrefire.extension.okta.common;

public interface OktaCommons {

	String OKTA_EXTENSION_GROUP_ID = "tribefire.extension.okta";

	String OKTA_DATA_MODEL_NAME = OKTA_EXTENSION_GROUP_ID + ":okta-model";
	String OKTA_API_MODEL_NAME = OKTA_EXTENSION_GROUP_ID + ":okta-api-model";
	String OKTA_DEPLOYMENT_MODEL_NAME = OKTA_EXTENSION_GROUP_ID + ":okta-deployment-model";

	String MODEL_GLOBAL_ID_PREFIX = "model:";
	String WIRE_GLOBAL_ID_PREFIX = "wire://";
	String OKTA_MODEL_GLOBAL_ID_PREFIX = MODEL_GLOBAL_ID_PREFIX + OKTA_EXTENSION_GROUP_ID + ":";

	String OKTA_DEPLOYMENT_MODEL_GLOBAL_ID = OKTA_MODEL_GLOBAL_ID_PREFIX + "okta-deployment-model";
	String OKTA_API_MODEL_GLOBAL_ID = OKTA_MODEL_GLOBAL_ID_PREFIX + "okta-api-model";
	String OKTA_MODEL_GLOBAL_ID = OKTA_MODEL_GLOBAL_ID_PREFIX + "okta-model";

	String OKTA_HTTP_AUTHORIZATION_SCHEME = "SSWS";
	String OKTA_HTTP_PARAM_AUTHORIZATION = "Authorization";
	String OKTA_HTTP_PATH_USERS = "/users";
	String OKTA_HTTP_PATH_USER = "/users/{userId}";
	String OKTA_HTTP_PATH_USER_GROUPS = "/users/{userId}/groups";
	String OKTA_HTTP_PATH_GROUPS = "/groups";
	String OKTA_HTTP_PATH_LIST_GROUP_MEMBERS = "/groups/{groupId}/users";
	String OKTA_HTTP_PATH_GROUP = "/groups/{groupId}";
	String OKTA_HTTP_PATH_LIST_APP_USERS = "/apps/{appId}/users";
	String OKTA_HTTP_PATH_LIST_APP_GROUPS = "/apps/{appId}/groups";

	String OKTA_HTTP_PATH_GET_OAUTH_ACCESS_TOKEN = "/../../oauth2/v1/token";

	String DEFAULT_OKTA_ACCESS_EXTERNALID = "access.okta";
	String DEFAULT_OKTA_ACCESS_NAME = "Okta Access";

	String OKTA_HTTP_PROCESSOR_EXTERNALID = "processor.http.okta";
	String OKTA_HTTP_PROCESSOR_NAME = "Okta Http Processor";

	String OKTA_HTTP_CONNECTOR_EXTERNALID = "connector.http.okta";
	String OKTA_HTTP_CONNECTOR_NAME = "Okta Http Client";

	String OKTA_AUTHORIZATION_PREPROCESSOR_EXTERNALID = "processor.okta.pre.authorization";
	String OKTA_AUTHORIZATION_PREPROCESSOR_NAME = "Okta Authorization Pre Processor";

	String OKTA_AUTHORIZATION_PREPROCESSOR_CLIENTSECRET_EXTERNALID = "processor.okta.pre.authorization.clientsecret";
	String OKTA_AUTHORIZATION_PREPROCESSOR_CLIENTSECRET_NAME = "Okta Authorization Pre Processor (with Client Secret)";

}
