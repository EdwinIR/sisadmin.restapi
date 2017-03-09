package com.sisadmin.admin.dto;

import java.math.BigDecimal;

import com.sisadmin.entity.Afp;
import com.sisadmin.entity.Periodo;

public class AfpPeriodoDto {

	private long id;
	private String codigo;
	private BigDecimal tasa;
	private BigDecimal descuento;
	private long afpId;
	private long periodoId;
	private String descripcionAfp;
	private String descripcionPeriodo;
	
	public String getDescripcionAfp() {
		return descripcionAfp;
	}
	public void setDescripcionAfp(String descripcionAfp) {
		this.descripcionAfp = descripcionAfp;
	}
	public String getDescripcionPeriodo() {
		return descripcionPeriodo;
	}
	public void setDescripcionPeriodo(String descripcionPeriodo) {
		this.descripcionPeriodo = descripcionPeriodo;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public BigDecimal getTasa() {
		return tasa;
	}
	public void setTasa(BigDecimal tasa) {
		this.tasa = tasa;
	}
	public BigDecimal getDescuento() {
		return descuento;
	}
	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}
	public long getAfpId() {
		return afpId;
	}
	public void setAfpId(long afpId) {
		this.afpId = afpId;
	}
	public long getPeriodoId() {
		return periodoId;
	}
	public void setPeriodoId(long periodoId) {
		this.periodoId = periodoId;
	}
	
	
	
}
