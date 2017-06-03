package org.brandao.brutos;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractConfigurableRenderView 
	implements ConfigurableRenderView{

	protected Map<DataType, RenderViewType> renderViewTypeMap;
	
	protected DataType defaultRenderViewType;
	
	public AbstractConfigurableRenderView(){
		this.renderViewTypeMap = new HashMap<DataType, RenderViewType>();
	}
	
	public void show(MvcRequest request, MvcResponse response) throws RenderViewException{
		RenderViewType renderViewType = this.renderViewTypeMap.get(response.getType());
		
		if(renderViewType == null){
			throw new RenderViewException("not found: " + renderViewType);
		}
		
		renderViewType.show(request, response);
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
	
	public void setDefaultRenderViewType(DataType dataType)
			throws RenderViewException {
		this.defaultRenderViewType = dataType;
	}

	public DataType getDefaultRenderViewType() throws RenderViewException {
		return this.defaultRenderViewType;
	}
	
}
