package com.sisadmin.api.controller;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sisadmin.compras.dto.OrdenCompraDto;
import com.sisadmin.entity.OrdenCompra;
import com.sisadmin.repository.AlmacenRepository;
import com.sisadmin.repository.AsientoDiarioRepository;
import com.sisadmin.repository.ClienteRepository;
import com.sisadmin.repository.CondicionPagoRepository;
import com.sisadmin.repository.DepartamentoRepository;
import com.sisadmin.repository.EmpresaRepository;
import com.sisadmin.repository.FacturaRepository;
import com.sisadmin.repository.CategorieRepository;
import com.sisadmin.repository.FormaPagoRepository;
import com.sisadmin.repository.MarcaRepository;
import com.sisadmin.repository.ModeloRepository;
import com.sisadmin.repository.MonedaRepository;
import com.sisadmin.repository.OperacionProveedorRepository;
import com.sisadmin.repository.OrdenCompraRepository;
import com.sisadmin.repository.ParteIngresoAlmacenRepository;
import com.sisadmin.repository.PlanCuentaRepository;
import com.sisadmin.repository.ProductoAlmacenRepository;
import com.sisadmin.repository.ProductoRepository;
import com.sisadmin.repository.PuestoTrabajoRepository;
import com.sisadmin.repository.Relacion979Repository;
import com.sisadmin.repository.RelacionSeisNueveRepository;
import com.sisadmin.repository.SedeRepository;
import com.sisadmin.repository.SubCentroCostoRepository;
import com.sisadmin.repository.TablaGeneralRepository;
import com.sisadmin.repository.TipoCambioRepository;
import com.sisadmin.repository.TipoDocumentoRepository;
import com.sisadmin.repository.TrabajadorRepository;
import com.sisadmin.repository.UbicacionRepository;
import com.sisadmin.repository.VendedorRepository;
import com.sisadmin.repository.ZonaRepository;





@Controller
public class ABMController {
	public static Date f= new Date();
	public static long h=f.getTime();
	public static Timestamp fh= new Timestamp(h);
	/**Afp**/ 

	/**Almacen**/
	/*
	*/
	

	

		
	
	


	

	/**Factura**/
	
	
	/*
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/factura")	   
	public @ResponseBody String borrarFactura(@RequestParam(value="id") Long id){
				facturaRepository.delete(id);
				return "Ok";				
	}		
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/factura/add")
	public @ResponseBody String agregarFactura(@RequestBody FacturaDto facturaDto){
				Factura nuevaFactura = new Factura();			
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				nuevaFactura.setNumeroInterno(facturaDto.getNumeroInterno());
				nuevaFactura.setFechaRegistro(facturaDto.getFechaRegistro());
				nuevaFactura.setFechaEmision(facturaDto.getFechaEmision());
				nuevaFactura.setFechaVencimiento(facturaDto.getFechaVencimiento());
				TipoDocumento tipodocumento =(TipoDocumento)tipoDocumentoRepository.findOne(facturaDto.getTipoDocumentoCodigo());
				nuevaFactura.setTipoDocumento(tipodocumento);
				nuevaFactura.setSerie(facturaDto.getSerie());
				nuevaFactura.setNumeracion(facturaDto.getNumeracion());
				Moneda tipomoneda=(Moneda)monedaRepository.findOne(facturaDto.getTipoMonedaCodigo());
				nuevaFactura.setTipoMoneda(tipomoneda);
				nuevaFactura.setRucProveedor(facturaDto.getRucProveedor());
				nuevaFactura.setRazonSocialProveedor(facturaDto.getRazonSocialProveedor());
				nuevaFactura.setDireccionProveedor(facturaDto.getDireccionProveedor());
				
				nuevaFactura.setGlosa(facturaDto.getGlosa());
				nuevaFactura.setOtrosCargos(facturaDto.getOtrosCargos());
				nuevaFactura.setBaseImponible(facturaDto.getBaseImponible());
				nuevaFactura.setTasaIgv(facturaDto.getTasaIgv());
				nuevaFactura.setIgv(facturaDto.getIgv());
				nuevaFactura.setTotal(facturaDto.getTotal());	
				nuevaFactura.setUsuarioCreacion("UsuarioCreador");
				nuevaFactura.setFechaCreacion(fechahora);				
				facturaRepository.save(nuevaFactura);				
				return "OK";
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/factura/update")
	public @ResponseBody String actualizarFactura(@RequestBody FacturaDto facturaParam){
				Factura facturaRepo = (Factura)facturaRepository.findOne(facturaParam.getId());
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				facturaRepo.setNumeroInterno(facturaParam.getNumeroInterno());
				facturaRepo.setFechaRegistro(facturaParam.getFechaRegistro());
				facturaRepo.setFechaEmision(facturaParam.getFechaEmision());
				facturaRepo.setFechaVencimiento(facturaParam.getFechaVencimiento());
				TipoDocumento tipodocumento =(TipoDocumento)tipoDocumentoRepository.findOne(facturaParam.getTipoDocumentoCodigo());
				facturaRepo.setTipoDocumento(tipodocumento);
				facturaRepo.setSerie(facturaParam.getSerie());
				facturaRepo.setNumeracion(facturaParam.getNumeracion());
				Moneda tipomoneda=(Moneda)monedaRepository.findOne(facturaParam.getTipoMonedaCodigo());
				facturaRepo.setTipoMoneda(tipomoneda);
				facturaRepo.setRucProveedor(facturaParam.getRucProveedor());
				facturaRepo.setRazonSocialProveedor(facturaParam.getRazonSocialProveedor());
				facturaRepo.setDireccionProveedor(facturaParam.getDireccionProveedor());
				
				facturaRepo.setGlosa(facturaParam.getGlosa());
				facturaRepo.setOtrosCargos(facturaParam.getOtrosCargos());
				facturaRepo.setBaseImponible(facturaParam.getBaseImponible());
				facturaRepo.setTasaIgv(facturaParam.getTasaIgv());
				facturaRepo.setIgv(facturaParam.getIgv());
				facturaRepo.setTotal(facturaParam.getTotal());
				facturaRepo.setUsuarioModificacion("UsuarioModificador");
				facturaRepo.setFechaModificacion(fechahora);
				facturaRepository.save(facturaRepo);				
				return "OK";
	}
	*/
	

	/**LetraPagarDto**/
		
	
	/**MarcaFamilia**/
	/*private MarcaFamiliaDto convertToDto(MarcaFamilia in) {
		MarcaFamiliaDto dto = new MarcaFamiliaDto();
		dto.setId(in.getId());
		dto.setMargenBase(in.getMargenBase());
		dto.setMargenA(in.getMargenA());
		dto.setMargenB(in.getMargenB());
		dto.setMargenC(in.getMargenC());
		dto.setDescripcionMarca(in.getMarca().getDescripcion());
		dto.setDescripcionFamilia(in.getFamilia().getDescripcion());
		return dto;
	}*/
	/*
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/marcafamilia/list")
	public @ResponseBody List<MarcaFamiliaDto> todasMarcaFamilias(){
				List<MarcaFamilia> marcafamilias = (List<MarcaFamilia>) marcaFamiliaRepository.findAll();
				List<MarcaFamiliaDto> marcafamiliaDtos = new ArrayList<MarcaFamiliaDto>();
				for (MarcaFamilia marcafamilia : marcafamilias) {
					marcafamiliaDtos.add(convertToDto(marcafamilia));
				}
				return marcafamiliaDtos;
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/marcafamilia")	   
	public @ResponseBody String borrarMarcaFamilia(@RequestParam(value="id") Long id){
				marcaFamiliaRepository.delete(id);
				return "Ok";				
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/marcafamilia/add")
	public @ResponseBody String agregarMarcaFamilia(@RequestBody MarcaFamiliaDto marcaFamiliaDto){
				MarcaFamilia nuevaMarcaFamilia = new MarcaFamilia();
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				
				Marca marca = (Marca)marcaRepository.findOne(marcaFamiliaDto.getMarcaId());
				nuevaMarcaFamilia.setMarca(marca);				
				Familia familia = (Familia)familiaRepository.findOne(marcaFamiliaDto.getFamiliaId());
				nuevaMarcaFamilia.setFamilia(familia);
				nuevaMarcaFamilia.setMargenBase(marcaFamiliaDto.getMargenBase());
				nuevaMarcaFamilia.setMargenA(marcaFamiliaDto.getMargenA());
				nuevaMarcaFamilia.setMargenB(marcaFamiliaDto.getMargenB());
				nuevaMarcaFamilia.setMargenC(marcaFamiliaDto.getMargenC());				
				nuevaMarcaFamilia.setUsuarioCreacion("UsuarioCreador");
				nuevaMarcaFamilia.setFechaCreacion(fechahora);				
				marcaFamiliaRepository.save(nuevaMarcaFamilia);				
				return "OK";
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/marcafamilia/update")
	public @ResponseBody String actualizarMarcaFamilia(@RequestBody MarcaFamiliaDto marcaFamiliaParam){
				MarcaFamilia marcaFamiliaRepo = (MarcaFamilia)marcaFamiliaRepository.findOne(marcaFamiliaParam.getId());
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				
				Marca marca = (Marca)marcaRepository.findOne(marcaFamiliaParam.getMarcaId());
				marcaFamiliaRepo.setMarca(marca);				
				Familia familia = (Familia)familiaRepository.findOne(marcaFamiliaParam.getFamiliaId());
				marcaFamiliaRepo.setFamilia(familia);
				marcaFamiliaRepo.setMargenBase(marcaFamiliaParam.getMargenBase());
				marcaFamiliaRepo.setMargenA(marcaFamiliaParam.getMargenA());
				marcaFamiliaRepo.setMargenB(marcaFamiliaParam.getMargenB());
				marcaFamiliaRepo.setMargenC(marcaFamiliaParam.getMargenC());				
				marcaFamiliaRepo.setUsuarioModificacion("UsuarioModificador");
				marcaFamiliaRepo.setFechaModificacion(fechahora);	
				marcaFamiliaRepository.save(marcaFamiliaRepo);				
				return "OK";
	}*/
	
	
	/**OrdenCompraDto**/
	private OrdenCompraDto convertToDto(OrdenCompra in) {
		OrdenCompraDto dto = new OrdenCompraDto();
		dto.setId(in.getId());
		dto.setCodigo(in.getCodigo());
		dto.setFechaEmision(in.getFechaEmision());
		dto.setIdProveedor(in.getProveedor().getId());
		dto.setFechaRecepcion(in.getFechaRecepcion());
		dto.setRazonSocialProveedor(in.getProveedor().getRazonSocial());
		dto.setDescripcionCondicionPago(in.getCondicionPago().getDescripcion());
		dto.setDescripcionAlmacen(in.getAlmacen().getDescripcion());
		dto.setDescripcionTipoPago(in.getTipoMoneda().getDescripcion());
		return dto;
	}
	
	/*
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/ordencompra/list")
	public @ResponseBody List<OrdenCompraDto> todosOrdenesCompras(){
				List<OrdenCompra> ordencompras = (List<OrdenCompra>) ordenCompraRepository.findAll();
				List<OrdenCompraDto> ordencomprasDtos = new ArrayList<OrdenCompraDto>();
				for (OrdenCompra ordencompra : ordencompras) {
					ordencomprasDtos.add(convertToDto(ordencompra));
				}
				return ordencomprasDtos;
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/ordencompra")	   
	public @ResponseBody String borrarOrdenCompra(@RequestParam(value="id") Long id){
				ordenCompraRepository.delete(id);
				return "Ok";				
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/ordencompra/add")
	public @ResponseBody String agregarOrdenCompra(@RequestBody OrdenCompraDto ordencompraDto){
				OrdenCompra nuevaOrdenCompra = new OrdenCompra();
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				nuevaOrdenCompra.setCodigo(ordencompraDto.getCodigo());
				nuevaOrdenCompra.setFechaEmision(ordencompraDto.getFechaEmision());
				nuevaOrdenCompra.setFechaRecepcion(ordencompraDto.getFechaRecepcion());
				Proveedor proveedor = (Proveedor)proveedorRepository.findOne(ordencompraDto.getIdProveedor());
				nuevaOrdenCompra.setProveedor(proveedor);
				Almacen almacen =(Almacen)almacenRepository.findOne(ordencompraDto.getIdAlmacen());
				nuevaOrdenCompra.setAlmacen(almacen);
				CondicionPago condicion=(CondicionPago)condicionPagoRepository.findOne(ordencompraDto.getCondicionPagoCodigo());
				nuevaOrdenCompra.setCondicionPago(condicion);
				Moneda tipomoneda=(Moneda)monedaRepository.findOne(ordencompraDto.getTipoMonedaCodigo());
				nuevaOrdenCompra.setTipoMoneda(tipomoneda);
				nuevaOrdenCompra.setUsuarioCreacion("usuarioCreador");
				nuevaOrdenCompra.setFechaCreacion(fechahora);
				ordenCompraRepository.save(nuevaOrdenCompra);				
				return "OK";
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/ordencompra/update")
	public @ResponseBody String actualizarOrdenCompra(@RequestBody OrdenCompraDto ordencompraParam){
				OrdenCompra ordencompraRepo = (OrdenCompra)ordenCompraRepository.findOne(ordencompraParam.getId());
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				ordencompraRepo.setCodigo(ordencompraParam.getCodigo());
				ordencompraRepo.setFechaEmision(ordencompraParam.getFechaEmision());
				ordencompraRepo.setFechaRecepcion(ordencompraParam.getFechaRecepcion());
				Proveedor proveedor = (Proveedor)proveedorRepository.findOne(ordencompraParam.getIdProveedor());
				ordencompraRepo.setProveedor(proveedor);
				Almacen almacen =(Almacen)almacenRepository.findOne(ordencompraParam.getIdAlmacen());
				ordencompraRepo.setAlmacen(almacen);
				CondicionPago condicion=(CondicionPago)condicionPagoRepository.findOne(ordencompraParam.getCondicionPagoCodigo());
				ordencompraRepo.setCondicionPago(condicion);
				Moneda tipomoneda=(Moneda)monedaRepository.findOne(ordencompraParam.getTipoMonedaCodigo());
				ordencompraRepo.setTipoMoneda(tipomoneda);
				ordencompraRepo.setUsuarioModificacion("UsuarioModificador");
				ordencompraRepo.setFechaModificacion(fechahora);
				ordenCompraRepository.save(ordencompraRepo);				
				return "OK";
	}
	
	
	*/
	

	
		
	
	
	

	/**TrabajadorDto**/

	/**Ubicacion**/
	/*
	private UbicacionDto convertToDto(Ubicacion in) {
		UbicacionDto dto = new UbicacionDto();
		//dto.setId(in.getId());
		dto.setCodigo(in.getCodigo());
		dto.setUbicacion(in.getUbicacion());
		dto.setDescripcionZona(in.getZona().getDescripcion());
		dto.setDescripcionAlmacen(in.getAlmacen().getDescripcion());
		return dto;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/ubicacion/list")
	public @ResponseBody List<UbicacionDto> todasUbicaciones(){
				List<Ubicacion> ubicaciones = (List<Ubicacion>) ubicacionRepository.findAll();
				List<UbicacionDto> ubicacionesDtos = new ArrayList<UbicacionDto>();
				for (Ubicacion ubicacion : ubicaciones) {
					ubicacionesDtos.add(convertToDto(ubicacion));
				}
				return ubicacionesDtos;
	}
	/*Ciro 03102016*/
	/*
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/ubicacion/byalmacenandzona")
	public @ResponseBody List<Ubicacion> obtenerUbicacionesPorAlmacenIdYZonaId(@RequestBody ProductoDto productoDto){
		List<Ubicacion> ubicaciones = new ArrayList<Ubicacion>();
		ubicaciones = ubicacionRepository.findUbicacionByAlmacenIdAndZonaId(productoDto.getAlmacenId(), productoDto.getZonaId());
		
		return ubicaciones;
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/ubicacion")	   
	public @ResponseBody String borrarUbicacion(@RequestParam(value="id") Long id){
				ubicacionRepository.delete(id);
				return "Ok";				
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/ubicacion/add")
	public @ResponseBody String agregarUbicacion(@RequestBody UbicacionDto ubicacion){
				Ubicacion nuevaUbicacion = new Ubicacion();
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				nuevaUbicacion.setCodigo(ubicacion.getCodigo());
				nuevaUbicacion.setUbicacion(ubicacion.getUbicacion());
				Zona zona =(Zona)zonaRepository.findOne(ubicacion.getZonaId());				
				Almacen almacen = (Almacen)almacenRepository.findOne(ubicacion.getAlmacenId());
				nuevaUbicacion.setAlmacen(almacen);
				nuevaUbicacion.setZona(zona);								
				nuevaUbicacion.setUsuarioCreacion("UsuarioCreador");
				nuevaUbicacion.setFechaCreacion(fechahora);
				ubicacionRepository.save(nuevaUbicacion);				
				return "OK";
	}			

	@RequestMapping(method=RequestMethod.POST, value="/api/v1/ubicacion/update")
	public @ResponseBody String actualizarUbicacion(@RequestBody UbicacionDto ubicacionParam){
				//Ubicacion ubicacionRepo = (Ubicacion)ubicacionRepository.findOne(ubicacionParam.getId());
				Ubicacion ubicacionRepo = new Ubicacion();		
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				ubicacionRepo.setCodigo(ubicacionParam.getCodigo());
				ubicacionRepo.setUbicacion(ubicacionParam.getUbicacion());
		
				Zona zona =(Zona)zonaRepository.findOne(ubicacionParam.getZonaId());				
				Almacen almacen = (Almacen)almacenRepository.findOne(ubicacionParam.getAlmacenId());
				ubicacionRepo.setAlmacen(almacen);
				ubicacionRepo.setZona(zona);			
		
				//ubicacionRepo.setZona(zona);
				ubicacionRepo.setUsuarioModificacion("UsuarioModificador");
				ubicacionRepo.setFechaModificacion(fechahora);
				ubicacionRepository.save(ubicacionRepo);				
				return "OK";
	}
	*/
	/**Unidad Medida**/	
	/*
	*/
	
	/**Zona**/
	
	/*Ciro 03102016*/
	//obtenerZonasPorAlmacenId

	
	
	
	
	
	

	
	
	/*Edwin*/
	


	

	
	
	
	/**
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/marca/list/id")
	public @ResponseBody Marca unaMarca(@RequestParam(value="id") Long id){
		return (Marca)marcaRepository.findOne(id);
	}	
	**/	
	
	
	@Autowired
    protected MarcaRepository marcaRepository;	
	@Autowired
    protected ClienteRepository clienteRepository;	
	@Autowired
    protected CategorieRepository familiaRepository;
	@Autowired
    protected ProductoRepository productoRepository;
	
	/*@Autowired*/
	/*protected UnidadMedidaRepository unidadMedidaRepository;*/
	@Autowired
	protected ModeloRepository modeloRepository;
	@Autowired
	protected UbicacionRepository ubicacionRepository;
	@Autowired
	protected AlmacenRepository almacenRepository;
	@Autowired
	protected OrdenCompraRepository ordenCompraRepository;
	@Autowired
	protected TablaGeneralRepository tablaGeneralRepository;
	@Autowired
	protected FacturaRepository facturaRepository;
	@Autowired
	protected OperacionProveedorRepository operacionProveedorRepository;
	@Autowired
	protected ParteIngresoAlmacenRepository parteIngresoAlmacenRepository;
	
	@Autowired
	protected AsientoDiarioRepository asientoDiarioRepository;
	@Autowired
	protected DepartamentoRepository departamentoRepository;
	@Autowired
	protected SedeRepository sedeRepository;
	@Autowired
	protected TrabajadorRepository trabajadorRepository;
	
	@Autowired
	protected PuestoTrabajoRepository puestoTrabajoRepository;
	@Autowired
	protected EmpresaRepository empresaRepository;
	
	
	@Autowired
	protected PlanCuentaRepository planCuentaRepository;
	
	@Autowired
	protected SubCentroCostoRepository subCentroCostoRepository;
	@Autowired
	protected TipoCambioRepository tipoCambioRepository;
	@Autowired
	protected RelacionSeisNueveRepository relacionSeisNueveRepository;
	@Autowired
	protected Relacion979Repository relacion979Repository;
	@Autowired
	protected CondicionPagoRepository condicionPagoRepository;
	@Autowired
	protected FormaPagoRepository formaPagoRepository;
	@Autowired
	protected MonedaRepository monedaRepository;
	@Autowired
	protected TipoDocumentoRepository tipoDocumentoRepository;
	@Autowired
	protected VendedorRepository vendedorRepository;
	@Autowired
	protected ZonaRepository zonaRepository;
	//@Autowired
	//protected MarcaFamiliaRepository marcaFamiliaRepository;
	@Autowired
	protected ProductoAlmacenRepository productoAlmacenRepository;
}
