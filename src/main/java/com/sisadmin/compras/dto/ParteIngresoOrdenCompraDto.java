package com.sisadmin.compras.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class ParteIngresoOrdenCompraDto {

	private long id;
	private String codigo;
	private Date fechaEmision;
	private Date fechaRecepcion;
	private long idAlmacen;	
	private String condicionPagoCodigo;
	private long idProveedor;
	private String tipoMonedaCodigo;
	private String razonSocialProveedor;
	private String descripcionTablaGeneral;
	private String descripcionAlmacen;
	private String descripcionCondicionPago;
	private String descripcionTipoPago;
	
	/*Agregados*/
	private Collection<ParteIngresoDetalleCompraDto> detalles = new ArrayList<ParteIngresoDetalleCompraDto>();

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

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}

	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}

	public long getIdAlmacen() {
		return idAlmacen;
	}

	public void setIdAlmacen(long idAlmacen) {
		this.idAlmacen = idAlmacen;
	}

	public String getCondicionPagoCodigo() {
		return condicionPagoCodigo;
	}

	public void setCondicionPagoCodigo(String condicionPagoCodigo) {
		this.condicionPagoCodigo = condicionPagoCodigo;
	}

	public long getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(long idProveedor) {
		this.idProveedor = idProveedor;
	}

	public String getTipoMonedaCodigo() {
		return tipoMonedaCodigo;
	}

	public void setTipoMonedaCodigo(String tipoMonedaCodigo) {
		this.tipoMonedaCodigo = tipoMonedaCodigo;
	}

	public String getRazonSocialProveedor() {
		return razonSocialProveedor;
	}

	public void setRazonSocialProveedor(String razonSocialProveedor) {
		this.razonSocialProveedor = razonSocialProveedor;
	}

	public String getDescripcionTablaGeneral() {
		return descripcionTablaGeneral;
	}

	public void setDescripcionTablaGeneral(String descripcionTablaGeneral) {
		this.descripcionTablaGeneral = descripcionTablaGeneral;
	}

	public String getDescripcionAlmacen() {
		return descripcionAlmacen;
	}

	public void setDescripcionAlmacen(String descripcionAlmacen) {
		this.descripcionAlmacen = descripcionAlmacen;
	}

	public String getDescripcionCondicionPago() {
		return descripcionCondicionPago;
	}

	public void setDescripcionCondicionPago(String descripcionCondicionPago) {
		this.descripcionCondicionPago = descripcionCondicionPago;
	}

	public String getDescripcionTipoPago() {
		return descripcionTipoPago;
	}

	public void setDescripcionTipoPago(String descripcionTipoPago) {
		this.descripcionTipoPago = descripcionTipoPago;
	}

	public Collection<ParteIngresoDetalleCompraDto> getDetalles() {
		return detalles;
	}

	public void setDetalles(Collection<ParteIngresoDetalleCompraDto> detalles) {
		this.detalles = detalles;
	}
	
}
