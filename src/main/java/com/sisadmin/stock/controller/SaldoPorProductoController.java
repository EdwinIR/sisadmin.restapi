package com.sisadmin.stock.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sisadmin.entity.Producto;
import com.sisadmin.entity.ProductoAlmacen;
import com.sisadmin.entity.ProductoDetalle;
import com.sisadmin.entity.UnidadMedida;
import com.sisadmin.repository.ProductoAlmacenRepository;
import com.sisadmin.repository.ProductoRepository;
import com.sisadmin.stock.dto.FilterSaldoPorProductoDto;
import com.sisadmin.stock.dto.SaldoPorProductoDto;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

/**
 * SaldoPorProductoController
 * @author ciro
 *
 */
@Controller
public class SaldoPorProductoController {
	public static Date f= new Date();
	public static long h=f.getTime();
	public static Timestamp fh= new Timestamp(h);
	
	private BigDecimal convertir(UnidadMedida unidadPrincipal, UnidadMedida unidadResultado,BigDecimal stock){
		
		BigDecimal resultado= null;
		
		int fisicoPrincipal = unidadPrincipal.getFisico();
		int fisicoResultado = unidadResultado.getFisico();
		
		BigDecimal porcentaje = new BigDecimal(fisicoPrincipal).divide(new BigDecimal(fisicoResultado).setScale(2,RoundingMode.CEILING)).setScale(2,RoundingMode.CEILING);
		
		resultado=	porcentaje.multiply(stock);
		
		return resultado;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/saldoporproducto/filter")
	public @ResponseBody SaldoPorProductoDto listadoSaldoProductoFilter(@RequestBody FilterSaldoPorProductoDto filterSaldoXProductoDto, HttpServletRequest request) throws ParseException{		
		Producto producto= productoRepository.findByCodProduct(filterSaldoXProductoDto.getProductoId());
		ProductoAlmacen productoAlmacen = productoAlmacenRepository.findByCodProduct(filterSaldoXProductoDto.getProductoId(),filterSaldoXProductoDto.getAlmacenId());
		
		String uniPpal =  producto.getUnidadMedidaPrincipal();
		UnidadMedida u = null;
		if(uniPpal.equalsIgnoreCase("A")){ ProductoDetalle d = producto.getProductoDetalleA(); u=d.getUnidadMedida();}
		if(uniPpal.equalsIgnoreCase("B")){ ProductoDetalle d = producto.getProductoDetalleB(); u=d.getUnidadMedida();}
		if(uniPpal.equalsIgnoreCase("C")){ ProductoDetalle d = producto.getProductoDetalleC(); u=d.getUnidadMedida();}
		
		UnidadMedida uA= null;
		UnidadMedida uB =null;
		UnidadMedida uC =null;
		
		BigDecimal stockA = new BigDecimal(0.00);
		BigDecimal stockB =new BigDecimal(0.00);
		BigDecimal stockC = new BigDecimal(0.00);
		
		BigDecimal precioA = new BigDecimal(0.00);
		BigDecimal precioB = new BigDecimal(0.00);
		BigDecimal precioC = new BigDecimal(0.00);
		if (producto.getProductoDetalleA() != null){
			uA = producto.getProductoDetalleA().getUnidadMedida();
			stockA= convertir(u, uA,productoAlmacen.getCantidad());
			precioA = producto.getProductoDetalleA().getPrecio();
		}
		if  (producto.getProductoDetalleB() != null){
			uB = producto.getProductoDetalleB().getUnidadMedida();
			stockB= convertir(u, uB,productoAlmacen.getCantidad());
			precioB = producto.getProductoDetalleB().getPrecio();
		}
		if  (producto.getProductoDetalleC() != null){
			uC = producto.getProductoDetalleC().getUnidadMedida();
			stockC= convertir(u, uC,productoAlmacen.getCantidad());
			precioC = producto.getProductoDetalleC().getPrecio();
		}
		
		SaldoPorProductoDto spp = new SaldoPorProductoDto();
		spp.setRazonSocialProveedor("");
		if(producto.getProveedor()!=null)
			spp.setRazonSocialProveedor(producto.getProveedor().getRazonSocial());			
		spp.setDescripcionFamilia("");
		spp.setDescripcionMarca("");
		if(producto.getMarcaFamilia()!=null){
			spp.setDescripcionFamilia(producto.getMarcaFamilia().getFamilia().getName());		
			spp.setDescripcionMarca(producto.getMarcaFamilia().getMarca().getDescripcion());
		}
		spp.setUltimaCompra(producto.getPrecioUltimaCompra());
		spp.setCodigoEquivalente(producto.getCodigoEquivalente());
		spp.setCodigoBarra(producto.getCodigoBarra());		
		spp.setPrecioA(precioA);
		spp.setPrecioB(precioB);
		spp.setPrecioC(precioC);
		spp.setStockA(stockA);
		spp.setStockB(stockB);
		spp.setStockC(stockC);
		
		return spp;
		
	}
	/**BuscarSaldoPorProducto**/
	/*@RequestMapping(method=RequestMethod.POST, value="/api/v1/saldoporproducto/find")
	public @ResponseBody List<ProductoAlmacenDto> todosSaldoPorProducto(SaldoPorProductoDto saldoPorProductoDto){
		/*List<ProductoAlmacenDto> productoAlmacenDtos = new ArrayList<ProductoAlmacenDto>();
		List<ProductoAlmacen> productoAlmacens = productoAlmacenRepository.findProductoAlmacenbyAlmacen(productoAlmacen.getAlmacen().getId());
		for(ProductoAlmacen proAlmacen : productoAlmacens){
			productoAlmacenDtos.add(convertToDto(proAlmacen));
		}
		*/
		/*System.out.println(saldoPorProductoDto.getAlmacenId());
		System.out.println(saldoPorProductoDto.getMarcaId());
		System.out.println(saldoPorProductoDto.getFamiliaId());
		return null;
	}*/
	
	/**convertToDto**/
	/*private ProductoAlmacenDto convertToDto(ProductoAlmacen productoAlmacen){
		ProductoAlmacenDto productoAlmacenDto = new ProductoAlmacenDto();
		
		//pensar para usarlo con ids
		/**productoAlmacenDto.setProducto(productoAlmacen.getProducto());
		productoAlmacenDto.setAlmacen(productoAlmacen.getAlmacen());
		productoAlmacenDto.setCantidad(productoAlmacen.getCantidad());
		productoAlmacenDto.setUnidadMedida(productoAlmacen.getUnidadMedida());**/
		
		/*return productoAlmacenDto;
	}*/
	
	/*ProductoAlmacenRepository*/
	/*private ProductoAlmacenRepository productoAlmacenRepository;
	@Autowired
	public void setProductoAlmacenRepository(
			ProductoAlmacenRepository productoAlmacenRepository) {
		this.productoAlmacenRepository = productoAlmacenRepository;
	}*/
	
	@Autowired
	private ProductoRepository productoRepository;
	@Autowired
	private ProductoAlmacenRepository productoAlmacenRepository;
	
}
