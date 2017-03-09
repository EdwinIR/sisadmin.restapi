package com.sisadmin.compras.dto;

import java.util.Date;

public class GestionCuentaPagarFiltroDto {
	
	private long proveedorId;
	private long documentoId;
	private Date fechaDesde;
	private Date fechaHasta;
	
	
	public long getProveedorId() {
		return proveedorId;
	}
	public void setProveedorId(long proveedorId) {
		this.proveedorId = proveedorId;
	}
	public long getDocumentoId() {
		return documentoId;
	}
	public void setDocumentoId(long documentoId) {
		this.documentoId = documentoId;
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
	
	
}
