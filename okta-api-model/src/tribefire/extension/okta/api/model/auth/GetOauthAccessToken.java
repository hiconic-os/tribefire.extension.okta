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
package tribefire.extension.okta.api.model.auth;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface GetOauthAccessToken extends GetAccessToken {

	EntityType<GetOauthAccessToken> T = EntityTypes.T(GetOauthAccessToken.class);

	String clientAssertionType = "clientAssertionType";
	String clientAssertion = "clientAssertion";

	String getClientAssertionType();
	void setClientAssertionType(String clientAssertionType);

	String getClientAssertion();
	void setClientAssertion(String clientAssertion);

}
