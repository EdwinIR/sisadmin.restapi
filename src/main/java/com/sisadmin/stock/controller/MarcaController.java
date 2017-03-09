package com.sisadmin.stock.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;





import com.sisadmin.entity.Marca;
import com.sisadmin.repository.MarcaRepository;

@Controller
public class MarcaController {

	
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/marca/list")
	public @ResponseBody List<Marca> todasMarcas(){
				return (List<Marca>) marcaRepository.findAll();
	}	
	
	

	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/marca")	   
	public @ResponseBody String borrarMarca(@RequestParam(value="id") Long id){
				marcaRepository.delete(id);
				return "Ok";				
	}	
	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/marca/add")
	public @ResponseBody String agregarMarca(@RequestBody Marca marca){		
				String respuesta = "OK";
				try {
					Marca nuevaMarca = new Marca();
					Date fecha = new Date(); 
					long hora = fecha.getTime();
					Timestamp fechahora = new Timestamp(hora);
					nuevaMarca.setDescripcion(marca.getDescripcion());
					nuevaMarca.setUsuarioCreacion("UsuarioCreador");
					nuevaMarca.setFechaCreacion(fechahora);
					nuevaMarca.setId(UUID.randomUUID().toString());
					nuevaMarca.setCodigo(marca.getCodigo());
					marcaRepository.save(nuevaMarca);
				} catch (Exception e) {
					respuesta = "Marca Repetida";
					e.printStackTrace();
				}
				return respuesta;
	}

	@RequestMapping(method=RequestMethod.POST, value="/api/v1/marca/update")
	public @ResponseBody String actualizarMarca(@RequestBody Marca marcaParam){
				Marca marcaRepo = (Marca)marcaRepository.findOne(marcaParam.getIdSa());
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora = new Timestamp(hora);
				marcaRepo.setCodigo(marcaParam.getCodigo());
				marcaRepo.setDescripcion(marcaParam.getDescripcion());
				marcaRepo.setUsuarioModificacion("UsuarioModificador");
				marcaRepo.setFechaModificacion(fechahora);
				marcaRepository.save(marcaRepo);				
				return "OK";
	}		
	@Autowired
	private MarcaRepository marcaRepository;
}
