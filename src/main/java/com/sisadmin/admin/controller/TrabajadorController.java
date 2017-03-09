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

import com.sisadmin.admin.dto.TrabajadorDto;
import com.sisadmin.entity.Departamento;
import com.sisadmin.entity.PuestoTrabajo;
import com.sisadmin.entity.Sede;
import com.sisadmin.entity.TipoTrabajador;
import com.sisadmin.entity.Trabajador;
import com.sisadmin.repository.DepartamentoRepository;
import com.sisadmin.repository.PuestoTrabajoRepository;
import com.sisadmin.repository.SedeRepository;
import com.sisadmin.repository.TipoTrabajadorRepository;
import com.sisadmin.repository.TrabajadorRepository;

@Controller
public class TrabajadorController {

	
	private TrabajadorDto convertToDto(Trabajador in) {
		TrabajadorDto dto = new TrabajadorDto();
		dto.setId(in.getId());
		dto.setDni(in.getDni());
		dto.setNombre(in.getNombre());
		dto.setDireccion(in.getDireccion());
		dto.setDescripcionSede(in.getSede().getDescripcion());
		dto.setDescripcionDepartamento(in.getDepartamento().getDescripcion());
		dto.setDescripcionTipoTrabajador(in.getTipoTrabajador().getDescripcion());
		dto.setDescripcionPuestoTrabajo(in.getPuestoTrabajo().getDescripcion());
		
		dto.setFechaNacimiento(in.getFechaNacimiento());
		dto.setEstadoCivil(in.getEstadoCivil());	
		return dto;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/trabajador/list")
	public @ResponseBody List<TrabajadorDto> todosTrabajadores(){
				List<Trabajador> trabajadores = (List<Trabajador>) trabajadorRepository.findAll();
				List<TrabajadorDto> trabajadoresDtos = new ArrayList<TrabajadorDto>();
				for (Trabajador trabajador : trabajadores) {
					trabajadoresDtos.add(convertToDto(trabajador));
				}
				return trabajadoresDtos;
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/trabajador")	   
	public @ResponseBody String borrarTrabajador(@RequestParam(value="id") Long id){
				trabajadorRepository.delete(id);
				return "Ok";				
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/trabajador/add")
	public @ResponseBody String agregarTrabajador(@RequestBody TrabajadorDto trabajadorDto){
				Trabajador nuevoTrabajador = new Trabajador();		
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				nuevoTrabajador.setDni(trabajadorDto.getDni());
				nuevoTrabajador.setNombre(trabajadorDto.getNombre());
				nuevoTrabajador.setDireccion(trabajadorDto.getDireccion());
				nuevoTrabajador.setFechaNacimiento(trabajadorDto.getFechaNacimiento());
				nuevoTrabajador.setEstadoCivil(trabajadorDto.getEstadoCivil());				
				Sede sede = (Sede)sedeRepository.findOne(trabajadorDto.getSedeId());
				nuevoTrabajador.setSede(sede);
				Departamento departamento = (Departamento)departamentoRepository.findOne(trabajadorDto.getDepartamentoId());
				nuevoTrabajador.setDepartamento(departamento);
				TipoTrabajador tipotrabajador = (TipoTrabajador)tipoTrabajadorRepository.findOne(trabajadorDto.getTipoTrabajadorId());
				nuevoTrabajador.setTipoTrabajador(tipotrabajador);
				PuestoTrabajo puestotrabajo = (PuestoTrabajo)puestoTrabajoRepository.findOne(trabajadorDto.getPuestoTrabajoId());
				nuevoTrabajador.setPuestoTrabajo(puestotrabajo);
				nuevoTrabajador.setUsuarioCreacion("UsuarioCreador");
				nuevoTrabajador.setFechaCreacion(fechahora);
				trabajadorRepository.save(nuevoTrabajador);				
				return "OK";
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/trabajador/update")
	public @ResponseBody String actualizarTrabajador(@RequestBody TrabajadorDto trabajadorParam){
				Trabajador trabajadorRepo = (Trabajador)trabajadorRepository.findOne(trabajadorParam.getId());
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				trabajadorRepo.setDni(trabajadorParam.getDni());
				trabajadorRepo.setNombre(trabajadorParam.getDni());
				trabajadorRepo.setDireccion(trabajadorParam.getDireccion());
				trabajadorRepo.setFechaNacimiento(trabajadorParam.getFechaNacimiento());
				trabajadorRepo.setEstadoCivil(trabajadorParam.getEstadoCivil());
				trabajadorRepo.setUsuarioModificacion("UsuarioModificador");
				trabajadorRepo.setFechaModificacion(fechahora);
				trabajadorRepository.save(trabajadorRepo);				
				return "OK";
	}

	@Autowired
	TrabajadorRepository trabajadorRepository;
	@Autowired
	DepartamentoRepository departamentoRepository;
	@Autowired
	PuestoTrabajoRepository puestoTrabajoRepository;	
	@Autowired
	TipoTrabajadorRepository tipoTrabajadorRepository;
	@Autowired
	SedeRepository sedeRepository;
	
}
