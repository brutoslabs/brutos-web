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

import java.lang.annotation.Annotation;
import java.util.*;
import org.brandao.brutos.*;
import org.brandao.brutos.annotation.configuration.*;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;

/**
 * Classe que permite a configuração de uma aplicação usando 
 * anotações e "Convention over configuration".
 * <p>Para que a configuração seja carregada em uma 
 * aplicação web, é necessário a utilização das tags 
 * <b><code>&lt;context:annotation-config/&gt;</code></b> e 
 * <b><code>&lt;context:component-scan/&gt;</code></b> no 
 * arquivo de configuração brutos-config.xml</p>
 * 
 * @author Afonso Brandao
 */
public class AnnotationApplicationContext extends AbstractApplicationContext{
    
    private Class[] allClazz;
    private List<Class> annotationConfig;
    private List<Class> compositeClassList;
    private Logger logger;
    
    /**
     * Cria uma nova aplicação.
     * @param clazz Componentes da aplicação.
     */
    public AnnotationApplicationContext(Class[] clazz) {
        this.allClazz = clazz;
        defineLogger();
    }

    /**
     * Cria uma nova aplicação.
     * @param clazz Componentes da aplicação.
     * @param parent Aplicação.
     */
    public AnnotationApplicationContext(Class[] clazz,
            ApplicationContext parent) {
        super(parent);
        this.allClazz = clazz;
        defineLogger();
    }

    private void defineLogger(){
        this.logger = LoggerProvider
                .getCurrentLoggerProvider().getLogger(AnnotationApplicationContext.class);
    }
    
    @Override
    public void configure( Properties config ) {
        super.configure(config);
        postConfigure();
    }

    private void postConfigure(){
        
        loadDefaultAnnotationConfig();
        
        compositeClassList = new ArrayList<Class>();
        
        compositeClassList.addAll(this.annotationConfig);
        compositeClassList.addAll(Arrays.asList(this.allClazz));
        
        AnnotationConfig rootAnnotationConfig = 
                createAnnotationTree(compositeClassList);
        
        List<Object> source = new ArrayList<Object>();
        source.addAll(Arrays.asList(this.allClazz));
        
        AnnotationConfig init = 
                new StartConfiguration((ApplyAnnotationConfig)rootAnnotationConfig);
        
        init.applyConfiguration(source, compositeClassList, this);
    }
    
    @Override
    public void destroy(){
    }

    protected AnnotationConfig createAnnotationTree(List<Class> list) {
        
        logger.info("creating configuration route");
        
        Map<Class,AnnotationConfigEntry> configMap = getAnnotationConfigEntry(list);
        List<AnnotationConfigEntry> root = new LinkedList<AnnotationConfigEntry>();
        
        AnnotationConfigEntry config = null;
        
        for(AnnotationConfigEntry ace: configMap.values()){
            Stereotype stereotype = ace.getStereotype();
            
            if(stereotype.target() == Configuration.class){
                ace.setNextAnnotationConfig(root);
                config = ace;
                continue;
            }

            Class<? extends Annotation>[] after = stereotype.executeAfter();
            
            for(Class<? extends Annotation> an: after){
                if(an == Configuration.class){
                    logger.warn("property after ignored: " + ace.getClass());
                    continue;
                }
                    
                AnnotationConfigEntry afterEntry = configMap.get(an);
                
                if(afterEntry == null)
                    throw new BrutosException("not found: " + an.getName());
                
                afterEntry.getNextAnnotationConfig().add(ace);
            }
            
            if(after.length == 0)
                root.add(ace);
        }
        
        if(config == null)
            throw new BrutosException("not found: @" + Configuration.class.getName());
        
        printConfigurationRoute(config);
        return config.getAnnotationConfig();
    }

    protected Map<Class,AnnotationConfigEntry> getAnnotationConfigEntry(List<Class> list){
        Map<Class,AnnotationConfigEntry> map = 
                new HashMap<Class,AnnotationConfigEntry>();

        for(Class clazz: list){
            
            if(!clazz.isAnnotationPresent(Stereotype.class) || 
               !AnnotationConfig.class.isAssignableFrom(clazz))
                continue;
            
            Stereotype st = 
                    (Stereotype) clazz.getAnnotation(Stereotype.class);
            
            AnnotationConfigEntry current = map.get(st.target());
            AnnotationConfigEntry newConfig = getAnnotationConfig(st, clazz);
            
            if(current != null){
                boolean override = 
                    newConfig.getStereotype().majorVersion() > current.getStereotype().majorVersion() ||
                    (newConfig.getStereotype().majorVersion() == current.getStereotype().majorVersion() &&
                    newConfig.getStereotype().minorVersion() > current.getStereotype().minorVersion());
                if( override )
                    map.put(st.target(), newConfig);
            }
            else
                map.put(st.target(), newConfig);
        }
        
        return map;
    }

    protected AnnotationConfigEntry getAnnotationConfig(Stereotype stereotype, Class clazz) {
        
        try{
            AnnotationConfig ac = (AnnotationConfig) ClassUtil.getInstance(clazz);
            AnnotationConfigEntry r = new AnnotationConfigEntry();
            r.setAnnotationConfig(ac);
            ac.setConfiguration(r);
            r.setStereotype(stereotype);
            r.setNextAnnotationConfig(new LinkedList<AnnotationConfigEntry>());

            Class<?> sourceConverterClass = stereotype.sourceConverter();
            
            if(sourceConverterClass != Converter.class){
                Converter valueConverter = 
                        (Converter)ClassUtil.getInstance(sourceConverterClass);
                ac.setSourceConverter(valueConverter);
            }
            
            return r;
        }
        catch( Exception e ){
            throw new BrutosException(e);
        }
    }
    
    protected void loadDefaultAnnotationConfig(){
        annotationConfig = new ArrayList<Class>();
        
        annotationConfig.add(RootAnnotationConfig.class);
        annotationConfig.add(ActionAnnotationConfig.class);
        annotationConfig.add(InterceptsStackAnnotationConfig.class);
        //annotationConfig.add(ActionParamAnnotationConfig.class);
        annotationConfig.add(BeanAnnotationConfig.class);
        annotationConfig.add(KeyCollectionAnnotationConfig.class);
        annotationConfig.add(ElementCollectionAnnotationConfig.class);
        annotationConfig.add(ControllerAnnotationConfig.class);
        annotationConfig.add(InterceptedByAnnotationConfig.class);
        annotationConfig.add(InterceptsAnnotationConfig.class);
        //annotationConfig.add(PropertyAnnotationConfig.class);
        annotationConfig.add(RestrictionAnnotationConfig.class);
        annotationConfig.add(RestrictionsAnnotationConfig.class);
        annotationConfig.add(ThrowSafeAnnotationConfig.class);
        annotationConfig.add(ThrowSafeListAnnotationConfig.class);
        annotationConfig.add(TypeDefAnnotationConfig.class);
        annotationConfig.add(IdentifyAnnotationConfig.class);
    }
    
    private void printConfigurationRoute(AnnotationConfigEntry root){
        nextPart(root, new StringBuilder());
    }
    
    private void nextPart(AnnotationConfigEntry entry, StringBuilder prefix){
        
        prefix.append(entry.getStereotype().target().getSimpleName());
        
        for(AnnotationConfigEntry next: entry.getNextAnnotationConfig()){
            String node =
                entry.getStereotype().target().getSimpleName() +
                " -> " +
                next.getStereotype().target().getSimpleName();
            
            if(prefix.indexOf(node) != -1)
                continue;
            
            nextPart(next,new StringBuilder(prefix).append(" -> "));
        }
        
        if(entry.getNextAnnotationConfig().isEmpty())
            logger.info("route config detected: " + prefix);
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
                ConfigurableApplicationContext applicationContext) {
            return super.applyInternalConfiguration(source, builder, applicationContext);
        }
        
    }
}
