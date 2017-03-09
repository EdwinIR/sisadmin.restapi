package com.sisadmin.stock.dto;

import java.math.BigDecimal;


public class ProductoDetalleDto {
	private long idProductoDetalle;
	private long unidadMedidaId;
	private String descripcionUnidadMedida;
	private String codigoBarra;
	private BigDecimal precio;
	
	
	
	public long getUnidadMedidaId() {
		return unidadMedidaId;
	}
	public void setUnidadMedidaId(long unidadMedidaId) {
		this.unidadMedidaId = unidadMedidaId;
	}
	public String getDescripcionUnidadMedida() {
		return descripcionUnidadMedida;
	}
	public void setDescripcionUnidadMedida(String descripcionUnidadMedida) {
		this.descripcionUnidadMedida = descripcionUnidadMedida;
	}
	public String getCodigoBarra() {
		return codigoBarra;
	}
	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	public long getIdProductoDetalle() {
		return idProductoDetalle;
	}
	public void setIdProductoDetalle(long idProductoDetalle) {
		this.idProductoDetalle = idProductoDetalle;
	}

	
	
	
	
}
