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

import com.sisadmin.entity.Periodo;
import com.sisadmin.repository.PeriodoRepository;


@Controller
public class PeriodoController {

	
	/**Periodo**/
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/periodo/list")
	public @ResponseBody List<Periodo> todosPeriodos(){
				return (List<Periodo>) periodoRepository.findAll();
	}	

	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/periodo")	   
	public @ResponseBody String borrarPeriodo(@RequestParam(value="id") Long id){
				periodoRepository.delete(id);
				return "Ok";				
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/periodo/add")
	public @ResponseBody String agregarPeriodo(@RequestBody Periodo periodo){
				Periodo nuevoPeriodo = new Periodo();
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				nuevoPeriodo.setCodigo(periodo.getCodigo());
				nuevoPeriodo.setTasaIgv(periodo.getTasaIgv());
				nuevoPeriodo.setTasaIsc(periodo.getTasaIsc());
				nuevoPeriodo.setTasaIes(periodo.getTasaIes());
				nuevoPeriodo.setMit(periodo.getMit());
				nuevoPeriodo.setUsuarioCreacion("UsuarioCreador");
				nuevoPeriodo.setFechaCreacion(fechahora);
				periodoRepository.save(nuevoPeriodo);				
				return "OK";
	}			

	@RequestMapping(method=RequestMethod.POST, value="/api/v1/periodo/update")
	public @ResponseBody String actualizarFamilia(@RequestBody Periodo periodoParam){
				Periodo periodoRepo = (Periodo)periodoRepository.findOne(periodoParam.getId());
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				periodoRepo.setCodigo(periodoParam.getCodigo());
				periodoRepo.setTasaIgv(periodoParam.getTasaIgv());
				periodoRepo.setTasaIsc(periodoParam.getTasaIsc());
				periodoRepo.setTasaIes(periodoParam.getTasaIes());
				periodoRepo.setMit(periodoParam.getMit());
				periodoRepo.setUsuarioModificacion("UsuarioModificador");
				periodoRepo.setFechaModificacion(fechahora);
				periodoRepository.save(periodoRepo);				
				return "OK";
	}

	@Autowired
	protected PeriodoRepository periodoRepository;
}
