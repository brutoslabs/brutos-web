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
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.validator.Validator;

/**
 *
 * @author Afonso Brandao
 */
public class MappingBean {

    private Form form;

    private String name;
    
    private Class<?> classType;
    
    private Map<String, FieldBean> fields;

    private boolean hierarchy;

    private String separator;

    private ConstructorBean constructor;

    private String factory;

    public MappingBean( Form form ) {
        this.fields = new HashMap();
        this.form = form;
        this.hierarchy = true;
        this.separator = ".";
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

    public Map<String, FieldBean> getFields() {
        return fields;
    }

    public void setFields(Map<String, FieldBean> fields) {
        this.fields = fields;
    }

    public Object getValue(){
        return getValue( null, null );
    }

    public Object getValue(boolean force){
        return getValue( null, null, null, -1, force );
    }

    public Object getValue( HttpServletRequest request ){
        return getValue( request, null );
    }

    public Object getValue( HttpServletRequest request, Object instance ){
        return getValue( request, instance, null, -1, false );
    }

    public Object getValue( HttpServletRequest request, Object instance, String prefix ){
        return getValue( request, instance, prefix, -1, false );
    }

    /*
    public Object getValue(HttpServletRequest request, Object instance, String prefix, long index ){
        return getValue(request, instance, prefix, index, false );
    }
    */
    public Object getValue( /**
                             * @deprecated
                             */
            HttpServletRequest request, Object instance, String prefix, long index, boolean force ){
        try{
            Object obj;

            obj = instance == null? getInstanceByConstructor( prefix, index, request ) : instance;

            if( obj == null )
                return null;


            boolean exist = instance != null ||
                    this.getConstructor().getArgs().size() != 0 ||
                    this.getConstructor().isMethodFactory();

            Iterator<FieldBean> fds = fields.values().iterator();
            BeanInstance beanInstance = new BeanInstance( obj, classType );
            
            while( fds.hasNext() ){
                FieldBean fb = fds.next();

                Object value = this.getValueField(beanInstance, prefix, index, request, fb);

                if( !exist && value != null )
                    exist = true;

                beanInstance.getSetter( fb.getName() ).set( value );

            }
            return exist || force? obj : null;
        }
        catch( Exception e ){
            return null;
        }
    }

    private Object getValueField( BeanInstance beanInstance, String prefix, long index, HttpServletRequest request, FieldBean fb ) throws Exception{
        Validator validator = fb.getValidator();
        Object property;

        if( fb.getMapping() == null ){
            Object value = fb.isStatic()?
                fb.getValue() :
                fb.getScope().get(
                        (prefix != null? prefix : "") +
                        fb.getParameterName() +
                            ( index < 0? "" : "[" + index + "]" ) );

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
                request,
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

    private Object getConstructorArg( String prefix, long index, HttpServletRequest request, FieldBean fb ) throws Exception{
        Validator validator = fb.getValidator();
        Object property;

        if( fb.getMapping() == null ){
            Object value = fb.isStatic()?
                fb.getValue() :
                fb.getScope().get(
                        (prefix != null? prefix : "") +
                        fb.getParameterName() +
                            ( index < 0? "" : "[" + index + "]" ) );

            Type type = fb.getType();
            property = type.getValue( value );
        }
        else{

            //obtem o objeto resultante
            MappingBean mappingBean = form.getMappingBean( fb.getMapping() );

            if( mappingBean == null )
                throw new BrutosException( "mapping name " + fb.getMapping() + " not found!" );

            property = mappingBean.getValue(
                request,
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

    public boolean isBean(){
        return true;
    }

    public boolean isCollection(){
        return false;
    }

    public boolean isMap(){
        return false;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
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

    private Object getInstanceByConstructor( String prefix, long index, HttpServletRequest request ) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, Exception{
        ConstructorBean cons = this.getConstructor();
        ConstructorBean conInject = this.getConstructor();
        if( conInject.isConstructor() ){
            
            Object[] args = this.getValues(cons.getArgs(), prefix, index, request );

            if( args == null )
                return null;

            Constructor insCons = this.getConstructor().getContructor();
            return insCons.newInstance( args );
        }
        else{
            MappingBean factoryBean =
                this.getFactory() != null?
                    form.getMappingBean(factory) :
                    null;

            Object factoryInstance = factoryBean.getValue(true);

            if( factoryInstance == null )
                return null;

            Method method = this.getConstructor().getMethod( factoryInstance );

            return method.invoke(
                    factory == null?
                        this.getClassType() :
                        factoryInstance,
                    getValues(cons.getArgs(), prefix, index, request ) );
        }
    }

    private Object[] getValues( List args, String prefix, long index, HttpServletRequest request ) throws Exception{

        Object[] values = new Object[ args.size() ];

        boolean exist = false;
        for( int i=0;i<args.size();i++ ){
            FieldBean arg = (FieldBean) args.get(i);
            values[i] = this.getConstructorArg( prefix, index, request, arg);
            if( values[i] != null )
                exist = true;
        }

        return exist || args.size() == 0? values : null;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
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
            //se nao existir nenhuma propriedade do bean, então o bean não existe
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

            //se nao existir nenhuma propriedade do bean, entao o bean não existe
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

            //se nao existir nenhuma propriedade do bean, entao o bean não existe
            return exist? obj : null;
        }
        catch( Exception e ){
            return null;
        }
    }
    */
}
