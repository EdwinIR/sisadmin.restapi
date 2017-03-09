package com.sisadmin.admin.dto;

import com.sisadmin.entity.CuentaContable;

public class RelacionSeisNueveDto {

	private long id;
	private long cuentaContableSeisId;
	private long cuentaContableNueveId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCuentaContableSeisId() {
		return cuentaContableSeisId;
	}
	public void setCuentaContableSeisId(long cuentaContableSeisId) {
		this.cuentaContableSeisId = cuentaContableSeisId;
	}
	public long getCuentaContableNueveId() {
		return cuentaContableNueveId;
	}
	public void setCuentaContableNueveId(long cuentaContableNueveId) {
		this.cuentaContableNueveId = cuentaContableNueveId;
	}
	
	
	
}
