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
package tribefire.extension.okta.templates.api;

import com.braintribe.logging.Logger;

import tribefire.extension.okta.deployment.model.OktaAuthenticationSupplier;
import tribefire.extension.templates.api.TemplateContextImpl;

public class OktaTemplateContextImpl extends TemplateContextImpl<OktaTemplateContext> implements OktaTemplateContext, OktaTemplateContextBuilder {

	private static final Logger logger = Logger.getLogger(OktaTemplateContextImpl.class);

	private String context;

	private String serviceBaseUrl;

	private OktaAuthenticationSupplier accessAuthenticationSupplier;

	private OktaAuthenticationSupplier defaultAuthenticationSupplier;

	@Override
	public OktaTemplateContextBuilder setContext(String context) {
		this.context = context;
		return this;
	}

	@Override
	public String getContext() {
		return context;
	}

	@Override
	public OktaTemplateContextBuilder setServiceBaseUrl(String serviceBaseUrl) {
		this.serviceBaseUrl = serviceBaseUrl;
		return this;
	}

	@Override
	public OktaTemplateContextBuilder setAccessAuthenticationSupplier(OktaAuthenticationSupplier accessAuthenticationSupplier) {
		this.accessAuthenticationSupplier = accessAuthenticationSupplier;
		return this;
	}

	@Override
	public String getServiceBaseUrl() {
		return serviceBaseUrl;
	}

	@Override
	public OktaAuthenticationSupplier getAccessAuthenticationSupplier() {
		return accessAuthenticationSupplier;
	}

	@Override
	public OktaTemplateContextBuilder setDefaultAuthenticationSupplier(OktaAuthenticationSupplier defaultAuthenticationSupplier) {
		this.defaultAuthenticationSupplier = defaultAuthenticationSupplier;
		return this;
	}

	@Override
	public OktaAuthenticationSupplier getDefaultAuthenticationSupplier() {
		return defaultAuthenticationSupplier;
	}

}
