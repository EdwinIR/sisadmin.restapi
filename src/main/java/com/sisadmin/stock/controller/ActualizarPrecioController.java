package com.sisadmin.stock.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.sisadmin.compras.dto.ActualizarPrecioFilter;
import com.sisadmin.compras.dto.ListActualizacionPrecioDto;
import com.sisadmin.compras.dto.OrdenCompraFilter;
import com.sisadmin.entity.ActualizarPrecio;
import com.sisadmin.entity.MarcaFamilia;
import com.sisadmin.entity.OrdenCompra;
import com.sisadmin.entity.Producto;
import com.sisadmin.entity.ProductoDetalle;
import com.sisadmin.entity.UnidadMedida;
import com.sisadmin.repository.ActualizarPrecioRepository;
import com.sisadmin.repository.MarcaFamiliaRepository;
import com.sisadmin.repository.ProductoDetalleRepository;
import com.sisadmin.repository.ProductoRepository;

@Controller
public class ActualizarPrecioController {
	private static int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;
    private static int DECIMALS = 2;
    
	@Transactional
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/actualizarprecio/producto")
	public @ResponseBody String  ActualizarPrecio(@RequestBody ListActualizacionPrecioDto lista){
		String mensaje="Error al actualizar Precio";
		Producto producto = (Producto)productoRepository.findByCodigoBarra(lista.getCodigoProducto());	
		
		if(producto.getMarcaFamilia()!=null){
			MarcaFamilia marcaFamilia =(MarcaFamilia)marcaFamiliaRepository.findOne(producto.getMarcaFamilia().getId());		 
			String uniPpal =  producto.getUnidadMedidaPrincipal();
			UnidadMedida u = null;
			if(uniPpal.equalsIgnoreCase("A")){ ProductoDetalle d = producto.getProductoDetalleA(); u=d.getUnidadMedida();}
			if(uniPpal.equalsIgnoreCase("B")){ ProductoDetalle d = producto.getProductoDetalleB(); u=d.getUnidadMedida();}
			if(uniPpal.equalsIgnoreCase("C")){ ProductoDetalle d = producto.getProductoDetalleC(); u=d.getUnidadMedida();}		 		
			
			UnidadMedida uA= null;
			UnidadMedida uB =null;
			UnidadMedida uC =null;
			BigDecimal precioA= new BigDecimal(0);
			BigDecimal precioB= new BigDecimal(0);
			BigDecimal precioC= new BigDecimal(0);
			
			int fisicoPrincipal = u.getFisico();
			
			BigDecimal margenBaseProducto= new BigDecimal(0);	
			margenBaseProducto = lista.getPrecioNuevo().add(marcaFamilia.getMargenBase().multiply(lista.getPrecioNuevo()).divide(new BigDecimal(100))).setScale(2,RoundingMode.CEILING);
			if(producto.getProductoDetalleC() !=null){			
				uC = producto.getProductoDetalleC().getUnidadMedida();			
				BigDecimal porcentaje = new BigDecimal(fisicoPrincipal).divide(new BigDecimal(uC.getFisico())).setScale(2,RoundingMode.CEILING);			
				precioC =(margenBaseProducto.add(marcaFamilia.getMargenC().multiply(margenBaseProducto).divide(new BigDecimal(100)))).divide(porcentaje).setScale(2,RoundingMode.CEILING);
				ProductoDetalle pd = (ProductoDetalle)productoDetalleRepository.findByCodProductoAndUnidadMedida(producto.getIdSa(),uC.getCodigo());
				pd.setPrecio(precioC);
				mensaje="Precio Actualizado";
			}			
			if (producto.getProductoDetalleA() != null){
				uA = producto.getProductoDetalleA().getUnidadMedida();
				BigDecimal porcentaje = new BigDecimal(fisicoPrincipal).divide(new BigDecimal(uA.getFisico())).setScale(2,RoundingMode.CEILING);	
				precioA =(margenBaseProducto.add(marcaFamilia.getMargenA().multiply(margenBaseProducto).divide(new BigDecimal(100)))).divide(porcentaje).setScale(2,RoundingMode.CEILING);
				
				ProductoDetalle pd = (ProductoDetalle)productoDetalleRepository.findByCodProductoAndUnidadMedida(producto.getIdSa(),uA.getCodigo());
				pd.setPrecio(precioA);
				mensaje="Precio Actualizado";
			}			
			if(producto.getProductoDetalleB() != null){
				uB = producto.getProductoDetalleB().getUnidadMedida();
				BigDecimal porcentaje;
				//BigDecimal porcentaje= new BigDecimal(fisicoPrincipal).divide(new BigDecimal(uB.getFisico())).setScale(2,RoundingMode.CEILING);
				porcentaje =validarFormatoMonto( new BigDecimal(fisicoPrincipal).divide(new BigDecimal(uB.getFisico())).toString());
				//porcentaje = validarFormatoMonto(new Integer(fisicoPrincipal).toString());
				
				precioB=(margenBaseProducto.add(marcaFamilia.getMargenB().multiply(margenBaseProducto).divide(new BigDecimal(100)))).divide(porcentaje).setScale(2,RoundingMode.CEILING);			
				ProductoDetalle pd = (ProductoDetalle)productoDetalleRepository.findByCodProductoAndUnidadMedida(producto.getIdSa(),uB.getCodigo());
				pd.setPrecio(precioB);
				mensaje="Precio Actualizado";
			}
			List<ActualizarPrecio> a=(List<ActualizarPrecio>)actualizarPrecioRepository.findByProduct(lista.getDescripcionProducto());
			for(ActualizarPrecio act : a){
				act.setEstado("ACTUALIZADO");
				actualizarPrecioRepository.save(act);
			}			
		}else{
			mensaje="El producto no tiene Margenes";
		}
		return mensaje;
	}
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/reportepia/filter", produces="application/octet")
	@Transactional
	public void reportePIA( @RequestBody ActualizarPrecioFilter actualizarPrecioFilter, HttpServletResponse resp) throws IOException, Exception{
		ArrayList<ListActualizacionPrecioDto> listaAll = new ArrayList<ListActualizacionPrecioDto>();	
		ArrayList<ListActualizacionPrecioDto> listaLimpia = new ArrayList<ListActualizacionPrecioDto>();
		List<ActualizarPrecio> listaPrecio = (List<ActualizarPrecio>)actualizarPrecioRepository.findAll();
		
		for(ActualizarPrecio precio:listaPrecio){
			List<ActualizarPrecio> actualizarPrecio = new ArrayList<ActualizarPrecio>();
			if(actualizarPrecioFilter.getInicioFecha()==null && actualizarPrecioFilter.getFinFecha()==null){
				actualizarPrecio = (List<ActualizarPrecio>)actualizarPrecioRepository.listadoIngresados(precio.getProducto().getDescripcion(),"PENDIENTE");
			}else{
				 actualizarPrecio = (List<ActualizarPrecio>)actualizarPrecioRepository.findByFechas(precio.getProducto().getDescripcion(),actualizarPrecioFilter.getInicioFecha(), actualizarPrecioFilter.getFinFecha());
			}
			ActualizarPrecio oc = actualizarPrecio.get(0);
			ProductoDetalle pd = (ProductoDetalle)productoDetalleRepository.findByCodProductoAndUnidadMedida(oc.getProducto().getIdSa(),oc.getUnidadMedida().getCodigo());
			ListActualizacionPrecioDto lista = new ListActualizacionPrecioDto();
			lista.setId((int)oc.getProducto().getIdSa());
			lista.setFechaInicio(oc.getFechaIngreso());
			lista.setCodigoProducto(oc.getProducto().getCodigoBarra());
			lista.setDescripcionProducto(oc.getProducto().getDescripcion());
			lista.setCodigoUnidadMedida(oc.getUnidadMedida().getCodigo());
			lista.setDescripcionUnidadMedida(oc.getUnidadMedida().getDescripcion());
			lista.setPrecioActual(pd.getPrecio());		
			BigDecimal precioNuevo = new BigDecimal(0);
			precioNuevo = transformarprecio(oc.getProducto(), oc.getPrecioNuevo());
			lista.setPrecioNuevo(precioNuevo);
			listaAll.add(lista);
		}		
		Map<Integer, ListActualizacionPrecioDto> mapLista = new HashMap<Integer, ListActualizacionPrecioDto>(listaAll.size());
		for(ListActualizacionPrecioDto l : listaAll) {
			mapLista.put(l.getId(), l);
		 }
		for(Entry<Integer, ListActualizacionPrecioDto> l : mapLista.entrySet()) {
				 listaLimpia.add(l.getValue());
		}			
		FileInputStream inputStream = new FileInputStream(new File("C:\\softbrill\\sisadmin\\reporteExcel\\actualizarprecio.xlsx"));								
		OPCPackage pkg = OPCPackage.open(inputStream);
		XSSFWorkbook wb = new XSSFWorkbook(pkg);								
		Sheet sheet = wb.getSheetAt(0);
		int rowIndex = 2;	
		for(ListActualizacionPrecioDto ap : listaLimpia){
			Row row = sheet.createRow(rowIndex++);	
			Cell cell0 = row.createCell(0);	cell0.setCellValue(fechaString(ap.getFechaInicio()));
			Cell cell1 = row.createCell(1);	cell1.setCellValue(validarString(ap.getDescripcionProducto()));
			Cell cell2 = row.createCell(2);	cell2.setCellValue(validarString(ap.getDescripcionUnidadMedida()));
			Cell cell3 = row.createCell(3);	cell3.setCellValue(montoString(ap.getPrecioActual()));
			Cell cell4 = row.createCell(4);	cell4.setCellValue(montoString(ap.getPrecioNuevo()));			
		}
		resp.setContentType("application/octet"); resp.setHeader("Content-Disposition","attachment; filename=\"" + "excelprueba.xlsx" +"\"");
		OutputStream outputStream = null;
		try {	outputStream = resp.getOutputStream();
				wb.write(outputStream);
				//IOUtils.copy(inputStream, outputStream);
			} catch (Exception e) {	System.out.println("Error al obtener archivo "); e.printStackTrace();
			} finally {	inputStream.close(); outputStream.close();	}
		
	}
	private BigDecimal transformarprecio(Producto pro,BigDecimal precio){	

		BigDecimal precioNuevo = new BigDecimal(0);
		Producto producto = (Producto)productoRepository.findOne(pro.getIdSa());
		
		if(producto.getMarcaFamilia()!=null){			
			MarcaFamilia marcaFamilia =(MarcaFamilia)marcaFamiliaRepository.findOne(producto.getMarcaFamilia().getId());		 
			String uniPpal =  producto.getUnidadMedidaPrincipal();
			UnidadMedida u = null;
			UnidadMedida uA= null;
			UnidadMedida uB =null;
			UnidadMedida uC =null;
			if(uniPpal.equalsIgnoreCase("A")){ 
				ProductoDetalle d = producto.getProductoDetalleA();
				u=d.getUnidadMedida();
				int fisicoPrincipal = u.getFisico();
				BigDecimal margenBaseProducto= new BigDecimal(0);	
				margenBaseProducto = precio.add(marcaFamilia.getMargenBase().multiply(precio).divide(new BigDecimal(100))).setScale(2,RoundingMode.CEILING);
				uA = producto.getProductoDetalleA().getUnidadMedida();
				BigDecimal porcentaje = new BigDecimal(fisicoPrincipal).divide(new BigDecimal(uA.getFisico())).setScale(2,RoundingMode.CEILING);	
				precioNuevo =(margenBaseProducto.add(marcaFamilia.getMargenA().multiply(margenBaseProducto).divide(new BigDecimal(100)))).divide(porcentaje).setScale(2,RoundingMode.CEILING);
				return precioNuevo;
			}
			if(uniPpal.equalsIgnoreCase("B")){ 
				ProductoDetalle d = producto.getProductoDetalleB(); 
				u=d.getUnidadMedida();
				int fisicoPrincipal = u.getFisico();
				BigDecimal margenBaseProducto= new BigDecimal(0);	
				margenBaseProducto = precio.add(marcaFamilia.getMargenBase().multiply(precio).divide(new BigDecimal(100))).setScale(2,RoundingMode.CEILING);
				uB = producto.getProductoDetalleB().getUnidadMedida();
				BigDecimal porcentaje;
				porcentaje = new BigDecimal(fisicoPrincipal).divide(new BigDecimal(uB.getFisico()));
				precioNuevo=(margenBaseProducto.add(marcaFamilia.getMargenB().multiply(margenBaseProducto).divide(new BigDecimal(100)))).divide(porcentaje,RoundingMode.CEILING);
			
			}
			if(uniPpal.equalsIgnoreCase("C")){ 				
				ProductoDetalle d = producto.getProductoDetalleC();
				u=d.getUnidadMedida();
				int fisicoPrincipal = u.getFisico();
				BigDecimal margenBaseProducto= new BigDecimal(0);	
				margenBaseProducto = precio.add(marcaFamilia.getMargenBase().multiply(precio).divide(new BigDecimal(100))).setScale(2,RoundingMode.CEILING);
				uC = producto.getProductoDetalleC().getUnidadMedida();			
				BigDecimal porcentaje = new BigDecimal(fisicoPrincipal).divide(new BigDecimal(uC.getFisico())).setScale(2,RoundingMode.CEILING);			
				precioNuevo =(margenBaseProducto.add(marcaFamilia.getMargenC().multiply(margenBaseProducto).divide(new BigDecimal(100)))).divide(porcentaje).setScale(2,RoundingMode.CEILING);
				
			}		
		}
		return precioNuevo;
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
	private String montoString(BigDecimal monto){
		String retorno = "";
		if(monto != null) return monto.toString();
		return retorno;
	}
	public BigDecimal validarFormatoMonto(String s){            
        String sAbsoluto = s; 
        BigDecimal monto = null;
        try {
            if(sAbsoluto.equals("")) {
            	monto = new BigDecimal("0.00"); 
            }else { 
            	monto = new BigDecimal(sAbsoluto);
            	monto = redondeo(monto);
            }
        } catch(Exception e){ 
        	e.printStackTrace();
        }
        return monto;
    }
	public BigDecimal redondeo(BigDecimal monto){
		return monto.setScale(DECIMALS, ROUNDING_MODE); 
	}



	
	@Autowired
	ProductoRepository productoRepository;
	@Autowired
	ProductoDetalleRepository productoDetalleRepository;
	@Autowired
	MarcaFamiliaRepository marcaFamiliaRepository;
	@Autowired
	ActualizarPrecioRepository actualizarPrecioRepository;
}
