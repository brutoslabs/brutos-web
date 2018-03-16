package org.brandao.brutos.mapping;

import java.util.HashMap;
import java.util.Map;

public class NodeBeanDecoder {

	private String path;

	private String beanPath;
	
	private Map<String, NodeBeanDecoder> nextNodes;

	private Object data;
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getBeanPath() {
		return beanPath;
	}

	public void setBeanPath(String beanPath) {
		this.beanPath = beanPath;
	}

	public void addNode(String name, NodeBeanDecoder node){
		if(this.nextNodes == null){
			this.nextNodes = new HashMap<String, NodeBeanDecoder>();
		}
		
		NodeBeanDecoder o = this.nextNodes.get(name);
		
		if(o != null){
			if(o instanceof ListNodeBeanDecoder){
				((ListNodeBeanDecoder)o).getItens().add(node);
			}
			else{
				ListNodeBeanDecoder list = new ListNodeBeanDecoder();
				list.getItens().add((NodeBeanDecoder) o);
				this.nextNodes.put(name, list);
			}
		}
		else{
			this.nextNodes.put(name, node);
		}
		
	}
	
	public NodeBeanDecoder getNextNode(String name){
		return this.nextNodes == null?
				null :
				this.nextNodes.get(name);
	}
	
}
