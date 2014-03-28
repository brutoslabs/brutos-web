/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.brandao.brutos.annotation;

import java.util.List;
import org.brandao.brutos.DefinitionReader;
import org.brandao.brutos.web.AbstractWebApplicationContext;

/**
 *
 * @author Cliente
 */
public class AbstractAnnotationWebApplicationContext 
    extends AbstractWebApplicationContext{
    
    private ComponentScannerDefinitionReader componentScannerDefinitionReader;
    
    
    public AbstractAnnotationWebApplicationContext(){
        this.componentScannerDefinitionReader = 
                new ComponentScannerDefinitionReader(this);
    }

    @Override
    public void loadDefinitions(
            DefinitionReader definitionReader ){

        if( getResources() != null)
            definitionReader.loadDefinitions(getResources());
        
        if( getLocations() != null)
            definitionReader.loadDefinitions(getLocations());
        
        this.componentScannerDefinitionReader.loadDefinitions();
    }
    
    public void flush(){

        this.initLogger();
        
        this.overrideConfig();
        
        this.init();
        
        this.initScopes();
        
        super.loadDefinitions(this.componentScannerDefinitionReader);
        
        this.initControllers();

        this.initUploadListener();
        
        this.initRequestParser();
    }
    
}
