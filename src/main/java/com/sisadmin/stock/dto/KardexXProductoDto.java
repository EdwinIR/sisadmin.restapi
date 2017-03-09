package com.sisadmin.stock.dto;

import java.sql.Date;

public class KardexXProductoDto {
	Date fecha;
	long salida;
	long entrada;
	long saldo;
	String referencia;
	long td;
	long serie;
	long numero;
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public long getSalida() {
		return salida;
	}
	public void setSalida(long salida) {
		this.salida = salida;
	}
	public long getEntrada() {
		return entrada;
	}
	public void setEntrada(long entrada) {
		this.entrada = entrada;
	}
	public long getSaldo() {
		return saldo;
	}
	public void setSaldo(long saldo) {
		this.saldo = saldo;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public long getTd() {
		return td;
	}
	public void setTd(long td) {
		this.td = td;
	}
	public long getSerie() {
		return serie;
	}
	public void setSerie(long serie) {
		this.serie = serie;
	}
	public long getNumero() {
		return numero;
	}
	public void setNumero(long numero) {
		this.numero = numero;
	}
	
	
	
}
