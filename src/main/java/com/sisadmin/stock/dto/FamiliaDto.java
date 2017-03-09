package com.sisadmin.stock.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;



public class FamiliaDto {

	private String id;
	private String codigo;
	private String descripcion;
	private BigDecimal margenBase;
	private BigDecimal margenA;
	private BigDecimal margenB;
	private BigDecimal margenC;
	private long zonaId;
	private String descripcionZona;
	private long idSa;
	
	
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
	public BigDecimal getMargenBase() {
		return margenBase;
	}
	public void setMargenBase(BigDecimal margenBase) {
		this.margenBase = margenBase;
	}
	public BigDecimal getMargenA() {
		return margenA;
	}
	public void setMargenA(BigDecimal margenA) {
		this.margenA = margenA;
	}
	public BigDecimal getMargenB() {
		return margenB;
	}
	public void setMargenB(BigDecimal margenB) {
		this.margenB = margenB;
	}
	public BigDecimal getMargenC() {
		return margenC;
	}
	public void setMargenC(BigDecimal margenC) {
		this.margenC = margenC;
	}
	
	public long getZonaId() {
		return zonaId;
	}
	public void setZonaId(long zonaId) {
		this.zonaId = zonaId;
	}
	public String getDescripcionZona() {
		return descripcionZona;
	}
	public void setDescripcionZona(String descripcionZona) {
		this.descripcionZona = descripcionZona;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getIdSa() {
		return idSa;
	}
	public void setIdSa(long idSa) {
		this.idSa = idSa;
	}
	
	
	
	
	
}
