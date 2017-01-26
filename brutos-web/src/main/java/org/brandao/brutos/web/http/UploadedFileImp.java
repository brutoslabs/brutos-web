

package org.brandao.brutos.web.http;

import java.io.File;


public class UploadedFileImp implements UploadedFile{
    
    private File file = null;
    private String contentType;
    private String fileName;
    
    public UploadedFileImp( File file, String contentType ) {
        this( file, contentType, null );
    }
    
    public UploadedFileImp( File file ) {
        this( file, null, null );
    }
    
    public UploadedFileImp( File file, String contentType, String fileName ) {
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
