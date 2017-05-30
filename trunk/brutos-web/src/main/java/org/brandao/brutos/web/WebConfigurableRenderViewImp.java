package org.brandao.brutos.web;

import org.brandao.brutos.ConfigurableRenderViewImp;
import org.brandao.brutos.web.http.view.JSPRenderView;
import org.brandao.brutos.web.http.view.JsonRenderView;

public class WebConfigurableRenderViewImp extends ConfigurableRenderViewImp{

	public WebConfigurableRenderViewImp(){
		super.registryRenderView(MediaType.TEXT_HTML, new JSPRenderView());
		super.registryRenderView(MediaType.APPLICATION_JSON, new JsonRenderView());
	}
	
}
