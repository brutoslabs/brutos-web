

package org.brandao.brutos;

import org.brandao.brutos.io.Resource;
import org.brandao.brutos.xml.XMLComponentDefinitionReader;

public abstract class AbstractXMLApplicationContext
        extends AbstractApplicationContext{

	
    public AbstractXMLApplicationContext( AbstractApplicationContext parent ){
        super(parent);
    }

    
    public AbstractXMLApplicationContext(){
        this( null );
    }

    
    protected void loadDefinitions(
            ComponentRegistry registry ){

        XMLComponentDefinitionReader definitionreader
                = new XMLComponentDefinitionReader(registry);
        
        Resource[] resources = getContextResources();
        
        if( resources != null)
            definitionreader.loadDefinitions(resources);
    }

    
    protected abstract Resource[] getContextResources();
    
}
