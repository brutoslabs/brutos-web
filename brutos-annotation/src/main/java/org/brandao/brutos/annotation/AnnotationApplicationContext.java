/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
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

package org.brandao.brutos.annotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.brandao.brutos.AbstractApplicationContext;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.ControllerBuilder;
import org.brandao.brutos.DispatcherType;

/**
 *
 * @author Afonso Brandao
 */
public class AnnotationApplicationContext extends AbstractApplicationContext{
    
    private List<Class> types;
    private List<Class> controllers;
    private List<Class> interceptors;
    private Class[] allClazz;
    
    public AnnotationApplicationContext(Class[] clazz) {
        this.allClazz = clazz;
    }

    public AnnotationApplicationContext(Class[] clazz,
            AbstractApplicationContext parent) {
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

            if( isController(classe) )
                this.controllers.add(classe);

            if( isInterceptor(classe) )
                this.interceptors.add(classe);

            if( classe.isAnnotationPresent( TypeDef.class ) )
                this.types.add(classe);

            checkCustomAnnotation(classe);
        }
    }

    /**
     * Verifica se a classe representa um controlador.
     * @param clazz Classe a ser verificada.
     * @return Verdadeiro se a classe representar um controlador.
     */
    protected boolean isController(Class clazz){
       return clazz.isAnnotationPresent( Controller.class ) ||
               clazz.getSimpleName().endsWith("Controller");
    }

    /**
     * Verifica se a classe representa um interceptador.
     * @param clazz Classe a ser verificada.
     * @return Verdadeiro se a classe representar um interceptador.
     */
    protected boolean isInterceptor(Class clazz){
       return clazz.isAnnotationPresent( Intercepts.class ) ||
               clazz.getSimpleName().endsWith("Interceptor");
    }

    /**
     * Verifica se a classe possui uma anotação customizada.
     * @param clazz Classe a ser verificada.
     * @return Verdadeiro se a classe representar um interceptador.
     */
    protected void checkCustomAnnotation(Class clazz){
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
