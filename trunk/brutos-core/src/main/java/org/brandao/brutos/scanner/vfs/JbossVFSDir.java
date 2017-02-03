package org.brandao.brutos.scanner.vfs;

import java.util.jar.JarFile;


public class JbossVFSDir implements Dir{

    private Dir path;
    
    public JbossVFSDir(java.io.File file){
        
        try{
            if(file != null && file.exists() && file.canRead()){
                if(file.isDirectory())
                    this.path = new SystemPath(file);
                else
                    this.path = new ZipDir(null,new JarFile(file));
            }
            else
                throw new VfsException("can't open path: " + file);
        }
        catch(VfsException e){
            throw e;
        }
        catch(Exception e){
            throw new VfsException(e);
        }
    }
    
    public File[] getFiles() {
        return path.getFiles();
    }

    public String getPath() {
        return path.getPath();
    }
    
}
