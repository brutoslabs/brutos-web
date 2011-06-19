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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassType;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.Types;

/**
 *
 * @author Afonso Brandao
 */
public class MethodForm {
    
    private Controller controller;
    
    private String name;

    private String methodName;

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
        this.dispatcherType = DispatcherType.INCLUDE;
        this.returnClass = void.class;
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
            this.load();

        return parameters;
    }

    public void setParameters(List<ParameterMethodMapping> parameters) {
        load = false;
        this.parameters = parameters;
    }

    public ThrowableSafeData getThrowsSafe( Class<? extends Throwable> thr ) {
        return throwsSafe.containsKey(thr)?
                throwsSafe.get(thr) :
                controller.getThrowsSafe(thr);
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
            this.load();
        
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

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
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

    public synchronized void load(){
        try{
            
            if( load )
                return;
            
            if( this.methodName == null ){
                load = true;
                return;
            }
            
            //Class<?> classType = controller.getClassType();
            method = getMethod( methodName, controller.getClassType() );//classType.getMethod( this.methodName, this.getParameterClass() );
            controller.getReverseMethods()
                    .put(method.toString(),this);
            setParametersType( Arrays.asList( method.getParameterTypes() ) );

            Class<?> returnClassType = method.getReturnType();

            if( returnClassType != void.class )
                setReturnType( Types.getType( returnClassType ) );

            setMethod( method );
            setReturnClass( returnClassType );
            load = true;
        }
        catch( BrutosException e ){
            throw e;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }

    }

    private Method getMethod( String name, Class clazz ){
        int size = parameters.size();
        Class[] classArgs = new Class[ size ];
        for( int i=0;i<size;i++ ){
            ParameterMethodMapping arg = parameters.get(i);
            classArgs[ i ] = arg.getBean().getClassType();
        }

        Class tmpClazz = clazz;
        while( tmpClazz != Object.class ){
            Method[] methods = tmpClazz.getDeclaredMethods();
            for( int i=0;i<methods.length;i++ ){
                Method m = methods[i];
                if( m.getName().equals(name) &&
                    isCompatible( m, classArgs ) ){
                    Class[] params = m.getParameterTypes();
                    for( int k=0;k<params.length;k++ ){
                        ParameterMethodMapping arg = parameters.get(k);
                        if( arg.getBean().getType() == null )
                            arg.getBean()
                                    .setType(Types.getType(params[k]));
                    }

                    return m;
                }
            }
            tmpClazz = tmpClazz.getSuperclass();
        }
        String msg = "not found: " + clazz.getName() + "." + name + "( ";

        for( int i=0;i<classArgs.length;i++ ){
            Class arg = classArgs[i];
            msg += i != 0? ", " : "";
            msg += arg == null? "?" : arg.getName();
        }
        msg += " )";

        throw new BrutosException( msg );
    }

    private boolean isCompatible( Method m, Class[] classArgs ){
        Class[] params = m.getParameterTypes();
        if( params.length == classArgs.length ){
            for( int i=0;i<params.length;i++ ){
                if( classArgs[i] != null &&
                        !ClassType.getWrapper( params[i] )
                            .isAssignableFrom( ClassType.getWrapper( classArgs[i] ) ) )
                    return false;
            }
            return true;
        }
        else
            return false;

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

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object invoke( Object source, Object[] args )
        throws IllegalAccessException, IllegalArgumentException,
                InvocationTargetException{
        return getMethod() != null?
            method.invoke( source , args) : null;
    }

    public boolean isAbstract(){
        return this.method == null;
    }

    public boolean isLoaded(){
        return this.load;
    }
}
