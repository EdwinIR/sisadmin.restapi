package com.sisadmin.stock.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;




import com.sisadmin.entity.Categorie;
import com.sisadmin.entity.MarcaFamilia;
import com.sisadmin.entity.Zona;
import com.sisadmin.repository.CategorieRepository;
import com.sisadmin.repository.ZonaRepository;
import com.sisadmin.stock.dto.FamiliaDto;


@Controller
public class FamiliaController {
		
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/familia/list")
	public @ResponseBody List<FamiliaDto> todasFamilias(){
				List<Categorie> familias = (List<Categorie>) familiaRepository.categoriasOrdenadas();
				List<FamiliaDto> familiasDtos = new ArrayList<FamiliaDto>();
				for (Categorie familia : familias) {familiasDtos.add(leer(familia));}
				return familiasDtos;
	}
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/familia")	   
	public @ResponseBody String borrarFamilia(@RequestParam(value="id") Long id){
				familiaRepository.delete(id);
				return "Ok";				
	}		
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/familia/add")
	public @ResponseBody String agregarFamilia(@RequestBody FamiliaDto familiaDto){		
		String respuesta = "OK";
		try {
				Categorie nuevaFamilia = new Categorie();				
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				nuevaFamilia.setName(familiaDto.getDescripcion());
				nuevaFamilia.setMargenBase(familiaDto.getMargenBase());
				nuevaFamilia.setMargenA(familiaDto.getMargenA());
				nuevaFamilia.setMargenB(familiaDto.getMargenB());
				nuevaFamilia.setMargenC(familiaDto.getMargenC());
				Zona zona = (Zona) zonaRepository.findOne(familiaDto.getZonaId());
				nuevaFamilia.setZona(zona);
				nuevaFamilia.setUsuarioCreacion("UsuarioCreador");
				nuevaFamilia.setFechaCreacion(fechahora);
				nuevaFamilia.setId(UUID.randomUUID().toString());
				nuevaFamilia.setCodigo(familiaDto.getCodigo());
				nuevaFamilia.setCatshowname(Boolean.valueOf("true"));
				familiaRepository.save(nuevaFamilia);
		} catch (Exception e) {
			respuesta = "Familia Repetida";
			e.printStackTrace();
		}
				return respuesta;
	}			

	@RequestMapping(method=RequestMethod.POST, value="/api/v1/familia/update")
	public @ResponseBody String actualizarFamilia(@RequestBody FamiliaDto familiaParam){
				Categorie familiaRepo = (Categorie)familiaRepository.findOne(familiaParam.getIdSa());
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				familiaRepo.setCodigo(familiaParam.getCodigo());
				familiaRepo.setName(familiaParam.getDescripcion());
				familiaRepo.setMargenBase(familiaParam.getMargenBase());
				familiaRepo.setMargenA(familiaParam.getMargenA());
				familiaRepo.setMargenB(familiaParam.getMargenB());
				familiaRepo.setMargenC(familiaParam.getMargenC());
				familiaRepo.setUsuarioModificacion("UsuarioModificador");
				familiaRepo.setFechaModificacion(fechahora);
				Zona zona = (Zona) zonaRepository.findOne(familiaParam.getZonaId());
				familiaRepo.setZona(zona);
				familiaRepository.save(familiaRepo);				
				return "OK";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/data/byfamilia")
	public @ResponseBody Categorie obtenerFamiliaXId(@RequestBody long idFamilia){
		Categorie fam = familiaRepository.findbyId(idFamilia);
		Categorie familia= new Categorie();
		familia.setMargenBase(fam.getMargenBase());
		familia.setMargenA(fam.getMargenA());
		familia.setMargenB(fam.getMargenB());
		familia.setMargenC(fam.getMargenC());
		
		return familia;
	}
	
	private FamiliaDto leer(Categorie in) {
		FamiliaDto dto = new FamiliaDto();				
		dto.setIdSa(in.getIdSa());
		dto.setId(in.getId());
		dto.setCodigo(in.getCodigo());
		dto.setDescripcion(in.getName());
		dto.setMargenBase(in.getMargenBase());
		dto.setMargenA(in.getMargenA());
		dto.setMargenB(in.getMargenB());
		dto.setMargenC(in.getMargenC());
		if(in.getZona() != null){
			dto.setDescripcionZona(in.getZona().getDescripcion());
		}
		return dto;
	}

	
	@Autowired
	private CategorieRepository familiaRepository;
	
	@Autowired
	private ZonaRepository zonaRepository;
}
