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

import com.sisadmin.admin.dto.ConceptoDto;
import com.sisadmin.entity.Concepto;
import com.sisadmin.entity.TipoTrabajador;
import com.sisadmin.repository.ConceptoRepository;
import com.sisadmin.repository.TipoTrabajadorRepository;


@Controller
public class ConceptoController {
	
	
	/**ConceptoDto**/
	private ConceptoDto convertToDto(Concepto in) {
		ConceptoDto dto = new ConceptoDto();
		dto.setId(in.getId());
		dto.setCodigo(in.getCodigo());
		dto.setDescripcion(in.getDescripcion());
		dto.setTipo(in.getTipo());
		dto.setInafecto(in.getInafecto());
		dto.setDescripcionTipoTrabajador(in.getTipoTrabajador().getDescripcion());
		return dto;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/concepto/list")
	public @ResponseBody List<ConceptoDto> todosConceptos(){
				List<Concepto> conceptos = (List<Concepto>) conceptoRepository.findAll();
				List<ConceptoDto> conceptosDtos = new ArrayList<ConceptoDto>();
				for (Concepto concepto : conceptos) {
					conceptosDtos.add(convertToDto(concepto));
				}
				return conceptosDtos;
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/concepto")	   
	public @ResponseBody String borrarConcepto(@RequestParam(value="id") Long id){
				conceptoRepository.delete(id);
				return "Ok";				
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/concepto/add")
	public @ResponseBody String agregarConcepto(@RequestBody ConceptoDto conceptoDto){
				Concepto nuevoConcepto = new Concepto();
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				nuevoConcepto.setCodigo(conceptoDto.getCodigo());
				nuevoConcepto.setDescripcion(conceptoDto.getDescripcion());
				nuevoConcepto.setTipo(conceptoDto.getTipo());		
				TipoTrabajador tipotrabajador = (TipoTrabajador)tipoTrabajadorRepository.findOne(conceptoDto.getTipoTrabajadorId());				
				nuevoConcepto.setTipoTrabajador(tipotrabajador);
				nuevoConcepto.setInafecto(conceptoDto.getInafecto());
				nuevoConcepto.setUsuarioCreacion("UsuarioCreador");
				nuevoConcepto.setFechaCreacion(fechahora);
				conceptoRepository.save(nuevoConcepto);				
				return "OK";
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/concepto/update")
	public @ResponseBody String actualizarConcepto(@RequestBody ConceptoDto conceptoParam){
				Concepto conceptoRepo = (Concepto)conceptoRepository.findOne(conceptoParam.getId());
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				conceptoRepo.setCodigo(conceptoParam.getCodigo());
				conceptoRepo.setDescripcion(conceptoParam.getDescripcion());
				conceptoRepo.setTipo(conceptoParam.getTipo());	
				conceptoRepo.setUsuarioModificacion("UsuarioModificador");
				conceptoRepo.setFechaModificacion(fechahora);
				TipoTrabajador tipotrabajador = (TipoTrabajador)tipoTrabajadorRepository.findOne(conceptoParam.getTipoTrabajadorId());				
				conceptoRepo.setTipoTrabajador(tipotrabajador);
				conceptoRepository.save(conceptoRepo);				
				return "OK";
	}

	@Autowired
	protected ConceptoRepository conceptoRepository;
	@Autowired
	protected TipoTrabajadorRepository tipoTrabajadorRepository;

}
