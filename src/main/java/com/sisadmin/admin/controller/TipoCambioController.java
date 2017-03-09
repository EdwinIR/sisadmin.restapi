package com.sisadmin.admin.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sisadmin.entity.TipoCambio;
import com.sisadmin.repository.TipoCambioRepository;

@Controller
public class TipoCambioController {

	/**TipoCambio**/	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/tipocambio/list")
	public @ResponseBody List<TipoCambio> todosTiposCambios(){
				return (List<TipoCambio>) tipoCambioRepository.findAll();
	}
	

	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/tipocambio")	   
	public @ResponseBody String borrarTipoCambio(@RequestParam(value="id") Long id){
				tipoCambioRepository.delete(id);
				return "Ok";				
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/tipocambio/add")
	public @ResponseBody String agregarTipoCambio(@RequestBody TipoCambio tipocambio){
				TipoCambio nuevoTipoCambio = new TipoCambio();
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				nuevoTipoCambio.setFecha(tipocambio.getFecha());
				nuevoTipoCambio.setTipoCompra(tipocambio.getTipoCompra());
				nuevoTipoCambio.setTipoVenta(tipocambio.getTipoVenta());
				nuevoTipoCambio.setUsuarioCreacion("UsuarioCreador");
				nuevoTipoCambio.setFechaCreacion(fechahora);
				tipoCambioRepository.save(nuevoTipoCambio);				
				return "OK";
	}			

	@RequestMapping(method=RequestMethod.POST, value="/api/v1/tipocambio/update")
	public @ResponseBody String actualizarTipoCambio(@RequestBody TipoCambio tipocambioParam){
				TipoCambio tipoCambioRepo = (TipoCambio)tipoCambioRepository.findOne(tipocambioParam.getId());
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				tipoCambioRepo.setFecha(tipocambioParam.getFecha());
				tipoCambioRepo.setUsuarioModificacion("UsuarioModificador");
				tipoCambioRepo.setFechaModificacion(fechahora);
				//tipoCambioRepo.setTipoCompra(tipoCambioParam.getTipoCompra());
				//tipoCambioRepo.setTipoVenta(tipoCambioParam.getTipoVenta());
				tipoCambioRepository.save(tipoCambioRepo);				
				return "OK";
	}

	@Autowired
	TipoCambioRepository tipoCambioRepository;
	
	
}
