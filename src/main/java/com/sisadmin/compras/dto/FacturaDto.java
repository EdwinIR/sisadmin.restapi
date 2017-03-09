package com.sisadmin.compras.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.sisadmin.entity.FacturaDetalle;

public class FacturaDto {

	  private long id;
	   private String numeroInterno;
	   private Date fechaRegistro;
	   private Date fechaEmision;
	   private Date fechaVencimiento;
	   private String tipoDocumentoCodigo;
	   private String serie;
	   private String numeracion;
	   private String tipoMonedaCodigo;	   
	   private String rucProveedor;
	   private String razonSocialProveedor;
	   private String direccionProveedor;
	   private long idProveedor;
	   private String glosa;
	   private BigDecimal cambio;

	   private Collection<FacturaDetalleDto> detalles = new ArrayList<FacturaDetalleDto>();
	   
	   private BigDecimal otrosCargos;
	   private BigDecimal baseImponible;
	   private BigDecimal tasaIgv;
	   private BigDecimal igv;
	   private BigDecimal total;
	   private String descripcionMoneda;
	   private String descripcionTipoDocumento;
  
	public String getDescripcionMoneda() {
		return descripcionMoneda;
	}
	public void setDescripcionMoneda(String descripcionMoneda) {
		this.descripcionMoneda = descripcionMoneda;
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
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
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
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public String getNumeracion() {
		return numeracion;
	}
	public void setNumeracion(String numeracion) {
		this.numeracion = numeracion;
	}
	public String getRucProveedor() {
		return rucProveedor;
	}
	public void setRucProveedor(String rucProveedor) {
		this.rucProveedor = rucProveedor;
	}
	public String getRazonSocialProveedor() {
		return razonSocialProveedor;
	}
	public void setRazonSocialProveedor(String razonSocialProveedor) {
		this.razonSocialProveedor = razonSocialProveedor;
	}
	public String getDireccionProveedor() {
		return direccionProveedor;
	}
	public void setDireccionProveedor(String direccionProveedor) {
		this.direccionProveedor = direccionProveedor;
	}
	public String getGlosa() {
		return glosa;
	}
	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}
	public BigDecimal getOtrosCargos() {
		return otrosCargos;
	}
	public void setOtrosCargos(BigDecimal otrosCargos) {
		this.otrosCargos = otrosCargos;
	}
	public BigDecimal getBaseImponible() {
		return baseImponible;
	}
	public void setBaseImponible(BigDecimal baseImponible) {
		this.baseImponible = baseImponible;
	}
	public BigDecimal getTasaIgv() {
		return tasaIgv;
	}
	public void setTasaIgv(BigDecimal tasaIgv) {
		this.tasaIgv = tasaIgv;
	}
	public BigDecimal getIgv() {
		return igv;
	}
	public void setIgv(BigDecimal igv) {
		this.igv = igv;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public String getTipoDocumentoCodigo() {
		return tipoDocumentoCodigo;
	}
	public void setTipoDocumentoCodigo(String tipoDocumentoCodigo) {
		this.tipoDocumentoCodigo = tipoDocumentoCodigo;
	}
	public String getTipoMonedaCodigo() {
		return tipoMonedaCodigo;
	}
	public void setTipoMonedaCodigo(String tipoMonedaCodigo) {
		this.tipoMonedaCodigo = tipoMonedaCodigo;
	}
	   
	 public String getDescripcionTipoDocumento() {
			return descripcionTipoDocumento;
		}
	public void setDescripcionTipoDocumento(String descripcionTipoDocumento) {
		this.descripcionTipoDocumento = descripcionTipoDocumento;
	}
	public Collection<FacturaDetalleDto> getDetalles() {
		return detalles;
	}
	public void setDetalles(Collection<FacturaDetalleDto> detalles) {
		this.detalles = detalles;
	}
	public long getIdProveedor() {
		return idProveedor;
	}
	public void setIdProveedor(long idProveedor) {
		this.idProveedor = idProveedor;
	}
	public BigDecimal getCambio() {
		return cambio;
	}
	public void setCambio(BigDecimal cambio) {
		this.cambio = cambio;
	}
	
}
