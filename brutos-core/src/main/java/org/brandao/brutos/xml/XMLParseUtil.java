

package org.brandao.brutos.xml;

import java.util.ArrayList;
import org.brandao.brutos.mapping.StringUtil;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class XMLParseUtil {

    private String namespace;
    
    public XMLParseUtil(){
        this(null);
    }

    public XMLParseUtil(String namespace){
        this.namespace = namespace;
    }
    
    public Element getElement( Element e, String name ){
        NodeList elements = 
                e.getElementsByTagNameNS(
                    this.namespace == null? "*" : this.namespace, name);
        
        for(int i=0;i<elements.getLength();i++){
            Element c = (Element) elements.item(i);
            if( c.getParentNode().equals(e) )
                return (Element)elements.item(0);
        }

        return null;
    }

    public NodeList getElements( Element e, String name ){
        CustomNodeList list = new CustomNodeList();

        NodeList es = e.getElementsByTagNameNS(
                    this.namespace == null? "*" : this.namespace, name);
        
        for( int i=0;i<es.getLength();i++ ){
            Element c = (Element) es.item(i);
            if( c.getParentNode().equals(e) )
                list.add(c);
        }
        
        return list;
    }

    public String getAttribute(Element e, String name){
        String value = e.getAttribute(name);
        
        if( StringUtil.isEmpty(value))
            return null;
        else
            return value;
    }

    private static class CustomNodeList
            extends ArrayList implements NodeList{

        public Node item(int index) {
            return (Node) get(index);
        }

        public int getLength() {
            return size();
        }

    }
}
