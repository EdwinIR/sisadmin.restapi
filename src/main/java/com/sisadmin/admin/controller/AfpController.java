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

import com.sisadmin.admin.dto.AfpPeriodoDto;
import com.sisadmin.entity.Afp;
import com.sisadmin.entity.AfpPeriodo;
import com.sisadmin.entity.Periodo;
import com.sisadmin.repository.AfpPeriodoRepository;
import com.sisadmin.repository.AfpRepository;
import com.sisadmin.repository.PeriodoRepository;

@Controller
public class AfpController {
	
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/afp/list")
	public @ResponseBody List<Afp> todosAfps(){
				return (List<Afp>) afpRepository.findAll();
	}

	/**AfpPeriodo**/
	private AfpPeriodoDto convertToDto(AfpPeriodo in) {
		AfpPeriodoDto dto = new AfpPeriodoDto();
		dto.setId(in.getId());
		dto.setCodigo(in.getCodigo());
		dto.setTasa(in.getTasa());
		dto.setDescuento(in.getDescuento());
		dto.setDescripcionAfp(in.getAfp().getDescripcion());
		dto.setDescripcionPeriodo(in.getPeriodo().getCodigo());
		return dto;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/afpperiodo/list")
	public @ResponseBody List<AfpPeriodoDto> todosAfpPeriodos(){
				List<AfpPeriodo> afpperiodos = (List<AfpPeriodo>) afpPeriodoRepository.findAll();
				List<AfpPeriodoDto> afpperiodosDtos = new ArrayList<AfpPeriodoDto>();
				for (AfpPeriodo afpperiodo : afpperiodos) {
					afpperiodosDtos.add(convertToDto(afpperiodo));
				}
				return afpperiodosDtos;
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/afpperiodo")	   
	public @ResponseBody String borrarAfpPeriodo(@RequestParam(value="id") Long id){
				afpPeriodoRepository.delete(id);
				return "Ok";				
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/afpperiodo/add")
	public @ResponseBody String agregarAfpPeriodo(@RequestBody AfpPeriodoDto afpperiodoDto){
				AfpPeriodo nuevoAfpPeriodo = new AfpPeriodo();
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				nuevoAfpPeriodo.setCodigo(afpperiodoDto.getCodigo());
				nuevoAfpPeriodo.setTasa(afpperiodoDto.getTasa());
				nuevoAfpPeriodo.setDescuento(afpperiodoDto.getDescuento());
				Periodo periodo = (Periodo)periodoRepository.findOne(afpperiodoDto.getPeriodoId());
				nuevoAfpPeriodo.setPeriodo(periodo);
				Afp afp = (Afp)afpRepository.findOne(afpperiodoDto.getAfpId());
				nuevoAfpPeriodo.setAfp(afp);
				nuevoAfpPeriodo.setUsuarioCreacion("UsuarioCreador");
				nuevoAfpPeriodo.setFechaCreacion(fechahora);
				/*Marca marca = (Marca)marcaRepository.findOne(productoDto.getMarcaId());
				nuevoProducto.setMarca(marca);
				Modelo modelo =(Modelo)modeloRepository.findOne(productoDto.getModeloId());
				nuevoProducto.setModelo(modelo);
				Proveedor proveedor=(Proveedor)proveedorRepository.findOne(productoDto.getProveedorId());
				nuevoProducto.setProveedor(proveedor);
				Familia familia=(Familia)familiaRepository.findOne(productoDto.getFamiliaId());
				nuevoProducto.setFamilia(familia);*/
				afpPeriodoRepository.save(nuevoAfpPeriodo);				
				return "OK";
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/afpperiodo/update")
	public @ResponseBody String actualizarAfpPeriodo(@RequestBody AfpPeriodoDto afpperiodoParam){
				AfpPeriodo afpperiodoRepo = (AfpPeriodo)afpPeriodoRepository.findOne(afpperiodoParam.getId());
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				afpperiodoRepo.setCodigo(afpperiodoParam.getCodigo());
				afpperiodoRepo.setTasa(afpperiodoParam.getTasa());
				afpperiodoRepo.setDescuento(afpperiodoParam.getDescuento());				
				/*Marca marca = (Marca)marcaRepository.findOne(productoParam.getMarcaId());
				productoRepo.setMarca(marca.getId());*/
				afpperiodoRepo.setUsuarioModificacion("UsuarioModificador");
				afpperiodoRepo.setFechaModificacion(fechahora);
				afpPeriodoRepository.save(afpperiodoRepo);				
				return "OK";
	}


	@Autowired
	protected AfpPeriodoRepository afpPeriodoRepository;
	@Autowired
	protected AfpRepository afpRepository;
	@Autowired
	protected PeriodoRepository periodoRepository;

}
