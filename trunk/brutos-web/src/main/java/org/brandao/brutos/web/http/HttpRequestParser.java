

package org.brandao.brutos.web.http;

import java.io.IOException;
import java.util.Properties;


public interface HttpRequestParser {

    
    boolean isMultipart( BrutosRequest request, 
            UploadListener uploadListener ) throws IOException;
    
    
    void parserMultipart( BrutosRequest request, Properties config,
            UploadListener uploadListener ) throws IOException;

    
    void parserContentType( BrutosRequest request, 
            String contentType ) throws IOException;

    
    UploadEvent getUploadEvent( BrutosRequest request );
    
}
