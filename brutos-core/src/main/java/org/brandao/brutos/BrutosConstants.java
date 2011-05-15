/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * This library is free software. You can redistribute it 
 * and/or modify it under the terms of the GNU General Public
 * License (GPL) version 3.0 or (at your option) any later 
 * version.
 * You may obtain a copy of the License at
 * 
 * http://www.gnu.org/licenses/gpl.html 
 * 
 * Distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied.
 *
 */

package org.brandao.brutos;

/**
 * Cont�m informa��es importantes para o funcionamento interno do Brutos.
 * 
 * @author Afonso Brandao
 */
public interface BrutosConstants {
    
    public final String WEBFRAME    = "Controller";

    public final String APPLICATION_CONTEXT = "application-context";

    public final String CUSTOM_TYPES = "customTypes";

    public final String EXCEPTION   = "brutos_exception";
    
    public final String BUNDLE_NAME = "default_bundle";
    
    public final String USER = "brutos_user";
    
    public final String IOC_MANAGER = "ioc-manager";
    
    public final String IOC_PROVIDER = "ioc-provider";
    
    public final String WEBFRAME_MANAGER = "webframe-manager";
    
    public final String INTERCEPTOR_MANAGER = "interceptor-manager";
    
    public final String VIEW_PROVIDER = "view-provider";
    
    public final String VALIDATOR_PROVIDER = "validator-provider";

    public final String REDIRECT = AbstractApplicationContext.class.getName() + ".REDIRECT";

    public final String UPLOAD_LISTENER_FACTORY = AbstractApplicationContext.class.getName() + ".UPLOAD_LISTENER_FACTORY";

    public final String REQUEST_INSTRUMENT = AbstractApplicationContext.class.getName() + ".REQUEST_INSTRUMENT";

    public final String HTTP_REQUEST_PARSER_FACTORY = AbstractApplicationContext.class.getName() + ".HTTP_REQUEST_PARSER_FACTORY";

    public final String HTTP_REQUEST_PARSER = AbstractApplicationContext.class.getName() + ".HTTP_REQUEST_PARSER";

    public final String SESSION_UPLOAD_STATS = AbstractApplicationContext.class.getName() + ".SESSION_UPLOAD_STATS";

    public final String EXCEPTION_DATA = AbstractApplicationContext.class.getName() + ".EXCEPTION_DATA";

    public final String ROOT_APPLICATION_CONTEXT_ATTRIBUTE = AbstractApplicationContext.class.getName() + ".ROOT";

    public final String FLASH_INSTRUMENT = AbstractApplicationContext.class.getName() + ".FLASH_INSTRUMENT";

    public final String LOGGER = AbstractApplicationContext.class.getName() + ".LOGGER";

    public final String DEFAULT_RETURN_NAME = AbstractApplicationContext.class.getName() + ".RETURN";
    
    public final String METHOD_RESOLVER = AbstractApplicationContext.class.getName() + ".METHOD_RESOLVER";

    public final String CONTROLLER_RESOLVER = AbstractApplicationContext.class.getName() + ".CONTROLLER_RESOLVER";

    public final String CONTROLLER = AbstractApplicationContext.class.getName() + ".CONTROLLER";

    public final String INVOKER = AbstractApplicationContext.class.getName() + ".INVOKER";

    public final String JSF_HANDLER = AbstractApplicationContext.class.getName() + ".JSF_Handler";
    
    public final String JSF_CONTEXT = AbstractApplicationContext.class.getName() + ".JSF_Context";
    
    public final String JSF_UI_VIEW_ROOT = AbstractApplicationContext.class.getName() + ".JSF_viewRoot";

    public final String JSF_ACTION_LISTENER = AbstractApplicationContext.class.getName() + ".JSF_Action_Listener";

}
