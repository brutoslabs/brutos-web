package org.brandao.brutos;

import java.io.IOException;
import java.util.Properties;

public interface RenderView {

	void configure(Properties properties);

	void show(RequestInstrument requestInstrument,
			StackRequestElement stackRequestElement) throws IOException;

	void destroy();

}
