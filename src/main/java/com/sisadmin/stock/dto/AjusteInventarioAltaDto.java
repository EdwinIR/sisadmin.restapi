package com.sisadmin.stock.dto;

import java.math.BigDecimal;

public class AjusteInventarioAltaDto {
	
	private long productoId;
	private long unidadMedidaId;
	private BigDecimal stockAnterior;
	private BigDecimal stockNuevo;
	private String observaciones;
	private long almacenId;
	public long getProductoId() {
		return productoId;
	}
	public void setProductoId(long productoId) {
		this.productoId = productoId;
	}
	public long getUnidadMedidaId() {
		return unidadMedidaId;
	}
	public void setUnidadMedidaId(long unidadMedidaId) {
		this.unidadMedidaId = unidadMedidaId;
	}
	public BigDecimal getStockAnterior() {
		return stockAnterior;
	}
	public void setStockAnterior(BigDecimal stockAnterior) {
		this.stockAnterior = stockAnterior;
	}
	public BigDecimal getStockNuevo() {
		return stockNuevo;
	}
	public void setStockNuevo(BigDecimal stockNuevo) {
		this.stockNuevo = stockNuevo;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public long getAlmacenId() {
		return almacenId;
	}
	public void setAlmacenId(long almacenId) {
		this.almacenId = almacenId;
	}
	
	

}
