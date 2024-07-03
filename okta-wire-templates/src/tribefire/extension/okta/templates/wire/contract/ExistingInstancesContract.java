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
