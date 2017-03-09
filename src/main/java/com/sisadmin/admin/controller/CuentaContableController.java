package com.sisadmin.admin.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sisadmin.admin.dto.CuentaContableDto;
import com.sisadmin.entity.CuentaContable;
import com.sisadmin.entity.CuentaDivisionaria;
import com.sisadmin.repository.CuentaContableRepository;
import com.sisadmin.repository.CuentaDivisionariaRepository;

public class CuentaContableController {
	
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/cuentacontable/list")
	public @ResponseBody List<CuentaContableDto> todasCuentasContables(){
				List<CuentaContable> cuentacontables = (List<CuentaContable>) cuentaContableRepository.findAll();
				List<CuentaContableDto> cuentacontablesDtos = new ArrayList<CuentaContableDto>();
				for (CuentaContable cuentacontable : cuentacontables) {
					//cuentacontablesDtos.add(convertToDto(cuentacontable));
				}
				return cuentacontablesDtos;
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/cuentacontable")	   
	public @ResponseBody String borrarCuentaContable(@RequestParam(value="id") Long id){
				cuentaContableRepository.delete(id);
				return "Ok";				
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/cuentacontable/add")
	public @ResponseBody String agregarCuentaContable(@RequestBody CuentaContableDto cuentaContableDto){
				CuentaContable nuevaCuentaContable = new CuentaContable();
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				nuevaCuentaContable.setCodigo(cuentaContableDto.getCodigo());
				nuevaCuentaContable.setDescripcion(cuentaContableDto.getDescripcion());						
				CuentaDivisionaria cuentaDivisionaria = (CuentaDivisionaria)cuentaDivisionariaRepository.findOne(cuentaContableDto.getCuentaDivisionariaId());
				nuevaCuentaContable.setCuentaDivisionaria(cuentaDivisionaria);		
				nuevaCuentaContable.setUsuarioCreacion("UsuarioCreador");
				nuevaCuentaContable.setFechaCreacion(fechahora);
				cuentaContableRepository.save(nuevaCuentaContable);				
				return "OK";
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/cuentacontable/update")
	public @ResponseBody String actualizarCuentaContable(@RequestBody CuentaContableDto cuentaContableParam){
				CuentaContable cuentaContableRepo = (CuentaContable)cuentaContableRepository.findOne(cuentaContableParam.getId());
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				cuentaContableRepo.setCodigo(cuentaContableParam.getCodigo());
				cuentaContableRepo.setDescripcion(cuentaContableParam.getDescripcion());		
				cuentaContableRepo.setUsuarioModificacion("UsuarioModificador");
				cuentaContableRepo.setFechaModificacion(fechahora);
				CuentaDivisionaria cuentaDivisionaria = (CuentaDivisionaria)cuentaDivisionariaRepository.findOne(cuentaContableParam.getCuentaDivisionariaId());
				cuentaContableRepo.setCuentaDivisionaria(cuentaDivisionaria);
				cuentaContableRepository.save(cuentaContableRepo);				
				return "OK";
	}

	@Autowired
	protected CuentaContableRepository cuentaContableRepository;
	
	@Autowired
	protected CuentaDivisionariaRepository cuentaDivisionariaRepository;
	
	
	
}
