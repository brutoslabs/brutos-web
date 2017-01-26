


package org.brandao.brutos;


public class ResponseProvider {

    private static final ThreadLocal responses;

    static{
        responses = new ThreadLocal();
    }
    
    private MvcResponseFactory factory;
    
    public MvcResponse start(){
        MvcResponse current = (MvcResponse) responses.get();
        MvcResponse response = this.factory.getResponse();
        responses.set(response);
        return current;
    }
    
    public static MvcResponse getResponse(){
        return (MvcResponse) responses.get();
    }

    public void destroy(MvcResponse old){
        if(old == null)
            responses.remove();
        else
            responses.set(old);
    }

    
    public MvcResponseFactory getFactory() {
        return factory;
    }

    
    public void setFactory(MvcResponseFactory factory) {
        this.factory = factory;
    }

}
