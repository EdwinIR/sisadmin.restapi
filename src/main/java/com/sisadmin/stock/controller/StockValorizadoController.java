package com.sisadmin.stock.controller;

import java.io.File;
import java.io.FileInputStream;
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

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sisadmin.entity.MarcaFamilia;
import com.sisadmin.entity.Producto;
import com.sisadmin.entity.ProductoAlmacen;
import com.sisadmin.entity.ProductoDetalle;
import com.sisadmin.repository.MarcaFamiliaRepository;
import com.sisadmin.repository.ProductoAlmacenRepository;
import com.sisadmin.repository.ProductoRepository;
import com.sisadmin.stock.dto.StockMinMaxFilter;
import com.sisadmin.stock.dto.StockValorizadoDto;
import com.sisadmin.util.PropertyLoader;

@Controller
public class StockValorizadoController {
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/stockvalor/filter")
	public @ResponseBody List<StockValorizadoDto> getAllFilterValor(@RequestBody StockMinMaxFilter stockMinMaxFilter, HttpServletRequest request) throws ParseException{

				MarcaFamilia marcaFamilia = marcaFamiliaRepository.findByIdMarcaAndIdFamilia(stockMinMaxFilter.getMarcaId(),stockMinMaxFilter.getFamiliaId());
				List<Producto> productos = (List<Producto>)productoRepository.findByCodFamiliaMarcas(marcaFamilia.getFamilia().getIdSa(),marcaFamilia.getMarca().getIdSa());
				List<StockValorizadoDto> minMaxDto = new ArrayList<StockValorizadoDto>();
				for(Producto prod : productos){
					ProductoAlmacen productoAlmacen = productoAlmacenRepository.findByCodProductoCodAlmacen(prod.getIdSa(),stockMinMaxFilter.getAlmacenId());	
					if(productoAlmacen != null) {
						StockValorizadoDto dto = new StockValorizadoDto();
								dto.setDescripcion(prod.getDescripcion());								
								dto.setStockFisico(productoAlmacen.getCantidad());								
								ProductoDetalle a =prod.getProductoDetalleA();
								ProductoDetalle b =prod.getProductoDetalleB();
								ProductoDetalle c =prod.getProductoDetalleC();
								BigDecimal precioPrincipal = new BigDecimal(0);
								String uniPpal = prod.getUnidadMedidaPrincipal();
								if(uniPpal != null && uniPpal.equalsIgnoreCase("A") && a!=null && a.getPrecio()!=null){precioPrincipal = a.getPrecio();}								
								if(uniPpal != null && uniPpal.equalsIgnoreCase("B") && b!=null && b.getPrecio()!=null){precioPrincipal = b.getPrecio();}
								if(uniPpal != null && uniPpal.equalsIgnoreCase("C") && c!=null && c.getPrecio()!=null){precioPrincipal = c.getPrecio();}
								dto.setUnidadMedida(prod.getUnidadMedidaPrincipal());
								dto.setPrecio(precioPrincipal);								
								BigDecimal stockValor = productoAlmacen.getCantidad().multiply(precioPrincipal);
								dto.setStockValor(stockValor);
								//dto.setStockMax(stockMax);
								minMaxDto.add(dto);
					}			
				}
				
				/*List<StockMinMaxDto> docDtos = new ArrayList<StockMinMaxDto>();
				for (ProductoAlmacen proAlm : productoAlmacen) {
					docDtos.add(convertToDto(proAlm));
										
				}*/
				return minMaxDto;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/stockvalor/pdf/filter", produces="application/octet")
	@Transactional
	public void getPdf(@RequestBody StockMinMaxFilter stockMinMaxFilter, HttpServletResponse resp) throws ParseException, IOException{
    			List<StockValorizadoDto> valorizadoDto = new ArrayList<StockValorizadoDto>();
				String almacen="";
                InputStream inputStream = null;
				OutputStream outputStream = null;
                try{
                	MarcaFamilia marcaFamilia = marcaFamiliaRepository.findByIdMarcaAndIdFamilia(stockMinMaxFilter.getMarcaId(),stockMinMaxFilter.getFamiliaId());
    				List<Producto> productos = (List<Producto>)productoRepository.findByCodFamiliaMarcas(marcaFamilia.getFamilia().getIdSa(),marcaFamilia.getMarca().getIdSa() );
                    
    				for(Producto prod : productos){
    					ProductoAlmacen productoAlmacen = productoAlmacenRepository.findByCodProductoCodAlmacen(prod.getIdSa(),stockMinMaxFilter.getAlmacenId());	
    					if(productoAlmacen != null) {
    						StockValorizadoDto dto = new StockValorizadoDto();
    								dto.setProductoId(prod.getIdSa());
    								dto.setDescripcion(prod.getDescripcion());    								
    								dto.setStockFisico(productoAlmacen.getCantidad());
    								almacen = productoAlmacen.getAlmacen().getDescripcion();    								
    								ProductoDetalle a =prod.getProductoDetalleA();
    								ProductoDetalle b =prod.getProductoDetalleB();
    								ProductoDetalle c =prod.getProductoDetalleC();
    								BigDecimal precioPrincipal = new BigDecimal(0);
    								dto.setUnidadMedida(prod.getUnidadMedidaPrincipal());
    								String uniPpal = prod.getUnidadMedidaPrincipal();
    								if(uniPpal != null && uniPpal.equalsIgnoreCase("A") && a!=null && a.getPrecio()!=null){precioPrincipal = a.getPrecio();}								
    								if(uniPpal != null && uniPpal.equalsIgnoreCase("B") && b!=null && b.getPrecio()!=null){precioPrincipal = b.getPrecio();}
    								if(uniPpal != null && uniPpal.equalsIgnoreCase("C") && c!=null && c.getPrecio()!=null){precioPrincipal = c.getPrecio();}
    								dto.setUnidadMedida(prod.getUnidadMedidaPrincipal());
    								dto.setPrecio(precioPrincipal);
    								BigDecimal stockValor = productoAlmacen.getCantidad().multiply(precioPrincipal);
    								dto.setStockValor(stockValor);
    								//dto.setStockMax(stockMax);
    								valorizadoDto.add(dto);
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
              
                    rutaPdf = properties.getProperty("sisadmin.path.stock.valorizado.archivo");
                    jasperFile = properties.getProperty("sisadmin.path.stock.valorizado.jasper");
                    logo1 = properties.getProperty("sisadmin.path.stock.valorizado.logo1");
                    fondo1 = properties.getProperty("sisadmin.path.stock.valorizado.fondo1");
                    hInputHeader.put("fondo1", fondo1);	              
                    hInputHeader.put("logo1", logo1);
                    Map mapDataSource[] = new HashMap[valorizadoDto.size()];
                    int i = 0; 
                    for (StockValorizadoDto dto : valorizadoDto){
                        HashMap<String, String> hLinea = new HashMap<String, String>();                    
                        hLinea.put("Codigo_Producto",dto.getProductoId()+"");
                        hLinea.put("Descripcion_Producto", dto.getDescripcion());                        
                        hLinea.put("UnidadMedida_Producto", dto.getUnidadMedida());                        
                        hLinea.put("Precio_Producto",dto.getPrecio()+"");
                        hLinea.put("Stock", dto.getStockFisico()+"");
                        hLinea.put("Stock_Valor",dto.getStockValor()+"");
                        mapDataSource[i] = hLinea;
                        i++;
                    }
                    JRMapArrayDataSource datasource = new JRMapArrayDataSource(mapDataSource);
                    FileInputStream is = new FileInputStream(jasperFile);
                    JasperPrint pdfRender = JasperFillManager.fillReport(is, params, datasource);
                    pdfFileName= rutaPdf+"reporte_stockvalorizado_"+fechaActual()+".pdf";              
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
