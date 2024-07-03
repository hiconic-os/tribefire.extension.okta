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

import com.braintribe.cfg.Configurable;
import com.braintribe.model.access.crud.api.read.PopulationReadingContext;
import com.braintribe.model.query.Ordering;
import com.braintribe.model.query.Paging;

public abstract class OktaReader {

	private int defaultPageSize = 100;

	// ***************************************************************************************************
	// Setters
	// ***************************************************************************************************

	@Configurable
	public void setDefaultPageSize(int defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}

	// ***************************************************************************************************
	// Query Helpers
	// ***************************************************************************************************

	protected Ordering resolveOrdering(PopulationReadingContext<?> context) {
		// Providing the type specific ordering is currently not supported by the framework (AccessAdapter).
		// So, context.getOrdering() always returns null.
		// Once it is supported we are prepared to use it with this method.
		// For now we are falling back to the originalOrdering of the query, if available.
		Ordering ordering = context.getOrdering();
		return (ordering == null) ? context.originalOrdering() : ordering;
	}

	protected Paging resolvePaging(PopulationReadingContext<?> context) {
		Paging paging = Paging.T.create();
		Paging originalPaging = context.originalPaging();
		if (originalPaging == null) {
			// No original Paging provided in the context. Create Default one.
			paging.setStartIndex(0);
			paging.setPageSize(this.defaultPageSize);
		} else {
			paging.setStartIndex(originalPaging.getStartIndex());
			paging.setPageSize(originalPaging.getPageSize());
		}

		// Now we have a paging (either originally from the request or the default one)
		// We finally need to increment the pageSize in order to let the framework know whether there are more elements
		// available.
		paging.setPageSize(paging.getPageSize() + 1); // This ensures the hasMore indication - this will be removed by
														// the AccessAdapter if available.

		return paging;
	}

}
