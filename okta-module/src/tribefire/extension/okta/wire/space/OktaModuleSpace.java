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
package tribefire.extension.okta.wire.space;

import com.braintribe.model.processing.deployment.api.binding.DenotationBindingBuilder;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.extension.okta.deployment.model.OktaAccess;
import tribefire.extension.okta.deployment.model.OktaAuthorizationPreProcessor;
import tribefire.extension.okta.deployment.model.jwt.OktaJwtTokenCredentialsAuthenticator;
import tribefire.module.wire.contract.TribefireModuleContract;
import tribefire.module.wire.contract.TribefireWebPlatformContract;

@Managed
public class OktaModuleSpace implements TribefireModuleContract {

	@Import
	private TribefireWebPlatformContract tfPlatform;

	@Import
	private OktaDeployablesSpace deployables;

	@Override
	public void bindDeployables(DenotationBindingBuilder bindings) {
		//@formatter:off
		bindings.bind(OktaAccess.T)
			.component(tfPlatform.binders().incrementalAccess())
			.expertFactory(deployables::oktaAccess);
		
		bindings.bind(OktaAuthorizationPreProcessor.T)
			.component(tfPlatform.binders().servicePreProcessor())
			.expertFactory(deployables::oktaAuthorizationPreProcessor);
		
		bindings.bind(OktaJwtTokenCredentialsAuthenticator.T)
			.component(tfPlatform.binders().serviceProcessor())
			.expertFactory(deployables::jwtCredentialsAuthenticator);
		//@formatter:on
	}

}
