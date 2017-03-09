package com.sisadmin.compras.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sisadmin.compras.dto.FacturaDetalleDto;
import com.sisadmin.compras.dto.FacturaDto;
import com.sisadmin.entity.Factura;
import com.sisadmin.entity.FacturaDetalle;
import com.sisadmin.entity.Moneda;
import com.sisadmin.entity.Producto;
import com.sisadmin.entity.Proveedor;
import com.sisadmin.entity.TipoDocumento;
import com.sisadmin.repository.DetalleFacturaProductoRepository;
import com.sisadmin.repository.FacturaRepository;
import com.sisadmin.repository.MonedaRepository;
import com.sisadmin.repository.ProductoRepository;
import com.sisadmin.repository.ProveedorRepository;
import com.sisadmin.repository.TipoDocumentoRepository;
import com.sisadmin.service.repository.FacturaDetalleService;
/**
 * Controller Factura
 * @author ciro
 *
 */
@Controller
public class FacturaController {

	public static Date f= new Date();
	public static long h=f.getTime();
	public static Timestamp fh= new Timestamp(h);	
	@Transactional
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/factura/list")
	public @ResponseBody List<FacturaDto> todasFacturas(){
				List<Factura> facturas = (List<Factura>) facturaRepository.findAll();
				List<FacturaDto> facturasDtos = new ArrayList<FacturaDto>();
				for (Factura factura : facturas) {
					facturasDtos.add(leer(factura));
				}
				return facturasDtos;
	}		
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/factura/add")
	public @ResponseBody String agregarFacturaCompra(@RequestBody FacturaDto facturaDto){
		Factura factura= leerFactura(facturaDto);
		facturaRepository.save(factura);
		
		return "OK";
	}	
	@Transactional
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/factura/update")
	public @ResponseBody String ActualizarFacturasDetalles(@RequestBody FacturaDto facturaDto){
		Factura factura = (Factura)facturaRepository.findOne(facturaDto.getId());
		
		for(FacturaDetalle fd : factura.getDetalleFacturaProductos()){detalleFacturaRepository.delete(fd.getId());}
		
		factura.getDetalleFacturaProductos().clear();
		actualizarFactura(facturaDto,factura);
		
		facturaRepository.save(factura);
		return "OK";
	}	
	private Factura leerFactura(FacturaDto facturaDto) {
		Date fecha = new Date(); 
		long hora = fecha.getTime();
		Timestamp fechahora= new Timestamp(hora);
		
		Factura factura = new Factura();
		
			factura.setFechaEmision(facturaDto.getFechaEmision());
			factura.setTotal(facturaDto.getTotal());
			factura.setTasaIgv(facturaDto.getTasaIgv());
			factura.setIgv(facturaDto.getIgv());
			factura.setBaseImponible(facturaDto.getBaseImponible());
			factura.setOtrosCargos(facturaDto.getOtrosCargos());
			factura.setCambio(facturaDto.getCambio());
			factura.setNumeracion(facturaDto.getNumeracion());
			factura.setSerie(facturaDto.getSerie());
			factura.setFechaCreacion(fechahora);
			factura.setUsuarioCreacion("usuarioCreacion");
			
			Moneda moneda = monedaRepository.findOne(facturaDto.getTipoMonedaCodigo());
			factura.setTipoMoneda(moneda);
			
			TipoDocumento tipoDocumento = TipoDocumentoRepository.findOne(facturaDto.getTipoDocumentoCodigo());
			factura.setTipoDocumento(tipoDocumento);
			
			Proveedor proveedor = ProveedorRepository.findOne(facturaDto.getIdProveedor());
			factura.setProveedor(proveedor);
			
				for(FacturaDetalleDto detalleDto:facturaDto.getDetalles() ){
					FacturaDetalle fd= convertToFacturaDetalle(detalleDto,factura);
					factura.getDetalleFacturaProductos().add(fd);
				}
		return factura;
	}
	
	private void actualizarFactura(FacturaDto facturaDto,Factura factura){
		Date fecha = new Date(); 
		long hora = fecha.getTime();
		Timestamp fechahora= new Timestamp(hora);	
		
		factura.setFechaEmision(facturaDto.getFechaEmision());
		factura.setTotal(facturaDto.getTotal());
		factura.setTasaIgv(facturaDto.getTasaIgv());
		factura.setIgv(facturaDto.getIgv());
		factura.setBaseImponible(facturaDto.getBaseImponible());
		factura.setOtrosCargos(facturaDto.getOtrosCargos());
		factura.setCambio(facturaDto.getCambio());
		factura.setNumeracion(facturaDto.getNumeracion());
		factura.setSerie(facturaDto.getSerie());
		factura.setFechaCreacion(fechahora);
		factura.setUsuarioCreacion("usuarioCreacion");
		
		Moneda moneda = monedaRepository.findOne(facturaDto.getTipoMonedaCodigo());
		factura.setTipoMoneda(moneda);
		
		TipoDocumento tipoDocumento = TipoDocumentoRepository.findOne(facturaDto.getTipoDocumentoCodigo());
		factura.setTipoDocumento(tipoDocumento);
		
		Proveedor proveedor = ProveedorRepository.findOne(facturaDto.getIdProveedor());
		factura.setProveedor(proveedor);
		
			for(FacturaDetalleDto detalleDto : facturaDto.getDetalles()){
				FacturaDetalle fd=convertToFacturaDetalle(detalleDto, factura);
				factura.getDetalleFacturaProductos().add(fd);
			}
		
	}
	
	
	
	
	private FacturaDetalle convertToFacturaDetalle(FacturaDetalleDto detalleDto, Factura factura) {
		FacturaDetalle fd= new FacturaDetalle();
		
		Producto producto = productoRepository.findOne(detalleDto.getProductoId());
		
		fd.setProducto(producto);
		fd.setCantidad(detalleDto.getCantidad());
		fd.setPrecio(detalleDto.getPrecio());
		fd.setFactura(factura);
		
		return fd;
	}	
	private FacturaDto leer(Factura in) {
		FacturaDto dto = new FacturaDto();			
			dto.setId(in.getId());		
			dto.setNumeroInterno(in.getNumeroInterno());
			dto.setFechaRegistro(in.getFechaRegistro());
			dto.setFechaEmision(in.getFechaEmision());
			dto.setFechaVencimiento(in.getFechaVencimiento());
			dto.setSerie(in.getSerie());
			dto.setNumeracion(in.getNumeracion());
			dto.setDescripcionMoneda("");
			if(in.getTipoMoneda()!=null){dto.setDescripcionMoneda(in.getTipoMoneda().getDescripcion());}			
			dto.setRazonSocialProveedor("");
			if(in.getProveedor()!=null){dto.setRazonSocialProveedor(in.getProveedor().getRazonSocial());}
			dto.setGlosa(in.getGlosa());
			dto.setOtrosCargos(in.getOtrosCargos());
			dto.setBaseImponible(in.getBaseImponible());
			dto.setTasaIgv(in.getTasaIgv());
			dto.setIgv(in.getIgv());
			dto.setTotal(in.getTotal());
			dto.setDescripcionTipoDocumento("");
			if(in.getTipoDocumento()!=null)
				dto.setDescripcionTipoDocumento(in.getTipoDocumento().getDescripcion());		
				for(FacturaDetalle f: in.getDetalleFacturaProductos()){
						FacturaDetalleDto fd = new FacturaDetalleDto();						
							fd.setProductoId(f.getProducto().getIdSa());
							fd.setCantidad(f.getCantidad());
							fd.setPrecio(f.getPrecio());			
							dto.getDetalles().add(fd);
				}				
		return dto;
	}	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/marca/reporteF", produces="application/octet")
	@Transactional
	public void writeFacturaListToFile(HttpServletResponse resp) throws Exception{
		Workbook workbook = new XSSFWorkbook();				
		Sheet sheet = workbook.createSheet("Marcas");		
		Factura fac = new Factura();			
		Cell celda;
		int i = 0;
		String[] titulos = { "id", "numeroInterno","fechaEmision","fechaRegistro","fechaVencimiento","tipoDocumento","serie","numeracion","tipoMoneda","cambio","glosa","otrosCargos","baseImponible",
							  "tasaIgv","igv","total","proveedor","usuarioCreacion","fechaCreacion","usuarioModificacion","fechaModificacion"};	
		
		int rowIndex = 0;			
		Row cabecera = sheet.createRow(rowIndex++);
		for (i = 0; i < titulos.length; i++) {				
				celda = cabecera.createCell(i);
				celda.setCellValue(titulos[i]);
		}				
		for(Factura factura: facturaRepository.findAll()){			
			Row row = sheet.createRow(rowIndex++);
				Cell cell0 = row.createCell(0);		
				cell0.setCellValue(factura.getId());
				Cell cell1 = row.createCell(1);
				cell1.setCellValue(validarString(factura.getNumeroInterno()));
				Cell cell2 = row.createCell(2);			
				cell2.setCellValue(fechaString(factura.getFechaEmision()));
				Cell cell3 = row.createCell(3);			
				cell3.setCellValue(fechaString(factura.getFechaRegistro()));
				Cell cell4 = row.createCell(4);			
				cell4.setCellValue(fechaString(factura.getFechaVencimiento()));
				Cell cell5 = row.createCell(5);
				cell5.setCellValue("");
				if(factura.getTipoDocumento()!=null){cell5.setCellValue(factura.getTipoDocumento().getCodigo());}
				Cell cell6 = row.createCell(6);
				cell6.setCellValue(validarString(factura.getSerie()));
				Cell cell7 = row.createCell(7);
				cell7.setCellValue(validarString(factura.getNumeracion()));
				Cell cell8 = row.createCell(8);
				cell8.setCellValue("");
				if(factura.getTipoMoneda()!=null){cell8.setCellValue(factura.getTipoMoneda().getCodigo());}
				Cell cell9 = row.createCell(9);
				cell9.setCellValue(montoString(factura.getCambio()));
				Cell cell10 = row.createCell(10);
				cell10.setCellValue(validarString(factura.getGlosa()));
				Cell cell11 = row.createCell(11);
				cell11.setCellValue(montoString(factura.getOtrosCargos()));
				Cell cell12 = row.createCell(12);
				cell12.setCellValue(montoString(factura.getBaseImponible()));
				Cell cell13 = row.createCell(13);
				cell13.setCellValue(montoString(factura.getTasaIgv()));
				Cell cell14 = row.createCell(14);
				cell14.setCellValue(montoString(factura.getIgv()));
				Cell cell15 = row.createCell(15);
				cell15.setCellValue(montoString(factura.getTotal()));
				Cell cell16 = row.createCell(16);		
				cell16.setCellValue("");
				if(factura.getProveedor()!=null){cell16.setCellValue(factura.getProveedor().getId());}					
				Cell cell17 = row.createCell(17);
				cell17.setCellValue(validarString(factura.getUsuarioCreacion()));
				Cell cell18 = row.createCell(18);			
				cell18.setCellValue(fechaString(factura.getFechaCreacion()));
				Cell cell19 = row.createCell(19);
				cell19.setCellValue(validarString(factura.getUsuarioModificacion()));
				Cell cell20 = row.createCell(20);			
				cell20.setCellValue(fechaString(factura.getFechaModificacion()));				
		}               
		
		
		
		System.out.println("excelprueba.xlsx" + " " + "exportado satisfactoriamente");		
		String ruta="C:\\softbrill\\sisadmin\\excel\\factura_"+fechaActual()+".xlsx";
		File archivoBorrar = new File(ruta);
		if (archivoBorrar.exists()){ archivoBorrar.delete(); }
		FileOutputStream fos = new FileOutputStream("C:/softbrill/sisadmin/excel/factura_"+fechaActual()+".xlsx");
		workbook.write(fos);
		resp.setContentType("application/octet");
		resp.setHeader("Content-Disposition","attachment; filename=\"" + "excelprueba.xlsx" +"\"");
		fos.close();
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
				inputStream = new FileInputStream(new File(ruta));
				outputStream = resp.getOutputStream();
				IOUtils.copy(inputStream, outputStream);
			} catch (Exception e) {
				System.out.println("Error al obtener archivo " + ruta);
				e.printStackTrace();
			} finally {
				inputStream.close();
				outputStream.close();
			}
		
		
		
		/**
		
			String absolutFileName = pathPrt + FilenameUtils.removeExtension(fileName) + ".PDF";
			resp.setContentType("application/octet");
		resp.setHeader("Content-Disposition","attachment; filename=\"" + "mario.pdf" +"\"");
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
				inputStream = new FileInputStream(new File(absolutFileName));
				outputStream = resp.getOutputStream();
				IOUtils.copy(inputStream, outputStream);
		} catch (Exception e) {
				System.out.println("Error al obtener archivo " + fileName);
				e.printStackTrace();
		} finally {
				inputStream.close();
				outputStream.close();
		}		   			
		**/		
	}
		
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/factura/lista")
	public @ResponseBody List<Factura> todasFacturasReporte(){
				return (List<Factura>) facturaRepository.findAll();
	}
	private String fechaActual(){
		 Date fechaActual = new Date();
		 SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		 return sdf.format(fechaActual);
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
	
	
	private String fechaString(Date date){
		String retorno = "";
		if(date != null) return date.toString();
		return retorno;
	}	
	
	@Autowired
	private FacturaDetalleService detalleFacturaProductoService;	
	@Autowired
	private ProductoRepository productoRepository;
	@Autowired
	private FacturaRepository facturaRepository;	
	@Autowired
	private TipoDocumentoRepository TipoDocumentoRepository;
	@Autowired
	private ProveedorRepository ProveedorRepository;
	@Autowired
	private MonedaRepository monedaRepository;	
	@Autowired
	private DetalleFacturaProductoRepository detalleFacturaRepository;
		
	
}
