package com.mx.zipcode.exception;

public class ValidacionException extends RuntimeException {

  
    /**
	 * 
	 */
	private static final long serialVersionUID = -2543446748358272010L;

	/**
     * @param msg
     */
    public ValidacionException(String msg) {
        super(msg);
    }
    
    /**
     * @param mensaje
     * @param exception
     */
    public ValidacionException(String mensaje, Throwable exception) {
        super(mensaje);
    }

}
