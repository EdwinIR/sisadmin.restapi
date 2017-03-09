package com.sisadmin.stock.dto;

import java.math.BigDecimal;

public class ListaXPrecioDto {
	
	private String codigoProducto;
	private String unidadMedidaPrincipal;
	private String descripcionProducto;
	private String descripcionFamilia;
	private String descripcionMarca;
	private BigDecimal stock;
	private BigDecimal precio;
	
	public String getCodigoProducto() {
		return codigoProducto;
	}
	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}
	public String getUnidadMedidaPrincipal() {
		return unidadMedidaPrincipal;
	}
	public void setUnidadMedidaPrincipal(String unidadMedidaPrincipal) {
		this.unidadMedidaPrincipal = unidadMedidaPrincipal;
	}
	public String getDescripcionProducto() {
		return descripcionProducto;
	}
	public void setDescripcionProducto(String descripcionProducto) {
		this.descripcionProducto = descripcionProducto;
	}
	public String getDescripcionFamilia() {
		return descripcionFamilia;
	}
	public void setDescripcionFamilia(String descripcionFamilia) {
		this.descripcionFamilia = descripcionFamilia;
	}
	public String getDescripcionMarca() {
		return descripcionMarca;
	}
	public void setDescripcionMarca(String descripcionMarca) {
		this.descripcionMarca = descripcionMarca;
	}
	public BigDecimal getStock() {
		return stock;
	}
	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	
}
