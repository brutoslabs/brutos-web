package org.brandao.brutos.type;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.web.http.UploadedFile;

public class FileType extends AbstractType implements Type {

	public FileType() {
	}

	public Class getClassType() {
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
			File f = (File) value;

			response.setInfo("Content-Disposition",
					"attachment;filename=" + f.getName() + ";");

			response.setLength((int) f.length());

			InputStream in = new FileInputStream(f);
			OutputStream out = response.processStream();

			try {
				byte[] buffer = new byte[3072];
				int length;

				while ((length = in.read(buffer)) != -1)
					out.write(buffer, 0, length);
			} finally {
				if (in != null)
					in.close();
			}
		}
	}

}
