

package org.brandao.brutos;

import java.io.InputStream;
import org.brandao.brutos.io.InputStreamResource;
import org.brandao.brutos.io.Resource;


public class InputStreamXMLApplicationContext
        extends AbstractXMLApplicationContext{

    private Resource[] resources;

    public InputStreamXMLApplicationContext( InputStream[] inputs,
            AbstractApplicationContext parent ){
        super( parent );

        resources = new Resource[inputs.length];
        for( int i=0;i<inputs.length;i++ )
            resources[i] = new InputStreamResource( inputs[i] );
    }

    public InputStreamXMLApplicationContext( InputStream[] inputs ){
        this(inputs,null);
    }

    public InputStreamXMLApplicationContext( InputStream input ){
        this(new InputStream[]{input}, null);
    }

    protected Resource[] getContextResources() {
        return resources;
    }

}
