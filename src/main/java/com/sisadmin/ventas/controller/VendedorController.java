package com.sisadmin.ventas.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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

import com.sisadmin.entity.Producto;
import com.sisadmin.entity.Vendedor;
import com.sisadmin.repository.VendedorRepository;
import com.sisadmin.stock.dto.FilterComboProductoDto;


@Controller
public class VendedorController {

	/**Vendedor**/
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/vendedor/list")
	public @ResponseBody List<Vendedor> todosVendedores(){
				return (List<Vendedor>) vendedorRepository.findAll();
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/vendedor")	   
	public @ResponseBody String borrarVendedor(@RequestParam(value="id") Long id){
			//	vendedorRepository.delete(id);
				return "Ok";				
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/vendedor/add")
	public @ResponseBody String agregarVendedor(@RequestBody Vendedor vendedor){
				Vendedor nuevoVendedor = new Vendedor();
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
			//	nuevoVendedor.setCodigo(vendedor.getCodigo());
			//	nuevoVendedor.setDni(vendedor.getDni());				
				nuevoVendedor.setNombreCompleto(vendedor.getNombreCompleto());
			//	nuevoVendedor.setDireccion(vendedor.getDireccion());
			//	nuevoVendedor.setTelefono(vendedor.getTelefono());
			//	nuevoVendedor.setFechaNacimiento(vendedor.getFechaNacimiento());
			//	nuevoVendedor.setEmail(vendedor.getEmail());
			//	nuevoVendedor.setNombreAbreviado(vendedor.getNombreAbreviado());
			//	nuevoVendedor.setUsuarioCreacion("UsuarioCreador");
			//	nuevoVendedor.setFechaCreacion(fechahora);
				vendedorRepository.save(nuevoVendedor);				
				return "OK";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/vendedor/update")
	public @ResponseBody String actualizarVendedor(@RequestBody Vendedor vendedorParam){
				//Vendedor vendedorRepo = (Vendedor)vendedorRepository.findOne(vendedorParam.getIdSa());
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
			//	vendedorRepo.setCodigo(vendedorParam.getCodigo());
			//	vendedorRepo.setDni(vendedorParam.getDni());
			//	vendedorRepo.setNombreCompleto(vendedorParam.getNombreCompleto());
			//	vendedorRepo.setDireccion(vendedorParam.getDireccion());
			//	vendedorRepo.setTelefono(vendedorParam.getTelefono());
			//	vendedorRepo.setFechaNacimiento(vendedorParam.getFechaNacimiento());
			//	vendedorRepo.setEmail(vendedorParam.getEmail());
			//	vendedorRepo.setNombreAbreviado(vendedorParam.getNombreAbreviado());
			//	vendedorRepo.setUsuarioModificacion("UsuarioModificador");
			//	vendedorRepo.setFechaModificacion(fechahora);
			//	vendedorRepository.save(vendedorRepo);					
				return "OK";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/reportevendedores/filter", produces="application/octet")
	@Transactional
	public void reporteVendedor( HttpServletResponse resp) throws IOException, Exception{
		List<Vendedor> vendedor = (List<Vendedor>)vendedorRepository.VendedoresOrdenados();			 		
		FileInputStream inputStream = new FileInputStream(new File("C:\\softbrill\\sisadmin\\reporteExcel\\vendedores.xlsx"));								
		OPCPackage pkg = OPCPackage.open(inputStream);
		XSSFWorkbook wb = new XSSFWorkbook(pkg);								
		Sheet sheet = wb.getSheetAt(0);
		int rowIndex = 2;	
		for(Vendedor v : vendedor){
			Row row = sheet.createRow(rowIndex++);	
			Cell cell0 = row.createCell(0);	cell0.setCellValue("");
			Cell cell1 = row.createCell(1);	cell1.setCellValue(validarString(v.getNombreCompleto()));
			Cell cell2 = row.createCell(2);cell2.setCellValue("");			
			Cell cell3 = row.createCell(3);cell3.setCellValue("");	
		}
		resp.setContentType("application/octet"); resp.setHeader("Content-Disposition","attachment; filename=\"" + "excelprueba.xlsx" +"\"");
		OutputStream outputStream = null;
		try {	outputStream = resp.getOutputStream();
				wb.write(outputStream);
			} catch (Exception e) {	System.out.println("Error al obtener archivo "); e.printStackTrace();
			} finally {	inputStream.close(); outputStream.close();	}		
	}
	private String validarString(String valor){		
		String val = "";		
		if(valor!=null){val=valor;}
		return  val;
	}

	@Autowired
	VendedorRepository vendedorRepository;
}
