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

import com.sisadmin.admin.dto.EmpresaDto;
import com.sisadmin.entity.Empresa;
import com.sisadmin.entity.Sede;
import com.sisadmin.repository.EmpresaRepository;
import com.sisadmin.repository.SedeRepository;

@Controller
public class EmpresaController {

	/**Empresa**/
	private EmpresaDto convertToDto(Empresa in) {
		EmpresaDto dto = new EmpresaDto();
		dto.setId(in.getId());
		dto.setCodigo(in.getCodigo());
		dto.setRazonSocial(in.getRazonSocial());
		dto.setRuc(in.getRuc());
		dto.setDireccion(in.getDireccion());
		dto.setDescripcionSede(in.getSede().getDescripcion());
		return dto;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/empresa/list")
	public @ResponseBody List<EmpresaDto> todasEmpresas(){
				List<Empresa> empresas = (List<Empresa>) empresaRepository.findAll();
				List<EmpresaDto> empresasDtos = new ArrayList<EmpresaDto>();
				for (Empresa empresa : empresas) {
					empresasDtos.add(convertToDto(empresa));
				}
				return empresasDtos;
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/empresa")	   
	public @ResponseBody String borrarEmpresa(@RequestParam(value="id") Long id){
				empresaRepository.delete(id);
				return "Ok";				
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/empresa/add")
	public @ResponseBody String agregarEmpresa(@RequestBody EmpresaDto empresaDto){
				Empresa nuevaEmpresa = new Empresa();
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				nuevaEmpresa.setCodigo(empresaDto.getCodigo());
				nuevaEmpresa.setRazonSocial(empresaDto.getRazonSocial());
				nuevaEmpresa.setRuc(empresaDto.getRuc());
				nuevaEmpresa.setDireccion(empresaDto.getDireccion());
				Sede sede = (Sede)sedeRepository.findOne(empresaDto.getSedeId());
				nuevaEmpresa.setSede(sede);
				nuevaEmpresa.setUsuarioCreacion("UsuarioCreador");
				nuevaEmpresa.setFechaCreacion(fechahora);
				empresaRepository.save(nuevaEmpresa);				
				return "OK";
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/empresa/update")
	public @ResponseBody String actualizarEmpresa(@RequestBody EmpresaDto empresaParam){
				Empresa empresaRepo = (Empresa)empresaRepository.findOne(empresaParam.getId());
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				empresaRepo.setCodigo(empresaParam.getCodigo());
				empresaRepo.setRazonSocial(empresaParam.getRazonSocial());
				empresaRepo.setRuc(empresaParam.getRuc());
				empresaRepo.setDireccion(empresaParam.getDireccion());	
				empresaRepo.setUsuarioModificacion("UsuarioModificador");
				empresaRepo.setFechaModificacion(fechahora);
				Sede sede = (Sede)sedeRepository.findOne(empresaParam.getSedeId());
				empresaRepo.setSede(sede);
				empresaRepository.save(empresaRepo);				
				return "OK";
	}
	
	
	@Autowired
	protected EmpresaRepository empresaRepository;
	@Autowired
	protected SedeRepository sedeRepository;
}
