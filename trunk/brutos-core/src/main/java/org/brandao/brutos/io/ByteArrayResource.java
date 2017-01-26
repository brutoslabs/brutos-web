

package org.brandao.brutos.io;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class ByteArrayResource extends AbstractResource{

    private byte[] byteArray;

    public ByteArrayResource( byte[] byteArray ){
        this.byteArray = byteArray;
    }

    public URL getURL() throws IOException {
            throw new FileNotFoundException  (
                 " URL does not exist");
    }

    public Resource getRelativeResource(String relativePath) throws IOException {
        throw new FileNotFoundException(
            "Cannot create a relative resource: " + relativePath );
    }

    public boolean exists() {
        return true;
    }

    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream( byteArray );
    }

    public boolean equals( Object e ){
        return e instanceof ByteArrayResource?
            ((ByteArrayResource)e).byteArray.hashCode() == this.byteArray.hashCode() :
            false;
    }

    public String getName() {
        return "Byte array";
    }

}
