package com.sisadmin.ventas.dto;

import java.util.Date;

public class GestionCuentaCobrarFiltroDto {
	
	private long clienteId;	
	private Date fechaDesde;
	private Date fechaHasta;
	
	public long getClienteId() {
		return clienteId;
	}
	public void setClienteId(long clienteId) {
		this.clienteId = clienteId;
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
