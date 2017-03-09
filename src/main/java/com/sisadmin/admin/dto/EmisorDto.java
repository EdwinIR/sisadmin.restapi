package com.sisadmin.admin.dto;

public class EmisorDto {

	private String ruc;
	private String razonSocial;
	private String razonComercial;	
	private String direccion;
    private String email;
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getRazonComercial() {
		return razonComercial;
	}
	public void setRazonComercial(String razonComercial) {
		this.razonComercial = razonComercial;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}    
    	
}
