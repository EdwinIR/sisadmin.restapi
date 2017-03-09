package com.sisadmin.stock.controller;

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

import com.sisadmin.entity.Almacen;
import com.sisadmin.repository.AlmacenRepository;

@Controller
public class AlmacenController {
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/almacen/list")
	public @ResponseBody List<Almacen> todosAlmacenes(){
				return (List<Almacen>) almacenRepository.findAll();
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/almacen/listval")
	public @ResponseBody List<Almacen> todosAlmacenesVal(@RequestBody long idAlmacen){
				return (List<Almacen>) almacenRepository.filtroAlmacenes(idAlmacen);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/almacen")	   
	public @ResponseBody String borrarAlmacen(@RequestParam(value="id") Long id){
				almacenRepository.delete(id);
				return "Ok";				
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/almacen/add")
	public @ResponseBody String agregarAlmacen(@RequestBody Almacen almacen){
				Almacen nuevoAlmacen = new Almacen();	
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				//nuevoAlmacen.setCodigo(almacen.getCodigo());
				nuevoAlmacen.setDescripcion(almacen.getDescripcion());	
				nuevoAlmacen.setUsuarioCreacion("UsuarioCreador");
				nuevoAlmacen.setFechaCreacion(fechahora);
				nuevoAlmacen.setDireccion(almacen.getDireccion());
				nuevoAlmacen.setTelefono(almacen.getTelefono());
				almacenRepository.save(nuevoAlmacen);				
				return "OK";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/almacen/update")
	public @ResponseBody String actualizarAlmacen(@RequestBody Almacen almacenParam){
				Almacen almacenRepo = (Almacen)almacenRepository.findOne(almacenParam.getId());
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				//almacenRepo.setCodigo(almacenParam.getCodigo());
				almacenRepo.setDescripcion(almacenParam.getDescripcion());
				almacenRepo.setUsuarioModificacion("UsuarioModificador");
				almacenRepo.setFechaModificacion(fechahora);
				almacenRepo.setDireccion(almacenParam.getDireccion());
				almacenRepo.setTelefono(almacenParam.getTelefono());
				almacenRepository.save(almacenRepo);			
				return "OK";
	}
	@Autowired
	private AlmacenRepository almacenRepository;

}
