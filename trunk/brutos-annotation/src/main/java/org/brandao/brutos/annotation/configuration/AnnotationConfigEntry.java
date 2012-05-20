/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.brandao.brutos.annotation.configuration;

import org.brandao.brutos.annotation.AnnotationConfig;

/**
 *
 * @author Brandao
 */
public class AnnotationConfigEntry {
    
    private AnnotationConfig annotationConfig;

    private AnnotationConfigEntry nextAnnotationConfig;
    
    public AnnotationConfig getAnnotationConfig() {
        return annotationConfig;
    }

    public void setAnnotationConfig(AnnotationConfig annotationConfig) {
        this.annotationConfig = annotationConfig;
    }

    public AnnotationConfigEntry getNextAnnotationConfig() {
        return nextAnnotationConfig;
    }

    public void setNextAnnotationConfig(AnnotationConfigEntry nextAnnotationConfig) {
        this.nextAnnotationConfig = nextAnnotationConfig;
    }
    
}
