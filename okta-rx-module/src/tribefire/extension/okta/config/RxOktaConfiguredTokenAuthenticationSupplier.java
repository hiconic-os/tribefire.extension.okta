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
package tribefire.extension.okta.config;

import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Confidential;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface RxOktaConfiguredTokenAuthenticationSupplier extends RxOktaAuthenticationSupplier {

	final EntityType<RxOktaConfiguredTokenAuthenticationSupplier> T = EntityTypes.T(RxOktaConfiguredTokenAuthenticationSupplier.class);

	@Initializer("'SSWS'")
	@Mandatory
	String getAuthorizationScheme();
	void setAuthorizationScheme(String authorizationScheme);

	@Confidential
	@Mandatory
	String getAuthorizationToken();
	void setAuthorizationToken(String authorizationToken);
}
