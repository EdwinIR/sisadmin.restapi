package com.sisadmin.stock.dto;

import java.math.BigDecimal;

public class SaldoPorProductoDto {
	
	private String razonSocialProveedor;
	private String descripcionFamilia;
	private String descripcionMarca;
	private BigDecimal ultimaCompra;
	private BigDecimal ultimaVenta;
	private String codigoEquivalente;
	private String codigoBarra;
	private BigDecimal stockA;
	private BigDecimal stockB;
	private BigDecimal stockC;
	private BigDecimal precioA;
	private BigDecimal precioB;
	private BigDecimal precioC;
	
	
	public String getRazonSocialProveedor() {
		return razonSocialProveedor;
	}
	public void setRazonSocialProveedor(String razonSocialProveedor) {
		this.razonSocialProveedor = razonSocialProveedor;
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
	
	public String getCodigoEquivalente() {
		return codigoEquivalente;
	}
	public void setCodigoEquivalente(String codigoEquivalente) {
		this.codigoEquivalente = codigoEquivalente;
	}
	public BigDecimal getUltimaCompra() {
		return ultimaCompra;
	}
	public void setUltimaCompra(BigDecimal ultimaCompra) {
		this.ultimaCompra = ultimaCompra;
	}
	public BigDecimal getUltimaVenta() {
		return ultimaVenta;
	}
	public void setUltimaVenta(BigDecimal ultimaVenta) {
		this.ultimaVenta = ultimaVenta;
	}
	public String getCodigoBarra() {
		return codigoBarra;
	}
	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}
	public BigDecimal getStockA() {
		return stockA;
	}
	public void setStockA(BigDecimal stockA) {
		this.stockA = stockA;
	}
	public BigDecimal getStockB() {
		return stockB;
	}
	public void setStockB(BigDecimal stockB) {
		this.stockB = stockB;
	}
	public BigDecimal getStockC() {
		return stockC;
	}
	public void setStockC(BigDecimal stockC) {
		this.stockC = stockC;
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
	
	
	

}
