package org.brandao.brutos.interceptor;

import java.util.List;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.mapping.PropertyController;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.scope.Scope;

public class DataInput {

	private Scope scope;

	public DataInput(Scope requestScope) {
		this.scope = requestScope;
	}

	public void read(Controller controller, Object object) {
		try {
			List fields = controller.getProperties();
			for (int i = 0; i < fields.size(); i++) {
				PropertyController ff = (PropertyController) fields.get(i);
				ff.setValue(object);
			}
			scope.put(BrutosConstants.WEBFRAME, object);
			scope.put(BrutosConstants.CONTROLLER, object);
		} catch (BrutosException e) {
			throw e;
		} catch (Exception e) {
			throw new BrutosException(e);
		}
	}

}
