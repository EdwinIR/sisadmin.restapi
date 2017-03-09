package com.sisadmin.stock.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.sisadmin.entity.ProductoDetalle;

public class ProductoDto {

	private long id;
	//private String codigo;
	private String codigoEquivalente;
	private BigDecimal pesoKilogramo;
	//private String codigoBarra;
	private String descripcion;
	//private Boolean afectoIgv;
	private long unidadMedidaId;
	private String unidadMedidaPrincipal;
	
	private long marcaId;
	private long almacenId;
	private long proveedorId;
	private long familiaId;
	private long unidadMedidaAId;
	private long unidadMedidaBId;
	private long unidadMedidaCId;
	private String descripcionMarca;
	private String descripcionFamilia;
	//private ProductoUbicacionDto ubicaciones = new ProductoUbicacionDto();
	
	/*Ciro 02102016*/
	//private Collection<ProductoDetalle> detalles = new ArrayList<>();
	private ProductoDetalleDto productoDetalleA;
	private ProductoDetalleDto productoDetalleB;
	private ProductoDetalleDto productoDetalleC;
	
	
	
	//private long almacenId;
	private long zonaId;
	private long ubicacionId;
	
	
	private boolean activo;
	private boolean tratamientoEspecial;
	private Date fechaUltimaCompra;
	private BigDecimal precioBase;
	private long unidadMedidaPrincipalId;
	
	
	public String getDescripcionMarca() {
		return descripcionMarca;
	}
	public void setDescripcionMarca(String descripcionMarca) {
		this.descripcionMarca = descripcionMarca;
	}
	
	public String getDescripcionFamilia() {
		return descripcionFamilia;
	}
	public void setDescripcionFamilia(String descripcionFamilia) {
		this.descripcionFamilia = descripcionFamilia;
	}
	/*public long getModeloId() {
		return modeloId;
	}
	public void setModeloId(long modeloId) {
		this.modeloId = modeloId;
	}*/
	public long getProveedorId() {
		return proveedorId;
	}
	public void setProveedorId(long proveedorId) {
		this.proveedorId = proveedorId;
	}
	public long getFamiliaId() {
		return familiaId;
	}
	public void setFamiliaId(long familiaId) {
		this.familiaId = familiaId;
	}	
	public long getMarcaId() {
		return marcaId;
	}
	public void setMarcaId(long marcaId) {
		this.marcaId = marcaId;
	}	
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
		
	public String getCodigoEquivalente() {
		return codigoEquivalente;
	}
	public void setCodigoEquivalente(String codigoEquivalente) {
		this.codigoEquivalente = codigoEquivalente;
	}
	public BigDecimal getPesoKilogramo() {
		return pesoKilogramo;
	}
	public void setPesoKilogramo(BigDecimal pesoKilogramo) {
		this.pesoKilogramo = pesoKilogramo;
	}
	public long getUnidadMedidaAId() {
		return unidadMedidaAId;
	}
	public void setUnidadMedidaAId(long unidadMedidaAId) {
		this.unidadMedidaAId = unidadMedidaAId;
	}
	public long getUnidadMedidaBId() {
		return unidadMedidaBId;
	}
	public void setUnidadMedidaBId(long unidadMedidaBId) {
		this.unidadMedidaBId = unidadMedidaBId;
	}
	public long getUnidadMedidaCId() {
		return unidadMedidaCId;
	}
	public void setUnidadMedidaCId(long unidadMedidaCId) {
		this.unidadMedidaCId = unidadMedidaCId;
	}
		
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public boolean isTratamientoEspecial() {
		return tratamientoEspecial;
	}
	public void setTratamientoEspecial(boolean tratamientoEspecial) {
		this.tratamientoEspecial = tratamientoEspecial;
	}
	public Date getFechaUltimaCompra() {
		return fechaUltimaCompra;
	}
	public void setFechaUltimaCompra(Date fechaUltimaCompra) {
		this.fechaUltimaCompra = fechaUltimaCompra;
	}
	public BigDecimal getPrecioBase() {
		return precioBase;
	}
	public void setPrecioBase(BigDecimal precioBase) {
		this.precioBase = precioBase;
	}
	public long getUnidadMedidaPrincipalId() {
		return unidadMedidaPrincipalId;
	}
	public void setUnidadMedidaPrincipalId(long unidadMedidaPrincipalId) {
		this.unidadMedidaPrincipalId = unidadMedidaPrincipalId;
	}
	public ProductoDetalleDto getProductoDetalleA() {
		return productoDetalleA;
	}
	public void setProductoDetalleA(ProductoDetalleDto productoDetalleA) {
		this.productoDetalleA = productoDetalleA;
	}
	public ProductoDetalleDto getProductoDetalleB() {
		return productoDetalleB;
	}
	public void setProductoDetalleB(ProductoDetalleDto productoDetalleB) {
		this.productoDetalleB = productoDetalleB;
	}
	public ProductoDetalleDto getProductoDetalleC() {
		return productoDetalleC;
	}
	public void setProductoDetalleC(ProductoDetalleDto productoDetalleC) {
		this.productoDetalleC = productoDetalleC;
	}
	
	public long getAlmacenId() {
		return almacenId;
	}
	public void setAlmacenId(long almacenId) {
		this.almacenId = almacenId;
	}
	public long getZonaId() {
		return zonaId;
	}
	public void setZonaId(long zonaId) {
		this.zonaId = zonaId;
	}
	public long getUbicacionId() {
		return ubicacionId;
	}
	public void setUbicacionId(long ubicacionId) {
		this.ubicacionId = ubicacionId;
	}
	public long getUnidadMedidaId() {
		return unidadMedidaId;
	}
	public void setUnidadMedidaId(long unidadMedidaId) {
		this.unidadMedidaId = unidadMedidaId;
	}
	public String getUnidadMedidaPrincipal() {
		return unidadMedidaPrincipal;
	}
	public void setUnidadMedidaPrincipal(String unidadMedidaPrincipal) {
		this.unidadMedidaPrincipal = unidadMedidaPrincipal;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	
	
	
}
