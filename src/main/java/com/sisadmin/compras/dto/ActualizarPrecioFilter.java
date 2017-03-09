package com.sisadmin.compras.dto;

import java.util.Date;

public class ActualizarPrecioFilter {
	private Date inicioFecha;
	private Date finFecha;
	
	public Date getInicioFecha() {
		return inicioFecha;
	}
	public void setInicioFecha(Date inicioFecha) {
		this.inicioFecha = inicioFecha;
	}
	public Date getFinFecha() {
		return finFecha;
	}
	public void setFinFecha(Date finFecha) {
		this.finFecha = finFecha;
	}
	
	
}
