package com.sisadmin.compras.dto;

import java.math.BigDecimal;

public class FacturaDetalleDto {
	
	private long productoId;
	private int cantidad;
	private BigDecimal precio;
	
	
	public long getProductoId() {
		return productoId;
	}
	public void setProductoId(long productoId) {
		this.productoId = productoId;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	
	

}
