package com.sisadmin.ventas.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sisadmin.entity.Cliente;
import com.sisadmin.entity.CuentaCobrar;
import com.sisadmin.entity.CuentaCobrarDetalle;
import com.sisadmin.repository.ClienteRepository;
import com.sisadmin.repository.CuentaCobrarDetalleRepository;
import com.sisadmin.repository.CuentaCobrarRepository;
import com.sisadmin.ventas.dto.CuentaCobrarDetalleDto;
import com.sisadmin.ventas.dto.CuentaCobrarDto;
import com.sisadmin.ventas.dto.GestionCuentaCobrarFiltroDto;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@Controller
public class CuentaCobrarController {

	
	@Transactional
	@RequestMapping(method = RequestMethod.GET, value ="/api/v1/cuentacobrar/list")
	public @ResponseBody List<CuentaCobrarDto> listarCuentasCobrar(){
		List<CuentaCobrar> listCuentaCobrar = (List<CuentaCobrar>) cuentaCobrarRepository.findAll();
		List<CuentaCobrarDto> listaCuentaCobrarDto = new ArrayList<CuentaCobrarDto>();
		for (CuentaCobrar cuentaCobrar : listCuentaCobrar) {
			listaCuentaCobrarDto.add(convertToDto(cuentaCobrar));
		}
		
		return listaCuentaCobrarDto;
	}
		
	private CuentaCobrarDto convertToDto(CuentaCobrar cuentaCobrar){
		CuentaCobrarDto dto = new CuentaCobrarDto();
		dto.setId(cuentaCobrar.getId());
		if(cuentaCobrar.getCliente()!=null){
			dto.setRucCliente(cuentaCobrar.getCliente().getIdentificador());
			dto.setRazonSocialCliente(cuentaCobrar.getCliente().getRazonSocial());
		}else{
			dto.setRucCliente("");
			dto.setRazonSocialCliente("");
		}		
		dto.setMontoTotal(cuentaCobrar.getTotal());
		dto.setMontoCobrado(cuentaCobrar.getMontoCobrado());
		dto.setFechaVencimiento(cuentaCobrar.getFechaVencimiento());
		dto.setEstado(cuentaCobrar.getEstado());
		dto.setTotal(cuentaCobrar.getTotal());
		for (CuentaCobrarDetalle ccdetalle : cuentaCobrar.getDetalleCuentaCobrar()){
			CuentaCobrarDetalleDto detalleDto = new CuentaCobrarDetalleDto();
			detalleDto.setMontoDeuda(ccdetalle.getMontoDeuda());
			detalleDto.setDocumento(ccdetalle.getDocumento());
			detalleDto.setComentarios(ccdetalle.getComentarios());
			detalleDto.setFechaVencimiento(ccdetalle.getFechaVencimiento());
			detalleDto.setFechaEmision(ccdetalle.getFechaEmision());
			dto.setDocumento(detalleDto.getDocumento());
			
			dto.getDetalles().add(detalleDto);
		}
		
		return dto;
	}
	
	@Transactional
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/cuentacobrar")	   
	public @ResponseBody String borrarCuentaCobrar(@RequestParam(value="id") Long id){
		
		CuentaCobrar cuentaCobrar = (CuentaCobrar)cuentaCobrarRepository.findOne(id);
		for(CuentaCobrarDetalle cd :cuentaCobrar.getDetalleCuentaCobrar()){
			cuentaCobrarDetalleRepository.delete(cd.getId());
		}
				cuentaCobrarRepository.delete(id);
				return "Ok";				
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/cuentacobrar/add")
	public @ResponseBody String agregarCuentaPagar(@RequestBody CuentaCobrarDto cuentaCobrarDto){
		CuentaCobrar cuentaCobrar = convertoToCuentaCobrar(cuentaCobrarDto);
		cuentaCobrarRepository.save(cuentaCobrar);
		return "OK";
	}
	
	private CuentaCobrar convertoToCuentaCobrar(CuentaCobrarDto cuentaCobrarDto){
		Date fecha = new Date(); 
		long hora = fecha.getTime();
		Timestamp fechahora= new Timestamp(hora);
		
		CuentaCobrar cuentaCobrar = new CuentaCobrar();
		//Cliente cliente = clienteRepository.findOne(cuentaCobrarDto.getClienteId());
		//cuentaCobrar.setCliente(cliente);
		cuentaCobrar.setEstado("Ingresado");
		cuentaCobrar.setTotal(cuentaCobrarDto.getTotal());
		cuentaCobrar.setMontoCobrado(new BigDecimal(0.00));
		
		for (CuentaCobrarDetalleDto detalleDto : cuentaCobrarDto.getDetalles()) {
			cuentaCobrar.setFechaVencimiento(detalleDto.getFechaVencimiento());
			CuentaCobrarDetalle ccd = convertToCuentaCobrarDetalle(detalleDto, cuentaCobrar);
			cuentaCobrar.getDetalleCuentaCobrar().add(ccd);
		}
		
		return cuentaCobrar;
	}
	
	private CuentaCobrarDetalle convertToCuentaCobrarDetalle(CuentaCobrarDetalleDto dto, CuentaCobrar cuentaCobrar){
		CuentaCobrarDetalle c = new CuentaCobrarDetalle();
		
		c.setMontoDeuda(dto.getMontoDeuda());
		c.setDocumento(dto.getDocumento());
		c.setComentarios(dto.getComentarios());
		c.setFechaVencimiento(dto.getFechaVencimiento());
		c.setCuentaCobrar(cuentaCobrar);	
		c.setFechaEmision(dto.getFechaEmision());	
		
		return c;
	}
	
	@Transactional
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/cuentacobrar/filtro")
	public @ResponseBody List<CuentaCobrarDto> listadoCuentaPagarFilter(@RequestBody GestionCuentaCobrarFiltroDto filterCuentaCobrar, HttpServletRequest request) throws ParseException{
				
		return obtenerFiltrado(filterCuentaCobrar);
	}
	
	private List<CuentaCobrarDto>obtenerFiltrado(GestionCuentaCobrarFiltroDto filtro){
		List<CuentaCobrarDto> cuentaCobrarDto = new ArrayList<CuentaCobrarDto>();
		
		long idCliente =filtro.getClienteId();
		Date fechaDesde = filtro.getFechaDesde();
		Date fechaHasta = filtro.getFechaHasta();
		
		if(fechaDesde!=null && fechaHasta!=null && idCliente==0){
			List<CuentaCobrar>cuentaCobrar = cuentaCobrarRepository.findFechas(fechaDesde, fechaHasta);
				if(cuentaCobrar!=null){
					for(CuentaCobrar cc:cuentaCobrar){
						cuentaCobrarDto.add(recuperarCuenta(cc));
					}
				}
		}
		
		if(fechaDesde==null && fechaHasta==null && idCliente>0){
			List<CuentaCobrar>cuentaCobrar = cuentaCobrarRepository.findByClienteId(idCliente);
				if(cuentaCobrar!=null){
					for(CuentaCobrar cc:cuentaCobrar){
						cuentaCobrarDto.add(recuperarCuenta(cc));
					}
				}
		}
		
		if(fechaDesde!=null && fechaHasta!=null && idCliente>0){
			List<CuentaCobrar>cuentaCobrar = cuentaCobrarRepository.findByClienteFechas(idCliente, fechaDesde, fechaHasta);
				if(cuentaCobrar!=null){
					for(CuentaCobrar cc:cuentaCobrar){
						cuentaCobrarDto.add(recuperarCuenta(cc));
					}
				}
		}
		
		if(fechaDesde==null && fechaHasta==null && idCliente==0){
			List<CuentaCobrar> cuentaCobrar = (List<CuentaCobrar>) cuentaCobrarRepository.findAll();
			for (CuentaCobrar cc : cuentaCobrar) {
				cuentaCobrarDto.add(recuperarCuenta(cc));
			}
		}
		
		return cuentaCobrarDto;
	}
	
	private CuentaCobrarDto recuperarCuenta(CuentaCobrar cc){		
		return convertToDto(cc);
	}

	
	
	
	@Autowired
	private CuentaCobrarDetalleRepository cuentaCobrarDetalleRepository;
	
	@Autowired
	private CuentaCobrarRepository cuentaCobrarRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
}
