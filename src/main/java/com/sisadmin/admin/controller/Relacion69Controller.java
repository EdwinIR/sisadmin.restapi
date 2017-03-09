package com.sisadmin.admin.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sisadmin.admin.dto.RelacionSeisNueveDto;
import com.sisadmin.entity.CuentaContable;
import com.sisadmin.entity.RelacionSeisNueve;
import com.sisadmin.repository.CuentaContableRepository;
import com.sisadmin.repository.CuentaDivisionariaRepository;
import com.sisadmin.repository.RelacionSeisNueveRepository;

@Controller
public class Relacion69Controller {

	
	/**RelacionSeisNueveDto**/
	private RelacionSeisNueveDto convertToDto(RelacionSeisNueve in) {
		RelacionSeisNueveDto dto = new RelacionSeisNueveDto();
		dto.setId(in.getId());
		return dto;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/relacionseisnueve/list")
	public @ResponseBody List<RelacionSeisNueveDto> todasRelacionesSeisNueve(){
				List<RelacionSeisNueve> relaciones = (List<RelacionSeisNueve>) relacionSeisNueveRepository.findAll();
				List<RelacionSeisNueveDto> relacionesDtos = new ArrayList<RelacionSeisNueveDto>();
				for (RelacionSeisNueve relacion : relaciones) {
					relacionesDtos.add(convertToDto(relacion));
				}
				return relacionesDtos;
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/relacionseisnueve")	   
	public @ResponseBody String borrarRelacion(@RequestParam(value="id") Long id){
				relacionSeisNueveRepository.delete(id);
				return "Ok";				
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/relacionseisnueve/add")
	public @ResponseBody String agregarRelacion(@RequestBody RelacionSeisNueveDto relacionSeisNueveDto){
				RelacionSeisNueve nuevaRelacion = new RelacionSeisNueve();	
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				CuentaContable cuentaContableSeis = (CuentaContable)cuentaContableRepository.findOne(relacionSeisNueveDto.getCuentaContableSeisId());				
				nuevaRelacion.setCuentaContableSeis(cuentaContableSeis);
				CuentaContable cuentaContableNueve = (CuentaContable)cuentaContableRepository.findOne(relacionSeisNueveDto.getCuentaContableNueveId());				
				nuevaRelacion.setCuentaContableSeis(cuentaContableNueve);	
				nuevaRelacion.setUsuarioCreacion("UsuarioCreador");
				nuevaRelacion.setFechaCreacion(fechahora);
				relacionSeisNueveRepository.save(nuevaRelacion);				
				return "OK";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/relacionseisnueve/update")
	public @ResponseBody String actualizarRelacion(@RequestBody RelacionSeisNueveDto relacionSeisNueveParam){
				RelacionSeisNueve relacionSeisNueveRepo = (RelacionSeisNueve)relacionSeisNueveRepository.findOne(relacionSeisNueveParam.getId());
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				relacionSeisNueveRepo.setUsuarioModificacion("UsuarioModificador");
				relacionSeisNueveRepo.setFechaModificacion(fechahora);
				relacionSeisNueveRepository.save(relacionSeisNueveRepo);				
				return "OK";
	}
	
	@Autowired
	RelacionSeisNueveRepository relacionSeisNueveRepository;
	@Autowired
	CuentaContableRepository cuentaContableRepository;
	@Autowired
	CuentaDivisionariaRepository cuentaDivisionariaRepository;
	
}
