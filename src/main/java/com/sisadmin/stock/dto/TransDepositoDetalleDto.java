package com.sisadmin.stock.dto;

import java.math.BigDecimal;

public class TransDepositoDetalleDto {
	
	private long id;
	private long idDeposito;
	private String codigo;
	private long idProducto;
	private String descripcionProducto;
	
	//Unidad Medidad Principal
	private long idUnidadMedida;
	private String descripcionUnidadMedida;
	
	private String productoLeido;
	
	private int cantidad;
	
	private BigDecimal stock;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}

	public String getDescripcionProducto() {
		return descripcionProducto;
	}

	public void setDescripcionProducto(String descripcionProducto) {
		this.descripcionProducto = descripcionProducto;
	}

	public long getIdUnidadMedida() {
		return idUnidadMedida;
	}

	public void setIdUnidadMedida(long idUnidadMedida) {
		this.idUnidadMedida = idUnidadMedida;
	}

	public String getDescripcionUnidadMedida() {
		return descripcionUnidadMedida;
	}

	public void setDescripcionUnidadMedida(String descripcionUnidadMedida) {
		this.descripcionUnidadMedida = descripcionUnidadMedida;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public long getIdDeposito() {
		return idDeposito;
	}

	public void setIdDeposito(long idDeposito) {
		this.idDeposito = idDeposito;
	}

	public String getProductoLeido() {
		return productoLeido;
	}

	public void setProductoLeido(String productoLeido) {
		this.productoLeido = productoLeido;
	}

	public BigDecimal getStock() {
		return stock;
	}

	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
