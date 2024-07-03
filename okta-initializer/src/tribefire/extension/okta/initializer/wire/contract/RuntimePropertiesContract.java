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
package tribefire.extension.okta.initializer.wire.contract;

import java.util.Set;

import com.braintribe.wire.api.annotation.Decrypt;
import com.braintribe.wire.api.annotation.Default;

import tribefire.cortex.initializer.support.wire.contract.PropertyLookupContract;

public interface RuntimePropertiesContract extends PropertyLookupContract {

	String OKTA_SERVICE_BASE_URL();
	boolean ENABLE_OKTA_ACCESS();

	@Default("SSWS")
	String OKTA_HTTP_AUTHORIZATION_SCHEME();
	@Decrypt
	String OKTA_HTTP_AUTHORIZATION_TOKEN();

	String OKTA_HTTP_OAUTH_AUDIENCE();
	@Decrypt
	String OKTA_HTTP_OAUTH_KEY_MODULUS_N_ENCRYPTED();
	@Decrypt
	String OKTA_HTTP_OAUTH_PRIVATE_EXPONENT_D_ENCRYPTED();
	String OKTA_HTTP_OAUTH_CLIENT_ID();
	Integer OKTA_HTTP_OAUTH_EXPIRATION_S();
	Set<String> OKTA_HTTP_OAUTH_SCOPES();

	String OKTA_CLIENT_SECRET_TOKEN_URL();
	String OKTA_CLIENT_ID();
	String OKTA_CLIENT_SECRET();
	Set<String> OKTA_CLIENT_SCOPES();
}
