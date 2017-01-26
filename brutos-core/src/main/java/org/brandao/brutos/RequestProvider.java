

package org.brandao.brutos;


public class RequestProvider {

    private static final ThreadLocal requests;
    
    static{
        requests = new ThreadLocal();
    }
    
    private MvcRequestFactory factory;
    
    public MvcRequest start(){
        MvcRequest current = (MvcRequest) requests.get();
        MvcRequest request = this.factory.getRequest();
        requests.set(request);
        return current;
    }
    
    public static MvcRequest getRequest(){
        return (MvcRequest) requests.get();
    }

    public void destroy(MvcRequest old){
        if(old == null)
            requests.remove();
        else
            requests.set(old);
    }

    
    public MvcRequestFactory getFactory() {
        return factory;
    }

    
    public void setFactory(MvcRequestFactory factory) {
        this.factory = factory;
    }

}
