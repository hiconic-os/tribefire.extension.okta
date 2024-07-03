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
package tribefire.extension.okta.initializer.wire.space;

import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.extension.okta.initializer.wire.contract.ExistingInstancesContract;
import tribefire.extension.okta.initializer.wire.contract.OktaContract;
import tribefire.extension.okta.initializer.wire.contract.OktaMainContract;
import tribefire.extension.okta.initializer.wire.contract.RuntimePropertiesContract;
import tribefire.module.wire.contract.ModelApiContract;

@Managed
public class OktaMainSpace implements OktaMainContract {

	@Import
	private ExistingInstancesContract existingInstances;

	@Import
	private CoreInstancesContract coreInstances;

	@Import
	private RuntimePropertiesContract runtime;

	@Import
	private OktaContract okta;

	@Import
	private ModelApiContract modelApi;

	@Override
	public ExistingInstancesContract existingInstances() {
		return existingInstances;
	}

	@Override
	public CoreInstancesContract coreInstances() {
		return coreInstances;
	}

	@Override
	public RuntimePropertiesContract runtime() {
		return runtime;
	}

	@Override
	public OktaContract okta() {
		return okta;
	}

	@Override
	public ModelApiContract modelApi() {
		return modelApi;
	}

}
