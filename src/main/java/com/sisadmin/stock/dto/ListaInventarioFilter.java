package com.sisadmin.stock.dto;

public class ListaInventarioFilter {
	
	private Long familiaId;
	private Long marcaId;
	private Long almacenId;
	//private Long zonaId;
	
	public Long getFamiliaId() {
		return familiaId;
	}
	public void setFamiliaId(Long familiaId) {
		this.familiaId = familiaId;
	}
	public Long getMarcaId() {
		return marcaId;
	}
	public void setMarcaId(Long marcaId) {
		this.marcaId = marcaId;
	}
	public Long getAlmacenId() {
		return almacenId;
	}
	public void setAlmacenId(Long almacenId) {
		this.almacenId = almacenId;
	}
	/*
	public Long getZonaId() {
		return zonaId;
	}
	public void setZonaId(Long zonaId) {
		this.zonaId = zonaId;
	}*/
}
