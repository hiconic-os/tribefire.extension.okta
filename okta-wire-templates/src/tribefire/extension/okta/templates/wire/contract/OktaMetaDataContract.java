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

import com.braintribe.model.extensiondeployment.meta.PreProcessWith;
import com.braintribe.model.processing.meta.editor.ModelMetaDataEditor;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.extension.okta.deployment.model.OktaAuthenticationSupplier;
import tribefire.extension.okta.templates.api.OktaTemplateContext;

public interface OktaMetaDataContract extends WireSpace {

	void configureMetaData(OktaTemplateContext context);

	PreProcessWith preProcessWithConfiguredAuthorization(OktaTemplateContext context, OktaAuthenticationSupplier authSupplier);

	void configureAuthorizationRequests(OktaTemplateContext context, ModelMetaDataEditor editor);
}
