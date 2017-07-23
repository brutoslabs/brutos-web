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

	public Object decode(UseBeanData entity, Object data) throws BeanDecoderException {
		try{
			return this.getValue(entity, entity.getName(), data);
		}
		catch(Throwable e){
			throw new BeanDecoderException(e);
		}
	}

	/* UseBeanData */
	
	public Object getValue(UseBeanData entity, String name, Object dta) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		if(entity.isNullable()){
			return null;
		}
		else
		if(entity.getMetaBean() != null){
			return this.getMetaBean(entity, dta);
		}
		else
		if(entity.getMapping() != null){
			return this.getBean(entity, name, dta);
		}
		else
		if(entity.getStaticValue() != null){
			return entity.getType().convert(entity.getStaticValue());
		}
		else
		if(entity.getType() instanceof CollectionType || entity.getType() instanceof ArrayType) {
			return this.getCollectionValue(entity, name, dta);
		}
		else{
			return this.getSimpleValue(entity, name, dta);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public Object getMetaBean(UseBeanData entity, Object dta) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		Map<String, Object> data = (Map<String, Object>)dta;
		MetaBean metaBean        = entity.getMetaBean();
		String parameterName     = metaBean.getName();
		ScopeType scopeType      = entity.getScopeType();
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

		return entity.getType().convert(this.getValue(bean, null, data));			
	}

	@SuppressWarnings("unchecked")
	public Object getBean(UseBeanData entity, String name, Object dta) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Map<String, Object> data = (Map<String, Object>)dta;
		Bean bean = entity.getMapping();
		
		Object value;
		
		if(name != null){
			value = data.get(name);
			
			if(value != null){
				value = this.getValue(bean, value);
			}
			
		}
		else{
			value = this.getValue(bean, data);
		}
		
		return entity.getType().convert(value);		
	}
	
	@SuppressWarnings("unchecked")
	public Object getCollectionValue(UseBeanData entity, String name, Object dta) {
		Map<String, Object> data = (Map<String, Object>)dta;
		ScopeType scopeType      = entity.getScopeType();
		Object value;
		
		if(scopeType.equals(ScopeType.PARAM)){
			value = data.get(name);
		}
		else{
			value = entity.getScope().getCollection(name);
		}
		
		return entity.getType().convert(value);
	}
	
	@SuppressWarnings("unchecked")
	public Object getSimpleValue(UseBeanData entity, String name, Object dta) {
		Map<String, Object> data = (Map<String, Object>)dta;
		ScopeType scopeType      = entity.getScopeType();
		Object value;
		
		if(scopeType.equals(ScopeType.PARAM)){
			value = data.get(name);
		}
		else{
			value = entity.getScope().get(name);
		}
		
		return entity.getType().convert(value);
	}
	
	/* DependencyBean */
	
	public Object getValue(DependencyBean dependencyBean, String name, Object data) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		if(dependencyBean.getMapping() != null){
			return this.getBeanValue(dependencyBean, name, data);
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
				return this.getSimpleValue(dependencyBean, name, data);
			}
		}
		else{
			return this.getMetaBean(dependencyBean, name, data);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public Object getBeanValue(DependencyBean dependencyBean, String name, Object data) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Bean bean = dependencyBean.getBean().getController().getBean(dependencyBean.getMapping());
		
		Object value;
		
		if(dependencyBean.getParent().isHierarchy() && name != null){
			
			if(!(data instanceof Map)){
				throw new DependencyException("expected object");
			}
			
			Map<String,Object> dta = (Map<String,Object>)data;
			
			value = dta.get(name);
			if(value != null){
				value = this.getValue(bean, value);
			}
			
		}
		else{
			value = this.getValue(bean, data);
		}
		
		return dependencyBean.getType().convert(value);
	}

	@SuppressWarnings("unchecked")
	public Object getSimpleValue(DependencyBean dependencyBean, String name, Object data) {
		Object value;
		if(name == null){
			value = data;
		}
		else{
			ScopeType scopeType = dependencyBean.getScopeType();
			
			if(scopeType.equals(ScopeType.PARAM)){
				
				if(!(data instanceof Map)){
					throw new DependencyException("expected object");
				}
				
				Map<String,Object> dta = (Map<String,Object>)data;
				
				value = dta.get(name);
			}
			else{
				value = dependencyBean.getScope().get(name);
			}
			
		}
		
		Type type = dependencyBean.getType();
		return type.convert(value);		
	}

	@SuppressWarnings("unchecked")
	public Object getMetaBean(DependencyBean dependencyBean, String name, Object data) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
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

		return this.getValue(bean, null, data);		
	}
	
	/* bean */
	
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
			return this.getValueBean(bean, data);
		}
	}
			
	
	@SuppressWarnings("unchecked")
	public Object getValueBean(Bean bean, Object dta) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{

		if(dta != null && !(dta instanceof Map)){
			String format = 
					  "{ \r\n"
					+ "    \"<property>\": <object | value>, \r\n"
					+ "    ..., \r\n"
					+ "}";
			throw new DependencyException("expected: " + format);
		}
		
		Map<String, Object> data = (Map<String, Object>)dta;
		Object value             = this.getInstance(bean.getConstructor(), data);
		
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
			try{
				if(!prop.canSet()){
					continue;
				}
				
				Object p = this.getValue(prop, prop.getName(), data);
				exist = exist || p != null;
				prop.setValueInSource(value, p);
			}
			catch(Throwable ex){
				throw new DependencyException("fail parse property: " + prop.getName(), ex);
			}
		}
		
		return value;
	}

	/* collection */
	
	public Object getValueCollection(CollectionBean entity, Object data) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Element e = (Element)entity.getCollection();
		
		if(e.getParameterName() != null){
			return this.getValueCollectionObject(entity, e, data);
		}
		else{
			return this.getValueCollectionSimple(entity, e, data);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Object getValueCollectionObject(CollectionBean entity, Element e,  Object dta) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		if(!(dta instanceof Map)){
			String format = 
					  "{ \r\n"
					+ "    \"<property>\": <object | value>, \r\n"
					+ "    ..., \r\n"
					+ "    \"<elementName>\": [\r\n"
					+ "        <object | value>,\r\n"
					+ "        ..."
					+ "    ]\r\n"
					+ "}";
			throw new DependencyException("expected: " + format);
		}
		
		Map<String,Object> data = (Map<String,Object>)dta;
		Object destValue        = this.getValueBean(entity, data);
		Object originValue      = data.get(e.getParameterName());
		
		if(!(destValue instanceof Collection)){
			throw new DependencyException("expected a collection type");
		}

		if(!(originValue instanceof Collection)){
			String format = 
					  "{ \r\n"
					+ "    \"<property>\": <object | value>, \r\n"
					+ "    ..., \r\n"
					+ "    \"<elementName>\": [\r\n"
					+ "        <object | value>,\r\n"
					+ "        ..."
					+ "    ]\r\n"
					+ "}";
			throw new DependencyException("expected: " + format);
		}
		
		Collection<Object> originElements = (Collection<Object>)data.get(e.getParameterName());
		Collection<Object> destElements   = (Collection<Object>)destValue;
		
		for(Object o: originElements){
			Object object = this.getValue(e, null, o);
			destElements.add(object);
		}
		
		return destValue;
		
	}

	@SuppressWarnings("unchecked")
	public Object getValueCollectionSimple(CollectionBean entity, Element e, Object dta) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		if(!(dta instanceof Collection)){
			String format = 
					" [\r\n"
					+ "    <object | value>,\r\n"
					+ "    ..."
					+ "]";
			throw new DependencyException("expected: " + format);
		}
		
		Collection<Object> data         = (Collection<Object>)dta;
		Object destValue                = this.getValueBean(entity, null);
		Collection<Object> destElements = (Collection<Object>)destValue;
		
		for(Object o: data){
			Object object = this.getValue(e, null, o);
			destElements.add(object);
		}
		
		return destValue;
	}

	/* map */
	
	public Object getValueMap(MapBean entity, Object data) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Key k = (Key)entity.getKey();
		
		if(k.getParameterName() != null){
			return this.getValueMapObject(entity, k, data);
		}
		else{
			return this.getValueMapSimple(entity, k, data);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Object getValueMapObject(MapBean entity, Key k, Object dta) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		if(!(dta instanceof Map)){
			String format = 
					  "{ \r\n"
					+ "    \"<property>\": <object | value>, \r\n"
					+ "    ..., \r\n"
					+ "    \"<elementName>\": [\r\n"
					+ "        { \r\n"
					+ "            \"<keyName>\": <keyObject | keyValue>, \r\n"
					+ "            \"<elementName>\": <elementObject | elementValue> \r\n"
					+ "        }, \r\n"
					+ "        ...\r\n"
					+ "    ]\r\n"
					+ "}";
			throw new DependencyException("expected: " + format);
		}
		
		Map<String,Object> data = (Map<String,Object>)dta;
		Element e               = (Element)entity.getCollection();
		Object destValue        = this.getValueBean(entity, data);
		Object originValue      = data.get(e.getParameterName());
		
		if(!(destValue instanceof Map)){
			throw new DependencyException("expected a map type");
		}

		if(!(originValue instanceof Collection)){
			String format = 
					  "{ \r\n"
					+ "    \"<property>\": <object | value>, \r\n"
					+ "    ..., \r\n"
					+ "    \"<elementName>\": [\r\n"
					+ "        { \r\n"
					+ "            \"<keyName>\": <keyObject | keyValue>, \r\n"
					+ "            \"<elementName>\": <elementObject | elementValue> \r\n"
					+ "        }, \r\n"
					+ "        ...\r\n"
					+ "    ]\r\n"
					+ "}";
			throw new DependencyException("expected: " + format);
		}
		
		Collection<Object> originElements = (Collection<Object>)originValue;
		Map<Object,Object> destElements   = (Map<Object,Object>)destValue;
		
		for(Object o: originElements){
			
			if(!(o instanceof Map)){
				throw new DependencyException("expected a object");
			}
			
			Map<String, Object> object = (Map<String, Object>)o;
			
			Object key   = this.getValue(k, k.getParameterName(), object);
			Object value = this.getValue(e, e.getParameterName(), object);
			
			destElements.put(key, value);
		}
		
		return destValue;
	}

	@SuppressWarnings("unchecked")
	public Object getValueMapSimple(MapBean entity, Key k, Object dta) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		if(!(dta instanceof Map)){
			String format = 
					  "{ \r\n"
					+ "    \"<key>\": <object | value>, \r\n"
					+ "    ...\r\n"
					+ "}";
			throw new DependencyException("expected: " + format);
		}
		
		Map<String, Object> data        = (Map<String, Object>)dta;
		Object destValue                = this.getValueBean(entity, null);
		Map<Object,Object> destElements = (Map<Object, Object>)destValue;
		Element e                       = (Element)entity.getCollection();
		
		for(String oKey: data.keySet()){
			
			Object key   = k.getType().convert(oKey);
			Object value = this.getValue(e, null, data.get(oKey));
			
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
		boolean exist = argsList.size() < 1;
		
		for(ConstructorArgBean arg: constructor.getConstructorArgs()){
			args[i] = this.getValue(arg, arg.getParameterName(), data);
			
			if(!exist){
				exist = exist || args[i] != null || arg.isNullable();
			}
			
			i++;
		}
		
		return exist? args : null;
	}
	
	/* util */
	
}
