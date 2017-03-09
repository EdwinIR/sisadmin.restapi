package com.sisadmin.admin.dto;

import com.sisadmin.entity.Sede;

public class EmpresaDto {

	private long id;
	private String codigo;
	private String razonSocial;
	private String ruc;
	private String direccion;
	private long sedeId;
	private String descripcionSede;
	
	
	public String getDescripcionSede() {
		return descripcionSede;
	}
	public void setDescripcionSede(String descripcionSede) {
		this.descripcionSede = descripcionSede;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public long getSedeId() {
		return sedeId;
	}
	public void setSedeId(long sedeId) {
		this.sedeId = sedeId;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	
	
}
