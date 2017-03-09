package com.sisadmin.ventas.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class CuentaCobrarDto {
	
	private long id;
	private String razonSocialCliente;
	private String rucCliente;
	private String direccionCliente;
	private long clienteId;
	
	private BigDecimal total;
	private String documento;
	private BigDecimal montoTotal;
	private BigDecimal montoCobrado;
	
	private String estado;
	private Date fechaVencimiento;
	
	private Collection<CuentaCobrarDetalleDto> detalles = new ArrayList<CuentaCobrarDetalleDto>();
	
	public String getRazonSocialCliente() {
		return razonSocialCliente;
	}
	public void setRazonSocialCliente(String razonSocialCliente) {
		this.razonSocialCliente = razonSocialCliente;
	}
	public String getRucCliente() {
		return rucCliente;
	}
	public void setRucCliente(String rucCliente) {
		this.rucCliente = rucCliente;
	}
	public String getDireccionCliente() {
		return direccionCliente;
	}
	public void setDireccionCliente(String direccionCliente) {
		this.direccionCliente = direccionCliente;
	}
	public long getClienteId() {
		return clienteId;
	}
	public void setClienteId(long clienteId) {
		this.clienteId = clienteId;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	
	public BigDecimal getMontoCobrado() {
		return montoCobrado;
	}
	public void setMontoCobrado(BigDecimal montoCobrado) {
		this.montoCobrado = montoCobrado;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	public Collection<CuentaCobrarDetalleDto> getDetalles() {
		return detalles;
	}
	public void setDetalles(Collection<CuentaCobrarDetalleDto> detalles) {
		this.detalles = detalles;
	}
	public BigDecimal getMontoTotal() {
		return montoTotal;
	}
	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
}
