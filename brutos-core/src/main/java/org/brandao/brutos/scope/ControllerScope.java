package org.brandao.brutos.scope;

import org.brandao.brutos.Invoker;
import org.brandao.brutos.mapping.Bean;
import org.brandao.brutos.mapping.Controller;

public class ControllerScope implements Scope {

	public void put(String name, Object value) {
	}

	public Object get(String name) {
		Controller controller = Invoker.getInstance().getStackRequestElement()
				.getController();

		Bean bean = controller.getBean(name);

		return bean == null ? null : bean.getValue(name + bean.getSeparator());
	}

	public Object getCollection(String name) {
		return get(name);
	}

	public void remove(String name) {
	}

}
