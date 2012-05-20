/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.brandao.brutos.annotation.configuration;

import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.annotation.AnnotationConfig;

/**
 *
 * @author Brandao
 */
public abstract class AbstractAnnotationConfig implements AnnotationConfig{

    private AnnotationConfigEntry annotation;
    
    public void setConfiguration(AnnotationConfigEntry annotation){
        this.annotation = annotation;
    }
    
    public Object applyInternalConfiguration(Object source, Object builder, 
            ConfigurableApplicationContext applicationContext) {

        AnnotationConfigEntry next = annotation.getNextAnnotationConfig();
        
        if(next.getAnnotationConfig().isApplicable(source))
            builder = next.getAnnotationConfig().applyConfiguration(source, builder, applicationContext);
        
        return builder;
    }

}
