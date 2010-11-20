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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.Types;

/**
 *
 * @author Afonso Brandao
 */
public class MethodForm {
    
    private Form form;
    
    private String name;
    
    private List<ParameterMethodMapping> parameters;
    
    private Map<Class<? extends Throwable>, ThrowableSafeData> throwsSafe;
    
    private Method method;
    
    private List<Class<?>> parametersType;
    
    private String returnIn;
    
    private String returnPage;
    
    private Type returnType;

    private Class<?> returnClass;

    private boolean redirect;

    private DispatcherType dispatcherType;

    private boolean load = false;

    public MethodForm() {
        this.parameters = new ArrayList();
        this.parametersType = new ArrayList();
        this.throwsSafe = new HashMap();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addParameter( ParameterMethodMapping value ){
        load = false;
        parameters.add(value);
    }
    
    public List<ParameterMethodMapping> getParameters() {

        if( !load )
            this.lazyLoad();

        return parameters;
    }

    public void setParameters(List<ParameterMethodMapping> parameters) {
        load = false;
        this.parameters = parameters;
    }

    public ThrowableSafeData getThrowsSafe( Class<? extends Throwable> thr ) {
        return throwsSafe.containsKey(thr)?
                throwsSafe.get(thr) :
                form.getThrowsSafe(thr);
    }

    public void setThrowsSafe(ThrowableSafeData thr) {
        this.throwsSafe.put( thr.getTarget() , thr);
    }
    
    public int getParamterSize(){
        return this.parameters.size();
    }

    public Class getParameterType( int index ){
        return this.parametersType.get( index );
    }

    public java.lang.reflect.Type getGenericParameterType( int index ){
        return method.getGenericParameterTypes()[index];
    }

    public Method getMethod() {

        if( !load )
            this.lazyLoad();
        
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public List<Class<?>> getParametersType() {
        return parametersType;
    }

    public void setParametersType(List<Class<?>> parametersType) {
        this.parametersType = parametersType;
    }

    public String getReturnIn() {
        return returnIn == null? BrutosConstants.DEFAULT_RETURN_NAME : returnIn;
    }

    public void setReturnIn(String returnIn) {
        this.returnIn = returnIn;
    }

    public String getReturnPage() {
        return returnPage;
    }

    public void setReturnPage(String returnPage) {
        this.returnPage = returnPage;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    public Class<?> getReturnClass() {
        return returnClass;
    }

    public void setReturnClass(Class<?> returnClass) {
        this.returnClass = returnClass;
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

    public synchronized void lazyLoad(){
        try{
            
            if( load )
                return;
            
            Class<?> classType = form.getClassType();
            method = classType.getMethod( this.name, this.getParameterClass() );
            setParametersType( Arrays.asList( method.getParameterTypes() ) );

            Class<?> returnClassType = method.getReturnType();

            if( returnClassType != void.class )
                setReturnType( Types.getType( returnClassType ) );

            setMethod( method );
            setReturnClass( returnClassType );
        }
        catch( BrutosException e ){
            throw e;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }

    }

    private Class[] getParameterClass(){
        int length = this.parameters.size();
        Class[] result = new Class[length];

        for( int i=0;i<length;i++ ){
            ParameterMethodMapping p = this.parameters.get(i);
            result[i] =  p.getBean().getClassType();
        }

        return result;
    }
}
