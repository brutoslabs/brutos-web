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


package org.brandao.brutos.type;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.ConfigurableResultAction;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.RenderView;
import org.brandao.brutos.RequestInstrument;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.StackRequestElement;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.scope.Scope;

/**
 *
 * @author Brandao
 */
public class ResultActionType extends AbstractType{

    private final ConcurrentMap cache;
    
    public ResultActionType(){
        this.cache = new ConcurrentHashMap();
    }
    
    public Object convert(Object value) {
        return value;
    }

    public void show(MvcResponse response, Object value) throws IOException {
        
        ConfigurableResultAction resultAction = (ConfigurableResultAction)value;
        ConfigurableApplicationContext context = 
                    (ConfigurableApplicationContext) Invoker.getCurrentApplicationContext();
        
        Map infos  = resultAction.getInfos();
        Map values = resultAction.getValues();
        Scope requestScope = context.getScopes().get(ScopeType.REQUEST.toString());
        
        for(Object key: infos.keySet()){
            response.setInfo((String)key, (String)infos.get(key));
        }
        
        for(Object key: values.keySet()){
            requestScope.put((String)key, values.get(key));
        }
        
        Object content = resultAction.getContent();
        
        if(content != null){
            Type contentType = this.getContentType(
                    resultAction.getContentType(), context);
            contentType.show(response, content);
        }
        else{
            RenderView renderView = context.getRenderView();
            Invoker invoker = context.getInvoker();
            RequestInstrument requestinstrument = 
                    invoker.getRequestInstrument();
            StackRequestElement stackRequestElement = 
                    invoker.getStackRequest(requestinstrument).getCurrent();
            
            String view = resultAction.getView();
            boolean resolved = resultAction.isResolvedView();
            
            Action action = stackRequestElement.getAction().getMethodForm();
            
            view = resolved? 
                    view :
                    context
                        .getViewResolver()
                        .getActionView(
                            action.getController().getClassType(), 
                            action.getExecutor(), 
                            view);
            
            stackRequestElement.setView(view);
            renderView.show(requestinstrument, stackRequestElement);
        }
    }
    
    private Type getContentType(Class contentType, ConfigurableApplicationContext context){
        Type type = (Type) this.cache.get(contentType);
        
        if(type != null)
            return type;
        else{
            synchronized(this){
                type = (Type) this.cache.get(contentType);
                if(type != null)
                    return type;
                
                TypeManager typeManager = context.getTypeManager();
            
                type = typeManager.getType(contentType);
                
                if(contentType == null)
                    throw new UnknownTypeException(contentType.getName());
                
                this.cache.put(contentType, type);
                return type;
            }
        }
    }
    
}
