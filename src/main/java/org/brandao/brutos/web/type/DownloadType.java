/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2017 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brandao.brutos.web.type;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.type.AbstractType;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.web.WebMvcResponse;
import org.brandao.brutos.web.http.Download;

/**
 * 
 * @author Brandao
 */
public class DownloadType 
	extends AbstractType 
	implements Type {

	public DownloadType() {
	}

	public Class<?> getClassType() {
		return Download.class;
	}

	public Object convert(Object value) {
		if (value instanceof Download)
			return value;
		else
			return null;
	}

	public void show(MvcResponse response, Object value){
		
		if (value instanceof Download) {
			WebMvcResponse wResponse    = (WebMvcResponse)response;
			HttpServletResponse servlet = (HttpServletResponse)wResponse.getServletResponse();
			Download download           = (Download) value;
			Map<String, Object> info    = download.getHeader();
			
			if (info != null) {
				for(String k: info.keySet()){
					Object v = info.get(k);
					servlet.addHeader(k, String.valueOf(v));
				}
			}

			if (download.getContentLength() != -1){
				servlet.setContentLength((int)download.getContentLength());
			}

			try{
				download.write(response.processStream());
			}
			catch(Throwable e){
				throw new BrutosException(e);
			}
		}
	}
	
	public boolean isAlwaysRender() {
		return true;
	}
	
}
