


package org.brandao.brutos.web;

import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.xml.XMLComponentDefinitionReader;


public class XMLWebApplicationContext
        extends AbstractWebApplicationContext{

    @Override
    public void loadDefinitions(ComponentRegistry registry){
    
        XMLComponentDefinitionReader definitionReader = 
                new XMLComponentDefinitionReader(this);
        
        if( super.resources != null)
            definitionReader.loadDefinitions(super.resources);
        
        if( super.locations != null)
            definitionReader.loadDefinitions(super.locations);
    }

}
