/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.brandao.brutos.annotation;

import java.util.List;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.DefinitionReader;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.web.AbstractWebApplicationContext;
import static org.brandao.brutos.web.AbstractWebApplicationContext.contextConfigName;
import static org.brandao.brutos.web.AbstractWebApplicationContext.defaultConfigContext;
import org.brandao.brutos.xml.XMLComponentDefinitionReader;

/**
 *
 * @author Cliente
 */
public class AbstractAnnotationWebApplicationContext 
    extends AbstractWebApplicationContext{
    
    private ComponentScannerDefinitionReader annotationComponentDefinitionReader;
    
    
    public AbstractAnnotationWebApplicationContext(){
        this.annotationComponentDefinitionReader = 
                new ComponentScannerDefinitionReader(this);
    }
    
    public void loadDefinitions(
            ComponentScannerDefinitionReader definitionReader ){

        if( getResources() != null)
            definitionReader.loadDefinitions(getResources());
        
        if( getLocations() != null)
            definitionReader.loadDefinitions(getLocations());
        
        List<String> codeBase = definitionReader.get
    }
    
    public void flush(){

        this.initLogger();
        
        this.overrideConfig();
        
        this.init();
        
        this.initScopes();
        
        super.loadDefinitions(this.annotationComponentDefinitionReader);
        
        this.initControllers();

        this.initUploadListener();
        
        this.initRequestParser();
    }
    
}
