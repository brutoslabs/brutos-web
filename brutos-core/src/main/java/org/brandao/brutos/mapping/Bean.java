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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.validator.ValidatorException;

/**
 *
 * @author Afonso Brandao
 */
public class Bean {

    private Controller form;

    private String name;
    
    private Class<?> classType;
    
    private Map<String, PropertyBean> fields;

    private boolean hierarchy;

    private String separator;

    private ConstructorBean constructor;

    private String factory;

    private String indexFormat;

    public Bean( Controller form ) {
        this.fields = new HashMap();
        this.form = form;
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

    public Class<?> getClassType() {
        return classType;
    }

    public void setClassType(Class<?> classType) {
        this.classType = classType;
    }

    public Map<String, PropertyBean> getFields() {
        return fields;
    }

    public void setFields(Map<String, PropertyBean> fields) {
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

    public Object getValue( Object instance, String prefix,
            ValidatorException exceptionHandler ){
        return getValue( instance, prefix, -1, exceptionHandler, false );
    }

    public Object getValue( Object instance, String prefix, long index, 
                ValidatorException exceptionHandler, boolean force ){
        try{

            ValidatorException vex = new ValidatorException();

            Object obj =
                instance == null?
                    getInstanceByConstructor( prefix, index, vex, force ) :
                    instance;

            if( obj == null )
                return null;


            boolean exist = instance != null ||
                    this.getConstructor().size() != 0 ||
                    (this.getConstructor().size() == 0 && fields.isEmpty()) ||
                    this.getConstructor().isMethodFactory();

            Iterator<PropertyBean> fds = fields.values().iterator();
            BeanInstance beanInstance = new BeanInstance( obj, classType );
            
            while( fds.hasNext() ){
                PropertyBean fb = fds.next();

                Object value = fb.getValue(prefix, index, vex);

                if( !exist && (value != null || fb.isNullable()) )
                    exist = true;

                beanInstance.getSetter( fb.getName() ).set( value );

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
            
            //return exist || force? obj : null;
        }
        catch( ValidatorException e ){
            throw e;
        }
        catch( BrutosException e ){
            throw e;
        }
        catch( Exception e ){
            throw new BrutosException(e);
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

    public Controller getForm() {
        return form;
    }

    public void setForm(Controller form) {
        this.form = form;
    }

    public boolean isHierarchy() {
        return hierarchy;
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
            ValidatorException exceptionHandler, boolean force ) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, Exception{
        ConstructorBean cons = this.getConstructor();
        ConstructorBean conInject = this.getConstructor();
        if( conInject.isConstructor() ){
            Constructor insCons = this.getConstructor().getContructor();
            Object[] args = this.getValues(cons, prefix, index, exceptionHandler, force );

            if( args == null )
                return null;
            
            return insCons.newInstance( args );
        }
        else{
            Bean factoryBean =
                this.getFactory() != null?
                    form.getMappingBean(factory) :
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
                throw new MappingException("can infinite loop: " + 
                        this.getName());
            
            return method.invoke(
                    factory == null?
                        this.getClassType() :
                        factoryInstance,
                    getValues(cons, prefix, index, exceptionHandler, true ) );
        }
    }

    private Object[] getValues( ConstructorBean constructorBean, String prefix, 
            long index, ValidatorException exceptionHandler, boolean force ) throws Exception{
        int size = constructorBean.size();
        Object[] values = new Object[ size ];

        boolean exist = false;
        for( int i=0;i<size;i++ ){
            ConstructorArgBean arg = constructorBean.getConstructorArg(i);
            values[i] = arg.getValue(prefix, index, exceptionHandler);
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
    
}
