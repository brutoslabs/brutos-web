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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.type.AbstractType;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.web.WebMvcResponse;
import org.brandao.brutos.web.http.UploadedFile;

/**
 * 
 * @author Brandao
 */
public class FileType 
	extends AbstractType implements Type {

	public FileType() {
	}

	public Class<?> getClassType() {
		return File.class;
	}

	public Object convert(Object value) {
		if (value instanceof UploadedFile)
			return ((UploadedFile) value).getFile();
		else
			return null;
	}

	public void show(MvcResponse response, Object value) throws IOException {
		if (value instanceof File) {
			WebMvcResponse wResponse    = (WebMvcResponse)response;
			HttpServletResponse servlet = (HttpServletResponse)wResponse.getServletResponse();
			File f = (File) value;

			servlet.setHeader("Content-Disposition",
					"attachment;filename=" + f.getName() + ";");

			servlet.setContentLength((int) f.length());

			InputStream in   = new FileInputStream(f);
			OutputStream out = servlet.getOutputStream();

			try {
				byte[] buffer = new byte[3072];
				int length;

				while ((length = in.read(buffer)) != -1)
					out.write(buffer, 0, length);
			}
			finally {
				if (in != null)
					in.close();
			}
		}
	}

}
