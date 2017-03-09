package com.sisadmin.stock.dto;

import java.util.Date;

public class AjusteInventarioFiltroDto {
	
	private long familiaId;
	private long marcaId;
	private Date fechaDesde;
	private Date fechaHasta;
	
	public long getFamiliaId() {
		return familiaId;
	}
	public void setFamiliaId(long familiaId) {
		this.familiaId = familiaId;
	}
	public long getMarcaId() {
		return marcaId;
	}
	public void setMarcaId(long marcaId) {
		this.marcaId = marcaId;
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
