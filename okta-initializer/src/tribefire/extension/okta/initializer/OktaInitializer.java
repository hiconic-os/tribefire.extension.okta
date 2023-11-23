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
