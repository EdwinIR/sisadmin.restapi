package com.sisadmin.stock.dto;

import java.math.BigDecimal;

public class StockMinMaxDto {
	
	private long idProd;
	private String descripcion;
	private String unidadMedida;
	private BigDecimal stockMin;
	private BigDecimal stockMax;
	private BigDecimal stockFisico;
	private BigDecimal precio;

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
	public BigDecimal getStockMin() {
		return stockMin;
	}
	public void setStockMin(BigDecimal stockMin) {
		this.stockMin = stockMin;
	}
	public BigDecimal getStockMax() {
		return stockMax;
	}
	public void setStockMax(BigDecimal stockMax) {
		this.stockMax = stockMax;
	}
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	public long getIdProd() {
		return idProd;
	}
	public void setIdProd(long idProd) {
		this.idProd = idProd;
	}	
	
}
