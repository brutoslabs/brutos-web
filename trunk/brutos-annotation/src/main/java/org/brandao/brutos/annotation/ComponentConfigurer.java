/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.brandao.brutos.annotation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.annotation.configuration.AbstractAnnotationConfig;
import org.brandao.brutos.annotation.configuration.ActionAnnotationConfig;
import org.brandao.brutos.annotation.configuration.AnnotationConfigEntry;
import org.brandao.brutos.annotation.configuration.ApplyAnnotationConfig;
import org.brandao.brutos.annotation.configuration.BeanAnnotationConfig;
import org.brandao.brutos.annotation.configuration.ControllerAnnotationConfig;
import org.brandao.brutos.annotation.configuration.Converter;
import org.brandao.brutos.annotation.configuration.ElementCollectionAnnotationConfig;
import org.brandao.brutos.annotation.configuration.IdentifyAnnotationConfig;
import org.brandao.brutos.annotation.configuration.InterceptedByAnnotationConfig;
import org.brandao.brutos.annotation.configuration.InterceptsAnnotationConfig;
import org.brandao.brutos.annotation.configuration.InterceptsStackAnnotationConfig;
import org.brandao.brutos.annotation.configuration.KeyCollectionAnnotationConfig;
import org.brandao.brutos.annotation.configuration.RestrictionAnnotationConfig;
import org.brandao.brutos.annotation.configuration.RestrictionsAnnotationConfig;
import org.brandao.brutos.annotation.configuration.RootAnnotationConfig;
import org.brandao.brutos.annotation.configuration.ThrowSafeAnnotationConfig;
import org.brandao.brutos.annotation.configuration.ThrowSafeListAnnotationConfig;
import org.brandao.brutos.annotation.configuration.TypeDefAnnotationConfig;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;

/**
 *
 * @author Cliente
 */
public class ComponentConfigurer {
    
    private static final List<Class> defaultAnnotationConfig = new ArrayList<Class>();;
    
    static{
        defaultAnnotationConfig.add(RootAnnotationConfig.class);
        defaultAnnotationConfig.add(ActionAnnotationConfig.class);
        defaultAnnotationConfig.add(InterceptsStackAnnotationConfig.class);
        defaultAnnotationConfig.add(BeanAnnotationConfig.class);
        defaultAnnotationConfig.add(KeyCollectionAnnotationConfig.class);
        defaultAnnotationConfig.add(ElementCollectionAnnotationConfig.class);
        defaultAnnotationConfig.add(ControllerAnnotationConfig.class);
        defaultAnnotationConfig.add(InterceptedByAnnotationConfig.class);
        defaultAnnotationConfig.add(InterceptsAnnotationConfig.class);
        defaultAnnotationConfig.add(RestrictionAnnotationConfig.class);
        defaultAnnotationConfig.add(RestrictionsAnnotationConfig.class);
        defaultAnnotationConfig.add(ThrowSafeAnnotationConfig.class);
        defaultAnnotationConfig.add(ThrowSafeListAnnotationConfig.class);
        defaultAnnotationConfig.add(TypeDefAnnotationConfig.class);
        defaultAnnotationConfig.add(IdentifyAnnotationConfig.class);
    }
    
    private List<Class> componentList;
    
    private ConfigurableApplicationContext applicationContext;
    
    private Logger logger;
    
    public ComponentConfigurer(ConfigurableApplicationContext applicationContext){
        this.applicationContext = applicationContext;
        this.logger = LoggerProvider
                .getCurrentLoggerProvider().getLogger(AnnotationApplicationContext.class);
    }
    
    public void init(ComponentRegistry componentRegistry){
        List<Class> compositeClassList = new ArrayList<Class>();
        
        compositeClassList.addAll(defaultAnnotationConfig);
        compositeClassList.addAll(componentList);
        
        AnnotationConfig rootAnnotationConfig = 
                createAnnotationTree(compositeClassList);
        
        List<Object> source = new ArrayList<Object>();
        source.addAll(Arrays.asList(compositeClassList));
        
        AnnotationConfig init = 
                new StartConfiguration((ApplyAnnotationConfig)rootAnnotationConfig);
        
        init.applyConfiguration(source, compositeClassList, componentRegistry);
    }
    
    protected AnnotationConfig createAnnotationTree(List<Class> list) {
        
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
            ac.setApplicationContext(this.applicationContext);
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
    
    public List<Class> getComponentList() {
        return componentList;
    }

    public void setComponentList(List<Class> componentList) {
        this.componentList = componentList;
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
