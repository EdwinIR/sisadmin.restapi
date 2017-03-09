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

import com.sisadmin.admin.dto.LetraPagarDto;
import com.sisadmin.entity.LetraPagar;
import com.sisadmin.entity.Moneda;
import com.sisadmin.entity.Proveedor;
import com.sisadmin.repository.LetraPagarRepository;
import com.sisadmin.repository.MonedaRepository;
import com.sisadmin.repository.ProveedorRepository;


@Controller
public class LetraPagarController {
	
	
	private LetraPagarDto convertToDto(LetraPagar in) {
		LetraPagarDto dto = new LetraPagarDto();
		dto.setId(in.getId());
		dto.setFechaEmision(in.getFechaEmision());
		dto.setFechaVencimiento(in.getFechaVencimiento());
		dto.setNumeroLetra(in.getNumeroLetra());
		dto.setTotal(in.getTotal());
		dto.setCuentaTotal(in.getCuentaTotal());
		dto.setDescripcionProveedor(in.getProveedor().getRazonSocial());
		dto.setDescripcionMoneda(in.getTipoMoneda().getDescripcion());
		return dto;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/letrapagar/list")
	public @ResponseBody List<LetraPagarDto> todasLetraPagar(){
				List<LetraPagar> letrapagar = (List<LetraPagar>) letraPagarRepository.findAll();
				List<LetraPagarDto> letrapagarDtos = new ArrayList<LetraPagarDto>();
				for (LetraPagar letra : letrapagar) {
					letrapagarDtos.add(convertToDto(letra));
				}
				return letrapagarDtos;
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/letrapagar")	   
	public @ResponseBody String borrarLetraPagar(@RequestParam(value="id") Long id){
				letraPagarRepository.delete(id);
				return "Ok";				
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/letrapagar/add")
	public @ResponseBody String agregarLetraPagar(@RequestBody LetraPagarDto letraPagarDto){
				LetraPagar nuevaLetraPagar = new LetraPagar();
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				nuevaLetraPagar.setFechaEmision(letraPagarDto.getFechaEmision());	
				nuevaLetraPagar.setFechaVencimiento(letraPagarDto.getFechaVencimiento());
				nuevaLetraPagar.setNumeroLetra(letraPagarDto.getNumeroLetra());
				nuevaLetraPagar.setTotal(letraPagarDto.getTotal());
				nuevaLetraPagar.setCuentaTotal(letraPagarDto.getCuentaTotal());				
				Moneda moneda = (Moneda)monedaRepository.findOne(letraPagarDto.getTipoMonedaCodigo());
				nuevaLetraPagar.setTipoMoneda(moneda);
				Proveedor proveedor =(Proveedor)proveedorRepository.findOne(letraPagarDto.getProveedorId());				
				nuevaLetraPagar.setProveedor(proveedor);
				nuevaLetraPagar.setUsuarioCreacion("UsuarioCreador");
				nuevaLetraPagar.setFechaCreacion(fechahora);
				
				letraPagarRepository.save(nuevaLetraPagar);				
				return "OK";
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/letrapagar/update")
	public @ResponseBody String actualizarLetraPagar(@RequestBody LetraPagarDto letraPagarParam){
				LetraPagar letraPagarRepo = (LetraPagar)letraPagarRepository.findOne(letraPagarParam.getId());
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				letraPagarRepo.setFechaEmision(letraPagarParam.getFechaEmision());	
				letraPagarRepo.setFechaVencimiento(letraPagarParam.getFechaVencimiento());
				letraPagarRepo.setNumeroLetra(letraPagarParam.getNumeroLetra());
				letraPagarRepo.setTotal(letraPagarParam.getTotal());
				letraPagarRepo.setCuentaTotal(letraPagarParam.getCuentaTotal());
				Moneda moneda = (Moneda)monedaRepository.findOne(letraPagarParam.getTipoMonedaCodigo());
				letraPagarRepo.setTipoMoneda(moneda);
				Proveedor proveedor =(Proveedor)proveedorRepository.findOne(letraPagarParam.getProveedorId());
				letraPagarRepo.setProveedor(proveedor);
				letraPagarRepo.setUsuarioModificacion("UsuarioModificador");
				letraPagarRepo.setFechaModificacion(fechahora);
				letraPagarRepository.save(letraPagarRepo);				
				return "OK";
	}

	@Autowired 
	protected LetraPagarRepository letraPagarRepository;
	@Autowired
    protected ProveedorRepository proveedorRepository;
	@Autowired
	protected MonedaRepository monedaRepository;

}
