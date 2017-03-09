package com.sisadmin.admin.dto;

import com.sisadmin.entity.CuentaDivisionaria;

public class CuentaContableDto {
	
	private long id;
	private String codigo;
	private String descripcion;
	private long cuentaDivisionariaId;
	private String descripcionCuentaDivisionaria;
	private String codigoCuentaDivisionaria;
	
	
	
	public String getDescripcionCuentaDivisionaria() {
		return descripcionCuentaDivisionaria;
	}
	public void setDescripcionCuentaDivisionaria(
			String descripcionCuentaDivisionaria) {
		this.descripcionCuentaDivisionaria = descripcionCuentaDivisionaria;
	}
	public String getCodigoCuentaDivisionaria() {
		return codigoCuentaDivisionaria;
	}
	public void setCodigoCuentaDivisionaria(String codigoCuentaDivisionaria) {
		this.codigoCuentaDivisionaria = codigoCuentaDivisionaria;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public long getCuentaDivisionariaId() {
		return cuentaDivisionariaId;
	}
	public void setCuentaDivisionariaId(long cuentaDivisionariaId) {
		this.cuentaDivisionariaId = cuentaDivisionariaId;
	}
	
	
	
}
