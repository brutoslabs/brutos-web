/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.brandao.brutos.annotation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.annotation.scanner.DefaultScanner;
import org.brandao.brutos.annotation.scanner.Scanner;
import org.brandao.brutos.annotation.scanner.TypeFilter;
import org.brandao.brutos.mapping.StringUtil;
import static org.brandao.brutos.xml.ContextDefinitionReader.BaseScannerAnnotationClass;
import static org.brandao.brutos.xml.ContextDefinitionReader.BaseScannerClass;
import static org.brandao.brutos.xml.ContextDefinitionReader.DefaultFilters;
import org.brandao.brutos.xml.XMLBrutosConstants;
import org.brandao.brutos.xml.XMLComponentDefinitionReader;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Cliente
 */
public class ComponentScannerDefinitionReader 
    extends XMLComponentDefinitionReader 
    implements ScannerDefinitionReader{

    private ComponentConfigurer componentConfigurer;
    
    private Scanner scanner;

    public ComponentScannerDefinitionReader(ComponentRegistry componentRegistry) {
        super(componentRegistry);
        this.componentConfigurer = new ComponentConfigurer();
        this.scanner = new DefaultScanner();

    }

    public void setBasePackage(String[] list) {
        this.scanner.setBasePackage(list);
    }

    public void addFilter(TypeFilter filter) {
        this.scanner.addFilter(filter);
    }

    public void removeFilter(TypeFilter value) {
        this.scanner.removeFilter(value);
    }
    
    public void loadDefinitions(){
        scanner.scan();
        componentConfigurer.setComponentList(scanner.getClassList());
        componentConfigurer.init(this.componenetRegistry);
    }
    
    
    protected void build( Element document ){
        super.build(document);

        localAnnotationConfig(parseUtil.getElement(
                    document,
                    XMLBrutosConstants.XML_BRUTOS_COMPONENT_SCAN ) );
    }
    
    private void localAnnotationConfig(Element element){
        
        if(element == null)
            return;
        
        String basePackageText       = element.getAttribute("base-package");
        basePackageText = basePackageText == null? "" : basePackageText;
        
        boolean useDefaultfilter = "true".equals(element.getAttribute("use-default-filters"));
        this.filters             = new ArrayList();
        this.basePackage         = StringUtil.getList(basePackageText, ",");
        
        if(useDefaultfilter)
            filters.addAll(loadDefaultFilters());
        
        NodeList list = parseUtil.getElements(element, "exclude-filter");
        for(int i=0;i<list.getLength();i++){
            Element filterNode = (Element)list.item(i);
            String expression = filterNode.getAttribute("expression");
            String type = filterNode.getAttribute("type");
            String filterClassName = getFilterClassName(expression, type);
            
            Object filter = 
                getTypeFilter(expression, false, filterClassName, true);
            
            if(filter != null)
                filters.add(filter);
        }
        
        list = parseUtil.getElements(element, "include-filter");
        
        for(int i=0;i<list.getLength();i++){
            Element filterNode = (Element)list.item(i);
            String expression = filterNode.getAttribute("expression");
            String type = filterNode.getAttribute("type");
            String filterClassName = getFilterClassName(expression, type);
            
            TypeFilter filter = 
                getTypeFilter(expression, true, filterClassName, true);
            
            if(filter != null)
                filters.add(filter);
        }
        
    }
    
    private String getFilterClassName(String expression, String type){
        if(XMLBrutosConstants.XML_BRUTOS_CUSTOM_FILTER.equals(type))
            return expression;
        else{
            String name = 
                type.length() > 1?
                Character.toUpperCase(type.charAt(0)) + type.substring(1) :
                type;
            return BaseScannerClass + name + "TypeFilter";
        }
    }
    
    private List loadDefaultFilters(){
        
        List filters = new ArrayList();
        
        for(int i=0;i<DefaultFilters.length;i++){
            String filterName = DefaultFilters[i];
            TypeFilter filter = 
                getTypeFilter(null, true, filterName, 
                    !filterName.startsWith(BaseScannerAnnotationClass));
            
            
            if(filter != null)
                filters.add(filter);
        }
        
        return filters;
    }
    
    private TypeFilter getTypeFilter(String expression, boolean include, 
        String className, boolean required){
        
        Properties prop = new Properties();
        prop.setProperty("filter-type", include? "include" : "exclude");
        
        if(expression != null)
            prop.setProperty("expression", expression);
        
        try{
            
            Class scannerFilterClass;
            scannerFilterClass = ClassUtil.get(className);
            TypeFilter filter = (TypeFilter)ClassUtil.getInstance(scannerFilterClass);
            filter.setConfiguration(prop);
            return filter;
        }
        catch (ClassNotFoundException ex) {
            if(required)
                throw new BrutosException(
                    "class not found: " + className,ex);
        } 
        catch (InstantiationException ex) {
            if(required)
                throw new BrutosException(
                    "can't initialize the scanner: " + className,ex);
        } 
        catch (IllegalAccessException ex) {
            if(required)
                throw new BrutosException(
                    "can't initialize the scanner: " + className,ex);
        }
        
        return null;
    }

}
