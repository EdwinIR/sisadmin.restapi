package com.sisadmin.stock.dto;

public class ProductoUbicacionDto {
	private long id;
	private long productoId;
	private long ubicacionId;
	private long zonaId;
	private String descripcionZona;
	private String descripcionUbicacion;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getProductoId() {
		return productoId;
	}
	public void setProductoId(long productoId) {
		this.productoId = productoId;
	}
	public long getUbicacionId() {
		return ubicacionId;
	}
	public void setUbicacionId(long ubicacionId) {
		this.ubicacionId = ubicacionId;
	}
	public long getZonaId() {
		return zonaId;
	}
	public void setZonaId(long zonaId) {
		this.zonaId = zonaId;
	}
	public String getDescripcionZona() {
		return descripcionZona;
	}
	public void setDescripcionZona(String descripcionZona) {
		this.descripcionZona = descripcionZona;
	}
	public String getDescripcionUbicacion() {
		return descripcionUbicacion;
	}
	public void setDescripcionUbicacion(String descripcionUbicacion) {
		this.descripcionUbicacion = descripcionUbicacion;
	}
	
	
	
}
