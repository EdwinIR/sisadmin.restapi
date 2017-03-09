package com.sisadmin.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserInfoException extends RuntimeException {

	private static final long serialVersionUID = 7006118229620469434L;
}
	