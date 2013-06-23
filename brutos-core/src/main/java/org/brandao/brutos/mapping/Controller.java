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

package org.brandao.brutos.mapping;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import org.brandao.brutos.*;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.interceptor.InterceptorProcess;
import org.brandao.brutos.ioc.IOCProvider;
import org.brandao.brutos.scope.Scope;

/**
 * Define um objeto que representa um controlador dentro da aplicação.
 * 
 * @author Brandao
 */
public class Controller {

    private String name;
    
    private String id;
    
    private Class classType;
    
    private String actionId;
    
    private Map mappingBeans;
    
    private List fields;
    
    private Map actions;

    private Map reverseMethods;
    
    private ActionListener actionListener;

    private Map throwsSafe;

    private List alias;

    /**
     * @deprecated 
     */
    private ScopeType scope;
    
    private String view;

    private boolean redirect;
    
    private String defaultAction;
    
    private List interceptorStack;
    
    private InterceptorProcess interceptorProcess;

    private DispatcherType dispatcherType;

    private List defaultInterceptorList;

    private boolean loaded;

    private ActionType actionType;
    
    /**
     * Cria um novo controlador.
     */
    public Controller() {
        this.fields = new ArrayList();
        this.mappingBeans = new LinkedHashMap();
        this.actions = new LinkedHashMap();
        this.interceptorStack = new ArrayList();
        this.alias = new ArrayList();
        this.reverseMethods = new LinkedHashMap();
        this.throwsSafe = new LinkedHashMap();
        this.interceptorProcess = new InterceptorProcess();
        this.loaded = false;
        this.scope = ScopeType.PARAM;
        this.redirect = false;
        this.interceptorProcess.setForm( this );
        this.actionType = ActionType.PARAMETER;
    }

    /**
     * Obtém o parâmetro que identifica a ação.
     * @return Nome do parâmetro.
     */
    public String getActionId() {
        return actionId;
    }

    /**
     * Obtém a instância do controlador.
     * 
     * @param iocProvider Contêiner IoC.
     * @return Instância do controlador.
     */
    public Object getInstance(IOCProvider iocProvider){
        Object instance = name == null? null : iocProvider.getBean(name);
        instance = instance == null? iocProvider.getBean(classType) : instance;

        if( instance == null )
            throw new BrutosException("can't get instance " + name + ":" + classType);
        else
            return instance;
    }

    /**
     * Define o parâmetro que identifica a ação.
     * @param actionId Nome do parâmetro.
     */
    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    /**
     * Obtém o mapeamento de um bean a partir de seu nome.
     * @param name Nome do bean.
     * @return Bean.
     */
    public Bean getBean(String name){
        return (Bean)mappingBeans.get(name);
    }
    
    public void addBean(String name, Bean bean){
        mappingBeans.put(name, bean);
    }
    /*
    public Map getMappingBeans() {
        return mappingBeans;
    }

    public void setMappingBeans(Map mappingBeans) {
        this.mappingBeans = mappingBeans;
    }
    */
    
    public boolean containsProperty(String name){
        return getProperty(name) != null;
    }
    
    public PropertyController getProperty(String name){
        
        for(int i=0;i<fields.size();i++){
            if( ((PropertyController)fields.get(i)).getName().equals(name) )
                return (PropertyController)fields.get(i);
        }
            
        return null;
    }

    public List getProperties() {
        return fields;
    }
    
    public void addProperty(PropertyController property){
        if(!containsProperty(property.getName()))
            fields.add(property);
    }
    
    /*
    public List getFields() {
        return fields;
    }

    public void setFields(List fields) {
        this.fields = fields;
    }
    */
    
    /**
     * @deprecated 
     * @return 
     */
    public ActionListener getAcion() {
        return getActionListener();
    }

    /**
     * @deprecated 
     */
    public void setAcion(ActionListener acion) {
        this.setActionListener(acion);
    }

    public Class getClassType() {
        return classType;
    }

    public void setClassType(Class classType) {
        this.classType = classType;
    }
    
    public Action getAction(String id) {
        return (Action)actions.get(id);
    }
    
    public Map getActions() {
        return actions;
    }
    
    public void addAction( String id, Action method ){
        this.loaded = false;
        this.actions.put(id, method);
    }

    Map getReverseMethods(){
        return reverseMethods;
    }

    void addReserveMethod( Method method, Action action ){
        
        ReverseActionKey key = new ReverseActionKey(method);

        List list = (List)reverseMethods.get(key);

        if( list == null ){
            list = new LinkedList();
            reverseMethods.put(key, list);
        }

        list.add( action );
    }

    public Action getMethod( Method method ){

        if( !this.loaded )
            loadConfiguration();

        ReverseActionKey key = new ReverseActionKey(method);

        List list = (List)reverseMethods.get(key);

        if(list == null || list.size() > 1)
            throw new
                BrutosException(
                    String.format("Ambiguous reference to action: %s",
                    new Object[]{method.getName()}));
        
        return (Action) list.get(0);
    }
    
    public void setMethods(Map methods) {
        this.actions = methods;
    }
    
    public void addInterceptor( Interceptor[] interceptor ){
        getInterceptorStack().addAll( Arrays.asList( interceptor ) );
    }
    
    public List getInterceptors(){
        return getInterceptorStack();
    }

    /**
     * @deprecated 
     * @return
     */
    public Object getInstance(){
        try{
            return getClassType().newInstance();
        }
        catch( Exception e ){
            throw new InvokeException( e );
        }
    }

    public Action getActionByName( String name ){
        Action mf = null;
        mf = (Action) (name == null ? null : actions.get(name));
        mf = (Action) (mf == null ? actions.get(getDefaultAction()) : mf);
        return mf;
    }

    public void proccessBrutosAction( InterceptorHandler handler ){

        if( !this.loaded )
            loadConfiguration();

        interceptorProcess.process( handler );
    }

    private synchronized void loadConfiguration(){
        if( !this.loaded ){
            Iterator keys = actions.keySet().iterator();

            while(keys.hasNext()){
                String key = (String)keys.next();
                Action ac = (Action) actions.get(key);
                if(!ac.isLoaded())
                    ac.load();
            }
            this.loaded = true;
        }

    }
    
    public void fieldsToRequest( Object webFrame ) {
        try{
            Scopes scopes = Invoker.getCurrentApplicationContext().getScopes();
            Field[] fields = getClassType().getDeclaredFields();
            Scope scope = scopes.get(ScopeType.REQUEST);

            //for( Field f: fields ){
            for(int i=0;i<fields.length;i++){
                Field f = fields[i];
                f.setAccessible( true );
                scope.put( f.getName(), f.get( webFrame ) );
            }
        }
        catch( Exception e ){
            
        }
    }
    
    public ThrowableSafeData getThrowsSafe( Class thr ) {
        return (ThrowableSafeData) throwsSafe.get(thr);
    }

    public void removeThrowsSafe(Class thr) {
        this.throwsSafe.remove( thr );
    }

    public void setThrowsSafe(ThrowableSafeData thr) {
        this.throwsSafe.put( thr.getTarget() , thr);
    }

    public void addAlias( String alias ){
        this.alias.add( alias );
    }

    public List getAlias(){
        return this.alias;
    }

    /**
     * @deprecated
     * @return .
     */
    public ScopeType getScope() {
        return scope;
    }

    /**
     * @deprecated 
     * @param scope
     */
    public void setScope(ScopeType scope) {
        this.scope = scope;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        view = view == null || view.trim().length() == 0 ? null : view;        
        this.view = view;
    }

    public String getDefaultAction() {
        return defaultAction;
    }

    public void setDefaultAction(String defaultAction) {
        this.defaultAction = defaultAction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ActionListener getActionListener() {
        return actionListener;
    }

    public void setActionListener(ActionListener action) {
        this.actionListener = action;
    }

    public List getInterceptorStack() {
        return interceptorStack;
    }

    public void setInterceptorStack(List interceptorStack) {
        this.interceptorStack = interceptorStack;
    }

    public InterceptorProcess getInterceptorProcess() {
        return interceptorProcess;
    }

    public void setInterceptorProcess(InterceptorProcess interceptorProcess) {
        this.interceptorProcess = interceptorProcess;
    }

    public boolean isRedirect() {
        return redirect;
    }

    public void setRedirect(boolean redirect) {
        this.redirect = redirect;
    }

    public DispatcherType getDispatcherType() {
        return dispatcherType;
    }

    public void setDispatcherType(DispatcherType dispatcherType) {
        this.dispatcherType = dispatcherType;
    }

    public List getDefaultInterceptorList() {
        return defaultInterceptorList;
    }

    public void setDefaultInterceptorList(List defaultInterceptorList) {
        this.defaultInterceptorList = defaultInterceptorList;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

}
