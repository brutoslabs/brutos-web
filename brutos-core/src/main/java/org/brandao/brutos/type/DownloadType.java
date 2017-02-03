^/*
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

package org.brandao.brutos.type;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.web.http.Download;

public class DownloadType extends AbstractType implements Type {

	public DownloadType() {
	}

	public Class getClassType() {
		return Download.class;
	}

	public Object convert(Object value) {
		if (value instanceof Download)
			return value;
		else
			return null;
	}

	public void show(MvcResponse response, Object value) throws IOException {
		if (value instanceof Download) {
			Download download = (Download) value;
			Map info = download.getHeader();
			if (info != null) {
				Iterator keys = download.getHeader().keySet().iterator();
				while (keys.hasNext()) {
					String key = (String) keys.next();
					info.put(key, info.get(key));
				}
			}

			if (download.getContentLength() != -1)
				response.setLength((int) download.getContentLength());

			download.write(response.processStream());
		}
	}
}
