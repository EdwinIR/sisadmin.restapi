package com.sisadmin.general.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sisadmin.entity.Departamento;
import com.sisadmin.repository.DepartamentoRepository;

@Controller
public class DepartamentoController {

	
	/**Departamento**/
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/departamento/list")
	public @ResponseBody List<Departamento> todosDepartamentos(){
				return (List<Departamento>) departamentoRepository.findAll();
	}	

	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/departamento")	   
	public @ResponseBody String borrarDepartamento(@RequestParam(value="id") Long id){
				departamentoRepository.delete(id);
				return "Ok";				
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/departamento/add")
	public @ResponseBody String agregarDepartamento(@RequestBody Departamento departamento){
				Departamento nuevoDepartamento = new Departamento();
				nuevoDepartamento.setCodigo(departamento.getCodigo());
				nuevoDepartamento.setDescripcion(departamento.getDescripcion());
				departamentoRepository.save(nuevoDepartamento);				
				return "OK";
	}				

	@RequestMapping(method=RequestMethod.POST, value="/api/v1/departamento/update")
	public @ResponseBody String actualizarDepartamento(@RequestBody Departamento departamentoParam){
				Departamento departamentoRepo = (Departamento)departamentoRepository.findOne(departamentoParam.getId());
				departamentoRepo.setCodigo(departamentoParam.getCodigo());
				departamentoRepo.setDescripcion(departamentoParam.getDescripcion());
				departamentoRepository.save(departamentoRepo);				
				return "OK";
	}
	
	@Autowired
	protected DepartamentoRepository departamentoRepository;
	
	
}
