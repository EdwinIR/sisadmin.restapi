package com.sisadmin.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sisadmin.entity.Modelo;
import com.sisadmin.repository.ModeloRepository;

@Controller
public class ModeloController {
	
	/**Modelo**/
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/modelo/list")
	public @ResponseBody List<Modelo> todosModelos(){
				return (List<Modelo>) modeloRepository.findAll();
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/modelo")	   
	public @ResponseBody String borrarModelo(@RequestParam(value="id") Long id){
				modeloRepository.delete(id);
				return "Ok";				
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/modelo/add")
	public @ResponseBody String agregarModelo(@RequestBody Modelo modelo){
				Modelo nuevoModelo = new Modelo();
				nuevoModelo.setCodigo(modelo.getCodigo());
				nuevoModelo.setDescripcion(modelo.getDescripcion());
				modeloRepository.save(nuevoModelo);				
				return "OK";
	}			

	@RequestMapping(method=RequestMethod.POST, value="/api/v1/modelo/update")
	public @ResponseBody String actualizarModelo(@RequestBody Modelo modeloParam){
				Modelo modeloRepo = (Modelo)modeloRepository.findOne(modeloParam.getId());
				modeloRepo.setCodigo(modeloParam.getCodigo());
				modeloRepo.setDescripcion(modeloParam.getDescripcion());
				modeloRepository.save(modeloRepo);				
				return "OK";
	}

	@Autowired
	protected ModeloRepository modeloRepository;

}
