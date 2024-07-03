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
import java.util.Map;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

@SelectiveInformation("${profile.lastName}, ${profile.firstName} (${status})")
public interface OktaUser extends GenericEntity {
	
	EntityType<OktaUser> T = EntityTypes.T(OktaUser.class);

	String status = "status";
	String created = "created";
	String activated = "activated";
	String statusChanged = "statusChanged";
	String lastLogin = "lastLogin";
	String lastUpdated = "lastUpdated";
	String passwordChanged = "passwordChanged";
	String profile = "profile";
	String credentials = "credentials";
	String type = "type";
	
	@Name("Status")
	OktaUserStatus getStatus();
	void setStatus(OktaUserStatus status);

	@Name("Created")
	Date getCreated();
	void setCreated(Date created);
	
	@Name("Activated")
	Date getActivated();
	void setActivated(Date activated);

	@Name("Status Changed")
	Date getStatusChanged();
	void setStatusChanged(Date statusChanged);

	@Name("Last Login")
	Date getLastLogin();
	void setLastLogin(Date lastLogin);

	@Name("Last Updated")
	Date getLastUpdated();
	void setLastUpdated(Date lastUpdated);

	@Name("Password Changed")
	Date getPasswordChanged();
	void setPasswordChanged(Date passwordChanged);

	@Name("Profile")
	OktaUserProfile getProfile();
	void setProfile(OktaUserProfile profile);
	
	@Name("Credentials")
	OktaCredentials getCredentials();
	void setCredentials(OktaCredentials credentials);
	
	@Name("Type")
	Map<String,Object> getType();
	void setType(Map<String,Object> type);
	
}
