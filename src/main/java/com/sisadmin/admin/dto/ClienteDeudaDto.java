package com.sisadmin.admin.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import com.sisadmin.entity.Cliente;

public class ClienteDeudaDto {
	
	private long id;	
	private long clienteId;
	private String razonSocialCliente;
	private String identificadorCliente;
	private BigDecimal montoAdeudado;
	
	private Collection<ClienteDeudaDetalleDto> detalles = new ArrayList<ClienteDeudaDetalleDto>();
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getClienteId() {
		return clienteId;
	}
	public void setClienteId(long clienteId) {
		this.clienteId = clienteId;
	}
	public String getRazonSocialCliente() {
		return razonSocialCliente;
	}
	public void setRazonSocialCliente(String razonSocialCliente) {
		this.razonSocialCliente = razonSocialCliente;
	}
	public BigDecimal getMontoAdeudado() {
		return montoAdeudado;
	}
	public void setMontoAdeudado(BigDecimal montoAdeudado) {
		this.montoAdeudado = montoAdeudado;
	}
	public String getIdentificadorCliente() {
		return identificadorCliente;
	}
	public void setIdentificadorCliente(String identificadorCliente) {
		this.identificadorCliente = identificadorCliente;
	}
	public Collection<ClienteDeudaDetalleDto> getDetalles() {
		return detalles;
	}
	public void setDetalles(Collection<ClienteDeudaDetalleDto> detalles) {
		this.detalles = detalles;
	}
	
	
	

}
