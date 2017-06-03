package org.brandao.brutos;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractConfigurableRenderView implements ConfigurableRenderView{

	protected Map<DataType, RenderViewType> renderViewTypeMap;
	
	public AbstractConfigurableRenderView(){
		this.renderViewTypeMap = new HashMap<DataType, RenderViewType>();
	}
	
	public void show(MvcRequest request, MvcResponse response) throws RenderViewException{
		RenderViewType renderViewType = this.getRenderViewByType(response.getType());
		renderViewType.show(request, response);
	}
	
	protected RenderViewType getRenderViewByType(DataType type){
		return this.renderViewTypeMap.get(type);
	}
	
	public synchronized void registryRenderView(DataType dataType, 
			RenderViewType value) throws RenderViewException{
		
		if(this.renderViewTypeMap.containsKey(dataType)){
			throw new RequestParserException("Parser already registered: " + dataType.getName());
		}
		
		this.renderViewTypeMap.put(dataType, value);
	}
	
	public synchronized void removeRenderView(DataType value) throws RenderViewException{
		
		if(!this.renderViewTypeMap.containsKey(value)){
			throw new RequestParserException("Parser not registered: " + value.getName());
		}
		
		this.renderViewTypeMap.remove(value);
	}
	
	public boolean contains(DataType dataType) {
		return this.renderViewTypeMap.containsKey(dataType);
	}
	
}
