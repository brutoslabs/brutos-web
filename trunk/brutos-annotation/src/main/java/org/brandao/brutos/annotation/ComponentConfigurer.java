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

import java.util.Arrays;
import java.util.List;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.annotation.configuration.AbstractAnnotationConfig;
import org.brandao.brutos.annotation.configuration.AnnotationConfigEntry;
import org.brandao.brutos.annotation.configuration.AnnotationUtil;
import org.brandao.brutos.annotation.configuration.ApplyAnnotationConfig;
import org.brandao.brutos.annotation.configuration.ConfigurationEntry;
import org.brandao.brutos.annotation.configuration.RootAnnotationConfig;
import org.brandao.brutos.annotation.scanner.Scanner;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.xml.FilterEntity;

/**
 *
 * @author Brandao
 */
public class ComponentConfigurer {
    
    private ConfigurationEntry configuration;
    
    private ConfigurableApplicationContext applicationContext;
    
    private Logger logger;
    
    public ComponentConfigurer(ConfigurableApplicationContext applicationContext){
        this.applicationContext = applicationContext;
        this.logger = LoggerProvider
                .getCurrentLoggerProvider().getLogger(AnnotationApplicationContext.class);
    }
    
    public void init(ComponentRegistry componentRegistry){
        
        if(this.configuration.getAllClazz() == null || this.configuration.getAllClazz().isEmpty()){
            ConfigurationEntry startConfig = new ConfigurationEntry();

            startConfig.setBasePackage(this.configuration.getBasePackage());
            startConfig.setUseDefaultfilter(false);

            FilterEntity filter = 
                new FilterEntity(
                        FilterType.ANNOTATION.getName(), 
                        Arrays.asList(new String[]{Configuration.class.getName()}));

            startConfig.setIncludeFilters(
                Arrays.asList(new FilterEntity[]{filter}));

            Scanner scanner = 
                    AnnotationUtil.createScanner(startConfig, null);
            scanner.scan();
            
            List<Class> configurationClass = this.configuration.getConfigClass();
            if(configurationClass == null){
                configurationClass = scanner.getClassList();
                this.configuration.setConfigClass(configurationClass);
            }
            else
                configurationClass.addAll(scanner.getClassList());
        }
        
        AnnotationConfig rootAnnotationConfig = AnnotationUtil
                .createAnnotationTree(
                        applicationContext, 
                        Arrays.asList( new Class[]{RootAnnotationConfig.class}));
                
        
        AnnotationConfig init = 
                new StartConfiguration((ApplyAnnotationConfig)rootAnnotationConfig);
        
        init.applyConfiguration(this.configuration, null, componentRegistry);
    }
    
    public ConfigurationEntry getConfiguration() {
        return configuration;
    }
    
    public void setConfiguration(ConfigurationEntry configuration) {
        this.configuration = configuration;
    }

    private class StartConfiguration extends AbstractAnnotationConfig{

        private ApplyAnnotationConfig root;
        
        public StartConfiguration(ApplyAnnotationConfig root){
            this.root = root;
            AnnotationConfigEntry entry = new AnnotationConfigEntry();
            entry.setAnnotationConfig(null);
            entry.setNextAnnotationConfig(Arrays.asList(root.getConfiguration()));
            super.setConfiguration(entry);
        }
        
        public boolean isApplicable(Object source) {
            return true;
        }

        public Object applyConfiguration(Object source, Object builder, 
                ComponentRegistry componentRegistry) {
            return super.applyInternalConfiguration(source, builder, componentRegistry);
        }
        
    }
    
}
