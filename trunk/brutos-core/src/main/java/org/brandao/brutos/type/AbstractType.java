

package org.brandao.brutos.type;


public abstract class AbstractType implements Type{
    
    protected Class classType;
    
    public Class getClassType(){
        return this.classType;
    }

    public void setClassType(Class value){
        this.classType = value;
    }

    public boolean isAlwaysRender(){
        return false;
    }
    
}
