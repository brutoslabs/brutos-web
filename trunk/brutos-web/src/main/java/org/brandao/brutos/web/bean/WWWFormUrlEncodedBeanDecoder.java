package org.brandao.brutos.web.bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.CodeGenerator;
import org.brandao.brutos.FetchType;
import org.brandao.brutos.ProxyFactory;
import org.brandao.brutos.mapping.Bean;
import org.brandao.brutos.mapping.BeanDecoder;
import org.brandao.brutos.mapping.BeanDecoderException;
import org.brandao.brutos.mapping.CollectionBean;
import org.brandao.brutos.mapping.ConstructorArgBean;
import org.brandao.brutos.mapping.ConstructorBean;
import org.brandao.brutos.mapping.DependencyBean;
import org.brandao.brutos.mapping.DependencyException;
import org.brandao.brutos.mapping.MapBean;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.MetaBean;
import org.brandao.brutos.mapping.PropertyBean;
import org.brandao.brutos.mapping.UseBeanData;
import org.brandao.brutos.type.ArrayType;
import org.brandao.brutos.type.CollectionType;
import org.brandao.brutos.type.Type;

public class WWWFormUrlEncodedBeanDecoder 
	implements BeanDecoder{

	private CodeGenerator codeGenerator;

	public void setCodeGenerator(CodeGenerator value) {
		this.codeGenerator = value;
	}
	
	/* useBeanData */
	
	public Object decode(UseBeanData entity, FetchType fetchType, Object data)
			throws BeanDecoderException {
		try{
			return entity.getValue(null);
		}
		catch(Throwable e){
			throw new BeanDecoderException(e);
		}
	}

	public Object getValue(UseBeanData entity, FetchType fetchType, Object data) {

		if(fetchType == null){
			fetchType = entity.getFetchType();
		}
		
		if(fetchType.equals(FetchType.LAZY)){
			ProxyFactory proxyFactory = 
					this.codeGenerator.getProxyFactory(entity.getClassType());
			return proxyFactory.getNewProxy(entity, data, this);
		}
		
		if (!entity.isNullable()) {
			if(entity.getMetaBean() != null){
				Object value =
					this.getValue(
						entity.getMetaBean(),
						entity.getName() == null ? 
							null : 
							entity.getName() + entity.getMetaBean().getSeparator(),
						-1
					);
				
				return entity.getType().convert(value);
			}
			else
			if(entity.getMapping() != null) {
				Object value =
					this.getValue(
						entity.getMapping(),
						entity.getName() == null?
							null :
							entity.getName() + entity.getMapping().getSeparator(),
						-1);
					
						
				return entity.getType().convert(value);
			}
			else
			if(entity.getStaticValue() != null){
				return entity.getType().convert(entity.getStaticValue());
			}
			else{
				Type type = entity.getType();
				Object value;
				
				if(type instanceof CollectionType || type instanceof ArrayType){
					value = 
						entity.getName() == null? 
							null : 
							entity.getScope().getCollection(entity.getName());
				}
				else{
					value = 
							entity.getName() == null? 
								null : 
								entity.getScope().get(entity.getName());
				}
				return type.convert(value);
			}
		}
		
		return null;
	}	

	public Object getValue(MetaBean metaBean, String prefix, long index) {
		String pre = prefix != null ? prefix : "";
		String key = pre + metaBean.getName();

		Object metaValue = metaBean.getScope().get(key);
		metaValue = metaBean.getType().convert(metaValue);

		if (metaValue == null)
			return null;

		DependencyBean bean = metaBean.getMetaValues().get(metaValue);

		if (bean == null){
			throw new MappingException("bean not found: " + metaValue);
		}

		return this.getValue(bean, null, prefix, index);
	}
	
	
	/* dependencyBean */
	
	public Object decode(DependencyBean dependencyBean, FetchType fetchType,
			Object data) throws BeanDecoderException {
		try{
			Map<String,Object> dta = new HashMap<String, Object>();
			
			return this.getValue(
				dependencyBean, fetchType, 
				(String)dta.get("prefix"), 
				(Integer)dta.get("index"));
		}
		catch(Throwable e){
			throw new BeanDecoderException(e);
		}
	}

	private Object getValue(DependencyBean entity, FetchType fetchType, 
			String prefix, long index) {
		
		if(fetchType == null){
			fetchType = entity.getFetchType();
		}
		
		if(fetchType.equals(FetchType.LAZY)){
			Map<String,Object> data = new HashMap<String, Object>();
			data.put("prefix", prefix);
			data.put("index", index);
			
			ProxyFactory proxyFactory = 
					this.codeGenerator.getProxyFactory(entity.getClassType());
			return proxyFactory.getNewProxy(entity, data, this);
		}
		
		Object result;

		if (entity.getMapping() != null) {
			Bean dependencyBean = entity.getController().getBean(entity.getMapping());

			if (dependencyBean == null){
				throw new BrutosException("mapping not found: " + entity.getMapping());
			}

			String newPrefix = null;
			
			if (entity.getParent().isHierarchy()) {
				String parameter = entity.getParameterName();
				
				if (parameter != null){
					newPrefix = parameter == null ? "" : parameter;
				}
			}

			if (newPrefix != null) {
				newPrefix += 
						index < 0 ? "" : 
						entity.getParent().getIndexFormat().replace("$index", String.valueOf(index));
				
				newPrefix += entity.getParent().getSeparator();
			}

			if (prefix != null) {
				if (newPrefix != null)
					newPrefix = prefix + newPrefix;
				else
					newPrefix = prefix;
			}

			Object value = this.getValue(dependencyBean, newPrefix, -1);
			return entity.getType().convert(value);
		}
		else
		if (entity.getMetaBean() == null) {
			if (entity.isStatic()){
				return entity.getValue();
			}
			else
			if(entity.isNullable()){
				return null;
			}
			else{
				String pre = prefix != null ? prefix : "";
				String param = entity.getParameterName() == null? "" : entity.getParameterName();
				String idx = 
						index < 0 ? "" : 
						entity.getParent().getIndexFormat().replace("$index", String.valueOf(index));
				String key = pre + param + idx;

				result = entity.getScope().get(key);
				return entity.getType().convert(result);
			}

		}
		else{
			result = this.getValue(entity.getMetaBean(), prefix, -1);
			return entity.getType().convert(result);
		}
		
	}
	
	/* bean */
	
	private Object getValue(Bean entity, String prefix, long index) {
		
		if(entity.isCollection()){
			return this.getValueCollection((CollectionBean)entity, prefix, index);
		}
		else
		if(entity.isMap()){
			return this.getValueMap((MapBean)entity, prefix, index);
		}
		else{
			return this.getValueBean(entity, prefix, index);
		}
	}

	private Object getValueBean(Bean entity, String prefix, long index) {
		
		ConstructorBean constructorBean = entity.getConstructor();
		Object value = this.getInstance(constructorBean, prefix, index);
		
		if(value == null){
			return null;
		}
		
		Map<String, PropertyBean> props = entity.getFields();
		
		boolean exist =
				constructorBean.size() > 0 ||
				constructorBean.isMethodFactory() ||
				!props.isEmpty();
		
		for(PropertyBean prop: props.values()){
			try{
				if(!prop.canSet()){
					continue;
				}
				
				Object p = this.getValue(prop, null, prefix, index);
				exist = exist || p != null;
				prop.setValueInSource(value, p);
			}
			catch(Throwable ex){
				throw new DependencyException("fail parse property: " + prop.getName(), ex);
			}
		}
		
		return value;
	}
	
	private Object getValueCollection(CollectionBean entity, String prefix, long index) {
		return null;
	}

	private Object getValueMap(MapBean entity, String prefix, long index) {
		return null;
	}

	/* constructor */
	
	private Object getInstance(ConstructorBean constructor, String prefix, long index){
		try{
			return constructor.isConstructor()? 
				this.getInstanceByConstructor(constructor, prefix, index) :
				this.getInstanceByFactory(constructor, prefix, index);
		}
		catch(Throwable e){
			throw new DependencyException("create instance failed: " + constructor.getBean().getName());
		}
	}
	
	private Object getInstanceByConstructor(ConstructorBean constructor,
			String prefix, long index) throws InstantiationException, 
			IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		Constructor<?> insCons = constructor.getContructor();
		Object[] args          = this.getArgs(constructor, prefix, index);
		
		if(args == null){
			return null;
		}
		
		return insCons.newInstance(args);
	}

	private Object getInstanceByFactory(ConstructorBean constructor, String prefix, long index) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		String factory = constructor.getMethodFactory();
		Object factoryInstance;
		
		if(factory != null){
			Bean factoryBean = constructor.getBean().getController().getBean(factory);
			
			if(factoryBean == null){
				throw new IllegalStateException("bean factory not found: " + factory);
			}
			
			factoryInstance = this.getValue(factoryBean, prefix, index);
			
		}
		else{
			factoryInstance = constructor.getBean().getClassType();
		}
		
		Method method = constructor.getMethod(factoryInstance);
		
		if (constructor.isCollection() && constructor.size() == 0)
			throw new MappingException("infinite loop detected: "
					+ constructor.getBean().getName());
		
		Object[] args  = this.getArgs(constructor, prefix, index);
		
		return method.invoke(factoryInstance, args);
	}
	
	private Object[] getArgs(ConstructorBean constructor, String prefix, long index) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{

		List<ConstructorArgBean> argsList = constructor.getConstructorArgs();
		
		Object[] args = new Object[argsList.size()];
		int i         = 0;
		boolean exist = argsList.size() < 1;
		
		for(ConstructorArgBean arg: constructor.getConstructorArgs()){
			args[i]        = this.getValue(arg, null, prefix, index);
			if(!exist){
				exist = exist || args[i] != null || arg.isNullable();
			}
			
			i++;
		}
		
		return exist? args : null;
	}
	
}
