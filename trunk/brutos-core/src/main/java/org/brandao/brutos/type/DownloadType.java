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
