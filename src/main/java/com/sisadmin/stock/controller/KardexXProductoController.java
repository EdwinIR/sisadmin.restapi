package com.sisadmin.stock.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sisadmin.stock.dto.FilterKardexProductoDto;
import com.sisadmin.stock.dto.KardexXProductoDto;


@Controller
public class KardexXProductoController {

	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/kardexxproducto/filter")
	public @ResponseBody List<KardexXProductoDto> getAllFilter(@RequestBody FilterKardexProductoDto filterKardexProductoDto, HttpServletRequest request) throws ParseException{

		List<KardexXProductoDto>  kardexProductoDtoLista = new ArrayList<KardexXProductoDto>();
		
		KardexXProductoDto pr = new KardexXProductoDto();
		pr.setNumero(123);
		pr.setSalida(1);
		pr.setEntrada(1);
		pr.setSaldo(1);
		kardexProductoDtoLista.add(pr);
		
		return kardexProductoDtoLista;
		
				/**
				MarcaFamilia marcaFamilia = marcaFamiliaRepository.findByIdMarcaAndIdFamilia(stockMinMaxFilter.getMarcaId(),stockMinMaxFilter.getFamiliaId());
				List<Producto> productos = (List<Producto>)productoRepository.findByCodFamiliaMarcas(marcaFamilia.getMarca().getId(), marcaFamilia.getFamilia().getId());
				List<StockMinMaxDto> minMaxDto = new ArrayList<StockMinMaxDto>();
				for(Producto prod : productos){
					ProductoAlmacen productoAlmacen = productoAlmacenRepository.findByCodProductoCodAlmacen(prod.getId(),stockMinMaxFilter.getAlmacenId());	
					if(productoAlmacen != null) {
								StockMinMaxDto dto = new StockMinMaxDto();
								dto.setDescripcion(prod.getDescripcion());
								dto.setUnidadMedida(prod.getUnidadMedidaPrincipal().getDescripcion());
								dto.setStockFisico(productoAlmacen.getCantidad());
								//dto.setStockMax(stockMax);
								minMaxDto.add(dto);
					}			
				}
				return minMaxDto;**/
	}

	
	
}
