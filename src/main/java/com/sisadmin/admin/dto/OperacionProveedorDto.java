package com.sisadmin.admin.dto;

import java.util.Date;

import com.sisadmin.entity.Proveedor;

public class OperacionProveedorDto {
	private long id;
	private String numeroInterno;
	private String numeroGuia;
	private long proveedorId;
	private Date fecha;
	private String razonSocialProveedor;
	
	
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
	public String getNumeroGuia() {
		return numeroGuia;
	}
	public void setNumeroGuia(String numeroGuia) {
		this.numeroGuia = numeroGuia;
	}
	public long getProveedorId() {
		return proveedorId;
	}
	public void setProveedorId(long proveedorId) {
		this.proveedorId = proveedorId;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getRazonSocialProveedor() {
		return razonSocialProveedor;
	}
	public void setRazonSocialProveedor(String razonSocialProveedor) {
		this.razonSocialProveedor = razonSocialProveedor;
	}
	
	
	
}
