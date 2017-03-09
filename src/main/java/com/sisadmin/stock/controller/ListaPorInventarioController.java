package com.sisadmin.stock.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import com.sisadmin.admin.dto.SisadminUtil;
import com.sisadmin.entity.MarcaFamilia;
import com.sisadmin.entity.Producto;
import com.sisadmin.entity.ProductoAlmacen;
import com.sisadmin.repository.AlmacenRepository;
import com.sisadmin.repository.MarcaFamiliaRepository;
import com.sisadmin.repository.ProductoAlmacenRepository;
import com.sisadmin.repository.ProductoRepository;
import com.sisadmin.repository.ZonaRepository;
import com.sisadmin.stock.dto.ListaInventarioFilter;
import com.sisadmin.stock.dto.ListaXInventarioDto;
import com.sisadmin.util.PropertyLoader;

@Controller
public class ListaPorInventarioController {
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/listainventario/filter")
    public @ResponseBody List<ListaXInventarioDto> getListado(@RequestBody ListaInventarioFilter listaInventarioFilter, HttpServletRequest request, HttpServletResponse resp) throws ParseException, IOException{
                List<ListaXInventarioDto> listaInventarioDto = new ArrayList<ListaXInventarioDto>();
                try{
                    MarcaFamilia marcaFamilia = marcaFamiliaRepository.findByIdMarcaAndIdFamilia(listaInventarioFilter.getMarcaId(),listaInventarioFilter.getFamiliaId());
                    List<Producto> productos = (List<Producto>)productoRepository.findByCodFamiliaMarcas(marcaFamilia.getFamilia().getIdSa(),marcaFamilia.getMarca().getIdSa());
                    for(Producto prod : productos){
                        ProductoAlmacen productoAlmacen = productoAlmacenRepository.findByCodProductoCodAlmacen(prod.getIdSa(),listaInventarioFilter.getAlmacenId());    
                        if(productoAlmacen != null) {
                                    ListaXInventarioDto dto = new ListaXInventarioDto();
                                    dto.setCodigoProducto(prod.getCodigoEquivalente());
                                    dto.setDescripcionProducto(prod.getDescripcion());                                    
                                    dto.setUnidadMedidaPrincipal(SisadminUtil.obtenerUnidadMedidaPpal(prod));
                                    dto.setDescripcionMarca("");
                                    if(validarObjecto(prod.getMarcaFamilia()))
                                    dto.setDescripcionMarca(prod.getMarcaFamilia().getMarca().getDescripcion());
                                    dto.setStock(productoAlmacen.getCantidad());
                                    listaInventarioDto.add(dto);
                        }            
                    }
                } catch(Exception e){}	                
                return listaInventarioDto;                    
    }
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/listainventario/pdf/filter", produces="application/octet")
	@Transactional
	public void getPdf(@RequestBody ListaInventarioFilter listaInventarioFilter, HttpServletResponse resp) throws ParseException, IOException{
				
				List<ListaXInventarioDto> listaInventarioDto = new ArrayList<ListaXInventarioDto>();
				InputStream inputStream = null;
				OutputStream outputStream = null;
				String almacen="";
				
                try{
                    MarcaFamilia marcaFamilia = marcaFamiliaRepository.findByIdMarcaAndIdFamilia(listaInventarioFilter.getMarcaId(),listaInventarioFilter.getFamiliaId());
                    List<Producto> productos = (List<Producto>)productoRepository.findByCodFamiliaMarcas(marcaFamilia.getFamilia().getIdSa(),marcaFamilia.getMarca().getIdSa());
                    for(Producto prod : productos){
                        ProductoAlmacen productoAlmacen = productoAlmacenRepository.findByCodProductoCodAlmacen(prod.getIdSa(),listaInventarioFilter.getAlmacenId());    
                        if(productoAlmacen != null) {
                                    ListaXInventarioDto dto = new ListaXInventarioDto();
                                    dto.setCodigoProducto(prod.getCodigoEquivalente());
                                    dto.setDescripcionProducto(prod.getDescripcion());
                                    dto.setUnidadMedidaPrincipal(SisadminUtil.obtenerUnidadMedidaPpal(prod)); 
                                    dto.setDescripcionMarca("");
                                    if(validarObjecto(prod.getMarcaFamilia()))
                                    dto.setDescripcionMarca(prod.getMarcaFamilia().getMarca().getDescripcion());
                                    dto.setStock(productoAlmacen.getCantidad());
                                    if(validarObjecto(productoAlmacen.getAlmacen()))
                                    almacen = productoAlmacen.getAlmacen().getDescripcion();
                                    listaInventarioDto.add(dto);
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
                    hInputHeader.put("Fecha_Emision", fechaEmision());
              
                    rutaPdf = properties.getProperty("sisadmin.path.lista.inventario.archivo");
                    jasperFile = properties.getProperty("sisadmin.path.lista.inventario.jasper");
                    logo1 = properties.getProperty("sisadmin.path.lista.inventario.logo1");
                    fondo1 = properties.getProperty("sisadmin.path.lista.inventario.fondo1");
                    hInputHeader.put("fondo1", fondo1);	              
                    hInputHeader.put("logo1", logo1);
                    Map mapDataSource[] = new HashMap[listaInventarioDto.size()];
                    int i = 0; 
                    for (ListaXInventarioDto dto : listaInventarioDto){
                        HashMap<String, String> hLinea = new HashMap<String, String>();                    
                        hLinea.put("Codigo_Producto",dto.getCodigoProducto());
                        hLinea.put("Descripcion_Producto",dto.getDescripcionProducto());                        
                        hLinea.put("UnidadMedida_Producto", dto.getUnidadMedidaPrincipal());                        
                        hLinea.put("Marca_Producto",dto.getDescripcionMarca());
                        hLinea.put("Stock", dto.getStock()+"");
                        mapDataSource[i] = hLinea;
                        i++;
                    }
                    JRMapArrayDataSource datasource = new JRMapArrayDataSource(mapDataSource);
                    FileInputStream is = new FileInputStream(jasperFile);
                    JasperPrint pdfRender = JasperFillManager.fillReport(is, params, datasource);
                    pdfFileName= rutaPdf+"reporte_listainventario_"+fechaActual()+".pdf";              
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
		 SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		 return sdf.format(fechaHoy);
	 }
	
	private Properties properties = PropertyLoader.getServicesProperties();
	
	
	@Autowired
	private ProductoAlmacenRepository productoAlmacenRepository;
	
	@Autowired
	private ZonaRepository zonaRepository;
	
	@Autowired
	private MarcaFamiliaRepository marcaFamiliaRepository;
	
	@Autowired
	private ProductoRepository productoRepository;
	
	@Autowired
	private AlmacenRepository almacenRepository;
}
