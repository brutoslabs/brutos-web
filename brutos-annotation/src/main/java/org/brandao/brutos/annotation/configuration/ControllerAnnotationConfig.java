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

    public Object applyConfiguration(Object source, Object builder, 
            ComponentRegistry componentRegistry) {
    
        try{
            return applyConfiguration0(source, builder, componentRegistry);
        }
        catch(Exception e){
            throw 
                new BrutosException(
                        "can't create controller: " + ((Class)source).getName(),
                        e );
        }
        
    }
    
    public Object applyConfiguration0(Object arg0, Object arg1,
            ComponentRegistry componentRegistry){
        
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

        controllerID = this.getControllerId(componentRegistry,annotationController, source);
        
        dispatcher = 
            viewAnnotation == null || "".equals(viewAnnotation.dispatcher())? 
                BrutosConstants.DEFAULT_DISPATCHERTYPE : 
                org.brandao.brutos.DispatcherType.valueOf(viewAnnotation.dispatcher());
        
        builder =
                componentRegistry.registerController(
                    controllerID,
                    null,
                    dispatcher,
                    name,
                    source,
                    actionID,
                    actionType);

        
        view = getView((View) source.getAnnotation(View.class), builder,
            componentRegistry);
        
        if(annotationController != null && annotationController.value().length > 1){
            String[] ids = annotationController.value();
            for(int i=1;i<ids.length;i++ ){
                if(!StringUtil.isEmpty(ids[i]))
                    builder.addAlias(StringUtil.adjust(ids[i]));
                else
                    throw new BrutosException("invalid controller id: " + source.getName());
            }
        }
            
        builder.setView(view);
        builder.setDefaultAction(defaultActionName);
        
        super.applyInternalConfiguration(source, builder, componentRegistry);

        importBeans(builder, componentRegistry, builder.getClassType());
        throwsSafe(builder, source, componentRegistry);
        addProperties(builder, componentRegistry, source);
        addActions( builder, componentRegistry, source );
        
        return builder;
    }
    
    protected void throwsSafe(ControllerBuilder builder, Class clazz,
            ComponentRegistry componentRegistry){
        
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
            super.applyInternalConfiguration(entry, builder, componentRegistry);
        
    }
    
    protected String getView(View viewAnnotation, ControllerBuilder controller,
        ComponentRegistry componentRegistry){
        
        boolean rendered = viewAnnotation == null? true : viewAnnotation.rendered();
        boolean resolved = viewAnnotation == null? false : viewAnnotation.resolved();
        
        String view = 
            viewAnnotation == null ||StringUtil.isEmpty(viewAnnotation.value())?
                null :
                StringUtil.adjust(viewAnnotation.value());
        
        
        if(rendered){
            if(resolved)
                return view;
            else
                return createControllerView(controller, componentRegistry, viewAnnotation.value());
        }
        else
            return null;
    }
    
    protected String createControllerView(ControllerBuilder controller,
            ComponentRegistry componentRegistry, String view){
        
        return applicationContext
                .getViewResolver()
                .getView(controller, null, null, view);
    }
    
    protected void addProperties(ControllerBuilder controllerBuilder, 
            ComponentRegistry componentRegistry, Class clazz){
    
        BeanInstance instance = new BeanInstance(null,clazz);
        List props = instance.getProperties();
        for(int i=0;i<props.size();i++){
            BeanProperty prop = (BeanProperty) props.get(i);
            super.applyInternalConfiguration(new BeanPropertyAnnotationImp(prop), 
                    controllerBuilder, componentRegistry);
        }
    }
    
    private void addAbstractActions( ControllerBuilder controllerBuilder, 
            ComponentRegistry componentRegistry, Class clazz, 
            List<ActionEntry> actionList ){

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
                        action.view(),
                        action.dispatcher(),
                        true);
                
                actionList.add(entry);
            }
        }
        
    }
    
    private void addActions( ControllerBuilder controllerBuilder, 
            ComponentRegistry componentRegistry, Class clazz, 
            List<ActionEntry> actionList ){

        Actions actions = 
                (Actions)clazz.getAnnotation(Actions.class);
        
        Action action = 
                (Action)clazz.getAnnotation(Action.class);
        
        List<Action> all = 
            new ArrayList<Action>();
        
        if(actions != null)
                all.addAll(Arrays.asList(actions.value()));
        
        if(action!= null)
            all.add(action);
        
        for(Action act: all){
            for(String id: act.value()){
                if(StringUtil.isEmpty(id))
                    throw new BrutosException("invalid action: " + id);
                
                if(StringUtil.isEmpty(act.view().value()))
                    throw new BrutosException("view must be informed: " + id);
                
                ActionEntry entry = 
                    new ActionEntry(
                        id,
                        null,
                        controllerBuilder.getClassType(),
                        null,
                        null,
                        null,
                        null,
                        act.view().value(),
                        act.view().dispatcher(),
                        true);
                
                actionList.add(entry);
            }
        }
        
    }
    
    protected void addActions( ControllerBuilder controllerBuilder, 
            ComponentRegistry componentRegistry, Class clazz ){
        
        List<ActionEntry> actionList = new ArrayList<ActionEntry>();
        
        addAbstractActions( controllerBuilder, componentRegistry, clazz, 
                actionList );
        
        addActions( controllerBuilder, componentRegistry, clazz, 
                actionList );
        
        Method[] methods = clazz.getMethods();

        for( Method m: methods ){
            ActionEntry entry = new ActionEntry(m);
            actionList.add(entry);
        }
        
        for( ActionEntry m: actionList ){
            super.applyInternalConfiguration(m, controllerBuilder, componentRegistry);
        }
    }

    protected void importBeans(ControllerBuilder controllerBuilder, 
            ComponentRegistry componentRegistry, Class clazz){
    
        ImportBeans beans = (ImportBeans) clazz.getAnnotation(ImportBeans.class);
        
        if(beans != null){
            for(Class bean: beans.value()){
                ImportBeanEntry beanEntry = new ImportBeanEntry(bean);
                super.applyInternalConfiguration(beanEntry, controllerBuilder, componentRegistry);
            }
        }
    }
    
    protected String getControllerName(ComponentRegistry componentRegistry, 
            Class controllerClass){
        String id = 
            controllerClass.getSimpleName()
                .replaceAll("Controller$", "");
        
        id = 
            (AnnotationUtil.isWebApplication(componentRegistry)? "/" : "") + id;
        
        return id;
    }
    
    protected String getControllerId(ComponentRegistry componentRegistry, 
            Controller annotation, Class controllerClass){
        boolean hasControllerId = 
            annotation != null && annotation.value().length > 0 && 
            !StringUtil.isEmpty(annotation.value()[0]);
        
        return hasControllerId? annotation.value()[0] : getControllerName(componentRegistry, controllerClass);
    }
    
    public boolean isApplicable(Object source) {
        return source instanceof Class && AnnotationUtil.isController((Class)source);
    }
    
}
