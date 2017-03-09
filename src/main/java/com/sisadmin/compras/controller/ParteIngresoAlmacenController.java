package com.sisadmin.compras.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sisadmin.compras.dto.FilterOrdenCompraDto;
import com.sisadmin.compras.dto.ListActualizacionPrecioDto;
import com.sisadmin.compras.dto.ParteIngresoAlmacenDto;
import com.sisadmin.compras.dto.ParteIngresoDetalleCompraDto;
import com.sisadmin.compras.dto.ParteIngresoOrdenCompraDto;
import com.sisadmin.entity.ActualizarPrecio;
import com.sisadmin.entity.Almacen;
import com.sisadmin.entity.MarcaFamilia;
import com.sisadmin.entity.OrdenCompra;
import com.sisadmin.entity.OrdenCompraDetalle;
import com.sisadmin.entity.ParteIngresoAlmacen;
import com.sisadmin.entity.Producto;
import com.sisadmin.entity.ProductoAlmacen;
import com.sisadmin.entity.ProductoDetalle;
import com.sisadmin.entity.Proveedor;
import com.sisadmin.entity.UnidadMedida;
import com.sisadmin.repository.ActualizarPrecioRepository;
import com.sisadmin.repository.AlmacenRepository;
import com.sisadmin.repository.MarcaFamiliaRepository;
import com.sisadmin.repository.OrdenCompraDetalleRepository;
import com.sisadmin.repository.OrdenCompraRepository;
import com.sisadmin.repository.ParteIngresoAlmacenRepository;
import com.sisadmin.repository.ProductoAlmacenRepository;
import com.sisadmin.repository.ProductoDetalleRepository;
import com.sisadmin.repository.ProductoRepository;
import com.sisadmin.repository.ProveedorRepository;
import com.sisadmin.repository.UnidadMedidaRepository;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@Controller
public class ParteIngresoAlmacenController {

	/**ParteIngresoAlmacen**/
	private ParteIngresoAlmacenDto convertToDto(ParteIngresoAlmacen in) {
		ParteIngresoAlmacenDto dto = new ParteIngresoAlmacenDto();
		dto.setId(in.getId());
		dto.setNumeroInterno(in.getNumeroInterno());
		dto.setFechaIngreso(in.getFechaIngreso());
		dto.setRazonSocialProveedor(in.getProveedor().getRazonSocial());
		if(in.getProveedor()!=null) {dto.setProveedorId(in.getProveedor().getId());}
		if(in.getOrdenCompra()!=null) {dto.setOrdenCompraId(in.getOrdenCompra().getId());}		
		return dto;
	}
	
	private ProductoAlmacen agregarProductoAlmacen(OrdenCompraDetalle ocd,long idAlmacen){
		ProductoAlmacen productoAlmacen = (ProductoAlmacen)productoAlmacenRepository.findByCodProductoCodAlmacen(ocd.getProducto().getIdSa(), idAlmacen);
		Date fecha = new Date(); 
		long hora = fecha.getTime();
		Timestamp fechahora= new Timestamp(hora);
		
		if(productoAlmacen!=null){			
			productoAlmacen.setCantidad(productoAlmacen.getCantidad().add(new BigDecimal(ocd.getCantidad())));
			productoAlmacen.setUsuarioModificacion("UsuarioModificador");
			productoAlmacen.setFechaModificacion(fechahora);
			return productoAlmacen;	
		}else{
			ProductoAlmacen proAlmacen = new ProductoAlmacen();
			Producto producto = (Producto)productoRepository.findOne(ocd.getProducto().getIdSa());		
			Almacen almacen = (Almacen)almacenRepository.findOne(idAlmacen);	
			proAlmacen.setProducto(producto);
			proAlmacen.setAlmacen(almacen);
			proAlmacen.setCantidad(new BigDecimal(ocd.getCantidad()));
			proAlmacen.setStockMinimo(new BigDecimal(1));
			proAlmacen.setStockMaximo(new BigDecimal(1000));
			proAlmacen.setUsuarioCreacion("UsuarioCreador");
			proAlmacen.setFechaCreacion(fechahora);
			return proAlmacen;
		}		
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/parteingresoalmacen/list")
	public @ResponseBody List<ParteIngresoAlmacenDto> todasPartesIngresosAlmacenes(){
				List<ParteIngresoAlmacen> parteingresoalmacenes = (List<ParteIngresoAlmacen>) parteIngresoAlmacenRepository.findAll();
				List<ParteIngresoAlmacenDto> parteingresoalmacenesDtos = new ArrayList<ParteIngresoAlmacenDto>();
				for (ParteIngresoAlmacen parteingresoalmacen : parteingresoalmacenes) {
					parteingresoalmacenesDtos.add(convertToDto(parteingresoalmacen));
				}
				return parteingresoalmacenesDtos;
	}	
	@Transactional
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/parteingresolistado/list")
	public @ResponseBody List<ListActualizacionPrecioDto> todasPartesIngresos(){
				ArrayList<ListActualizacionPrecioDto> listaAll = new ArrayList<ListActualizacionPrecioDto>();	
				ArrayList<ListActualizacionPrecioDto> listaLimpia = new ArrayList<ListActualizacionPrecioDto>();
				List<ActualizarPrecio> listaPrecio = (List<ActualizarPrecio>)actualizarPrecioRepository.findAll();
				
				for(ActualizarPrecio precio:listaPrecio){
					List<ActualizarPrecio> listado = (List<ActualizarPrecio>)actualizarPrecioRepository.listadoIngresados(precio.getProducto().getDescripcion(),"PENDIENTE");
					if(listado.size()>0){
					ActualizarPrecio oc = listado.get(0);
					ProductoDetalle pd = (ProductoDetalle)productoDetalleRepository.findByCodProductoAndUnidadMedida(oc.getProducto().getIdSa(),oc.getUnidadMedida().getCodigo());
					ListActualizacionPrecioDto lista = new ListActualizacionPrecioDto();
					lista.setIdPrecio(oc.getId());
					lista.setId((int)oc.getProducto().getIdSa());
					lista.setCodigoProducto(oc.getProducto().getCodigoBarra());
					lista.setDescripcionProducto(oc.getProducto().getDescripcion());
					lista.setCodigoUnidadMedida(oc.getUnidadMedida().getCodigo());
					lista.setDescripcionUnidadMedida(oc.getUnidadMedida().getDescripcion());
					lista.setPrecioActual(pd.getPrecio());		
					BigDecimal precioNuevo = new BigDecimal(0);
					precioNuevo = transformarprecio(oc.getProducto(), oc.getPrecioNuevo());
					lista.setPrecioNuevo(precioNuevo);
					listaAll.add(lista);
					}
				}				
						
				Map<Integer, ListActualizacionPrecioDto> mapLista = new HashMap<Integer, ListActualizacionPrecioDto>(listaAll.size());
				for(ListActualizacionPrecioDto l : listaAll) {
					mapLista.put(l.getId(), l);
				 }
				for(Entry<Integer, ListActualizacionPrecioDto> l : mapLista.entrySet()) {
						 listaLimpia.add(l.getValue());
				}
			return	listaLimpia;
	}	
	private BigDecimal transformarprecio(Producto pro,BigDecimal precio){	

		BigDecimal precioNuevo = new BigDecimal(0);
		Producto producto = (Producto)productoRepository.findOne(pro.getIdSa());
		
		if(producto.getMarcaFamilia()!=null){			
			MarcaFamilia marcaFamilia =(MarcaFamilia)marcaFamiliaRepository.findOne(producto.getMarcaFamilia().getId());		 
			String uniPpal =  producto.getUnidadMedidaPrincipal();
			UnidadMedida u = null;
			UnidadMedida uA= null;
			UnidadMedida uB =null;
			UnidadMedida uC =null;
			if(uniPpal.equalsIgnoreCase("A")){ 
				ProductoDetalle d = producto.getProductoDetalleA();
				u=d.getUnidadMedida();
				int fisicoPrincipal = u.getFisico();
				BigDecimal margenBaseProducto= new BigDecimal(0);	
				margenBaseProducto = precio.add(marcaFamilia.getMargenBase().multiply(precio).divide(new BigDecimal(100))).setScale(2,RoundingMode.CEILING);
				uA = producto.getProductoDetalleA().getUnidadMedida();
				BigDecimal porcentaje = new BigDecimal(fisicoPrincipal).divide(new BigDecimal(uA.getFisico())).setScale(2,RoundingMode.CEILING);	
				precioNuevo =(margenBaseProducto.add(marcaFamilia.getMargenA().multiply(margenBaseProducto).divide(new BigDecimal(100)))).divide(porcentaje).setScale(2,RoundingMode.CEILING);
				return precioNuevo;
			}
			if(uniPpal.equalsIgnoreCase("B")){ 
				ProductoDetalle d = producto.getProductoDetalleB(); 
				u=d.getUnidadMedida();
				int fisicoPrincipal = u.getFisico();
				BigDecimal margenBaseProducto= new BigDecimal(0);	
				margenBaseProducto = precio.add(marcaFamilia.getMargenBase().multiply(precio).divide(new BigDecimal(100))).setScale(2,RoundingMode.CEILING);
				uB = producto.getProductoDetalleB().getUnidadMedida();
				BigDecimal porcentaje;
				porcentaje = new BigDecimal(fisicoPrincipal).divide(new BigDecimal(uB.getFisico()));
				precioNuevo=(margenBaseProducto.add(marcaFamilia.getMargenB().multiply(margenBaseProducto).divide(new BigDecimal(100)))).divide(porcentaje,RoundingMode.CEILING);
			
			}
			if(uniPpal.equalsIgnoreCase("C")){ 				
				ProductoDetalle d = producto.getProductoDetalleC();
				u=d.getUnidadMedida();
				int fisicoPrincipal = u.getFisico();
				BigDecimal margenBaseProducto= new BigDecimal(0);	
				margenBaseProducto = precio.add(marcaFamilia.getMargenBase().multiply(precio).divide(new BigDecimal(100))).setScale(2,RoundingMode.CEILING);
				uC = producto.getProductoDetalleC().getUnidadMedida();			
				BigDecimal porcentaje = new BigDecimal(fisicoPrincipal).divide(new BigDecimal(uC.getFisico())).setScale(2,RoundingMode.CEILING);			
				precioNuevo =(margenBaseProducto.add(marcaFamilia.getMargenC().multiply(margenBaseProducto).divide(new BigDecimal(100)))).divide(porcentaje).setScale(2,RoundingMode.CEILING);
				
			}		
		}
		return precioNuevo;
	}
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/parteingresoalmacen")	   
	public @ResponseBody String borrarParteIngresoAlmacen(@RequestParam(value="id") Long id){
		
		ParteIngresoAlmacen pi = parteIngresoAlmacenRepository.findOne(id);		
		OrdenCompra ordencompra = (OrdenCompra)ordenCompraRepository.findOne(pi.getOrdenCompra().getId());
		ordencompra.setEstado("Pendiente");
		ordenCompraRepository.save(ordencompra);
		/* 
		TODO
		Actualizar Stock de Productos quitando lo ingreso
		*/
		parteIngresoAlmacenRepository.delete(id);
		return "Ok";				
	}	
	@Transactional
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/parteingresoalmacen/add")
	public @ResponseBody String agregarParteIngresoAlmacen(@RequestBody ParteIngresoAlmacenDto parteIngresoAlmacenDto){
				ParteIngresoAlmacen nuevoParteIngresoAlmacen = new ParteIngresoAlmacen();
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				nuevoParteIngresoAlmacen.setNumeroInterno(parteIngresoAlmacenDto.getNumeroInterno());
				nuevoParteIngresoAlmacen.setFechaIngreso(parteIngresoAlmacenDto.getFechaIngreso());				
				Proveedor proveedor = (Proveedor)proveedorRepository.findOne(parteIngresoAlmacenDto.getProveedorId());
				nuevoParteIngresoAlmacen.setProveedor(proveedor);
				OrdenCompra ordencompra = (OrdenCompra)ordenCompraRepository.findOne(parteIngresoAlmacenDto.getOrdenCompraId());
				nuevoParteIngresoAlmacen.setOrdenCompra(ordencompra);				
				for(OrdenCompraDetalle ocd: ordencompra.getDetalleOrdenCompras()){									
					ActualizarPrecio precio = new ActualizarPrecio();
					productoAlmacenRepository.save(agregarProductoAlmacen(ocd,parteIngresoAlmacenDto.getAlmacenId()));
					
					Producto producto = (Producto)productoRepository.findOne(ocd.getProducto().getIdSa());	
					precio.setProducto(producto);
					BigDecimal precioNuevo = new BigDecimal(0);
					precioNuevo= ocd.getPrecioNeto().add(ocd.getPrecioNeto().multiply(new BigDecimal(0.18))).setScale(2,RoundingMode.CEILING);;
					precio.setPrecioNuevo(precioNuevo);
					UnidadMedida unidadMedida = (UnidadMedida)unidadMedidadRepository.findOne(ocd.getUnidadMedida().getId());
					precio.setUnidadMedida(unidadMedida);
					precio.setFechaIngreso(parteIngresoAlmacenDto.getFechaIngreso());
					precio.setEstado("PENDIENTE");
					actualizarPrecioRepository.save(precio);					
				}				
				nuevoParteIngresoAlmacen.setUsuarioCreacion("UsuarioCreador");
				nuevoParteIngresoAlmacen.setFechaCreacion(fechahora);
				parteIngresoAlmacenRepository.save(nuevoParteIngresoAlmacen);
				ordencompra.setEstado("Ingresada");
				ordenCompraRepository.save(ordencompra);
				/* 
				TODO
				Actualizar Stock de Productos Agregando nuevos productos
				*/				
				return "OK";
	}	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/parteingresoalmacen/update")
	public @ResponseBody String actualizarParteIngresoAlmacen(@RequestBody ParteIngresoAlmacenDto parteingresoalmacenParam){
				ParteIngresoAlmacen parteingresoalmacenRepo = (ParteIngresoAlmacen)parteIngresoAlmacenRepository.findOne(parteingresoalmacenParam.getId());	
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				parteingresoalmacenRepo.setNumeroInterno(parteingresoalmacenParam.getNumeroInterno());
				parteingresoalmacenRepo.setFechaIngreso(parteingresoalmacenParam.getFechaIngreso());
				Proveedor proveedor = (Proveedor)proveedorRepository.findOne(parteingresoalmacenParam.getProveedorId());
				parteingresoalmacenRepo.setProveedor(proveedor);
				OrdenCompra ordencompra = (OrdenCompra)ordenCompraRepository.findOne(parteingresoalmacenParam.getOrdenCompraId());
				parteingresoalmacenRepo.setOrdenCompra(ordencompra);
				parteingresoalmacenRepo.setUsuarioModificacion("UsuarioModificador");
				parteingresoalmacenRepo.setFechaModificacion(fechahora);
				parteIngresoAlmacenRepository.save(parteingresoalmacenRepo);				
				return "OK";
	}	
	@Transactional
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/ordencompra/filter")
	public @ResponseBody ParteIngresoOrdenCompraDto listadoOrdenCompraFilter(@RequestBody FilterOrdenCompraDto filterOrdenCompraDto, HttpServletRequest request) throws ParseException{
		OrdenCompra ordenCompra = ordenCompraRepository.findOne(filterOrdenCompraDto.getOrdenCompraId());		
		
		//OrdenCompraDto dto = new OrdenCompraDto();
		ParteIngresoOrdenCompraDto dto=new ParteIngresoOrdenCompraDto();
		
		dto.setId(ordenCompra.getId());
		dto.setCodigo(ordenCompra.getCodigo());
		dto.setFechaEmision(ordenCompra.getFechaEmision());
		dto.setIdProveedor(ordenCompra.getProveedor().getId());
		dto.setFechaRecepcion(ordenCompra.getFechaRecepcion());
		dto.setRazonSocialProveedor(ordenCompra.getProveedor().getRazonSocial());
		dto.setDescripcionCondicionPago(ordenCompra.getCondicionPago().getDescripcion());
		dto.setDescripcionAlmacen(ordenCompra.getAlmacen().getDescripcion());
		dto.setDescripcionTipoPago(ordenCompra.getTipoMoneda().getDescripcion());	
		
		for(OrdenCompraDetalle o : ordenCompra.getDetalleOrdenCompras()){
		
		//for(ParteIngresoDetalleCompraDto: ordenCompra.getDetalleOrdenCompras()){
				//OrdenCompraDetalleDto ocd = new OrdenCompraDetalleDto();	
				ParteIngresoDetalleCompraDto ocd= new ParteIngresoDetalleCompraDto();
				
				//ocd.setProductoId(o.getProducto().getId());
				//ocd.setUnidadMedidaId(o.getUnidadMedida().getId());
				ocd.setDescripcionProducto(o.getProducto().getDescripcion());
				ocd.setDescripcionUnidadMedida(o.getUnidadMedida().getAbreviado());
				ocd.setBonificado(o.isBonificado());
				ocd.setPrecioUnitario(o.getPrecioUnitario());			
				ocd.setDescuento1(o.getDescuento1());
				ocd.setDescuento2(o.getDescuento2());
				ocd.setDescuento3(o.getDescuento3());
				ocd.setDescuento4(o.getDescuento4());
				ocd.setPrecioNeto(o.getPrecioNeto());
				ocd.setCantidad(o.getCantidad());
				ocd.setTotalCompra(o.getTotalCompra());
		
				dto.getDetalles().add(ocd);
		}		
		
		return dto;
	}	

	@Autowired
	ParteIngresoAlmacenRepository parteIngresoAlmacenRepository;
	@Autowired
	ProveedorRepository proveedorRepository;
	@Autowired
	OrdenCompraRepository ordenCompraRepository;
	@Autowired
	ProductoRepository productoRepository;
	@Autowired
	ProductoAlmacenRepository productoAlmacenRepository;
	@Autowired
	AlmacenRepository almacenRepository;
	@Autowired
	ProductoDetalleRepository productoDetalleRepository;
	@Autowired
	OrdenCompraDetalleRepository ordenCompraDetalleRepository;
	@Autowired
	MarcaFamiliaRepository marcaFamiliaRepository;
	@Autowired
	ActualizarPrecioRepository actualizarPrecioRepository;
	@Autowired
	UnidadMedidaRepository unidadMedidadRepository;
}
