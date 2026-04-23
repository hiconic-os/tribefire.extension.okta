// ============================================================================
package tribefire.extension.okta.templates.wire.space;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.typecondition.origin.IsDeclaredIn;
import com.braintribe.model.meta.data.MetaData;
import com.braintribe.model.meta.data.constraint.Unmodifiable;
import com.braintribe.model.meta.selector.EntityTypeSelector;
import com.braintribe.model.processing.meta.editor.ModelMetaDataEditor;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.space.WireSpace;

import hiconic.rx.model.service.processing.md.PreProcessWith;
import hiconic.rx.model.service.processing.md.ProcessWith;
import hiconic.rx.module.api.service.ModelConfiguration;
import hiconic.rx.module.api.service.ModelConfigurations;
import hiconic.rx.webapi.client.model.meta.HttpDefaultFailureResponseType;
import hiconic.rx.webapi.client.model.meta.HttpProcessWith;
import tribefire.extension.okta.api.model.AuthorizedOktaRequest;
import tribefire.extension.okta.api.model.OktaRequest;
import tribefire.extension.okta.api.model.auth.GetAccessToken;
import tribefire.extension.okta.api.model.auth.GetClientSecretAccessToken;
import tribefire.extension.okta.api.model.auth.GetOauthAccessToken;
import tribefire.extension.okta.api.model.auth.HasAuthorization;
import tribefire.extension.okta.api.model.user.GetGroup;
import tribefire.extension.okta.api.model.user.GetUser;
import tribefire.extension.okta.api.model.user.GetUserGroups;
import tribefire.extension.okta.api.model.user.ListAppGroups;
import tribefire.extension.okta.api.model.user.ListAppUsers;
import tribefire.extension.okta.api.model.user.ListGroupMembers;
import tribefire.extension.okta.api.model.user.ListGroups;
import tribefire.extension.okta.api.model.user.ListUsers;
import tribefire.extension.okta.config.RxOktaAuthenticationSupplier;
import tribefire.extension.okta.model.OktaError;
import tribefire.extension.okta.templates.api.RxOktaTemplateContext;
import tribrefire.extension.okta.common.OktaCommons;

@Managed
public class RxOktaMetaDataSpace implements WireSpace, OktaCommons {

	@Import
	private RxOktaHttpSpace http;

	@Import
	private RxOktaTemplatesSpace initializer;

	@Import
	private RxOktaModelsSpace models;

	public void configureMetaData(RxOktaTemplateContext context, ModelConfigurations configurations) {
		configureApiModel(context, configurations);
		configureDataModelMetaData(configurations);
	}

	private void configureApiModel(RxOktaTemplateContext context, ModelConfigurations configurations) {
		ModelConfiguration configuredModel = models.configuredOktaApiModel(configurations);

		// OktaRequest related
		configuredModel.configureModel(editor -> {
			editor.onEntityType(OktaRequest.T).addMetaData(oktaRequestProcessingMds(context));
			editor.onEntityType(OktaRequest.T).addMetaData(httpDefaultFailureResponseType());
			editor.onEntityType(AuthorizedOktaRequest.T).addMetaData(preProcessWithAuthorization(context));

			editor.onEntityType(ListUsers.T).addMetaData(http.httpGet(), http.httpPathForListUsers());
			editor.onEntityType(ListUsers.T).addPropertyMetaData(http.httpQueryParamAsIsIfDeclared());
			editor.onEntityType(ListUsers.T).addPropertyMetaData(ListUsers.query, http.httpQueryParamForQuery());
			editor.onEntityType(GetUser.T).addMetaData(http.httpGet(), http.httpPathForGetUser());
			editor.onEntityType(GetUserGroups.T).addMetaData(http.httpGet(), http.httpPathForGetUserGroups());
			editor.onEntityType(ListGroups.T).addMetaData(http.httpGet(), http.httpPathForListGroups());
			editor.onEntityType(ListGroups.T).addPropertyMetaData(ListGroups.query, http.httpQueryParamForQuery());
			editor.onEntityType(ListGroupMembers.T).addMetaData(http.httpGet(), http.httpPathForListGroupMembers());
			editor.onEntityType(GetGroup.T).addMetaData(http.httpGet(), http.httpPathForGetGroup());
			editor.onEntityType(ListAppUsers.T).addMetaData(http.httpGet(), http.httpPathForListAppUsers());
			editor.onEntityType(ListAppGroups.T).addMetaData(http.httpGet(), http.httpPathForListAppGroups());

			if (context.getDefaultAuthenticationSupplier() != null) {
				editor.onEntityType(HasAuthorization.T)
						.addMetaData(preProcessWithConfiguredAuthorization(context, context.getDefaultAuthenticationSupplier()));
			}

			configureAuthorizationRequests(editor);
		});
	}

	public void configureAuthorizationRequests(ModelMetaDataEditor editor) {
		editor.onEntityType(GetAccessToken.T).addPropertyMetaData(GetOauthAccessToken.grantType, http.httpBodyParamForGrantType());
		editor.onEntityType(GetAccessToken.T).addPropertyMetaData(GetOauthAccessToken.scope, http.httpBodyParamForScope());

		editor.onEntityType(GetOauthAccessToken.T).addMetaData(http.httpPost(), http.httpPathForGetOauthAccessToken(),
				http.httpConsumesXWwwFormUrlEncoded());
		editor.onEntityType(GetOauthAccessToken.T).addPropertyMetaData(GetOauthAccessToken.clientAssertionType,
				http.httpQueryParamForClientAssertionType());
		editor.onEntityType(GetOauthAccessToken.T).addPropertyMetaData(GetOauthAccessToken.clientAssertion, http.httpQueryParamForClientAssertion());

		editor.onEntityType(GetClientSecretAccessToken.T).addMetaData(http.httpPost(), http.httpPathForGetClientSecretAccessToken(),
				http.httpConsumesXWwwFormUrlEncoded());
		editor.onEntityType(GetClientSecretAccessToken.T).addPropertyMetaData(GetClientSecretAccessToken.clientId, http.httpBodyParamForClientId());
		editor.onEntityType(GetClientSecretAccessToken.T).addPropertyMetaData(GetClientSecretAccessToken.clientSecret,
				http.httpBodyParamForClientSecret());

		editor.onEntityType(HasAuthorization.T).addPropertyMetaData(HasAuthorization.authorization, http.httpHeaderParamForAuthorization());
	}

	@Managed
	public PreProcessWith preProcessWithAuthorization(RxOktaTemplateContext context) {
		PreProcessWith bean = PreProcessWith.T.create();
		bean.setAssociate(initializer.authorizationPreProcessor(context));
		return bean;
	}

	@Managed
	public PreProcessWith preProcessWithConfiguredAuthorization(RxOktaTemplateContext context, RxOktaAuthenticationSupplier authSupplier) {
		PreProcessWith bean = PreProcessWith.T.create();
		bean.setAssociate(initializer.configuredAuthorizationPreProcessor(context, authSupplier));
		return bean;
	}

	@Managed
	public MetaData[] oktaRequestProcessingMds(RxOktaTemplateContext context) {
		MetaData[] bean = new MetaData[] { processWithOktaHttpProcessor(context), httpProcessWithOktaClient(context) };
		return bean;
	}

	@Managed
	private ProcessWith processWithOktaHttpProcessor(RxOktaTemplateContext context) {
		ProcessWith bean = ProcessWith.T.create();
		bean.setAssociate(http.dynamicHttpProcessor(context));
		return bean;
	}

	@Managed
	private HttpProcessWith httpProcessWithOktaClient(RxOktaTemplateContext context) {
		HttpProcessWith bean = HttpProcessWith.T.create();
		bean.setHttpClient(http.oktaHttpClient(context, context.getAccessAuthenticationSupplier()));
		return bean;
	}

	@Managed
	public HttpDefaultFailureResponseType httpDefaultFailureResponseType() {
		HttpDefaultFailureResponseType bean = HttpDefaultFailureResponseType.T.create();
		bean.setResponseTypeSignature(OktaError.T.getTypeSignature());
		return bean;
	}

	private void configureDataModelMetaData(ModelConfigurations configurations) {
		models.configuredOktaAccessModel(configurations).configureModel(editor -> {
			editor.onEntityType(GenericEntity.T).addPropertyMetaData(unmodifiableIfDeclaredInOktaModel());
		});
	}

	@Managed
	public Unmodifiable unmodifiableIfDeclaredInOktaModel() {
		Unmodifiable bean = Unmodifiable.T.create();
		bean.setSelector(declaredInOktaModelSelector());
		return bean;
	}

	@Managed
	private EntityTypeSelector declaredInOktaModelSelector() {
		EntityTypeSelector bean = EntityTypeSelector.T.create();
		bean.setTypeCondition(isDeclaredInOktaModelCondition());
		return bean;
	}
	@Managed
	private IsDeclaredIn isDeclaredInOktaModelCondition() {
		IsDeclaredIn bean = IsDeclaredIn.T.create();
		bean.setModelName(OKTA_DATA_MODEL_NAME);
		return bean;
	}

}
