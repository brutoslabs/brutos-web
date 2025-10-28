/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2025 Afonso Brandao. (afonso.rbn@gmail.com)
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.type.AbstractType;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.web.RequestBody;
import org.brandao.brutos.web.WebMvcResponse;

/**
 * 
 * @author Brandao
 */
public class RequestBodyType 
	extends AbstractType implements Type {

	public RequestBodyType() {
	}

	public Class<?> getClassType() {
		return RequestBody.class;
	}

	public Object convert(Object value) {
			return value;
	}

	public void show(MvcResponse response, Object value){
		try{
			this.innerShow(response, value);
		}
		catch(Throwable e){
			throw new BrutosException(e);
		}
	}
	
	private void innerShow(MvcResponse response, Object value) throws IOException{
		if (value instanceof RequestBody) {
			RequestBody requestBody     = (RequestBody)value;
			WebMvcResponse wResponse    = (WebMvcResponse)response;

			wResponse.addHeader("Content-Disposition", "inline;filename=" + System.currentTimeMillis() + ".txt;");
			wResponse.setContentLength((int) requestBody.getLenght());

			OutputStream out = wResponse.processStream();

			try(InputStream in = requestBody.getInputStream()) {
				byte[] buffer = new byte[3072];
				int length;

				while ((length = in.read(buffer)) != -1)
					out.write(buffer, 0, length);
			}
			
		}
	}

	public boolean isAlwaysRender() {
		return true;
	}
	
}
