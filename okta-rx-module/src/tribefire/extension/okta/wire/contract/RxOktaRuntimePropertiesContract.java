// ============================================================================
package tribefire.extension.okta.wire.contract;

import java.util.Set;

import com.braintribe.wire.api.annotation.Decrypt;
import com.braintribe.wire.api.annotation.Default;

import hiconic.rx.module.api.wire.RxPropertiesContract;

public interface RxOktaRuntimePropertiesContract extends RxPropertiesContract {

	String OKTA_SERVICE_BASE_URL();
	boolean ENABLE_OKTA_ACCESS();

	@Default("SSWS")
	String OKTA_HTTP_AUTHORIZATION_SCHEME();
	@Decrypt
	String OKTA_HTTP_AUTHORIZATION_TOKEN();

	String OKTA_HTTP_OAUTH_AUDIENCE();
	@Decrypt
	String OKTA_HTTP_OAUTH_KEY_MODULUS_N_ENCRYPTED();
	@Decrypt
	String OKTA_HTTP_OAUTH_PRIVATE_EXPONENT_D_ENCRYPTED();
	String OKTA_HTTP_OAUTH_CLIENT_ID();
	Integer OKTA_HTTP_OAUTH_EXPIRATION_S();
	Set<String> OKTA_HTTP_OAUTH_SCOPES();

	String OKTA_CLIENT_SECRET_TOKEN_URL();
	String OKTA_CLIENT_ID();
	String OKTA_CLIENT_SECRET();
	Set<String> OKTA_CLIENT_SCOPES();
}
