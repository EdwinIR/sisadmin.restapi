package com.sisadmin.stock.dto;

import java.sql.Timestamp;

import com.sisadmin.entity.Almacen;

public class ZonaDto {

	private long id;
	private String codigo;
	private String descripcion;
	private String usuarioCreacion;
	private String usuarioModificacion;
	private Timestamp fechaCreacion;
	private Timestamp fechaModificacion;
	private long almacenId;
	private String descripcionAlmacen;
	
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
	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}
	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}
	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}
	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}
	public Timestamp getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Timestamp getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(Timestamp fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
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
	
	
	
}
