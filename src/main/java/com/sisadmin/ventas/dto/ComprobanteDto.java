package com.sisadmin.ventas.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class ComprobanteDto {
	
	private long id;
	private String almacen;
	private String cliente;
	private String nroDocumento;
	private BigDecimal montoTotal;
	private Date fechaEmision;
	private String identificadorCliente;
	private String vendedorDescripcion;
	private Collection<ComprobanteDetalleDto> detalles =new ArrayList<ComprobanteDetalleDto>();
	
	private String usuarioCreacion;
	private String usuarioModificacion;
	private Timestamp fechaCreacion;
	private Timestamp fechaModificacion;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAlmacen() {
		return almacen;
	}
	public void setAlmacen(String almacen) {
		this.almacen = almacen;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getNroDocumento() {
		return nroDocumento;
	}
	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}
	public BigDecimal getMontoTotal() {
		return montoTotal;
	}
	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}
	public Date getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
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
	public Collection<ComprobanteDetalleDto> getDetalles() {
		return detalles;
	}
	public void setDetalles(Collection<ComprobanteDetalleDto> detalles) {
		this.detalles = detalles;
	}
	public String getIdentificadorCliente() {
		return identificadorCliente;
	}
	public void setIdentificadorCliente(String identificadorCliente) {
		this.identificadorCliente = identificadorCliente;
	}
	public String getVendedorDescripcion() {
		return vendedorDescripcion;
	}
	public void setVendedorDescripcion(String vendedorDescripcion) {
		this.vendedorDescripcion = vendedorDescripcion;
	}
	

}
