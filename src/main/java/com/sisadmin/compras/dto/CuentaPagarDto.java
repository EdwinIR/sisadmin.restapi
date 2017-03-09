package com.sisadmin.compras.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class CuentaPagarDto {
	private long id;
	private String razonSocialProveedor;
	private String rucProveedor;
	private String direccionProveedor;
	private long proveedorId;
	private BigDecimal total;
	private String documento;
	private BigDecimal montoTotal;
	private BigDecimal montoPagado;
	private Date fechaVencimiento;
	private String estado;
	
	private Collection<CuentaPagarDetalleDto> detalles = new ArrayList<CuentaPagarDetalleDto>();
	
	
	public String getRazonSocialProveedor() {
		return razonSocialProveedor;
	}
	public void setRazonSocialProveedor(String razonSocialProveedor) {
		this.razonSocialProveedor = razonSocialProveedor;
	}
	public String getRucProveedor() {
		return rucProveedor;
	}
	public void setRucProveedor(String rucProveedor) {
		this.rucProveedor = rucProveedor;
	}
	public String getDireccionProveedor() {
		return direccionProveedor;
	}
	public void setDireccionProveedor(String direccionProveedor) {
		this.direccionProveedor = direccionProveedor;
	}
	public Collection<CuentaPagarDetalleDto> getDetalles() {
		return detalles;
	}
	public void setDetalles(Collection<CuentaPagarDetalleDto> detalles) {
		this.detalles = detalles;
	}
	public long getProveedorId() {
		return proveedorId;
	}
	public void setProveedorId(long proveedorId) {
		this.proveedorId = proveedorId;
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
	public BigDecimal getMontoTotal() {
		return montoTotal;
	}
	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}
	public BigDecimal getMontoPagado() {
		return montoPagado;
	}
	public void setMontoPagado(BigDecimal montoPagado) {
		this.montoPagado = montoPagado;
	}
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	
	

}
