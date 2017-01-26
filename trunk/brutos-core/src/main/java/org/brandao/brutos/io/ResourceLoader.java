

package org.brandao.brutos.io;


public interface ResourceLoader {

    String FILE_URL_PREFIX = "file:/";

    String CLASSPATH_URL_PREFIX = "classpath:";

    Resource getResource( String path );

    ClassLoader getClassloader();

}
