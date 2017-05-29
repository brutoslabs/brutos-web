package org.brandao.brutos.web.http.view;

import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.RenderView;
import org.brandao.brutos.RequestInstrument;
import org.brandao.brutos.StackRequest;
import org.brandao.brutos.StackRequestElement;
import org.brandao.brutos.mapping.ResultAction;
import org.brandao.brutos.web.MediaType;
import org.brandao.brutos.web.WebMvcRequest;
import org.brandao.brutos.web.WebMvcResponse;
import org.brandao.brutos.web.bean.JsonBeanEncoder;

public class JsonRenderView implements RenderView{

	public void configure(Properties properties) {
	}

	public void show(RequestInstrument requestInstrument,
			StackRequestElement stackRequestElement) {
		
        StackRequest stackRequest    = requestInstrument.getStackRequest();
        StackRequestElement first    = stackRequest.getFirst();
        
        WebMvcRequest mvcRequest     = (WebMvcRequest)first.getRequest();
        WebMvcResponse mvcResponse   = (WebMvcResponse)first.getResponse();
		HttpServletResponse response = (HttpServletResponse) mvcResponse.getServletResponse();
		
		response.setContentType(MediaType.APPLICATION_JSON.getName());
		response.setCharacterEncoding("UTF-8");
		
		Object result = mvcResponse.getResult();
		
		if(result != null){
			ResultAction resultAction = 
				mvcRequest.getResourceAction()
				.getMethodForm().getResultAction(); 
			
			try{
				resultAction.encode(
						new JsonBeanEncoder(
								response.getOutputStream(), 
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
