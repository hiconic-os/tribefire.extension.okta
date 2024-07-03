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
package tribefire.extension.okta.model;

import java.util.Date;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

@SelectiveInformation("${groupId}")
public interface OktaGroupReference extends GenericEntity {

	EntityType<OktaGroupReference> T = EntityTypes.T(OktaGroupReference.class);

	String groupId = "groupId";
	String lastUpdated = "lastUpdated";
	String priority = "priority";

	@Name("Group Id")
	String getGroupId();
	void setGroupId(String groupId);

	@Name("Last Updated")
	Date getLastUpdated();
	void setLastUpdated(Date lastUpdated);

	@Name("Priority")
	Double getPriority();
	void setPriority(Double priority);
}
