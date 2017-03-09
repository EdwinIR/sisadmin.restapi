package com.sisadmin.stock.controller;

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

import com.sisadmin.entity.Almacen;
import com.sisadmin.entity.Zona;
import com.sisadmin.repository.AlmacenRepository;
import com.sisadmin.repository.ZonaRepository;
import com.sisadmin.stock.dto.ZonaDto;

@Controller
public class ZonaController {

	private ZonaDto convertToDto(Zona in) {
		ZonaDto dto = new ZonaDto();
		dto.setId(in.getId());
		//dto.setCodigo(in.getCodigo());
		dto.setAlmacenId(in.getAlmacen().getId());
		dto.setDescripcion(in.getDescripcion());
		dto.setDescripcionAlmacen(in.getAlmacen().getDescripcion());
		return dto;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/zona/list")
	public @ResponseBody List<ZonaDto> todasZonas(){
				List<Zona> zonas = (List<Zona>) zonaRepository.findAllZona();
				List<ZonaDto> zonasDtos = new ArrayList<ZonaDto>();
				for (Zona zona : zonas) {
					zonasDtos.add(convertToDto(zona));
				}
				return zonasDtos;
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/zona")	   
	public @ResponseBody String borrarZona(@RequestParam(value="id") Long id){
				zonaRepository.delete(id);
				return "Ok";				
	}
	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/zona/add")
	public @ResponseBody String agregarZona(@RequestBody ZonaDto zona){
		String respuesta = "OK";
		try {
				Zona nuevaZona = new Zona();
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				//nuevaZona.setCodigo(zona.getCodigo());
				nuevaZona.setDescripcion(zona.getDescripcion());
				nuevaZona.setUsuarioCreacion("UsuarioCreador");
				nuevaZona.setFechaCreacion(fechahora);				
				Almacen almacen =(Almacen)almacenRepository.findOne(zona.getAlmacenId());
				nuevaZona.setAlmacen(almacen);				
				zonaRepository.save(nuevaZona);
		} catch (Exception e) {
			respuesta = "Zona Repetida";
			e.printStackTrace();
		}
				return respuesta;
	}			

	@RequestMapping(method=RequestMethod.POST, value="/api/v1/zona/update")
	public @ResponseBody String actualizarZona(@RequestBody ZonaDto zonaDto){
				Zona zona = (Zona)zonaRepository.findOne(zonaDto.getId());
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				//zonaRepo.setCodigo(zonaParam.getCodigo());
				zona.setDescripcion(zonaDto.getDescripcion());
				zona.setUsuarioCreacion("UsuarioModificador");
				zona.setFechaCreacion(fechahora);				
				Almacen almacen =(Almacen)almacenRepository.findOne(zonaDto.getAlmacenId());
				zona.setAlmacen(almacen);	
				zonaRepository.save(zona);				
				return "OK";
	}
	
	@Autowired
	private ZonaRepository zonaRepository;
	@Autowired
	private AlmacenRepository almacenRepository;
	
}
