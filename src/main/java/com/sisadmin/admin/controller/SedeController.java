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

import com.sisadmin.entity.Sede;
import com.sisadmin.repository.SedeRepository;


@Controller
public class SedeController {
	
	/**Sede**/
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/sede/list")
	public @ResponseBody List<Sede> todasSedes(){
				return (List<Sede>) sedeRepository.findAll();
	}	

	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/sede")	   
	public @ResponseBody String borrarSede(@RequestParam(value="id") Long id){
				sedeRepository.delete(id);
				return "Ok";				
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/sede/add")
	public @ResponseBody String agregarSede(@RequestBody Sede sede){
				Sede nuevaSede = new Sede();
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				nuevaSede.setCodigo(sede.getCodigo());
				nuevaSede.setDescripcion(sede.getDescripcion());
				nuevaSede.setDireccion(sede.getDireccion());
				nuevaSede.setUsuarioCreacion("UsuarioCreador");
				nuevaSede.setFechaCreacion(fechahora);
				sedeRepository.save(nuevaSede);				
				return "OK";
	}				

	@RequestMapping(method=RequestMethod.POST, value="/api/v1/sede/update")
	public @ResponseBody String actualizarSede(@RequestBody Sede sedeParam){
				Sede sedeRepo = (Sede)sedeRepository.findOne(sedeParam.getId());
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				sedeRepo.setCodigo(sedeParam.getCodigo());
				sedeRepo.setDescripcion(sedeParam.getDescripcion());
				sedeRepo.setDireccion(sedeParam.getDireccion());
				sedeRepo.setUsuarioModificacion("UsuarioModificador");
				sedeRepo.setFechaModificacion(fechahora);
				sedeRepository.save(sedeRepo);				
				return "OK";
	}


	@Autowired
	SedeRepository sedeRepository;
	
}
