package com.sisadmin.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ContingencyException extends RuntimeException {

	private static final long serialVersionUID = -1214007420129527484L; 

}
