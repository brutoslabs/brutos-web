

package org.brandao.brutos;

import org.brandao.brutos.io.FileSystemResource;
import org.brandao.brutos.io.Resource;


public class FileSystemXMLApplicationContext
        extends AbstractXMLApplicationContext{

    private Resource[] resources;

    public FileSystemXMLApplicationContext( String[] locations,
            AbstractApplicationContext parent ){
        super( parent );

        resources = new Resource[locations.length];
        for( int i=0;i<locations.length;i++ )
            resources[i] = new FileSystemResource( locations[i] );
    }

    public FileSystemXMLApplicationContext( String[] locations ){
        this(locations,null);
    }

    public FileSystemXMLApplicationContext( String location ){
        this(new String[]{location}, null);
    }

    protected Resource getContextResource( String path ){
        return new FileSystemResource( path );
    }

    protected Resource[] getContextResources() {
        return resources;
    }

}
