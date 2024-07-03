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

public interface OktaUserProfile  extends GenericEntity {
	
	EntityType<OktaUserProfile> T = EntityTypes.T(OktaUserProfile.class);
	
	String login = "login";
	String email = "email";
	String secondEmail = "secondEmail";
	String firstName = "firstName";
	String lastName = "lastName";
	String middleName = "middleName";
	String honorificPrefix = "honorificPrefix";
	String honorificSuffix = "honorificSuffix";
	String title = "title";
	String displayName = "displayName";
	String nickName = "nickName";
	String profileUrl = "profileUrl";
	String primaryPhone = "primaryPhone";
	String mobilePhone = "mobilePhone";
	String streetAddress = "streetAddress";
	String city = "city";
	String state = "state";
	String zipCode = "zipCode";
	String countryCode = "countryCode";
	String postalAddress = "postalAddress";
	String preferredLanguage = "preferredLanguage";
	String locale = "locale";
	String timezone = "timezone";
	String userType = "userType";
	String employeeNumber = "employeeNumber";
	String costCenter = "costCenter";
	String organization = "organization";
	String division = "division";
	String department = "department";
	String managerId = "managerId";
	String manager = "manager";
	
	String getLogin();
	void setLogin(String login);
	String getEmail();
	void setEmail(String email);
	String getSecondEmail();
	void setSecondEmail(String secondEmail);
	String getFirstName();
	void setFirstName(String firstName);
	String getLastName();
	void setLastName(String lastName);
	String getMiddleName();
	void setMiddleName(String middleName);
	String getHonorificPrefix();
	void setHonorificPrefix(String honorificPrefix);
	String getHonorificSuffix();
	void setHonorificSuffix(String honorificSuffix);
	String getTitle();
	void setTitle(String title);
	String getDisplayName();
	void setDisplayName(String displayName);
	String getNickName();
	void setNickName(String nickName);
	String getProfileUrl();
	void setProfileUrl(String profileUrl);
	String getPrimaryPhone();
	void setPrimaryPhone(String primaryPhone);
	String getMobilePhone();
	void setMobilePhone(String mobilePhone);
	String getStreetAddress();
	void setStreetAddress(String streetAddress);
	String getCity();
	void setCity(String city);
	String getState();
	void setState(String state);
	String getZipCode();
	void setZipCode(String zipCode);
	String getCountryCode();
	void setCountryCode(String countryCode);
	String getPostalAddress();
	void setPostalAddress(String postalAddress);
	String getPreferredLanguage();
	void setPreferredLanguage(String preferredLanguage);
	String getLocale();
	void setLocale(String locale);
	String getTimezone();
	void setTimezone(String timezone);
	String getUserType();
	void setUserType(String userType);
	String getEmployeeNumber();
	void setEmployeeNumber(String employeeNumber);
	String getCostCenter();
	void setCostCenter(String costCenter);
	String getOrganization();
	void setOrganization(String organization);
	String getDivision();
	void setDivision(String division);
	String getDepartment();
	void setDepartment(String department);
	String getManagerId();
	void setManagerId(String managerId);
	String getManager();
	void setManager(String manager);
	
	
}