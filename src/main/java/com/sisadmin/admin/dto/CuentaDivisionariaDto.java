package com.sisadmin.admin.dto;

import com.sisadmin.entity.PlanCuenta;

public class CuentaDivisionariaDto {

	private long id;
	private String codigo;
	private String descripcion;
	private long planCuentaId;
	private String descripcionPlanCuenta;
	//private String codigoPlanCuenta;
	
	
	
	/*public String getCodigoPlanCuenta() {
		return codigoPlanCuenta;
	}
	public void setCodigoPlanCuenta(String codigoPlanCuenta) {
		this.codigoPlanCuenta = codigoPlanCuenta;
	}*/
	public String getDescripcionPlanCuenta() {
		return descripcionPlanCuenta;
	}
	public void setDescripcionPlanCuenta(String descripcionPlanCuenta) {
		this.descripcionPlanCuenta = descripcionPlanCuenta;
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
	public long getPlanCuentaId() {
		return planCuentaId;
	}
	public void setPlanCuentaId(long planCuentaId) {
		this.planCuentaId = planCuentaId;
	}

	
	
}
