

package org.brandao.brutos;

import java.util.Properties;


public interface PropertyRegistry {
    
    void registerProperty(String name, String value);
    
    String getProperty(String name);

    Properties getProperties();
    
}
