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
public class ActionBuilder extends RestrictionBuilder{
    
    protected Controller controller;
    
    protected Action action;
    
    protected ValidatorFactory validatorFactory;
    
    protected ControllerBuilder controllerBuilder;
    
    protected ConfigurableApplicationContext applicationContext;
    
    public ActionBuilder( Action methodForm, 
            Controller controller, ValidatorFactory validatorFactory,
            ControllerBuilder controllerBuilder,
            ConfigurableApplicationContext applicationContext) {
        super(methodForm.getResultValidator().getConfiguration());
        this.controller = controller;
        this.action = methodForm;
        this.validatorFactory = validatorFactory;
        this.controllerBuilder = controllerBuilder;
        this.applicationContext = applicationContext;
    }

    public void addAlias(String value){
        value = StringUtil.adjust(value);
        
        if( StringUtil.isEmpty(value) )
            throw new BrutosException("invalid alias");
        
        this.controller.addAction(value, action);
    }

    public void removeAlias(String value){
        value = StringUtil.adjust(value);
        
        if( StringUtil.isEmpty(value) )
            throw new BrutosException("invalid alias");
        
        this.controller.removeAction(value);
    }
    

    public ParametersBuilder buildParameters(){
        return 
            new ParametersBuilder(controller, 
                    action, validatorFactory, controllerBuilder, 
                    applicationContext);
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
        return addThrowable( target, null, 
                !"true".equals(applicationContext.getConfiguration()
                .getProperty(BrutosConstants.VIEW_RESOLVER_AUTO)),
                id, DispatcherType.FORWARD );
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
    public ActionBuilder addThrowable( Class target, String view, 
            boolean resolvedView, String id, DispatcherType dispatcher ){
        return this.addThrowable(target, view, id, dispatcher, resolvedView);
    }
    
    /**
     * Intercepta e atribui uma identificaçaoo a uma determinada exceção. O
     * objeto resultante da exceção pode ser usando na visão.
     *
     * @param target Exceção alvo.
     * @param view Visão. Se omitido, será usada a visão da ação.
     * @param id Identificação.
     * @param dispatcher Modo como será direcionado o fluxo para a visão.
     * @param resolvedView Define se a vista informada é real ou não. 
     * Se verdadeiro a vista informada é real, caso contrário ela será resolvida.
     * @return Contrutor da ação.
     */
    public ActionBuilder addThrowable( Class target, String view, 
            String id, DispatcherType dispatcher, boolean resolvedView ){
        view = StringUtil.adjust(view);
        
        String originalView = view;
        
        view = 
            resolvedView? 
                view : 
                applicationContext.
                        getViewResolver()
                        .getView(
                                this.controllerBuilder, 
                                this, 
                                target, 
                                view);
        
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
        thr.setOriginalView(originalView);
        thr.setResolvedView(resolvedView);
        thr.setRedirect(false);
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
