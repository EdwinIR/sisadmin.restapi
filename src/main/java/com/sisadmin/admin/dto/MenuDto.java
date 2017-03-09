package com.sisadmin.admin.dto;

public class MenuDto {
	
	private String codOpcionMenu;
    private String descripcion;
    private String codOpcionMenuPadre;
    private boolean seleccionado;
    
	public String getCodOpcionMenu() {
		return codOpcionMenu;
	}
	public void setCodOpcionMenu(String codOpcionMenu) {
		this.codOpcionMenu = codOpcionMenu;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCodOpcionMenuPadre() {
		return codOpcionMenuPadre;
	}
	public void setCodOpcionMenuPadre(String codOpcionMenuPadre) {
		this.codOpcionMenuPadre = codOpcionMenuPadre;
	}
	public boolean isSeleccionado() {
		return seleccionado;
	}
	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}
    
    

}
