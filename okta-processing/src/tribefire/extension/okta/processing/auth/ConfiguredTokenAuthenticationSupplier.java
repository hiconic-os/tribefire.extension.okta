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
package tribefire.extension.okta.processing.auth;

import java.util.function.Supplier;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;

import tribefire.extension.okta.api.model.auth.HasAuthorization;
import tribrefire.extension.okta.common.OktaCommons;

public class ConfiguredTokenAuthenticationSupplier implements AuthenticationSupplier, OktaCommons {

	private Supplier<String> authenticationTokenSupplier;
	private String authenticationScheme = OKTA_HTTP_AUTHORIZATION_SCHEME;

	@Override
	public void authorizeRequest(HasAuthorization request) {
		String authorizationToken = this.authenticationTokenSupplier.get();
		if (authorizationToken == null) {
			throw new IllegalArgumentException("No Authorization for OktaRequest: " + request
					+ ". Please manually set the authorization property or configure a token via runtime property: OKTA_HTTP_AUTHORIZATION_TOKEN");
		}
		request.setAuthorization(this.authenticationScheme + " " + authorizationToken);
	}

	// ***************************************************************************************************
	// Setters
	// ***************************************************************************************************

	@Required
	@Configurable
	public void setAuthenticationTokenSupplier(Supplier<String> authenticationTokenSupplier) {
		this.authenticationTokenSupplier = authenticationTokenSupplier;
	}

	@Configurable
	public void setAuthenticationScheme(String authenticationScheme) {
		this.authenticationScheme = authenticationScheme;
	}

}
