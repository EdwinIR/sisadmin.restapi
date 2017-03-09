package com.sisadmin.compras.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ListActualizacionPrecioDto {
	private int id;
	private String codigoProducto;
	private String descripcionProducto;
	private int codigoUnidadMedida;
	private String descripcionUnidadMedida;
	private BigDecimal precioActual;
	private BigDecimal precioNuevo;
	private Date fechaInicio;
	private long idPrecio;
	
	public String getCodigoProducto() {
		return codigoProducto;
	}
	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}
	public String getDescripcionProducto() {
		return descripcionProducto;
	}
	public void setDescripcionProducto(String descripcionProducto) {
		this.descripcionProducto = descripcionProducto;
	}
	public int getCodigoUnidadMedida() {
		return codigoUnidadMedida;
	}
	public void setCodigoUnidadMedida(int codigoUnidadMedida) {
		this.codigoUnidadMedida = codigoUnidadMedida;
	}
	public String getDescripcionUnidadMedida() {
		return descripcionUnidadMedida;
	}
	public void setDescripcionUnidadMedida(String descripcionUnidadMedida) {
		this.descripcionUnidadMedida = descripcionUnidadMedida;
	}
	public BigDecimal getPrecioActual() {
		return precioActual;
	}
	public void setPrecioActual(BigDecimal precioActual) {
		this.precioActual = precioActual;
	}
	public BigDecimal getPrecioNuevo() {
		return precioNuevo;
	}
	public void setPrecioNuevo(BigDecimal precioNuevo) {
		this.precioNuevo = precioNuevo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public long getIdPrecio() {
		return idPrecio;
	}
	public void setIdPrecio(long idPrecio) {
		this.idPrecio = idPrecio;
	}
	
	
}
