package com.sisadmin.compras.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sisadmin.compras.dto.CuentaPagarDto;
import com.sisadmin.compras.dto.GestionCuentaPagarFiltroDto;
import com.sisadmin.entity.AjusteInventario;
import com.sisadmin.entity.CuentaCobrar;
import com.sisadmin.entity.CuentaPagar;
import com.sisadmin.entity.Producto;
import com.sisadmin.entity.ProductoAlmacen;
import com.sisadmin.entity.ProductoDetalle;
import com.sisadmin.entity.UnidadMedida;
import com.sisadmin.repository.AjusteInventarioRepository;
import com.sisadmin.repository.ProductoAlmacenRepository;
import com.sisadmin.repository.ProductoRepository;
import com.sisadmin.repository.UnidadMedidaRepository;
import com.sisadmin.stock.dto.AjusteInventarioAltaDto;
import com.sisadmin.stock.dto.AjusteInventarioDto;
import com.sisadmin.stock.dto.AjusteInventarioFiltroDto;
import com.sisadmin.stock.dto.FilterSaldoPorProductoDto;
import com.sisadmin.stock.dto.ProductoAlmacenDto;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@Controller
public class AjusteInventarioController {

	@RequestMapping(method=RequestMethod.GET, value="/api/v1/ajusteinventario/list")
	public @ResponseBody List<AjusteInventarioDto> todosAjustesInventarios(){
				List<AjusteInventario> ajusteinventarios = (List<AjusteInventario>) ajusteInventarioRepository.findAll();
				List<AjusteInventarioDto> ajusteinventarioDtos = new ArrayList<AjusteInventarioDto>();
				for (AjusteInventario ajusteinventario : ajusteinventarios) {ajusteinventarioDtos.add(leer(ajusteinventario));}
				return ajusteinventarioDtos;
	}	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/ajusteinventario/add")
	public @ResponseBody String agregarAjusteInventario(@RequestBody AjusteInventarioAltaDto ajusteInventarioDto){
				AjusteInventario nuevoAjusteInventario = new AjusteInventario();
				Date fecha = new Date();
				Producto producto = productoRepository.findOne(ajusteInventarioDto.getProductoId());
				nuevoAjusteInventario.setFecha(fecha);
				nuevoAjusteInventario.setProducto(producto);
				UnidadMedida unidadMedida= unidadMedidaRepository.findOne(ajusteInventarioDto.getUnidadMedidaId());
				nuevoAjusteInventario.setUnidadMedida(unidadMedida);
				nuevoAjusteInventario.setStockAnterior(ajusteInventarioDto.getStockAnterior());
				nuevoAjusteInventario.setStockNuevo(ajusteInventarioDto.getStockNuevo());
				nuevoAjusteInventario.setObservaciones(ajusteInventarioDto.getObservaciones());
				ProductoAlmacen productoAlmacenRepo = (ProductoAlmacen)productoAlmacenRepository.findByCodProduct(ajusteInventarioDto.getProductoId(),ajusteInventarioDto.getAlmacenId());
				productoAlmacenRepo.setCantidad(ajusteInventarioDto.getStockNuevo());
				productoAlmacenRepository.save(productoAlmacenRepo);	
				ajusteInventarioRepository.save(nuevoAjusteInventario);				
				return "OK";
	}		
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/productoalmacen/filter")
	public @ResponseBody ProductoAlmacenDto listadoProductoAlmacenFilter(@RequestBody FilterSaldoPorProductoDto filterSaldoXProductoDto, HttpServletRequest request) throws ParseException{
		Producto producto = productoRepository.findByCodProduct(filterSaldoXProductoDto.getProductoId());
		ProductoAlmacen productoAlmacen = productoAlmacenRepository.findByCodProduct(filterSaldoXProductoDto.getProductoId(),filterSaldoXProductoDto.getAlmacenId());		
		ProductoAlmacenDto pad= new ProductoAlmacenDto();
		pad.setId(producto.getIdSa());
		ProductoDetalle pd=obtenerProductoDetalle(producto);
		if(pd!=null){ pad.setUnidadMedidaId(pd.getUnidadMedida().getId()); pad.setDescripcionUnidadMedida(pd.getUnidadMedida().getDescripcion()); }
		pad.setCodigoBarra(producto.getCodigoBarra());
		pad.setStock(new BigDecimal(0));
		if(productoAlmacen!=null){pad.setStock(productoAlmacen.getCantidad());}
		return pad;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/ajusteinventario/filtro")
	public @ResponseBody List<AjusteInventarioDto> listadoAjusteInventarioFIlter(@RequestBody AjusteInventarioFiltroDto filterAjusteInventario, HttpServletRequest request) throws ParseException{					
		return obtenerFiltrado(filterAjusteInventario);
	}		
	private List<AjusteInventarioDto> obtenerFiltrado(AjusteInventarioFiltroDto filtro){
		List<AjusteInventarioDto> ajusteinventarioDtos = new ArrayList<AjusteInventarioDto>();
		
		long idFamilia =filtro.getFamiliaId();long idMarca =filtro.getMarcaId();
		Date fechaDesde = filtro.getFechaDesde();Date fechaHasta = filtro.getFechaHasta();		
		
		if(fechaDesde!=null && fechaHasta!=null && idFamilia>0 && idMarca>0){			
				long id = productoRepository.findByFamiliaMarcas(idFamilia,idMarca);			
				List<AjusteInventario> ajusteInventario = ajusteInventarioRepository.findByMarcaFamiliaFechas(id,fechaDesde,fechaHasta);					
						if(ajusteInventario!=null){
							for(AjusteInventario ai : ajusteInventario){ajusteinventarioDtos.add(recuperarAjuste(ai));}						
						}			
		}
		if(idFamilia==0 && idMarca==0 && fechaDesde!=null && fechaHasta!=null){			
				List<AjusteInventario> ajusteInventario =ajusteInventarioRepository.findFechas(fechaDesde, fechaHasta);
					if(ajusteInventario!=null){
						for(AjusteInventario ai : ajusteInventario){ajusteinventarioDtos.add(recuperarAjuste(ai));}						
					}			
		}		
		if(fechaDesde==null && fechaHasta==null && idFamilia>0 && idMarca>0){			
				long idd = productoRepository.findByFamiliaMarcas(idFamilia,idMarca)  ;
				List<AjusteInventario>ajusteInventario = ajusteInventarioRepository.findByMarcaFamilia(idd);
					if(ajusteInventario!=null){
						for(AjusteInventario ai : ajusteInventario){ajusteinventarioDtos.add(recuperarAjuste(ai));}						
					}			
		}		
		if(fechaDesde==null && fechaHasta==null && idMarca==0 && idFamilia>0){			
				List<AjusteInventario>ajusteInventario = ajusteInventarioRepository.findByFamilia(idFamilia);
					if(ajusteInventario!=null){
						for(AjusteInventario ai : ajusteInventario){ajusteinventarioDtos.add(recuperarAjuste(ai));}
					}
		}		
		if(fechaDesde==null && fechaHasta==null && idMarca==0 && idFamilia==0){
			List<AjusteInventario> ajusteInventario = (List<AjusteInventario>) ajusteInventarioRepository.findAll();
					if(ajusteInventario!=null){
						for (AjusteInventario ai : ajusteInventario) {ajusteinventarioDtos.add(recuperarAjuste(ai));}
					}
		}		
		return ajusteinventarioDtos;
	}	
	private AjusteInventarioDto recuperarAjuste(AjusteInventario ai){				
		return leer(ai);
	}	
	private AjusteInventarioDto leer(AjusteInventario in) {
		AjusteInventarioDto dto = new AjusteInventarioDto();
		
		dto.setFecha(in.getFecha());
		
		if(in.getProducto()!=null){
			dto.setCodigoBarra(in.getProducto().getCodigoBarra());
			dto.setDescripcionProducto(in.getProducto().getDescripcion());
		}else{
			dto.setCodigoBarra("");
			dto.setDescripcionProducto("");
		}			
		if(in.getUnidadMedida()!=null){
			dto.setDescripcionUnidadMedida(in.getUnidadMedida().getDescripcion());
		}else{
			dto.setDescripcionUnidadMedida("");
		}		
		dto.setStockAnterior(in.getStockAnterior());
		dto.setStockNuevo(in.getStockNuevo());
		dto.setObservaciones(in.getObservaciones());
		return dto;
	}
	private ProductoDetalle obtenerProductoDetalle(Producto p){
		String ppal = p.getUnidadMedidaPrincipal();
		ProductoDetalle pd = null;
		if(ppal.equalsIgnoreCase("A")){pd = p.getProductoDetalleA();}
		if(ppal.equalsIgnoreCase("B")){pd = p.getProductoDetalleB();}
		if(ppal.equalsIgnoreCase("C")){pd = p.getProductoDetalleC();}
		return pd;
	}
	
	

	
	
	@Autowired
	private ProductoAlmacenRepository productoAlmacenRepository;
	@Autowired
	private ProductoRepository productoRepository;
	@Autowired
	private UnidadMedidaRepository unidadMedidaRepository;
	@Autowired
	private AjusteInventarioRepository ajusteInventarioRepository;
	
}
