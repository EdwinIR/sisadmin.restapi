package com.sisadmin.stock.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import au.com.bytecode.opencsv.CSVReader;

import com.sisadmin.compras.dto.MarcaFamiliaImportDto;
import com.sisadmin.entity.Categorie;
import com.sisadmin.entity.Marca;
import com.sisadmin.entity.MarcaFamilia;
import com.sisadmin.entity.Producto;
import com.sisadmin.repository.CategorieRepository;
import com.sisadmin.repository.MarcaFamiliaRepository;
import com.sisadmin.repository.MarcaRepository;
import com.sisadmin.stock.dto.FilterComboProductoDto;
import com.sisadmin.stock.dto.MarcaFamiliaDto;

@Controller
public class MarcaFamiliaController {

	private MarcaFamiliaDto convertToDto(MarcaFamilia in) {
		MarcaFamiliaDto dto = new MarcaFamiliaDto();
		dto.setId(in.getId());
		dto.setMargenBase(in.getMargenBase());
		dto.setMargenA(in.getMargenA());
		dto.setMargenB(in.getMargenB());
		dto.setMargenC(in.getMargenC());
		if(in.getMarca()!=null){dto.setDescripcionMarca(in.getMarca().getDescripcion());}
		if(in.getFamilia()!=null){dto.setDescripcionFamilia(in.getFamilia().getName());}
		return dto;
	}
	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/marcafamilia/importar/filter")
	public @ResponseBody String importar( MarcaFamiliaImportDto importDto){			
			/*
			 * Todo Leer CSV de Marcas y Familias y hacer inserts en MarcaFamilia
			 * para recuperar la Marca y FAmilia usar los CODIGOS 
			 */
			
		try{   				
				CSVReader reader = new CSVReader(new FileReader("C:/softbrill/sisadmin/imports/margenes.csv"),'|');	
			    String [] nextLine;
			    int i = 0;
			    while ((nextLine = reader.readNext()) != null) {
			    		 		
			    			if(i == 0){} else {
			    				String codFamilia = nextLine[0].trim();
			    				String codMarca = nextLine[1].trim();
			    				String margenBase = nextLine[2].trim();
			    				String margenA = nextLine[3].trim();
			    				String margenB = nextLine[4].trim();
			    				String margenC = nextLine[5].trim();			    				
			    				MarcaFamilia nuevaMarcaFamilia = new MarcaFamilia();
			    				Date fecha = new Date(); 
			    				long hora = fecha.getTime();
			    				Timestamp fechahora= new Timestamp(hora);
			    				
			    				Marca marca = (Marca)marcaRepository.findByCodigo(codMarca);
			    				nuevaMarcaFamilia.setMarca(marca);				
			    				Categorie familia = (Categorie)familiaRepository.findByCodigo(codFamilia);
			    				nuevaMarcaFamilia.setFamilia(familia);
			    				
			    				nuevaMarcaFamilia.setMargenBase(obtenerMonto(margenBase));
			    				nuevaMarcaFamilia.setMargenA(obtenerMonto(margenA));
			    				nuevaMarcaFamilia.setMargenB(obtenerMonto(margenB));
			    				nuevaMarcaFamilia.setMargenC(obtenerMonto(margenC));				
			    				nuevaMarcaFamilia.setUsuarioCreacion("UsuarioCreador");
			    				nuevaMarcaFamilia.setFechaCreacion(fechahora);
			    				//nuevaMarcaFamilia.setMarcfam(marca.getDescripcion()+familia.getName());
			    				marcaFamiliaRepository.save(nuevaMarcaFamilia);
			    			}
			    			i++;
			    		
			    }	
			}catch(Exception e){e.printStackTrace();}	    
			return "Ok";
	}
	
	private BigDecimal obtenerMonto(String monto){
			BigDecimal res = new BigDecimal("0.00");
			try{
				if(monto != null){
					res = new BigDecimal(monto);
				}
			}catch(Exception e){e.printStackTrace();}
			return res;
			
	}
	
	
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
		String respuesta = "OK";
		try {
				MarcaFamilia nuevaMarcaFamilia = new MarcaFamilia();
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				
				Marca marca = (Marca)marcaRepository.findOne(marcaFamiliaDto.getMarcaId());
				nuevaMarcaFamilia.setMarca(marca);				
				Categorie familia = (Categorie)familiaRepository.findOne(marcaFamiliaDto.getFamiliaId());
				nuevaMarcaFamilia.setFamilia(familia);
				nuevaMarcaFamilia.setMargenBase(marcaFamiliaDto.getMargenBase());
				nuevaMarcaFamilia.setMargenA(marcaFamiliaDto.getMargenA());
				nuevaMarcaFamilia.setMargenB(marcaFamiliaDto.getMargenB());
				nuevaMarcaFamilia.setMargenC(marcaFamiliaDto.getMargenC());				
				nuevaMarcaFamilia.setUsuarioCreacion("UsuarioCreador");
				nuevaMarcaFamilia.setFechaCreacion(fechahora);
				nuevaMarcaFamilia.setMarcfam(marca.getDescripcion()+familia.getName());
				marcaFamiliaRepository.save(nuevaMarcaFamilia);
		} catch (Exception e) {
			respuesta = "Marca Familia Repetida";
			e.printStackTrace();
		}
		return respuesta;
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/marcafamilia/update")
	public @ResponseBody String actualizarMarcaFamilia(@RequestBody MarcaFamiliaDto marcaFamiliaParam){
				MarcaFamilia marcaFamiliaRepo = (MarcaFamilia)marcaFamiliaRepository.findOne(marcaFamiliaParam.getId());
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				
				Marca marca = (Marca)marcaRepository.findOne(marcaFamiliaParam.getMarcaId());
				marcaFamiliaRepo.setMarca(marca);				
				Categorie familia = (Categorie)familiaRepository.findOne(marcaFamiliaParam.getFamiliaId());
				marcaFamiliaRepo.setFamilia(familia);
				marcaFamiliaRepo.setMargenBase(marcaFamiliaParam.getMargenBase());
				marcaFamiliaRepo.setMargenA(marcaFamiliaParam.getMargenA());
				marcaFamiliaRepo.setMargenB(marcaFamiliaParam.getMargenB());
				marcaFamiliaRepo.setMargenC(marcaFamiliaParam.getMargenC());				
				marcaFamiliaRepo.setUsuarioModificacion("UsuarioModificador");
				marcaFamiliaRepo.setFechaModificacion(fechahora);	
				marcaFamiliaRepository.save(marcaFamiliaRepo);				
				return "OK";
	}
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/reportemargenes/filter", produces="application/octet")
	@Transactional
	public void reporteMargenes( @RequestBody FilterComboProductoDto filter, HttpServletResponse resp) throws IOException, Exception{
		List<MarcaFamilia> marcaFamilia = new ArrayList<MarcaFamilia>();
		if(filter.getFamiliaId()!=0 && filter.getMarcaId()!=0){
			marcaFamilia=(List<MarcaFamilia>)marcaFamiliaRepository.findByFamiliaAndMarca(filter.getFamiliaId(), filter.getMarcaId());
		}else if(filter.getFamiliaId()!=0 &&filter.getMarcaId()==0){
			marcaFamilia=(List<MarcaFamilia>)marcaFamiliaRepository.findByFamiliaId(filter.getFamiliaId());
		}else{
			marcaFamilia=(List<MarcaFamilia>)marcaFamiliaRepository.findAll();
		}
		 		
		FileInputStream inputStream = new FileInputStream(new File("C:\\softbrill\\sisadmin\\reporteExcel\\margenmarcafamilia.xlsx"));								
		OPCPackage pkg = OPCPackage.open(inputStream);
		XSSFWorkbook wb = new XSSFWorkbook(pkg);								
		Sheet sheet = wb.getSheetAt(0);
		int rowIndex = 2;	
		for(MarcaFamilia mf : marcaFamilia){
			Row row = sheet.createRow(rowIndex++);	
			Cell cell0 = row.createCell(0);	cell0.setCellValue("");
			if(mf.getFamilia()!=null){cell0.setCellValue(mf.getFamilia().getName());}
			Cell cell1 = row.createCell(1);	cell1.setCellValue("");
			if(mf.getMarca()!=null){cell1.setCellValue(mf.getMarca().getDescripcion());}			
			Cell cell2 = row.createCell(2);cell2.setCellValue(montoString(mf.getMargenBase()));			
			Cell cell3 = row.createCell(3);cell3.setCellValue(montoString(mf.getMargenA()));	
			Cell cell4 = row.createCell(4);cell4.setCellValue(montoString(mf.getMargenB()));
			Cell cell5 = row.createCell(5);cell5.setCellValue(montoString(mf.getMargenC()));
		}
		resp.setContentType("application/octet"); resp.setHeader("Content-Disposition","attachment; filename=\"" + "excelprueba.xlsx" +"\"");
		OutputStream outputStream = null;
		try {	outputStream = resp.getOutputStream();
				wb.write(outputStream);
				//IOUtils.copy(inputStream, outputStream);
			} catch (Exception e) {	System.out.println("Error al obtener archivo "); e.printStackTrace();
			} finally {	inputStream.close(); outputStream.close();	}
		
	}		
	private String montoString(BigDecimal monto){
		String retorno = "";
		if(monto != null) return monto.toString();
		return retorno;
	}
	
	@Autowired
	private MarcaFamiliaRepository marcaFamiliaRepository;
	@Autowired
	private MarcaRepository marcaRepository;
	@Autowired
	private CategorieRepository familiaRepository;
}
