package tribefire.extension.okta.wire;

import java.util.Arrays;
import java.util.List;

import com.braintribe.wire.api.module.WireModule;

import hiconic.rx.module.api.wire.RxModule;
import tribefire.extension.okta.templates.wire.RxOktaTemplateWireModule;
import tribefire.extension.okta.wire.space.OktaRxModuleSpace;

public enum OktaRxModule implements RxModule<OktaRxModuleSpace> {

	INSTANCE;

	@Override
	public List<WireModule> dependencies() {
		return Arrays.asList(RxOktaTemplateWireModule.INSTANCE);
	}
}