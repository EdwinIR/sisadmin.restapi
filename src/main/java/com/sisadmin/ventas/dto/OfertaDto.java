package com.sisadmin.ventas.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class OfertaDto {

	
	private String descripcion;
	private Date fechaInicio;
	private Date fechaFin;	
	private Collection<OfertaDetalleDto> ofertaDetalleDtos =new  ArrayList<OfertaDetalleDto>();
	
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public Collection<OfertaDetalleDto> getOfertaDetalleDtos() {
		return ofertaDetalleDtos;
	}
	public void setOfertaDetalleDtos(Collection<OfertaDetalleDto> ofertaDetalleDtos) {
		this.ofertaDetalleDtos = ofertaDetalleDtos;
	}
	
	
	
	
}
