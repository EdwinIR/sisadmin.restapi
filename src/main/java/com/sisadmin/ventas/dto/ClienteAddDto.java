package com.sisadmin.ventas.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.sisadmin.entity.ClienteSucursal;

public class ClienteAddDto {

	private long idSa;
	private String identificador;
	private String razonSocial;
	private String rubro;
	private String email;
	private Date fechaAlta;
	private Collection<ClienteSucursal> detalles = new ArrayList<ClienteSucursal>();
	
	
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getRubro() {
		return rubro;
	}
	public void setRubro(String rubro) {
		this.rubro = rubro;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public Collection<ClienteSucursal> getDetalles() {
		return detalles;
	}
	public void setDetalles(Collection<ClienteSucursal> detalles) {
		this.detalles = detalles;
	}
	public long getIdSa() {
		return idSa;
	}
	public void setIdSa(long idSa) {
		this.idSa = idSa;
	}
	
	
	
}
