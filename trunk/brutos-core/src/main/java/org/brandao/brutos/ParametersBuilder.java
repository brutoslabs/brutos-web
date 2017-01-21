/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2012 Afonso Brandao. (afonso.rbn@gmail.com)
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

package org.brandao.brutos;

import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.MetaBean;
import org.brandao.brutos.mapping.ParameterAction;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.type.ObjectType;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.TypeUtil;
import org.brandao.brutos.type.UnknownTypeException;

/**
 * Monta os parâmetros de uma ação.
 * 
 * @author Brandao
 */
public class ParametersBuilder extends RestrictionBuilder{
    
    private Controller controller;
    
    private Action action;
    
    private ValidatorFactory validatorFactory;
    
    private ControllerBuilder controllerBuilder;
    
    private ConfigurableApplicationContext applicationContext;

    public ParametersBuilder(Controller controller, Action action, 
            ValidatorFactory validatorFactory, ControllerBuilder controllerBuilder, 
            ConfigurableApplicationContext applicationContext) {
        super(action.getParametersValidator().getConfiguration());
        this.controller = controller;
        this.action = action;
        this.validatorFactory = validatorFactory;
        this.controllerBuilder = controllerBuilder;
        this.applicationContext = applicationContext;
    }
    
    /**
     * Adiciona um novo parâmetro.
     *
     * @param name Nome do parâmetro.
     * @param scope Escopo.
     * @param enumProperty Tipo de mapeamento de {@link java.lang.Enum}.
     * @param classType Tipo do parâmetro.
     * @return Construtor do parâmetro.
     */
    public ParameterBuilder addParameter( String name, ScopeType scope, EnumerationType enumProperty, Class<?> classType ){
        return addParameter( name, scope, enumProperty, null, null, null, 
                null, false, classType );
    }

    /**
     * Adiciona um novo parâmetro que recebe somente valores nulos.
     *
     * @return Contrutor do parâmetro.
     */
    public ParameterBuilder addNullParameter(){
        return addParameter( null, null, null, null, null, null,
                null, false, null );
    }

    /**
     * Adiciona um novo parâmetro.
     * 
     * @param name Nome do parâmetro.
     * @param scope Escopo.
     * @param temporalProperty Padrão de mapeamento de tipos {@link java.util.Calendar} e {@link java.util.Date} 
     * @param classType
     * @return
     */
    public ParameterBuilder addParameter( String name, ScopeType scope, String temporalProperty, Class<?> classType ){
        return addParameter( name, scope, EnumerationType.ORDINAL, 
                temporalProperty, null, null, null, false, classType );
    }
    
    /**
     * Configura um novo par�metro.
     *
     * @param name Identifica��o do par�metro.
     * @param scope Escopo.
     * @param type Faz o processamento do par�metro.
     * @return Contrutor do par�metro.
     */
    public ParameterBuilder addParameter( String name, ScopeType scope, Type typeDef ){
        return addParameter( name, scope, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, typeDef, null, false, typeDef.getClassType() );
    }
    
    /**
     * Configura um novo par�metro.
     *
     * @param name Identifica��o do par�metro.
     * @param enumProperty Usado na configura��o de par�metros do tipo enum.
     * @param classType Tipo do par�metro.
     * @return Contrutor do par�metro.
     */
    public ParameterBuilder addParameter( String name, EnumerationType enumProperty, Class<?> classType ){
        return addParameter( name, ScopeType.PARAM, enumProperty, null, null,
                null, null, false, classType );
    }
    
    /**
     * Configura um novo par�metro.
     *
     * @param name Identifica��o do par�metro.
     * @param scope Escopo.
     * @param classType Tipo do par�metro.
     * @return Contrutor do par�metro.
     */
    public ParameterBuilder addParameter( String name, ScopeType scope, Class<?> classType ){
        return addParameter( name, scope, EnumerationType.ORDINAL, "dd/MM/yyyy", 
                null, null, null, false, classType );
    }
    
    /**
     * Configura um novo par�metro.
     *
     * @param name Identifica��o do par�metro.
     * @param temporalProperty Usado na configura��o de datas.
     * @param classType Tipo do par�metro.
     * @return Contrutor do par�metro.
     */
    public ParameterBuilder addParameter( String name, String temporalProperty, Class<?> classType ){
        return addParameter( name, ScopeType.PARAM, EnumerationType.ORDINAL,
                temporalProperty, null, null, null, false, classType );
    }
    
    /**
     * Configura um novo par�metro.
     *
     * @param name Identifica��o do par�metro.
     * @param type Faz o processamento do par�metro.
     * @return Contrutor do par�metro.
     */
    public ParameterBuilder addParameter( String name, Type typeDef ){
        return addParameter( name, ScopeType.PARAM, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, typeDef, null, false, typeDef.getClassType() );
    }
    
    /**
     * Configura um novo par�metro.
     *
     * @param mapping Nome do mapeamento do par�metro. Esse mapeamento
     * deve ser previamente criado com o m�todo buildMappingBean(...).
     * @param classType Tipo do par�metro.
     * @return Contrutor do par�metro.
     */
    public ParameterBuilder addParameterMapping( String mapping, Class<?> classType ){
        return addParameter( null, ScopeType.PARAM, EnumerationType.ORDINAL, "dd/MM/yyyy",
                mapping, null, null, false, classType );
    }

    /**
     * Configura um novo par�metro.
     *
     * @param name Identifica��o do par�metro.
     * @param mapping Nome do mapeamento do par�metro. Esse mapeamento
     * deve ser previamente criado com o m�todo buildMappingBean(...).
     * @param classType Tipo do par�metro.
     * @return Contrutor do par�metro.
     */
    public ParameterBuilder addParameterMapping( String name, String mapping, Class<?> classType ){
        return addParameter( name, ScopeType.PARAM, EnumerationType.ORDINAL, "dd/MM/yyyy",
                mapping, null, null, false, classType );
    }
    
    /**
     * Configura um novo par�metro.
     *
     * @param name Identifica��o do par�metro.
     * @param scope Escopo.
     * @param mapping Nome do mapeamento do par�metro. Esse mapeamento
     * deve ser previamente criado com o m�todo buildMappingBean(...).
     * @param classType Tipo do par�metro.
     * @return Contrutor do par�metro.
     */
    public ParameterBuilder addParameterMapping( String name, String mapping, ScopeType scope, Class<?> classType ){
        return addParameter( name, scope, EnumerationType.ORDINAL, "dd/MM/yyyy",
                mapping, null, null, false, classType );
    }
    /**
     * Configura um novo par�metro.
     *
     * @param name Identifica��o do par�metro.
     * @param classType Tipo do par�metro.
     * @return Contrutor do par�metro.
     */
    public ParameterBuilder addParameter( String name, Class<?> classType ){
        return addParameter( name, ScopeType.PARAM, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, null, null, false, classType );
    }

    /**
     * Constr�i um novo par�metro.
     *
     * @param classType Tipo do par�metro.
     * @return Contrutor do par�metro.
     */

    public BeanBuilder buildParameter( Class<?> classType ){
        String beanName = 
                this.action.getCode() + "#" + this.action.getParamterSize();
        BeanBuilder bb = this.controllerBuilder
                    .buildMappingBean(beanName, null, classType);

        this.addParameterMapping(beanName, classType);
        return bb;
    }

    /**
     * Constrói um novo parâmetro.
     *
     * @param classType Tipo do par�metro.
     * @return Contrutor do par�metro.
     */

    public BeanBuilder buildParameter( String name, Class<?> classType ){
        String beanName = 
                this.action.getCode() + "#" + this.action.getParamterSize();
        BeanBuilder bb = this.controllerBuilder
                    .buildMappingBean(beanName, null, classType);

        this.addParameterMapping(name, beanName, classType);
        return bb;
    }
    
    /**
     * Constrói um novo parâmetro.
     *
     * @param classType Tipo do parâmetro.
     * @param beanType Tipo do bean.
     * @return Contrutor do par�metro.
     */

    public BeanBuilder buildParameter( Class<?> classType, Class<?> beanType ){
        String beanName =
                this.action.getCode()+"#"+this.action.getParamterSize();
        BeanBuilder bb = this.controllerBuilder
                    .buildMappingBean(beanName, null, beanType);

        this.addParameterMapping(beanName, classType);
        return bb;
    }

    /**
     * Constrói um novo parâmetro.
     *
     * @param classType Tipo do parâmetro.
     * @param beanType Tipo do bean.
     * @return Contrutor do par�metro.
     */

    public BeanBuilder buildParameter( String name, Class<?> classType, Class<?> beanType ){
        String beanName =
                this.action.getCode()+"#"+this.action.getParamterSize();
        BeanBuilder bb = this.controllerBuilder
                    .buildMappingBean(beanName, null, beanType);

        this.addParameterMapping(name, beanName, classType);
        return bb;
    }
    
    /**
     * Configura um novo par�metro.
     *
     * @param classType Tipo do par�metro.
     * @param value Valor do Par�metro.
     * @return Contrutor do par�metro.
     */
    public ParameterBuilder addStaticParameter( Class<?> classType, Object value ){
        return addParameter( null, ScopeType.PARAM, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, null, value, false, classType );
    }

    /**
     * Configura um novo par�metro.
     * 
     * @param name Identifica��o do par�metro.
     * @param scope Escopo.
     * @param enumProperty Usado na configura��o de par�metros do tipo enum.
     * @param mapping Nome do mapeamento do par�metro. Esse mapeamento
     * deve ser previamente criado com o m�todo buildMappingBean(...).
     * @param temporalProperty Usado na configura��o de datas.
     * @param type Faz o processamento do par�metro.
     * @param classType Tipo do par�metro.
     * @return Contrutor do par�metro.
     */
    public ParameterBuilder addParameter( String name, ScopeType scope, EnumerationType enumProperty,
            String temporalProperty, String mapping, Type typeDef, Object value,
            boolean nullable, Class<?> classType ){
        return addParameter( name, scope, enumProperty, temporalProperty, 
                mapping, typeDef, value, nullable, (Object)classType );
    }

	public ParameterBuilder addGenericParameter(String name, Class<?> classType){
		return 
			this.addParameter(name, BrutosConstants.DEFAULT_SCOPETYPE, 
				BrutosConstants.DEFAULT_ENUMERATIONTYPE,
				BrutosConstants.DEFAULT_TEMPORALPROPERTY, null, 
				null, null,
	            false, true, classType);		
	}

	public ParameterBuilder addGenericParameter(String name){
		return 
			this.addParameter(name, BrutosConstants.DEFAULT_SCOPETYPE, 
				BrutosConstants.DEFAULT_ENUMERATIONTYPE,
				BrutosConstants.DEFAULT_TEMPORALPROPERTY, null, 
				null, null,
	            false, true, null);		
	}

	public ParameterBuilder addParameter(String name, ScopeType scope, EnumerationType enumProperty,
            String temporalProperty, String mapping, Type typeDef, Object value,
            boolean nullable, Object classType){
		return this.addParameter(name, scope, enumProperty, temporalProperty, mapping, typeDef, value,
	            nullable, false, classType);
	}
	
    @SuppressWarnings("unchecked")
	public ParameterBuilder addParameter(String name, ScopeType scope, EnumerationType enumProperty,
            String temporalProperty, String mapping, Type typeDef, Object value,
            boolean nullable, boolean generic, Object classType){

        name = StringUtil.adjust(name);
        temporalProperty = StringUtil.adjust(temporalProperty);
        mapping = StringUtil.adjust(mapping);
        Class<?> rawType = TypeUtil.getRawType(classType);
        
        if(StringUtil.isEmpty(name) && (StringUtil.isEmpty(mapping) && !generic && value == null && !nullable)){
        	throw new IllegalArgumentException("bean name is required");
        }
        
        //if(StringUtil.isEmpty(name) && value == null && !nullable)
        //	throw new IllegalArgumentException("bean name is required");
        
        if(scope == null)
        	throw new MappingException("invalid scope");
        
        Configuration validatorConfig = new Configuration();
        
        ParameterAction parameter = new ParameterAction(this.action);

        parameter.setName(name);
        parameter.setScopeType(scope);
        parameter.setValidate( this.validatorFactory.getValidator(validatorConfig) );
        parameter.setStaticValue(value);
        parameter.setNullable(nullable);
        
        if(typeDef == null){
        	if(classType != null){
	            try{
	                typeDef = 
		        		this.applicationContext.getTypeManager()
		                		.getType(classType, enumProperty, temporalProperty );
	                
	            }
	            catch( UnknownTypeException e ){
	                throw new MappingException( 
	                        String.format( "%s.%s(...) index %d : %s" ,
	                            new Object[]{
	                                this.controller.getClassType().getName(),
	                                action.getExecutor(),
	                                new Integer(action.getParamterSize()),
	                                e.getMessage()} ), e );
	            }
	        }
                
            if(typeDef == null)
                typeDef = new ObjectType(rawType);
        }
        else
    	if(classType != null){
            if(!typeDef.getClassType().isAssignableFrom(rawType)){
                throw new MappingException(
                        String.format(
                            "expected %s found %s",
                            new Object[]{
                                rawType.getName(),
                                typeDef.getClassType().getName()
                            }
                        )
                );
            }
        }
        
        parameter.setType(typeDef);
        
        if(generic){
            MetaBean metaBean = new MetaBean(controller);
            metaBean.setClassType(rawType);
            parameter.setMetaBean(metaBean);
        }
        else
        if( !StringUtil.isEmpty(mapping) ){
            if( controller.getBean(mapping) != null )
                parameter.setMapping( controller.getBean( mapping ) );
            else
                throw new MappingException( "mapping name " + mapping + " not found!" );
        }
        /*else{
            Type definedType = parameter.getType();
            
            //TODO: remove ObjectType and use if mapping not null
            if(definedType != null && definedType.getClass() == ObjectType.class && rawType != Object.class)
            	throw new MappingException("unknown type: " + rawType.getSimpleName());
        }
        */

        action.addParameter(parameter);
        return new ParameterBuilder(parameter, this, this.validatorFactory);
    }

    public int getParametersSize(){
        return this.action.getParamterSize();
    }
    
    public ParameterBuilder getParameter(int index){
        ParameterAction param = this.action.getParameter(index);
        return new ParameterBuilder(param, this, this.validatorFactory);
    }
    
    public ControllerBuilder getControllerBuilder(){
        return this.controllerBuilder;
    }
    
}
