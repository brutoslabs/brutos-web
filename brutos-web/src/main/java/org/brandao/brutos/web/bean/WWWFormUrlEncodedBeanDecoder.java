package org.brandao.brutos.web.bean;

import org.brandao.brutos.mapping.AbstractBeanDecoder;
import org.brandao.brutos.mapping.DependencyBean;
import org.brandao.brutos.mapping.MetaBean;
import org.brandao.brutos.mapping.NodeBeanDecoder;
import org.brandao.brutos.mapping.UseBeanData;
import org.brandao.brutos.type.ArrayType;
import org.brandao.brutos.type.CollectionType;
import org.brandao.brutos.type.Type;

public class WWWFormUrlEncodedBeanDecoder
	extends AbstractBeanDecoder {

	@Override
	protected Object getNextDataLevel(String name, Object data) {
		return null;
	}

	@Override
	protected Object getValue(MetaBean entity, StringBuilder path, NodeBeanDecoder node) {
		Object metaValue = entity.getScope().get(path.toString());
		return entity.getType().convert(metaValue);
	}

	@Override
	protected Object getValue(UseBeanData entity, StringBuilder path, NodeBeanDecoder node) {
		
		if(entity.getStaticValue() != null){
			return entity.getType().convert(entity.getStaticValue());
		}
		
		Type type = entity.getType();
		Object value;
		
		if(type instanceof CollectionType || type instanceof ArrayType){
			value = 
				path.length() == 0? 
					null : 
					entity.getScope().getCollection(path.toString());
		}
		else{
			value = 
				path.length() == 0? 
						null : 
						entity.getScope().get(path.toString());
		}
		return type.convert(value);
	}

	@Override
	protected Object getValue(DependencyBean entity, StringBuilder path,
			NodeBeanDecoder node) {
		
		if(entity.getValue() != null){
			return entity.getType().convert(entity.getValue());
		}
		
		Type type = entity.getType();
		Object value;
		
		if(type instanceof CollectionType || type instanceof ArrayType){
			value = 
				path.length() == 0? 
					null : 
					entity.getScope().getCollection(path.toString());
		}
		else{
			value = 
				path.length() == 0? 
					null : 
					entity.getScope().get(path.toString());
		}
		
		return type.convert(value);		
	}
	
}
