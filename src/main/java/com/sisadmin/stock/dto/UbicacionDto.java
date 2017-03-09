package com.sisadmin.stock.dto;

public class UbicacionDto {
	private long id;
	private String abreviado;
	private String descripcion;
	
	private long zonaId;
	private long almacenId;
	private String descripcionZona;
	private String descripcionAlmacen;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public long getAlmacenId() {
		return almacenId;
	}
	public void setAlmacenId(long almacenId) {
		this.almacenId = almacenId;
	}
	public String getDescripcionAlmacen() {
		return descripcionAlmacen;
	}
	public void setDescripcionAlmacen(String descripcionAlmacen) {
		this.descripcionAlmacen = descripcionAlmacen;
	}
	public String getAbreviado() {
		return abreviado;
	}
	public void setAbreviado(String abreviado) {
		this.abreviado = abreviado;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
