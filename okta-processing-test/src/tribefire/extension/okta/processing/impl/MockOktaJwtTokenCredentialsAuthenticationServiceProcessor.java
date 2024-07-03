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
package tribefire.extension.okta.processing.impl;

import java.io.StringReader;
import java.util.Map;

import com.braintribe.codec.marshaller.json.JsonStreamMarshaller;
import com.braintribe.exception.Exceptions;
import com.braintribe.gm.model.reason.Maybe;
import com.okta.jwt.impl.DefaultJwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.io.Decoders;
import tribefire.extension.okta.processing.jwt.AbstractOktaJwtTokenCredentialsAuthenticationServiceProcessor;

public class MockOktaJwtTokenCredentialsAuthenticationServiceProcessor extends AbstractOktaJwtTokenCredentialsAuthenticationServiceProcessor {

	private String base64ToString(String value) {
		try {
			return new String(Decoders.BASE64URL.decode(value), "UTF-8");
		} catch (Exception e) {
			throw Exceptions.unchecked(e);
		}
	}

	@Override
	protected Maybe<com.okta.jwt.Jwt> decodeJwt(String token) {

		String parts[] = token.split("\\.");

		String bodyAsStr = parts[1];

		String json = base64ToString(bodyAsStr);

		JsonStreamMarshaller marshaller = new JsonStreamMarshaller();
		Map<String, Object> map = (Map<String, Object>) marshaller.unmarshall(new StringReader(json));

		Claims body = new DefaultClaims(map);

		return Maybe.complete(new DefaultJwt(token, body.getIssuedAt().toInstant(), body.getExpiration().toInstant(), body));
	}
}
