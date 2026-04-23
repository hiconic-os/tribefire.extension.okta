// ============================================================================
package tribefire.extension.okta.configuration.model;

import java.util.Map;
import java.util.Set;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface OktaConfiguration extends GenericEntity {

	EntityType<OktaConfiguration> T = EntityTypes.T(OktaConfiguration.class);

	@Name("Issuer")
	@Description("The issuer the verifier will expect.")
	String getIssuer();
	void setIssuer(String issuer);

	@Name("Audience")
	@Description("The audience the verifier will expect. Default value: \"api://default\".")
	@Initializer("'api://default'")
	String getAudience();
	void setAudience(String audience);

	@Name("Connection Timeout")
	@Description("The connection timeout (in milliseconds).")
	@Initializer("10000l")
	long getConnectionTimeoutMs();
	void setConnectionTimeoutMs(long connectionTimeoutMs);

	@Name("Read Timeout")
	@Description("The network read timeout (in milliseconds).")
	@Initializer("10000l")
	long getReadTimeoutMs();
	void setReadTimeoutMs(long readTimeoutMs);

	@Name("Properties Claims")
	@Description("Claims that should be added to the properties (which eventually will be added to the user session).")
	Set<String> getPropertiesClaims();
	void setPropertiesClaims(Set<String> propertiesClaims);

	@Name("Username Claim")
	@Description("The claim that contains the user name.")
	@Initializer("'sub'")
	String getUsernameClaim();
	void setUsernameClaim(String usernameClaim);

	@Description("A map that maps from a Claim property name to a prefix that should be applied to all values to deduce user roles")
	@Name("Claim Roles and Prefixes")
	Map<String, String> getClaimRolesAndPrefixes();
	void setClaimRolesAndPrefixes(Map<String, String> claimRolesAndPrefixes);

	@Name("Default Roles")
	@Description("A set of roles users should get.")
	Set<String> getDefaultRoles();
	void setDefaultRoles(Set<String> defaultRoles);

	@Name("Invalidate JwtTokenCredentials on Logout")
	@Initializer("true")
	boolean getInvalidateTokenCredentialsOnLogout();
	void setInvalidateTokenCredentialsOnLogout(boolean invalidateTokenCredentialsOnLogout);

}
