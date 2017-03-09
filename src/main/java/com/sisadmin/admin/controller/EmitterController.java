package com.sisadmin.admin.controller;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sisadmin.admin.dto.EmisorDto;
import com.sisadmin.entity.Emisor;
import com.sisadmin.service.repository.EmisorActual;


@Controller
public class EmitterController {


	
	
	
    
    
   	@RequestMapping(method=RequestMethod.GET, value="/api/v1/emitter")
	@Transactional
	public @ResponseBody EmisorDto getEmitter() {

		 Emisor emitter = EmisorActual.getEmisorActual();
		 EmisorDto result = mapData(emitter);
		 return result;
	}
	
   	private EmisorDto mapData(Emisor in) {
   			EmisorDto emisor = new EmisorDto();
   			emisor.setRuc(in.getRuc());
   			emisor.setRazonSocial(in.getRazonSocial());
   			emisor.setRazonComercial(in.getRazonComercial());
   			emisor.setDireccion(in.getDireccion());
   			emisor.setEmail(in.getEmail());
   			
   			/**
   			emisor.setCalle("Av. Felipe Salaverry 2080");
   			emisor.setUrbanizacion("Jesus Maria");
   			emisor.setDistrito("Jesus Maria");
   			emisor.setDepartamento("Lima");
   			emisor.setPais("PERU");
   			emisor.setResolucion("2067483020234");
   			
   			emisor.setCertificado("D:/demo/certificado/certpap.crt");
   			emisor.setVencimientoCertificado("2016-12-01");
   			emisor.setUsuarioSunat("usudemo");
   			emisor.setPassUsuarioSunat("*************");
   			**/
   					
   			return emisor;
   		
   	}
   	
   	/**
	private EmitterDto mapData(Emisor in) {
		EmitterDto dto = new EmitterDto();
		dto.setAddress(in.getDireccion());
		dto.setIdentification(in.getRuc());
		dto.setName(in.getRazonSocial());
		dto.setBusinessName(in.getRazonComercial());		
		dto.setPathGen(in.getCarpetaEntradaTxt());
		dto.setPathSig(in.getCarpetaUbl());
		dto.setPathAut(in.getCarpetaAprobado());
		dto.setPathArc(in.getCarpetaAprobado());
		dto.setPathRec(in.getCarpetaRecibidoTxt());
		dto.setPathRej(in.getCarpetaRechazado());
		dto.setPathHold(in.getCarpetaRecibidoTxt());
		dto.setPathGuide(emisorActual.getEmisorActual().getCarpetaJasper()); //TODO aca poner carpeta de guias de remision
		dto.setPathJas(in.getCarpetaJasper());		
		return dto;	
	}
	
		
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/emitter/config")
	@Transactional
	public @ResponseBody EmitterConfDto getConfEmitter() throws IOException {
		//initPropertyFile();
		Emisor emitter = emisorService.findOneByIdentification(emisorActual.GlobalId);		
		EmitterConfDto result = convertToDto(emitter);
		return result;
	}
	**/
	
	

/**
	private boolean initPropertyFile() throws IOException{
		InputStream io = null;
		try
		{
			if(properties==null){
				properties = new Properties();
				io = new FileInputStream(System.getProperty("invoicec.context")+"\\docintro.properties");
				properties.load(io);
			}
		}catch(IOException e){
			return false;
		}finally{
			try{
				if(io!=null){
					io.close();
				}	
			}catch(IOException e){
			}
		}
		return true;
	}

	private Properties properties;**/	

//	private Properties properties = PropertyLoader.getDocintroProperties();
	
}

	
	
	


/**
@Autowired
protected EmitterService emitterService;	
@Autowired
protected DocumentService documentService;		
@Autowired
protected PropertyTypeService propertyTypeService;	
@Autowired
protected SisfeReadCertificateService digitalSignatureRetriever;	
@Autowired
protected InvoicecSpaceService spaceChecker;	
@Autowired
protected CurrentEmitter currenEmitter;
**/