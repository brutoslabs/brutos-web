

package org.brandao.brutos.rmi;


public class RemoteException extends Exception{

    public RemoteException() {
	super();
    }

    public RemoteException(String message) {
	super(message);
    }

    public RemoteException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoteException(Throwable cause) {
        super(cause);
    }

}
