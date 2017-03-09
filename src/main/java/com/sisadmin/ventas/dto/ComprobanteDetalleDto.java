package com.sisadmin.ventas.dto;

import java.math.BigDecimal;

public class ComprobanteDetalleDto {
	
	//private long id;
	private String codigo;
	private String producto;
	private String unidadMedida;
	private int cantidad;
	private BigDecimal precioUnitario;
	private BigDecimal precioTotal;
	/*
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}*/
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public String getUnidadMedida() {
		return unidadMedida;
	}
	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	public BigDecimal getPrecioTotal() {
		return precioTotal;
	}
	public void setPrecioTotal(BigDecimal precioTotal) {
		this.precioTotal = precioTotal;
	}
}
