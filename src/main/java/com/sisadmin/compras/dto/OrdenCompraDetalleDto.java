package com.sisadmin.compras.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.sisadmin.entity.Almacen;
import com.sisadmin.entity.OrdenCompra;
import com.sisadmin.entity.Producto;
import com.sisadmin.entity.UnidadMedida;

public class OrdenCompraDetalleDto {

	private long productoId;
	private String descripcionProducto;
	private long unidadMedidaId;
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
	
	private String productoLeido;
		
	
	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	public BigDecimal getPrecioNeto() {
		return precioNeto;
	}
	public void setPrecioNeto(BigDecimal precioNeto) {
		this.precioNeto = precioNeto;
	}
	
	public boolean isBonificado() {
		return bonificado;
	}
	public void setBonificado(boolean bonificado) {
		this.bonificado = bonificado;
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
	
	public BigDecimal getTotalCompra() {
		return totalCompra;
	}
	public void setTotalCompra(BigDecimal totalCompra) {
		this.totalCompra = totalCompra;
	}
	
	public BigInteger getCantidad() {
		return cantidad;
	}
	public void setCantidad(BigInteger cantidad) {
		this.cantidad = cantidad;
	}
	
	public long getProductoId() {
		return productoId;
	}
	public void setProductoId(long productoId) {
		this.productoId = productoId;
	}
	
	public long getUnidadMedidaId() {
		return unidadMedidaId;
	}
	public void setUnidadMedidaId(long unidadMedidaId) {
		this.unidadMedidaId = unidadMedidaId;
	}
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
	
	public String getProductoLeido() {
		return productoLeido;
	}
	public void setProductoLeido(String productoLeido) {
		this.productoLeido = productoLeido;
	}

	
	
	
}
