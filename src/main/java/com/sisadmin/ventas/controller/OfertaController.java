package com.sisadmin.ventas.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sisadmin.entity.Oferta;
import com.sisadmin.entity.OfertaDetalle;
import com.sisadmin.entity.Producto;
import com.sisadmin.entity.ProductoDetalle;
import com.sisadmin.repository.OfertaRepository;
import com.sisadmin.repository.ProductoDetalleRepository;
import com.sisadmin.repository.ProductoRepository;
import com.sisadmin.stock.dto.ProductoDetalleDto;
import com.sisadmin.ventas.dto.OfertaDetalleDto;
import com.sisadmin.ventas.dto.OfertaDto;
import com.sisadmin.ventas.dto.ProductoOfertaDto;

@Controller
public class OfertaController {

	@RequestMapping(method=RequestMethod.GET, value="/api/v1/oferta/list")
	public @ResponseBody List<Oferta> todasOfertas(){
				List<Oferta> ofertas = (List<Oferta>) ofertaRepository.findAll();
				
				
				
				/*
				List<OrdenCompraDto> ordencomprasDtos = new ArrayList<OrdenCompraDto>();
				for (OrdenCompra ordencompra : ordencompras) {
					ordencomprasDtos.add(convertToDto(ordencompra));
				}*/
				return ofertas;
	}
	
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/productooferta/list")
	public @ResponseBody List<ProductoOfertaDto> productoOferta(){

				List<Producto> productos = (List<Producto>)productoRepository.findAll();
				List<ProductoOfertaDto> ofertaDetalleDtos = new ArrayList<ProductoOfertaDto>();
		
				for (Producto producto : productos) {
						ofertaDetalleDtos.add(generarProductoOfertaPara(producto));
				}		
				return ofertaDetalleDtos;
	}
	
	
	
	
	private ProductoOfertaDto generarProductoOfertaPara(Producto producto){
				
			ProductoOfertaDto productoOfertaDto = new ProductoOfertaDto();
			productoOfertaDto.setDescripcionProducto(producto.getDescripcion());
			productoOfertaDto.setProductoId(producto.getIdSa());
			if(producto.getProductoDetalleA()!= null) { productoOfertaDto.setPrecioA(producto.getProductoDetalleA().getPrecio());}
			if(producto.getProductoDetalleB()!= null) {	productoOfertaDto.setPrecioB(producto.getProductoDetalleB().getPrecio()); }
			if(producto.getProductoDetalleC()!= null) { productoOfertaDto.setPrecioC(producto.getProductoDetalleC().getPrecio()); }
			return productoOfertaDto;
	}
	
	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/oferta/add")
	public @ResponseBody String agregarOferta(@RequestBody OfertaDto ofertaDto){
		//OrdenCompra ordenCompra = convertToOrdenCompra(ordenCompraDto);
		
		Oferta of = generarOferta(ofertaDto);
		ofertaRepository.save(of);
		return "OK";
	}
	
	private Oferta generarOferta(OfertaDto ofertaDto){
		Oferta oferta = new Oferta();
		oferta.setDescripcion(oferta.getDescripcion());
		oferta.setFechaInicio(ofertaDto.getFechaInicio());
		oferta.setFechaFin(ofertaDto.getFechaFin());
		for(OfertaDetalleDto detalleDto : ofertaDto.getOfertaDetalleDtos()){
					OfertaDetalle det = new OfertaDetalle();
					det.setPrecioOfertaA(detalleDto.getPrecioOfertaA());
					det.setPrecioOfertaB(detalleDto.getPrecioOfertaB());
					det.setPrecioOfertaC(detalleDto.getPrecioOfertaC());					
					det.setPrecioA(detalleDto.getPrecioA());
					det.setPrecioB(detalleDto.getPrecioB());
					det.setPrecioC(detalleDto.getPrecioC());
					oferta.getDetalles().add(det);
					det.setOferta(oferta);
		}
		return oferta;
	}
	
	
	
	
	
	
	@Autowired
	private OfertaRepository ofertaRepository;
	
	
	@Autowired
	private ProductoRepository productoRepository;

	
	
}




/**
@RequestMapping(method=RequestMethod.GET, value="/api/v1/ofertadetalle/list")
public @ResponseBody List<ProductoDetalleDto> todosAfpPeriodos(){
			List<ProductoDetalle> productoDetalle = (List<ProductoDetalle>) productoDetalleRepository.findAll();
			List<ProductoDetalleDto> productoDetalleDtos = new ArrayList<ProductoDetalleDto>();
			
			return productoDetalleDtos;
}

@RequestMapping(method=RequestMethod.POST, value="/api/v1/ofertadetalle/bycodigoproducto")
public @ResponseBody List<ProductoDetalle> obtenerProductoAlmacen(@RequestBody Long id){
	List<ProductoDetalle> productos = new ArrayList<ProductoDetalle>();
	productos = productoDetalleRepository.findByCodProducto(id);
	//convertToDto(producto);
	return productos;
}

private ProductoDetalleDto convertToDto(Producto producto) {
	ProductoDetalleDto dto = new ProductoDetalleDto();
	
	return dto;
}

@Autowired
private ProductoDetalleRepository productoDetalleRepository;
**/

