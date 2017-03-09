package com.sisadmin.admin.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sisadmin.admin.dto.CuentaContableDto;
import com.sisadmin.admin.dto.CuentaDivisionariaDto;
import com.sisadmin.entity.CondicionPago;
import com.sisadmin.entity.CuentaContable;
import com.sisadmin.entity.CuentaDivisionaria;
import com.sisadmin.entity.FormaPago;
import com.sisadmin.entity.MarcaFamilia;
import com.sisadmin.entity.Moneda;
import com.sisadmin.entity.Producto;
import com.sisadmin.entity.ProductoAlmacen;
import com.sisadmin.entity.PuestoTrabajo;
import com.sisadmin.entity.TipoDocumento;
import com.sisadmin.entity.TipoTrabajador;
import com.sisadmin.entity.Ubicacion;
import com.sisadmin.entity.Zona;
import com.sisadmin.repository.CondicionPagoRepository;
import com.sisadmin.repository.CuentaContableRepository;
import com.sisadmin.repository.CuentaDivisionariaRepository;
import com.sisadmin.repository.FormaPagoRepository;
import com.sisadmin.repository.MarcaFamiliaRepository;
import com.sisadmin.repository.MonedaRepository;
import com.sisadmin.repository.ProductoAlmacenRepository;
import com.sisadmin.repository.ProductoRepository;
import com.sisadmin.repository.PuestoTrabajoRepository;
import com.sisadmin.repository.TipoDocumentoRepository;
import com.sisadmin.repository.TipoTrabajadorRepository;
import com.sisadmin.repository.UbicacionRepository;
import com.sisadmin.repository.ZonaRepository;
import com.sisadmin.stock.dto.ComboProductoDto;
import com.sisadmin.stock.dto.FilterComboProductoDto;
import com.sisadmin.stock.dto.ProductoAlmacenDto;
import com.sisadmin.stock.dto.ZonaDto;

@Controller
public class ComboController {
	
	

	@RequestMapping(method=RequestMethod.GET, value="/api/v1/relacionseis/list")
	public @ResponseBody List<CuentaContableDto> todasRelacionesSeis(){
    		List <CuentaContableDto> ldoctypes = new ArrayList<CuentaContableDto>();
			List<CuentaContable> lCuentaContable = cuentaContableRepository.findByCodTabla("6");	
			for (CuentaContable cuentaContable : lCuentaContable) {
				CuentaContableDto docType = new CuentaContableDto();
				docType.setId(cuentaContable.getId());
				docType.setDescripcion(cuentaContable.getDescripcion());
				ldoctypes.add(docType);
			}
			return ldoctypes;
    }
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/relacionnueve/list")
	public @ResponseBody List<CuentaContableDto> todasRelacionesNueves(){
    		List <CuentaContableDto> ldoctypes = new ArrayList<CuentaContableDto>();
			List<CuentaContable> lCuentaContable = cuentaContableRepository.findByCodTabla("9");	
			for (CuentaContable cuentaContable : lCuentaContable) {
				CuentaContableDto docType = new CuentaContableDto();
				docType.setId(cuentaContable.getId());
				docType.setDescripcion(cuentaContable.getDescripcion());
				ldoctypes.add(docType);
			}
			return ldoctypes;
    }
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/relacion79/list")
	public @ResponseBody List<CuentaContableDto> todasRelaciones79(){
    		List <CuentaContableDto> ldoctypes = new ArrayList<CuentaContableDto>();
			List<CuentaContable> lCuentaContable = cuentaContableRepository.findByCodTabla("79");	
			for (CuentaContable cuentaContable : lCuentaContable) {
				CuentaContableDto docType = new CuentaContableDto();
				docType.setId(cuentaContable.getId());
				docType.setDescripcion(cuentaContable.getDescripcion());
				ldoctypes.add(docType);
			}
			return ldoctypes;
    }
	

	@RequestMapping(method=RequestMethod.GET, value="/api/v1/relacion9/list")
	public @ResponseBody List<CuentaDivisionariaDto> todasRelaciones9(){
    		List <CuentaDivisionariaDto> ldoctypes = new ArrayList<CuentaDivisionariaDto>();
			List<CuentaDivisionaria> lCuentaContable = cuentaDivisionariaRepository.findByCodTabla("9");	
			for (CuentaDivisionaria cuentaContable : lCuentaContable) {
				CuentaDivisionariaDto docType = new CuentaDivisionariaDto();
				docType.setId(cuentaContable.getId());
				docType.setDescripcion(cuentaContable.getDescripcion());
				ldoctypes.add(docType);
			}
			return ldoctypes;
    }

	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/zona/byalmacen")
	public @ResponseBody List<ZonaDto> obtenerZonaPorAlmacenId(@RequestBody long almacenId){
		List<Zona> zonas = new ArrayList<Zona>();
		zonas = zonaRepository.findZonaByAlmacenId(almacenId);
		List<ZonaDto> zonasDtos = new ArrayList<ZonaDto>();
		for (Zona zona : zonas) {
			zonasDtos.add(convertToDto(zona));
		}
		return zonasDtos;
	}	

	private ZonaDto convertToDto(Zona in) {
		ZonaDto dto = new ZonaDto();
		dto.setId(in.getId());
		//dto.setCodigo(in.getCodigo());
		dto.setAlmacenId(in.getAlmacen().getId());
		dto.setDescripcion(in.getDescripcion());
		dto.setDescripcionAlmacen(in.getAlmacen().getDescripcion());
		return dto;
	}
	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/ubicacion/byzona")
	public @ResponseBody List<Ubicacion> obtenerUbicacionesPorZonaId(@RequestBody long zonaId){		
		List<Ubicacion> ubicaciones = ubicacionRepository.findUbicacionByZonaId(zonaId);
		return ubicaciones;
	}
	
	/*@RequestMapping(method=RequestMethod.POST, value="/api/v1/productoalmacen/byfamiliamarca")
	public @ResponseBody List<Producto> obtenerProductoPorMarcaIdYFamiliaId(@RequestBody ProductoAlmacenDto  productoAlmacenDto){
		List<Producto> productos = new ArrayList<Producto>();
		productos = productoRepository.findByCodFamiliaMarca(productoAlmacenDto.getFamiliaId(),productoAlmacenDto.getMarcaId(),productoAlmacenDto.getUnidadMedidaId());
		return productos;
	}*/
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/productoalmacen/bycodigoproducto")
	public @ResponseBody List<ProductoAlmacen> obtenerProductoAlmacen(@RequestBody Long id){
		List<ProductoAlmacen> productos = new ArrayList<ProductoAlmacen>();
		productos = productoAlmacenRepository.findByCodProducto(id);		
		return productos;
	}

	@RequestMapping(method=RequestMethod.POST, value="/api/v1/marca/byfamilia")
	public @ResponseBody List<MarcaFamilia> obtenerMarcaPorFamiliaId(@RequestBody long familiaId){
		List<MarcaFamilia> marcasFamilia = new ArrayList<MarcaFamilia>();
		marcasFamilia = marcaFamiliaRepository.findByFamiliaId(familiaId);
		return marcasFamilia;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/producto/bymarcafamilia")
	public @ResponseBody List<ComboProductoDto> productosByMarcaAndFamilia(@RequestBody  FilterComboProductoDto filtro){
		
		MarcaFamilia marcaFamilia = marcaFamiliaRepository.findByIdMarcaAndIdFamilia(filtro.getMarcaId(), filtro.getFamiliaId());
		List<Producto> productos = (List<Producto>)productoRepository.findByCodFamiliaMarcas(marcaFamilia.getFamilia().getIdSa(),marcaFamilia.getMarca().getIdSa());
		List<ComboProductoDto> listaProdDto = new ArrayList<ComboProductoDto>();
		for(Producto producto : productos){
				ComboProductoDto dto = new ComboProductoDto();
				dto.setId(producto.getIdSa());
				dto.setDescripcion(producto.getDescripcion());
				listaProdDto.add(dto);	
		}
		return listaProdDto;
		
		
		
		
		/**
		List<MarcaFamilia> marcasFamilia = new ArrayList<MarcaFamilia>();
		marcasFamilia = marcaFamiliaRepository.findByFamiliaId(familiaId);
		return marcasFamilia;**/
	}
	
	
	
	
	
	
	
	
	
	


	@RequestMapping(method=RequestMethod.GET, value="/api/v1/condicionpago/list")
	public @ResponseBody List<CondicionPago> todasCondicionesPagos(){
				return (List<CondicionPago>) condicionPagoRepository.findAll();
	}

	@RequestMapping(method=RequestMethod.GET, value="/api/v1/formapago/list")
	public @ResponseBody List<FormaPago> todasFormasPagos(){
				return (List<FormaPago>) formaPagoRepository.findAll();
	}

	@RequestMapping(method=RequestMethod.GET, value="/api/v1/moneda/list")
	public @ResponseBody List<Moneda> todasMonedas(){
				return (List<Moneda>) monedaRepository.findAll();
	}

	@RequestMapping(method=RequestMethod.GET, value="/api/v1/tipodocumento/list")
	public @ResponseBody List<TipoDocumento> todosTiposDocumentos(){
				return (List<TipoDocumento>) tipoDocumentoRepository.findAll();
	}

	@RequestMapping(method=RequestMethod.GET, value="/api/v1/puestotrabajo/list")
	public @ResponseBody List<PuestoTrabajo> todosPuestosTrabajos(){
				return (List<PuestoTrabajo>) puestoTrabajoRepository.findAll();
	}
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/tipotrabajador/list")
	public @ResponseBody List<TipoTrabajador> todosTiposTrabajadores(){
				return (List<TipoTrabajador>) tipoTrabajadorRepository.findAll();
	}


	
		
	
	@Autowired
	protected CuentaDivisionariaRepository cuentaDivisionariaRepository;
	@Autowired
	protected CuentaContableRepository cuentaContableRepository;	
	@Autowired
	protected ZonaRepository zonaRepository;
	@Autowired
	protected UbicacionRepository ubicacionRepository;
	@Autowired
	protected MarcaFamiliaRepository marcaFamiliaRepository;
	@Autowired
	protected CondicionPagoRepository condicionPagoRepository;
	@Autowired
	protected FormaPagoRepository formaPagoRepository;
	@Autowired
	protected MonedaRepository monedaRepository;
	@Autowired
	protected TipoDocumentoRepository tipoDocumentoRepository;	
	@Autowired
	protected ProductoAlmacenRepository productoAlmacenRepository;
	@Autowired
	protected ProductoRepository productoRepository;
	@Autowired
	protected PuestoTrabajoRepository puestoTrabajoRepository;
	@Autowired
	protected TipoTrabajadorRepository tipoTrabajadorRepository;

	
	
	
}




/*@RequestMapping(method=RequestMethod.GET, value="/api/v1/cliente/list")
public @ResponseBody List<ClienteDto> getAll(){
			List<Cliente> customers = (List<Cliente>) clienteRepository.findAll();
			List<ClienteDto> customerDtos = new ArrayList<ClienteDto>();
			for (Cliente customer : customers) {
								customerDtos.add(convertToDto(customer));
			}
			return customerDtos;
}

@Autowired
protected ClienteRepository clienteRepository;

private ClienteDto convertToDto(Cliente in) {
	ClienteDto dto = new ClienteDto();
	dto.setIdentificador(in.getIdentificador());
	dto.setRazonSocial(in.getIdentificador());
	dto.setTipoDocumento(in.getTipoDocumento());
	return dto;
}*/

/**
@RequestMapping(method=RequestMethod.GET, value="/api/v1/combo/customer/list")
public @ResponseBody List<CustomerComboDto> getAll(){
			List<Cliente> customers = (List<Cliente>) clienteRepository.findAll();
			List<CustomerComboDto> customerDtos = new ArrayList<CustomerComboDto>();
			for (Cliente customer : customers) {
								customerDtos.add(convertToDto(customer));
			}
			return customerDtos;
}

@RequestMapping(method=RequestMethod.GET, value="/api/v1/combo/emitter/list")
public @ResponseBody List<EmitterComboDto> emitters() {
	List<Emisor> emitters = (List<Emisor>) emisorRepository.findAll();
	List<EmitterComboDto> emitterDtos = new ArrayList<EmitterComboDto>();
	for (Emisor emitter : emitters) {
						emitterDtos.add(convertToDto(emitter));
	}
	return emitterDtos;		
}

@RequestMapping(method=RequestMethod.GET, value="/api/v1/combo/role/list")
public @ResponseBody List<Role> getRoles() {
		return (List<Role>) roleService.findAll();
}


@RequestMapping(method=RequestMethod.GET, value="/api/v1/combo/documenttype/list")
public @ResponseBody List<DocumentTypeComboDto> getAllTypes(){
		List <DocumentTypeComboDto> ldoctypes = new ArrayList<DocumentTypeComboDto>();
		List<General> lGeneral = generalService.findByCodTabla("4");		
		for (General general : lGeneral) {
			DocumentTypeComboDto docType = new DocumentTypeComboDto();
			docType.setTypeId(general.getValue());
			docType.setDescr(general.getDescripcion());
			ldoctypes.add(docType);
		}
		return ldoctypes;
}


@RequestMapping(method=RequestMethod.GET, value="/api/v1/combo/resumenbaja/list")
public @ResponseBody List<DocumentTypeComboDto> obtenerTipoResumenBaja(){
		List <DocumentTypeComboDto> ldoctypes = new ArrayList<DocumentTypeComboDto>();
		List<General> lGeneral = generalService.findByCodTabla("5");		
		for (General general : lGeneral) {
			DocumentTypeComboDto docType = new DocumentTypeComboDto();
			docType.setTypeId(general.getValue());
			docType.setDescr(general.getDescripcion());
			ldoctypes.add(docType);
		}
		return ldoctypes;    	
}


@RequestMapping(method=RequestMethod.GET, value="/api/v1/combo/idtype/list")
public @ResponseBody List<IdentificationTypeInfo> getIdentificationTypes() {
		List <IdentificationTypeInfo> lIDTypes = new ArrayList<IdentificationTypeInfo>();
		List<General> lGeneral = generalService.findByCodTabla("95");			
		for (General general : lGeneral) {
					IdentificationTypeInfo idType = new IdentificationTypeInfo();
					idType.setCodigo(general.getValue());
					idType.setDescripcion(general.getDescripcion());				
					lIDTypes.add(idType);
		}
		return lIDTypes;
}	

@RequestMapping(method=RequestMethod.GET, value="/api/v1/combo/usertype/list")
public @ResponseBody List <IdentificationTypeInfo>  getUserTypes() {
	List <IdentificationTypeInfo> listUserType = new ArrayList<IdentificationTypeInfo>();
	List<General> lGeneral = generalService.findByCodTabla("1");
	for (General general : lGeneral) {
		IdentificationTypeInfo idType = new IdentificationTypeInfo();
		idType.setCodigo(general.getValue());
		idType.setDescripcion(general.getDescripcion());
		
		listUserType.add(idType);
	}
	return listUserType;
}		

@RequestMapping(method=RequestMethod.GET, value="/api/v1/combo/currencytype/list")
public @ResponseBody List<IdentificationTypeInfo> getCurrencyTypes() {
		List <IdentificationTypeInfo> lCurrencyTypes = new ArrayList<IdentificationTypeInfo>();
		List<General> lGeneral = generalService.findByCodTabla("2");		
		for (General general : lGeneral) {
			IdentificationTypeInfo idType = new IdentificationTypeInfo();
			idType.setCodigo(general.getValue());
			idType.setDescripcion(general.getDescripcion());
			
			lCurrencyTypes.add(idType);
		}
		return lCurrencyTypes;
}		
	

@RequestMapping(method=RequestMethod.GET, value="/api/v1/combo/filial/list")
public @ResponseBody List<FilialDto> getFilial() {
	List<Sede> customers = (List<Sede>) sedeRepository.findAll();
	List<FilialDto> customerDtos = new ArrayList<FilialDto>();
	for (Sede customer : customers) {
						customerDtos.add(convertToDto(customer));
	}
	return customerDtos;
}

private CustomerComboDto convertToDto(Cliente in) {
			CustomerComboDto dto = new CustomerComboDto();
			dto.setId(in.getId());
			dto.setName(in.getRazonSocial());
			dto.setIdentification(in.getRuc());
			return dto;
}

private EmitterComboDto convertToDto(Emisor in) {
			EmitterComboDto dto = new EmitterComboDto();
			dto.setId(in.getId());
			dto.setName(in.getRazonSocial());
			dto.setIdentification(in.getRuc());								
			return dto;
}


private FilialDto convertToDto(Sede in) {
		FilialDto dto = new FilialDto();
		dto.setId(in.getId());
		dto.setIdentification(in.getCodigo());
		dto.setDescription(in.getNombre());
		return dto;
}


@Autowired
protected ClienteRepository clienteRepository;	
@Autowired
protected EmisorRepository emisorRepository;
@Autowired
protected SedeRepository sedeRepository;	
@Autowired
protected GeneralService generalService;
@Autowired
protected RoleService roleService;

**/


/**
@Autowired
protected EmitterService emitterService;
@Autowired
protected CustomerService customerService;
@Autowired
protected CompanyFilialService filialService;	
@Autowired
protected SerieService serieService;

@RequestMapping(method=RequestMethod.GET, value="/api/v1/combo/frequency/list")
public @ResponseBody List<IdentificationTypeInfo> getFrequency() {
	List <IdentificationTypeInfo> lperiodicity = new ArrayList<IdentificationTypeInfo>();
	List<General> lGeneral = generalService.findByCodTabla("3");		
	for (General general : lGeneral) {
		IdentificationTypeInfo idType = new IdentificationTypeInfo();
		idType.setCodigo(general.getValue());
		idType.setDescripcion(general.getDescripcion());
		lperiodicity.add(idType);
	}
	return lperiodicity;
}


**/


/**
@RequestMapping(method=RequestMethod.GET, value="/api/v1/combo/serie/boleta/list")
public @ResponseBody List<Serie> getSeriesBoleta() {
	return (List<Serie>) serieService.obtenerSerieBoletas();
}

@RequestMapping(method=RequestMethod.GET, value="/api/v1/combo/serie/factura/list")
public @ResponseBody List<Serie> getSeriesFactura() {
	return (List<Serie>) serieService.obtenerSerieFacturas();
}
**/

/**
private DocumentTypeComboDto convertToDto(DocumentType in) {
		DocumentTypeComboDto dto = new DocumentTypeComboDto();
		dto.setTypeId(in.getTypeId());
		dto.setDescr(in.getDescr());
		return dto;
}**/
/**    @RequestMapping(method=RequestMethod.GET, value="/api/v1/combo/documenttype/list")
public @ResponseBody List<DocumentTypeComboDto> getAllTypes(){		
		List<DocumentType> documentTypes = (List<DocumentType>) documentTypeService.findAll();
		List<DocumentTypeComboDto> documentTypeDtos = new ArrayList<DocumentTypeComboDto>();
		for (DocumentType documentType : documentTypes) {
								documentTypeDtos.add(convertToDto(documentType));
		}
		return documentTypeDtos;
}
**/

