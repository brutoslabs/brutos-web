package org.brandao.brutos.web.bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.brandao.brutos.ScopeType;
import org.brandao.brutos.mapping.Bean;
import org.brandao.brutos.mapping.BeanDecoder;
import org.brandao.brutos.mapping.BeanDecoderException;
import org.brandao.brutos.mapping.CollectionBean;
import org.brandao.brutos.mapping.ConstructorArgBean;
import org.brandao.brutos.mapping.ConstructorBean;
import org.brandao.brutos.mapping.DependencyBean;
import org.brandao.brutos.mapping.DependencyException;
import org.brandao.brutos.mapping.Element;
import org.brandao.brutos.mapping.Key;
import org.brandao.brutos.mapping.MapBean;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.MetaBean;
import org.brandao.brutos.mapping.PropertyBean;
import org.brandao.brutos.mapping.UseBeanData;
import org.brandao.brutos.type.ArrayType;
import org.brandao.brutos.type.CollectionType;
import org.brandao.brutos.type.Type;

public class JsonBeanDecoder implements BeanDecoder{

	@SuppressWarnings("unchecked")
	public Object decode(UseBeanData entity, Object data) throws BeanDecoderException {
		try{
			if(!(data instanceof Map)){
				throw new BeanDecoderException("expected object");
			}
			
			return this.getValue(entity, (Map<String, Object>)data);
		}
		catch(BeanDecoderException e){
			throw e;
		}
		catch(Throwable e){
			throw new BeanDecoderException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Object getValue(UseBeanData entity, Map<String, Object> data) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		if(entity.isNullable()){
			return null;
		}
		else
		if(entity.getMetaBean() != null){
			MetaBean metaBean = entity.getMetaBean();
			String parameterName = metaBean.getName();
			
			ScopeType scopeType = entity.getScopeType();
			Object value;
			
			if(scopeType.equals(ScopeType.PARAM)){
				value = data.get(parameterName);
			}
			else{
				value = entity.getScope().get(parameterName);
			}
			
			Type type = entity.getType();
			value = type.convert(value);
			
			if (value == null)
				return null;

			DependencyBean bean = metaBean.getMetaValues().get(value);

			if (bean == null)
				throw new MappingException("bean not found: " + value);

			return entity.getType().convert(this.getValue(bean, data));			
		}
		else
		if(entity.getMapping() != null){
			Bean bean = entity.getMapping();
			
			String parameter = entity.getName();
			
			if(parameter != null){
				Object v = data.get(parameter);
				return this.getValue(bean, (Map<String,Object>)v);
			}
			
			Object value = this.getValue(bean, data);
			return entity.getType().convert(value);
		}
		else
		if(entity.getStaticValue() != null){
			return entity.getType().convert(entity.getStaticValue());
		}
		else
		if(entity.getType() instanceof CollectionType || entity.getType() instanceof ArrayType) {
			String parameterName = entity.getName(); 
			ScopeType scopeType  = entity.getScopeType();
			Object value;
			
			if(scopeType.equals(ScopeType.PARAM)){
				value = data.get(parameterName);
			}
			else{
				value = entity.getScope().getCollection(parameterName);
			}
			
			return entity.getType().convert(value);
		}
		else{
			String parameterName = entity.getName(); 
			ScopeType scopeType  = entity.getScopeType();
			Object value;
			
			if(scopeType.equals(ScopeType.PARAM)){
				value = data.get(parameterName);
			}
			else{
				value = entity.getScope().get(parameterName);
			}
			
			return entity.getType().convert(value);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public Object getValue(DependencyBean dependencyBean, Object data) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		if(dependencyBean.getMapping() != null){

			if(!(data instanceof Map)){
				throw new DependencyException("expected object");
			}
			
			Map<String,Object> dta = (Map<String,Object>)data;
			
			Bean bean = dependencyBean.getBean().getController().getBean(dependencyBean.getMapping());
			
			if(dependencyBean.getParent().isHierarchy()){
				String parameter = dependencyBean.getParameterName();
				if(parameter != null){
					Object v = dta.get(parameter);
					return this.getValue(bean, (Map<String,Object>)v);
				}
			}
			
			return this.getValue(bean, dta);
		}
		else
		if(dependencyBean.getMetaBean() == null){
			if(dependencyBean.isStatic()){
				return dependencyBean.getValue();
			}
			else
			if(dependencyBean.isNullable()){
				return null;
			}
			else{
				String parameter = dependencyBean.getParameterName();
				if(parameter == null){
					return dependencyBean.getScopeType().equals(ScopeType.PARAM)? data : null;
				}
				else{
					ScopeType scopeType = dependencyBean.getScopeType();
					Object value;
					if(scopeType.equals(ScopeType.PARAM)){
						
						if(!(data instanceof Map)){
							throw new DependencyException("expected object");
						}
						
						Map<String,Object> dta = (Map<String,Object>)data;
						
						value = dta.get(parameter);
					}
					else{
						value = dependencyBean.getScope().get(parameter);
					}
					
					Type type = dependencyBean.getType();
					return type.convert(value);
				}
			}
		}
		else{
			MetaBean metaBean = dependencyBean.getMetaBean();
			String parameterName = metaBean.getName();
			
			ScopeType scopeType = dependencyBean.getScopeType();
			Object value;
			
			if(scopeType.equals(ScopeType.PARAM)){
				if(!(data instanceof Map)){
					throw new DependencyException("expected object");
				}
				
				Map<String,Object> dta = (Map<String,Object>)data;
				
				value = dta.get(parameterName);
			}
			else{
				value = dependencyBean.getScope().get(parameterName);
			}
			
			Type type = dependencyBean.getType();
			value = type.convert(value);
			
			if (value == null)
				return null;

			DependencyBean bean = metaBean.getMetaValues().get(value);

			if (bean == null)
				throw new MappingException("bean not found: " + value);

			return this.getValue(bean, data);
			
		}
		
	}
	
	/* bean */
	
	@SuppressWarnings("unchecked")
	public Object getValue(Bean bean, Object data) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		if(bean.isCollection()){
			return this.getValueCollection((CollectionBean)bean, data);
		}
		else
		if(bean.isMap()){
			return this.getValueMap((MapBean)bean, data);
		}
		else{
			if(!(data instanceof Map)){
				throw new DependencyException("expected a object");
			}
			return this.getValueBean(bean, (Map<String, Object>)data);
		}
	}
			
	
	public Object getValueBean(Bean bean, Map<String, Object> data) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		Object value = this.getInstance(bean.getConstructor(), data);
		
		if(value == null){
			return null;
		}
		
		ConstructorBean constructorBean = bean.getConstructor();
		Map<String, PropertyBean> props = bean.getFields();
		boolean exist =
				constructorBean.size() > 0 ||
				constructorBean.isMethodFactory() ||
				!props.isEmpty();
		
		for(PropertyBean prop: props.values()){
			if(!prop.canSet()){
				continue;
			}
			
			Object p = this.getValue(prop, data);
			exist = exist || p != null;
			prop.setValueInSource(value, p);
		}
		
		return value;
	}

	/* collection */
	
	@SuppressWarnings("unchecked")
	public Object getValueCollection(CollectionBean entity, Object data) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Element e = (Element)entity.getCollection();
		
		if(e.getParameterName() != null){
			if(!(data instanceof Map)){
				throw new DependencyException("expected a object");
			}
			return this.getValueCollection(entity, e, (Map<String,Object>)data);
		}
		else{
			if(!(data instanceof Collection)){
				throw new DependencyException("expected a object");
			}
			return this.getValueCollection(entity, e, (Collection<Object>)data);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Object getValueCollection(CollectionBean entity, Element e, Map<String,Object> data) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Object destValue   = this.getValue(entity, data);
		Object originValue = data.get(e.getParameterName());
		
		if(!(destValue instanceof Collection)){
			throw new DependencyException("expected a collection type");
		}

		if(!(originValue instanceof Collection)){
			throw new DependencyException("expected a collection");
		}
		
		Collection<Object> originElements = (Collection<Object>)data.get(e.getParameterName());
		Collection<Object> destElements   = (Collection<Object>)destValue;
		
		for(Object o: originElements){
			Object object = this.getValue(e, o);
			destElements.add(object);
		}
		
		return destValue;
	}

	@SuppressWarnings("unchecked")
	public Object getValueCollection(CollectionBean entity, Element e, Collection<Object> originElements) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Object destValue                = this.getValue(entity, null);
		Collection<Object> destElements = (Collection<Object>)destValue;
		
		for(Object o: originElements){
			Object object = this.getValue(e, o);
			destElements.add(object);
		}
		
		return destValue;
	}

	/* map */
	
	@SuppressWarnings("unchecked")
	public Object getValueMap(MapBean entity, Object data) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Key k = (Key)entity.getKey();
		
		if(k.getParameterName() != null){
			if(!(data instanceof Map)){
				throw new DependencyException("expected a object");
			}
			return this.getValueMapObject(entity, k, (Map<String,Object>)data);
		}
		else{
			if(!(data instanceof Collection)){
				throw new DependencyException("expected a object");
			}
			return this.getValueMapSimple(entity, k, (Map<String, Object>)data);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Object getValueMapObject(MapBean entity, Key k, Map<String,Object> data) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Object destValue   = this.getValue(entity, data);
		Object originValue = data.get(k.getParameterName());
		
		if(!(destValue instanceof Map)){
			throw new DependencyException("expected a map type");
		}

		if(!(originValue instanceof Collection)){
			throw new DependencyException("expected a collection");
		}
		
		Collection<Object> originElements = (Collection<Object>)originValue;
		Map<Object,Object> destElements   = (Map<Object,Object>)destValue;
		Element e                         = (Element)entity.getCollection();
		
		for(Object o: originElements){
			
			if(!(o instanceof Map)){
				throw new DependencyException("expected a object");
			}
			
			Map<String, Object> object = (Map<String, Object>)o;
			
			Object key   = this.getValue(k, object.get(k.getParameterName()));
			Object value = this.getValue(e, object.get(e.getParameterName()));
			
			destElements.put(key, value);
		}
		
		return destValue;
	}

	@SuppressWarnings("unchecked")
	public Object getValueMapSimple(MapBean entity, Key k, Map<String, Object> originElements) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Object destValue                = this.getValue(entity, null);
		Map<Object,Object> destElements = (Map<Object, Object>)destValue;
		Element e                       = (Element)entity.getCollection();
		
		for(String oKey: originElements.keySet()){
			
			Object key   = this.getValue(k, oKey);
			Object value = this.getValue(e, originElements.get(oKey));
			
			destElements.put(key, value);
		}
		
		return destValue;
	}
	
	/* constructor */
	
	private Object getInstance(ConstructorBean constructor, Map<String, Object> data){
		try{
			return constructor.isConstructor()? 
				this.getInstanceByConstructor(constructor, data) :
				this.getInstanceByFactory(constructor, data);
		}
		catch(Throwable e){
			throw new DependencyException("create instance failed: " + constructor.getBean().getName());
		}
	}
	
	private Object getInstanceByConstructor(ConstructorBean constructor,
			Map<String, Object> data) throws InstantiationException, 
			IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		Constructor<?> insCons = constructor.getContructor();
		Object[] args          = this.getArgs(constructor, data);
		
		if(args == null){
			return null;
		}
		
		return insCons.newInstance(args);
	}

	private Object getInstanceByFactory(ConstructorBean constructor, Map<String, Object> data) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		String factory = constructor.getMethodFactory();
		Object factoryInstance;
		
		if(factory != null){
			Bean factoryBean = constructor.getBean().getController().getBean(factory);
			
			if(factoryBean == null){
				throw new IllegalStateException("bean factory not found: " + factory);
			}
			
			factoryInstance = this.getValue(factoryBean, data);
			
		}
		else{
			factoryInstance = constructor.getBean().getClassType();
		}
		
		Method method = constructor.getMethod(factoryInstance);
		
		if (constructor.isCollection() && constructor.size() == 0)
			throw new MappingException("infinite loop detected: "
					+ constructor.getBean().getName());
		
		Object[] args  = this.getArgs(constructor, data);
		
		return method.invoke(factoryInstance, args);
	}
	
	private Object[] getArgs(ConstructorBean constructor, Map<String, Object> data) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{

		List<ConstructorArgBean> argsList = constructor.getConstructorArgs();
		
		Object[] args = new Object[argsList.size()];
		int i         = 0;
		boolean exist = false;
		
		for(ConstructorArgBean arg: constructor.getConstructorArgs()){
			args[i] = this.getValue(arg, data);
			
			if(!exist){
				exist = exist || args[i] != null || arg.isNullable();
			}
			
			i++;
		}
		
		return exist? args : null;
	}
	
	/* util */
	
}
