

package org.brandao.brutos.validator;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import org.brandao.brutos.BrutosException;


public class ValidatorException extends BrutosException implements Serializable {

    public List exceptions = new LinkedList();

    public ValidatorException() {
	super();
    }

    public ValidatorException(String message) {
	super(message);
    }

    public ValidatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidatorException(Throwable cause) {
        super(cause);
    }

    public void addCause(ValidatorException vex){
        exceptions.add(vex);
    }

    public void addCauses(List vex){
        exceptions.addAll(vex);
    }

    public List getCauses(){
        return this.exceptions;
    }

}
