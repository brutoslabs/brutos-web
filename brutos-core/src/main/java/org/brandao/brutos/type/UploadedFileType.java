package org.brandao.brutos.type;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.web.http.UploadedFile;

public class UploadedFileType extends AbstractType implements Type {

	public UploadedFileType() {
	}

	public Class getClassType() {
		return UploadedFile.class;
	}

	public Object convert(Object value) {
		if (value instanceof UploadedFile)
			return value;
		else if (value == null)
			return null;
		else
			throw new UnknownTypeException(value.getClass().getName());
	}

	public void show(MvcResponse response, Object value) throws IOException {
		if (value instanceof UploadedFile) {
			UploadedFile f = (UploadedFile) value;

			if (f.getFile() != null) {
				response.setInfo("Content-Disposition", "attachment;filename="
						+ f.getFileName() + ";");
			}

			response.setLength((int) f.getFile().length());

			InputStream in = new FileInputStream(f.getFile());
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
