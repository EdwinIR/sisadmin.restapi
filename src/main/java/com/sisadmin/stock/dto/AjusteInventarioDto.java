package com.sisadmin.stock.dto;

import java.math.BigDecimal;
import java.util.Date;

public class AjusteInventarioDto {
	
	private String numeroAjuste;
	private Date fecha;
	private long productoId;
	private long unidadMedidaId;
	private String codigoBarra;
	private String descripcionProducto;
	private String descripcionUnidadMedida;
	private BigDecimal stockAnterior;
	private BigDecimal stockNuevo;
	private String observaciones;
	
	
	public String getNumeroAjuste() {
		return numeroAjuste;
	}
	public void setNumeroAjuste(String numeroAjuste) {
		this.numeroAjuste = numeroAjuste;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public long getProductoId() {
		return productoId;
	}
	public void setProductoId(long productoId) {
		this.productoId = productoId;
	}
	public String getCodigoBarra() {
		return codigoBarra;
	}
	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
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
	public BigDecimal getStockAnterior() {
		return stockAnterior;
	}
	public void setStockAnterior(BigDecimal stockAnterior) {
		this.stockAnterior = stockAnterior;
	}
	public BigDecimal getStockNuevo() {
		return stockNuevo;
	}
	public void setStockNuevo(BigDecimal stockNuevo) {
		this.stockNuevo = stockNuevo;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public long getUnidadMedidaId() {
		return unidadMedidaId;
	}
	public void setUnidadMedidaId(long unidadMedidaId) {
		this.unidadMedidaId = unidadMedidaId;
	}
	
	

}
