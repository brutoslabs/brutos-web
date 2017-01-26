

package org.brandao.brutos.web.test;

import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;


public interface WebApplicationTester {
    
    void prepareContext(Map<String,String> parameters);

    void prepareRequest(Map<String,String> parameters);
    
    void prepareSession(Map<String,String> parameters);
    
    void checkException(Throwable e) throws Throwable;
    
    void checkResult(HttpServletRequest request, 
                HttpServletResponse response, 
                ServletContext context,
                ConfigurableWebApplicationContext applicationContext);
    
}
