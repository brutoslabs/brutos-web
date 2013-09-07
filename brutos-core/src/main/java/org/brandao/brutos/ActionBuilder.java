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
    Action action;
    ValidatorProvider validatorProvider;
    ControllerBuilder controllerBuilder;
    ConfigurableApplicationContext applicationContext;
    
    public ActionBuilder( Action methodForm, 
            Controller controller, ValidatorProvider validatorProvider,
            ControllerBuilder controllerBuilder,
            ConfigurableApplicationContext applicationContext) {
        this.controller = controller;
        this.action = methodForm;
        this.validatorProvider = validatorProvider;
        this.controllerBuilder = controllerBuilder;
        this.applicationContext = applicationContext;
    }

    public void addAlias(String value){
        value = StringUtil.adjust(value);
        
        if( StringUtil.isEmpty(value) )
            throw new BrutosException("invalid alias");
        
        this.controller.addAction(value, action);
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
                this.action.getName()+"#"+this.action.getParamterSize();
        BeanBuilder bb = this.controllerBuilder
                    .buildMappingBean(beanName, classType);

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
                    .buildMappingBean(beanName, classType);

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
                    .buildMappingBean(beanName, beanType);

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
                    .buildMappingBean(beanName, beanType);

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
            String temporalProperty, String mapping, Type type, Object value,
            boolean nullable, Class classType ){
        return addParameter( name, scope, enumProperty, temporalProperty, 
                mapping, type, value, nullable, (Object)classType );
    }
    
    public ParameterBuilder addParameter( String name, ScopeType scope, EnumerationType enumProperty,
            String temporalProperty, String mapping, Type type, Object value,
            boolean nullable, Object classType ){

        name = StringUtil.adjust(name);
        temporalProperty = StringUtil.adjust(temporalProperty);
        mapping = StringUtil.adjust(mapping);
        Class rawType = classType == null? null : TypeManager.getRawType(classType);
        
        Configuration validatorConfig = new Configuration();
        
        UseBeanData useBean = new UseBeanData();

        useBean.setNome( name );
        useBean.setScopeType( scope );
        useBean.setValidate( validatorProvider.getValidator( validatorConfig ) );
        useBean.setStaticValue(value);
        useBean.setNullable(nullable);
        
        if( !StringUtil.isEmpty(mapping) ){
            if( controller.getBean(mapping) != null )
                useBean.setMapping( controller.getBean( mapping ) );
            else
                throw new BrutosException( "mapping name " + mapping + " not found!" );
                
        }
        
        if( type != null ){
            useBean.setType( type );
            if( rawType != null &&
                    !rawType.isAssignableFrom( useBean.getType().getClassType() ) )
                throw new BrutosException( 
                        "expected " + rawType.getName() + " found " +
                        type.getClassType().getName() );
        }
        else
        if(rawType != null && mapping == null){
            try{
                useBean.setType( 
                        TypeManager.getType( 
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
            
            if( useBean.getType() == null )
                throw new UnknownTypeException( rawType.getName() );
        }

        ParameterAction pmm = new ParameterAction();
        pmm.setBean( useBean );
        pmm.setParameterId( 0 );
        
        action.addParameter( pmm );
        return new ParameterBuilder( pmm );
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
        view = StringUtil.adjust(view);
        id = StringUtil.adjust(id);

        if( target == null )
            throw new BrutosException( "target is required: " + controller.getClassType().getName() );

        if( !Throwable.class.isAssignableFrom( target ) )
            throw new BrutosException( "target is not allowed: " +target.getName() );

        if(dispatcher == null)
            dispatcher = BrutosConstants.DEFAULT_DISPATCHERTYPE;
        
        ThrowableSafeData thr = new ThrowableSafeData();
        thr.setParameterName(id);
        thr.setTarget(target);
        thr.setView(view);
        thr.setRedirect( false );
        thr.setDispatcher(dispatcher);
        action.setThrowsSafe(thr);
        return this;
    }

    public ControllerBuilder getControllerBuilder(){
        return this.controllerBuilder;
    }
    
    public String getName(){
        return this.action.getName();
    }
    
    public ActionBuilder setView(String value){
        this.action.setView(value);
        return this;
    }
    
    public String getView(){
        return this.action.getView();
    }
    
    public ActionBuilder setName(String value){
        
        value = StringUtil.adjust(value);
        
        if(StringUtil.isEmpty(value))
            throw new BrutosException("invalid action name");
        
        this.action.setName(value);
        return this;
    }

    public ActionBuilder setDispatcherType(String value){
        value = StringUtil.adjust(value);
        
        if(StringUtil.isEmpty(value))
            throw new BrutosException("invalid dispatcher type");
        
        this.setDispatcherType(DispatcherType.valueOf(value));
        
        return this;
    }

    public ActionBuilder setDispatcherType(DispatcherType value){
        this.action.setDispatcherType(value);
        return this;
    }

    public DispatcherType getDispatcherType(){
        return this.action.getDispatcherType();
    }
    
    public ActionBuilder setExecutor(String value){
        value = StringUtil.adjust(value);
        this.action.setExecutor(value);
        return this;
    }
    
    public String getExecutor(){
        return this.action.getExecutor();
    }
    
    public ActionBuilder setResult(String value){
        value = StringUtil.adjust(value);
        this.action.setReturnIn(
                StringUtil.isEmpty(value)? 
                    BrutosConstants.DEFAULT_RETURN_NAME :
                    value);
        
        return this;
    }
    
    public String getResult(){
        return this.action.getReturnIn();
    }

    public ActionBuilder setResultRendered(boolean value){
        this.action.setReturnRendered(value);
        return this;
    }

    public boolean isResultRendered(){
        return this.action.isReturnRendered();
    }
    
    public int getParametersSize(){
        return this.action.getParamterSize();
    }
    
    public ParameterBuilder getParameter(int index){
        ParameterAction param = this.action.getParameter(index);
        return new ParameterBuilder(param);
    }
}
