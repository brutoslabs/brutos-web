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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.annotation.AnnotationConfig;
import org.brandao.brutos.annotation.Stereotype;

/**
 *
 * @author Brandao
 */
public class RootAnnotationConfig implements AnnotationConfig{

    public boolean isApplicable(Object source) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object applyConfiguration(Object source, Object builder, ConfigurableApplicationContext applicationContext) {
        List<AnnotationConfig> sourceList = (List<AnnotationConfig>)source;
        List<AnnotationConfigEntry> list = 
                new LinkedList<AnnotationConfigEntry>();
        Map<String,AnnotationConfig> map = 
                new HashMap<String,AnnotationConfig>();
        
        for(AnnotationConfig an: sourceList){
            Stereotype st = 
                    an.getClass().getAnnotation(Stereotype.class);
            String key = st.target().getSimpleName();
            

        }
        
        return null;
    }
    
}
