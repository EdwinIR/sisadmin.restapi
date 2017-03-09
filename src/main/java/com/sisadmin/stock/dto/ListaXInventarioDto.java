package com.sisadmin.stock.dto;

import java.math.BigDecimal;

public class ListaXInventarioDto {

	private String codigoProducto;
	private String unidadMedidaPrincipal;
	private String descripcionProducto;
	private String descripcionMarca;
	private BigDecimal stock;
	
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
}
