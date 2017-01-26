

package org.brandao.brutos.scanner.vfs;

import java.util.ArrayList;
import java.util.List;


public class SystemPath implements Dir{

    private java.io.File root;
    
    public SystemPath(java.io.File root){
        if(!isValid(root))
            throw new VfsException("can't open path: " + root);
        this.root = root;
    }

    private boolean isValid(java.io.File root){
        return root != null && root.exists() 
            && root.isDirectory() && root.canRead();

    }
    
    public File[] getFiles() {
        List vFiles = new ArrayList();
        
        listFiles(vFiles, root);
        
        File[] result = new File[vFiles.size()];
        
        for(int i=0;i<result.length;i++)
            result[i] = (File)vFiles.get(i);
        
        return result;
    }

    public void listFiles(List vFiles, java.io.File file) {
        
        java.io.File[] files = file.listFiles();
        
        for(int i=0;i<files.length;i++){
            
            java.io.File child = files[i];
            
            if(child.isDirectory())
                listFiles(vFiles, child);
            else
                vFiles.add( new SystemFile(this,child));
        }
        
    }
    
    public String getPath() {
        return root.getPath().replace("\\", "/").replaceAll("/+", "/");
    }
    
}
