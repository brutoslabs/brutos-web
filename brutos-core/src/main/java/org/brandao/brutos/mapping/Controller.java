/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * This library is free software. You can redistribute it 
 * and/or modify it under the terms of the GNU General Public
 * License (GPL) version 3.0 or (at your option) any later 
 * version.
 * You may obtain a copy of the License at
 * 
 * http://www.gnu.org/licenses/gpl.html 
 * 
 * Distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied.
 *
 */

package org.brandao.brutos.mapping;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.Scopes;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.interceptor.InterceptorProcess;
import org.brandao.brutos.ioc.IOCProvider;
import org.brandao.brutos.scope.Scope;

/**
 *
 * @author Afonso Brandao
 */
public class Controller {

    private String name;
    
    private String id;
    
    private Class classType;
    
    private String actionId;
    
    private Map mappingBeans;
    
    private List fields;
    
    private Map methods;

    private Map reverseMethods;
    
    private ActionListener action;

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

    public Controller() {
        this.fields = new ArrayList();
        this.mappingBeans = new LinkedHashMap();
        this.methods = new LinkedHashMap();
        this.interceptorStack = new ArrayList();
        this.alias = new ArrayList();
        this.reverseMethods = new LinkedHashMap();
        this.throwsSafe = new LinkedHashMap();
        this.interceptorProcess = new InterceptorProcess();
        this.loaded = false;
        this.interceptorProcess.setForm( this );
    }

    public String getActionId() {
        return actionId;
    }

    public Object getInstance(IOCProvider iocProvider){
        Object instance = name == null? null : iocProvider.getBean(name);
        instance = instance == null? iocProvider.getBean(classType) : instance;

        if( instance == null )
            throw new BrutosException("can't get instance " + name + ":" + classType);
        else
            return instance;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public Map getMappingBeans() {
        return mappingBeans;
    }

    public void setMappingBeans(Map mappingBeans) {
        this.mappingBeans = mappingBeans;
    }

    public List getFields() {
        return fields;
    }

    public void setFields(List fields) {
        this.fields = fields;
    }

    /**
     * @deprecated 
     * @return 
     */
    public ActionListener getAcion() {
        return getAction();
    }

    /**
     * @deprecated 
     */
    public void setAcion(ActionListener acion) {
        this.setAction(acion);
    }

    public Class getClassType() {
        return classType;
    }

    public void setClassType(Class classType) {
        this.classType = classType;
    }
    
    public void addMappingBean( Bean mapping ){
        if( getMappingBeans() == null )
            setMappingBeans(new HashMap());
        
        if( getMappingBeans().containsKey( mapping.getName() ) )
            throw new MappingException( "conflict mapping name: " + mapping.getName() );
        else
            getMappingBeans().put( mapping.getName(), mapping );
    }

    public Bean getMappingBean( String name ){
        return (Bean) getMappingBeans().get( name );
    }
    
    public Map getMethods() {
        return methods;
    }

    public void addMethod( String id, Action method ){

        //if( method.getMethod() != null )
        //    this.reverseMethods.put(method.getMethodName().toString(), method);
        this.loaded = false;
        this.methods.put(id, method);
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
        this.methods = methods;
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
        mf = (Action) (name == null ? null : getMethods().get(name));
        mf = (Action) (mf == null ? getMethods().get(getDefaultAction()) : mf);
        return mf;
    }

    public void proccessBrutosAction( InterceptorHandler handler ){

        if( !this.loaded )
            loadConfiguration();

        interceptorProcess.process( handler );
    }

    private synchronized void loadConfiguration(){
        if( !this.loaded ){
            Iterator keys = methods.keySet().iterator();

            while(keys.hasNext()){
                String key = (String)keys.next();
                Action ac = (Action) methods.get(key);
                if(!ac.isLoaded())
                    ac.load();
            }
            this.loaded = true;
        }

    }
    
    public void fieldsToRequest( Object webFrame ) {
        try{
            Scopes scopes = Invoker.getApplicationContext().getScopes();
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

    public ActionListener getAction() {
        return action;
    }

    public void setAction(ActionListener action) {
        this.action = action;
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

}
