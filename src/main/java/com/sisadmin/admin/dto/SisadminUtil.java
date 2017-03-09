package com.sisadmin.admin.dto;

import com.sisadmin.entity.Producto;
import com.sisadmin.entity.ProductoDetalle;

public class SisadminUtil {

	public static String obtenerUnidadMedidaPpal(Producto p){
		String ppal = p.getUnidadMedidaPrincipal();
		ProductoDetalle pd = null;
		if(ppal.equalsIgnoreCase("A")){pd = p.getProductoDetalleA();}
		if(ppal.equalsIgnoreCase("B")){pd = p.getProductoDetalleB();}
		if(ppal.equalsIgnoreCase("C")){pd = p.getProductoDetalleC();}
		if(pd!=null) {return pd.getUnidadMedida().getDescripcion();}
		return "";		
	}
	
	public static ProductoDetalle obtenerProductoDetalle(Producto p){
		String ppal = p.getUnidadMedidaPrincipal();
		ProductoDetalle pd = null;
		if(ppal.equalsIgnoreCase("A")){pd = p.getProductoDetalleA();}
		if(ppal.equalsIgnoreCase("B")){pd = p.getProductoDetalleB();}
		if(ppal.equalsIgnoreCase("C")){pd = p.getProductoDetalleC();}
		return pd;
	}


}
