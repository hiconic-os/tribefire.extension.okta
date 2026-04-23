// ============================================================================
package tribefire.extension.okta.config;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface RxOktaAuthorizationPreProcessor extends GenericEntity {

	final EntityType<RxOktaAuthorizationPreProcessor> T = EntityTypes.T(RxOktaAuthorizationPreProcessor.class);

	RxOktaAuthenticationSupplier getAuthenticationSupplier();
	void setAuthenticationSupplier(RxOktaAuthenticationSupplier authenticationSupplier);
}
