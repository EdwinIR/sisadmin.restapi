package com.sisadmin.stock.dto;

import java.math.BigDecimal;

public class StockValorizadoDto {

	private long productoId;
	private String descripcion;
	private String unidadMedida;
	private BigDecimal precio;
	private BigDecimal stockFisico;
	private BigDecimal stockValor;
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getUnidadMedida() {
		return unidadMedida;
	}
	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}
	public BigDecimal getStockFisico() {
		return stockFisico;
	}
	public void setStockFisico(BigDecimal stockFisico) {
		this.stockFisico = stockFisico;
	}
	public BigDecimal getStockValor() {
		return stockValor;
	}
	public void setStockValor(BigDecimal stockValor) {
		this.stockValor = stockValor;
	}
	public long getProductoId() {
		return productoId;
	}
	public void setProductoId(long productoId) {
		this.productoId = productoId;
	}
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
}
