package com.sisadmin.stock.dto;

import java.math.BigDecimal;

import com.sisadmin.entity.Categorie;
import com.sisadmin.entity.Marca;

public class MarcaFamiliaDto {
	
	private long id;
	private long marcaId;
	private long familiaId;
	
	private BigDecimal margenBase;
	private BigDecimal margenA;
	private BigDecimal margenB;
	private BigDecimal margenC;
	
	private String descripcionMarca;
	private String descripcionFamilia;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getMarcaId() {
		return marcaId;
	}
	public void setMarcaId(long marcaId) {
		this.marcaId = marcaId;
	}
	public long getFamiliaId() {
		return familiaId;
	}
	public void setFamiliaId(long familiaId) {
		this.familiaId = familiaId;
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
	public String getDescripcionMarca() {
		return descripcionMarca;
	}
	public void setDescripcionMarca(String descripcionMarca) {
		this.descripcionMarca = descripcionMarca;
	}
	public String getDescripcionFamilia() {
		return descripcionFamilia;
	}
	public void setDescripcionFamilia(String descripcionFamilia) {
		this.descripcionFamilia = descripcionFamilia;
	}
	
	

}
