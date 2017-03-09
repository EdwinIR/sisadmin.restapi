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

import com.sisadmin.entity.AsientoDiario;
import com.sisadmin.repository.AsientoDiarioRepository;

@Controller
public class AsientoDiarioController {

	
	/**AsientoDiario**/
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/asientodiario/list")
	public @ResponseBody List<AsientoDiario> todosAsientosDiarios(){
				return (List<AsientoDiario>) asientoDiarioRepository.findAll();
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/asientodiario")	   
	public @ResponseBody String borrarAsientoDiario(@RequestParam(value="id") Long id){
				asientoDiarioRepository.delete(id);
				return "Ok";				
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/asientodiario/add")
	public @ResponseBody String agregarAsientoDiario(@RequestBody AsientoDiario asientodiario){
				AsientoDiario nuevoAsientoDiario = new AsientoDiario();
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				nuevoAsientoDiario.setFecha(asientodiario.getFecha());
				nuevoAsientoDiario.setNumeroComprobante(asientodiario.getNumeroComprobante());
				nuevoAsientoDiario.setGlosa(asientodiario.getGlosa());
				nuevoAsientoDiario.setUsuarioCreacion("UsuarioCreador");
				nuevoAsientoDiario.setFechaCreacion(fechahora);
				asientoDiarioRepository.save(nuevoAsientoDiario);				
				return "OK";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/asientodiario/update")
	public @ResponseBody String actualizarAsientoDiario(@RequestBody AsientoDiario asientodiarioParam){
				AsientoDiario asientodiarioRepo = (AsientoDiario)asientoDiarioRepository.findOne(asientodiarioParam.getId());
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				asientodiarioRepo.setFecha(asientodiarioParam.getFecha());
				asientodiarioRepo.setNumeroComprobante(asientodiarioParam.getNumeroComprobante());
				asientodiarioRepo.setGlosa(asientodiarioParam.getGlosa());
				asientodiarioRepo.setUsuarioModificacion("UsuarioModificador");
				asientodiarioRepo.setFechaModificacion(fechahora);
				asientoDiarioRepository.save(asientodiarioRepo);				
				return "OK";
	}
	
	@Autowired
	protected AsientoDiarioRepository asientoDiarioRepository;
	
}
