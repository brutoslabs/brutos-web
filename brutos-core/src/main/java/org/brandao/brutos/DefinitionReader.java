

package org.brandao.brutos;

import org.brandao.brutos.io.Resource;
import org.brandao.brutos.io.ResourceLoader;


public interface DefinitionReader {

    void loadDefinitions( Resource resource );

    void loadDefinitions( Resource[] resource );

    void loadDefinitions( String[] locations );

    void loadDefinitions( String location );

    ResourceLoader getResourceLoader();
    
}
