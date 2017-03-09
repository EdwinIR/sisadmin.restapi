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

import com.sisadmin.admin.dto.CuentaDivisionariaDto;
import com.sisadmin.entity.CuentaDivisionaria;
import com.sisadmin.entity.PlanCuenta;
import com.sisadmin.repository.CuentaDivisionariaRepository;
import com.sisadmin.repository.PlanCuentaRepository;

@Controller
public class CuentaDivisionariaController {

	/**CuentaDivisionaria**/
	private CuentaDivisionariaDto convertToDto(CuentaDivisionaria in) {
		CuentaDivisionariaDto dto = new CuentaDivisionariaDto();
		dto.setId(in.getId());
		dto.setCodigo(in.getCodigo());
		dto.setDescripcion(in.getDescripcion());
		dto.setPlanCuentaId(in.getPlanCuenta().getId());
		//dto.setCodigoPlanCuenta(in.getPlanCuenta().getCodigo());
		dto.setDescripcionPlanCuenta(in.getPlanCuenta().getDescripcion());
		
		return dto;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/cuentadivisionaria/list")
	public @ResponseBody List<CuentaDivisionariaDto> todasCuentasDivisionarias(){
				List<CuentaDivisionaria> cuentadivisionarias = (List<CuentaDivisionaria>) cuentaDivisionariaRepository.findAll();
				List<CuentaDivisionariaDto> cuentadivisionariasDtos = new ArrayList<CuentaDivisionariaDto>();
				for (CuentaDivisionaria cuentadivisionaria : cuentadivisionarias) {
					cuentadivisionariasDtos.add(convertToDto(cuentadivisionaria));
				}
				return cuentadivisionariasDtos;
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/cuentadivisionaria")	   
	public @ResponseBody String borrarCuentaDivisionaria(@RequestParam(value="id") Long id){
				cuentaDivisionariaRepository.delete(id);
				return "Ok";				
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/cuentadivisionaria/add")
	public @ResponseBody String agregarCuentaDivisionaria(@RequestBody CuentaDivisionariaDto cuentaDivisionariaDto){
				CuentaDivisionaria nuevaCuentaDivisionaria = new CuentaDivisionaria();
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				nuevaCuentaDivisionaria.setCodigo(cuentaDivisionariaDto.getCodigo());
				nuevaCuentaDivisionaria.setDescripcion(cuentaDivisionariaDto.getDescripcion());						
				PlanCuenta planCuenta = (PlanCuenta)planCuentaRepository.findOne(cuentaDivisionariaDto.getPlanCuentaId());
				nuevaCuentaDivisionaria.setPlanCuenta(planCuenta);
				nuevaCuentaDivisionaria.setUsuarioCreacion("UsuarioCreador");
				nuevaCuentaDivisionaria.setFechaCreacion(fechahora);
				cuentaDivisionariaRepository.save(nuevaCuentaDivisionaria);				
				return "OK";
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/cuentadivisionaria/update")
	public @ResponseBody String actualizarCuentaDivisionaria(@RequestBody CuentaDivisionariaDto cuentaDivisionariaParam){
				CuentaDivisionaria cuentaDivisionariaRepo = (CuentaDivisionaria)cuentaDivisionariaRepository.findOne(cuentaDivisionariaParam.getId());
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				cuentaDivisionariaRepo.setCodigo(cuentaDivisionariaParam.getCodigo());
				cuentaDivisionariaRepo.setDescripcion(cuentaDivisionariaParam.getDescripcion());	
				cuentaDivisionariaRepo.setUsuarioModificacion("UsuarioModificador");
				cuentaDivisionariaRepo.setFechaModificacion(fechahora);				
				PlanCuenta planCuenta = (PlanCuenta)planCuentaRepository.findOne(cuentaDivisionariaParam.getPlanCuentaId());
				cuentaDivisionariaRepo.setPlanCuenta(planCuenta);
				cuentaDivisionariaRepository.save(cuentaDivisionariaRepo);				
				return "OK";
	}

	@Autowired
	protected CuentaDivisionariaRepository cuentaDivisionariaRepository;
	
	@Autowired
	protected PlanCuentaRepository planCuentaRepository;
	
	
}
