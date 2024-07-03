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
package tribefire.extension.okta.templates.wire.space;

import java.util.List;

import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;
import tribefire.extension.okta.templates.api.OktaTemplateContext;
import tribefire.extension.okta.templates.wire.contract.ExistingInstancesContract;
import tribefire.extension.okta.templates.wire.contract.OktaModelsContract;

@Managed
public class OktaModelsSpace extends AbstractInitializerSpace implements OktaModelsContract {

	@Import
	private ExistingInstancesContract existingInstances;

	@Import
	private CoreInstancesContract coreInstances;

	@Override
	@Managed
	public GmMetaModel configuredOktaApiModel(OktaTemplateContext context) {
		GmMetaModel bean = create(GmMetaModel.T);

		GmMetaModel apiModel = existingInstances.oktaApiModel();

		bean.setName(ExistingInstancesContract.OKTA_EXTENSION_GROUP_ID + ":" + context.getIdPrefix() + ".configured-okta-api-model");
		bean.setVersion(apiModel.getVersion());
		bean.getDependencies().addAll(List.of(apiModel));

		return bean;
	}

	@Override
	@Managed
	public GmMetaModel configuredOktaAccessModel(OktaTemplateContext context) {
		GmMetaModel bean = create(GmMetaModel.T);

		GmMetaModel dataModel = existingInstances.oktaModel();

		bean.setName(ExistingInstancesContract.OKTA_EXTENSION_GROUP_ID + ":" + context.getIdPrefix() + ".configured-okta-access-model");
		bean.setVersion(dataModel.getVersion());
		bean.getDependencies().add(dataModel);

		return bean;
	}

	@Override
	@Managed
	public GmMetaModel configuredOktaWbModel(OktaTemplateContext context) {
		GmMetaModel model = create(GmMetaModel.T);

		model.setName(ExistingInstancesContract.OKTA_EXTENSION_GROUP_ID + ":" + context.getIdPrefix() + ".configured-okta-wb-model");
		model.getDependencies().add(configuredOktaAccessModel(context));
		model.getDependencies().add(existingInstances.oktaApiModel());
		model.getDependencies().add(coreInstances.workbenchModel());
		model.getDependencies().add(coreInstances.essentialMetaDataModel());

		return model;
	}

	@Override
	@Managed
	public GmMetaModel configuredOktaDeploymentModel(OktaTemplateContext context) {
		GmMetaModel bean = create(GmMetaModel.T);

		GmMetaModel deploymentModel = existingInstances.oktaDeploymentModel();

		bean.setName(ExistingInstancesContract.OKTA_EXTENSION_GROUP_ID + ":configured-okta-deployment-model");
		bean.setVersion(deploymentModel.getVersion());
		bean.getDependencies().addAll(List.of(deploymentModel));

		return bean;
	}
}
