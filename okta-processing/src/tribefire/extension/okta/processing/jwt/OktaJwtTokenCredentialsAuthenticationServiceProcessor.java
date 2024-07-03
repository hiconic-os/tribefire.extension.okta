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
package tribefire.extension.okta.processing.jwt;

import java.time.Duration;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.InitializationAware;
import com.braintribe.cfg.Required;
import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.gm.model.reason.Reasons;
import com.braintribe.gm.model.security.reason.InvalidCredentials;
import com.braintribe.logging.Logger;
import com.okta.jwt.AccessTokenVerifier;
import com.okta.jwt.Jwt;
import com.okta.jwt.JwtVerifiers;

public class OktaJwtTokenCredentialsAuthenticationServiceProcessor extends AbstractOktaJwtTokenCredentialsAuthenticationServiceProcessor
		implements InitializationAware {

	private static Logger logger = Logger.getLogger(OktaJwtTokenCredentialsAuthenticationServiceProcessor.class);

	private String issuer;
	private String audience;
	private long connectionTimeoutMs;

	private AccessTokenVerifier accessTokenVerifier;

	private ClassLoader moduleClassLoader;

	@Required
	@Configurable
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	@Required
	@Configurable
	public void setAudience(String audience) {
		this.audience = audience;
	}

	@Required
	@Configurable
	public void setConnectionTimeoutMs(long connectionTimeoutMs) {
		this.connectionTimeoutMs = connectionTimeoutMs;
	}

	@Required
	@Configurable
	public void setModuleClassLoader(ClassLoader moduleClassLoader) {
		this.moduleClassLoader = moduleClassLoader;
	}

	@Override
	public void postConstruct() {
		ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(moduleClassLoader);
		try {
			//@formatter:off
			accessTokenVerifier = JwtVerifiers.accessTokenVerifierBuilder()
				.setIssuer(issuer)
				.setAudience(audience)                // defaults to 'api://default'
				.setConnectionTimeout(Duration.ofMillis(connectionTimeoutMs)) // defaults to 1s
				.build();
			//@formatter:on
		} finally {
			Thread.currentThread().setContextClassLoader(oldClassLoader);
		}
	}

	@Override
	protected Maybe<Jwt> decodeJwt(String token) {

		try {
			return Maybe.complete(accessTokenVerifier.decode(token));
		} catch (Exception e) {
			String message = "Token could not be verified via " + issuer + ". Token was: " + token;
			logger.debug(() -> message, e);

			return Reasons.build(InvalidCredentials.T).text("JWT Token was not a valid Okta token").toMaybe();
		}
	}

}
