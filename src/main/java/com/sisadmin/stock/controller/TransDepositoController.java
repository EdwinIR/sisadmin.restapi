package com.sisadmin.stock.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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

import com.sisadmin.entity.Almacen;
import com.sisadmin.entity.GuiaRemision;
import com.sisadmin.entity.GuiaRemisionDetalle;
import com.sisadmin.entity.Marca;
import com.sisadmin.entity.Producto;
import com.sisadmin.entity.ProductoAlmacen;
import com.sisadmin.entity.TransDeposito;
import com.sisadmin.entity.TransDepositoDetalle;
import com.sisadmin.entity.UnidadMedida;
import com.sisadmin.repository.AlmacenRepository;
import com.sisadmin.repository.GuiaRemisionDetalleRepository;
import com.sisadmin.repository.GuiaRemisionRepository;
import com.sisadmin.repository.MarcaRepository;
import com.sisadmin.repository.ProductoAlmacenRepository;
import com.sisadmin.repository.ProductoRepository;
import com.sisadmin.repository.TransDepositoDetalleRepository;
import com.sisadmin.repository.TransDepositoRepository;
import com.sisadmin.repository.UnidadMedidaRepository;
import com.sisadmin.stock.dto.FilterProductoAlmacenDto;
import com.sisadmin.stock.dto.GuiaRemisionDto;
import com.sisadmin.stock.dto.TransDepositoDetalleDto;
import com.sisadmin.stock.dto.TransDepositoDto;
import com.sisadmin.util.PropertyLoader;

@Controller
public class TransDepositoController {
	
	private void actualizarProductoAlmacen(TransDepositoDetalleDto detalleDto,long idAlmacenOrigen,long idAlmacenDestino){
		ProductoAlmacen productoAlmacenOrigen = (ProductoAlmacen)productoAlmacenRepository.findByCodProductoCodAlmacen(detalleDto.getIdProducto(), idAlmacenOrigen);		
		
		Date fecha = new Date(); 
		long hora = fecha.getTime();
		Timestamp fechahora= new Timestamp(hora);
		
		if(productoAlmacenOrigen!=null){			
			productoAlmacenOrigen.setCantidad(productoAlmacenOrigen.getCantidad().subtract(new BigDecimal(detalleDto.getCantidad())));
			productoAlmacenOrigen.setUsuarioModificacion("UsuarioModificador");
			productoAlmacenOrigen.setFechaModificacion(fechahora);
			productoAlmacenRepository.save(productoAlmacenOrigen);
		}					
	}
	
	private TransDepositoDto leer(TransDeposito tra){
		TransDepositoDto transDto = new TransDepositoDto();
		transDto.setId(tra.getId());
		transDto.setAlmacenDestinoId(0);
		transDto.setDescripcionAlmacenDestino("");
		if(tra.getAlmacenDestino()!=null){
			transDto.setAlmacenDestinoId(tra.getAlmacenDestino().getId());
			transDto.setDescripcionAlmacenDestino(tra.getAlmacenDestino().getDescripcion());
		}
		transDto.setAlmacenOrigenId(0);
		transDto.setDescripcionAlmacenOrigen("");
		if(tra.getAlmacenOrigen()!=null){
			transDto.setAlmacenOrigenId(tra.getAlmacenOrigen().getId());
			transDto.setDescripcionAlmacenOrigen(tra.getAlmacenOrigen().getDescripcion());
		}	
		transDto.setFecha(tra.getFecha());
		transDto.setEstado(tra.getEstado());
		
		for (TransDepositoDetalle detalle : tra.getDetalleTransDeposito()) {
			TransDepositoDetalleDto detalleDto = new TransDepositoDetalleDto();
			detalleDto.setIdProducto(0);
			if(detalle.getProducto()!=null)
			detalleDto.setIdProducto(detalle.getProducto().getIdSa());
			detalleDto.setIdUnidadMedida(0);
			detalleDto.setDescripcionUnidadMedida("");
			if(detalle.getUnidadMedida()!=null){
				detalleDto.setIdUnidadMedida(detalle.getUnidadMedida().getId());
				detalleDto.setDescripcionUnidadMedida(detalle.getUnidadMedida().getDescripcion());
			}
			detalleDto.setCantidad(detalle.getCantidad()); 
			
			transDto.getDetalles().add(detalleDto);
		}		
		return transDto;
	}
	private void finalizarProductoAlmacen(TransDepositoDetalleDto detalleDto,long idAlmacenOrigen,long idAlmacenDestino){	
		Date fecha = new Date(); 
		long hora = fecha.getTime();
		Timestamp fechahora= new Timestamp(hora);
			ProductoAlmacen productoAlmacenDestino = (ProductoAlmacen)productoAlmacenRepository.findByCodProductoCodAlmacen(detalleDto.getIdProducto(), idAlmacenDestino);
			if(productoAlmacenDestino!=null){
				productoAlmacenDestino.setCantidad(productoAlmacenDestino.getCantidad().add(new BigDecimal(detalleDto.getCantidad())));
				productoAlmacenDestino.setUsuarioModificacion("UsuarioModificador");
				productoAlmacenDestino.setFechaModificacion(fechahora);
				productoAlmacenRepository.save(productoAlmacenDestino);
			}else{
				ProductoAlmacen proAlmacen = new ProductoAlmacen();
				Producto producto = (Producto)productoRepository.findOne(detalleDto.getIdProducto());		
				Almacen almacen = (Almacen)almacenRepository.findOne(idAlmacenDestino);	
				proAlmacen.setProducto(producto);
				proAlmacen.setAlmacen(almacen);
				proAlmacen.setCantidad(new BigDecimal(detalleDto.getCantidad()));
				proAlmacen.setStockMinimo(new BigDecimal(1));
				proAlmacen.setStockMaximo(new BigDecimal(1000));
				proAlmacen.setUsuarioCreacion("UsuarioCreador");
				proAlmacen.setFechaCreacion(fechahora);
				productoAlmacenRepository.save(proAlmacen);
			}
		
	}
	private TransDeposito actualizar(TransDepositoDto transDepositoDto){
		Date fecha = new Date(); 
		long hora = fecha.getTime();
		Timestamp fechahora= new Timestamp(hora);
		TransDeposito transDeposito = (TransDeposito)transDepositoRepository.findOne(transDepositoDto.getId());
		transDeposito.setEstado("CONCLUIDO");
		
		for (TransDepositoDetalleDto detalleDto : transDepositoDto.getDetalles()) {
			finalizarProductoAlmacen(detalleDto, transDepositoDto.getAlmacenOrigenId(),transDepositoDto.getAlmacenDestinoId());
		}
		
		return transDeposito;
	}
	
	private TransDeposito nueva(TransDepositoDto transDepositoDto){
		Date fecha = new Date(); 
		long hora = fecha.getTime();
		Timestamp fechahora= new Timestamp(hora);
		
		TransDeposito transDeposito = new TransDeposito();
		
		Almacen almacenOrigen = almacenRepository.findOne(transDepositoDto.getAlmacenOrigenId());
		transDeposito.setAlmacenOrigen(almacenOrigen);
		
		Almacen almacenDestino = almacenRepository.findOne(transDepositoDto.getAlmacenDestinoId());
		transDeposito.setAlmacenDestino(almacenDestino);
		
		transDeposito.setFecha(transDepositoDto.getFecha());
		transDeposito.setFechaCreacion(fechahora);
		transDeposito.setUsuarioCreacion("usuarioCreacion");
		transDeposito.setEstado("PENDIENTE");
		
		for (TransDepositoDetalleDto detalleDto : transDepositoDto.getDetalles()) {
			
			actualizarProductoAlmacen(detalleDto, transDepositoDto.getAlmacenOrigenId(),transDepositoDto.getAlmacenDestinoId());		
			
			TransDepositoDetalle transDepDet = new TransDepositoDetalle();
			Producto prod = productoRepository.findOne(detalleDto.getIdProducto());
			UnidadMedida uni = unidadMedidaRepository.findOne(detalleDto.getIdUnidadMedida());
			transDepDet.setCantidad(detalleDto.getCantidad());
			transDepDet.setProducto(prod);
			transDepDet.setUnidadMedida(uni);
			transDeposito.getDetalleTransDeposito().add(transDepDet);
			transDepDet.setTransDeposito(transDeposito);
		}
		
		return transDeposito;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/transdeposito/add")
	public @ResponseBody String agregarTransDeposito(@RequestBody TransDepositoDto transDepositoDto){
		TransDeposito transDeposito= nueva(transDepositoDto);
		transDepositoRepository.save(transDeposito);
		
		return "OK";
	}
	
	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/transdeposito/finalizar")
	public @ResponseBody String finalizarTransDeposito(@RequestBody TransDepositoDto transDepositoDto){
		TransDeposito transDeposito= actualizar(transDepositoDto);
		transDepositoRepository.save(transDeposito)	;	
		return "OK";
	}
	
	@Transactional
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/transdeposito/list")
	public @ResponseBody List<TransDepositoDto> transDepositoTodos(){
		List<TransDeposito> transDepositos = (List<TransDeposito>) transDepositoRepository.findAll();
		List<TransDepositoDto> transDto = new ArrayList<TransDepositoDto>();
		for (TransDeposito td : transDepositos) {
			transDto.add(leer(td));
		}
		return transDto;		
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/transdeposito/byproducto")
	public @ResponseBody BigDecimal obtenerMarcaPorFamiliaId(@RequestBody FilterProductoAlmacenDto filtro){
		BigDecimal stock = new BigDecimal(0);
		ProductoAlmacen producto = (ProductoAlmacen)productoAlmacenRepository.findByCodProductoCodAlmacen(filtro.getProductoId(), filtro.getAlmacenId());
		if(producto!=null){
			stock = producto.getCantidad();
		}
		return stock;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/guiaremision/add")
	public @ResponseBody String agregarTransDeposito(@RequestBody GuiaRemisionDto guiaRemision){
		GuiaRemision guia = new GuiaRemision();
		Almacen almacenOrigen = (Almacen)almacenRepository.findOne(guiaRemision.getAlmacenOrigenId());
		guia.setAlmacenOrigen(almacenOrigen);
		Almacen almacenDestino = (Almacen)almacenRepository.findOne(guiaRemision.getAlmacenDestinoId());
		guia.setAlmacenDestino(almacenDestino);
		guia.setRucDestinatario(guiaRemision.getRucDestinatario());
		guia.setDenominacionDestinatario(guiaRemision.getDenominacionDestinatario());
		guia.setNumeroGuia(guiaRemision.getNumeroGuia());
		guia.setNombresTransportista(guiaRemision.getNombresTransportista());
		guia.setLicenciaTransportista(guiaRemision.getLicenciaTransportista());
		guia.setMarcaTransporte(guiaRemision.getMarcaTransporte());
		guia.setPlaca(guiaRemision.getPlaca());
		guia.setFechaInicio(guiaRemision.getFechaInicio());
		List<TransDepositoDetalle> trans = (List<TransDepositoDetalle>)transDepositoDetalleRepository.findTransDetalleById(guiaRemision.getTransferenciaId());
		for(TransDepositoDetalle det : trans){			
			GuiaRemisionDetalle guiaDetalle = new GuiaRemisionDetalle();			
			guiaDetalle.setUnidadMedida(det.getUnidadMedida());
			guiaDetalle.setCantidad(det.getCantidad());
			guiaDetalle.setProducto(det.getProducto());			
			guia.getDetalles().add(guiaDetalle);
			guiaDetalle.setGuiaRemision(guia);				
		}
		guiaRemisionRepository.save(guia);			
		return "OK";
	}
	@Transactional
	public GuiaRemision recuperarGuia(GuiaRemision guia,GuiaRemisionDto guiaRemisionDto){		
		
		if(guia!=null){
			Almacen almacenOrigen = (Almacen)almacenRepository.findOne(guiaRemisionDto.getAlmacenOrigenId());
			guia.setAlmacenOrigen(almacenOrigen);
			Almacen almacenDestino = (Almacen)almacenRepository.findOne(guiaRemisionDto.getAlmacenDestinoId());
			guia.setAlmacenDestino(almacenDestino);
			guia.setRucDestinatario("20317082062");
			guia.setDenominacionDestinatario("PLASTICOS 2000 S.R.L.");
			guia.setNumeroGuia(guiaRemisionDto.getNumeroGuia());
			guia.setNombresTransportista(guiaRemisionDto.getNombresTransportista());
			guia.setLicenciaTransportista(guiaRemisionDto.getLicenciaTransportista());
			guia.setMarcaTransporte(guiaRemisionDto.getMarcaTransporte());
			guia.setPlaca(guiaRemisionDto.getPlaca());
			guia.setFechaInicio(guiaRemisionDto.getFechaInicio());
			TransDeposito transDeposito = (TransDeposito)transDepositoRepository.findOne(guiaRemisionDto.getTransferenciaId());
			guia.setTransDeposito(transDeposito);
			List<TransDepositoDetalle> trans = (List<TransDepositoDetalle>)transDepositoDetalleRepository.findTransDetalleById(guiaRemisionDto.getTransferenciaId());
			List<GuiaRemisionDetalle> guiadetalle = (List<GuiaRemisionDetalle>)guiaRemisionDetalleRepository.findByGuiaId(guia.getId());			
			
			guiaRemisionRepository.save(guia);
			return guia;
		}else{
			GuiaRemision nuevaGuia = new GuiaRemision();
			Almacen almacenOrigen = (Almacen)almacenRepository.findOne(guiaRemisionDto.getAlmacenOrigenId());
			nuevaGuia.setAlmacenOrigen(almacenOrigen);
			Almacen almacenDestino = (Almacen)almacenRepository.findOne(guiaRemisionDto.getAlmacenDestinoId());
			nuevaGuia.setAlmacenDestino(almacenDestino);
			nuevaGuia.setRucDestinatario("20317082062");
			nuevaGuia.setDenominacionDestinatario("PLASTICOS 2000 S.R.L.");
			nuevaGuia.setNumeroGuia(guiaRemisionDto.getNumeroGuia());
			nuevaGuia.setNombresTransportista(guiaRemisionDto.getNombresTransportista());
			nuevaGuia.setLicenciaTransportista(guiaRemisionDto.getLicenciaTransportista());
			nuevaGuia.setMarcaTransporte(guiaRemisionDto.getMarcaTransporte());
			nuevaGuia.setPlaca(guiaRemisionDto.getPlaca());
			nuevaGuia.setFechaInicio(guiaRemisionDto.getFechaInicio());
			TransDeposito transDeposito = (TransDeposito)transDepositoRepository.findOne(guiaRemisionDto.getTransferenciaId());
			nuevaGuia.setTransDeposito(transDeposito);
			List<TransDepositoDetalle> trans = (List<TransDepositoDetalle>)transDepositoDetalleRepository.findTransDetalleById(guiaRemisionDto.getTransferenciaId());
			for(TransDepositoDetalle det : trans){			
				GuiaRemisionDetalle guiaDetalle = new GuiaRemisionDetalle();			
				guiaDetalle.setUnidadMedida(det.getUnidadMedida());
				guiaDetalle.setCantidad(det.getCantidad());
				guiaDetalle.setProducto(det.getProducto());		
				Marca marca = (Marca)marcaRepository.findMarcaBycodigo(det.getProducto().getMarca());
				guiaDetalle.setMarca(marca);
				nuevaGuia.getDetalles().add(guiaDetalle);
				guiaDetalle.setGuiaRemision(nuevaGuia);				
			}
			guiaRemisionRepository.save(nuevaGuia);
			return nuevaGuia;
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/transdeposito/pdf/filter", produces="application/octet")
	@Transactional
	public void getPdf(@RequestBody GuiaRemisionDto guiaRemisionDto, HttpServletResponse resp) throws ParseException, IOException{
		
		GuiaRemision guiaRemision = (GuiaRemision)guiaRemisionRepository.findByTransId(guiaRemisionDto.getTransferenciaId());
		
		GuiaRemision guia =recuperarGuia(guiaRemision,guiaRemisionDto);		
		
		InputStream inputStream = null;
		OutputStream outputStream = null;
		String almacen="";	
		try {
			
			
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
	      hInputHeader.put("Fecha_Emision",guia.getFechaInicio());
	      hInputHeader.put("RUC", "20317082062");                    
	      hInputHeader.put("Destinatario", guia.getDenominacionDestinatario());
	      hInputHeader.put("Punto_Partida", guia.getAlmacenOrigen().getDireccion());
	      hInputHeader.put("Punto_Llegada", guia.getAlmacenDestino().getDireccion());
	      hInputHeader.put("Numero_Documento", guia.getNumeroGuia());
	              
	      rutaPdf = properties.getProperty("sisadmin.path.guia.transferencia.archivo");
	      jasperFile = properties.getProperty("sisadmin.path.guia.transferencia.jasper");
	      logo1 = properties.getProperty("sisadmin.path.guia.transferencia.logo1");
	      fondo1 = properties.getProperty("sisadmin.path.guia.transferencia.fondo1");
	      hInputHeader.put("fondo1", fondo1);	              
	      hInputHeader.put("logo1", logo1);
	      Map mapDataSource[] = new HashMap[guia.getDetalles().size()];
	      int i = 0; 
	      for (GuiaRemisionDetalle dto : guia.getDetalles()){
	    	  HashMap<String, String> hLinea = new HashMap<String, String>();                    
	    	  hLinea.put("Producto_Id",dto.getProducto().getCodigoBarra());
	    	  hLinea.put("Descripcion_Producto", dto.getProducto().getDescripcion());  
	    	  hLinea.put("Producto_Marca", dto.getMarca().getDescripcion());
	    	  hLinea.put("Cantidad",dto.getCantidad()+"");
	    	  hLinea.put("UnidadMedida_Producto", dto.getUnidadMedida().getDescripcion()); 
	    	  mapDataSource[i] = hLinea;
	    	  i++;
	      }
	      JRMapArrayDataSource datasource = new JRMapArrayDataSource(mapDataSource);
	      FileInputStream is = new FileInputStream(jasperFile);
	      JasperPrint pdfRender = JasperFillManager.fillReport(is, params, datasource);
	      pdfFileName= rutaPdf+"reporte_guia_remision"+fechaActual()+".pdf";              
	      File archivoBorrar = new File(pdfFileName);
			if (archivoBorrar.exists()){ archivoBorrar.delete(); }
	                    
	        	JasperExportManager.exportReportToPdfFile(pdfRender,pdfFileName);
	        	resp.setContentType("application/octet");
				resp.setHeader("Content-Disposition","attachment; filename=\"" + "mario.pdf" +"\"");
						
				File archivoNuevo = new File(pdfFileName);
				inputStream = new FileInputStream(archivoNuevo);
				outputStream = resp.getOutputStream();
				IOUtils.copy(inputStream, outputStream);
		} catch (Exception e) {
			inputStream.close();
      		outputStream.close();
		}
		
		
      	
      		
            
     }
	
	private String fechaActual(){
		 Date fechaActual = new Date();
		 SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		 return sdf.format(fechaActual);
	}
	
	
	
	private Properties properties = PropertyLoader.getServicesProperties();
		
	@Autowired
	private AlmacenRepository almacenRepository;
	@Autowired
	private ProductoRepository productoRepository;	
	@Autowired
	private UnidadMedidaRepository unidadMedidaRepository;
	@Autowired
	private TransDepositoRepository transDepositoRepository;
	@Autowired
	private TransDepositoDetalleRepository transDepositoDetalleRepository;
	@Autowired
	private ProductoAlmacenRepository productoAlmacenRepository;
	@Autowired
	private GuiaRemisionRepository guiaRemisionRepository;
	@Autowired
	private MarcaRepository marcaRepository;
	@Autowired
	private GuiaRemisionDetalleRepository guiaRemisionDetalleRepository;
}
