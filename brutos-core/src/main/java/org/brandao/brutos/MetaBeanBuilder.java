package org.brandao.brutos;

import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.MetaBean;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.type.DateTimeType;
import org.brandao.brutos.type.EnumType;
import org.brandao.brutos.type.Type;

public class MetaBeanBuilder {

	private MetaBean metaBean;
	
	private EnumerationType enumProperty;
	
	private String temporalProperty;
	
	public MetaBeanBuilder(MetaBean metaBean, String name, 
            ScopeType scope, EnumerationType enumProperty, String temporalProperty, 
            Class<?> classType, Type type){
		
        this.metaBean         = metaBean;
        this.enumProperty     = enumProperty;
        this.temporalProperty = temporalProperty;
        
        this.setScope(scope);
        this.setName(name);
        
        if(type == null)
        	this.setClassType(classType);
        else
        	this.setType(type);
	}
	
	public MetaBeanBuilder addMetaValue(String value, String mapping){
		this.metaBean.putMetaValue(value, mapping);
		return this;
	}

	public MetaBeanBuilder removeMetaValue(String value){
		this.metaBean.removeMetaValue(value);
		return this;
	}
	
	public MetaBeanBuilder setName(String name){
		name = StringUtil.adjust(name);
        
        if(StringUtil.isEmpty(name))
            throw new IllegalArgumentException("meta bean name cannot be empty");
		
        this.metaBean.setName(name);
        
        return this;
	}
	
	public String getName(){
		return this.metaBean.getName();
	}
	
	public MetaBeanBuilder setScope(ScopeType scope){
		
        if(scope == null)
            throw new NullPointerException("scope of meta bean cannot be null");
		
        this.metaBean.setScopeType(scope);
        
        return this;
	}
	
	public ScopeType getScope(){
		return this.metaBean.getScopeType();
	}
	
	public MetaBeanBuilder setEnumProperty(EnumerationType enumProperty){
		Type type = this.metaBean.getType();
		
		if(type instanceof EnumType)
			((EnumType)type).setEnumerationType(enumProperty);
		else
			throw new MappingException("enumProperty not supported");
		
		return this;
	}

	public EnumerationType getEnumProperty(EnumerationType enumProperty){
		Type type = this.metaBean.getType();
		this.enumProperty = enumProperty;
		
		if(type instanceof EnumType)
			return ((EnumType)type).getEnumerationType();
		else
			return null;
	}
	
	public MetaBeanBuilder setTemporalProperty(String pattern){
		Type type = this.metaBean.getType();
		this.temporalProperty = pattern;
		
		if(type instanceof DateTimeType)
			((DateTimeType)type).setPattern(pattern);
		else
			throw new MappingException("temporalProperty not supported");
		
		return this;
	}

	public String getTemporalProperty(){
		Type type = this.metaBean.getType();
		
		if(type instanceof DateTimeType)
			return ((DateTimeType)type).getPattern();
		else
			return null;
	}

	public MetaBeanBuilder setClassType(Class<?> classType){
		
    	if(this.metaBean.getType() == null){
        	Type type = 
                    this.metaBean
                    .getController()
                    .getContext().getTypeManager()
                        .getType(
                    		classType,
                            this.enumProperty,
                            this.temporalProperty );
        	
        	this.metaBean.setType(type);
    	}
    	else
			throw new MappingException("type has been defined");
        		
        
        return this;
	}

	public Class<?> getClassType(){
		return this.metaBean.getType().getClassType();
	}

	public MetaBeanBuilder setType(Type type){
		this.metaBean.setType(type);
		return this;
	}

	public Type getType(){
		return this.metaBean.getType();
	}
	
}
