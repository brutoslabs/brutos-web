/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.brandao.brutos.annotation.web;

import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.web.AbstractWebApplicationContext;

/**
 *
 * @author Cliente
 */
public class AnnotationWebApplicationContext 
    extends AbstractWebApplicationContext{
    
    public AnnotationWebApplicationContext(){
    }

    @Override
    protected void loadDefinitions(ComponentRegistry registry) {
        AnnotationDefinitionReader definitionReader =
                new AnnotationDefinitionReader(registry);
        
        if(this.getLocations() != null)
            definitionReader.loadDefinitions(locations);
        
        if(this.getResources() != null)
            definitionReader.loadDefinitions(resources);
        
        definitionReader.loadDefinitions();
    }

    
}
