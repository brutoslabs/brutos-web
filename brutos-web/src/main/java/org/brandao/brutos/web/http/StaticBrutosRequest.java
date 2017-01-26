

package org.brandao.brutos.web.http;

import javax.servlet.ServletRequest;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.WebApplicationContext;


public class StaticBrutosRequest extends BrutosRequestWrapper{

    public StaticBrutosRequest(ServletRequest request){
        super( getBrutosRequest(request) );
    }

    private static BrutosRequest getBrutosRequest( ServletRequest request ){
        try{
            BrutosRequest brutosRequest =
                    createBrutosRequest(
                        ContextLoader.getCurrentWebApplicationContext(),
                        request );
            return brutosRequest;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    private static BrutosRequest createBrutosRequest(WebApplicationContext app,
            ServletRequest servletRequest){

        Class clazz = getBrutosRequestClass(app);

        if(BrutosRequest.class.isAssignableFrom(clazz)){
            try{
                BrutosRequest request =
                    (BrutosRequest)clazz
                        .getConstructor(ServletRequest.class)
                            .newInstance(servletRequest);
                return request;
            }
            catch( Exception e ){
                throw new BrutosException("unable to create instance: " +
                        clazz.getName(),e);
            }
        }
        else
            throw new BrutosException("request is not valid:"+
                    clazz.getName());
    }

    private static Class getBrutosRequestClass(WebApplicationContext app){
        String brutosRequestClassName =
                app.getConfiguration().getProperty(
                    "org.brandao.brutos.web.request",
                    BrutosRequestImp.class.getName()
                );

        return getBrutosRequestClass(brutosRequestClassName);
    }

    private static Class getBrutosRequestClass( String contextClassName ){
        try {
            return Thread.currentThread().getContextClassLoader()
                    .loadClass(contextClassName);
        } catch (ClassNotFoundException ex) {
            throw new BrutosException( "Failed to load: " + contextClassName, ex );
        }
    }

}
