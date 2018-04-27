package org.brandao.brutos.mapping;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.CodeGenerator;
import org.brandao.brutos.FetchType;
import org.brandao.brutos.ProxyFactory;

public abstract class AbstractBeanDecoder 
	implements BeanDecoder{

	private CodeGenerator codeGenerator;

	public void setCodeGenerator(CodeGenerator value) {
		this.codeGenerator = value;
	}
	
	/* useBeanData */
	
	public Object decode(UseBeanData entity, FetchType fetchType, Object data)
			throws BeanDecoderException {
		try{
			if(data instanceof BeanLoadInfo){
				BeanLoadInfo info = (BeanLoadInfo)data;
					return this.getValue(
							entity, fetchType, info.getData().getData(), info.getPath(), info.getData());
			}
			else{
				return this.getValue(
						entity, fetchType, data, new StringBuilder(), new NodeBeanDecoder());
			}
		}
		catch(Throwable e){
			throw new BeanDecoderException(e);
		}
	}

	protected Object getValue(UseBeanData entity, 
			FetchType fetchType, Object data, StringBuilder path, NodeBeanDecoder parent) {

		NodeBeanDecoder node = this.getNextNode(entity, path, parent);
		
		if(fetchType == null){
			fetchType = entity.getFetchType();
		}
		
		if(fetchType.equals(FetchType.LAZY)){
			ProxyFactory proxyFactory = 
					this.codeGenerator.getProxyFactory(entity.getClassType());
			return proxyFactory.getNewProxy(entity, data, this);
		}
		
		if(entity.isNullable()){
			return null;
		}
		else
		if(entity.getMetaBean() != null){
			Object value = this.getValue(entity.getMetaBean(), data, path, node);
			return entity.getType().convert(value);
		}
		else
		if(entity.getMapping() != null) {
			Object value = this.getValue(entity.getMapping(), path, node);
			return entity.getType().convert(value);
		}
		else{
			return this.getValue(entity, path, node);
		}

	}	

	protected Object getValue(MetaBean entity, Object data, StringBuilder path, NodeBeanDecoder parent) {
		
		int len = path.length();
		
		this.updatePath(path, entity.getSeparator(), entity.getName());
		
		Object metaValue = this.getValue(entity, path, parent);

		path.setLength(len);
		
		DependencyBean bean = entity.getMetaValues().get(metaValue);

		if (bean == null){
			throw new MappingException("bean not found: " + metaValue);
		}

		//this.updatePath(path, entity.getSeparator(), entity.getName());
		
		return this.getValue(bean, null, path, parent);
	}
	
	
	/* dependencyBean */
	
	public Object decode(DependencyBean dependencyBean, FetchType fetchType,
			Object data) throws BeanDecoderException {
		try{
			if(data instanceof BeanLoadInfo){
				BeanLoadInfo info = (BeanLoadInfo)data;
					return this.getValue(
						dependencyBean, fetchType, 
						info.getPath(), 
						info.getData());
			}
			else{
				return this.getValue(
						dependencyBean, fetchType, 
						new StringBuilder(), 
						new NodeBeanDecoder());
			}
		}
		catch(Throwable e){
			throw new BeanDecoderException(e);
		}
	}

	protected Object getValue(DependencyBean entity, FetchType fetchType, 
			StringBuilder path, NodeBeanDecoder node) {
		
		if(fetchType == null){
			fetchType = entity.getFetchType();
		}
		
		if(fetchType.equals(FetchType.LAZY)){
			BeanLoadInfo info = new BeanLoadInfo(new StringBuilder(path), node);
			ProxyFactory proxyFactory = 
					this.codeGenerator.getProxyFactory(entity.getClassType());
			return proxyFactory.getNewProxy(entity, info, this);
		}
		
		if(entity.isNullable()){
			return null;
		}
		else
		if(entity.getMetaBean() != null){
			Object value = this.getValue(entity.getMetaBean(), path, node);
			return entity.getType().convert(value);
		}
		else
		if(entity.getMapping() != null) {
			Bean dependencyBean = entity.getController().getBean(entity.getMapping());

			if (dependencyBean == null){
				throw new BrutosException("mapping not found: " + entity.getMapping());
			}
			
			Object value = this.getValue(dependencyBean, path, node);
			return entity.getType().convert(value);
		}
		else{
			return this.getValue(entity, path, node);
		}
		
	}
	
	/* bean */
	
	protected Object getValue(Bean entity, StringBuilder path, NodeBeanDecoder parent) {
		
		if(entity.isCollection()){
			return this.getValueCollection((CollectionBean)entity, path, parent);
		}
		else
		if(entity.isMap()){
			return this.getValueMap((MapBean)entity, path, parent);
		}
		else{
			return this.getValueBean(entity, path, parent);
		}
	}

	protected Object getValueBean(Bean entity, StringBuilder path, NodeBeanDecoder parent) {
		
		ConstructorBean constructorBean = entity.getConstructor();
		Object value = this.getInstance(constructorBean, path, parent);
		
		if(value == null){
			return null;
		}
		
		Map<String, PropertyBean> props = entity.getFields();
		
		boolean exist =
				constructorBean.size() > 0 ||
				constructorBean.isMethodFactory() ||
				props.isEmpty();
		
		for(PropertyBean prop: props.values()){
			try{
				if(!prop.canSet()){
					continue;
				}
				
				int len = path.length();
				
				NodeBeanDecoder node = this.getNextNode(prop, path, parent);
				
				Object p = this.getValue(prop, null, path, node);
				
				if(p != null){
					exist = true;
					prop.setValueInSource(value, p);
				}
				
				path.setLength(len);
				
			}
			catch(Throwable ex){
				throw new DependencyException("fail parse property: " + prop.getName(), ex);
			}
		}
		
		return exist? value : null;
	}

	/* collection */
	
	protected Object getValueCollection(CollectionBean entity, StringBuilder path, NodeBeanDecoder parent) {
		Element e = (Element)entity.getCollection();
		
		if(e.getParameterName() != null){
			return this.getValueCollectionObject(entity, e, path, parent);
		}
		else{
			return this.getValueCollectionSimple(entity, e, path, parent);
		}
	}

	protected Object getValueCollectionObject(CollectionBean entity, Element e,
			StringBuilder path, NodeBeanDecoder parent) {
		throw new UnsupportedOperationException();
	}

	protected Object getValueCollectionSimple(CollectionBean entity, Element e,
			StringBuilder path, NodeBeanDecoder parent) {
		throw new UnsupportedOperationException();
	}
	
	/* map */
	
	protected Object getValueMap(MapBean entity, StringBuilder path, NodeBeanDecoder parent) {
		
		Key k = (Key)entity.getKey();
		
		if(k.getParameterName() != null){
			return this.getValueMapObject(entity, k, path, parent);
		}
		else{
			return this.getValueMapSimple(entity, k, path, parent);
		}
	}

	protected Object getValueMapObject(MapBean entity, Key k, StringBuilder path, NodeBeanDecoder parent){
		throw new UnsupportedOperationException();
	}
	
	protected Object getValueMapSimple(MapBean entity, Key k, StringBuilder path, NodeBeanDecoder parent){
		throw new UnsupportedOperationException();
	}
	
	/* constructor */
	
	protected Object getInstance(ConstructorBean constructor, StringBuilder path, NodeBeanDecoder parent){
		try{
			return constructor.isConstructor()? 
				this.getInstanceByConstructor(constructor, path, parent) :
				this.getInstanceByFactory(constructor, path, parent);
		}
		catch(Throwable e){
			throw new DependencyException("create instance failed: " + constructor.getBean().getName(), e);
		}
	}
	
	protected Object getInstanceByConstructor(ConstructorBean constructor,
			StringBuilder path, NodeBeanDecoder parent) throws InstantiationException, 
			IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		Constructor<?> insCons = constructor.getContructor();
		Object[] args          = this.getArgs(constructor, path, parent);
		
		if(args == null){
			return null;
		}
		
		return insCons.newInstance(args);
	}

	protected Object getInstanceByFactory(ConstructorBean constructor, StringBuilder path, NodeBeanDecoder parent) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		String factory = constructor.getMethodFactory();
		Object factoryInstance;
		
		if(factory != null){
			Bean factoryBean = constructor.getBean().getController().getBean(factory);
			
			if(factoryBean == null){
				throw new IllegalStateException("bean factory not found: " + factory);
			}
			
			factoryInstance = this.getValue(factoryBean, path, parent);
			
		}
		else{
			factoryInstance = constructor.getBean().getClassType();
		}
		
		Method method = constructor.getMethod(factoryInstance);
		
		if (constructor.isCollection() && constructor.size() == 0)
			throw new MappingException("infinite loop detected: "
					+ constructor.getBean().getName());
		
		Object[] args  = this.getArgs(constructor, path, parent);
		
		return method.invoke(factoryInstance, args);
	}
	
	protected Object[] getArgs(ConstructorBean constructor, StringBuilder path, NodeBeanDecoder parent) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{

		List<ConstructorArgBean> argsList = constructor.getConstructorArgs();
		
		Object[] args = new Object[argsList.size()];
		int i         = 0;
		boolean exist = argsList.size() < 1;
		
		for(ConstructorArgBean arg: constructor.getConstructorArgs()){
			
			int length = path.length();
			
			NodeBeanDecoder node = this.getNextNode(arg, path, parent);

			args[i] = this.getValue(arg, null, path, node);
			
			if(!exist){
				exist = exist || args[i] != null || arg.isNullable();
			}
			
			path.setLength(length);
			
			i++;
		}
		
		return exist? args : null;
	}

	/* util */

	protected void updatePath(StringBuilder builder, String separator, String name){
		
		if(name != null){
			if(builder.length() == 0){
				builder.append(name);
			}
			else{
				builder.append(separator).append(name);
			}
		}
		
	}

	/* Next Node */
	
	protected NodeBeanDecoder getNextNode(UseBeanData entity, StringBuilder path, NodeBeanDecoder current){
		return this.getNextNode(entity.getRealName(), entity.getName(), path, current);
	}

	protected NodeBeanDecoder getNextNode(DependencyBean entity, StringBuilder path, NodeBeanDecoder current){
		return this.getNextNode(entity.getRealName(), entity.getParameterName(), path, current);
	}
	
	protected NodeBeanDecoder getNextNode(String beanPath, String pathName, 
			StringBuilder path, NodeBeanDecoder current){
		
		Object data = current.getData();
		
		if(pathName != null){
			
			if(path.length() > 0 && !this.endsWith(path, ".")){
				path.append(".");
			}
			
			path.append(pathName);
			
			data = this.getNextDataLevel(pathName, current.getData());
			
		}

		NodeBeanDecoder node = new NodeBeanDecoder();
		node.setBeanPath(beanPath);
		node.setPath(pathName);
		node.setData(data);
		current.addNode(beanPath, node);
		
		return node;
	}
	
	/* implementation */
	
	protected abstract Object getNextDataLevel(String name, Object data);

	protected abstract Object getValue(MetaBean entity, StringBuilder path, NodeBeanDecoder node);

	protected abstract Object getValue(UseBeanData entity, StringBuilder path, NodeBeanDecoder node);
	
	protected abstract Object getValue(DependencyBean entity, StringBuilder path, NodeBeanDecoder node);
	
	/* util */
	
	protected boolean endsWith(StringBuilder builder, String value){
		return builder.length() < value.length() || builder.length() != value.length()?
				false :
				builder.substring(builder.length() - value.length()).equals(value);
	}
}
