


package org.brandao.brutos.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class AbstractResourceTestImp extends AbstractResource{

    private String path;

    public AbstractResourceTestImp( String path, String relativePath ){
        this.path = super.createRelativePath(path, relativePath);
    }

    public URL getURL() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Resource getRelativeResource(String relativePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean exists() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public InputStream getInputStream() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getPath() {
        return path;
    }

}
