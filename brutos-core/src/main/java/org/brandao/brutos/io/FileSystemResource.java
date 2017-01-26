

package org.brandao.brutos.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class FileSystemResource extends AbstractResource{

    private String path;

    private File file;
    
    public FileSystemResource( String path ){
        this.path = path;
        this.file = new File(path);
    }
    
    public URL getURL() throws IOException {
        return
            new URL( ResourceLoader.FILE_URL_PREFIX
                + this.file.getAbsolutePath() );
    }

    public Resource getRelativeResource(String relativePath) throws IOException {
        return new FileSystemResource(
                this.createRelativePath(this.path, relativePath) );
    }

    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.file);
    }

    public boolean exists() {
        return this.file.exists();
    }

    public boolean equals( Object e ){
        return e instanceof FileSystemResource?
            ((FileSystemResource)e).path.equals( this.path ) :
            false;
    }

    public String getName() {
        return this.path;
    }

}
