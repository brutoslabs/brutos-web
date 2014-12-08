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
import org.brandao.brutos.mapping.ParameterAction;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.TypeManager;
import org.brandao.brutos.type.TypeUtil;
import org.brandao.brutos.type.UnknownTypeException;

/**
 *
 * @author Cliente
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
     * Configura um novo par�metro.
     *
     * @param name Identifica��o do par�metro.
     * @param scope Escopo.
     * @param enumProperty Usado na configura��o de par�metros do tipo enum.
     * @param classType Tipo do par�metro.
     * @return Contrutor do par�metro.
     */
    public ParameterBuilder addParameter( String name, ScopeType scope, EnumerationType enumProperty, Class classType ){
        return addParameter( name, scope, enumProperty, null, null, null, 
                null, false, classType );
    }

    /**
     * Configura um novo parâmetro que não possui valor.
     *
     * @return Contrutor do parâmetro.
     */
    public ParameterBuilder addNullParameter(){
        return addParameter( null, null, null, null, null, null,
                null, false, null );
    }
    
    /**
     * Configura um novo par�metro.
     *
     * @param name Identifica��o do par�metro.
     * @param scope Escopo.
     * @param temporalProperty Usado na configura��o de datas.
     * @param classType Tipo do par�metro.
     * @return Contrutor do par�metro.
     */
    public ParameterBuilder addParameter( String name, ScopeType scope, String temporalProperty, Class classType ){
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
    public ParameterBuilder addParameter( String name, EnumerationType enumProperty, Class classType ){
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
    public ParameterBuilder addParameter( String name, ScopeType scope, Class classType ){
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
    public ParameterBuilder addParameter( String name, String temporalProperty, Class classType ){
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
    public ParameterBuilder addParameterMapping( String mapping, Class classType ){
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
    public ParameterBuilder addParameterMapping( String name, String mapping, Class classType ){
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
    public ParameterBuilder addParameterMapping( String name, String mapping, ScopeType scope, Class classType ){
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
    public ParameterBuilder addParameter( String name, Class classType ){
        return addParameter( name, ScopeType.PARAM, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, null, null, false, classType );
    }

    /**
     * Constr�i um novo par�metro.
     *
     * @param classType Tipo do par�metro.
     * @return Contrutor do par�metro.
     */

    public BeanBuilder buildParameter( Class classType ){
        String beanName = 
                this.action.getName()+"#"+this.action.getParamterSize();
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

    public BeanBuilder buildParameter( String name, Class classType ){
        String beanName = 
                this.action.getName()+"#"+this.action.getParamterSize();
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

    public BeanBuilder buildParameter( Class classType, Class beanType ){
        String beanName =
                this.action.getName()+"#"+this.action.getParamterSize();
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

    public BeanBuilder buildParameter( String name, Class classType, Class beanType ){
        String beanName =
                this.action.getName()+"#"+this.action.getParamterSize();
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
    public ParameterBuilder addStaticParameter( Class classType, Object value ){
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
            boolean nullable, Class classType ){
        return addParameter( name, scope, enumProperty, temporalProperty, 
                mapping, typeDef, value, nullable, (Object)classType );
    }
    
    public ParameterBuilder addParameter( String name, ScopeType scope, EnumerationType enumProperty,
            String temporalProperty, String mapping, Type typeDef, Object value,
            boolean nullable, Object classType ){

        name = StringUtil.adjust(name);
        temporalProperty = StringUtil.adjust(temporalProperty);
        mapping = StringUtil.adjust(mapping);
        Class rawType = classType == null? null : TypeUtil.getRawType(classType);
        
        Configuration validatorConfig = new Configuration();
        
        ParameterAction parameter = new ParameterAction(this.action);

        parameter.setName( name );
        parameter.setScopeType( scope );
        parameter.setValidate( this.validatorFactory.getValidator(validatorConfig) );
        parameter.setStaticValue(value);
        parameter.setNullable(nullable);
        
        if( !StringUtil.isEmpty(mapping) ){
            if( controller.getBean(mapping) != null )
                parameter.setMapping( controller.getBean( mapping ) );
            else
                throw new BrutosException( "mapping name " + mapping + " not found!" );
                
        }
        
        if( typeDef != null ){
            if(classType != null){
                if(!typeDef.getClassType().isAssignableFrom(rawType)){
                    throw new IllegalArgumentException(
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
            
            parameter.setType( typeDef );
        }
        else
        if(rawType != null && mapping == null){
            try{
                parameter.setType( 
                        this.applicationContext.getTypeManager()
                            .getType( 
                                classType, 
                                enumProperty, 
                                temporalProperty ));
            }
            catch( UnknownTypeException e ){
                throw new UnknownTypeException( 
                        String.format( "%s.%s(...) index %d : %s" ,
                            new Object[]{
                                this.controller.getClassType().getName(),
                                action.getExecutor(),
                                new Integer(action.getParamterSize()),
                                e.getMessage()} ), e );
            }
            
            if( parameter.getType() == null )
                throw new UnknownTypeException( rawType.getName() );
        }

        action.addParameter( parameter );
        return new ParameterBuilder( parameter );
    }

    public int getParametersSize(){
        return this.action.getParamterSize();
    }
    
    public ParameterBuilder getParameter(int index){
        ParameterAction param = this.action.getParameter(index);
        return new ParameterBuilder(param);
    }
    
}
