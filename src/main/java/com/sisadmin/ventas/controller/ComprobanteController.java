package com.sisadmin.ventas.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
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

import com.sisadmin.compras.dto.FacturaDto;
import com.sisadmin.compras.dto.OrdenCompraFilter;
import com.sisadmin.entity.AjusteInventario;
import com.sisadmin.entity.Comprobante;
import com.sisadmin.entity.ComprobanteDetalle;
import com.sisadmin.entity.Factura;
import com.sisadmin.repository.ComprobanteDetalleRepository;
import com.sisadmin.repository.ComprobanteRepository;
import com.sisadmin.stock.dto.AjusteInventarioDto;
import com.sisadmin.stock.dto.AjusteInventarioFiltroDto;
import com.sisadmin.ventas.dto.ComprobanteDetalleDto;
import com.sisadmin.ventas.dto.ComprobanteDto;
import com.sisadmin.ventas.dto.ComprobanteFilterDto;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@Controller
public class ComprobanteController {
	
	@Transactional
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/comprobante/list")
	public @ResponseBody List<ComprobanteDto> todosComprobantes(){
		List<Comprobante> comprobantes = (List<Comprobante>) comprobanteRepository.findAll();
		List<ComprobanteDto> comprobantesDto = new ArrayList<ComprobanteDto>();
		for (Comprobante com : comprobantes) {
			comprobantesDto.add(leer(com));
		}
		return comprobantesDto;
	}
	
	@Transactional
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/comprobante/filtro")
	public @ResponseBody List<ComprobanteDto> listadoComprobanteFilter(@RequestBody ComprobanteFilterDto filterComprobante, HttpServletRequest request) throws ParseException{
		
		List<ComprobanteDto> listaComprobantesDto = new ArrayList<ComprobanteDto>();
		Date fechaDesde = filterComprobante.getFechaDesde();
		Date fechaHasta = filterComprobante.getFechaHasta();
		String nroDocumento = filterComprobante.getNroDocumento();
		String vendedorId = filterComprobante.getVendedorId();	
		
		if ( fechaDesde != null && fechaHasta != null && nroDocumento==null && vendedorId ==null){
				List<Comprobante> comprobantes= comprobanteRepository.findByFechas(filterComprobante.getFechaDesde(),filterComprobante.getFechaHasta());
				for(Comprobante comp: comprobantes){					
					if(comp !=null){
						listaComprobantesDto.add(leer(comp));
					}
			}
			
		}else if (nroDocumento == null && fechaDesde != null && fechaHasta != null && vendedorId !=null){		
				List<Comprobante> comprobantes= comprobanteRepository.findByFechasAndVendedor(filterComprobante.getVendedorId(),filterComprobante.getFechaDesde(),filterComprobante.getFechaHasta());
				for(Comprobante comp: comprobantes){				
					if(comp !=null){				
						listaComprobantesDto.add(leer(comp));
					}			
				}				
		
		}else if (fechaDesde != null && fechaHasta != null && nroDocumento != null && vendedorId ==null){
			List<Comprobante> comprobantes = comprobanteRepository.findByFechasAndNroDocumento(filterComprobante.getNroDocumento(), filterComprobante.getFechaDesde(),filterComprobante.getFechaHasta());
			for(Comprobante comp: comprobantes){				
				if(comp !=null){
					listaComprobantesDto.add(leer(comp));
				}
			}
		}else if (nroDocumento != null && fechaDesde == null && fechaHasta == null && vendedorId ==null){
			List<Comprobante> comprobantes= comprobanteRepository.findByNroDocumento(filterComprobante.getNroDocumento());
			for(Comprobante comp: comprobantes){
				
				if(comp !=null){
					listaComprobantesDto.add(leer(comp));
				}
			}
		}else if(vendedorId !=null && nroDocumento == null && fechaDesde == null && fechaHasta == null){
			List<Comprobante> comprobantes= comprobanteRepository.findByVendedorId(filterComprobante.getVendedorId());
			for(Comprobante comp: comprobantes){
				
				if(comp !=null){
					listaComprobantesDto.add(leer(comp));
				}
			}
		}else if(vendedorId !=null && nroDocumento != null && fechaDesde == null && fechaHasta == null){
			List<Comprobante> comprobantes= comprobanteRepository.findByVendedorAndDocumento(filterComprobante.getVendedorId(),filterComprobante.getNroDocumento());
			for(Comprobante comp: comprobantes){
				
				if(comp !=null){
					listaComprobantesDto.add(leer(comp));
				}
			}
		}else if(vendedorId ==null && nroDocumento == null && fechaDesde == null && fechaHasta == null){
			List<Comprobante> comprobantes= (List<Comprobante>) comprobanteRepository.findAll();
			for(Comprobante comp: comprobantes){				
				if(comp !=null){
					listaComprobantesDto.add(leer(comp));
				}
			}
		}
		
		return listaComprobantesDto;
	}
		
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/comprobante/reporteF/filter", produces="application/octet")
	@Transactional
	public void writeFacturaListToFile(@RequestBody ComprobanteFilterDto filterComprobante,HttpServletResponse resp) throws Exception{
		
		List<ComprobanteDto> listaComprobantesDto = new ArrayList<ComprobanteDto>();
		Date fechaDesde = filterComprobante.getFechaDesde();
		Date fechaHasta = filterComprobante.getFechaHasta();
		String nroDocumento = filterComprobante.getNroDocumento();
		String vendedorId = filterComprobante.getVendedorId();	
		
		if ( fechaDesde != null && fechaHasta != null && nroDocumento==null && vendedorId ==null){
				List<Comprobante> comprobantes= comprobanteRepository.findByFechas(filterComprobante.getFechaDesde(),filterComprobante.getFechaHasta());
				for(Comprobante comp: comprobantes){
					
					if(comp !=null){						
						listaComprobantesDto.add(leer(comp));
					}
			}
			
		}else if (nroDocumento == null && fechaDesde != null && fechaHasta != null && vendedorId !=null){		
				List<Comprobante> comprobantes= comprobanteRepository.findByFechasAndVendedor(filterComprobante.getVendedorId(),filterComprobante.getFechaDesde(),filterComprobante.getFechaHasta());
				for(Comprobante comp: comprobantes){
				
					if(comp !=null){				
					listaComprobantesDto.add(leer(comp));
					}			
				}				
		
		}else if (fechaDesde != null && fechaHasta != null && nroDocumento != null && vendedorId ==null){
			List<Comprobante> comprobantes = comprobanteRepository.findByFechasAndNroDocumento(filterComprobante.getNroDocumento(), filterComprobante.getFechaDesde(),filterComprobante.getFechaHasta());
			for(Comprobante comp: comprobantes){
				
				if(comp !=null){
					listaComprobantesDto.add(leer(comp));
				}
			}
		}else if (nroDocumento != null && fechaDesde == null && fechaHasta == null && vendedorId ==null){
			List<Comprobante> comprobantes= comprobanteRepository.findByNroDocumento(filterComprobante.getNroDocumento());
			for(Comprobante comp: comprobantes){
				
				if(comp !=null){
					listaComprobantesDto.add(leer(comp));
				}
			}
		}else if(vendedorId !=null && nroDocumento == null && fechaDesde == null && fechaHasta == null){
			List<Comprobante> comprobantes= comprobanteRepository.findByVendedorId(filterComprobante.getVendedorId());
			for(Comprobante comp: comprobantes){
				
				if(comp !=null){
					listaComprobantesDto.add(leer(comp));
				}
			}
		}else if(vendedorId !=null && nroDocumento != null && fechaDesde == null && fechaHasta == null){
			List<Comprobante> comprobantes= comprobanteRepository.findByVendedorAndDocumento(filterComprobante.getVendedorId(),filterComprobante.getNroDocumento());
			for(Comprobante comp: comprobantes){
				
				if(comp !=null){
					listaComprobantesDto.add(leer(comp));
				}
			}
		}else if(vendedorId ==null && nroDocumento == null && fechaDesde == null && fechaHasta == null){
			List<Comprobante> comprobantes= (List<Comprobante>) comprobanteRepository.findAll();
			for(Comprobante comp: comprobantes){				
				if(comp !=null){
					listaComprobantesDto.add(leer(comp));
				}
			}
		}	
		
		
		Workbook workbook = new XSSFWorkbook();				
		Sheet sheet = workbook.createSheet("Comprobantes");		
		Factura fac = new Factura();			
		Cell celda;
		int i = 0;
		String[] titulos = { "NumeroComprobante","Cliente","MontoTotal","fechaEmision","Vendedor"};	
		
		int rowIndex = 0;			
		Row cabecera = sheet.createRow(rowIndex++);
		for (i = 0; i < titulos.length; i++) {				
				celda = cabecera.createCell(i);
				celda.setCellValue(titulos[i]);
		}				
		for(ComprobanteDto comprobante: listaComprobantesDto){			
			Row row = sheet.createRow(rowIndex++);
				Cell cell0 = row.createCell(0);		
				cell0.setCellValue(validarString(comprobante.getNroDocumento()));			
				Cell cell1 = row.createCell(1);
				cell1.setCellValue(validarString(comprobante.getCliente()));				
				Cell cell2 = row.createCell(2);			
				cell2.setCellValue(montoString(comprobante.getMontoTotal()));
				Cell cell3 = row.createCell(3);			
				cell3.setCellValue(fechaString(comprobante.getFechaEmision()));
				Cell cell4 = row.createCell(4);			
				cell4.setCellValue(validarString(comprobante.getVendedorDescripcion()));
				}               
		
		
		
		System.out.println("excelprueba.xlsx" + " " + "exportado satisfactoriamente");		
		String ruta="C:\\softbrill\\sisadmin\\excelcomprobante\\Comprobante_"+fechaActual()+".xlsx";
		File archivoBorrar = new File(ruta);
		if (archivoBorrar.exists()){ archivoBorrar.delete(); }
		FileOutputStream fos = new FileOutputStream("C:/softbrill/sisadmin/excelcomprobante/Comprobante_"+fechaActual()+".xlsx");
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
	}
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/reportecomprobantes/filter", produces="application/octet")
	@Transactional
	public void reporteComprobante( @RequestBody OrdenCompraFilter ordenCompraFilter, HttpServletResponse resp) throws IOException, Exception{
		List<Comprobante> comprobante = new ArrayList<Comprobante>();
		if(ordenCompraFilter.getFechaFin()==null && ordenCompraFilter.getFechaInicio()==null){
			comprobante=(List<Comprobante>)comprobanteRepository.findAll();
		}else{
			comprobante = (List<Comprobante>)comprobanteRepository.findByFechas(ordenCompraFilter.getFechaInicio(), ordenCompraFilter.getFechaFin());		
		}
		 		
		FileInputStream inputStream = new FileInputStream(new File("C:\\softbrill\\sisadmin\\reporteExcel\\comprobantes.xlsx"));								
		OPCPackage pkg = OPCPackage.open(inputStream);
		XSSFWorkbook wb = new XSSFWorkbook(pkg);								
		Sheet sheet = wb.getSheetAt(0);
		int rowIndex = 2;	
		for(Comprobante c : comprobante){
			Row row = sheet.createRow(rowIndex++);	
			Cell cell0 = row.createCell(0);	cell0.setCellValue(validarString(c.getNroDocumento()));
			Cell cell1 = row.createCell(1);	
			cell1.setCellValue("");
			if(c.getCliente()!=null){cell1.setCellValue(c.getCliente().getRazonSocial());}
			Cell cell2 = row.createCell(2);	cell2.setCellValue(montoString(c.getMontoTotal()));
			Cell cell3 = row.createCell(3);	cell3.setCellValue(fechaString(c.getFechaEmision()));
					
		}
		resp.setContentType("application/octet"); resp.setHeader("Content-Disposition","attachment; filename=\"" + "excelprueba.xlsx" +"\"");
		OutputStream outputStream = null;
		try {	outputStream = resp.getOutputStream();
				wb.write(outputStream);
				//IOUtils.copy(inputStream, outputStream);
			} catch (Exception e) {	System.out.println("Error al obtener archivo "); e.printStackTrace();
			} finally {	inputStream.close(); outputStream.close();	}
		
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
	private ComprobanteDto leer(Comprobante com){
		ComprobanteDto comprobanteDto = new ComprobanteDto();
		comprobanteDto.setId(com.getIdSa());
		if(com.getAlmacen()!=null){comprobanteDto.setAlmacen(com.getAlmacen().getDescripcion());}
		if(com.getCliente()!=null){comprobanteDto.setCliente(com.getCliente().getRazonSocial());}
		comprobanteDto.setNroDocumento(com.getNroDocumento());
		comprobanteDto.setMontoTotal(com.getMontoTotal());
		comprobanteDto.setFechaEmision(com.getFechaEmision());
		if(com.getCliente()!=null)
		comprobanteDto.setIdentificadorCliente(com.getCliente().getIdentificador());
		
		comprobanteDto.setUsuarioCreacion(com.getUsuarioCreacion());
		comprobanteDto.setUsuarioModificacion(com.getUsuarioModificacion());
		comprobanteDto.setFechaCreacion(com.getFechaCreacion());
		comprobanteDto.setFechaModificacion(com.getFechaModificacion());
		
		if(com.getVendedor()!= null){
			comprobanteDto.setVendedorDescripcion(com.getVendedor().getNombreCompleto());
		}
		
		List<ComprobanteDetalle> comprobanteDetalle = (List<ComprobanteDetalle>)comprobanteDetalleRepository.findByTicket(com.getId());
		
		for (ComprobanteDetalle detalle : comprobanteDetalle) {
			ComprobanteDetalleDto detalleDto = new ComprobanteDetalleDto();
			detalleDto.setCodigo("");			
			detalleDto.setProducto("");
			detalleDto.setUnidadMedida("");
			if(detalle.getProducto()!=null){
				detalleDto.setCodigo(detalle.getProducto().getCodigoBarra());			
				detalleDto.setProducto(detalle.getProducto().getDescripcion());
			}
			if(detalle.getUnidadMedida()!=null)
			detalleDto.setUnidadMedida(detalle.getUnidadMedida().getDescripcion());
			
			detalleDto.setCantidad(detalle.getCantidad());
			detalleDto.setPrecioUnitario(detalle.getPrecio_unitario());
			detalleDto.setPrecioTotal(detalle.getPrecio_total());
			
			comprobanteDto.getDetalles().add(detalleDto);
		}
		
		return comprobanteDto;
	}
	
	@Autowired
	private ComprobanteRepository comprobanteRepository;
	@Autowired
	private ComprobanteDetalleRepository comprobanteDetalleRepository;
}
