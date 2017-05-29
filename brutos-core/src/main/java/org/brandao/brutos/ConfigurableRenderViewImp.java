package org.brandao.brutos;

import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.DataTypeMap;

public class ConfigurableRenderViewImp 
	extends AbstractConfigurableRenderView{

	public void show(MvcRequest request, MvcResponse response) throws RenderViewException{

		DataTypeMap accept        = request.getAcceptResponse();
		Action action             = request.getResourceAction().getMethodForm();
		DataTypeMap responseTypes = action.getResponseTypes();
		DataType useDataType      = accept.getFirstAccept(responseTypes);
		
		if(useDataType == null){
			throw new RenderViewException("unsupported response type");
		}
		
		RenderViewType renderViewType = this.renderViewTypeMap.get(useDataType);
		
		renderViewType.show(request, response);
	}

}
