package cn.ms.gateway.core.filter.route;

import cn.ms.gateway.base.adapter.ICallback;
import cn.ms.gateway.base.connector.IConnector;
import cn.ms.gateway.base.filter.annotation.Filter;
import cn.ms.gateway.base.filter.support.MSFilter;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

@Filter
public class ConnectorFilter extends MSFilter<GatewayREQ, GatewayRES> {

	IConnector<GatewayREQ, GatewayRES> connector;

	@SuppressWarnings("unchecked")
	@Override
	public <MOD> void inject(MOD mod) throws Exception {
		if (mod instanceof IConnector) {
			connector = (IConnector<GatewayREQ, GatewayRES>) mod;
		}
	}

	@Override
	public boolean check(GatewayREQ req, GatewayRES res, Object... args)
			throws Exception {
		return true;
	}

	@Override
	public GatewayRES run(GatewayREQ req, GatewayRES res, Object... args)
			throws Exception {
		GatewayRES gatewayRES = connector.getConnectorHandler().handler(req,
				new ICallback<GatewayREQ, GatewayRES>() {

					@Override
					public void before(GatewayREQ req, Object... args) throws Exception {
						// TODO Auto-generated method stub

					}

					@Override
					public GatewayRES callback(GatewayREQ req, GatewayRES res, Object... args) throws Exception {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public void after(GatewayREQ req, Object... args) throws Exception {
						// TODO Auto-generated method stub

					}
				}, args);

		return gatewayRES;
	}

}