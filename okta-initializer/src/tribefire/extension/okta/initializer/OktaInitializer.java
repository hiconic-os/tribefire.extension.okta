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
package tribefire.extension.okta.initializer;

import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.initializer.support.api.WiredInitializerContext;
import tribefire.cortex.initializer.support.impl.AbstractInitializer;
import tribefire.extension.okta.initializer.wire.OktaInitializerWireModule;
import tribefire.extension.okta.initializer.wire.contract.OktaContract;
import tribefire.extension.okta.initializer.wire.contract.OktaMainContract;
import tribefire.extension.okta.initializer.wire.contract.RuntimePropertiesContract;

public class OktaInitializer extends AbstractInitializer<OktaMainContract> {

	@Override
	public WireTerminalModule<OktaMainContract> getInitializerWireModule() {
		return OktaInitializerWireModule.INSTANCE;
	}

	@Override
	public void initialize(PersistenceInitializationContext context, WiredInitializerContext<OktaMainContract> initializerContext,
			OktaMainContract mainContract) {

		OktaContract okta = mainContract.okta();
		RuntimePropertiesContract runtime = mainContract.runtime();

		okta.configure();
		if (runtime.ENABLE_OKTA_ACCESS()) {
			okta.oktaAccess();
		}

	}

}
