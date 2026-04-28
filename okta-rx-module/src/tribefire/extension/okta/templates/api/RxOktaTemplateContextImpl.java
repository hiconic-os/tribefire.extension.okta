// ============================================================================
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

import hiconic.rx.module.api.wire.RxServiceProcessingContract;
import hiconic.rx.webapi.client.api.ClientsFactory;
import tribefire.extension.okta.config.RxOktaAuthenticationSupplier;

public class RxOktaTemplateContextImpl implements RxOktaTemplateContext, RxOktaTemplateContextBuilder {

	private String name;
	private String idPrefix;
	private String context;

	private String serviceBaseUrl;
	private RxOktaAuthenticationSupplier accessAuthenticationSupplier;
	private RxOktaAuthenticationSupplier defaultAuthenticationSupplier;
	private ClientsFactory clientsFactory;
	private RxServiceProcessingContract serviceProcessingContract;

	@Override
	public RxOktaTemplateContextBuilder setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public RxOktaTemplateContextBuilder setIdPrefix(String idPrefix) {
		this.idPrefix = idPrefix;
		return this;
	}

	@Override
	public String getIdPrefix() {
		return idPrefix;
	}

	@Override
	public RxOktaTemplateContextBuilder setContext(String context) {
		this.context = context;
		return this;
	}

	@Override
	public String getContext() {
		return context;
	}

	@Override
	public RxOktaTemplateContextBuilder setServiceBaseUrl(String serviceBaseUrl) {
		this.serviceBaseUrl = serviceBaseUrl;
		return this;
	}

	@Override
	public RxOktaTemplateContextBuilder setAccessAuthenticationSupplier(RxOktaAuthenticationSupplier accessAuthenticationSupplier) {
		this.accessAuthenticationSupplier = accessAuthenticationSupplier;
		return this;
	}

	@Override
	public String getServiceBaseUrl() {
		return serviceBaseUrl;
	}

	@Override
	public RxOktaAuthenticationSupplier getAccessAuthenticationSupplier() {
		return accessAuthenticationSupplier;
	}

	@Override
	public RxOktaTemplateContextBuilder setDefaultAuthenticationSupplier(RxOktaAuthenticationSupplier defaultAuthenticationSupplier) {
		this.defaultAuthenticationSupplier = defaultAuthenticationSupplier;
		return this;
	}

	@Override
	public RxOktaAuthenticationSupplier getDefaultAuthenticationSupplier() {
		return defaultAuthenticationSupplier;
	}

	@Override
	public RxOktaTemplateContextBuilder setClientsFactory(ClientsFactory clientsFactory) {
		this.clientsFactory = clientsFactory;
		return this;
	}

	@Override
	public ClientsFactory getClientsFactory() {
		return clientsFactory;
	}

	@Override
	public RxOktaTemplateContextBuilder setServiceProcessingContract(RxServiceProcessingContract serviceProcessingContract) {
		this.serviceProcessingContract = serviceProcessingContract;
		return this;
	}

	@Override
	public RxServiceProcessingContract serviceProcessingContract() {
		return serviceProcessingContract;
	}

	@Override
	public RxOktaTemplateContext build() {
		return this;
	}

}
