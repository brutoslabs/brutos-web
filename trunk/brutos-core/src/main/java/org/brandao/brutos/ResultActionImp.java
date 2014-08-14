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

package org.brandao.brutos;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Brandao
 */
public class ResultActionImp 
    implements ConfigurableResultAction{

    private ResultParam resultParam;
    
    private ResultTypeParam resultTypeParam;
    
    public ResultActionImp(){
        this.resultParam = null;
        this.resultTypeParam = null;
    }

    public ResultParam use() {
        return this.use(null, false);
    }
    
    public ResultParam use(String view) {
        return this.use(view, false);
    }

    public synchronized ResultParam use(String view, boolean resolved) {
        if(this.resultParam != null || this.resultTypeParam != null)
            throw new IllegalStateException();
        else{
            this.resultParam = new ResultParamImp(view, resolved, new HashMap(), new HashMap());
            return this.resultParam;
        }
    }

    public synchronized ResultTypeParam use(Class type) {
        if(this.resultParam != null || this.resultTypeParam != null)
            throw new IllegalStateException();
        else{
            this.resultTypeParam = new ResultTypeParamImp(type, new HashMap());
            return this.resultTypeParam;
        }
    }

    public ResultParam getResultParam() {
        return resultParam;
    }

    public void setResultParam(ResultParam resultParam) {
        this.resultParam = resultParam;
    }

    public ResultTypeParam getResultTypeParam() {
        return resultTypeParam;
    }

    public void setResultTypeParam(ResultTypeParam resultTypeParam) {
        this.resultTypeParam = resultTypeParam;
    }

    public static class ResultParamImp 
        implements ConfigurableResultParam {

        private String view;
        
        private boolean resolved;
        
        private Map values;
        
        private Map infos;
        
        public ResultParamImp(String view, boolean resolved, Map values,Map infos){
            this.resolved = resolved;
            this.view     = view;
            this.values   = values;
            this.infos    = infos;
        }
        
        public synchronized ResultParam include(String name, Object o) {
            if(this.values.put(name, o) != null)
                throw new IllegalStateException();
            else
                return this;
        }

        public synchronized ResultParam includeInfo(String name, String o) {
            if(this.infos.put(name, o) != null)
                throw new IllegalStateException();
            else
                return this;
        }
        
        public String getView() {
            return view;
        }

        public void setView(String view) {
            this.view = view;
        }

        public boolean isResolved() {
            return resolved;
        }

        public void setResolved(boolean resolved) {
            this.resolved = resolved;
        }

        public Map getValues() {
            return values;
        }

        public void setValues(Map values) {
            this.values = values;
        }

        public Map getInfos() {
            return infos;
        }

        public void setInfos(Map infos) {
            this.infos = infos;
        }

    }
    
    public static class ResultTypeParamImp 
        implements ConfigurableResultTypeParam {

        private Class type;
        
        private Object value;
        
        private Map infos;
        
        public ResultTypeParamImp(Class type, Map infos){
            this.type  = type;
            this.value = null;
            this.infos = infos;
        }
        
        public synchronized void include(Object o) {
            if(this.value != null)
                throw new IllegalStateException();
            else
                this.value = o;
        }

        public synchronized ResultTypeParam includeInfo(String name, String o) {
            if(this.infos.put(name, o) != null)
                throw new IllegalStateException();
            else
                return this;
        }
        
        public Class getType() {
            return type;
        }

        public void setType(Class type) {
            this.type = type;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Map getInfos() {
            return infos;
        }

        public void setInfos(Map infos) {
            this.infos = infos;
        }
        
    }
    
}
