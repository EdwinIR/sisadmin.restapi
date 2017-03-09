package com.sisadmin.stock.dto;

import java.math.BigDecimal;

import com.sisadmin.entity.Almacen;
import com.sisadmin.entity.Categorie;
import com.sisadmin.entity.Marca;
import com.sisadmin.entity.Producto;
import com.sisadmin.entity.UnidadMedida;

public class ProductoAlmacenDto {
	
	private long id;
	private String descripcionUnidadMedida;
	private String codigoBarra;
	private BigDecimal stock;
	private BigDecimal cantidad;
	private long unidadMedidaId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public BigDecimal getStock() {
		return stock;
	}
	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}
	public BigDecimal getCantidad() {
		return cantidad;
	}
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}
	public long getUnidadMedidaId() {
		return unidadMedidaId;
	}
	public void setUnidadMedidaId(long unidadMedidaId) {
		this.unidadMedidaId = unidadMedidaId;
	}
	
	
		
	

}
