package com.sisadmin.admin.dto;

import java.util.Date;

import com.sisadmin.entity.Departamento;
import com.sisadmin.entity.PuestoTrabajo;
import com.sisadmin.entity.Sede;
import com.sisadmin.entity.TipoTrabajador;

public class TrabajadorDto {

	 	private long id;	   
	   private String dni;
	   private String nombre;
	   private String direccion;
	   private Date fechaNacimiento;
	   private String estadoCivil;
	   private long sedeId;
	   private long departamentoId;
	   private long tipoTrabajadorId;
	   private long puestoTrabajoId;
	   private String descripcionSede;
	   private String descripcionDepartamento;
	   private String descripcionTipoTrabajador;
	   private String descripcionPuestoTrabajo;
	   
	   
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	public long getSedeId() {
		return sedeId;
	}
	public void setSedeId(long sedeId) {
		this.sedeId = sedeId;
	}
	public long getDepartamentoId() {
		return departamentoId;
	}
	public void setDepartamentoId(long departamentoId) {
		this.departamentoId = departamentoId;
	}
	public long getTipoTrabajadorId() {
		return tipoTrabajadorId;
	}
	public void setTipoTrabajadorId(long tipoTrabajadorId) {
		this.tipoTrabajadorId = tipoTrabajadorId;
	}
	public long getPuestoTrabajoId() {
		return puestoTrabajoId;
	}
	public void setPuestoTrabajoId(long puestoTrabajoId) {
		this.puestoTrabajoId = puestoTrabajoId;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcionSede() {
		return descripcionSede;
	}
	public void setDescripcionSede(String descripcionSede) {
		this.descripcionSede = descripcionSede;
	}
	public String getDescripcionDepartamento() {
		return descripcionDepartamento;
	}
	public void setDescripcionDepartamento(String descripcionDepartamento) {
		this.descripcionDepartamento = descripcionDepartamento;
	}
	public String getDescripcionTipoTrabajador() {
		return descripcionTipoTrabajador;
	}
	public void setDescripcionTipoTrabajador(String descripcionTipoTrabajador) {
		this.descripcionTipoTrabajador = descripcionTipoTrabajador;
	}
	public String getDescripcionPuestoTrabajo() {
		return descripcionPuestoTrabajo;
	}
	public void setDescripcionPuestoTrabajo(String descripcionPuestoTrabajo) {
		this.descripcionPuestoTrabajo = descripcionPuestoTrabajo;
	}
	   
	   
	
}
