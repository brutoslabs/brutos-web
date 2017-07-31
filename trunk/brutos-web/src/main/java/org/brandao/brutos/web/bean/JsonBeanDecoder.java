package org.brandao.brutos.web.bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.brandao.brutos.CodeGenerator;
import org.brandao.brutos.FetchType;
import org.brandao.brutos.ProxyFactory;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.Scopes;
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
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.type.ArrayType;
import org.brandao.brutos.type.CollectionType;
import org.brandao.brutos.type.Type;

public class JsonBeanDecoder implements BeanDecoder{
	
	private CodeGenerator codeGenerator;
	
	public void setCodeGenerator(CodeGenerator value){
		this.codeGenerator = value;
	}
	
	public Object decode(UseBeanData entity, FetchType fetchType, Object data) throws BeanDecoderException {
		try{
			return this.getValue(entity, fetchType, data);
		}
		catch(Throwable e){
			throw new BeanDecoderException(e);
		}
	}

	/* UseBeanData */
	
	@SuppressWarnings("unchecked")
	public Object getValue(UseBeanData entity, FetchType fetchType, Object requestData) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		if(fetchType == null){
			fetchType = entity.getFetchType();
		}
		
		if(fetchType.equals(FetchType.LAZY)){
			ProxyFactory proxyFactory = 
					this.codeGenerator.getProxyFactory(entity.getClassType());
			return proxyFactory.getNewProxy(entity, requestData, this);
		}
		
		if(entity.isNullable()){
			return null;
		}
		else
		if(entity.getMetaBean() != null){
			return this.getMetaBean(entity, requestData);
		}
		else
		if(entity.getMapping() != null){
			
			if(entity.getName() != null){
				
				if(requestData == null || !this.isObject(requestData)){
					throw new DependencyException("expected a object");
				}
				
				Object value = 
					this.getScopedValue(
						entity.getName(), 
						entity.getScopeType(), (Map<String,Object>)requestData);
				
				return this.getBean(entity, value);				
			}
			else{
				return this.getBean(entity, requestData);
			}
			
		}
		else
		if(entity.getStaticValue() != null){
			return entity.getType().convert(entity.getStaticValue());
		}
		else
		if(entity.getType() instanceof CollectionType || entity.getType() instanceof ArrayType) {
			return this.getCollectionValue(entity, requestData);
		}
		else{
			return this.getSimpleValue(entity, requestData);
		}
		
	}
	
	public Object getMetaBean(UseBeanData entity, Object requestData) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		MetaBean metaBean        = entity.getMetaBean();
		String parameterName     = metaBean.getName();
		ScopeType scopeType      = entity.getScopeType();
		Object value             = this.getScopedValue(parameterName, scopeType, requestData);
		Type type                = entity.getType();
		value                    = type.convert(value);
		
		if (value == null){
			return null;
		}

		DependencyBean bean = metaBean.getMetaValues().get(value);

		if (bean == null){
			throw new MappingException("bean not found: " + value);
		}

		return entity.getType().convert(this.getValue(bean, null, requestData));			
	}

	public Object getBean(UseBeanData entity, Object requestData) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Bean bean                = entity.getMapping();
		Object value             = this.getValue(bean, requestData);
		return entity.getType().convert(value);		
	}
	
	public Object getCollectionValue(UseBeanData entity, Object requestData) {
		
		ScopeType scopeType      = entity.getScopeType();
		Object value             = this.getScopedCollection(entity.getName(), scopeType, requestData);
		return entity.getType().convert(value);
	}
	
	public Object getSimpleValue(UseBeanData entity, Object requestData) {
		
		ScopeType scopeType      = entity.getScopeType();
		Object value             = this.getScopedValue(entity.getName(), scopeType, requestData);
		return entity.getType().convert(value);
	}
	
	/* DependencyBean */

	public Object decode(DependencyBean dependencyBean, FetchType fetchType, Object data) throws BeanDecoderException{
		try{
			return this.getValue(dependencyBean, fetchType, data);
		}
		catch(Throwable e){
			throw new BeanDecoderException(e);
		}
	}
	
	public Object getValue(DependencyBean dependencyBean, FetchType fetchType, Object data) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{

		if(fetchType == null){
			fetchType = dependencyBean.getFetchType();
		}
		
		if(fetchType.equals(FetchType.LAZY)){
			ProxyFactory proxyFactory = 
					this.codeGenerator.getProxyFactory(dependencyBean.getClassType());
			return proxyFactory.getNewProxy(dependencyBean, data, this);
		}
		
		if(dependencyBean.getMapping() != null){
			return this.getBeanValue(dependencyBean, data);
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
				return this.getSimpleValue(dependencyBean, data);
			}
		}
		else{
			return this.getMetaBean(dependencyBean, data);
		}
		
	}
	
	public Object getBeanValue(DependencyBean dependencyBean, Object data) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Bean bean = dependencyBean.getBean().getController().getBean(dependencyBean.getMapping());
		
		if(data == null){
			return null;
		}
		else{
			Object value = this.getValue(bean, data);
			return dependencyBean.getType().convert(value);
		}
	}

	public Object getSimpleValue(DependencyBean dependencyBean, Object data) {
		Type type = dependencyBean.getType();
		return type.convert(data);		
	}

	public Object getMetaBean(DependencyBean dependencyBean, Object requestData) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		MetaBean metaBean    = dependencyBean.getMetaBean();
		String parameterName = metaBean.getName();
		ScopeType scopeType  = dependencyBean.getScopeType();
		Object value         = this.getScopedValue(parameterName, scopeType, requestData);
		Type type            = dependencyBean.getType();
		value                = type.convert(value);
		
		if (value == null){
			return null;
		}

		DependencyBean bean = metaBean.getMetaValues().get(value);

		if (bean == null){
			throw new MappingException("bean not found: " + value);
		}
		
		return this.getValue(bean, null, requestData);		
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
			
	
	public Object getValueBean(Bean bean, Object requestData) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{

		Object value = this.getInstance(bean.getConstructor(), requestData);
		
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
				
				Object fieldData = this.getScopedValue(prop.getParameterName(), prop.getScopeType(), requestData);
				Object p         = this.getValue(prop, null, fieldData);
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
	
	/**
	 * Converte os dados da requisição em um objeto do tipo {@link java.util.Collection}.
	 * <p>Formado esperado:</p>
	 * <pre>
     * {
     *    "&lt;property&gt;": &lt;object&gt; | &lt;value&gt;,
     *    ..., 
     *    "elements": [ &lt;object&gt; | &lt;value&gt;, ... ]
     * }
	 * </pre>
	 * @param entity Mapeamento da entidade.
	 * @param k Mapeamento dos elementos.
	 * @param requestData Dados da requisição.
	 * @throws IllegalAccessException Lançada se ocorrer uma falha ao tentar criar a instância do {@link java.util.Collection}.
	 * @throws IllegalArgumentException Lançada se ocorrer uma falha ao tentar criar a instância do {@link java.util.Collection}.
	 * @throws InvocationTargetException Lançada se ocorrer uma falha ao tentar criar a instância do {@link java.util.Collection}.
	 * @throws DependencyException Lançada se os dados da solicitação não forem o esperado.
	 * @return Instância da classe {@link java.util.Collection}.
	 */	
	@SuppressWarnings("unchecked")
	public Object getValueCollectionObject(CollectionBean entity, Element e,  Object requestData) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Object destValue        = this.getValueBean(entity, requestData);
		Object originValue      = this.getScopedValue(e.getParameterName(), e.getScopeType(), requestData);
		
		if(destValue == null){
			return null;
		}
		
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
		
		Collection<Object> originElements = (Collection<Object>)this.getScopedValue("elements", e.getScopeType(), requestData);
		Collection<Object> destElements   = (Collection<Object>)destValue;
		
		if(originElements == null){
			return null;
		}
		
		for(Object o: originElements){
			Object object = this.getValue(e, null, o);
			destElements.add(object);
		}
		
		return destValue;
		
	}

	/**
	 * Converte os dados da requisição em um objeto do tipo {@link java.util.Collection}.
	 * <p>Formado esperado:</p>
	 * <pre>
     * [ &lt;object&gt; | &lt;value&gt;, ... ]
	 * </pre>
	 * @param entity Mapeamento da entidade.
	 * @param k Mapeamento dos elementos.
	 * @param requestData Dados da requisição.
	 * @throws IllegalAccessException Lançada se ocorrer uma falha ao tentar criar a instância do {@link java.util.Collection}.
	 * @throws IllegalArgumentException Lançada se ocorrer uma falha ao tentar criar a instância do {@link java.util.Collection}.
	 * @throws InvocationTargetException Lançada se ocorrer uma falha ao tentar criar a instância do {@link java.util.Collection}.
	 * @throws DependencyException Lançada se os dados da solicitação não forem o esperado.
	 * @return Instância da classe {@link java.util.Collection}.
	 */	
	@SuppressWarnings("unchecked")
	public Object getValueCollectionSimple(CollectionBean entity, Element e, Object requestData) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		if(requestData == null){
			return null;
		}
		
		if(!this.isArray(requestData)){
			String format = " [ <object> | <value>, ... ]";
			throw new DependencyException("expected: " + format);
		}
		
		Collection<Object> data = (Collection<Object>)requestData;
		Object destValue        = this.getValueBean(entity, null);
		
		if(!(destValue instanceof Collection)){
			throw new DependencyException("expected a collection type");
		}
		
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
	
	/**
	 * Converte os dados da requisição em um objeto do tipo {@link java.util.Map}.
	 * <p>Formado esperado:</p>
	 * <pre>
     * {
     *    "&lt;property&gt;": &lt;object&gt; | &lt;value&gt;,
     *    ..., 
     *    "elements": [
     *        { 
     *            "&lt;keyName&gt;": &lt;keyObject&gt; | &lt;keyValue&gt;,
     *            "&lt;elementName&gt;": &lt;elementObject&gt; | &lt;elementValue&gt;
     *        },
     *        ...
     *    ]
     * }
	 * </pre>
	 * @param entity Mapeamento da entidade.
	 * @param k Mapeamento da chave.
	 * @param requestData Dados da requisição.
	 * @throws IllegalAccessException Lançada se ocorrer uma falha ao tentar criar a instância do {@link java.util.Map}.
	 * @throws IllegalArgumentException Lançada se ocorrer uma falha ao tentar criar a instância do {@link java.util.Map}.
	 * @throws InvocationTargetException Lançada se ocorrer uma falha ao tentar criar a instância do {@link java.util.Map}.
	 * @throws DependencyException Lançada se os dados da solicitação não forem o esperado.
	 * @return Instância da classe {@link java.util.Map}.
	 */
	@SuppressWarnings("unchecked")
	private Object getValueMapObject(MapBean entity, Key k, Object requestData) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Element e               = (Element)entity.getCollection();
		Object destValue        = this.getValueBean(entity, requestData);
		Object originValue      = this.getScopedValue("elements", e.getScopeType(), requestData);
		
		if(destValue == null){
			return null;
		}
		
		if(originValue == null){
			return destValue;
		}
		
		if(!(destValue instanceof Map)){
			throw new DependencyException("expected a map type");
		}

		if(this.isArray(originValue)){
			String format = 
					  " [\r\n"
					+ "     { \r\n"
					+ "         \"<keyName>\": <keyObject | keyValue>, \r\n"
					+ "         \"<elementName>\": <elementObject | elementValue> \r\n"
					+ "     }, \r\n"
					+ "     ...\r\n"
					+ " ]";
			throw new DependencyException("expected: " + format);
		}
		
		Collection<Object> originElements = (Collection<Object>)originValue;
		Map<Object,Object> destElements   = (Map<Object,Object>)destValue;
		
		for(Object o: originElements){
			
			if(!(o instanceof Map)){
				throw new DependencyException("expected a object");
			}
			
			Map<String, Object> object = (Map<String, Object>)o;
			
			Object keyData = this.getScopedValue(k.getParameterName(), k.getScopeType(), object);
			Object key   = this.getValue(k, null, keyData);
			
			Object elementData = this.getScopedValue(e.getParameterName(), e.getScopeType(), object);
			Object value       = this.getValue(e, null, elementData);
			
			destElements.put(key, value);
		}
		
		return destValue;
	}

	/**
	 * Converte os dados da requisição em um objeto do tipo {@link java.util.Map}.
	 * <p>Formado esperado:</p>
	 * <pre>
     * {
     *    "&lt;property&gt;": &lt;object&gt; | &lt;value&gt;,
     *    ..., 
     * }
	 * </pre>
	 * @param entity Mapeamento da entidade.
	 * @param k Mapeamento da chave.
	 * @param requestData Dados da requisição.
	 * @throws IllegalAccessException Lançada se ocorrer uma falha ao tentar criar a instância do {@link java.util.Map}.
	 * @throws IllegalArgumentException Lançada se ocorrer uma falha ao tentar criar a instância do {@link java.util.Map}.
	 * @throws InvocationTargetException Lançada se ocorrer uma falha ao tentar criar a instância do {@link java.util.Map}.
	 * @throws DependencyException Lançada se os dados da solicitação não forem o esperado.
	 * @return Instância da classe {@link java.util.Map}.
	 */	
	@SuppressWarnings("unchecked")
	public Object getValueMapSimple(MapBean entity, Key k, Object requestData) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		if(requestData == null){
			return null;
		}
		
		if(!this.isObject(requestData)){
			String format = 
					  "{ \r\n"
					+ "    \"<key>\": <object | value>, \r\n"
					+ "    ...\r\n"
					+ "}";
			throw new DependencyException("expected: " + format);
		}
		
		Map<String, Object> mapRequestdata = (Map<String, Object>)requestData;
		Object destValue                   = this.getValueBean(entity, null);
		Map<Object,Object> destElements    = (Map<Object, Object>)destValue;
		Element e                          = (Element)entity.getCollection();
		
		for(String oKey: mapRequestdata.keySet()){
			
			Object key   = k.getType().convert(oKey);
			
			Object v     = this.getScopedValue(oKey, e.getScopeType(), mapRequestdata);
			Object value = this.getValue(e, null, v);
			
			destElements.put(key, value);
		}
		
		return destValue;
	}
	
	/* constructor */
	
	private Object getInstance(ConstructorBean constructor, Object data){
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
			Object data) throws InstantiationException, 
			IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		Constructor<?> insCons = constructor.getContructor();
		Object[] args          = this.getArgs(constructor, data);
		
		if(args == null){
			return null;
		}
		
		return insCons.newInstance(args);
	}

	private Object getInstanceByFactory(ConstructorBean constructor, Object data) 
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
	
	private Object[] getArgs(ConstructorBean constructor, Object data) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{

		List<ConstructorArgBean> argsList = constructor.getConstructorArgs();
		
		Object[] args = new Object[argsList.size()];
		int i         = 0;
		boolean exist = argsList.size() < 1;
		
		for(ConstructorArgBean arg: constructor.getConstructorArgs()){
			Object argData = this.getScopedValue(arg.getParameterName(), arg.getScopeType(), data); 
			args[i]        = this.getValue(arg, null, argData);
			
			if(!exist){
				exist = exist || args[i] != null || arg.isNullable();
			}
			
			i++;
		}
		
		return exist? args : null;
	}
	
	/* util */
	
	@SuppressWarnings("unchecked")
	public Object getScopedValue(String name, ScopeType scopeType, Object params){
		if(params != null && scopeType.toString().equals("param")){
			
			Map<String, Object> paramsMap = (Map<String, Object>) params;
			Object param = paramsMap.get(name);
			
			if(param != null){
				return param;
			}
		}
		
		Scope scope = Scopes.getCurrentScope(scopeType);
		return scope.get(name);
	}

	@SuppressWarnings("unchecked")
	public Object getScopedCollection(String name, ScopeType scopeType, Object params){
		if(params != null && scopeType.toString().equals("param")){
			
			Map<String, Object> paramsMap = (Map<String, Object>) params;
			Object param = paramsMap.get(name);
			
			if(param != null){
				return param;
			}
		}
		
		Scope scope = Scopes.getCurrentScope(scopeType);
		return scope.getCollection(name);
	}

	public boolean isObject(Object requestData){
		return requestData instanceof Map;
	}
	
	public boolean isArray(Object requestData){
		return requestData instanceof Collection;
	}
	
}
