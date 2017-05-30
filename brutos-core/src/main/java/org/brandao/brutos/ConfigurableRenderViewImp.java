package org.brandao.brutos;

public class ConfigurableRenderViewImp 
	extends AbstractConfigurableRenderView{

	public void show(MvcRequest request, MvcResponse response) throws RenderViewException{
		RenderViewType renderViewType = this.renderViewTypeMap.get(response.getType());
		renderViewType.show(request, response);
	}

}
