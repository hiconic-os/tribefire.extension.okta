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
// ============================================================================
// Copyright BRAINTRIBE TECHNOLOGY GMBH, Austria, 2002-2022
// 
// This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
// 
// This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public License along with this library; See http://www.gnu.org/licenses/.
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
