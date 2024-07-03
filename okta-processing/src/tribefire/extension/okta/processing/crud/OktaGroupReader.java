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
package tribefire.extension.okta.processing.crud;

import java.util.List;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.InitializationAware;
import com.braintribe.cfg.Required;
import com.braintribe.logging.Logger;
import com.braintribe.model.access.crud.api.read.EntityReader;
import com.braintribe.model.access.crud.api.read.EntityReadingContext;
import com.braintribe.model.access.crud.api.read.PopulationReader;
import com.braintribe.model.access.crud.api.read.PopulationReadingContext;
import com.braintribe.model.access.crud.support.read.DispatchingPredicates;
import com.braintribe.model.access.crud.support.read.DispatchingReader;
import com.braintribe.model.access.crud.support.read.EmptyReader;
import com.braintribe.model.access.crud.support.read.IdReaderBridge;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.service.api.ServiceRequest;

import tribefire.extension.okta.api.model.AuthorizedOktaRequest;
import tribefire.extension.okta.api.model.user.GetGroup;
import tribefire.extension.okta.api.model.user.ListGroups;
import tribefire.extension.okta.model.OktaGroup;
import tribrefire.extension.okta.common.OktaCommons;

public class OktaGroupReader extends OktaReader
		implements EntityReader<OktaGroup>, PopulationReader<OktaGroup>, DispatchingPredicates, InitializationAware, OktaCommons {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(OktaGroupReader.class);

	protected DispatchingReader<OktaGroup> dispatcher = new DispatchingReader<>();
	private Evaluator<ServiceRequest> evaluator;
	private String domainId = DEFAULT_OKTA_ACCESS_EXTERNALID;

	// ***************************************************************************************************
	// Setters
	// ***************************************************************************************************

	@Required
	@Configurable
	public void setEvaluator(Evaluator<ServiceRequest> evaluator) {
		this.evaluator = evaluator;
	}

	@Configurable
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	// ***************************************************************************************************
	// Initializations
	// ***************************************************************************************************

	@Override
	public void postConstruct() {
		this.dispatcher
		//@formatter:off
				// Population Readers
				.registerPopulationReader(isExclusiveIdCondition(), IdReaderBridge.instance(this))
				.registerPopulationReader(isAlwaysTrue(), this::findEntries)
				// Property Readers
				.registerPropertyReader(isAlwaysTrue(), EmptyReader.instance()); // Default (last registration)
			//@formatter:on
	}

	// ***************************************************************************************************
	// EntityReader
	// ***************************************************************************************************

	@Override
	public OktaGroup getEntity(EntityReadingContext<OktaGroup> context) {
		GetGroup request = GetGroup.T.create();
		request.setGroupId(context.getId());

		OktaGroup oktaGroup = evaluateOktaRequest(request);
		return oktaGroup;
	}

	// ***************************************************************************************************
	// PopulationReader
	// ***************************************************************************************************

	@Override
	public Iterable<OktaGroup> findEntities(PopulationReadingContext<OktaGroup> context) {
		return this.dispatcher.findEntities(context);
	}

	protected Iterable<OktaGroup> findEntries(PopulationReadingContext<? extends OktaGroup> context) {
		ListGroups request = ListGroups.T.create();

		List<OktaGroup> oktaGroups = evaluateOktaRequest(request);
		return oktaGroups;
	}

	// ***************************************************************************************************
	// Helpers
	// ***************************************************************************************************

	@SuppressWarnings("unchecked")
	private <R> R evaluateOktaRequest(AuthorizedOktaRequest request) {
		request.setDomainId(this.domainId);
		return (R) request.eval(this.evaluator).get();
	}

}
