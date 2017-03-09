package com.sisadmin.admin.controller;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sisadmin.entity.Categorie;
import com.sisadmin.entity.Marca;
import com.sisadmin.entity.Producto;
import com.sisadmin.entity.ProductoAlmacen;
import com.sisadmin.repository.CategorieRepository;
import com.sisadmin.repository.MarcaRepository;
import com.sisadmin.repository.ProductoAlmacenRepository;
import com.sisadmin.repository.ProductoRepository;

@Controller
public class ReporteController {

	private void generarPdf(ProductoAlmacen producto)throws Exception{
		
		HashMap<String, Object> hInputHeader = new HashMap<String, Object>();
		String jasperFile = ""; 
		String pdfFileName="";		
												
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("inputHeader", hInputHeader);
		params.put("logoPath","C:\\edwin\\utils\\jasper\\");
		SimpleDateFormat sdf = new SimpleDateFormat( "dd-MM-yyyy");
		hInputHeader.put("Ruc_Emisor", "123456789");
		hInputHeader.put("Numero_Legal", "123456");
		hInputHeader.put("Fecha_Emision", "123456");				
		hInputHeader.put("Monto_Gravado", "12.00");
		hInputHeader.put("Monto_Exonerado", "12.00");
		hInputHeader.put("Monto_Inafecto", "12.00");
		hInputHeader.put("Monto_Gratuito", "12.00");				
		hInputHeader.put("Total_Descuentos", "12.00");				
		hInputHeader.put("IGV", "12.00");
		hInputHeader.put("Monto_Total", "12.00");				
		hInputHeader.put("Cliente_Razon_Social", "123456789");
		hInputHeader.put("Cliente_Ruc", "123456");
		hInputHeader.put("Cliente_Direccion", "123456");
		hInputHeader.put("digestValue", "123456");
		hInputHeader.put("Moneda", "123456");				
		hInputHeader.put("Factura_Asociada", "123456");
		hInputHeader.put("Leyenda", "123456");
		hInputHeader.put("fondo1", "C:\\edwin\\utils\\jasper\\sello.png");
		hInputHeader.put("fondo2", "C:\\edwin\\utils\\jasper\\sello.png");
		hInputHeader.put("logo1", "C:\\edwin\\utils\\jasper\\logo.png");
		hInputHeader.put("logo2", "C:\\edwin\\utils\\jasper\\logo.png");
		hInputHeader.put("logo3", "C:\\edwin\\utils\\jasper\\logo.png");
		hInputHeader.put("logo4", "C:\\edwin\\utils\\jasper\\logo.png");
		hInputHeader.put("logo5", "C:\\edwin\\utils\\jasper\\logo.png");
		hInputHeader.put("logo6", "C:\\edwin\\utils\\jasper\\logo.png");
		jasperFile= "C:\\edwin\\utils\\jasper\\invoice.jasper";
		
		/*Map mapDataSource[] = new HashMap[1];
		HashMap<String, String> hLinea = new HashMap<String, String>();						
		hLinea.put("Linea_Numero", "Hola");
		hLinea.put("Descripcion", "Hola");	
		hLinea.put("Unidad_Medida", "Hola");
		hLinea.put("Codigo_Producto", "Hola");
		hLinea.put("Cantidad",   "10.22");
		hLinea.put("Valor_Unitario_SinIGV", "10.22");
		hLinea.put("Total_Linea_SinIGV", "10.22");
		mapDataSource[0] = hLinea;*/
		int i = 0;
		Collection<ProductoAlmacen> lineas=new ArrayList<ProductoAlmacen>() ;
		Map mapDataSource[] = new HashMap[lineas.size()];				
		for (ProductoAlmacen linea : lineas){
				HashMap<String, String> hLinea = new HashMap<String, String>();						
				//hLinea.put("Linea_Numero", linea.getPk());
				//hLinea.put("Descripcion", linea.getPk());	
				//hLinea.put("Unidad_Medida", linea.getCantidad());
				//hLinea.put("Codigo_Producto", linea.getCodigoProducto());
			//hLinea.put("Cantidad",linea.getCantidad().toString());
				mapDataSource[i] = hLinea;
				i++;
		}
		
	}
	
	/**Clientes**/
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/document/pdf")
	public @ResponseBody String todosClientes() throws Exception{
		
		HashMap<String, Object> hInputHeader = new HashMap<String, Object>();
		String jasperFile = ""; 
		String pdfFileName="";		
												
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("inputHeader", hInputHeader);
		params.put("logoPath","C:\\edwin\\utils\\jasper\\");
		SimpleDateFormat sdf = new SimpleDateFormat( "dd-MM-yyyy");
		hInputHeader.put("Ruc_Emisor", "123456789");
		hInputHeader.put("Numero_Legal", "123456");
		hInputHeader.put("Fecha_Emision", "123456");				
		hInputHeader.put("Monto_Gravado", "12.00");
		hInputHeader.put("Monto_Exonerado", "12.00");
		hInputHeader.put("Monto_Inafecto", "12.00");
		hInputHeader.put("Monto_Gratuito", "12.00");				
		hInputHeader.put("Total_Descuentos", "12.00");				
		hInputHeader.put("IGV", "12.00");
		hInputHeader.put("Monto_Total", "12.00");				
		hInputHeader.put("Cliente_Razon_Social", "123456789");
		hInputHeader.put("Cliente_Ruc", "123456");
		hInputHeader.put("Cliente_Direccion", "123456");
		hInputHeader.put("digestValue", "123456");
		hInputHeader.put("Moneda", "123456");				
		hInputHeader.put("Factura_Asociada", "123456");
		hInputHeader.put("Leyenda", "123456");
		hInputHeader.put("fondo1", "C:\\edwin\\utils\\jasper\\sello.png");
		hInputHeader.put("fondo2", "C:\\edwin\\utils\\jasper\\sello.png");
		hInputHeader.put("logo1", "C:\\edwin\\utils\\jasper\\logo.png");
		hInputHeader.put("logo2", "C:\\edwin\\utils\\jasper\\logo.png");
		hInputHeader.put("logo3", "C:\\edwin\\utils\\jasper\\logo.png");
		hInputHeader.put("logo4", "C:\\edwin\\utils\\jasper\\logo.png");
		hInputHeader.put("logo5", "C:\\edwin\\utils\\jasper\\logo.png");
		hInputHeader.put("logo6", "C:\\edwin\\utils\\jasper\\logo.png");
		jasperFile= "C:\\edwin\\utils\\jasper\\invoice.jasper";
		
		/*Map mapDataSource[] = new HashMap[1];
		HashMap<String, String> hLinea = new HashMap<String, String>();						
		hLinea.put("Linea_Numero", "Hola");
		hLinea.put("Descripcion", "Hola");	
		hLinea.put("Unidad_Medida", "Hola");
		hLinea.put("Codigo_Producto", "Hola");
		hLinea.put("Cantidad",   "10.22");
		hLinea.put("Valor_Unitario_SinIGV", "10.22");
		hLinea.put("Total_Linea_SinIGV", "10.22");
		mapDataSource[0] = hLinea;*/
			//Map<String, Object> mapDataSource=new HashMap<String, Object>();
		
		long id=0;
		Collection<Producto> lineaproducto=(ArrayList<Producto>)productoRepository.findAll();
		 
		Collection<ProductoAlmacen> c=(ArrayList<ProductoAlmacen>)productoAlmacenRepository.findAll();
		Map mapDataSource[] = new HashMap[c.size()];	
		
		int i = 0; 
		for(Producto p : lineaproducto) {			
            id=p.getIdSa();                       
            Collection<ProductoAlmacen>lineas=(ArrayList<ProductoAlmacen>)productoAlmacenRepository.findByCodProducto(id);         
           
    		for (ProductoAlmacen linea : lineas){
    				HashMap<String, String> hLinea = new HashMap<String, String>();    				
    				hLinea.put("Linea_Numero", p.getDescripcion());
    				hLinea.put("Descripcion", p.getDescripcion());	
    				hLinea.put("Unidad_Medida",bigDecimalaString( p.getPrecioCompra()));
    				hLinea.put("Codigo_Producto",p.getCodigoEquivalente());
    				//06/10/16	//hLinea.put("Cantidad",linea.getCantidad().toString());
    				mapDataSource[i] = hLinea;
    				i++;
    		}    		
        }	
		
		JRMapArrayDataSource datasource = new JRMapArrayDataSource(mapDataSource);
		FileInputStream is = new FileInputStream(jasperFile);
		//JasperPrint pdfRender = JasperFillManager.fillReport(is, params,new JREmptyDataSource());
		JasperPrint pdfRender = JasperFillManager.fillReport(is, params, datasource);
		pdfFileName="C:\\edwin\\utils\\jasper\\prueba.pdf";
		
		JasperExportManager.exportReportToPdfFile(pdfRender,	pdfFileName);		
		
		return pdfFileName;
		
	}
	
	
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/document1/pdf")
	public @ResponseBody String todosMarcas() throws Exception{
		
		HashMap<String, Object> hInputHeader = new HashMap<String, Object>();
		String jasperFile = ""; 
		String pdfFileName="";		
												
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("inputHeader", hInputHeader);
		params.put("logoPath","C:\\edwin\\utils\\jasper\\");
		SimpleDateFormat sdf = new SimpleDateFormat( "dd-MM-yyyy");
		hInputHeader.put("Ruc_Emisor", "123456789");
		hInputHeader.put("Numero_Legal", "123456");
		hInputHeader.put("Fecha_Emision", "123456");				
		hInputHeader.put("Monto_Gravado", "12.00");
		hInputHeader.put("Monto_Exonerado", "12.00");
		hInputHeader.put("Monto_Inafecto", "12.00");
		hInputHeader.put("Monto_Gratuito", "12.00");				
		hInputHeader.put("Total_Descuentos", "12.00");				
		hInputHeader.put("IGV", "12.00");
		hInputHeader.put("Monto_Total", "12.00");				
		hInputHeader.put("Cliente_Razon_Social", "123456789");
		hInputHeader.put("Cliente_Ruc", "123456");
		hInputHeader.put("Cliente_Direccion", "123456");
		hInputHeader.put("digestValue", "123456");
		hInputHeader.put("Moneda", "123456");				
		hInputHeader.put("Factura_Asociada", "123456");
		hInputHeader.put("Leyenda", "123456");
		hInputHeader.put("fondo1", "C:\\edwin\\utils\\jasper\\sello.png");
		hInputHeader.put("fondo2", "C:\\edwin\\utils\\jasper\\sello.png");
		hInputHeader.put("logo1", "C:\\edwin\\utils\\jasper\\logo.png");
		hInputHeader.put("logo2", "C:\\edwin\\utils\\jasper\\logo.png");
		hInputHeader.put("logo3", "C:\\edwin\\utils\\jasper\\logo.png");
		hInputHeader.put("logo4", "C:\\edwin\\utils\\jasper\\logo.png");
		hInputHeader.put("logo5", "C:\\edwin\\utils\\jasper\\logo.png");
		hInputHeader.put("logo6", "C:\\edwin\\utils\\jasper\\logo.png");
		jasperFile= "C:\\edwin\\utils\\jasper\\invoice.jasper";
		
		/*long id=0;
		Collection<Producto> lineaproducto=(ArrayList<Producto>)productoRepository.findAll();
		 
		Collection<ProductoAlmacen> c=(ArrayList<ProductoAlmacen>)productoAlmacenRepository.findAll();
		Map mapDataSource[] = new HashMap[c.size()];	
		
		int i = 0; 
		for(Producto p : lineaproducto) {			
            id=p.getId();                       
            Collection<ProductoAlmacen>lineas=(ArrayList<ProductoAlmacen>)productoAlmacenRepository.findByCodProduct(id);         
           
    		for (ProductoAlmacen linea : lineas){
    				HashMap<String, String> hLinea = new HashMap<String, String>();    				
    				hLinea.put("Linea_Numero", p.getDescripcion());
    				hLinea.put("Descripcion", p.getDescripcion());	
    				hLinea.put("Unidad_Medida",bigDecimalaString( p.getPrecioCompra()));
    				hLinea.put("Codigo_Producto",p.getCodigo());
    				hLinea.put("Cantidad",linea.getCantidad().toString());
    				mapDataSource[i] = hLinea;
    				i++;
    		}    		
        }	*/
		
		
		
		long idFamilia=0;
		long idMarca=0;
		long idProducto=0;
		Collection<Producto> lineaproducto=(ArrayList<Producto>)productoRepository.findAll();
		Collection<Categorie> lineafamilia=(ArrayList<Categorie>)familiaRepository.findAll();
		Collection<Marca> lineamarca=(ArrayList<Marca>)marcaRepository.findAll();
		Collection<ProductoAlmacen> lineaproductoalmacen=(ArrayList<ProductoAlmacen>)productoAlmacenRepository.findAll();
		 
		Collection<Producto> c=(ArrayList<Producto>)productoRepository.findAll();
		Map mapDataSource[] = new HashMap[c.size()];	
		
		int i = 0; 
		
		
		
		
		for(Categorie f : lineafamilia) {			
            idFamilia=f.getIdSa();
         for(Marca m : lineamarca){
        	 idMarca=m.getIdSa();
        	 Collection<Producto>lineas=(ArrayList<Producto>)productoRepository.findByCodFamiliaMarca(idFamilia,idMarca,idMarca);        
           for(Producto p : lineas){
        	   idProducto=p.getIdSa();
        	   Collection<ProductoAlmacen>lineass=(ArrayList<ProductoAlmacen>)productoAlmacenRepository.findByCodProducto(idProducto);
        	   for(ProductoAlmacen pa : lineass){
        		   for (Producto linea : lineas){
       				HashMap<String, String> hLinea = new HashMap<String, String>();    				
       				//hLinea.put("Linea_Numero", m.getCodigo());
       				hLinea.put("Descripcion", linea.getDescripcion());	
       				hLinea.put("Unidad_Medida",bigDecimalaString( linea.getPrecioCompra()));
       				hLinea.put("Codigo_Producto",linea.getCodigoEquivalente());
       			//06/10/16	//hLinea.put("Cantidad",pa.getCantidad().toString());
       				mapDataSource[i] = hLinea;
       				i++;
           	   								} 
        	   							}         	           	   
           							}          	         	 
         						}
							}
                 		   		
        	
		
		JRMapArrayDataSource datasource = new JRMapArrayDataSource(mapDataSource);
		FileInputStream is = new FileInputStream(jasperFile);
		//JasperPrint pdfRender = JasperFillManager.fillReport(is, params,new JREmptyDataSource());
		JasperPrint pdfRender = JasperFillManager.fillReport(is, params, datasource);
		pdfFileName="C:\\edwin\\utils\\jasper\\prueba.pdf";
		
		JasperExportManager.exportReportToPdfFile(pdfRender,	pdfFileName);		
		
		return pdfFileName;
		
	}
	
	
	
	
	
	
	
	
	public String bigDecimalaString(BigDecimal monto){
		String montoStr="";
		if(monto!=null){
			montoStr=monto.toString();
		}
		
		return montoStr;
	}
	
	
	@Autowired
	protected ProductoAlmacenRepository productoAlmacenRepository;
	@Autowired
	protected ProductoRepository productoRepository;
	@Autowired
	protected CategorieRepository familiaRepository;
	@Autowired
	protected MarcaRepository marcaRepository;
	
}
