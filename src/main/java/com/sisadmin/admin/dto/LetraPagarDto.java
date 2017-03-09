package com.sisadmin.admin.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.sisadmin.entity.Proveedor;
import com.sisadmin.entity.TablaGeneral;

public class LetraPagarDto {
	
	private long id;	
	private Date fechaEmision;
	private Date fechaVencimiento;	
	private String numeroLetra;
	private String tipoMonedaCodigo;	
	private long proveedorId;
	private BigDecimal total;
	private BigDecimal cuentaTotal;
	
	private String descripcionProveedor;
	private String descripcionMoneda;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	public String getNumeroLetra() {
		return numeroLetra;
	}
	public void setNumeroLetra(String numeroLetra) {
		this.numeroLetra = numeroLetra;
	}
	
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public BigDecimal getCuentaTotal() {
		return cuentaTotal;
	}
	public void setCuentaTotal(BigDecimal cuentaTotal) {
		this.cuentaTotal = cuentaTotal;
	}
	public String getDescripcionProveedor() {
		return descripcionProveedor;
	}
	public void setDescripcionProveedor(String descripcionProveedor) {
		this.descripcionProveedor = descripcionProveedor;
	}
	public String getDescripcionMoneda() {
		return descripcionMoneda;
	}
	public void setDescripcionMoneda(String descripcionMoneda) {
		this.descripcionMoneda = descripcionMoneda;
	}	
	public long getProveedorId() {
		return proveedorId;
	}
	public void setProveedorId(long proveedorId) {
		this.proveedorId = proveedorId;
	}
	public String getTipoMonedaCodigo() {
		return tipoMonedaCodigo;
	}
	public void setTipoMonedaCodigo(String tipoMonedaCodigo) {
		this.tipoMonedaCodigo = tipoMonedaCodigo;
	}
	
	
	

}
