

package org.brandao.brutos;

import java.util.Map;


public interface ConfigurableResultAction extends ResultAction{

    String getView();
    
    boolean isResolvedView();

    Class getContentType();
    
    Object getContent();
    
    Map getValues();

    void setValues(Map values);

    Map getInfos();

    void setInfos(Map infos);
    
}
