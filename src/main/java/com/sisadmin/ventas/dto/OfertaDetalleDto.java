package com.sisadmin.ventas.dto;

import java.math.BigDecimal;

public class OfertaDetalleDto {

	private long productoId;
	private String descripcionProducto;
	
	private BigDecimal precioA;
	private BigDecimal precioB;
	private BigDecimal precioC;
	
	private BigDecimal precioOfertaA;
	private BigDecimal precioOfertaB;
	private BigDecimal precioOfertaC;
	

	private String productoOfertaLeido;
	
	
	public long getProductoId() {
		return productoId;
	}
	public void setProductoId(long productoId) {
		this.productoId = productoId;
	}
	public String getDescripcionProducto() {
		return descripcionProducto;
	}
	public void setDescripcionProducto(String descripcionProducto) {
		this.descripcionProducto = descripcionProducto;
	}
	public BigDecimal getPrecioA() {
		return precioA;
	}
	public void setPrecioA(BigDecimal precioA) {
		this.precioA = precioA;
	}
	public BigDecimal getPrecioB() {
		return precioB;
	}
	public void setPrecioB(BigDecimal precioB) {
		this.precioB = precioB;
	}
	public BigDecimal getPrecioC() {
		return precioC;
	}
	public void setPrecioC(BigDecimal precioC) {
		this.precioC = precioC;
	}
	public BigDecimal getPrecioOfertaA() {
		return precioOfertaA;
	}
	public void setPrecioOfertaA(BigDecimal precioOfertaA) {
		this.precioOfertaA = precioOfertaA;
	}
	public BigDecimal getPrecioOfertaB() {
		return precioOfertaB;
	}
	public void setPrecioOfertaB(BigDecimal precioOfertaB) {
		this.precioOfertaB = precioOfertaB;
	}
	public BigDecimal getPrecioOfertaC() {
		return precioOfertaC;
	}
	public void setPrecioOfertaC(BigDecimal precioOfertaC) {
		this.precioOfertaC = precioOfertaC;
	}
	public String getProductoOfertaLeido() {
		return productoOfertaLeido;
	}
	public void setProductoOfertaLeido(String productoOfertaLeido) {
		this.productoOfertaLeido = productoOfertaLeido;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	/**
	OfertaDetalleProductoDto producto;

	public OfertaDetalleProductoDto getProducto() {
		return producto;
	}

	public void setProducto(OfertaDetalleProductoDto producto) {
		this.producto = producto;
	}**/
	
}
