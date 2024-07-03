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
package tribefire.extension.okta.deployment.model;

import java.util.Set;

import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface OktaOauthTokenAuthenticationSupplier extends OktaAuthenticationSupplier {

	final EntityType<OktaOauthTokenAuthenticationSupplier> T = EntityTypes.T(OktaOauthTokenAuthenticationSupplier.class);

	String getKeyModulusN();
	void setKeyModulusN(String keyModulusN);

	String getPrivateExponentD();
	void setPrivateExponentD(String privateExponentD);

	@Mandatory
	String getClientId();
	void setClientId(String clientId);

	@Mandatory
	String getAudience();
	void setAudience(String audience);

	@Initializer("300")
	Integer getExpirationDurationInSeconds();
	void setExpirationDurationInSeconds(Integer expirationDurationInSeconds);

	Set<String> getScopes();
	void setScopes(Set<String> scopes);

}
