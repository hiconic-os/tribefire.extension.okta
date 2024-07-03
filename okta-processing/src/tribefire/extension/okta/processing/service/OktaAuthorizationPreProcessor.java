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
package tribefire.extension.okta.processing.service;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.model.processing.service.api.ServicePreProcessor;
import com.braintribe.model.processing.service.api.ServiceRequestContext;
import com.braintribe.model.service.api.ServiceRequest;

import tribefire.extension.okta.api.model.auth.HasAuthorization;
import tribefire.extension.okta.processing.auth.AuthenticationSupplier;
import tribrefire.extension.okta.common.OktaCommons;

public class OktaAuthorizationPreProcessor implements ServicePreProcessor<ServiceRequest>, OktaCommons {

	private AuthenticationSupplier authenticationSupplier;

	// ***************************************************************************************************
	// Setters
	// ***************************************************************************************************

	@Required
	@Configurable
	public void setAuthenticationSupplier(AuthenticationSupplier authenticationSupplier) {
		this.authenticationSupplier = authenticationSupplier;
	}

	// ***************************************************************************************************
	// Processing
	// ***************************************************************************************************

	@Override
	public ServiceRequest process(ServiceRequestContext requestContext, ServiceRequest request) {
		if (request instanceof HasAuthorization) {
			HasAuthorization hasAuthorization = (HasAuthorization) request;
			String authorization = hasAuthorization.getAuthorization();
			if (authorization == null && authenticationSupplier != null) {
				authenticationSupplier.authorizeRequest(hasAuthorization);
			}
		}
		return request;
	}

}
