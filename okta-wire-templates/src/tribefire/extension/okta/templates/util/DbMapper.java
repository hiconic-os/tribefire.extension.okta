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
package tribefire.extension.okta.templates.util;

import com.braintribe.model.processing.meta.editor.ModelMetaDataEditor;

import tribefire.extension.okta.templates.wire.contract.OktaDbMappingsContract;

public class DbMapper {
	private OktaDbMappingsContract dbMappings;
	private ModelMetaDataEditor editor;

	public DbMapper(OktaDbMappingsContract dbMappings, ModelMetaDataEditor editor) {
		this.dbMappings = dbMappings;
		this.editor = editor;
	}

	public void applyDbMappings() {
		applyDbMappingsInternal();
	}

	private void applyDbMappingsInternal() {
		applyLengthMappings();
		applyIndices();
	}

	private void applyIndices() {
		// dbMappings.applyIndex(editor, AuthenticationToken.T, AuthenticationToken.token);
	}

	private void applyLengthMappings() {
		// editor.onEntityType(Entry.T).addPropertyMetaData(Entry.name, dbMappings.maxLen1k());

	}
}