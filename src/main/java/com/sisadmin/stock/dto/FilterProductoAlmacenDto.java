package com.sisadmin.stock.dto;

public class FilterProductoAlmacenDto {
	private long productoId;
	private long almacenId;
	public long getProductoId() {
		return productoId;
	}
	public void setProductoId(long productoId) {
		this.productoId = productoId;
	}
	public long getAlmacenId() {
		return almacenId;
	}
	public void setAlmacenId(long almacenId) {
		this.almacenId = almacenId;
	}
	
}
