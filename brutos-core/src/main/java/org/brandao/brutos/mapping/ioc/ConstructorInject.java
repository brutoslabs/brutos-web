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

package org.brandao.brutos.mapping.ioc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;

/**
 *
 * @author Afonso Brandao
 */
public class ConstructorInject {
    
    private List args;
    
    private Constructor contructor;

    private Method method;

    private String methodFactory;

    private Injectable inject;

    public ConstructorInject( Injectable inject ){
        this.args = new ArrayList();
        this.inject = inject;
    }

    /**
     * @deprecated
     * @param constructor
     * @param args
     */
    public ConstructorInject( Constructor constructor, Injectable[] args ) {
        this.contructor = constructor;
        this.args = args.length == 0? new ArrayList() : Arrays.asList( args );
    }

    public void setMethodFactory( String method ){
        this.methodFactory = method;
    }

    public String getMethodFactory(){
        return this.methodFactory;
    }

    public void addArg( Injectable arg ){
        args.add(arg);
    }

    public void removeArg( Injectable arg ){
        args.remove(arg);
    }

    public Injectable getArg( int index ){
        return (Injectable) args.get(index);
    }

    public int length(){
        return args.size();
    }

    public List getArgs() {
        return args;
    }

    public void setArgs(List args) {
        this.args = args;
    }

    public boolean isConstructor(){
        return methodFactory == null;
    }

    public boolean isMethodFactory(){
        return methodFactory != null;
    }

    public Constructor getContructor() {
        if( contructor == null )
            contructor = getContructor( inject.getTarget() );
        return contructor;
    }

    public void setContructor(Constructor contructor) {
        this.contructor = contructor;
    }

    public Method getMethod( Object factory ) {
        if( method == null ){
            Class clazz = factory == null?
                                inject.getTarget() :
                                factory.getClass();

            method = getMethod( methodFactory, clazz );
            if( method.getReturnType() == void.class )
                throw new BrutosException( "invalid return: " + method.toString() );
        }
        return method;
    }

    private Constructor getContructor( Class clazz ){
        Class[] classArgs = new Class[ args.size() ];

        //int i=0;
        //for( Injectable arg: args ){
        for(int i=0;i<args.size();i++){
            Injectable arg = (Injectable) args.get(i);
            if( arg.getTarget() != null )
                classArgs[ i ] = arg.getTarget();
            //i++;
        }

        //for( Constructor con: clazz.getConstructors() ){
        Constructor[] cons = clazz.getConstructors();
        for(int i=0;i<cons.length;i++){
            Constructor con = cons[i];
            if( isCompatible( con, classArgs ) )
                return con;
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
        Class[] classArgs = new Class[ args.size() ];

        //int i=0;
        //for( Injectable arg: args ){
        for(int i=0;i<args.size();i++){
            Injectable arg = (Injectable) args.get(i);
            if( arg.getTarget() != null )
                classArgs[ i ] = arg.getTarget();
            //i++;
        }

        Method[] methods = clazz.getDeclaredMethods();
        //for( Method m: clazz.getDeclaredMethods() ){
        for( int i=0;i<methods.length;i++ ){
            Method m = methods[i];
            if( m.getName().equals(name) && 
                /*( inject.getFactory() != null || Modifier.isStatic( m.getModifiers() ) ) &&*/
                isCompatible( m, classArgs ) )
                return m;
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
                if( classArgs[i] != null && !ClassUtil.getWrapper( params[i] ).isAssignableFrom( ClassUtil.getWrapper( classArgs[i] ) ) )
                    return false;
            }
            return true;
        }
        else
            return false;

    }

}
