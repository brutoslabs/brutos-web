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

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 *
 * @author Brandao
 */
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

}
