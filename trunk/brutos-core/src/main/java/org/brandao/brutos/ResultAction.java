

package org.brandao.brutos;


public interface ResultAction {
    
    
    ResultAction setView(String view);

    
    ResultAction setView(String view, boolean resolved);

    
    ResultAction setContentType(Class type);
    
    
    ResultAction addInfo(String name, String o);
    
    
    ResultAction setContent(Object value);
    
    
    ResultAction add(String name, Object o);
    
}
