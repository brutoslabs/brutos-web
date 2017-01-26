

package org.brandao.brutos.xml;

import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.DefinitionReader;
import org.brandao.brutos.io.ResourceLoader;


public abstract class AbstractDefinitionReader implements DefinitionReader{

    protected ComponentRegistry componentRegistry;

    public AbstractDefinitionReader(ComponentRegistry componenetRegistry){
        this.componentRegistry = componenetRegistry;
    }
 
    public ResourceLoader getResourceLoader() {
        return this.componentRegistry;
    }
    
}
