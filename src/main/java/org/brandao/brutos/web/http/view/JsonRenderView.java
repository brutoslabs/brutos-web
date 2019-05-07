package org.brandao.brutos.web.http.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.MvcRequest;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.RenderViewException;
import org.brandao.brutos.RenderViewType;
import org.brandao.brutos.RequestInstrument;
import org.brandao.brutos.StackRequest;
import org.brandao.brutos.StackRequestElement;
import org.brandao.brutos.mapping.ResultAction;
import org.brandao.brutos.web.MediaType;
import org.brandao.brutos.web.WebMvcRequest;
import org.brandao.brutos.web.WebMvcResponse;
import org.brandao.brutos.web.bean.JsonBeanEncoder;
import org.brandao.brutos.web.mapping.WebAction;
import org.brandao.brutos.web.mapping.WebThrowableSafeData;
import org.brandao.jbrgates.DefaultJSONContext;
import org.brandao.jbrgates.JSONContext;

public class JsonRenderView implements RenderViewType{

	private JSONContext jsonContext;
	
	public JsonRenderView(){
		this.jsonContext = new DefaultJSONContext();
	}
	
	public void configure(Properties properties) {
	}

	public void show(MvcRequest request, MvcResponse response) {
		
		RequestInstrument requestInstrument = request.getRequestInstrument();
        StackRequest stackRequest           = requestInstrument.getStackRequest();
        StackRequestElement first           = stackRequest.getFirst();
        StackRequestElement element         = request.getStackRequestElement();
        
        WebMvcRequest mvcRequest     = (WebMvcRequest)first.getRequest();
        WebMvcResponse mvcResponse   = (WebMvcResponse)first.getResponse();
		HttpServletResponse servletResponse = 
				(HttpServletResponse) mvcResponse.getServletResponse();
		
		servletResponse.setContentType(MediaType.APPLICATION_JSON.getName());
		servletResponse.setCharacterEncoding("UTF-8");
		
		if(element.getThrowableSafeData() != null){
			WebThrowableSafeData t = 
				(WebThrowableSafeData)element.getThrowableSafeData();
			try{
				Map<String,Object> dta = new HashMap<String, Object>();
				dta.put("message", request.getThrowable().getMessage());
				servletResponse.setStatus(((WebAction)t.getAction()).getResponseStatus());
				this.jsonContext.encode(dta, servletResponse.getOutputStream());
			}
			catch(Throwable e){
				throw new RenderViewException(e);
			}
		}
		
		Object result = mvcResponse.getResult();
		
		if(result != null){
			ResultAction resultAction = 
				mvcRequest.getResourceAction()
				.getMethodForm().getResultAction(); 
			
			try{
				resultAction.encode(
						new JsonBeanEncoder(
								servletResponse.getOutputStream(), 
								"UTF-8"), 
						result);
			}
			catch(Throwable e){
				throw new BrutosException(e);
			}
		}
		
	}

	public void destroy() {
	}

}
