package com.mx.zipcode.exception;

/**
 * Excepci\u00f3n de l\u00f3gica de negocios simulada que indica que no se puede encontrar una entidad comercial o un registro deseado.
 */
/**
 * @author BranchOffice
 *
 */
public class UnknownResourceException extends RuntimeException {

    private static final long serialVersionUID = 8330860863595220574L;

    /**
     * @param msg
     */
    public UnknownResourceException(String msg) {
        super(msg);
    }
    
    /**
     * @param mensaje
     * @param exception
     */
    public UnknownResourceException(String mensaje, Throwable exception) {
        super(mensaje);
    }
}