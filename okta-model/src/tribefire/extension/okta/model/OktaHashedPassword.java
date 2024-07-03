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

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface OktaHashedPassword extends GenericEntity {
	
	EntityType<OktaHashedPassword> T = EntityTypes.T(OktaHashedPassword.class);
	
	
	String algorithm = "algorithm";
	String value = "value";
	String salt = "salt";
	String workFactor = "workFactor";
	String saltOrder = "saltOrder";
	
	String getAlgorithm();
	void setAlgorithm(String algorithm);

	String getValue();
	void setValue(String value);

	String getSalt();
	void setSalt(String salt);

	Integer getWorkFactor();
	void setWorkFactor(Integer workFactor);

	String getSaltOrder();
	void setSaltOrder(String saltOrder);
	
	
	
}
