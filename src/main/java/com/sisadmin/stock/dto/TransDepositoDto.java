package com.sisadmin.stock.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.sisadmin.entity.TransDepositoDetalle;


public class TransDepositoDto {

	private long id;
	private long almacenOrigenId;
	private String descripcionAlmacenOrigen;
	private long almacenDestinoId;
	private String descripcionAlmacenDestino;
	private Date fecha;
	private String estado;
	
	private String usuarioCreacion;
	private String usuarioModificacion;
	private Timestamp fechaCreacion;
	private Timestamp fechaModificacion;
	
	private Collection<TransDepositoDetalleDto> detalles = new ArrayList<TransDepositoDetalleDto>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAlmacenOrigenId() {
		return almacenOrigenId;
	}

	public void setAlmacenOrigenId(long almacenOrigenId) {
		this.almacenOrigenId = almacenOrigenId;
	}

	public long getAlmacenDestinoId() {
		return almacenDestinoId;
	}

	public void setAlmacenDestinoId(long almacenDestinoId) {
		this.almacenDestinoId = almacenDestinoId;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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

	public Collection<TransDepositoDetalleDto> getDetalles() {
		return detalles;
	}

	public void setDetalles(Collection<TransDepositoDetalleDto> detalles) {
		this.detalles = detalles;
	}

	public String getDescripcionAlmacenOrigen() {
		return descripcionAlmacenOrigen;
	}

	public void setDescripcionAlmacenOrigen(String descripcionAlmacenOrigen) {
		this.descripcionAlmacenOrigen = descripcionAlmacenOrigen;
	}

	public String getDescripcionAlmacenDestino() {
		return descripcionAlmacenDestino;
	}

	public void setDescripcionAlmacenDestino(String descripcionAlmacenDestino) {
		this.descripcionAlmacenDestino = descripcionAlmacenDestino;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
