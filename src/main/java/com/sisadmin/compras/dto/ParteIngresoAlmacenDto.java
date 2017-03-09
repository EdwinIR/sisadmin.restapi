package com.sisadmin.compras.dto;

import java.util.Date;

public class ParteIngresoAlmacenDto {
	private long id;
	private String numeroInterno;
	private Date fechaIngreso;
	private long proveedorId;
	private long ordenCompraId;
	private String razonSocialProveedor;
	private long almacenId;
	
	
	
	public String getRazonSocialProveedor() {
		return razonSocialProveedor;
	}
	public void setRazonSocialProveedor(String razonSocialProveedor) {
		this.razonSocialProveedor = razonSocialProveedor;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNumeroInterno() {
		return numeroInterno;
	}
	public void setNumeroInterno(String numeroInterno) {
		this.numeroInterno = numeroInterno;
	}
	public Date getFechaIngreso() {
		return fechaIngreso;
	}
	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	public long getProveedorId() {
		return proveedorId;
	}
	public void setProveedorId(long proveedorId) {
		this.proveedorId = proveedorId;
	}
	public long getOrdenCompraId() {
		return ordenCompraId;
	}
	public void setOrdenCompraId(long ordenCompraId) {
		this.ordenCompraId = ordenCompraId;
	}
	public long getAlmacenId() {
		return almacenId;
	}
	public void setAlmacenId(long almacenId) {
		this.almacenId = almacenId;
	}
	
	
	
}
