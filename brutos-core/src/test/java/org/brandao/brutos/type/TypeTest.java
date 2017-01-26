


package org.brandao.brutos.type;

import java.util.List;
import junit.framework.TestCase;
import org.brandao.brutos.web.http.ParameterList;


public class TypeTest  extends TestCase{

    public void testGetClassType(){
        Class type = TypeUtil.getRawType(int.class);
        TestCase.assertEquals(int.class,type);
    }

    public void testgetRawType(){
        GenericTypeImp genericType =
                new GenericTypeImp(List.class,new Class[]{Integer.class});
        Class type = TypeUtil.getRawType(genericType);
        TestCase.assertEquals(List.class, type);
    }

    public void testgetListType(){
        GenericTypeImp genericType =
                new GenericTypeImp(List.class,new Class[]{Integer.class});
        ListType type = (ListType) (new TypeManagerImp()).getType(genericType);
        TestCase.assertEquals(Integer.class, type.getCollectionType().getClassType());
    }

}
