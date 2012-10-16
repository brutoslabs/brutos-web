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

package org.brandao.brutos.annotation.configuration;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.brandao.brutos.*;
import org.brandao.brutos.annotation.*;
import org.brandao.brutos.annotation.bean.BeanPropertyAnnotationImp;
import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.bean.BeanProperty;
import org.brandao.brutos.mapping.StringUtil;

/**
 *
 * @author Brandao
 */
@Stereotype(target=Controller.class)
public class ControllerAnnotationConfig 
    extends AbstractAnnotationConfig{

    @Override
    public Object applyConfiguration(Object arg0, Object arg1,
            ConfigurableApplicationContext applicationContext){
        
        Class source = (Class)arg0;
        ControllerBuilder builder;
        
        Controller annotationController =
                (Controller) source.getAnnotation(Controller.class);
        View viewAnnotation = (View) source.getAnnotation(View.class);
        ActionStrategy strategy = (ActionStrategy) source.getAnnotation(ActionStrategy.class);
        
        String controllerID;
        String view;
        org.brandao.brutos.DispatcherType dispatcher;
        
        String name     = null;
        String actionID = null;
        String defaultActionName = null;
        ActionType actionType = 
            strategy == null? 
                null : 
                ActionType.valueOf(strategy.value().name());
        
        if(annotationController != null){
            name = annotationController.name();
            actionID = annotationController.actionId();
            defaultActionName = annotationController.defaultActionName();
        }

        controllerID = this.getControllerId(annotationController, source);
        
        dispatcher = 
            viewAnnotation == null || "".equals(viewAnnotation.dispatcher())? 
                BrutosConstants.DEFAULT_DISPATCHERTYPE : 
                org.brandao.brutos.DispatcherType.valueOf(viewAnnotation.dispatcher());
        
        builder =
                applicationContext.getControllerManager().addController(
                    controllerID,
                    null,
                    dispatcher,
                    name,
                    source,
                    actionID,
                    actionType);

        
        view = getView((View) source.getAnnotation(View.class), builder,
            applicationContext);
        
        if(annotationController != null && annotationController.id().length > 1){
            String[] ids = annotationController.id();
            for(int i=1;i<ids.length;i++ ){
                if(!StringUtil.isEmpty(ids[i]))
                    builder.addAlias(StringUtil.adjust(ids[i]));
                else
                    throw new BrutosException("invalid controller id: " + source.getName());
            }
        }
            
        builder.setView(view);
        builder.setDefaultAction(defaultActionName);
        
        super.applyInternalConfiguration(source, builder, applicationContext);

        throwsSafe(builder, source, applicationContext);
        addProperties(builder, applicationContext, source);
        addActions( builder, applicationContext, source );
        
        return builder;
    }
    
    protected void throwsSafe(ControllerBuilder builder, Class clazz,
            ConfigurableApplicationContext applicationContext){
        
        List<ThrowableEntry> list = new ArrayList<ThrowableEntry>();
        ThrowSafeList throwSafeList = (ThrowSafeList) clazz.getAnnotation(ThrowSafeList.class);
        ThrowSafe throwSafe = (ThrowSafe) clazz.getAnnotation(ThrowSafe.class);
        
        if(throwSafeList != null)
            list.addAll(
                    AnnotationUtil.toList(AnnotationUtil.toList(throwSafeList)));

        if(throwSafe != null)
            list.add(
                    AnnotationUtil.toEntry(throwSafe));
        
        for(ThrowableEntry entry: list)
            super.applyInternalConfiguration(entry, builder, applicationContext);
        
    }
    
    protected String getView(View viewAnnotation, ControllerBuilder controller,
        ConfigurableApplicationContext applicationContext){
        
        boolean rendered = viewAnnotation == null? true : viewAnnotation.rendered();
        
        String view = 
            viewAnnotation != null && viewAnnotation.id().trim().length() > 0?
                viewAnnotation.id() : null;
        
        
        if(rendered){
            if(view != null)
                return viewAnnotation.id();
            else
                return createControllerView(controller, applicationContext);
        }
        else
            return null;
    }
    
    protected String createControllerView(ControllerBuilder controller,
            ConfigurableApplicationContext applicationContext){
        
        return applicationContext.getViewResolver()
                .getView(controller, null, null,
                applicationContext.getConfiguration());
    }
    
    protected void addProperties(ControllerBuilder controllerBuilder, 
            ConfigurableApplicationContext applicationContext, Class clazz){
    
        BeanInstance instance = new BeanInstance(null,clazz);
        List props = instance.getProperties();
        for(int i=0;i<props.size();i++){
            BeanProperty prop = (BeanProperty) props.get(i);
            super.applyInternalConfiguration(new BeanPropertyAnnotationImp(prop), 
                    controllerBuilder, applicationContext);
        }
    }
    
    protected void addActions( ControllerBuilder controllerBuilder, 
            ConfigurableApplicationContext applicationContext, Class clazz ){
        
        List<ActionEntry> actionList = new ArrayList<ActionEntry>();
        
        AbstractActions abstractActions = 
                (AbstractActions)clazz.getAnnotation(AbstractActions.class);
        
        AbstractAction abstractAction = 
                (AbstractAction)clazz.getAnnotation(AbstractAction.class);
        
        List<AbstractAction> actions = 
            new ArrayList<AbstractAction>();
        
        if(abstractActions != null)
                actions.addAll(Arrays.asList(abstractActions.value()));
        
        if(abstractAction!= null)
            actions.add(abstractAction);
        
        for(AbstractAction action: actions){
            for(String id: action.id()){
                if(StringUtil.isEmpty(id))
                    throw new BrutosException("invalid action: " + id);
                
                ActionEntry entry = 
                    new ActionEntry(
                        id,
                        null,
                        controllerBuilder.getClassType(),
                        null,
                        null,
                        null,
                        null,
                        true);
                
                actionList.add(entry);
            }
        }
        
        Method[] methods = clazz.getMethods();

        for( Method m: methods ){
            ActionEntry entry = new ActionEntry(m);
            actionList.add(entry);
        }
        
        for( AbstractAction m: actions ){
            super.applyInternalConfiguration(m, controllerBuilder, applicationContext);
        }
    }

    protected String getControllerName(Class controllerClass){
        return controllerClass.getSimpleName().replaceAll("Controller$", "").toLowerCase();
    }
    
    protected String getControllerId(Controller annotation, Class controllerClass){
        boolean hasControllerId = 
            annotation != null && annotation.id().length > 0 && 
            !StringUtil.isEmpty(annotation.id()[0]);
        
        return hasControllerId? annotation.id()[0] : getControllerName(controllerClass);
    }
    
    public boolean isApplicable(Object source) {
        return source instanceof Class && AnnotationUtil.isController((Class)source);
    }
    
}
