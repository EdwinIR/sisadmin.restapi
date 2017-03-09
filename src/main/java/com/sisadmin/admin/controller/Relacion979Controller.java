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

import com.sisadmin.admin.dto.Relacion979Dto;
import com.sisadmin.admin.dto.RelacionSeisNueveDto;
import com.sisadmin.entity.CuentaContable;
import com.sisadmin.entity.CuentaDivisionaria;
import com.sisadmin.entity.Relacion979;
import com.sisadmin.repository.CuentaContableRepository;
import com.sisadmin.repository.CuentaDivisionariaRepository;
import com.sisadmin.repository.Relacion979Repository;

@Controller
public class Relacion979Controller {

	
	/**RelacionNuevesietenueve**/
	private Relacion979Dto convertToDto(Relacion979 in) {
		Relacion979Dto dto = new Relacion979Dto();
		dto.setId(in.getId());
		return dto;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/relacion979/list")
	public @ResponseBody List<Relacion979Dto> todasRelaciones979(){
				List<Relacion979> relaciones = (List<Relacion979>) relacion979Repository.findAll();
				List<Relacion979Dto> relacionesDtos = new ArrayList<Relacion979Dto>();
				for (Relacion979 relacion : relaciones) {
					relacionesDtos.add(convertToDto(relacion));
				}
				return relacionesDtos;
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/relacion979")	   
	public @ResponseBody String borrarRelacion979(@RequestParam(value="id") Long id){
				relacion979Repository.delete(id);
				return "Ok";				
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/relacion979/add")
	public @ResponseBody String agregarRelacion979(@RequestBody Relacion979Dto relacion979Dto){
				Relacion979 nuevaRelacion = new Relacion979();	
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				CuentaContable cuentaContable79 = (CuentaContable)cuentaContableRepository.findOne(relacion979Dto.getCuentaContable79Id());				
				nuevaRelacion.setCuentaContable79(cuentaContable79);
				CuentaDivisionaria cuentaDivisionaria9 = (CuentaDivisionaria)cuentaDivisionariaRepository.findOne(relacion979Dto.getCuentaDivisionaria9Id());
				nuevaRelacion.setCuentaDivisionaria9(cuentaDivisionaria9);
				nuevaRelacion.setUsuarioCreacion("UsuarioCreador");
				nuevaRelacion.setFechaCreacion(fechahora);
				relacion979Repository.save(nuevaRelacion);				
				return "OK";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/relacion979/update")
	public @ResponseBody String actualizarRelacion979(@RequestBody RelacionSeisNueveDto relacion979Param){
				Relacion979 relacion979Repo = (Relacion979)relacion979Repository.findOne(relacion979Param.getId());
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				relacion979Repo.setUsuarioModificacion("UsuarioModificador");
				relacion979Repo.setFechaModificacion(fechahora);				
				relacion979Repository.save(relacion979Repo);				
				return "OK";
	}
	
	@Autowired
	Relacion979Repository relacion979Repository;
	@Autowired
	CuentaContableRepository cuentaContableRepository;
	@Autowired
	CuentaDivisionariaRepository cuentaDivisionariaRepository;
}
