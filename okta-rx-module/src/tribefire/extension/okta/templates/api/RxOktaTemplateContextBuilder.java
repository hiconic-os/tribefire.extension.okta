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

public interface RxOktaTemplateContextBuilder {

	RxOktaTemplateContextBuilder setName(String name);

	RxOktaTemplateContextBuilder setIdPrefix(String idPrefix);

	RxOktaTemplateContextBuilder setContext(String context);

	RxOktaTemplateContextBuilder setServiceBaseUrl(String serviceBaseUrl);

	RxOktaTemplateContextBuilder setAccessAuthenticationSupplier(RxOktaAuthenticationSupplier accessAuthenticationSupplier);

	RxOktaTemplateContextBuilder setDefaultAuthenticationSupplier(RxOktaAuthenticationSupplier defaultAuthenticationSupplier);

	RxOktaTemplateContextBuilder setClientsFactory(ClientsFactory clientsFactory);

	RxOktaTemplateContextBuilder setServiceProcessingContract(RxServiceProcessingContract serviceProcessingContract);

	RxOktaTemplateContext build();

}
