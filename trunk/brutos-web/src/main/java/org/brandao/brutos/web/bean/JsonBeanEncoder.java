/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2017 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brandao.brutos.web.bean;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.mapping.AbstractBeanEncoder;
import org.brandao.brutos.mapping.Bean;
import org.brandao.brutos.mapping.BeanEncoderException;
import org.brandao.brutos.mapping.CollectionBean;
import org.brandao.brutos.mapping.DependencyBean;
import org.brandao.brutos.mapping.Element;
import org.brandao.brutos.mapping.Key;
import org.brandao.brutos.mapping.MapBean;
import org.brandao.brutos.mapping.MetaBean;
import org.brandao.brutos.mapping.PropertyBean;
import org.brandao.brutos.mapping.UseBeanData;
import org.brandao.brutos.type.AnyType;
import org.brandao.brutos.type.DateTimeType;
import org.brandao.brutos.type.EnumType;
import org.brandao.brutos.type.Type;

/**
 * Codifica uma entidade em json.
 * 
 * @author Brandao
 *
 */
public class JsonBeanEncoder extends AbstractBeanEncoder{

	//protected static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.sss'Z'");

	protected OutputStream originalStream;
	
	protected String charsetName;
	
	protected Writer writer;
	
	public JsonBeanEncoder(OutputStream stream, String charsetName) 
			throws UnsupportedEncodingException {
		this.originalStream  = stream;
		this.charsetName     = charsetName;
		this.writer          = new OutputStreamWriter(stream, charsetName);
	}

	public void encode(UseBeanData entity, Object value) throws BeanEncoderException{
		try{
			this.innerEncode(entity, value);
		}
		catch(Throwable e){
			throw new BeanEncoderException(e);
		}
	}
	
	public void innerEncode(UseBeanData entity, Object value) throws Throwable{
		
		if(entity.getMetaBean() != null){
			
			if(entity.getName() != null){
				writer.append("\"").append(entity.getName()).append("\": { ");
			}
			
			this.encode(entity, 0, entity.getMetaBean(), value);
			
			if(entity.getName() != null){
				writer.append(" }");
			}
			
		}
		else
		if(entity.getMapping() != null){
			this.encode(entity, 0, entity.getMapping(), value);
		}
		else
		if(!entity.isNullable() && entity.getStaticValue() != null){
			
			if(entity.getName() != null){
				writer.append("\"").append(entity.getName()).append("\": ");
			}
			
			writer.append(this.getValue(entity.getType(), value));
		}
		else{
			if(entity.getName() != null){
				writer.append("\"").append(entity.getName()).append("\": ");
			}
			
			writer.append(this.getValue(entity.getType(), value));
		}
		
		writer.flush();
	}
	
	private void encode(Object parent, int fieldIndex, MetaBean bean, Object value) throws Throwable{
		
		DependencyBean dependency = null;
		Object key                = null;
		
		Map<Object, DependencyBean> metaValues = bean.getMetaValues();
		
		for(Object k: metaValues.keySet()){
			DependencyBean d = metaValues.get(k);
			
			if(d.getClassType() == value.getClass()){
				key        = k;
				dependency = d;
			}
		}

		Type type = bean.getType();
		
		if(fieldIndex++ > 0){
			writer.append(", ");
		}
		
		writer.append("\"").append(bean.getName()).append("\": ")
			.append(type.toString(key));
		
		this.encode(parent, fieldIndex, dependency, value);
		
		writer.flush();
	}
	
	private void encode(Object parent, int fieldIndex, DependencyBean entity, Object value) throws Throwable{
		
		if(entity.getMetaBean() != null){
			
			if(entity.getParameterName() != null){
				writer.append("\"").append(entity.getParameterName()).append("\": { ");
			}
			
			this.encode(entity, fieldIndex, entity.getMetaBean(), value);
			
			if(entity.getParameterName() != null){
				writer.append(" }");
			}
			
		}
		else
		if(entity.getMapping() != null){
			
			Bean mapping = 
					entity.getBean().getController().getBean(entity.getMapping());

			if (mapping == null)
				throw new BrutosException("mapping not found: " + entity.getMapping());
			
			this.encode(entity, fieldIndex, mapping, value);
		}
		else
		if(!entity.isNullable() && entity.isStatic()){
			
			if(entity.getParameterName() != null){
				writer.append("\"").append(entity.getParameterName()).append("\": ");
			}
			
			writer.append(this.getValue(entity.getType(), value));
		}
		else{
			if(entity.getParameterName() != null){
				/*
				if(fieldIndex++ > 0){
					writer.append(", ");
				}
				*/
				writer.append("\"").append(entity.getParameterName()).append("\": ");
			}
			
			writer.append(this.getValue(entity.getType(), value));
		}
		
		writer.flush();
	}

	private void encode(Object parent, int fieldIndex, Bean bean, Object value) throws Throwable{
		
		if(bean.isCollection()){
			this.encode(parent, fieldIndex, (CollectionBean)bean, value);
		}
		else
		if(bean.isMap()){
			this.encode(parent, fieldIndex, (MapBean)bean, value);
		}
		else{

			Map<String,PropertyBean> properties = bean.getFields();
			
			if(properties.isEmpty()){
				return;
			}
			
			String name = null;
			
			if(parent instanceof UseBeanData){
				name = ((UseBeanData)parent).getName();
			}
			else
			if(!(parent instanceof Element) && !(parent instanceof Key)){
				name = ((DependencyBean)parent).getParameterName();
			}
		

			int currentFieldIndex = fieldIndex;
			
			if(name != null){
				
				writer.append("\"").append(name).append("\": { ");
				
				fieldIndex = 0;				
			}
			
			fieldIndex = this.encodeProperties(properties, parent, fieldIndex, value);
			
			if(name != null){
				fieldIndex = currentFieldIndex;
				writer.append(" }");
			}
			
		}
		
		writer.flush();
	}
	
	private void encode(Object parent, int fieldIndex, CollectionBean bean, Object value) throws Throwable{
		
		Element e        = (Element)bean.getCollection();
		boolean isObject = e.getParameterName() != null;
		String name      = null;
		
		if(parent instanceof UseBeanData){
			name = ((UseBeanData)parent).getName();
		}
		else{
			name = ((DependencyBean)parent).getParameterName();
		}
		
		int currentFieldIndex = fieldIndex;
		
		//Start Object
		if(name != null){
			writer.append("\"").append(name).append("\": ");
			
			if(isObject){
				writer.append("{ ");
			}
			else{
				writer.append("[ ");
			}
			
			fieldIndex = 0;
		}
		
		if(isObject){
			fieldIndex = this.encodeProperties(bean.getFields(), parent, fieldIndex, value);
			
			if(fieldIndex++ > 0){
				writer.append(", ");
			}
			
			writer.append("\"").append(e.getParameterName()).append("\": [ ");
			
		}

		Collection<?> values = (Collection<?>) value;
		
		int index = 0;
		
		for(Object o: values){
			
			if(index > 0){
				writer.append(", ");
			}
			
			this.encode(parent, 0, index++, e, o);
		}
		
		//End object
		if(name != null){
			
			if(isObject){
				writer.append(" ] }");
			}
			else{
				writer.append(" ]");
			}
			
			fieldIndex = currentFieldIndex;
		}
		
		writer.flush();
	}	

	private void encode(Object parent, int fieldIndex, int indexElement, Element entity, Object value) throws Throwable{
		
		if(entity.getMetaBean() != null){
			writer.append("{ ");
			this.encode(entity, fieldIndex, entity.getMetaBean(), value);
			writer.append(" }");
		}
		else
		if(entity.getMapping() != null){
			
			writer.append("{ ");
			
			Bean mapping = 
					entity.getBean().getController().getBean(entity.getMapping());

			if (mapping == null)
				throw new BrutosException("mapping not found: " + entity.getMapping());
			
			this.encode(entity, fieldIndex, mapping, value);
			
			writer.append(" }");
		}
		else
		if(!entity.isNullable() && entity.isStatic()){
			writer.append(this.getValue(entity.getType(), value));
		}
		else{
			writer.append(this.getValue(entity.getType(), value));
		}
		
		writer.flush();
	}
	
	private void encode(Object parent, int fieldIndex, int indexElement, Key entity, Object value) throws Throwable{
		
		if(entity.getMetaBean() != null){
			writer.append("{ ");
			this.encode(entity, fieldIndex, entity.getMetaBean(), value);
			writer.append(" }");
		}
		else
		if(entity.getMapping() != null){
			
			writer.append("{ ");
			
			Bean mapping = 
					entity.getBean().getController().getBean(entity.getMapping());

			if (mapping == null)
				throw new BrutosException("mapping not found: " + entity.getMapping());
			
			this.encode(entity, fieldIndex, mapping, value);
			
			writer.append(" }");
		}
		else
		if(!entity.isNullable() && entity.isStatic()){
			writer.append(this.getValue(entity.getType(), value));
		}
		else{
			writer.append(this.getValue(entity.getType(), value));
		}
		
		writer.flush();
	}
	
	private void encode(Object parent, int fieldIndex, MapBean bean, Object value) throws Throwable{
		
		Element e        = (Element)bean.getCollection();
		Key k            = (Key)bean.getKey();
		
		boolean isObject = k.getParameterName() != null;
		String name      = null;
		
		if(parent instanceof UseBeanData){
			name = ((UseBeanData)parent).getName();
		}
		else{
			name = ((DependencyBean)parent).getParameterName();
		}
		
		int currentFieldIndex = fieldIndex;
		
		//Start Object
		if(name != null){
			
			writer.append("\"").append(name).append("\": ");
			writer.append("{ ");
			
			fieldIndex = 0;
		}
		
		Map<?,?> values = (Map<?,?>) value;
		
		if(isObject){
			fieldIndex = this.encodeProperties(bean.getFields(), parent, fieldIndex, value);
			
			if(fieldIndex > 0){
				writer.append(", ");
			}

			writer.append("\"").append(k.getParameterName()).append("\": [ ");
			
			int index = 0;
			for(Entry<?,?> entry: values.entrySet()){
				
				if(index > 0){
					writer.append(", ");
				}
				
				this.encode(parent, fieldIndex, index++, k, entry.getKey());
			}

			writer.append(" ], ");
			writer.append("\"").append(e.getParameterName()).append("\": [ ");
			
			index = 0;
			
			for(Entry<?,?> entry: values.entrySet()){
				
				if(index > 0){
					writer.append(", ");
				}
				
				this.encode(parent, fieldIndex, index++, e, entry.getValue());
			}
			
			fieldIndex++;
			
			writer.append(" ]");
			
		}
		else{
			
			int index = 0;
			Type type = k.getType();
			
			for(Entry<?,?> entry: values.entrySet()){
				
				if(fieldIndex++ > 0){
					writer.append(", ");
				}
				
				String key = type.toString(entry.getKey());
				writer.append("\"").append(key).append("\": ");
				this.encode(parent, fieldIndex, index++, e, entry.getValue());
			}
		}

		
		
		//End object
		if(name != null){
			writer.append(" }");
			fieldIndex = currentFieldIndex;
		}
		
		writer.flush();
	}	
	
	private int encodeProperties(Map<String,PropertyBean> properties, 
			Object parent, int fieldIndex, Object value) throws Throwable{
		
		for(PropertyBean property: properties.values()){
			
			Object fbValue = property.getValueFromSource(value);
			
			if(fbValue != null){
				
				if(fieldIndex > 0){
					writer.append(", ");
				}
				
				this.encode(parent, fieldIndex, property, fbValue);
				
				fieldIndex++;
			}
			
		}
		
		return fieldIndex;
	}
	
	private String getValue(Type type, Object value){
		if(type.getClassType() == String.class || 
			type instanceof DateTimeType || 
			type.getClass() == AnyType.class ){
			return "\"" + type.toString(value) + "\"";
		}
		else
		if(type instanceof EnumType && ((EnumType)type).getEnumerationType() == EnumerationType.STRING){
			return "\"" + type.toString(value) + "\"";
		}
		else{
			return type.toString(value);
		}
		
	}
	
	public String toString(){
		return this.originalStream.toString();
	}
}
