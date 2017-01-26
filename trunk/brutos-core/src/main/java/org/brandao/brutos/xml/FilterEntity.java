

package org.brandao.brutos.xml;

import java.util.List;


public class FilterEntity {
    
    private String type;
    
    private List<String> expression;

    public FilterEntity(String type, List<String> expression) {
        this.type = type;
        this.expression = expression;
    }

    public List<String> getExpression() {
        return expression;
    }

    public void setExpression(List<String> expression) {
        this.expression = expression;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
