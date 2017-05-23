package org.brandao.brutos;

import java.io.Serializable;
import java.util.EventListener;

public interface RequestParserListener 
	extends EventListener, Serializable{

    void started(RequestParserEvent value);

    void finished(RequestParserEvent value);

}
