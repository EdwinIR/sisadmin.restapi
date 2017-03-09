package com.sisadmin.stock.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sisadmin.admin.dto.SisadminUtil;
import com.sisadmin.entity.MarcaFamilia;
import com.sisadmin.entity.Producto;
import com.sisadmin.entity.ProductoAlmacen;
import com.sisadmin.entity.ProductoDetalle;
import com.sisadmin.entity.UnidadMedida;
import com.sisadmin.repository.MarcaFamiliaRepository;
import com.sisadmin.repository.ProductoAlmacenRepository;
import com.sisadmin.repository.ProductoRepository;
import com.sisadmin.service.repository.EmisorActual;
import com.sisadmin.stock.dto.StockMinMaxDto;
import com.sisadmin.stock.dto.StockMinMaxFilter;
import com.sisadmin.stock.dto.StockValorizadoDto;
import com.sisadmin.util.PropertyLoader;



@Controller
public class StockMinMaxController {
	
		@RequestMapping(method=RequestMethod.POST, value="/api/v1/stockminmax/filter")
	    public @ResponseBody List<StockMinMaxDto> getListado(@RequestBody StockMinMaxFilter stockMinMaxFilter, HttpServletRequest request, HttpServletResponse resp) throws ParseException, IOException{
	                List<StockMinMaxDto> minMaxDto = new ArrayList<StockMinMaxDto>();
	                try{
	                    MarcaFamilia marcaFamilia = marcaFamiliaRepository.findByIdMarcaAndIdFamilia(stockMinMaxFilter.getMarcaId(),stockMinMaxFilter.getFamiliaId());
	                    List<Producto> productos = (List<Producto>)productoRepository.findByCodFamiliaMarcas(marcaFamilia.getFamilia().getIdSa(),marcaFamilia.getMarca().getIdSa());
	                    
	                    for(Producto prod : productos){
	                        ProductoAlmacen productoAlmacen = productoAlmacenRepository.findByCodProductoCodAlmacen(prod.getIdSa(),stockMinMaxFilter.getAlmacenId());    
	                        if(productoAlmacen != null) {
	                                    StockMinMaxDto dto = new StockMinMaxDto();
	                                    dto.setDescripcion(prod.getDescripcion());
	                                    dto.setIdProd(prod.getIdSa());
	                                    dto.setUnidadMedida(SisadminUtil.obtenerUnidadMedidaPpal(prod));
	                                    dto.setStockFisico(productoAlmacen.getCantidad());
	                                    dto.setStockMin(productoAlmacen.getStockMinimo());
	                                    dto.setStockMax(productoAlmacen.getStockMaximo());
	                                    minMaxDto.add(dto);
	                        }            
	                    }
	                } catch(Exception e){}	                
	                return minMaxDto;
	                    
	        }
		 
		@RequestMapping(method=RequestMethod.POST, value="/api/v1/stockminmax/pdf/filter", produces="application/octet")
		@Transactional
		public void getPdf(@RequestBody StockMinMaxFilter stockMinMaxFilter, HttpServletResponse resp) throws ParseException, IOException{
					List<StockMinMaxDto> minMaxDto = new ArrayList<StockMinMaxDto>();
	                InputStream inputStream = null;
					OutputStream outputStream = null;
					String almacen="";
					BigDecimal precio = new BigDecimal(00);
	                try{
	                    MarcaFamilia marcaFamilia = marcaFamiliaRepository.findByIdMarcaAndIdFamilia(stockMinMaxFilter.getMarcaId(),stockMinMaxFilter.getFamiliaId());
	                    List<Producto> productos = (List<Producto>)productoRepository.findByCodFamiliaMarcas(marcaFamilia.getFamilia().getIdSa(),marcaFamilia.getMarca().getIdSa());	                    
	                    for(Producto prod : productos){
	                        ProductoAlmacen productoAlmacen = productoAlmacenRepository.findByCodProductoCodAlmacen(prod.getIdSa(),stockMinMaxFilter.getAlmacenId());    
	                        if(productoAlmacen != null) {
	                                    StockMinMaxDto dto = new StockMinMaxDto();
	                                    dto.setDescripcion(prod.getDescripcion());
	                                    dto.setIdProd(prod.getIdSa());
	                                    dto.setUnidadMedida(SisadminUtil.obtenerUnidadMedidaPpal(prod));
	                                    dto.setStockFisico(productoAlmacen.getCantidad());
	                                    dto.setStockMin(productoAlmacen.getStockMinimo());
	                                    dto.setStockMax(productoAlmacen.getStockMaximo());
	                                    if(validarObjecto(productoAlmacen.getAlmacen()))
	                                    	almacen = productoAlmacen.getAlmacen().getDescripcion();
	                                    minMaxDto.add(dto);
	                        }            
	                    }
	                    
	                    HashMap<String, Object> hInputHeader = new HashMap<String, Object>();
	                    String jasperFile = ""; 
	                    String logo1 = "";
	                    String fondo1 = "";
	                    String rutaPdf ="";
	                    String pdfFileName="";
	                    Map<String,Object> params = new HashMap<String,Object>();
	                    params.put("inputHeader", hInputHeader);
	                    params.put("logoPath","C:\\Users\\mario\\Desktop\\reporte\\");
	                    SimpleDateFormat sdf = new SimpleDateFormat( "dd-MM-yyyy");
	                    hInputHeader.put("Empresa", "123456789");
	                    hInputHeader.put("Sucursal", almacen);
	                    hInputHeader.put("Direccion", "123456");
	                    hInputHeader.put("Fecha_Emision",fechaEmision());
	              
	                    rutaPdf = properties.getProperty("sisadmin.path.stock.minmax.archivo");
	                    jasperFile = properties.getProperty("sisadmin.path.stock.minmax.jasper");
	                    logo1 = properties.getProperty("sisadmin.path.stock.minmax.logo1");
	                    fondo1 = properties.getProperty("sisadmin.path.stock.minmax.fondo1");
	                    hInputHeader.put("fondo1", fondo1);	              
	                    hInputHeader.put("logo1", logo1);
	                    Map mapDataSource[] = new HashMap[minMaxDto.size()];
	                    int i = 0; 
	                    for (StockMinMaxDto dto : minMaxDto){
	                        HashMap<String, String> hLinea = new HashMap<String, String>();                    
	                        hLinea.put("Codigo_Producto",dto.getIdProd()+"");
	                        hLinea.put("Descripcion_Producto", dto.getDescripcion());                        
	                        hLinea.put("UnidadMedida_Producto", dto.getUnidadMedida());                        
	                        hLinea.put("Precio_Producto",precio.toString());                        
	                        hLinea.put("Stock_Min",dto.getStockMin().toString());
	                        hLinea.put("Stock_Max", dto.getStockMax().toString());
	                        hLinea.put("Stock", dto.getStockFisico().toString());
	                        mapDataSource[i] = hLinea;
	                        i++;
	                    }
	                    JRMapArrayDataSource datasource = new JRMapArrayDataSource(mapDataSource);
	                    FileInputStream is = new FileInputStream(jasperFile);
	                    JasperPrint pdfRender = JasperFillManager.fillReport(is, params, datasource);
	                    pdfFileName= rutaPdf+"reporte_stockminmax_"+fechaActual()+".pdf";              
	                    File archivoBorrar = new File(pdfFileName);
						if (archivoBorrar.exists()){ archivoBorrar.delete(); }
	                    
	                    JasperExportManager.exportReportToPdfFile(pdfRender,pdfFileName);
	                    resp.setContentType("application/octet");
						resp.setHeader("Content-Disposition","attachment; filename=\"" + "mario.pdf" +"\"");
						
						File archivoNuevo = new File(pdfFileName);
						inputStream = new FileInputStream(archivoNuevo);
						outputStream = resp.getOutputStream();
						IOUtils.copy(inputStream, outputStream);
	                    
	                } catch(Exception e){}
	                finally {
	                		inputStream.close();
	                		outputStream.close();
	                }
	        }
				
		private boolean validarObjecto(Object objecto){
			boolean flag = false;
			if(objecto!=null){flag=true;}			
			return flag;
		}		
		private String fechaActual(){
			 Date fechaActual = new Date();
			 SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			 return sdf.format(fechaActual);
		}		 
		private String fechaEmision(){
			 Date fechaHoy = new Date();
			 SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy   hh:mm");
			 return sdf.format(fechaHoy);
		 }
		 private Properties properties = PropertyLoader.getServicesProperties();
		 
		
			@Autowired
			private ProductoAlmacenRepository productoAlmacenRepository;
			
			@Autowired
			private MarcaFamiliaRepository marcaFamiliaRepository;
			
			@Autowired
			private ProductoRepository productoRepository;
}
