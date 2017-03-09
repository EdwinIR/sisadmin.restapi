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

import com.sisadmin.admin.dto.SubCentroCostoDto;
import com.sisadmin.entity.CentroCosto;
import com.sisadmin.entity.SubCentroCosto;
import com.sisadmin.repository.CentroCostoRepository;
import com.sisadmin.repository.SubCentroCostoRepository;

@Controller
public class SubCentroController {

	
	/**SubCentroCosto**/	
	private SubCentroCostoDto convertToDto(SubCentroCosto in) {
		SubCentroCostoDto dto = new SubCentroCostoDto();
		dto.setId(in.getId());
		dto.setCodigo(in.getCodigo());
		dto.setDescripcion(in.getDescripcion());
		dto.setDescripcionCentroCosto(in.getCentroCosto().getDescripcion());
		
		return dto;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/subcentrocosto/list")
	public @ResponseBody List<SubCentroCostoDto> todosSubCentroCostos(){
				List<SubCentroCosto> subcentrocostos = (List<SubCentroCosto>) subCentroCostoRepository.findAll();
				List<SubCentroCostoDto> subcentrocostosDtos = new ArrayList<SubCentroCostoDto>();
				for (SubCentroCosto subcentrocosto : subcentrocostos) {
					subcentrocostosDtos.add(convertToDto(subcentrocosto));
				}
				return subcentrocostosDtos;
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/subcentrocosto")	   
	public @ResponseBody String borrarSubCentroCosto(@RequestParam(value="id") Long id){
		subCentroCostoRepository.delete(id);
				return "Ok";				
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/subcentrocosto/add")
	public @ResponseBody String agregarSubCentroCosto(@RequestBody SubCentroCostoDto subCentroCostoDto){
				SubCentroCosto nuevoSubCentroCosto = new SubCentroCosto();
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				nuevoSubCentroCosto.setCodigo(subCentroCostoDto.getCodigo());
				nuevoSubCentroCosto.setDescripcion(subCentroCostoDto.getDescripcion());						
				CentroCosto centroCosto = (CentroCosto)centroCostoRepository.findOne(subCentroCostoDto.getCentroCostoId());
				nuevoSubCentroCosto.setCentroCosto(centroCosto);
				nuevoSubCentroCosto.setUsuarioCreacion("UsuarioCreador");
				nuevoSubCentroCosto.setFechaCreacion(fechahora);
				subCentroCostoRepository.save(nuevoSubCentroCosto);				
				return "OK";
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/subcentrocosto/update")
	public @ResponseBody String actualizarSubCentroCosto(@RequestBody SubCentroCostoDto subCentroCostoParam){
				SubCentroCosto subCentroCostoRepo = (SubCentroCosto)subCentroCostoRepository.findOne(subCentroCostoParam.getId());
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				subCentroCostoRepo.setCodigo(subCentroCostoParam.getCodigo());
				subCentroCostoRepo.setDescripcion(subCentroCostoParam.getDescripcion());	
				subCentroCostoRepo.setUsuarioModificacion("UsuarioModificador");
				subCentroCostoRepo.setFechaModificacion(fechahora);
				CentroCosto centroCosto = (CentroCosto)centroCostoRepository.findOne(subCentroCostoParam.getCentroCostoId());
				subCentroCostoRepo.setCentroCosto(centroCosto);
				subCentroCostoRepository.save(subCentroCostoRepo);				
				return "OK";
	}
	
	@Autowired
	SubCentroCostoRepository subCentroCostoRepository;
	
	@Autowired
	CentroCostoRepository centroCostoRepository;

}
