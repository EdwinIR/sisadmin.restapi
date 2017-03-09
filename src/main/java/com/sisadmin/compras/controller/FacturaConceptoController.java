package com.sisadmin.compras.controller;

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

import com.sisadmin.compras.dto.FacturaConceptoDto;
import com.sisadmin.entity.FacturaConcepto;
import com.sisadmin.repository.FacturaConceptoRepository;

@Controller
public class FacturaConceptoController {

	@RequestMapping(method=RequestMethod.GET, value="/api/v1/facturaconcepto/list")
	public @ResponseBody List<FacturaConceptoDto> todosFacturasConcepto(){
		List<FacturaConceptoDto> facturaConceptoDto = new ArrayList<FacturaConceptoDto>();
		List<FacturaConcepto> facturaConcepto = (List<FacturaConcepto>) facturaConceptoRepository.findAll();
		for(FacturaConcepto facturaconcepto : facturaConcepto){
			facturaConceptoDto.add(convertToDto(facturaconcepto));
		}
		return facturaConceptoDto;		
	}

	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/facturaconcepto/add")
	public @ResponseBody String agregarFacturaConcepto(@RequestBody FacturaConceptoDto facturaConceptoDto){
		FacturaConcepto facturaConcepto= convertToFacturaConcepto(facturaConceptoDto);
		facturaConceptoRepository.save(facturaConcepto);
		
		return "OK";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/facturaconcepto/update")
	public @ResponseBody String actualizarFacturaConcepto(@RequestBody FacturaConceptoDto facturaConceptoDto){
		//FacturaConcepto facturaConcepto = convertToFactura(facturaConceptoDto);
		FacturaConcepto facturaConcepto = (FacturaConcepto)facturaConceptoRepository.findOne(facturaConceptoDto.getId());
		convertToFacturaConcepto(facturaConceptoDto,facturaConcepto);
		
		facturaConceptoRepository.save(facturaConcepto);
				return "OK";
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value = "/api/v1/facturaconcepto" )
	public @ResponseBody String borrarFacturaConcepto(@RequestParam(value="id") Long id){
		facturaConceptoRepository.delete(id);
		return "Ok";
	}
	
	private FacturaConceptoDto convertToDto(FacturaConcepto facturaConcepto){
		FacturaConceptoDto facturaConceptoDto = new FacturaConceptoDto();
		/*Cabecera*/
		facturaConceptoDto.setId(facturaConcepto.getId());
		facturaConceptoDto.setConcepto(facturaConcepto.getConcepto());
		facturaConceptoDto.setSerie(facturaConcepto.getSerie());
		facturaConceptoDto.setNroDocumento(facturaConcepto.getNroDocumento());
		facturaConceptoDto.setMonto(facturaConcepto.getMonto());
		facturaConceptoDto.setFechaEmision(facturaConcepto.getFechaEmision());
		facturaConceptoDto.setFechaVencimiento(facturaConcepto.getFechaVencimiento());
		return facturaConceptoDto;
	}
	
	private FacturaConcepto convertToFacturaConcepto(FacturaConceptoDto facturaConceptoDto,FacturaConcepto facturaConcepto) {
		Date fecha = new Date(); 
		long hora = fecha.getTime();
		Timestamp fechahora= new Timestamp(hora);
		//Cabecera de la factura
		facturaConcepto.setNroDocumento(facturaConceptoDto.getNroDocumento());
		facturaConcepto.setSerie(facturaConceptoDto.getSerie());
		facturaConcepto.setConcepto(facturaConceptoDto.getConcepto());
		facturaConcepto.setMonto(facturaConceptoDto.getMonto());
		facturaConcepto.setFechaEmision(facturaConceptoDto.getFechaEmision());
		facturaConcepto.setFechaVencimiento(facturaConceptoDto.getFechaVencimiento());
		facturaConcepto.setFechaPago(facturaConceptoDto.getFechaPago());
		facturaConcepto.setFechaCreacion(fechahora);
		facturaConcepto.setUsuarioCreacion("usuarioCreacion");
		
		return facturaConcepto;
	}
	
	private FacturaConcepto convertToFacturaConcepto(FacturaConceptoDto facturaConceptoDto){
		Date fecha = new Date(); 
		long hora = fecha.getTime();
		Timestamp fechahora= new Timestamp(hora);
		
		FacturaConcepto facturaConcepto = new FacturaConcepto();
		
		facturaConcepto.setNroDocumento(facturaConceptoDto.getNroDocumento());
		facturaConcepto.setSerie(facturaConceptoDto.getSerie());
		facturaConcepto.setConcepto(facturaConceptoDto.getConcepto());
		facturaConcepto.setMonto(facturaConceptoDto.getMonto());
		facturaConcepto.setFechaEmision(facturaConceptoDto.getFechaEmision());
		facturaConcepto.setFechaVencimiento(facturaConceptoDto.getFechaVencimiento());
		facturaConcepto.setFechaPago(facturaConceptoDto.getFechaPago());
		facturaConcepto.setFechaCreacion(fechahora);
		facturaConcepto.setUsuarioCreacion("usuarioCreacion");
		
		return facturaConcepto;
	}
	
	
	@Autowired
	private FacturaConceptoRepository facturaConceptoRepository;
	
	
	
}
