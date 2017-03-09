package com.sisadmin.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class EmitterInfoException extends RuntimeException {
	private static final long serialVersionUID = -8505554676084540043L;

}
