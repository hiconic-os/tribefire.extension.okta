// ============================================================================
package tribefire.extension.okta.config;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

import hiconic.rx.access.model.configuration.Access;

public interface RxOktaAccess extends Access {

	EntityType<RxOktaAccess> T = EntityTypes.T(RxOktaAccess.class);

}
