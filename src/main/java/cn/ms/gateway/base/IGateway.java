package cn.ms.gateway.base;

import cn.ms.gateway.base.adapter.IAdapter;
import cn.ms.gateway.base.container.IContainer;

public interface IGateway<REQ, RES> extends IAdapter {

	void inject(IContainer<REQ, RES> container, Object... args)
			throws Exception;
}
