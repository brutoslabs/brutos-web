

package org.brandao.brutos.web.test;

import java.util.Properties;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.io.ByteArrayResource;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.test.MockObjectFactory;
import org.brandao.brutos.test.MockRenderView;
import org.brandao.brutos.validator.JSR303ValidatorFactory;
import org.brandao.brutos.web.XMLWebApplicationContext;


public class MockXMLWebApplicationContext 
    extends XMLWebApplicationContext{

    public static final String IGNORE_RESOURCES = "org.brandao.brutos.mock.ignore_resources";

    public static final String XML_CONTENT = "org.brandao.brutos.mock.xml_content";
    
    private boolean ignoreResources;
    
    private String resource;
    
    @Override
    protected void overrideConfig(){
        Properties config = this.getConfiguration();
        
        if(config.get(BrutosConstants.RENDER_VIEW_CLASS) == null)
        	config.put(BrutosConstants.RENDER_VIEW_CLASS, MockRenderView.class.getName());
        
        if(config.get(BrutosConstants.INVOKER_CLASS) == null)
        	config.put(BrutosConstants.INVOKER_CLASS, MockWebInvoker.class.getName());
        
        if(config.get(BrutosConstants.OBJECT_FACTORY_CLASS) == null)
        	config.put(BrutosConstants.OBJECT_FACTORY_CLASS, MockObjectFactory.class.getName());
        
        if(config.get(BrutosConstants.VALIDATOR_FACTORY_CLASS) == null)
        	config.put(BrutosConstants.VALIDATOR_FACTORY_CLASS, JSR303ValidatorFactory.class.getName());
        
        super.overrideConfig();
        
        this.ignoreResources = "true".equals(config.getProperty(IGNORE_RESOURCES, "false"));
        this.resource = config.getProperty(XML_CONTENT);
        
        if(this.resource != null)
            super.setResources(new Resource[]{new ByteArrayResource(this.resource.getBytes())});
        
        if(this.ignoreResources)
            super.setLocations(new String[]{});
        
    }
    
}
