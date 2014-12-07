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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.TypeManager;
import org.brandao.brutos.validator.Validator;

/**
 *
 * @author Afonso Brandao
 */
public class Action {
    
    private Controller controller;
    
    private String id;
    
    private String name;

    private String simpleName;
    
    private List alias;
    
    private String executor;

    private List parameters;
    
    private Map throwsSafe;
    
    private Method method;
    
    private List parametersType;
    
    private String returnIn;
    
    private boolean returnRendered;
    
    private String view;
    
    private boolean resolvedView;
    
    private Type returnType;

    private Class returnClass;

    private boolean redirect;

    private DispatcherType dispatcherType;

    private Validator parametersValidator;

    private Validator resultValidator;
    
    public Action() {
        this.parameters = new ArrayList();
        this.parametersType = new ArrayList();
        this.throwsSafe = new HashMap();
        this.dispatcherType = DispatcherType.INCLUDE;
        this.returnClass = void.class;
        this.redirect = false;
        this.alias = new ArrayList();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.id = 
                this.controller.getId() == null? 
                    name : 
                    this.controller.getId() + name;
    }

    public void addParameter( ParameterAction value ){
        parameters.add(value);
    }
    
    public ParameterAction getParameter(int index){
        return (ParameterAction)parameters.get(index);
    }

    public void removeParameter(int index){
        parameters.remove(index);
    }
    
    public List getParameters() {
        return parameters;
    }

    public void setParameters(List parameters) {
        this.parameters = parameters;
    }

    public ThrowableSafeData getThrowsSafe( Class thr ) {
        return (ThrowableSafeData) (
                throwsSafe.containsKey(thr) ?
                    throwsSafe.get(thr) :
                    controller.getThrowsSafe(thr));
    }

    public void setThrowsSafe(ThrowableSafeData thr) {
        this.throwsSafe.put( thr.getTarget() , thr);
    }
    
    public int getParamterSize(){
        return this.parameters.size();
    }

    public Class getParameterType( int index ){
        return (Class) this.parametersType.get( index );
    }

    public java.lang.reflect.Type getGenericParameterType( int index ){
        return method.getGenericParameterTypes()[index];
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public List getParametersType() {
        return parametersType;
    }

    public void setParametersType(List parametersType) {
        this.parametersType = parametersType;
    }

    public String getReturnIn() {
        return returnIn == null? BrutosConstants.DEFAULT_RETURN_NAME : returnIn;
    }

    public void setReturnIn(String returnIn) {
        this.returnIn = returnIn;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        view = view == null || view.trim().length() == 0 ? null : view;        
        this.view = view;
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

    public Class getReturnClass() {
        return returnClass;
    }

    public void setReturnClass(Class returnClass) {
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

    public synchronized void flush(){
        try{
            if( this.executor == null ){
                return;
            }
            
            method = getMethod( executor, controller.getClassType() );
            controller.addReserveMethod(method, this);
            setParametersType( Arrays.asList( method.getParameterTypes() ) );

            Class returnClassType = method.getReturnType();

            if( returnClassType != void.class ){
                setReturnType( 
                        ((ConfigurableApplicationContext)this.controller.getContext())
                                .getTypeManager().getType( 
                                    returnClassType ) );
            }

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

    private Method getMethod( String name, Class clazz ){
        int size = parameters.size();
        Class[] classArgs = new Class[ size ];
        for( int i=0;i<size;i++ ){
            ParameterAction arg = (ParameterAction) parameters.get(i);
            classArgs[ i ] = arg.getClassType();
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
                        
                        ParameterAction arg = 
                                (ParameterAction) parameters.get(k);
                        Type type = arg.getType();
                        //Bean mapping = arg.getMapping();
                        
                        if( type == null/* && mapping == null */){
                            arg.
                                setType(
                                    ((ConfigurableApplicationContext)this.controller.getContext())
                                            .getTypeManager().getType(
                                    params[k]));
                        }
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
                        !ClassUtil.getWrapper( params[i] )
                            .isAssignableFrom( ClassUtil.getWrapper( classArgs[i] ) ) )
                    return false;
            }
            return true;
        }
        else
            return false;

    }

    public Class[] getParameterClass(){
        int length = this.parameters.size();
        Class[] result = new Class[length];

        for( int i=0;i<length;i++ ){
            ParameterAction p = 
                    (ParameterAction) this.parameters.get(i);
            result[i] =  p.getClassType();
        }

        return result;
    }

    public Object[] getParameterValues(Object controllerInstance){
        int length = this.parameters.size();
        Object[] values = new Object[ length ];

        int index = 0;
        for( int i=0;i<length;i++ ){
            ParameterAction p = (ParameterAction) this.parameters.get(i);
            values[index++] = p.getValue(controllerInstance);
        }
        
        this.parametersValidator.validate(this, controllerInstance, values);
        
        return values;
    }

    public Object[] getParameterValues(Object controllerInstance, Object[] values){
        this.parametersValidator.validate(this, controllerInstance, values);
        return values;
    }
    
    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String value) {
        this.executor = value;
    }

    public Object invoke( Object source, Object[] args )
        throws IllegalAccessException, IllegalArgumentException,
                InvocationTargetException{
        
        Object result = null;
        if(this.method != null){
            
            if(this.parametersValidator != null)
                this.parametersValidator.validate(this, source, args);
            
            result = method.invoke( source , args);
            
            if(this.resultValidator != null)
                this.resultValidator.validate(this, source, result);
        }
        
        return result;
    }

    public boolean isAbstract(){
        return this.method == null;
    }

    public void addAlias(String value){
        this.alias.add(value);
    }
    
    public void removeAlias(String value){
        this.alias.remove(value);
    }

    public List getAlias(){
        return this.alias;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getId() {
        return id;
    }

    public boolean isReturnRendered() {
        return returnRendered;
    }

    public void setReturnRendered(boolean returnRendered) {
        this.returnRendered = returnRendered;
    }

    public boolean isResolvedView() {
        return resolvedView;
    }

    public void setResolvedView(boolean resolvedView) {
        this.resolvedView = resolvedView;
    }

    public Validator getParametersValidator() {
        return this.parametersValidator;
    }

    public void setParametersValidator(Validator parametersValidator) {
        this.parametersValidator = parametersValidator;
    }

    /**
     * @return the resultValidator
     */
    public Validator getResultValidator() {
        return resultValidator;
    }

    /**
     * @param resultValidator the resultValidator to set
     */
    public void setResultValidator(Validator resultValidator) {
        this.resultValidator = resultValidator;
    }

}
