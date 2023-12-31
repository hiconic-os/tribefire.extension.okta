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
package tribefire.extension.okta.templates.wire.contract;

import com.braintribe.model.ddra.DdraConfiguration;
import com.braintribe.model.deployment.Module;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.meta.GmStringType;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.cortex.initializer.support.impl.lookup.GlobalId;
import tribefire.cortex.initializer.support.impl.lookup.InstanceLookup;
import tribrefire.extension.okta.common.OktaCommons;

@InstanceLookup(lookupOnly = true)
public interface ExistingInstancesContract extends WireSpace, OktaCommons {

	String GLOBAL_ID_PREFIX = "model:" + OKTA_EXTENSION_GROUP_ID + ":";

	// ***************************************************************************************************
	// Modules
	// ***************************************************************************************************

	@GlobalId("module://tribefire.extension.okta:okta-module")
	Module oktaModule();

	// ***************************************************************************************************
	// Models
	// ***************************************************************************************************

	@GlobalId(OKTA_DEPLOYMENT_MODEL_GLOBAL_ID)
	GmMetaModel oktaDeploymentModel();

	@GlobalId(OKTA_API_MODEL_GLOBAL_ID)
	GmMetaModel oktaApiModel();

	@GlobalId(OKTA_MODEL_GLOBAL_ID)
	GmMetaModel oktaModel();

	@GlobalId("model:tribefire.cortex:tribefire-cortex-service-model")
	GmMetaModel cortexServiceModel();

	@GlobalId("model:tribefire.cortex:configured-tribefire-platform-service-model")
	GmMetaModel platformServiceModel();

	// ***************************************************************************************************
	// DDRA
	// ***************************************************************************************************

	@GlobalId("ddra:config")
	DdraConfiguration ddraConfiguration();

	@GlobalId("type:string")
	GmStringType stringType();

}
