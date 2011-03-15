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

package org.brandao.brutos.web.http;

import java.io.File;

/**
 *
 * @author Afonso Brandao
 */
public class BrutosFile {
    
    private File file = null;
    private String contentType;
    private String fileName;
    
    public BrutosFile( File file, String contentType ) {
        this( file, contentType, null );
    }
    
    public BrutosFile( File file ) {
        this( file, null, null );
    }
    
    public BrutosFile( File file, String contentType, String fileName ) {
        setFile(file);
        setContentType(contentType);
        setFileName(fileName);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
}
