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

import com.sisadmin.admin.dto.CentroCostoDto;
import com.sisadmin.admin.dto.CuentaContableDto;
import com.sisadmin.entity.CentroCosto;
import com.sisadmin.entity.CuentaContable;
import com.sisadmin.entity.CuentaDivisionaria;
import com.sisadmin.repository.CentroCostoRepository;
import com.sisadmin.repository.CuentaDivisionariaRepository;


@Controller
public class CentroCostoController {
	
	/**CentroCosto**/	
	private CentroCostoDto convertToDto(CentroCosto in) {
		CentroCostoDto dto = new CentroCostoDto();
		dto.setId(in.getId());
		dto.setCodigo(in.getCodigo());
		dto.setDescripcion(in.getDescripcion());
		dto.setDescripcionCuentaDivisionaria(in.getCuentaDivisionaria().getDescripcion());
		
		return dto;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/centrocosto/list")
	public @ResponseBody List<CentroCostoDto> todosCentroCostos(){
				List<CentroCosto> centrocostos = (List<CentroCosto>) centroCostoRepository.findAll();
				List<CentroCostoDto> centrocostosDtos = new ArrayList<CentroCostoDto>();
				for (CentroCosto centrocosto : centrocostos) {
					centrocostosDtos.add(convertToDto(centrocosto));
				}
				return centrocostosDtos;
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/centrocosto")	   
	public @ResponseBody String borrarCentroCosto(@RequestParam(value="id") Long id){
		centroCostoRepository.delete(id);
				return "Ok";				
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/centrocosto/add")
	public @ResponseBody String agregarCentroCosto(@RequestBody CentroCostoDto centroCostoDto){
				CentroCosto nuevoCentroCosto = new CentroCosto();
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				nuevoCentroCosto.setCodigo(centroCostoDto.getCodigo());
				nuevoCentroCosto.setDescripcion(centroCostoDto.getDescripcion());						
				CuentaDivisionaria cuentaDivisionaria = (CuentaDivisionaria)cuentaDivisionariaRepository.findOne(centroCostoDto.getCuentaDivisionariaId());
				nuevoCentroCosto.setCuentaDivisionaria(cuentaDivisionaria);	
				nuevoCentroCosto.setUsuarioCreacion("UsuarioCreador");
				nuevoCentroCosto.setFechaCreacion(fechahora);
				centroCostoRepository.save(nuevoCentroCosto);				
				return "OK";
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/centrocosto/update")
	public @ResponseBody String actualizarCentroCosto(@RequestBody CentroCostoDto centroCostoParam){
				CentroCosto centroCostoRepo = (CentroCosto)centroCostoRepository.findOne(centroCostoParam.getId());
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				centroCostoRepo.setCodigo(centroCostoParam.getCodigo());
				centroCostoRepo.setDescripcion(centroCostoParam.getDescripcion());			
				centroCostoRepo.setUsuarioModificacion("UsuarioModificacion");
				centroCostoRepo.setFechaModificacion(fechahora);
				CuentaDivisionaria cuentaDivisionaria = (CuentaDivisionaria)cuentaDivisionariaRepository.findOne(centroCostoParam.getCuentaDivisionariaId());
				centroCostoRepo.setCuentaDivisionaria(cuentaDivisionaria);
				centroCostoRepository.save(centroCostoRepo);				
				return "OK";
	}
	
	/**CuentaContable**/	
	private CuentaContableDto convertToDto(CuentaContable in) {
		CuentaContableDto dto = new CuentaContableDto();
		dto.setId(in.getId());
		dto.setCodigo(in.getCodigo());
		dto.setDescripcion(in.getDescripcion());
		dto.setCodigoCuentaDivisionaria(in.getCuentaDivisionaria().getCodigo());
		dto.setDescripcionCuentaDivisionaria(in.getCuentaDivisionaria().getDescripcion());
		
		return dto;
	}

	@Autowired
	protected CentroCostoRepository centroCostoRepository;
	@Autowired
	protected CuentaDivisionariaRepository cuentaDivisionariaRepository;

}
