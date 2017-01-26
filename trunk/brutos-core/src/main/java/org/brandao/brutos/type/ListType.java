
package org.brandao.brutos.type;

import java.util.List;


public class ListType extends AbstractCollectionType{

    protected Class getCollectionClass() {
        return this.getClassType() == List.class?
                TypeUtil.getDefaultListType() : 
                this.getClassType();
    }
    
}
