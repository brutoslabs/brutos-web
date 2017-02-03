package org.brandao.brutos.xml;

import java.util.List;


public class ScannerEntity {
    
    private String scannerClassName;
    
    private String[] basePackage;
    
    private boolean useDefaultfilter;
    
    private List excludeFilters;
    
    private List includeFilters;

    public String getScannerClassName() {
        return scannerClassName;
    }

    public void setScannerClassName(String scannerClassName) {
        this.scannerClassName = scannerClassName;
    }

    public String[] getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String[] basePackage) {
        this.basePackage = basePackage;
    }

    public boolean isUseDefaultfilter() {
        return useDefaultfilter;
    }

    public void setUseDefaultfilter(boolean useDefaultfilter) {
        this.useDefaultfilter = useDefaultfilter;
    }

    public List getExcludeFilters() {
        return excludeFilters;
    }

    public void setExcludeFilters(List excludeFilters) {
        this.excludeFilters = excludeFilters;
    }

    public List getIncludeFilters() {
        return includeFilters;
    }

    public void setIncludeFilters(List includeFilters) {
        this.includeFilters = includeFilters;
    }
    
}
