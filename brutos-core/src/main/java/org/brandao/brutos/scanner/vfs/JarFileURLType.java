package org.brandao.brutos.scanner.vfs;

import java.net.URL;
import java.util.jar.JarFile;


public class JarFileURLType implements URLType{

    public Dir toDir(URL url) throws Exception {
        return new ZipDir(
            Vfs.getRelativePath(url),
            new JarFile(Vfs.toFile(url)));
    }

    public boolean matches(URL url) throws Exception {
        return url.getProtocol().equals("file") 
                && url.toExternalForm().contains(".jar");
    }
    
}
