package com.sisadmin.compras.dto;

import java.math.BigDecimal;
import java.util.Date;

public class CuentaPagarParcialDto {

	
	private long id;
	private Date fecha;
	private BigDecimal montoParcial;
	private String observaciones;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public BigDecimal getMontoParcial() {
		return montoParcial;
	}
	public void setMontoParcial(BigDecimal montoParcial) {
		this.montoParcial = montoParcial;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	
}
