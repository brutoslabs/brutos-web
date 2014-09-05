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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.validator.ValidatorException;

/**
 *
 * @author Afonso Brandao
 */
public class Bean {

    private static final Logger logger = LoggerProvider
            .getCurrentLoggerProvider().getLogger(Bean.class);
    
    private Controller controller;

    private String name;
    
    private Class classType;
    
    private Map fields;

    private boolean hierarchy;

    private String separator;

    private ConstructorBean constructor;

    private String factory;

    private String indexFormat;

    public Bean( Controller controller ) {
        this.fields = new HashMap();
        this.controller = controller;
        this.hierarchy = true;
        this.separator = ".";
        this.indexFormat = "[$index]";
        this.constructor = new ConstructorBean(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getClassType() {
        return classType;
    }

    public void setClassType(Class classType) {
        this.classType = classType;
    }

    public Map getFields() {
        return fields;
    }

    public void setFields(Map fields) {
        this.fields = fields;
    }

    public Object getValue(){
        return getValue( null );
    }

    public Object getValue(boolean force){
        return getValue( null, null, -1, null, force );
    }

    public Object getValue( Object instance ){
        return getValue( instance, null, -1, null, false );
    }

    public Object getValue( String prefix ){
        return getValue( null, prefix, -1, null, false );
    }
    
    public Object getValue( Object instance, String prefix,
            ValidatorException exceptionHandler ){
        return getValue( instance, prefix, -1, exceptionHandler, false );
    }

    public Object getValue( Object instance, String prefix, long index, 
                ValidatorException exceptionHandler, boolean force ){
        
        if(logger.isDebugEnabled())
            logger.debug(
                    String.format(
                    "creating instance of bean %s: %s",
                    new Object[]{this.name,this.classType.getName()}));
        
        ValidatorException vex = new ValidatorException();
        Object obj;
        
        try{
            obj =
                instance == null?
                    getInstanceByConstructor( prefix, index, vex, force ) :
                    instance;
            
            if( obj == null )
                return null;


            boolean exist = instance != null ||
                    this.getConstructor().size() != 0 ||
                    (this.getConstructor().size() == 0 && fields.isEmpty()) ||
                    this.getConstructor().isMethodFactory();

            Iterator fds = fields.values().iterator();
            BeanInstance beanInstance = new BeanInstance( obj, classType );
            
            while( fds.hasNext() ){
                PropertyBean fb = (PropertyBean) fds.next();

                boolean existProperty = resolveAndSetProperty(fb, beanInstance, 
                        prefix, index, vex );
                
                if( !exist && (existProperty || fb.isNullable()) )
                    exist = true;

            }

            if(exist || force){
                if( exceptionHandler == null ){
                    if( !vex.getCauses().isEmpty() )
                        throw vex;
                    else
                        return obj;
                }
                else{

                    exceptionHandler.addCauses(vex.getCauses());
                    return obj;
                }
            }
            else
                return null;
            
        }
        catch( ValidatorException e ){
            throw e;
        }
        catch( Throwable e ){
            throw new MappingException( 
                    String.format(
                        "problem to create new instance of bean %s", 
                        new Object[]{this.getName()} ), e );
        }
    }
    
    private boolean resolveAndSetProperty(PropertyBean fb, BeanInstance beanInstance, 
            String prefix, long index, ValidatorException vex ){

        try{
            Object property = 
                    beanInstance.get( fb.getName() );
            Object value = fb.getValue(prefix, index, vex, property);
            
            if(logger.isDebugEnabled())
                logger.debug(
                        String.format(
                            "binding %s to property: %s", 
                            new Object[]{value,fb.getName()}));

            beanInstance.set( fb.getName(), value );
            return value != null;
        }
        catch( DependencyException e ){
            throw e;
        }
        catch( Throwable e ){
            throw new DependencyException(
                String.format("problem to resolve dependency: %s",
                    new Object[]{fb.getParameterName()}),
                    e);
        }
    }
    
    public boolean isBean(){
        return true;
    }

    public boolean isCollection(){
        return false;
    }

    public boolean isMap(){
        return false;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public boolean isHierarchy() {
        return hierarchy;
    }

    public void setMethodfactory( String methodFactory ){
        getConstructor().setMethodFactory(methodFactory);
    }
    
    public String getMethodfactory(){
        return getConstructor().getMethodFactory();
    }
    
    public void setHierarchy(boolean hierarchy) {
        this.hierarchy = hierarchy;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public ConstructorBean getConstructor() {
        return constructor;
    }

    public void setConstructor(ConstructorBean constructor) {
        this.constructor = constructor;
    }

    private Object getInstanceByConstructor( String prefix, long index,
            ValidatorException exceptionHandler, boolean force ) 
            throws InstantiationException, IllegalAccessException, 
            IllegalArgumentException, InvocationTargetException{
        
        ConstructorBean cons = this.getConstructor();
        if( cons.isConstructor() ){
            Constructor insCons = this.getConstructor().getContructor();
            Object[] args = this.getValues(cons, prefix, index, exceptionHandler, force );

            if( args == null )
                return null;
            
            return insCons.newInstance( args );
        }
        else{
            Bean factoryBean =
                this.getFactory() != null?
                    controller.getBean(factory) :
                    null;

            Object factoryInstance = null;
            
            if( this.getFactory() != null ){

                if( factoryBean == null )
                    throw new MappingException("bean not found: " + factory);
                
                factoryInstance = factoryBean.getValue(true);

                if( factoryInstance == null )
                    return null;
            }

            Method method = this.getConstructor().getMethod( factoryInstance );

            if( index != -1 && this.getConstructor().size() == 0 )
                throw new MappingException("infinite loop: " + 
                        this.getName());
            
            return method.invoke(
                    factory == null?
                        this.getClassType() :
                        factoryInstance,
                    getValues(cons, prefix, index, exceptionHandler, true ) );
        }
    }

    private Object[] getValues( ConstructorBean constructorBean, String prefix, 
            long index, ValidatorException exceptionHandler, boolean force ) {
        int size = constructorBean.size();
        Object[] values = new Object[ size ];

        boolean exist = false;
        for( int i=0;i<size;i++ ){
            ConstructorArgBean arg = constructorBean.getConstructorArg(i);
            values[i] = arg.getValue(prefix, index, exceptionHandler);
            
            if(logger.isDebugEnabled())
                logger.debug(
                    String.format(
                        "binding %s to constructor arg: %s", 
                        new Object[]{values[i],String.valueOf(i)}));
            
            if( force || values[i] != null || arg.isNullable()  )
                exist = true;
        }

        return exist || size == 0? values : null;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getIndexFormat() {
        return indexFormat;
    }

    public void setIndexFormat(String indexFormat) {
        this.indexFormat = indexFormat;
    }

    public PropertyBean getProperty(String name){
        return (PropertyBean) this.fields.get(name);
    }
    
}
