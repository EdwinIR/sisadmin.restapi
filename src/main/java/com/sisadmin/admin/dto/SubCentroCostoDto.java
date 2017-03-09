package com.sisadmin.admin.dto;

import com.sisadmin.entity.CentroCosto;

public class SubCentroCostoDto {
	
	private long id;
	private String codigo;
	private String descripcion;
	private long centroCostoId;
	private String descripcionCentroCosto;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public long getCentroCostoId() {
		return centroCostoId;
	}
	public void setCentroCostoId(long centroCostoId) {
		this.centroCostoId = centroCostoId;
	}
	public String getDescripcionCentroCosto() {
		return descripcionCentroCosto;
	}
	public void setDescripcionCentroCosto(String descripcionCentroCosto) {
		this.descripcionCentroCosto = descripcionCentroCosto;
	}
	
	
	
}
