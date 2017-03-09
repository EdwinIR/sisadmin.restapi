package com.sisadmin.compras.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.sisadmin.entity.CuentaPagar;

public class CuentaPagarDetalleDto {
	
	
	//private long cuentaPagarId;
	private BigDecimal montoDeuda;
	private String documento;
	private String comentarios;
	private Date fechaEmision;
	private Date fechaVencimiento;
	
	
	/*public long getCuentaPagarId() {
		return cuentaPagarId;
	}
	public void setCuentaPagarId(long cuentaPagarId) {
		this.cuentaPagarId = cuentaPagarId;
	}*/
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
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
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
	
	

}
