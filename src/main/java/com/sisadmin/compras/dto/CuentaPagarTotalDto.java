package com.sisadmin.compras.dto;

import java.math.BigDecimal;
import java.util.Date;

public class CuentaPagarTotalDto {
	
	private long id;
	private Date fecha;	
	private String observaciones;
	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	
	

}
