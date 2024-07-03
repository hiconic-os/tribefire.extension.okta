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
package tribefire.extension.okta.api.model.user;

import java.util.List;

import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.service.api.ServiceRequest;

import tribefire.extension.okta.api.model.AuthorizedOktaRequest;
import tribefire.extension.okta.model.OktaUser;

public interface ListUsers extends AuthorizedOktaRequest {
	
	EntityType<ListUsers> T = EntityTypes.T(ListUsers.class);

	String query = "query";
	String filter = "filter";
	String search = "search";

	String limit = "limit";
	String after = "after";
	String sortBy = "sortBy";
	String sortOrder = "sortOrder";

	String getQuery();
	void setQuery(String query);
	
	String getFilter();
	void setFilter(String filter);

	String getSearch();
	void setSearch(String search);

	Integer getLimit();
	void setLimit(Integer limit);

	String getAfter();
	void setAfter(String after);

	String getSortBy();
	void setSortBy(String sortBy);

	String getSortOrder();
	void setSortOrder(String sortOrder);

	
	@Override
	EvalContext<List<OktaUser>> eval(Evaluator<ServiceRequest> evaluator);
	
}
