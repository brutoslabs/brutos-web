package org.brandao.brutos.scanner.vfs;

import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class JarURLType implements URLType{

    public Dir toDir(URL url) throws Exception {
        URLConnection urlConnection = url.openConnection();
        if(urlConnection instanceof JarURLConnection){
            return new ZipDir(
                Vfs.getRelativePath(url),
                ((JarURLConnection) urlConnection).getJarFile());
        }
        else
            return null;
    }

    public boolean matches(URL url) throws Exception {
        return "jar".equals(url.getProtocol());
    }
    
}
