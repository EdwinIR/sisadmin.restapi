package com.sisadmin.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sisadmin.admin.dto.ClienteDeudaDetalleDto;
import com.sisadmin.admin.dto.ClienteDeudaDto;
import com.sisadmin.compras.dto.OrdenCompraDetalleDto;
import com.sisadmin.compras.dto.OrdenCompraDto;
import com.sisadmin.entity.ClienteDeuda;
import com.sisadmin.entity.ClienteDeudaDetalle;
import com.sisadmin.entity.OrdenCompra;
import com.sisadmin.entity.OrdenCompraDetalle;
import com.sisadmin.repository.ClienteDeudaRepository;

@Controller
public class ABMControllerClienteDeuda {

	
	private ClienteDeudaDto convertToDto(ClienteDeuda in) {
		ClienteDeudaDto dto = new ClienteDeudaDto();
		
		dto.setId(in.getId());
		dto.setClienteId(in.getCliente().getIdSa());
		dto.setMontoAdeudado(in.getMontoAdeudado());
		dto.setRazonSocialCliente(in.getCliente().getRazonSocial());
		dto.setIdentificadorCliente(in.getCliente().getIdentificador());
		
		for(ClienteDeudaDetalle cd : in.getDetalleDeuda()){
			ClienteDeudaDetalleDto cdd = new ClienteDeudaDetalleDto();
			cdd.setNroDocumento(cd.getComprobante().getNroDocumento());
			cdd.setFechaEmisionDoc(cd.getComprobante().getFechaEmision());
			cdd.setMontoTotalDoc(cd.getComprobante().getMontoTotal());
			cdd.setMontoAdeudadoDoc(cd.getMontoAdeudado());
			cdd.setFechaVencimiento(cd.getFechaVencimiento());
			cdd.setObservaciones(cd.getObervaciones());
			dto.getDetalles().add(cdd);	
			
		}
				
		return dto;		
	}
	
	@Transactional
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/clientedeuda/list")
	public @ResponseBody List<ClienteDeudaDto> todosClientesDeudas(){
				List<ClienteDeuda> clientedeudas = (List<ClienteDeuda>) clienteDeudaRepository.findAll();				
				List<ClienteDeudaDto> clientedeudasDtos = new ArrayList<ClienteDeudaDto>();
				for (ClienteDeuda clientedeuda : clientedeudas) {
					clientedeudasDtos.add(convertToDto(clientedeuda));
				}
				return clientedeudasDtos;
	}
	
	
	@Autowired
	private ClienteDeudaRepository clienteDeudaRepository;
}
