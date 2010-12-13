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
import java.util.List;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassType;

/**
 * 
 * @author Brandao
 */
public class ConstructorBean {

    private List args;

    private Constructor contructor;

    private Method method;

    private String methodFactory;

    private MappingBean bean;

    public ConstructorBean( MappingBean bean ){
        this.args = new ArrayList();
        this.bean = bean;
    }

    public boolean isConstructor(){
        return getMethodFactory() == null;
    }

    public boolean isMethodFactory(){
        return getMethodFactory() != null;
    }

    public Constructor getContructor() {
        if( contructor == null )
            setContructor(getContructor(getBean().getClassType()));
        return contructor;
    }

    public Method getMethod( Object factory ) {
        if( getMethod() == null ){
            Class clazz = factory == null?
                                getBean().getClassType() :
                                factory.getClass();

            setMethod(getMethod(getMethodFactory(), clazz));
            if( getMethod().getReturnType() == void.class )
                throw new BrutosException( "invalid return: " + getMethod().toString() );
        }
        return getMethod();
    }

    private Constructor getContructor( Class clazz ){
        Class[] classArgs = new Class[ getArgs().size() ];

        for( int i=0;i<getArgs().size();i++ ){
            MappingBean arg = (MappingBean) getArgs().get(i);
            classArgs[ i ] = arg.getClassType();
        }

        Constructor[] cons = clazz.getConstructors();
        for( int i=0;i<cons.length;i++ ){
            Constructor con = cons[i];
            if( isCompatible( con, classArgs ) ){
                return con;
            }
        }

        String msg = "not found: " + clazz.getName() + "( ";

        for( int i=0;i<classArgs.length;i++ ){
            Class arg = classArgs[i];
            msg += i != 0? ", " : "";
            msg += arg == null? "?" : arg.getName();
        }
        msg += " )";

        throw new BrutosException( msg );
    }

    private Method getMethod( String name, Class clazz ){
        Class[] classArgs = new Class[ getArgs().size() ];

        for( int i=0;i<getArgs().size();i++ ){
            MappingBean arg = (MappingBean) getArgs().get(i);
            //if( arg.getTarget() != null )
                classArgs[ i ] = arg.getClassType();
        }

        Method[] methods = clazz.getDeclaredMethods();
        for( int i=0;i<methods.length;i++ ){
            Method m = methods[i];
            if( m.getName().equals(name) &&
                isCompatible( m, classArgs ) ){
                return m;
            }
        }

        String msg = "not found: " + clazz.getName() + "( ";

        for( int i=0;i<classArgs.length;i++ ){
            Class arg = classArgs[i];
            msg += i != 0? ", " : "";
            msg += arg == null? "?" : arg.getName();
        }
        msg += " )";

        throw new BrutosException( msg );
    }

    private boolean isCompatible( Constructor m, Class[] classArgs ){
        Class[] params = m.getParameterTypes();
        if( params.length == classArgs.length ){
            for( int i=0;i<params.length;i++ ){
                if( classArgs[i] != null && !params[i].isAssignableFrom( classArgs[i] ) )
                //if( classArgs[i] != null && !ClassType.getWrapper( params[i] ).isAssignableFrom( ClassType.getWrapper( classArgs[i] ) ) )
                    return false;
            }
            return true;
        }
        else
            return false;

    }

    private boolean isCompatible( Method m, Class[] classArgs ){
        Class[] params = m.getParameterTypes();
        if( params.length == classArgs.length ){
            for( int i=0;i<params.length;i++ ){
                //if( classArgs[i] != null && !params[i].isAssignableFrom( classArgs[i] ) )
                if( classArgs[i] != null && !ClassType.getWrapper( params[i] ).isAssignableFrom( ClassType.getWrapper( classArgs[i] ) ) )
                    return false;
            }
            return true;
        }
        else
            return false;

    }

    public List getArgs() {
        return args;
    }

    public void setArgs(List args) {
        this.args = args;
    }

    public void setContructor(Constructor contructor) {
        this.contructor = contructor;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getMethodFactory() {
        return methodFactory;
    }

    public void setMethodFactory(String methodFactory) {
        this.methodFactory = methodFactory;
    }

    public MappingBean getBean() {
        return bean;
    }

    public void setBean(MappingBean bean) {
        this.bean = bean;
    }

}
