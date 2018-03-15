package org.brandao.brutos.mapping;

import java.util.ArrayList;
import java.util.List;

public class ListNodeBeanDecoder 
	extends NodeBeanDecoder{

	private List<NodeBeanDecoder> itens;

	public ListNodeBeanDecoder(){
		this.itens = new ArrayList<NodeBeanDecoder>();
	}
	public List<NodeBeanDecoder> getItens() {
		return itens;
	}

	public void setItens(List<NodeBeanDecoder> itens) {
		this.itens = itens;
	}

	
}
