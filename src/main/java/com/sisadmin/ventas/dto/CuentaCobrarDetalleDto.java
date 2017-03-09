package com.sisadmin.ventas.dto;

import java.math.BigDecimal;
import java.util.Date;

public class CuentaCobrarDetalleDto {

	private BigDecimal montoDeuda;
	private String documento;
	private String comentarios;
	private Date fechaVencimiento;
	private Date fechaEmision;
	
	public BigDecimal getMontoDeuda() {
		return montoDeuda;
	}
	public void setMontoDeuda(BigDecimal montoDeuda) {
		this.montoDeuda = montoDeuda;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	
	public Date getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
}
