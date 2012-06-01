/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
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

import org.brandao.brutos.mapping.*;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.TypeManager;
import org.brandao.brutos.type.UnknownTypeException;
import org.brandao.brutos.validator.ValidatorProvider;

/**
 * Constrói uma ação. A ação pode ter ou não parâmetros. Os parâmetros podem ser
 * obtidos tanto da requisição, sessão ou do contexto. Podendo ser de tipo primitivo
 * ou não. No caso de um objeto complexo, é possével usar um mapeamento predefinido.
 * Se a ação retornar algum valor, este será processado e incluído na requisição,
 * para posteriormente ser usada na visão. As exceções lançaadas durante a execução
 * da ação podem alterar o fluxo lógico da aplicação. <p>No exemplo a seguir, depois
 * de executar a ação showPerson é exibida a visão personView.jsp e se for lançada
 * a exceção NotExistPerson a visão error.jsp será exibida</p>.
 * 
 * <pre>
 * public class MyController{
 *
 *   public void showPerson( int id ){
 *     ...
 *   }
 * }
 *
 * controllerBuilder
 *   .addAction( "show", "showPerson", "personView.jsp" )
 *   .addThrowable( NotExistPerson.class, "error.jsp", "exception", DispatcherType.FORWARD )
 *   .addParameter( "idPerson", int.class );
 *   
 * </pre>
 * 
 * @author Afonso Brandao
 */
public class ActionBuilder {
    
    Controller controller;
    Action methodForm;
    ValidatorProvider validatorProvider;
    ControllerBuilder controllerBuilder;

    public ActionBuilder( Action methodForm, 
            Controller controller, ValidatorProvider validatorProvider,
            ControllerBuilder controllerBuilder ) {
        this.controller = controller;
        this.methodForm = methodForm;
        this.validatorProvider = validatorProvider;
        this.controllerBuilder = controllerBuilder;
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
    public ParameterBuilder addParameter( String name, ScopeType scope, Type type ){
        return addParameter( name, scope, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, type, null, false, type.getClassType() );
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
    public ParameterBuilder addParameter( String name, Type type ){
        return addParameter( name, ScopeType.PARAM, EnumerationType.ORDINAL, "dd/MM/yyyy",
                null, type, null, false, type.getClassType() );
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
                this.methodForm.getName()+"#"+this.methodForm.getParamterSize();
        BeanBuilder bb = this.controllerBuilder
                    .buildMappingBean(beanName, classType);

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

    public BeanBuilder buildParameter( Class classType, Class beanType ){
        String beanName =
                this.methodForm.getName()+"#"+this.methodForm.getParamterSize();
        BeanBuilder bb = this.controllerBuilder
                    .buildMappingBean(beanName, beanType);

        this.addParameterMapping(beanName, classType);
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
            String temporalProperty, String mapping, Type type, Object value,
            boolean nullable, Class classType ){

        name = name == null || name.replace( " ", "" ).length() == 0? null : name;
        temporalProperty = temporalProperty == null || temporalProperty.replace( " ", "" ).length() == 0? null : temporalProperty;
        mapping = mapping == null || mapping.replace( " ", "" ).length() == 0? null : mapping;
        
        //if( methodForm.getParameters().size() > methodForm.getParametersType().size() )
        //    throw new BrutosException( "index > " + methodForm.getParametersType().size() );
        
        /*Class classType = methodForm.
                    getParametersType().get( methodForm.getParamterSize() );*/

        Configuration validatorConfig = new Configuration();
        
        UseBeanData useBean = new UseBeanData();

        useBean.setNome( name );
        useBean.setScopeType( scope );
        useBean.setValidate( validatorProvider.getValidator( validatorConfig ) );
        useBean.setStaticValue(value);
        useBean.setNullable(nullable);
        
        if( mapping != null ){
            if( controller.getMappingBeans().containsKey( mapping ) )
                useBean.setMapping( controller.getMappingBean( mapping ) );
            else
                throw new BrutosException( "mapping name " + mapping + " not found!" );
                
        }
        //else
        if( type != null ){
            useBean.setType( type );
            if( classType != null &&
                    !classType.isAssignableFrom( useBean.getType().getClassType() ) )
                throw new BrutosException( 
                        "expected " + classType.getName() + " found " +
                        type.getClassType().getName() );
        }
        else
        if(classType != null && mapping == null){
            try{
                /*useBean.setType( Types.getType( methodForm.getGenericParameterType( methodForm.getParamterSize() ), enumProperty, temporalProperty ) );*/
                useBean.setType( TypeManager.getType( classType, enumProperty, temporalProperty ));
            }
            catch( UnknownTypeException e ){
                throw new UnknownTypeException( 
                        String.format( "%s.%s(...) index %d : %s" ,
                            new Object[]{
                                this.controller.getClassType().getName(),
                                methodForm.getMethodName(),
                                new Integer(methodForm.getParamterSize()),
                                e.getMessage()} ), e );
            }
            
            if( useBean.getType() == null )
                throw new UnknownTypeException( classType.getName() );
        }

        ParameterMethodMapping pmm = new ParameterMethodMapping();
        pmm.setBean( useBean );
        pmm.setParameterId( 0 );
        
        methodForm.addParameter( pmm );
        return new ParameterBuilder( validatorConfig );
    }

    /**
     * Intercepta e atribui uma identificaçao a uma determinada exceçao. O
     * objeto resultante da exceção pode ser usando na visão.
     *
     * @param target Exceçao alvo.
     * @param id Identificação.
     * @return Contrutor da ação.
     */
    public ActionBuilder addThrowable( Class target, String id ){
        return addThrowable( target, null, id, DispatcherType.FORWARD );
    }

    /**
     * Intercepta e atribui uma identificaçaoo a uma determinada exceção. O
     * objeto resultante da exceção pode ser usando na visão.
     *
     * @param target Exceção alvo.
     * @param view Visão. Se omitido, será usada a visão da ação.
     * @param id Identificação.
     * @param dispatcher Modo como será direcionado o fluxo para a visão.
     * @return Contrutor da ação.
     */
    public ActionBuilder addThrowable( Class target, String view, String id, DispatcherType dispatcher ){
        view = view == null || view.replace( " ", "" ).length() == 0? null : view;
        id = id == null || id.replace( " ", "" ).length() == 0? null : id;

        if( target == null )
            throw new BrutosException( "target is required: " + controller.getClassType().getName() );

        if( !Throwable.class.isAssignableFrom( target ) )
            throw new BrutosException( "target is not allowed: " +target.getName() );

        ThrowableSafeData thr = new ThrowableSafeData();
        thr.setParameterName(id);
        thr.setTarget(target);
        thr.setUri(view);
        thr.setRedirect( false );
        thr.setDispatcher(dispatcher);
        methodForm.setThrowsSafe(thr);
        return this;
    }

    public ControllerBuilder getControllerBuilder(){
        return this.controllerBuilder;
    }
    
    public String getName(){
        return this.methodForm.getName();
    }
    
    public ActionBuilder setView(String value){
        this.methodForm.setView(value);
        return this;
    }
}
