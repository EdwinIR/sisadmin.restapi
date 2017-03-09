package com.sisadmin.compras.controller;

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

import com.sisadmin.compras.dto.CuentaPagarDetalleDto;
import com.sisadmin.compras.dto.CuentaPagarDto;
import com.sisadmin.compras.dto.CuentaPagarParcialDto;
import com.sisadmin.compras.dto.CuentaPagarTotalDto;
import com.sisadmin.compras.dto.GestionCuentaPagarFiltroDto;
import com.sisadmin.compras.dto.OrdenCompraDetalleDto;
import com.sisadmin.compras.dto.OrdenCompraDto;
import com.sisadmin.entity.Almacen;
import com.sisadmin.entity.CondicionPago;
import com.sisadmin.entity.CuentaCobrar;
import com.sisadmin.entity.CuentaPagar;
import com.sisadmin.entity.CuentaPagarDetalle;
import com.sisadmin.entity.Categorie;
import com.sisadmin.entity.Moneda;
import com.sisadmin.entity.OrdenCompra;
import com.sisadmin.entity.OrdenCompraDetalle;
import com.sisadmin.entity.Producto;
import com.sisadmin.entity.Proveedor;
import com.sisadmin.entity.UnidadMedida;
import com.sisadmin.entity.Zona;
import com.sisadmin.repository.CuentaPagarDetalleRepository;
import com.sisadmin.repository.CuentaPagarRepository;
import com.sisadmin.repository.ProveedorRepository;
import com.sisadmin.stock.dto.AjusteInventarioDto;
import com.sisadmin.stock.dto.AjusteInventarioFiltroDto;
import com.sisadmin.stock.dto.FamiliaDto;
import com.sun.org.apache.bcel.internal.generic.GETSTATIC;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@Controller
public class CuentaPagarController {

	
	@Transactional
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/cuentapagar/list")
	public @ResponseBody List<CuentaPagarDto> todosCuentasPagar(){
				List<CuentaPagar> ordencompras = (List<CuentaPagar>) cuentaPagarRepository.findAll();				
				List<CuentaPagarDto> ordencomprasDtos = new ArrayList<CuentaPagarDto>();
				for (CuentaPagar ordencompra : ordencompras) {
					ordencomprasDtos.add(convertToDto(ordencompra));
				}
				return ordencomprasDtos;
	}
	
	private CuentaPagarDto convertToDto(CuentaPagar in) {		
		CuentaPagarDto dto= new CuentaPagarDto();
		dto.setId(in.getId());
		
		if(in.getProveedor()!=null){
			dto.setRucProveedor(in.getProveedor().getRuc());
			dto.setDireccionProveedor(in.getProveedor().getDireccion());
			dto.setRazonSocialProveedor(in.getProveedor().getRazonSocial());	
			dto.setProveedorId(in.getProveedor().getId());
		}else{
			dto.setRucProveedor("");
			dto.setDireccionProveedor("");
			dto.setRazonSocialProveedor("");	
			dto.setProveedorId(0);
		}							
		dto.setMontoTotal(in.getTotal());
		dto.setMontoPagado(in.getMontoPagado());
		dto.setFechaVencimiento(in.getFechaVencimiento());
		dto.setEstado(in.getEstado());
		dto.setTotal(in.getTotal());
		for(CuentaPagarDetalle c:in.getDetalleCuentaPagar()){
			CuentaPagarDetalleDto cpd= new CuentaPagarDetalleDto();
			cpd.setMontoDeuda(c.getMontoDeuda());
			cpd.setDocumento(c.getDocumento());
			cpd.setComentarios(c.getComentarios());
			cpd.setFechaEmision(c.getFechaEmision());
			cpd.setFechaVencimiento(c.getFechaVencimiento());
			dto.setDocumento(cpd.getDocumento());
			
			dto.getDetalles().add(cpd);
		}
		
		return dto;
	
}
	
	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/cuentapagar/add")
	public @ResponseBody String agregarCuentaPagar(@RequestBody CuentaPagarDto cuentaPagarDto){
		CuentaPagar cuentaPagar = convertToCuentaPagar(cuentaPagarDto);
		cuentaPagarRepository.save(cuentaPagar);
		return "OK";
	}	
	
	private CuentaPagar convertToCuentaPagar(CuentaPagarDto cuentaPagarDto){
		Date fecha = new Date(); 
		long hora = fecha.getTime();
		Timestamp fechahora= new Timestamp(hora);
		
		CuentaPagar cuentaPagar = new CuentaPagar();	
		Proveedor proveedor = proveedorRepository.findOne(cuentaPagarDto.getProveedorId());
		cuentaPagar.setProveedor(proveedor);
		cuentaPagar.setEstado("Ingresado");
		cuentaPagar.setTotal(cuentaPagarDto.getTotal());
		cuentaPagar.setMontoPagado(new BigDecimal(0.00));
		
		
		
		for(CuentaPagarDetalleDto detalleDto: cuentaPagarDto.getDetalles()){	
				cuentaPagar.setFechaVencimiento(detalleDto.getFechaVencimiento());
				CuentaPagarDetalle ocd= convertToCuentaPagarDetalle(detalleDto, cuentaPagar);
				cuentaPagar.getDetalleCuentaPagar() .add(ocd);
		}	
		
		return cuentaPagar;
	}
	
	private CuentaPagarDetalle convertToCuentaPagarDetalle(CuentaPagarDetalleDto detalleDto, CuentaPagar cuentaPagar) {
		CuentaPagarDetalle c = new CuentaPagarDetalle();
		
		c.setMontoDeuda(detalleDto.getMontoDeuda());
		c.setDocumento(detalleDto.getDocumento());
		c.setComentarios(detalleDto.getComentarios());
		c.setFechaVencimiento(detalleDto.getFechaVencimiento());
		c.setCuentaPagar(cuentaPagar);
		c.setFechaEmision(detalleDto.getFechaEmision());
	
		return c;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/cuentapagartotal/update")
	public @ResponseBody String actualizarCuentaPagarTotal(@RequestBody CuentaPagarTotalDto cuentaPagarTotalDto){
		CuentaPagar cuentaPagarRepo =(CuentaPagar)cuentaPagarRepository.findOne(cuentaPagarTotalDto.getId());				
			cuentaPagarRepo.setFecha(cuentaPagarTotalDto.getFecha());
			cuentaPagarRepo.setObservaciones(cuentaPagarTotalDto.getObservaciones());
			cuentaPagarRepo.setEstado("Pagado Totalmente");
			cuentaPagarRepo.setMontoPagado(cuentaPagarRepo.getTotal());
			
			cuentaPagarRepository.save(cuentaPagarRepo);
		return "OK";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/cuentapagarparcial/update")
	public @ResponseBody String actualizarCuentaPagarParcial(@RequestBody CuentaPagarParcialDto cuentaPagarTotalDto){
		CuentaPagar cuentaPagarRepo =(CuentaPagar)cuentaPagarRepository.findOne(cuentaPagarTotalDto.getId());
		
			BigDecimal montoPagado = cuentaPagarRepo.getMontoPagado();		
			BigDecimal montoAumentado= cuentaPagarTotalDto.getMontoParcial();		
			BigDecimal suma= montoPagado.add(montoAumentado);		
			cuentaPagarRepo.setMontoPagado(suma);		
			cuentaPagarRepo.setFecha(cuentaPagarTotalDto.getFecha());
			cuentaPagarRepo.setObservaciones(cuentaPagarTotalDto.getObservaciones());
			cuentaPagarRepo.setEstado("Pagado Parcialmente");			
			
			cuentaPagarRepository.save(cuentaPagarRepo);
		return "OK";
	}
		
	@Transactional
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/cuentapagar")	   
	public @ResponseBody String borrarCuentaPagar(@RequestParam(value="id") Long id){
		
		CuentaPagar cuentaPagar = (CuentaPagar)cuentaPagarRepository.findOne(id);
		for(CuentaPagarDetalle cp :cuentaPagar.getDetalleCuentaPagar()){
			cuentaPagarDetalleRepository.delete(cp.getId());
		}
				cuentaPagarRepository.delete(id);
				return "Ok";				
	}	
	
	
	@Transactional
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/cuentapagar/filtro")
	public @ResponseBody List<CuentaPagarDto> listadoCuentaPagarFilter(@RequestBody GestionCuentaPagarFiltroDto filterCuentaPagar, HttpServletRequest request) throws ParseException{
				
		return obtenerFiltrado(filterCuentaPagar);
	}
	
	private List<CuentaPagarDto> obtenerFiltrado(GestionCuentaPagarFiltroDto filtro){
		List<CuentaPagarDto> cuentaPagarDto = new ArrayList<CuentaPagarDto>();
		
		long idProveedor =filtro.getProveedorId();
		Date fechaDesde = filtro.getFechaDesde();
		Date fechaHasta = filtro.getFechaHasta();
		
		if(fechaDesde!=null && fechaHasta!=null && idProveedor==0){
			List<CuentaPagar> cuentaPagar = cuentaPagarRepository.findFechas(fechaDesde,fechaHasta);
				if(cuentaPagar!=null){
					for(CuentaPagar cp : cuentaPagar){
						cuentaPagarDto.add(recuperarCuenta(cp));
					}
				}
		}
		
		if(fechaDesde==null && fechaHasta==null && idProveedor>0){
			List<CuentaPagar> cuentaPagar = cuentaPagarRepository.findByProveedorId(idProveedor);
				if(cuentaPagar!=null){
					for(CuentaPagar cp : cuentaPagar){
						cuentaPagarDto.add(recuperarCuenta(cp));
					}					
				}
		}
		
		if(fechaDesde!=null && fechaHasta!=null && idProveedor>0){
			List<CuentaPagar> cuentaPagar = cuentaPagarRepository.findByProveedorFechas(idProveedor, fechaDesde, fechaHasta);
				if(cuentaPagar!=null){
					for(CuentaPagar cp : cuentaPagar){
						cuentaPagarDto.add(recuperarCuenta(cp));
					}				
				}
		}		
		
		if(fechaDesde==null && fechaHasta==null && idProveedor==0){
			List<CuentaPagar> cuentaPagar = (List<CuentaPagar>) cuentaPagarRepository.findAll();
			for (CuentaPagar cp : cuentaPagar) {
				cuentaPagarDto.add(recuperarCuenta(cp));
			}
		}
		return cuentaPagarDto;
	}
	
	private CuentaPagarDto recuperarCuenta(CuentaPagar cp){						
		return convertToDto(cp);
	}

	@Autowired
	private CuentaPagarRepository cuentaPagarRepository;
	@Autowired
	private ProveedorRepository proveedorRepository;
	@Autowired 
	private CuentaPagarDetalleRepository cuentaPagarDetalleRepository;
}
