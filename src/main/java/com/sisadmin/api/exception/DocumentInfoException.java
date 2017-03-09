package com.sisadmin.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DocumentInfoException  extends RuntimeException {
	private static final long serialVersionUID = 2403628703841111050L;

}
