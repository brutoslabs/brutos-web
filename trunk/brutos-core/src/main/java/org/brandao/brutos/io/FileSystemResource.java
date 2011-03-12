/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * This library is free software. You can redistribute it
 * and/or modify it under the terms of the GNU General Public
 * License (GPL) version 3.0 or (at your option) any later
 * version.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/gpl.html
 *
 * Distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 *
 */
package org.brandao.brutos.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 *
 * @author Brandao
 */
public class FileSystemResource extends AbstractResource{

    private String path;

    private File file;
    
    public FileSystemResource( String path ){
        this.path = path;
        this.file = new File(path);
    }
    
    public URL getURL() throws IOException {
        return
            new URL( AbstractResource.FILE_URL_PREFIX
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

}
