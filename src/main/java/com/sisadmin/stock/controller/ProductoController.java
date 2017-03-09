package com.sisadmin.stock.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.StyledEditorKit.BoldAction;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import au.com.bytecode.opencsv.CSVReader;

import com.sisadmin.compras.dto.OrdenCompraFilter;
import com.sisadmin.compras.dto.ProductoImportDto;
import com.sisadmin.entity.Categorie;
import com.sisadmin.entity.Marca;
import com.sisadmin.entity.MarcaFamilia;
import com.sisadmin.entity.OrdenCompra;
import com.sisadmin.entity.Producto;
import com.sisadmin.entity.ProductoAlmacen;
import com.sisadmin.entity.ProductoDetalle;
import com.sisadmin.entity.ProductoUbicacion;
import com.sisadmin.entity.Proveedor;
import com.sisadmin.entity.Ubicacion;
import com.sisadmin.entity.UnidadMedida;
import com.sisadmin.repository.AlmacenRepository;
import com.sisadmin.repository.CategorieRepository;
import com.sisadmin.repository.MarcaFamiliaRepository;
import com.sisadmin.repository.MarcaRepository;
import com.sisadmin.repository.ProductoAlmacenRepository;
import com.sisadmin.repository.ProductoDetalleRepository;
import com.sisadmin.repository.ProductoRepository;
import com.sisadmin.repository.ProductoUbicacionRepository;
import com.sisadmin.repository.ProveedorRepository;
import com.sisadmin.repository.UbicacionRepository;
import com.sisadmin.repository.UnidadMedidaRepository;
import com.sisadmin.repository.ZonaRepository;
import com.sisadmin.stock.dto.FilterComboProductoDto;
import com.sisadmin.stock.dto.FilterReporteStock;
import com.sisadmin.stock.dto.ProductoDetalleDto;
import com.sisadmin.stock.dto.ProductoDto;
import com.sisadmin.stock.dto.ProductoUbicacionDto;
import com.sisadmin.stock.dto.ReporteProductoAlmacenDto;
import com.sisadmin.stock.dto.ReporteProductoPrecioDto;

/**
 * ABMControllerProducto
 * @author Ciro
 *
 */
@Controller
public class ProductoController {
	
	public static Date f= new Date();
	public static long h=f.getTime();
	public static Timestamp fh= new Timestamp(h);
	
	@Transactional
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/producto/list")
	public @ResponseBody List<ProductoDto> todosProductos(){
		List<Producto> productos = (List<Producto>) productoRepository.productosOrdenados();
		List<ProductoDto> productosDtos = new ArrayList<ProductoDto>();
		for (Producto producto : productos) {productosDtos.add(convertToDto(producto));}
		return productosDtos;
	}	
	@Transactional
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/producto/listaproductos")
	public @ResponseBody List<ProductoDto> listadoProductos(){
		List<Producto> productos = (List<Producto>) productoRepository.productosOrdenados();
		List<ProductoDto> productosDtos = new ArrayList<ProductoDto>();
		for (Producto producto : productos) {productosDtos.add(listaProducto(producto));}
		return productosDtos;
	}
		
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/producto")	   
	public @ResponseBody String borrarProducto(@RequestParam(value="id") Long id){
				productoRepository.delete(id);
				return "Ok";				
	}	
	@Transactional
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/producto/add")
	public @ResponseBody String agregarProducto(@RequestBody ProductoDto productoDto){
		Producto p = new Producto();
		Producto producto = convertToProducto(p,productoDto);		
		productoRepository.save(producto);
				return "OK";
	}	
	
	
	
	@Transactional
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/producto/update")
	public @ResponseBody String actualizarProducto(@RequestBody ProductoDto productoDto){
		Producto producto = (Producto)productoRepository.findOne(productoDto.getId());
		Date fecha = new Date(); 
		long hora = fecha.getTime();
		Timestamp fechahora= new Timestamp(hora);
		producto.setCodigoBarra(productoDto.getCodigoEquivalente());		
		producto.setCodigoEquivalente(productoDto.getCodigoEquivalente());		
		producto.setUnidadMedidaPrincipal(productoDto.getUnidadMedidaPrincipal());		
		Categorie categoria=familiaRepository.findOne(productoDto.getFamiliaId());
		producto.setCategory(categoria.getId());
		Marca marca = marcaRepository.findOne(productoDto.getMarcaId());
		producto.setMarca(marca.getId());
		
		producto.setDescripcion(productoDto.getDescripcion());
		producto.setPesoKilogramo(productoDto.getPesoKilogramo());
		producto.setTratamientoEspecial(productoDto.isTratamientoEspecial());
		
		ProductoUbicacion ubialmacen = null;
		for(ProductoUbicacion ubi : producto.getUbicaciones()){
			if(ubi.getUbicacion().getZona().getAlmacen().getId() == productoDto.getAlmacenId()){
				ubialmacen =ubi;
			}
		}
		if(ubialmacen != null) {producto.getUbicaciones().remove(ubialmacen);}
		
		Ubicacion ubicacion = ubicacionRepository.findOne(productoDto.getUbicacionId());
		ProductoUbicacion pub= new ProductoUbicacion();
		pub.setUbicacion(ubicacion);
		pub.setProducto(producto);
		producto.getUbicaciones().add(pub);
		
		/**
		Paso 1.  Recuperar todas las ubicaciones del producto
		Loopear en las ubicaciones, buscando la ubicacion cuya zona, cuyo almacen es el de la sesion
		guardar la ubicacion existente en la zona y almacen 
		
		Paso 2.  despues del loop, borrar de la coleccion el elemento guardado en el loop
		
		Paso 3. Agregar la nueva ubicacion ingresada
		si no existe ---> agregar nueva ubicacion
		si existe --> borrar esa ubicacion y agregar la nueva	
		
		Ubicacion ubicacion = ubicacionRepository.findOne(productoDto.getUbicacionId());
		if(ubicacion!=null) {
				ProductoUbicacion pub= new ProductoUbicacion();
				pub.setUbicacion(ubicacion);
				pub.setProducto(producto);
				producto.getUbicaciones().add(pub);
		}**/
		
		
		
		Proveedor proveedor = proveedorRepository.findOne(productoDto.getProveedorId());
		producto.setProveedor(proveedor);
		
		MarcaFamilia marcaFamilia = marcaFamiliaRepository.findByIdMarcaAndIdFamilia(productoDto.getMarcaId(), productoDto.getFamiliaId());
		producto.setMarcaFamilia(marcaFamilia);
		if(producto.getProductoDetalleA()!=null ){
			ProductoDetalle detalleA = productoDetalleRepository.findByProductId(producto.getProductoDetalleA().getId());			
				detalleA.setProducto(producto);
				UnidadMedida uA = new UnidadMedida();
				uA = unidadMedidaRepository.findOne(productoDto.getProductoDetalleA().getUnidadMedidaId());
				detalleA.setUnidadMedida(uA);
				detalleA.setCodigoBarra(productoDto.getProductoDetalleA().getCodigoBarra());		
				detalleA.setPrecio(productoDto.getProductoDetalleA().getPrecio());
				producto.setProductoDetalleA(detalleA);						
		}else{
			if(productoDto.getProductoDetalleA().getCodigoBarra()!=null && productoDto.getProductoDetalleA().getUnidadMedidaId()!=0){
				ProductoDetalle detalleAA = new ProductoDetalle();
				detalleAA.setProducto(producto);
				UnidadMedida uA = new UnidadMedida();
				uA = unidadMedidaRepository.findOne(productoDto.getProductoDetalleA().getUnidadMedidaId());
				detalleAA.setUnidadMedida(uA);
				detalleAA.setCodigoBarra(productoDto.getProductoDetalleA().getCodigoBarra());		
				detalleAA.setPrecio(productoDto.getProductoDetalleA().getPrecio());
				producto.setProductoDetalleA(detalleAA);
			}			
		}	
		if(producto.getProductoDetalleB()!=null ){
			ProductoDetalle detalleB = productoDetalleRepository.findByProductId(producto.getProductoDetalleB().getId());			
				detalleB.setProducto(producto);
				UnidadMedida uB = new UnidadMedida();
				uB = unidadMedidaRepository.findOne(productoDto.getProductoDetalleB().getUnidadMedidaId());
				detalleB.setUnidadMedida(uB);
				detalleB.setCodigoBarra(productoDto.getProductoDetalleB().getCodigoBarra());		
				detalleB.setPrecio(productoDto.getProductoDetalleB().getPrecio());
				producto.setProductoDetalleB(detalleB);			
		}else{
			if(productoDto.getProductoDetalleB().getCodigoBarra()!=null && productoDto.getProductoDetalleB().getUnidadMedidaId()!=0){
				ProductoDetalle detalleBB = new ProductoDetalle();
				detalleBB.setProducto(producto);
				UnidadMedida uB = new UnidadMedida();
				uB = unidadMedidaRepository.findOne(productoDto.getProductoDetalleB().getUnidadMedidaId());
				detalleBB.setUnidadMedida(uB);
				detalleBB.setCodigoBarra(productoDto.getProductoDetalleB().getCodigoBarra());		
				detalleBB.setPrecio(productoDto.getProductoDetalleB().getPrecio());
				producto.setProductoDetalleB(detalleBB);
			}			
		}
		if(producto.getProductoDetalleC()!=null){
			ProductoDetalle detalleC = productoDetalleRepository.findByProductId(producto.getProductoDetalleC().getId());			
				detalleC.setProducto(producto);
				UnidadMedida uC = new UnidadMedida();
				uC = unidadMedidaRepository.findOne(productoDto.getProductoDetalleC().getUnidadMedidaId());
				detalleC.setUnidadMedida(uC);
				detalleC.setCodigoBarra(productoDto.getProductoDetalleC().getCodigoBarra());		
				detalleC.setPrecio(productoDto.getProductoDetalleC().getPrecio());
				producto.setProductoDetalleC(detalleC);			
		}else{
			if(productoDto.getProductoDetalleC().getCodigoBarra()!=null && productoDto.getProductoDetalleC().getUnidadMedidaId()!=0){
				ProductoDetalle detalleCC = new ProductoDetalle();
				detalleCC.setProducto(producto);
				UnidadMedida uC = new UnidadMedida();
				uC = unidadMedidaRepository.findOne(productoDto.getProductoDetalleC().getUnidadMedidaId());
				detalleCC.setUnidadMedida(uC);
				detalleCC.setCodigoBarra(productoDto.getProductoDetalleC().getCodigoBarra());		
				detalleCC.setPrecio(productoDto.getProductoDetalleC().getPrecio());
				producto.setProductoDetalleC(detalleCC);
			}			
		}
						
		return "OK";
	}
	
	/**convertToEntity**/
	private Producto convertToProducto(Producto producto,ProductoDto productoDto){
		Date fecha = new Date(); 
		long hora = fecha.getTime();
		Timestamp fechahora= new Timestamp(hora);
		
		producto.setId(UUID.randomUUID().toString());
		producto.setCodigoBarra(productoDto.getCodigoEquivalente());		
		producto.setCodigoEquivalente(productoDto.getCodigoEquivalente());
		producto.setTaxcat("001");
		producto.setStockcost(new BigDecimal(0));
		producto.setStockvolume(new BigDecimal(0));
		producto.setIscom(Boolean.valueOf("false"));
		producto.setIsscale(Boolean.valueOf("false"));
		producto.setIskitchen(Boolean.valueOf("true"));
		producto.setPrintkb(Boolean.valueOf("false"));
		producto.setSendstatus(Boolean.valueOf("false"));
		producto.setIsservice(Boolean.valueOf("false"));
		producto.setIsvprice(Boolean.valueOf("false"));
		producto.setIsverpatrib(Boolean.valueOf("false"));
		producto.setWarranty(Boolean.valueOf("false"));
		producto.setStockunits(new BigDecimal(0));
		
		
		producto.setUnidadMedidaPrincipal(productoDto.getUnidadMedidaPrincipal());
		Categorie categoria=familiaRepository.findOne(productoDto.getFamiliaId());
		producto.setCategory(categoria.getId());
		Marca marca = marcaRepository.findOne(productoDto.getMarcaId());
		producto.setMarca(marca.getId());
		
		producto.setDescripcion(productoDto.getDescripcion());
		producto.setPesoKilogramo(productoDto.getPesoKilogramo());
		producto.setTratamientoEspecial(productoDto.isTratamientoEspecial());		
		
		Ubicacion ubicacion = new Ubicacion();
		ubicacion = ubicacionRepository.findOne(productoDto.getUbicacionId());
		ProductoUbicacion pu= new ProductoUbicacion();
		pu.setUbicacion(ubicacion);
		pu.setProducto(producto);
		producto.getUbicaciones().add(pu);			
		
		Proveedor proveedor = new Proveedor();
		proveedor = proveedorRepository.findOne(productoDto.getProveedorId());
		producto.setProveedor(proveedor);
		
		MarcaFamilia marcaFamilia = new MarcaFamilia();
		marcaFamilia = marcaFamiliaRepository.findByIdMarcaAndIdFamilia(productoDto.getMarcaId(), productoDto.getFamiliaId());
		producto.setMarcaFamilia(marcaFamilia);
		
		if(productoDto.getProductoDetalleA()!=null){
			if(productoDto.getProductoDetalleA().getCodigoBarra()!=null && productoDto.getProductoDetalleA().getUnidadMedidaId()!=0){
				ProductoDetalle detalleA = new ProductoDetalle();
				detalleA.setProducto(producto);
				UnidadMedida uA = new UnidadMedida();
				uA = unidadMedidaRepository.findOne(productoDto.getProductoDetalleA().getUnidadMedidaId());
				detalleA.setUnidadMedida(uA);
				detalleA.setCodigoBarra(productoDto.getProductoDetalleA().getCodigoBarra());		
				detalleA.setPrecio(productoDto.getProductoDetalleA().getPrecio());
				producto.setProductoDetalleA(detalleA);	
			}
		}
		
		if(productoDto.getProductoDetalleB()!=null){
			if(productoDto.getProductoDetalleB().getCodigoBarra()!=null && productoDto.getProductoDetalleB().getUnidadMedidaId()!=0){
				ProductoDetalle detalleB = new ProductoDetalle();
				detalleB.setProducto(producto);
				UnidadMedida uB = new UnidadMedida();
				uB = unidadMedidaRepository.findOne(productoDto.getProductoDetalleB().getUnidadMedidaId());
				detalleB.setUnidadMedida(uB);
				detalleB.setCodigoBarra(productoDto.getProductoDetalleB().getCodigoBarra());		
				detalleB.setPrecio(productoDto.getProductoDetalleB().getPrecio());
				producto.setProductoDetalleB(detalleB);	
			}
		}
		if(productoDto.getProductoDetalleC()!=null){
			if(productoDto.getProductoDetalleC().getCodigoBarra()!=null && productoDto.getProductoDetalleC().getUnidadMedidaId()!=0){
				ProductoDetalle detalleC = new ProductoDetalle();
				detalleC.setProducto(producto);
				UnidadMedida uC = new UnidadMedida();
				uC = unidadMedidaRepository.findOne(productoDto.getProductoDetalleC().getUnidadMedidaId());
				detalleC.setUnidadMedida(uC);
				detalleC.setCodigoBarra(productoDto.getProductoDetalleC().getCodigoBarra());		
				detalleC.setPrecio(productoDto.getProductoDetalleC().getPrecio());
				producto.setProductoDetalleC(detalleC);	
			}
		}
		
		
		/*ProductoDetalle*/
		/**
		for(ProductoDetalle productoDetalle: productoDto.getDetalles()){
			productoDetalle.setProducto(producto);
			producto.getDetalleProductos().add(productoDetalle);
		}**/		
		return producto;
	}	
	
	private ProductoDto listaProducto(Producto producto) {
		ProductoDto productoDto = new ProductoDto();
		productoDto.setId(producto.getIdSa());
		productoDto.setCodigoEquivalente(producto.getCodigoEquivalente());
		productoDto.setDescripcion(producto.getDescripcion());		
		productoDto.setCodigoEquivalente(producto.getCodigoEquivalente());
		productoDto.setPesoKilogramo(producto.getPesoKilogramo());
		productoDto.setUnidadMedidaPrincipal(producto.getUnidadMedidaPrincipal());
		
		if(producto.getProveedor()!=null) {productoDto.setProveedorId(producto.getProveedor().getId());}		
		
		//Si no tiene marca familia entra al if
		if(producto.getMarcaFamilia() != null){
			productoDto.setMarcaId(producto.getMarcaFamilia().getMarca().getIdSa());
		}
		if(producto.getMarcaFamilia() != null){
			productoDto.setFamiliaId(producto.getMarcaFamilia().getFamilia().getIdSa());
		}
		if(productoDto.getFamiliaId()!=0){
			Categorie f = familiaRepository.findOne(productoDto.getFamiliaId());
			productoDto.setDescripcionFamilia(f.getName());
		}
		
		if(productoDto.getMarcaId()!=0){
			Marca m = marcaRepository.findOne(productoDto.getMarcaId());
			productoDto.setDescripcionMarca(m.getDescripcion());
		}
		
		productoDto.setTratamientoEspecial(producto.isTratamientoEspecial());
		
		productoDto.setProductoDetalleA(obtenerProductoDetalle(producto.getProductoDetalleA()));
		productoDto.setProductoDetalleB(obtenerProductoDetalle(producto.getProductoDetalleB()));
		productoDto.setProductoDetalleC(obtenerProductoDetalle(producto.getProductoDetalleC()));
		
		ProductoUbicacionDto pu = new ProductoUbicacionDto();
		for (ProductoUbicacion productoUbicacion : producto.getUbicaciones()) {
			if (productoUbicacion != null && productoUbicacion.getUbicacion() != null){
				long id=productoUbicacion.getUbicacion().getZona().getAlmacen().getId();
				//if(productoDto.getAlmacenId()==id){
					pu.setUbicacionId(productoUbicacion.getUbicacion().getId());
					pu.setZonaId(productoUbicacion.getUbicacion().getZona().getId());
				//}	
			}else{
				pu.setUbicacionId(0);
				pu.setZonaId(0);	
			}	
		}	
		productoDto.setUbicacionId(pu.getUbicacionId());
		productoDto.setZonaId(pu.getZonaId());		
		return productoDto;
	}
	
	private ProductoDto convertToDto(Producto producto) {
		ProductoDto productoDto = new ProductoDto();
		productoDto.setId(producto.getIdSa());
		productoDto.setCodigoEquivalente(producto.getCodigoEquivalente());
		productoDto.setDescripcion(producto.getDescripcion());		
		productoDto.setCodigoEquivalente(producto.getCodigoEquivalente());
		productoDto.setPesoKilogramo(producto.getPesoKilogramo());
		productoDto.setUnidadMedidaPrincipal(producto.getUnidadMedidaPrincipal());
		
		if(producto.getProveedor()!=null) {productoDto.setProveedorId(producto.getProveedor().getId());}		
		
		String uPpal = producto.getUnidadMedidaPrincipal();
		ProductoDetalle pd = null;
		if(uPpal!=null && uPpal.equalsIgnoreCase("A")){	
			pd = producto.getProductoDetalleA();
			productoDto.setUnidadMedidaId(pd.getUnidadMedida().getId());
			productoDto.setUnidadMedidaPrincipal(pd.getUnidadMedida().getDescripcion());			
		}
		if(uPpal!=null && uPpal.equalsIgnoreCase("B")){	
			pd = producto.getProductoDetalleB();
			productoDto.setUnidadMedidaId(pd.getUnidadMedida().getId());
			productoDto.setUnidadMedidaPrincipal(pd.getUnidadMedida().getDescripcion());
		}
		if(uPpal!=null && uPpal.equalsIgnoreCase("C")){	
			pd = producto.getProductoDetalleC();
			if(pd.getUnidadMedida()!=null){productoDto.setUnidadMedidaId(pd.getUnidadMedida().getId());}
			if(pd.getUnidadMedida()!=null){productoDto.setUnidadMedidaPrincipal(pd.getUnidadMedida().getDescripcion());}
		}			
		if(pd!=null && pd.getUnidadMedida()!=null){
			productoDto.setUnidadMedidaPrincipalId(pd.getUnidadMedida().getId());
		}
		
		//Si no tiene marca familia entra al if
		if(producto.getMarcaFamilia() != null){
			productoDto.setMarcaId(producto.getMarcaFamilia().getMarca().getIdSa());
		}
		if(producto.getMarcaFamilia() != null){
			productoDto.setFamiliaId(producto.getMarcaFamilia().getFamilia().getIdSa());
		}
		if(productoDto.getFamiliaId()!=0){
			Categorie f = familiaRepository.findOne(productoDto.getFamiliaId());
			productoDto.setDescripcionFamilia(f.getName());
		}
		
		if(productoDto.getMarcaId()!=0){
			Marca m = marcaRepository.findOne(productoDto.getMarcaId());
			productoDto.setDescripcionMarca(m.getDescripcion());
		}
		
		productoDto.setTratamientoEspecial(producto.isTratamientoEspecial());
		
		productoDto.setProductoDetalleA(obtenerProductoDetalle(producto.getProductoDetalleA()));
		productoDto.setProductoDetalleB(obtenerProductoDetalle(producto.getProductoDetalleB()));
		productoDto.setProductoDetalleC(obtenerProductoDetalle(producto.getProductoDetalleC()));
		
		ProductoUbicacionDto pu = new ProductoUbicacionDto();
		for (ProductoUbicacion productoUbicacion : producto.getUbicaciones()) {
			if (productoUbicacion != null && productoUbicacion.getUbicacion() != null){
				long id=productoUbicacion.getUbicacion().getZona().getAlmacen().getId();
				//if(productoDto.getAlmacenId()==id){
					pu.setUbicacionId(productoUbicacion.getUbicacion().getId());
					pu.setZonaId(productoUbicacion.getUbicacion().getZona().getId());
					pu.setDescripcionUbicacion(productoUbicacion.getUbicacion().getDescripcion());
					pu.setDescripcionZona(productoUbicacion.getUbicacion().getZona().getDescripcion());
				//}	
			}else{
				pu.setUbicacionId(0);
				pu.setZonaId(0);	
			}	
		}	
		productoDto.setUbicacionId(pu.getUbicacionId());
		productoDto.setZonaId(pu.getZonaId());		
		return productoDto;
	}		
	
	@Transactional
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/producto/bycode")
	public @ResponseBody ProductoDto recuperarProducto(@RequestBody String codigo){
		
		Producto p = (Producto)productoRepository.findByCode(codigo);
		ProductoDto pro=null;
		if(p!=null){
			pro=	convertToDto(p);
		}		
		
		return pro;
	}
	private ProductoDetalleDto obtenerProductoDetalle(ProductoDetalle detalle){
		ProductoDetalleDto dto = new ProductoDetalleDto();
		if(detalle != null) {
					if(detalle.getUnidadMedida() != null){					
							dto.setUnidadMedidaId(detalle.getUnidadMedida().getId());
							dto.setDescripcionUnidadMedida(detalle.getUnidadMedida().getDescripcion());
					}		
					dto.setCodigoBarra(detalle.getCodigoBarra());
					dto.setPrecio(detalle.getPrecio());
		} else {
			dto.setUnidadMedidaId(0);
			dto.setDescripcionUnidadMedida("");
			dto.setCodigoBarra("");
		}
		return dto;	
	}
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/reporteproductos/filter", produces="application/octet")
	@Transactional
	public void reporteProducto( @RequestBody FilterComboProductoDto filter, HttpServletResponse resp) throws IOException, Exception{
		List<Producto> producto = new ArrayList<Producto>();
		if(filter.getFamiliaId()!=0 && filter.getMarcaId()!=0){	
			producto=(List<Producto>)productoRepository.findByCodFamiliaMarcas(filter.getFamiliaId(),filter.getMarcaId());
		}else if(filter.getFamiliaId()!=0 &&filter.getMarcaId()==0){
			producto=(List<Producto>)productoRepository.findByCodFamilia(filter.getFamiliaId());
		}else{	
			producto=(List<Producto>)productoRepository.findAll();
		}		 		
		FileInputStream inputStream = new FileInputStream(new File("C:\\softbrill\\sisadmin\\reporteExcel\\productos.xlsx"));								
		OPCPackage pkg = OPCPackage.open(inputStream);
		XSSFWorkbook wb = new XSSFWorkbook(pkg);								
		Sheet sheet = wb.getSheetAt(0);
		int rowIndex = 2;	
		for(Producto p : producto){
			Row row = sheet.createRow(rowIndex++);	
			Cell cell0 = row.createCell(0);	cell0.setCellValue(validarString(p.getCodigoEquivalente()));
			Cell cell1 = row.createCell(1);	cell1.setCellValue(validarString(p.getDescripcion()));
			Cell cell2 = row.createCell(2);cell2.setCellValue("");	
			if(p.getMarcaFamilia()!=null){cell2.setCellValue(p.getMarcaFamilia().getFamilia().getName());}			
			Cell cell3 = row.createCell(3);cell3.setCellValue("");
			if(p.getMarcaFamilia()!=null){cell3.setCellValue(p.getMarcaFamilia().getMarca().getDescripcion());}			
		}
		resp.setContentType("application/octet"); resp.setHeader("Content-Disposition","attachment; filename=\"" + "excelprueba.xlsx" +"\"");
		OutputStream outputStream = null;
		try {	outputStream = resp.getOutputStream();
				wb.write(outputStream);
			} catch (Exception e) {	System.out.println("Error al obtener archivo "); e.printStackTrace();
			} finally {	inputStream.close(); outputStream.close();	}		
	}
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/reporteproductoprecios/filter", produces="application/octet")
	@Transactional
	public void reporteProductoPrecios( @RequestBody FilterComboProductoDto filter, HttpServletResponse resp) throws IOException, Exception{
		List<Producto> producto = new ArrayList<Producto>();
		List<ReporteProductoPrecioDto> listAll = new ArrayList<ReporteProductoPrecioDto>();
		if(filter.getFamiliaId()!=0 && filter.getMarcaId()!=0){	
			producto=(List<Producto>)productoRepository.findByCodFamiliaMarcas(filter.getFamiliaId(),filter.getMarcaId());
			for(Producto p : producto){listAll.add(recuperarProductoPrecio(p));}
		}else if(filter.getFamiliaId()!=0 &&filter.getMarcaId()==0){
			producto=(List<Producto>)productoRepository.findByCodFamilia(filter.getFamiliaId());
			for(Producto p : producto){listAll.add(recuperarProductoPrecio(p));}
		}else{	
			producto=(List<Producto>)productoRepository.findAll();
			for(Producto p : producto){listAll.add(recuperarProductoPrecio(p));}
		}		 		
		FileInputStream inputStream = new FileInputStream(new File("C:\\softbrill\\sisadmin\\reporteExcel\\productosprecios.xlsx"));								
		OPCPackage pkg = OPCPackage.open(inputStream);
		XSSFWorkbook wb = new XSSFWorkbook(pkg);								
		Sheet sheet = wb.getSheetAt(0);
		int rowIndex = 2;	
		for(ReporteProductoPrecioDto p : listAll){
			Row row = sheet.createRow(rowIndex++);	
			Cell cell0 = row.createCell(0);	cell0.setCellValue(validarString(p.getCodigo()));
			Cell cell1 = row.createCell(1);	cell1.setCellValue(validarString(p.getDescripcion()));
			Cell cell2 = row.createCell(2);cell2.setCellValue(validarString(p.getFamilia()));		
			Cell cell3 = row.createCell(3);cell3.setCellValue(validarString(p.getMarca()));
			Cell cell4 = row.createCell(4);cell4.setCellValue(validarString(p.getUnidadMedidaA()));
			Cell cell5 = row.createCell(5);cell5.setCellValue(montoString(p.getPrecioA()));
			Cell cell6 = row.createCell(6);cell6.setCellValue(validarString(p.getUnidadMedidaB()));
			Cell cell7 = row.createCell(7);cell7.setCellValue(montoString(p.getPrecioB()));
			Cell cell8 = row.createCell(8);cell8.setCellValue(validarString(p.getUnidadMedidaC()));
			Cell cell9 = row.createCell(9);cell9.setCellValue(montoString(p.getPrecioC()));
		}
		resp.setContentType("application/octet"); resp.setHeader("Content-Disposition","attachment; filename=\"" + "excelprueba.xlsx" +"\"");
		OutputStream outputStream = null;
		try {	outputStream = resp.getOutputStream();
				wb.write(outputStream);
			} catch (Exception e) {	System.out.println("Error al obtener archivo "); e.printStackTrace();
			} finally {	inputStream.close(); outputStream.close();	}		
	}
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/producto/importar/filter")
	public @ResponseBody String importar( ProductoImportDto importDto){
		try{
			CSVReader reader = new CSVReader(new FileReader("C:/softbrill/sisadmin/imports/productos.csv"),';');	
		    String [] nextLine;
		    int i = 0;
		    while ((nextLine = reader.readNext()) != null) {
		      try{
		    	if(i==0 || !(i%2==0)){} else {		    				
		    		String codProducto = nextLine[0].trim();
		    		String codEquivalente = nextLine[1].trim();
		    		String descripcion = nextLine[2].trim();
		    		String codMarca = nextLine[3].trim();		    				
		    		String codFamilia = nextLine[4].trim();		    				
		    		String codUniMedA = nextLine[5].trim();
		    		String codUniMedB = nextLine[6].trim();
		    		String codUniMedC = nextLine[7].trim();
		    		String unidadPrincipal = nextLine[8].trim();		    				
		    		String precioCompra = nextLine[9].trim();
		    		String precioDetalleA = nextLine[10].trim();
		    		String precioDetalleB = nextLine[11].trim();
		    		String precioDetalleC = nextLine[12].trim();
		    		String codigoBarraA = nextLine[13].trim();
		    		String codigoBarraB = nextLine[14].trim();
		    		String codigoBarraC = nextLine[15].trim();
		    		Producto producto = new Producto();
		    		producto.setId(UUID.randomUUID().toString());
		    		producto.setCodigo(new Integer(codProducto).intValue());
		    		producto.setCodigoEquivalente(codEquivalente);
		    		producto.setDescripcion(descripcion);
		    		producto.setCodigoBarra(codProducto);		    				
		    		producto.setUnidadMedidaPrincipal(unidadPrincipal);		    				
		    		
		    		String regex = "\\d+\\.\\d+\\.\\d+";
		    		if(precioCompra.matches(regex)){
		    					precioCompra = precioCompra.replaceFirst("\\.", "");
		    		}		    		
		    		producto.setPrecioCompra(new BigDecimal(precioCompra));		    		
		    		if(codProducto.contains("31017")){System.out.println("stop");}    		
		    		
		    		Date fecha = new Date(); 
		    		long hora = fecha.getTime();
		    		Timestamp fechahora= new Timestamp(hora);		    				
		    		Marca marca = (Marca)marcaRepository.findByCodigo(codMarca);		    								
		    		Categorie familia = (Categorie)familiaRepository.findByCodigo(codFamilia);
		    		MarcaFamilia marcaFamilia = marcaFamiliaRepository.findByIdMarcaAndIdFamilia(marca.getIdSa(), familia.getIdSa());
		    		producto.setMarcaFamilia(marcaFamilia);		    				
		    		producto.setUnidadMedidaPrincipal(unidadPrincipal);
		    		if(!codUniMedA.equals("0")){
		    			ProductoDetalle detalleA = new ProductoDetalle();
		    			detalleA.setProducto(producto);
			    		UnidadMedida uA = new UnidadMedida();
			    		uA = unidadMedidaRepository.findByCodigo(new Integer(codUniMedA));
			    		detalleA.setUnidadMedida(uA);
			    		detalleA.setCodigoBarra(codigoBarraA);		
			    		detalleA.setPrecio(new BigDecimal(precioDetalleA));
			    		producto.setProductoDetalleA(detalleA);
		    		}
		    		if(!codUniMedB.equals("0")){
			    		ProductoDetalle detalleB = new ProductoDetalle();
			    		detalleB.setProducto(producto);
			    		UnidadMedida uB = new UnidadMedida();
			    		uB = unidadMedidaRepository.findByCodigo(new Integer(codUniMedB));
			    		detalleB.setUnidadMedida(uB);
			    		detalleB.setCodigoBarra(codigoBarraB);		
			    		detalleB.setPrecio(new BigDecimal(precioDetalleB));
			    		producto.setProductoDetalleB(detalleB);
		    		}	
		    		if(!codUniMedC.equals("0")){
			    		ProductoDetalle detalleC = new ProductoDetalle();
			    		detalleC.setProducto(producto);
			    		UnidadMedida uC = new UnidadMedida();
			    		uC = unidadMedidaRepository.findByCodigo(new Integer(codUniMedC));
			    		detalleC.setUnidadMedida(uC);
			    		detalleC.setCodigoBarra(codigoBarraC);		
			    		detalleC.setPrecio(new BigDecimal(precioDetalleC));
			    		producto.setProductoDetalleC(detalleC);
		    		}	
		    		producto.setMarca(marca.getId());
		    		producto.setCategory(familia.getId());
		    		producto.setTaxcat("001");
		    		producto.setPricesell(new BigDecimal(precioCompra));// TODO ver que hacer pues deberia ser el precio de la unidad principal
		    		producto.setStockcost(new BigDecimal(0));
		    		producto.setStockvolume(new BigDecimal(0));
		    		producto.setIscom(false);
		    		producto.setIsscale(false);
		    		producto.setIskitchen(true);
		    		producto.setPrintkb(false);
		    		producto.setSendstatus(false);
		    		producto.setIsservice(false);
		    		producto.setDisplay("<HTML><center>"+descripcion);
		    		producto.setIsvprice(false);
		    		producto.setIsverpatrib(false);
		    		producto.setTexttip(descripcion);
		    		producto.setWarranty(false);
		    		producto.setStockunits(new BigDecimal(0));
		    		productoRepository.save(producto);
		    	}
		    	i++;
		      } catch(Exception e){e.printStackTrace();}		    	
		    }
		    reader.close();
		}catch(Exception e){e.printStackTrace();}		
		return "ok";
	}
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/reporteproductostock/filter", produces="application/octet")
	@Transactional
	public void reporteProductoStock( @RequestBody FilterReporteStock filter, HttpServletResponse resp) throws IOException, Exception{
		List<Producto> producto = new ArrayList<Producto>();
		List<ReporteProductoAlmacenDto> listAll = new ArrayList<ReporteProductoAlmacenDto>();
		if(filter.getFamiliaId()!=0 && filter.getMarcaId()!=0){
			producto=(List<Producto>)productoRepository.findByCodFamiliaMarcas(filter.getFamiliaId(),filter.getMarcaId());
				for(Producto p : producto){
					ProductoAlmacen pa = productoAlmacenRepository.findByCodProduct(p.getIdSa(),filter.getAlmacenId());	
					if(pa!=null){listAll.add(recuperarProductoAlmacen(p,pa));}
				}
		}else if(filter.getFamiliaId()!=0 &&filter.getMarcaId()==0){
			producto=(List<Producto>)productoRepository.findByCodFamilia(filter.getFamiliaId());
				for(Producto p : producto){
					ProductoAlmacen pa = productoAlmacenRepository.findByCodProduct(p.getIdSa(),filter.getAlmacenId());
					if(pa!=null){listAll.add(recuperarProductoAlmacen(p,pa));}
				}
		}else{
			producto=(List<Producto>)productoRepository.findAll();
				for(Producto p : producto){
					ProductoAlmacen pa = productoAlmacenRepository.findByCodProduct(p.getIdSa(),filter.getAlmacenId());
					if(pa!=null){listAll.add(recuperarProductoAlmacen(p,pa));}
				}
		}		 		
		FileInputStream inputStream = new FileInputStream(new File("C:\\softbrill\\sisadmin\\reporteExcel\\productostock.xlsx"));								
		OPCPackage pkg = OPCPackage.open(inputStream);
		XSSFWorkbook wb = new XSSFWorkbook(pkg);								
		Sheet sheet = wb.getSheetAt(0);
		int rowIndex = 2;	
		for(ReporteProductoAlmacenDto p : listAll){
			Row row = sheet.createRow(rowIndex++);	
			Cell cell0 = row.createCell(0);	cell0.setCellValue(validarString(p.getCodigo()));
			Cell cell1 = row.createCell(1);	cell1.setCellValue(validarString(p.getDescripcion()));
			Cell cell2 = row.createCell(2);cell2.setCellValue(validarString(p.getFamilia()));						
			Cell cell3 = row.createCell(3);cell3.setCellValue(validarString(p.getMarca()));
			Cell cell4 = row.createCell(4);cell4.setCellValue(validarString(p.getUnidadMedida()));
			Cell cell5 = row.createCell(5);cell5.setCellValue(validarString(p.getCodigoBarra()));
			Cell cell6 = row.createCell(6);cell6.setCellValue(montoString(p.getStock()));
		}
		resp.setContentType("application/octet"); resp.setHeader("Content-Disposition","attachment; filename=\"" + "excelprueba.xlsx" +"\"");
		OutputStream outputStream = null;
		try {	outputStream = resp.getOutputStream();
				wb.write(outputStream);
			} catch (Exception e) {	System.out.println("Error al obtener archivo "); e.printStackTrace();
			} finally {	inputStream.close(); outputStream.close();	}		
	}
	private ReporteProductoAlmacenDto recuperarProductoAlmacen(Producto p,ProductoAlmacen pa){
		ReporteProductoAlmacenDto dto = new ReporteProductoAlmacenDto();		
			ProductoDetalle pd=obtenerProductoDetalle(p);			
			dto.setCodigo(p.getCodigoEquivalente());
			dto.setDescripcion(p.getDescripcion());
			dto.setFamilia("");
			if(p.getMarcaFamilia()!=null){dto.setFamilia(p.getMarcaFamilia().getFamilia().getName());}
			dto.setMarca("");
			if(p.getMarcaFamilia()!=null){dto.setMarca(p.getMarcaFamilia().getMarca().getDescripcion());}
			dto.setUnidadMedida("");
			if(pd.getUnidadMedida()!=null){dto.setUnidadMedida(pd.getUnidadMedida().getDescripcion());}
			dto.setCodigoBarra(p.getCodigoBarra());
			dto.setStock(pa.getCantidad());			
		return dto;		
	}
	private ReporteProductoPrecioDto recuperarProductoPrecio(Producto p){
		ReporteProductoPrecioDto dto = new ReporteProductoPrecioDto();
			dto.setCodigo(p.getCodigoEquivalente());
			dto.setDescripcion(p.getDescripcion());
			dto.setFamilia("");
			if(p.getMarcaFamilia()!=null){dto.setFamilia(p.getMarcaFamilia().getFamilia().getName());}
			dto.setMarca("");
			if(p.getMarcaFamilia()!=null){dto.setMarca(p.getMarcaFamilia().getMarca().getDescripcion());}
			dto.setPrecioA(new BigDecimal(0.00));dto.setUnidadMedidaA("");
			if(p.getProductoDetalleA()!=null){dto.setPrecioA(p.getProductoDetalleA().getPrecio());
				if(p.getProductoDetalleA().getUnidadMedida()!=null){dto.setUnidadMedidaA(p.getProductoDetalleA().getUnidadMedida().getDescripcion());}			
			}
			dto.setPrecioB(new BigDecimal(0.00));dto.setUnidadMedidaB("");
			if(p.getProductoDetalleB()!=null){dto.setPrecioB(p.getProductoDetalleB().getPrecio());
				if(p.getProductoDetalleB().getUnidadMedida()!=null){dto.setUnidadMedidaB(p.getProductoDetalleB().getUnidadMedida().getDescripcion());}			
			}
			dto.setPrecioC(new BigDecimal(0.00));dto.setUnidadMedidaC("");
			if(p.getProductoDetalleC()!=null){dto.setPrecioC(p.getProductoDetalleC().getPrecio());
				if(p.getProductoDetalleC().getUnidadMedida()!=null){dto.setUnidadMedidaC(p.getProductoDetalleC().getUnidadMedida().getDescripcion());}				
			}			
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
	private String validarString(String valor){		
		String val = "";		
		if(valor!=null){val=valor;}
		return  val;
	}
	private String montoString(BigDecimal monto){
		String retorno = "";
		if(monto != null) return monto.toString();
		return retorno;
	}
	
	private ProductoRepository productoRepository;

	@Autowired
	public void setProductoRepository(ProductoRepository productoRepository) {
		this.productoRepository = productoRepository;
	}	
	private MarcaRepository marcaRepository;
	@Autowired
	public void setMarcaRepository(MarcaRepository marcaRepository) {
		this.marcaRepository = marcaRepository;
	}	
	private CategorieRepository familiaRepository;
	@Autowired
	public void setFamiliaRepository(CategorieRepository familiaRepository) {
		this.familiaRepository = familiaRepository;
	}
	private ProveedorRepository proveedorRepository;
	@Autowired
	public void setProveedorRepository(ProveedorRepository proveedorRepository) {
		this.proveedorRepository = proveedorRepository;
	}
	private AlmacenRepository almacenRepository;
	@Autowired
	public void setAlmacenRepository(AlmacenRepository almacenRepository) {
		this.almacenRepository = almacenRepository;
	}
	private ZonaRepository zonaRepository;
	@Autowired
	public void setZonaRepository(ZonaRepository zonaRepository) {
		this.zonaRepository = zonaRepository;
	}
	
	/*UbicacionRepository*/
	private UbicacionRepository ubicacionRepository;
	@Autowired
	public void setUbicacionRepository(UbicacionRepository ubicacionRepository) {
		this.ubicacionRepository = ubicacionRepository;
	}
	
	/*MarcaFamiliaRepository*/
	private MarcaFamiliaRepository marcaFamiliaRepository;
	@Autowired
	public void setMarcaFamiliaRepository(MarcaFamiliaRepository marcaFamiliaRepository) {
		this.marcaFamiliaRepository = marcaFamiliaRepository;
	}
	
	/*UnidadMedida*/
	private UnidadMedidaRepository unidadMedidaRepository;
	@Autowired
	public void setUnidadMedidaRepository(UnidadMedidaRepository unidadMedidaRepository) {
		this.unidadMedidaRepository = unidadMedidaRepository;
	}
	
	@Autowired
	private ProductoDetalleRepository productoDetalleRepository;
	@Autowired
	private ProductoAlmacenRepository productoAlmacenRepository;
	@Autowired
	private ProductoUbicacionRepository productoUbicacionRepository;
	
}
	
