package org.brandao.brutos.scanner.vfs;

import java.net.URL;


public class SystemURLType implements URLType{

    public Dir toDir(URL url) throws Exception {
        return new SystemPath(Vfs.toFile(url));
    }

    public boolean matches(URL url) throws Exception {
        return 
            url.getProtocol().equals("file") 
            && !url.toExternalForm().contains(".jar");
    }

    
}
