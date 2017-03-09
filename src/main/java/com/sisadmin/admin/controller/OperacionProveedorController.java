package com.sisadmin.admin.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sisadmin.admin.dto.OperacionProveedorDto;
import com.sisadmin.entity.OperacionProveedor;
import com.sisadmin.entity.Proveedor;
import com.sisadmin.repository.OperacionProveedorRepository;
import com.sisadmin.repository.ProveedorRepository;

@Controller
public class OperacionProveedorController {

	/**OperacionProveedor**/
	private OperacionProveedorDto convertToDto(OperacionProveedor in) {
		OperacionProveedorDto dto = new OperacionProveedorDto();
		
		dto.setNumeroInterno(in.getNumeroInterno());
		dto.setNumeroGuia(in.getNumeroGuia());
		dto.setRazonSocialProveedor(in.getProveedor().getRazonSocial());
		dto.setFecha(in.getFecha());
		
		return dto;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/operacionproveedor/list")
	public @ResponseBody List<OperacionProveedorDto> todosOperacionProveedor(){
				List<OperacionProveedor> ordencompras = (List<OperacionProveedor>) operacionProveedorRepository.findAll();
				List<OperacionProveedorDto> ordencomprasDtos = new ArrayList<OperacionProveedorDto>();
				for (OperacionProveedor ordencompra : ordencompras) {
					ordencomprasDtos.add(convertToDto(ordencompra));
				}
				return ordencomprasDtos;
	}	
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/operacionproveedor")	   
	public @ResponseBody String borrarOperacionProveedor(@RequestParam(value="id") Long id){
				operacionProveedorRepository.delete(id);
				return "Ok";				
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/operacionproveedor/add")
	public @ResponseBody String agregarOperacionProveedor(@RequestBody OperacionProveedorDto operacionProveedorDto){
				OperacionProveedor nuevaOperacionProveedor = new OperacionProveedor();
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				nuevaOperacionProveedor.setNumeroInterno(operacionProveedorDto.getNumeroInterno());
				nuevaOperacionProveedor.setNumeroGuia(operacionProveedorDto.getNumeroGuia());		
				nuevaOperacionProveedor.setFecha(operacionProveedorDto.getFecha());				
				Proveedor proveedor = (Proveedor)proveedorRepository.findOne(operacionProveedorDto.getProveedorId());
				nuevaOperacionProveedor.setProveedor(proveedor);
				nuevaOperacionProveedor.setUsuarioCreacion("UsuarioCreador");
				nuevaOperacionProveedor.setFechaCreacion(fechahora);
				operacionProveedorRepository.save(nuevaOperacionProveedor);				
				return "OK";
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/operacionproveedor/update")
	public @ResponseBody String actualizarOperacionProveedor(@RequestBody OperacionProveedorDto operacionproveedorParam){
				OperacionProveedor operacionproveedorRepo = (OperacionProveedor)operacionProveedorRepository.findOne(operacionproveedorParam.getId());
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				operacionproveedorRepo.setId(operacionproveedorParam.getId());
				operacionproveedorRepo.setNumeroInterno(operacionproveedorParam.getNumeroInterno());
				operacionproveedorRepo.setNumeroGuia(operacionproveedorParam.getNumeroGuia());		
				operacionproveedorRepo.setFecha(operacionproveedorParam.getFecha());	
				operacionproveedorRepo.setUsuarioCreacion("UsuarioModificador");
				operacionproveedorRepo.setFechaModificacion(fechahora);
				operacionProveedorRepository.save(operacionproveedorRepo);				
				return "OK";
	}

	
	@Autowired
	OperacionProveedorRepository operacionProveedorRepository;
	@Autowired
	ProveedorRepository proveedorRepository;
}
