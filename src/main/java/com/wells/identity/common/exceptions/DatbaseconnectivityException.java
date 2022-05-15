package com.wells.identity.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="DB issues")
public class DatbaseconnectivityException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public DatbaseconnectivityException(String message){
    	super(message);
    }
}
