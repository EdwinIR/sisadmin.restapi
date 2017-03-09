package com.sisadmin.stock.dto;

import java.util.Date;

import com.sisadmin.entity.Almacen;

public class GuiaRemisionDto {

	
	private long id;
	private Date fechaInicio;
	private String rucDestinatario;
	private String denominacionDestinatario;
	private long almacenOrigenId;
	private long almacenDestinoId;	
	private String numeroGuia; 
	private String nombresTransportista;	
	private String licenciaTransportista; 
	private String marcaTransporte;
	private String placa;
	private long transferenciaId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getRucDestinatario() {
		return rucDestinatario;
	}
	public void setRucDestinatario(String rucDestinatario) {
		this.rucDestinatario = rucDestinatario;
	}
	public String getDenominacionDestinatario() {
		return denominacionDestinatario;
	}
	public void setDenominacionDestinatario(String denominacionDestinatario) {
		this.denominacionDestinatario = denominacionDestinatario;
	}
	public long getAlmacenOrigenId() {
		return almacenOrigenId;
	}
	public void setAlmacenOrigenId(long almacenOrigenId) {
		this.almacenOrigenId = almacenOrigenId;
	}
	public long getAlmacenDestinoId() {
		return almacenDestinoId;
	}
	public void setAlmacenDestinoId(long almacenDestinoId) {
		this.almacenDestinoId = almacenDestinoId;
	}
	public String getNumeroGuia() {
		return numeroGuia;
	}
	public void setNumeroGuia(String numeroGuia) {
		this.numeroGuia = numeroGuia;
	}
	public String getNombresTransportista() {
		return nombresTransportista;
	}
	public void setNombresTransportista(String nombresTransportista) {
		this.nombresTransportista = nombresTransportista;
	}
	public String getLicenciaTransportista() {
		return licenciaTransportista;
	}
	public void setLicenciaTransportista(String licenciaTransportista) {
		this.licenciaTransportista = licenciaTransportista;
	}
	public String getMarcaTransporte() {
		return marcaTransporte;
	}
	public void setMarcaTransporte(String marcaTransporte) {
		this.marcaTransporte = marcaTransporte;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public long getTransferenciaId() {
		return transferenciaId;
	}
	public void setTransferenciaId(long transferenciaId) {
		this.transferenciaId = transferenciaId;
	}
	
}
