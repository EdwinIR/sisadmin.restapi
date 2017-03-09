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

import com.sisadmin.entity.PlanCuenta;
import com.sisadmin.repository.PlanCuentaRepository;

@Controller
public class PlanCuentaController {


	/**PlanCuenta**/
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/plancuenta/list")
	public @ResponseBody List<PlanCuenta> todosPlanesCuentas(){
				return (List<PlanCuenta>) planCuentaRepository.findAll();
	}	

	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/plancuenta")	   
	public @ResponseBody String borrarPlanCuenta(@RequestParam(value="id") Long id){
				planCuentaRepository.delete(id);
				return "Ok";				
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/plancuenta/add")
	public @ResponseBody String agregarPlanCuenta(@RequestBody PlanCuenta plancuenta){
				PlanCuenta nuevoPlanCuenta = new PlanCuenta();				
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				nuevoPlanCuenta.setCodigo(plancuenta.getCodigo());
				nuevoPlanCuenta.setDescripcion(plancuenta.getDescripcion());
				nuevoPlanCuenta.setInventarioActivo(plancuenta.getInventarioActivo());
				nuevoPlanCuenta.setInventarioPasivo(plancuenta.getInventarioPasivo());
				nuevoPlanCuenta.setNaturalezaPerdida(plancuenta.getNaturalezaPerdida());
				nuevoPlanCuenta.setNaturalezaGanancia(plancuenta.getNaturalezaGanancia());
				nuevoPlanCuenta.setFuncionPerdida(plancuenta.getFuncionPerdida());
				nuevoPlanCuenta.setFuncionGanancia(plancuenta.getFuncionGanancia());
				nuevoPlanCuenta.setUsuarioCreacion("UsuarioCreador");
				nuevoPlanCuenta.setFechaCreacion(fechahora);
				planCuentaRepository.save(nuevoPlanCuenta);				
				return "OK";
	}			

	@RequestMapping(method=RequestMethod.POST, value="/api/v1/plancuenta/update")
	public @ResponseBody String actualizarPlanCuenta(@RequestBody PlanCuenta planCuentaParam){
				PlanCuenta planCuentaRepo = (PlanCuenta)planCuentaRepository.findOne(planCuentaParam.getId());
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				planCuentaRepo.setCodigo(planCuentaParam.getCodigo());
				planCuentaRepo.setDescripcion(planCuentaParam.getDescripcion());
				planCuentaRepo.setInventarioActivo(planCuentaParam.getInventarioActivo());
				planCuentaRepo.setInventarioPasivo(planCuentaParam.getInventarioPasivo());
				planCuentaRepo.setNaturalezaPerdida(planCuentaParam.getNaturalezaPerdida());
				planCuentaRepo.setNaturalezaGanancia(planCuentaParam.getNaturalezaGanancia());
				planCuentaRepo.setFuncionPerdida(planCuentaParam.getFuncionPerdida());
				planCuentaRepo.setFuncionGanancia(planCuentaParam.getFuncionGanancia());
				planCuentaRepo.setUsuarioModificacion("UsuarioModificador");
				planCuentaRepo.setFechaModificacion(fechahora);
				planCuentaRepository.save(planCuentaRepo);				
				return "OK";
	}


	@Autowired
	private PlanCuentaRepository planCuentaRepository;
}
