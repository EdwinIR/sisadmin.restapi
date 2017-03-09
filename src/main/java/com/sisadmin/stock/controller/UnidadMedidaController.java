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

import com.sisadmin.entity.UnidadMedida;
import com.sisadmin.repository.UnidadMedidaRepository;
import com.sun.org.apache.xml.internal.utils.UnImplNode;

@Controller
public class UnidadMedidaController {

	@RequestMapping(method=RequestMethod.GET, value="/api/v1/unidadmedida/list")
	public @ResponseBody List<UnidadMedida> todasUnidadesMedidas(){
		return (List<UnidadMedida>) unidadMedidaRepository.unidadesOrdenadas();
	}
	

	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/unidadmedida")	   
	public @ResponseBody String borrarUnidadmedida(@RequestParam(value="id") Long id){
				unidadMedidaRepository.delete(id);
				return "Ok";				
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/unidadmedida/add")
	public @ResponseBody String agregarUnidadMedida(@RequestBody UnidadMedida unidadmedida){
		String respuesta = "OK";
		try {
		
				UnidadMedida nuevaUnidadMedida = new UnidadMedida();
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);	
				nuevaUnidadMedida.setCodigo(unidadmedida.getCodigo());
				nuevaUnidadMedida.setDescripcion(unidadmedida .getDescripcion());
				nuevaUnidadMedida.setAbreviado(unidadmedida.getAbreviado());
				nuevaUnidadMedida.setUsuarioCreacion("UsuarioCreador");
				nuevaUnidadMedida.setFechaCreacion(fechahora);
				nuevaUnidadMedida.setFisico(unidadmedida.getFisico());
				unidadMedidaRepository.save(nuevaUnidadMedida);
	} catch (Exception e) {
		respuesta = "Unidad de Medida u Abreviado Repetido";
		e.printStackTrace();
	}
				return respuesta;
	}			

	@RequestMapping(method=RequestMethod.POST, value="/api/v1/unidadmedida/update")
	public @ResponseBody String actualizarUnidadMedida(@RequestBody UnidadMedida unidadMedidaParam){
				UnidadMedida unidadMedida = (UnidadMedida)unidadMedidaRepository.findOne(unidadMedidaParam.getId());
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				unidadMedida.setCodigo(unidadMedidaParam.getCodigo());
				unidadMedida.setDescripcion(unidadMedidaParam .getDescripcion());
				unidadMedida.setAbreviado(unidadMedidaParam.getAbreviado());
				unidadMedida.setUsuarioModificacion("UsuarioModificador");
				unidadMedida.setFechaModificacion(fechahora);
				unidadMedida.setFisico(unidadMedidaParam.getFisico());
				unidadMedidaRepository.save(unidadMedida);				
				return "OK";
	}

	@Autowired
	private UnidadMedidaRepository unidadMedidaRepository;
	
}
