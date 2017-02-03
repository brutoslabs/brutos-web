package org.brandao.brutos.scanner.vfs;

import java.net.URL;


public interface URLType {
    
    Dir toDir(URL url) throws Exception;
    
    boolean matches(URL url) throws Exception;
}
