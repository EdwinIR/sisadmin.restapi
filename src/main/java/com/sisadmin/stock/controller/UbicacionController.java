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
import com.sisadmin.entity.Ubicacion;
import com.sisadmin.entity.Zona;
import com.sisadmin.repository.AlmacenRepository;
import com.sisadmin.repository.UbicacionRepository;
import com.sisadmin.repository.ZonaRepository;
import com.sisadmin.stock.dto.ProductoDto;
import com.sisadmin.stock.dto.UbicacionDto;
import com.sisadmin.stock.dto.ZonaDto;

@Controller
public class UbicacionController {
	
	private UbicacionDto convertToDto(Ubicacion in) {
		UbicacionDto dto = new UbicacionDto();
		//dto.setId(in.getId());
		dto.setAbreviado(in.getAbreviado());
		dto.setDescripcion(in.getDescripcion());
		dto.setAlmacenId(in.getZona().getAlmacen().getId());
		dto.setZonaId(in.getZona().getId());
		dto.setDescripcionZona(in.getZona().getDescripcion());
		dto.setDescripcionAlmacen(in.getZona().getAlmacen().getDescripcion());
		return dto;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/ubicacion/byalmacen")
	public @ResponseBody List<UbicacionDto> todasUbicacionesPorAlmacen(@RequestBody long almacenId){
				Zona zona = (Zona)zonaRepository.findOne(almacenId);
				List<Ubicacion> ubicaciones = (List<Ubicacion>) ubicacionRepository.findUbicacionByZonaId(zona.getId());
				List<UbicacionDto> ubicacionesDtos = new ArrayList<UbicacionDto>();
				for (Ubicacion ubicacion : ubicaciones) {
					ubicacionesDtos.add(convertToDto(ubicacion));
				}
				return ubicacionesDtos;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/ubicaciones/byalmacen")
	public @ResponseBody List<UbicacionDto> todasUbicacionesPorAlmacen2(@RequestBody long almacenId){
				Zona zona = (Zona)zonaRepository.findOne(almacenId);
				//List<Ubicacion> ubicaciones = (List<Ubicacion>) ubicacionRepository.findUbicacionByZonaId(zona.getId());
				List<Ubicacion> ubicaciones = (List<Ubicacion>) ubicacionRepository.findUbicacionByAlmacenId(almacenId);
				List<UbicacionDto> ubicacionesDtos = new ArrayList<UbicacionDto>();
				for (Ubicacion ubicacion : ubicaciones) {
					ubicacionesDtos.add(convertToDto(ubicacion));
				}
				return ubicacionesDtos;
	}
	
	

	@RequestMapping(method=RequestMethod.GET, value="/api/v1/ubicacion/list")
	public @ResponseBody List<UbicacionDto> todasUbicaciones(){
				List<Ubicacion> ubicaciones = (List<Ubicacion>) ubicacionRepository.findAll();
				List<UbicacionDto> ubicacionesDtos = new ArrayList<UbicacionDto>();
				for (Ubicacion ubicacion : ubicaciones) {
					ubicacionesDtos.add(convertToDto(ubicacion));
				}
				return ubicacionesDtos;
	}
	/*Ciro 03102016*/
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/ubicacion/byalmacenandzona")
	public @ResponseBody List<Ubicacion> obtenerUbicacionesPorAlmacenIdYZonaId(@RequestBody ProductoDto productoDto){
		List<Ubicacion> ubicaciones = new ArrayList<Ubicacion>();
		//ubicaciones = ubicacionRepository.findUbicacionByAlmacenIdAndZonaId(productoDto.getAlmacenId(), productoDto.getZonaId());
		
		return ubicaciones;
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/ubicacion")	   
	public @ResponseBody String borrarUbicacion(@RequestParam(value="id") Long id){
				ubicacionRepository.delete(id);
				return "Ok";				
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/ubicacion/add")
	public @ResponseBody String agregarUbicacion(@RequestBody UbicacionDto ubicacion){
				Ubicacion nuevaUbicacion = new Ubicacion();
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				nuevaUbicacion.setAbreviado(ubicacion.getAbreviado());
				nuevaUbicacion.setDescripcion(ubicacion.getDescripcion());
				Zona zona =(Zona)zonaRepository.findOne(ubicacion.getZonaId());				
				Almacen almacen = (Almacen)almacenRepository.findOne(ubicacion.getAlmacenId());
				//nuevaUbicacion.setAlmacen(almacen);
				nuevaUbicacion.setZona(zona);								
				nuevaUbicacion.setUsuarioCreacion("UsuarioCreador");
				nuevaUbicacion.setFechaCreacion(fechahora);
				ubicacionRepository.save(nuevaUbicacion);				
				return "OK";
	}			

	@RequestMapping(method=RequestMethod.POST, value="/api/v1/ubicacion/update")
	public @ResponseBody String actualizarUbicacion(@RequestBody UbicacionDto ubicacionParam){
				//Ubicacion ubicacionRepo = (Ubicacion)ubicacionRepository.findOne(ubicacionParam.getId());
				Ubicacion ubicacionRepo = new Ubicacion();		
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				ubicacionRepo.setDescripcion(ubicacionParam.getDescripcion());
				ubicacionRepo.setAbreviado(ubicacionParam.getAbreviado());
		
				Zona zona =(Zona)zonaRepository.findOne(ubicacionParam.getZonaId());				
				Almacen almacen = (Almacen)almacenRepository.findOne(ubicacionParam.getAlmacenId());
				//ubicacionRepo.setAlmacen(almacen);
				ubicacionRepo.setZona(zona);			
		
				//ubicacionRepo.setZona(zona);
				ubicacionRepo.setUsuarioModificacion("UsuarioModificador");
				ubicacionRepo.setFechaModificacion(fechahora);
				ubicacionRepository.save(ubicacionRepo);				
				return "OK";
	}
	
	@Autowired
	private UbicacionRepository ubicacionRepository;
	
	@Autowired
	private ZonaRepository zonaRepository;
	
	@Autowired
	private AlmacenRepository almacenRepository;
}
