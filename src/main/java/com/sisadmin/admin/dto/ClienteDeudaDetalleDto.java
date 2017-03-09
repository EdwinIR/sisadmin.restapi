package com.sisadmin.admin.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ClienteDeudaDetalleDto {

	private String nroDocumento;
	private Date fechaEmisionDoc;
	private BigDecimal montoTotalDoc;
	private BigDecimal montoAdeudadoDoc;
	private Date fechaVencimiento;
	private String observaciones;
	
	public String getNroDocumento() {
		return nroDocumento;
	}
	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}
	public Date getFechaEmisionDoc() {
		return fechaEmisionDoc;
	}
	public void setFechaEmisionDoc(Date fechaEmisionDoc) {
		this.fechaEmisionDoc = fechaEmisionDoc;
	}
	public BigDecimal getMontoTotalDoc() {
		return montoTotalDoc;
	}
	public void setMontoTotalDoc(BigDecimal montoTotalDoc) {
		this.montoTotalDoc = montoTotalDoc;
	}
	public BigDecimal getMontoAdeudadoDoc() {
		return montoAdeudadoDoc;
	}
	public void setMontoAdeudadoDoc(BigDecimal montoAdeudadoDoc) {
		this.montoAdeudadoDoc = montoAdeudadoDoc;
	}
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	
	
}
