package com.sisadmin.ventas.dto;

import java.util.Date;

public class ComprobanteFilterDto {
	
	private String nroDocumento;
	private Date fechaDesde;
	private Date fechaHasta;
	private String vendedorId;
	
	public String getNroDocumento() {
		return nroDocumento;
	}
	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}
	public Date getFechaDesde() {
		return fechaDesde;
	}
	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	public Date getFechaHasta() {
		return fechaHasta;
	}
	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	public String getVendedorId() {
		return vendedorId;
	}
	public void setVendedorId(String vendedorId) {
		this.vendedorId = vendedorId;
	}
}
