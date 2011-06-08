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
import org.brandao.brutos.type.NullType;
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
        return getValue( null, null, -1, force );
    }

    public Object getValue( Object instance ){
        return getValue( instance, null, -1, false );
    }

    public Object getValue( Object instance, String prefix ){
        return getValue( instance, prefix, -1, false );
    }

    /*
    public Object getValue(HttpServletRequest request, Object instance, String prefix, long index ){
        return getValue(request, instance, prefix, index, false );
    }
    */
    public Object getValue( Object instance, String prefix, long index, boolean force ){
        try{
            Object obj;

            obj = instance == null? getInstanceByConstructor( prefix, index ) : instance;

            if( obj == null )
                return null;


            boolean exist = instance != null ||
                    this.getConstructor().size() != 0 ||
                    (this.getConstructor().size() == 0 && fields.size() == 0) ||
                    this.getConstructor().isMethodFactory();

            Iterator<PropertyBean> fds = fields.values().iterator();
            BeanInstance beanInstance = new BeanInstance( obj, classType );
            
            while( fds.hasNext() ){
                PropertyBean fb = fds.next();

                Object value = fb.getValue(prefix, index);
                        //this.getValueField(beanInstance, prefix, index, fb);

                if( !exist && (value != null || fb.isNullable()) )
                    exist = true;

                beanInstance.getSetter( fb.getName() ).set( value );

            }
            return exist || force? obj : null;
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
    /*
    private Object getValueField( BeanInstance beanInstance, String prefix, long index, PropertyBean fb ) throws Exception{
        Validator validator = fb.getValidator();
        Object property;

        if( fb.getMapping() == null ){
            Object value = fb.isStatic()?
                fb.getValue() :
                fb.getScope().get(
                        (prefix != null? prefix : "") +
                        fb.getParameterName() +
                            //( index < 0? "" : "[" + index + "]" ) );
                            ( index < 0?
                                "" :
                                indexFormat.replace(
                                    "$index",
                                    String.valueOf(index) ) ) );

            Type type = fb.getType();
            property = type.getValue( value );
        }
        else{
            //obtem o atual valor da propriedade
            property = beanInstance.getGetter( fb.getName() ).get();

            //obtem o objeto resultante
            MappingBean mappingBean = form.getMappingBean( fb.getMapping() );

            if( mappingBean == null )
                throw new BrutosException( "mapping name " + fb.getMapping() + " not found!" );

            property = mappingBean.getValue(
                property,
                isHierarchy()?
                    prefix != null?
                        prefix + fb.getParameterName() + getSeparator() :
                        fb.getParameterName() + getSeparator()
                    :
                    null );

        }

        if( validator != null )
            validator.validate(fb, property);

        return property;
    }
    
    private Object getConstructorArg( String prefix, long index, ConstructorArgBean arg ) throws Exception{
        Validator validator = arg.getValidator();
        Object property;

        if( arg.getMapping() == null ){
            Object value = fb.isStatic()?
                fb.getValue() :
                fb.getScope().get(
                        (prefix != null? prefix : "") +
                        fb.getParameterName() +
                            //( index < 0? "" : "[" + index + "]" ) );
                            ( index < 0?
                                "" :
                                indexFormat.replace(
                                    "$index",
                                    String.valueOf(index) ) ) );

            Type type = fb.getType();
            property = type.getValue( value );
        }
        else{

            //obtem o objeto resultante
            MappingBean mappingBean = form.getMappingBean( fb.getMapping() );

            if( mappingBean == null )
                throw new BrutosException( "mapping name " + fb.getMapping() + " not found!" );

            property = mappingBean.getValue(
                null,
                isHierarchy()?
                    prefix != null?
                        prefix + fb.getParameterName() + getSeparator() :
                        fb.getParameterName() + getSeparator()
                    :
                    null );

        }

        if( validator != null )
            validator.validate(fb, property);

        return property;
    }
    */
    
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

    private Object getInstanceByConstructor( String prefix, long index ) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, Exception{
        ConstructorBean cons = this.getConstructor();
        ConstructorBean conInject = this.getConstructor();
        if( conInject.isConstructor() ){
            Constructor insCons = this.getConstructor().getContructor();
            Object[] args = this.getValues(cons, prefix, index, false );

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
                    getValues(cons, prefix, index, true ) );
        }
    }

    private Object[] getValues( ConstructorBean constructorBean, String prefix, long index, boolean force ) throws Exception{
        int size = constructorBean.size();
        Object[] values = new Object[ size ];

        boolean exist = false;
        for( int i=0;i<size;i++ ){
            ConstructorArgBean arg = constructorBean.getConstructorArg(i);
            values[i] = arg.getValue(prefix, index);
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

    /*
    public Object getValue( HttpServletRequest request ){
        return getValue( request, null );
    }
    
    public Object getValue( HttpServletRequest request, Object instance ){
        return getValue( request, instance, -1 );
    }

    public Object getValue( HttpServletRequest request, Object instance, long index ){
        try{
            //bean
            Object obj;
            //se true o bean existe
            boolean exist = false;
            obj = instance == null? classType.newInstance() : instance;
            Iterator<FieldBean> fds = fields.values().iterator();
            BeanInstance beanInstance = new BeanInstance( obj, classType );
            while( fds.hasNext() ){
                FieldBean fb = fds.next();
                //Field f = fb.getField();
                if( fb.getMapping() == null ){
                    Object value =
                        ((BrutosRequest)request)
                            .getObject(
                                fb.getParameterName() +
                                    ( index < 0? "" : "[" + index + "]" ) );

                    //se value for diferente null, entao existe uma propriedade do bean
                    if( !exist && value != null )
                        exist = true;
                    
                    Type type = fb.getType();
                    Object o = type.getValue( request, request.getSession().getServletContext(), value );
                    beanInstance.getSetter( fb.getField().getName() ).set( o );
                }
                else{
                    //obtem o atual valor da propriedade
                    Object property = beanInstance.getGetter( fb.getField().getName() ).get();

                    //obtem o objeto resultante
                    property = fb.getMapping().getValue( request, property, index );

                    //se property for diferente null, entao existe uma propriedade do bean
                    if( !exist && property != null )
                        exist = true;

                    beanInstance.getSetter( fb.getField().getName() )
                        .set( property );
                }
            }
            //se nao existir nenhuma propriedade do bean, ent�o o bean n�o existe
            return exist || instance != null? obj : null;
        }
        catch( Exception e ){
            return null;
        }
        
    }
    
    public Object getValue( HttpSession session ){
        return getValue( session, -1 );
    }

    public Object getValue( HttpSession session, long index ){
        try{
            //bean
            Object obj = classType.newInstance();
            //se true o bean existe
            boolean exist = false;

            Iterator<FieldBean> fds = fields.values().iterator();
            BeanInstance beanInstance = new BeanInstance( obj, classType );
            while( fds.hasNext() ){
                FieldBean fb = fds.next();
                Field f = fb.getField();
                f.setAccessible( true );
                if( fb.getMapping() == null ){
                    Object value = session.getAttribute( fb.getParameterName()
                                        + ( index < 0? "" : "[" + index + "]" ) );

                    if( value != null ){
                        //se value for diferente null, entao existe uma propriedade do bean
                        if( !exist )
                            exist = true;

                        f.set( obj, value );
                    }
                }
                else{
                    //obtem o atual valor da propriedade
                    Object property = beanInstance.getGetter( fb.getField().getName() ).get();

                    //obtem o objeto resultante
                    property = fb.getMapping().getValue( session, index );

                    //se property for diferente null, entao existe uma propriedade do bean
                    if( !exist && property != null )
                        exist = true;

                    beanInstance.getSetter( fb.getField().getName() )
                        .set( property );
                }
            }

            //se nao existir nenhuma propriedade do bean, entao o bean n�o existe
            return exist? obj : null;
        }
        catch( Exception e ){
            return null;
        }
    }
    
    public Object getValue( ServletContext context ){
        return getValue( context, -1 );
    }
    
    public Object getValue( ServletContext context, long index ){
        try{
            //bean
            Object obj = classType.newInstance();
            //se true o bean existe
            boolean exist = false;

            Iterator<FieldBean> fds = fields.values().iterator();
            BeanInstance beanInstance = new BeanInstance( obj, classType );
            while( fds.hasNext() ){
                FieldBean fb = fds.next();
                Field f = fb.getField();
                f.setAccessible( true );
                if( fb.getMapping() == null ){
                    Object value = context.getAttribute( fb.getParameterName()
                                        + ( index < 0? "" : "[" + index + "]" ) );

                    if( value != null ){
                        //se value for diferente null, entao existe uma propriedade do bean
                        if( !exist )
                            exist = true;

                        f.set( obj, value );
                    }
                }
                else{
                    //obtem o atual valor da propriedade
                    Object property = beanInstance.getGetter( fb.getField().getName() ).get();

                    //obtem o objeto resultante
                    property = fb.getMapping().getValue( context, index );

                    //se property for diferente null, entao existe uma propriedade do bean
                    if( !exist && property != null )
                        exist = true;

                    beanInstance.getSetter( fb.getField().getName() )
                        .set( property );
                }
            }

            //se nao existir nenhuma propriedade do bean, entao o bean n�o existe
            return exist? obj : null;
        }
        catch( Exception e ){
            return null;
        }
    }
    */
}
