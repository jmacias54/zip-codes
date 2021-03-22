package com.mx.zipcode.exception;

/**
 * @author JM997909
 *
 */
public class ServiceException extends RuntimeException{

	
	private static final long serialVersionUID = 2500154844789915161L;


	/**
	 * @param msg
	 */
	public ServiceException(String msg) {
        super(msg);
    }
    
    /**
     * @param mensaje
     * @param exception
     */
    public ServiceException(String mensaje, Throwable exception) {
        super(mensaje);
    }

}
