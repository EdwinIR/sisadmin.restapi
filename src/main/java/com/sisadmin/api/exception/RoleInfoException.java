package com.sisadmin.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class RoleInfoException extends RuntimeException {

	private static final long serialVersionUID = -4929709116521464890L;

}
