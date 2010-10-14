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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.interceptor.InterceptorProcess;

/**
 *
 * @author Afonso Brandao
 */
public class Form {

    /**
     *
     */
    private String id;
    
    private String uri;
    
    private Class<?> classType;
    
    private String methodId;
    
    private Map<String, MappingBean> mappingBeans;
    
    private List<FieldForm> fields;
    
    private Map<String, MethodForm> methods;
    
    private Action action;

    private Map<Class, ThrowableSafeData> throwsSafe;

    private List<String> alias;

    private ScopeType scope;
    
    private String page;

    private boolean redirect;
    
    private String defaultMethodName;
    
    private List<Interceptor> interceptorStack;
    
    private InterceptorProcess interceptorProcess;

    private DispatcherType dispatcherType;

    public Form() {
        fields = new ArrayList();
        mappingBeans = new HashMap();
        methods = new HashMap();
        interceptorStack = new ArrayList();
        this.alias = new ArrayList<String>();
        this.throwsSafe = new HashMap<Class, ThrowableSafeData>();
        interceptorProcess = new InterceptorProcess();
        interceptorProcess.setForm( this );
    }

    public String getMethodId() {
        return methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public Map<String, MappingBean> getMappingBeans() {
        return mappingBeans;
    }

    public void setMappingBeans(Map<String, MappingBean> mappingBeans) {
        this.mappingBeans = mappingBeans;
    }

    public List<FieldForm> getFields() {
        return fields;
    }

    public void setFields(List<FieldForm> fields) {
        this.fields = fields;
    }

    public Action getAcion() {
        return getAction();
    }

    public void setAcion(Action acion) {
        this.setAction(acion);
    }

    public Class<?> getClassType() {
        return classType;
    }

    public void setClassType(Class<?> classType) {
        this.classType = classType;
    }
    
    public void addMappingBean( MappingBean mapping ){
        if( getMappingBeans() == null )
            setMappingBeans(new HashMap());
        
        if( getMappingBeans().containsKey( mapping.getName() ) )
            throw new MappingException( "conflict mapping name: " + mapping.getName() );
        else
            getMappingBeans().put( mapping.getName(), mapping );
    }

    public MappingBean getMappingBean( String name ){
        return getMappingBeans().get( name );
    }
    
    public Map<String, MethodForm> getMethods() {
        return methods;
    }

    public void setMethods(Map<String, MethodForm> methods) {
        this.methods = methods;
    }
    
    public void addInterceptor( Interceptor ... interceptor ){
        getInterceptorStack().addAll( Arrays.asList( interceptor ) );
    }
    
    public List<Interceptor> getInterceptors(){
        return getInterceptorStack();
    }

    @Deprecated
    public Object getInstance(){
        try{
            return getClassType().newInstance();
        }
        catch( Exception e ){
            throw new InvokeException( e );
        }
    }

    public MethodForm getMethodByName( String name ){
        MethodForm mf = null;
        mf = name == null? null : getMethods().get( name );
        mf = mf == null? getMethods().get( getDefaultMethodName() ) : mf;
        return mf;
    }

    public void proccessBrutosAction( InterceptorHandler handler ){
        getInterceptorProcess().process( handler );
    }
    
    public void fieldsToRequest( HttpServletRequest request, Object webFrame ) {
        try{
            Field[] fields = getClassType().getDeclaredFields();
            for( Field f: fields ){
                f.setAccessible( true );
                request.setAttribute( f.getName(), f.get( webFrame ) );
            }
        }
        catch( Exception e ){
            
        }
    }
    
    public ThrowableSafeData getThrowsSafe( Class<? extends Throwable> thr ) {
        return throwsSafe.get(thr);
    }

    public void removeThrowsSafe(Class<? extends Throwable> thr) {
        this.throwsSafe.remove( thr );
    }

    public void setThrowsSafe(ThrowableSafeData thr) {
        this.throwsSafe.put( thr.getTarget() , thr);
    }

    public void addAlias( String alias ){
        this.alias.add( alias );
    }

    public List<String> getAlias(){
        return this.alias;
    }

    public ScopeType getScope() {
        return scope;
    }

    public void setScope(ScopeType scope) {
        this.scope = scope;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getDefaultMethodName() {
        return defaultMethodName;
    }

    public void setDefaultMethodName(String defaultMethodName) {
        this.defaultMethodName = defaultMethodName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public List<Interceptor> getInterceptorStack() {
        return interceptorStack;
    }

    public void setInterceptorStack(List<Interceptor> interceptorStack) {
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

}
