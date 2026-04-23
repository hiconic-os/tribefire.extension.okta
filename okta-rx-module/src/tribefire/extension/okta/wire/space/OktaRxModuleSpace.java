package tribefire.extension.okta.wire.space;

import com.braintribe.model.access.crud.CrudExpertAccess;
import com.braintribe.model.access.crud.CrudExpertResolver;
import com.braintribe.model.access.crud.api.read.EntityReader;
import com.braintribe.model.access.crud.api.read.PopulationReader;
import com.braintribe.model.access.crud.api.read.PropertyReader;
import com.braintribe.model.access.crud.support.query.preprocess.EntityCachingQueryPreProcessor;
import com.braintribe.model.access.crud.support.query.preprocess.QueryOrderingAndPagingAdapter;
import com.braintribe.model.access.crud.support.read.EmptyReader;
import com.braintribe.model.access.crud.support.resolver.RegistryBasedExpertResolver;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.core.expert.impl.ConfigurableGmExpertRegistry;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import hiconic.rx.access.module.api.AccessContract;
import hiconic.rx.access.module.api.AccessDataModelConfiguration;
import hiconic.rx.access.module.api.AccessModelConfigurations;
import hiconic.rx.access.module.api.AccessServiceModelConfiguration;
import hiconic.rx.module.api.service.ModelConfigurations;
import hiconic.rx.module.api.wire.RxModuleContract;
import hiconic.rx.module.api.wire.RxPlatformContract;
import tribefire.extension.okta.config.RxOktaAccess;
import tribefire.extension.okta.model.OktaGroup;
import tribefire.extension.okta.model.OktaUser;
import tribefire.extension.okta.processing.crud.OktaGroupReader;
import tribefire.extension.okta.processing.crud.OktaUserReader;
import tribefire.extension.okta.templates.wire.space.RxOktaModelsSpace;
import tribefire.extension.okta.wire.contract.RxOktaRuntimePropertiesContract;
import tribefire.extension.okta.wire.space.initializer.RxOktaSpace;

/**
 * This module's javadoc is yet to be written.
 */
@Managed
public class OktaRxModuleSpace implements RxModuleContract {

	@Import
	private RxPlatformContract platform;

	@Import
	private RxOktaSpace okta;

	@Import
	private RxOktaRuntimePropertiesContract runtime;

	@Import
	private AccessContract access;

	@Import
	private RxOktaModelsSpace models;

	@Override
	public void configureModels(ModelConfigurations configurations) {
		okta.configure(configurations);

		if (runtime.ENABLE_OKTA_ACCESS())
			configureOktaAccess(configurations);
	}

	private void configureOktaAccess(ModelConfigurations configurations) {
		RxOktaAccess deployable = okta.oktaAccess(configurations);

		AccessModelConfigurations accessModelConfigurations = access.accessModelConfigurations();

		AccessDataModelConfiguration dataModelConfiguration = accessModelConfigurations.dataModelConfiguration(deployable.getAccessId());
		dataModelConfiguration.addModel(models.configuredOktaAccessModel(configurations));

		AccessServiceModelConfiguration apiModelConfiguration = accessModelConfigurations.serviceModelConfiguration(deployable.getAccessId());
		apiModelConfiguration.addModel(models.configuredOktaApiModel(configurations));

		access.deploy(deployable, oktaAccess(deployable));
	}

	@Managed
	private CrudExpertAccess oktaAccess(RxOktaAccess deployable) {
		CrudExpertAccess bean = new CrudExpertAccess();
		bean.setAccessId(deployable.getAccessId());
		bean.setMetaModelProvider(() -> dataModelOf(deployable));
		bean.setExpertResolver(oktaCrudExpertResolver(deployable));
		bean.setQueryPreProcessor(new EntityCachingQueryPreProcessor(QueryOrderingAndPagingAdapter.REMOVE_ORDERING_AND_ADAPT_PAGING_TO_FIRST_PAGE));

		//@formatter:off
		bean.configureDefaultTc()
			.addExclusionProperty(OktaUser.T, OktaUser.profile)
			.addExclusionProperty(OktaUser.T, OktaUser.credentials)
			.addExclusionProperty(OktaUser.T, OktaUser.type);
		//@formatter:on

		return bean;
	}

	private GmMetaModel dataModelOf(RxOktaAccess deployable) {
		return access.accessDomains().byId(deployable.getAccessId()).configuredDataModel().modelOracle().getGmMetaModel();
	}

	@Managed
	private CrudExpertResolver oktaCrudExpertResolver(RxOktaAccess access) {
		RegistryBasedExpertResolver resolver = new RegistryBasedExpertResolver();
		//@formatter:off
		resolver.setRegistry(
				new ConfigurableGmExpertRegistry()
				// User (Readers)
				.add(EntityReader.class, OktaUser.class, userReader(access))
				.add(PopulationReader.class, OktaUser.class, userReader(access))
				// Group (Readers)
				.add(EntityReader.class, OktaGroup.class, groupReader(access))
				.add(PopulationReader.class, OktaGroup.class, groupReader(access))
				// Default
				.add(EntityReader.class, GenericEntity.class, EmptyReader.instance())
				.add(PopulationReader.class, GenericEntity.class, EmptyReader.instance())
				.add(PropertyReader.class, GenericEntity.class, EmptyReader.instance()));
		//@formatter:on
		return resolver;
	}

	@Managed
	private OktaUserReader userReader(RxOktaAccess access) {
		OktaUserReader bean = new OktaUserReader();
		bean.setEvaluator(platform.serviceProcessing().evaluator());
		bean.setDomainId(access.getAccessId());
		return bean;
	}

	@Managed
	private OktaGroupReader groupReader(RxOktaAccess access) {
		OktaGroupReader bean = new OktaGroupReader();
		bean.setEvaluator(platform.serviceProcessing().evaluator());
		bean.setDomainId(access.getAccessId());
		return bean;
	}

}