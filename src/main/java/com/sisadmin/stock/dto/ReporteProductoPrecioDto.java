package com.sisadmin.stock.dto;

import java.math.BigDecimal;

public class ReporteProductoPrecioDto {
	
	private String codigo;
	private String descripcion;
	private String familia;
	private String marca;
	private BigDecimal precioA;
	private BigDecimal precioB;
	private BigDecimal precioC;
	private String unidadMedidaA;
	private String unidadMedidaB;
	private String unidadMedidaC;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getFamilia() {
		return familia;
	}
	public void setFamilia(String familia) {
		this.familia = familia;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
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
	public String getUnidadMedidaA() {
		return unidadMedidaA;
	}
	public void setUnidadMedidaA(String unidadMedidaA) {
		this.unidadMedidaA = unidadMedidaA;
	}
	public String getUnidadMedidaB() {
		return unidadMedidaB;
	}
	public void setUnidadMedidaB(String unidadMedidaB) {
		this.unidadMedidaB = unidadMedidaB;
	}
	public String getUnidadMedidaC() {
		return unidadMedidaC;
	}
	public void setUnidadMedidaC(String unidadMedidaC) {
		this.unidadMedidaC = unidadMedidaC;
	}
	
	
	
}
