package com.sisadmin.compras.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ParteIngresoDetalleCompraDto {
	
	
	private String descripcionProducto;
	private String descripcionUnidadMedida;
	private boolean bonificado;
	private BigDecimal precioUnitario;
	private BigDecimal descuento1;
	private BigDecimal descuento2;
	private BigDecimal descuento3;
	private BigDecimal descuento4;
	private BigDecimal precioNeto;
	private BigInteger cantidad;
	private BigDecimal totalCompra;
	public String getDescripcionProducto() {
		return descripcionProducto;
	}
	public void setDescripcionProducto(String descripcionProducto) {
		this.descripcionProducto = descripcionProducto;
	}
	public String getDescripcionUnidadMedida() {
		return descripcionUnidadMedida;
	}
	public void setDescripcionUnidadMedida(String descripcionUnidadMedida) {
		this.descripcionUnidadMedida = descripcionUnidadMedida;
	}
	public boolean isBonificado() {
		return bonificado;
	}
	public void setBonificado(boolean bonificado) {
		this.bonificado = bonificado;
	}
	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	public BigDecimal getDescuento1() {
		return descuento1;
	}
	public void setDescuento1(BigDecimal descuento1) {
		this.descuento1 = descuento1;
	}
	public BigDecimal getDescuento2() {
		return descuento2;
	}
	public void setDescuento2(BigDecimal descuento2) {
		this.descuento2 = descuento2;
	}
	public BigDecimal getDescuento3() {
		return descuento3;
	}
	public void setDescuento3(BigDecimal descuento3) {
		this.descuento3 = descuento3;
	}
	public BigDecimal getDescuento4() {
		return descuento4;
	}
	public void setDescuento4(BigDecimal descuento4) {
		this.descuento4 = descuento4;
	}
	public BigDecimal getPrecioNeto() {
		return precioNeto;
	}
	public void setPrecioNeto(BigDecimal precioNeto) {
		this.precioNeto = precioNeto;
	}
	public BigInteger getCantidad() {
		return cantidad;
	}
	public void setCantidad(BigInteger cantidad) {
		this.cantidad = cantidad;
	}
	public BigDecimal getTotalCompra() {
		return totalCompra;
	}
	public void setTotalCompra(BigDecimal totalCompra) {
		this.totalCompra = totalCompra;
	}
	
	

}
