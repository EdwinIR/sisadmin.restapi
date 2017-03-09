package com.sisadmin.compras.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.sisadmin.compras.dto.OrdenCompraDetalleDto;
import com.sisadmin.compras.dto.OrdenCompraDto;
import com.sisadmin.compras.dto.OrdenCompraFilter;
import com.sisadmin.entity.Almacen;
import com.sisadmin.entity.Comprobante;
import com.sisadmin.entity.CondicionPago;
import com.sisadmin.entity.Moneda;
import com.sisadmin.entity.OrdenCompra;
import com.sisadmin.entity.OrdenCompraDetalle;
import com.sisadmin.entity.Producto;
import com.sisadmin.entity.Proveedor;
import com.sisadmin.entity.UnidadMedida;
import com.sisadmin.repository.AlmacenRepository;
import com.sisadmin.repository.ComprobanteRepository;
import com.sisadmin.repository.CondicionPagoRepository;
import com.sisadmin.repository.MonedaRepository;
import com.sisadmin.repository.OrdenCompraDetalleRepository;
import com.sisadmin.repository.OrdenCompraRepository;
import com.sisadmin.repository.ProductoRepository;
import com.sisadmin.repository.ProveedorRepository;
import com.sisadmin.repository.UnidadMedidaRepository;
import com.sisadmin.service.repository.EmisorActual;
/**
 * ABMDetalleOrdenCompra
 * @author ciro
 *
 */
@Controller
public class OrdenCompraController {

	
	
	@Transactional
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/ordencompra/list")
	public @ResponseBody List<OrdenCompraDto> listar(){
		List<OrdenCompra> ordencompras = (List<OrdenCompra>) ordenCompraRepository.findAll(); List<OrdenCompraDto> ordencomprasDtos = new ArrayList<OrdenCompraDto>();
		for (OrdenCompra ordencompra : ordencompras) {	ordencomprasDtos.add(leer(ordencompra)); }
		return ordencomprasDtos;
	}
	@Transactional
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/ordencomprapdte/list")
	public @ResponseBody List<OrdenCompraDto> listarTerminadas(){
		List<OrdenCompra> ordencompras = (List<OrdenCompra>) ordenCompraRepository.ordenesPendientes("Pendiente"); 
		List<OrdenCompraDto> ordencomprasDtos = new ArrayList<OrdenCompraDto>();
		for (OrdenCompra ordencompra : ordencompras) {	ordencomprasDtos.add(leer(ordencompra)); }
		return ordencomprasDtos;
	}

	@RequestMapping(method=RequestMethod.POST, value="/api/v1/ordencompra/add")
	public @ResponseBody String agregar(@RequestBody OrdenCompraDto ordenCompraDto){
		ordenCompraRepository.save(nueva(ordenCompraDto)); return "OK";
	}
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/ordencompra")	   
	public @ResponseBody String borrar(@RequestParam(value="id") Long id){
		ordenCompraRepository.delete(id); return "Ok";				
	}	
	@Transactional
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/ordencompra/update")
	public @ResponseBody String actualizar(@RequestBody OrdenCompraDto ordenCompraDto){
		OrdenCompra ordenCompra = (OrdenCompra)ordenCompraRepository.findOne(ordenCompraDto.getId());
		for(OrdenCompraDetalle od : ordenCompra.getDetalleOrdenCompras()){ordenCompraDetalleRepository.delete(od.getId());}
		ordenCompra.getDetalleOrdenCompras().clear();		
		actualizar(ordenCompraDto, ordenCompra);
		ordenCompraRepository.save(ordenCompra);
		return "OK";
	}	
	private OrdenCompra nueva(OrdenCompraDto ordenCompraDto){
		OrdenCompra ordenCompra = new OrdenCompra();
		ordenCompra.setFechaEmision(ordenCompraDto.getFechaEmision());
		ordenCompra.setFechaRecepcion(ordenCompraDto.getFechaRecepcion());
		ordenCompra.setEstado(ordenCompraDto.getEstado());
		Proveedor proveedor = proveedorRepository.findOne(ordenCompraDto.getIdProveedor()); ordenCompra.setProveedor(proveedor);
		Almacen almacen = almacenRepository.findOne(ordenCompraDto.getIdAlmacen()); ordenCompra.setAlmacen(almacen);
		CondicionPago condicionPago = condicionPagoRepository.findOne(ordenCompraDto.getCondicionPagoCodigo());	ordenCompra.setCondicionPago(condicionPago);
		Moneda moneda = monedaRepository.findOne(ordenCompraDto.getTipoMonedaCodigo());	ordenCompra.setTipoMoneda(moneda);
		for(OrdenCompraDetalleDto detalleDto: ordenCompraDto.getDetalles()){					
			 ordenCompra.getDetalleOrdenCompras().add(nuevoDetalle(detalleDto, ordenCompra)); }
		return ordenCompra;
	}
	private void actualizar(OrdenCompraDto ordenCompraDto, OrdenCompra ordenCompra){
		ordenCompra.setFechaEmision(ordenCompraDto.getFechaEmision());
		ordenCompra.setFechaRecepcion(ordenCompraDto.getFechaRecepcion());
		ordenCompra.setEstado(ordenCompraDto.getEstado());		
		Proveedor proveedor = proveedorRepository.findOne(ordenCompraDto.getIdProveedor());	ordenCompra.setProveedor(proveedor);
		Almacen almacen = almacenRepository.findOne(ordenCompraDto.getIdAlmacen());	ordenCompra.setAlmacen(almacen);
		CondicionPago condicionPago = condicionPagoRepository.findOne(ordenCompraDto.getCondicionPagoCodigo());	ordenCompra.setCondicionPago(condicionPago);
		Moneda moneda = monedaRepository.findOne(ordenCompraDto.getTipoMonedaCodigo());	ordenCompra.setTipoMoneda(moneda);		
		for(OrdenCompraDetalleDto detalleDto: ordenCompraDto.getDetalles()){					
				ordenCompra.getDetalleOrdenCompras().add(nuevoDetalle(detalleDto, ordenCompra)); }
	}	
	private OrdenCompraDetalle nuevoDetalle(OrdenCompraDetalleDto detalleDto, OrdenCompra orden) {
		OrdenCompraDetalle o = new OrdenCompraDetalle();		
		Producto producto = productoRepository.findOne(detalleDto.getProductoId());
		UnidadMedida uni = unidadMedidaRepository.findOne(detalleDto.getUnidadMedidaId());		
		o.setProducto(producto);
		o.setUnidadMedida(uni);
		o.setBonificado(detalleDto.isBonificado());		
		o.setPrecioUnitario(detalleDto.getPrecioUnitario());
		o.setDescuento1(detalleDto.getDescuento1());
		o.setDescuento2(detalleDto.getDescuento2());
		o.setDescuento3(detalleDto.getDescuento3());
		o.setDescuento4(detalleDto.getDescuento4());		
		o.setPrecioNeto(detalleDto.getPrecioNeto());		
		o.setCantidad(detalleDto.getCantidad());
		o.setTotalCompra(detalleDto.getTotalCompra());		
		o.setOrdenCompra(orden);
		return o;
	}
	private OrdenCompraDto leer(OrdenCompra in) {
		OrdenCompraDto dto = new OrdenCompraDto();
		dto.setId(in.getId());
		dto.setFechaEmision(in.getFechaEmision());
		dto.setIdProveedor(in.getProveedor().getId());
		dto.setFechaRecepcion(in.getFechaRecepcion());
		dto.setRazonSocialProveedor(in.getProveedor().getRazonSocial());
		dto.setDescripcionCondicionPago(in.getCondicionPago().getDescripcion());
		dto.setDescripcionAlmacen(in.getAlmacen().getDescripcion());
		dto.setDescripcionTipoPago(in.getTipoMoneda().getDescripcion());
		dto.setEstado(in.getEstado());
		for(OrdenCompraDetalle o : in.getDetalleOrdenCompras()){
				OrdenCompraDetalleDto ocd = new OrdenCompraDetalleDto();	
				ocd.setDescripcionProducto(o.getProducto().getDescripcion());
				ocd.setDescripcionUnidadMedida(o.getUnidadMedida().getDescripcion());				
				ocd.setProductoId(o.getProducto().getIdSa());
				ocd.setUnidadMedidaId(o.getUnidadMedida().getId());
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
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/ordencomprafiltro/filter")
    public @ResponseBody List<OrdenCompraDto> getListado(@RequestBody OrdenCompraFilter ordenCompraFilter, HttpServletRequest request, HttpServletResponse resp) throws ParseException, IOException{
                List<OrdenCompraDto> ordenCompraDto = new ArrayList<OrdenCompraDto>();
                try{                    
                    List<OrdenCompra> ordenCompra = (List<OrdenCompra>)ordenCompraRepository.ordenesFechas(ordenCompraFilter.getFechaInicio(), ordenCompraFilter.getFechaFin());
                    
                    for(OrdenCompra oc : ordenCompra){ 
                    		OrdenCompraDto dto = new OrdenCompraDto();
                    			dto.setId(oc.getId());
                    			dto.setFechaEmision(oc.getFechaEmision());
                    			dto.setIdProveedor(oc.getProveedor().getId());
                    			dto.setFechaRecepcion(oc.getFechaRecepcion());
                    			dto.setRazonSocialProveedor(oc.getProveedor().getRazonSocial());
                    			dto.setDescripcionCondicionPago(oc.getCondicionPago().getDescripcion());
                    			dto.setDescripcionAlmacen(oc.getAlmacen().getDescripcion());
                    			dto.setDescripcionTipoPago(oc.getTipoMoneda().getDescripcion());
                    			dto.setEstado(oc.getEstado());
                                ordenCompraDto.add(dto);                              
                    }
                } catch(Exception e){}	               
                return ordenCompraDto;                    
        }
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/reporteordencompras/filter", produces="application/octet")
	@Transactional
	public void reporteOrdenCompra( @RequestBody OrdenCompraFilter ordenCompraFilter, HttpServletResponse resp) throws IOException, Exception{
		List<OrdenCompra> ordenCompra = new ArrayList<OrdenCompra>();
		if(ordenCompraFilter.getFechaFin()==null && ordenCompraFilter.getFechaInicio()==null){
			ordenCompra=(List<OrdenCompra>)ordenCompraRepository.findAll();
		}else{
			ordenCompra = (List<OrdenCompra>)ordenCompraRepository.ordenesFechas(ordenCompraFilter.getFechaInicio(), ordenCompraFilter.getFechaFin());		
		}
		 		
		FileInputStream inputStream = new FileInputStream(new File("C:\\softbrill\\sisadmin\\reporteExcel\\ordencompra.xlsx"));								
		OPCPackage pkg = OPCPackage.open(inputStream);
		XSSFWorkbook wb = new XSSFWorkbook(pkg);								
		Sheet sheet = wb.getSheetAt(0);
		int rowIndex = 2;	
		for(OrdenCompra oc : ordenCompra){
			Row row = sheet.createRow(rowIndex++);	
			Cell cell0 = row.createCell(0);	cell0.setCellValue(oc.getId());
			Cell cell1 = row.createCell(1);	cell1.setCellValue(oc.getProveedor().getRazonSocial());
			Cell cell2 = row.createCell(2);	cell2.setCellValue(fechaString(oc.getFechaEmision()));
			Cell cell3 = row.createCell(3);	cell3.setCellValue(fechaString(oc.getFechaRecepcion()));
			Cell cell4 = row.createCell(4);	cell4.setCellValue(validarString(oc.getEstado()));			
		}
		resp.setContentType("application/octet"); resp.setHeader("Content-Disposition","attachment; filename=\"" + "excelprueba.xlsx" +"\"");
		OutputStream outputStream = null;
		try {	outputStream = resp.getOutputStream();
				wb.write(outputStream);
				//IOUtils.copy(inputStream, outputStream);
			} catch (Exception e) {	System.out.println("Error al obtener archivo "); e.printStackTrace();
			} finally {	inputStream.close(); outputStream.close();	}
		
	}	
	private String validarString(String valor){		
		String val = "";		
		if(valor!=null){val=valor;}
		return  val;
	}
	private String fechaString(Date date){
		String retorno = "";
		if(date != null) return date.toString();
		return retorno;
	}	
	@Autowired
	private OrdenCompraRepository ordenCompraRepository;
	@Autowired
	private ProveedorRepository proveedorRepository;	
	@Autowired
	private AlmacenRepository almacenRepository;
	@Autowired
	private CondicionPagoRepository condicionPagoRepository;
	@Autowired
	private MonedaRepository monedaRepository;
	@Autowired
	private ProductoRepository productoRepository;
	@Autowired
	private UnidadMedidaRepository unidadMedidaRepository;
	@Autowired
	private OrdenCompraDetalleRepository ordenCompraDetalleRepository;
	@Autowired
	private ComprobanteRepository comprobanteRepository;
}




/**
private  OrdenCompraService detalleOrdenCompraService;
@Autowired
public void setDetalleOrdenCompraService(
		OrdenCompraService detalleOrdenCompraService) {
	this.detalleOrdenCompraService = detalleOrdenCompraService;
}



**/
