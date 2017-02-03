package org.brandao.brutos.xml;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ParserErrorHandler implements ErrorHandler {

	public ParserErrorHandler() {
	}

	public void warning(SAXParseException saxpe) throws SAXException {
		throw saxpe;
	}

	public void error(SAXParseException saxpe) throws SAXException {
		throw saxpe;
	}

	public void fatalError(SAXParseException saxpe) throws SAXException {
		throw saxpe;
	}

}
