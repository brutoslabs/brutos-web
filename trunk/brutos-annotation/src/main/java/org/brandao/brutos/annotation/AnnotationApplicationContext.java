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

package org.brandao.brutos.annotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.brandao.brutos.ApplicationContext;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.DispatcherType;

/**
 *
 * @author Afonso Brandao
 */
public class AnnotationApplicationContext extends ApplicationContext{
    
    private List<Class> types;
    private List<Class> controllers;
    private List<Class> interceptors;
    private Class[] allClazz;
    
    public AnnotationApplicationContext(Class[] clazz) {
        this.allClazz = clazz;
    }

    public AnnotationApplicationContext(Class[] clazz,
            ApplicationContext parent) {
        super(parent);
        this.allClazz = clazz;
    }

    public void configure( Configuration config ) {
        super.configure(configuration);
        loadClass();
        loadControllers();
    }

    @Override
    public void destroy(){
        this.controllers  = null;
        this.interceptors = null;
        this.types        = null;
    }
    
    private void loadClass(){
        this.controllers = new ArrayList<Class>();
        this.interceptors = new ArrayList<Class>();
        this.types = new ArrayList<Class>();

        for( Class classe: allClazz ){

            if( classe.isAnnotationPresent( Controller.class ) )
                this.controllers.add(classe);

            if( classe.isAnnotationPresent( Intercepts.class ) )
                this.interceptors.add(classe);

            if( classe.isAnnotationPresent( TypeDef.class ) )
                this.types.add(classe);

        }
    }


    protected void loadControllers(){

        for( Class clazz: controllers ){
            Controller annotationController =
                    (Controller) clazz.getAnnotation(Controller.class);

            ControllerBuilder controllerBuilder =
                    controllerManager.addController(
                        annotationController.id(),
                        annotationController.view(),
                        DispatcherType.valueOf(annotationController.dispatcher()),
                        annotationController.name(), clazz,
                        annotationController.actionId());

            addActions( controllerBuilder, clazz );
        }
    }

    protected void addActions( ControllerBuilder controllerBuilder, Class clazz ){
        Method[] methods = clazz.getMethods();

        for( Method m: methods ){

            if( m.isAnnotationPresent(Action.class) ){
                Action aAction = m.getAnnotation( Action.class );
                controllerBuilder
                    .addAction(aAction.id(), aAction.resultName(),
                    aAction.view(), DispatcherType.valueOf(aAction.dispatcher()),
                    m.getName());
            }
        }
    }
}
