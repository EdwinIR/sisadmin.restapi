package com.sisadmin.admin.dto;

import com.sisadmin.entity.TipoTrabajador;

public class ConceptoDto {

	private long id;
	private String codigo;
	private String descripcion;
	private String tipo;
	private long tipoTrabajadorId;
	private Boolean inafecto;
	private String descripcionTipoTrabajador;
	
	
	public String getDescripcionTipoTrabajador() {
		return descripcionTipoTrabajador;
	}
	public void setDescripcionTipoTrabajador(String descripcionTipoTrabajador) {
		this.descripcionTipoTrabajador = descripcionTipoTrabajador;
	}
	
	
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
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public long getTipoTrabajadorId() {
		return tipoTrabajadorId;
	}
	public void setTipoTrabajadorId(long tipoTrabajadorId) {
		this.tipoTrabajadorId = tipoTrabajadorId;
	}
	public Boolean getInafecto() {
		return inafecto;
	}
	public void setInafecto(Boolean inafecto) {
		this.inafecto = inafecto;
	}
	
	
	
}
