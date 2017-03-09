package com.sisadmin.compras.controller;

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

import com.sisadmin.compras.dto.OrdenCompraFilter;
import com.sisadmin.compras.dto.ProveedorFilter;
import com.sisadmin.entity.OrdenCompra;
import com.sisadmin.entity.Proveedor;
import com.sisadmin.repository.ProveedorRepository;


@Controller
public class ProveedorController {
	
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/proveedor/list")
	public @ResponseBody List<Proveedor> todosProveedores(){
				return (List<Proveedor>) proveedorRepository.proveedoresOrdenados();
	}
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/proveedor")	   
	public @ResponseBody String borrarProveedor(@RequestParam(value="id") Long id){
				proveedorRepository.delete(id);
				return "Ok";				
	}	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/proveedor/add")
	public @ResponseBody String agregarProveedor(@RequestBody Proveedor proveedor){
				Proveedor nuevoProveedor = new Proveedor();
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				nuevoProveedor.setRuc(proveedor.getRuc());
				nuevoProveedor.setRazonSocial(proveedor.getRazonSocial());
				nuevoProveedor.setDireccion(proveedor.getDireccion());
				nuevoProveedor.setCiudad(proveedor.getCiudad());
				nuevoProveedor.setTelefonoUno(proveedor.getTelefonoUno());
				nuevoProveedor.setTelefonoDos(proveedor.getTelefonoDos());
				nuevoProveedor.setContacto(proveedor.getContacto());
				nuevoProveedor.setEmail(proveedor.getEmail());
				nuevoProveedor.setRubro(proveedor.getRubro());
				nuevoProveedor.setFechaAlta(proveedor.getFechaAlta());
				nuevoProveedor.setWebSite(proveedor.getWebSite());
				nuevoProveedor.setUsuarioCreacion("UsuarioCreador");
				nuevoProveedor.setFechaCreacion(fechahora);
				proveedorRepository.save(nuevoProveedor);				
				return "OK";
	}
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/proveedor/update")
	public @ResponseBody String actualizarProveedor(@RequestBody Proveedor proveedorParam){
				Proveedor proveedorRepo = (Proveedor)proveedorRepository.findOne(proveedorParam.getId());
				Date fecha = new Date(); 
				long hora = fecha.getTime();
				Timestamp fechahora= new Timestamp(hora);
				proveedorRepo.setRuc(proveedorParam.getRuc());
				proveedorRepo.setRazonSocial(proveedorParam.getRazonSocial());
				proveedorRepo.setDireccion(proveedorParam.getDireccion());
				proveedorRepo.setCiudad(proveedorParam.getCiudad());
				proveedorRepo.setTelefonoUno(proveedorParam.getTelefonoUno());
				proveedorRepo.setTelefonoDos(proveedorParam.getTelefonoDos());
				proveedorRepo.setContacto(proveedorParam.getContacto());
				proveedorRepo.setEmail(proveedorParam.getEmail());
				proveedorRepo.setRubro(proveedorParam.getRubro());
				proveedorRepo.setFechaAlta(proveedorParam.getFechaAlta());
				proveedorRepo.setUsuarioModificacion("UsuarioModificador");
				proveedorRepo.setFechaModificacion(fechahora);
				proveedorRepository.save(proveedorRepo);				
				return "OK";
	}
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/reporteproveedor/filter", produces="application/octet")
	@Transactional
	public void reporteProveedor( @RequestBody ProveedorFilter proveedorFilter, HttpServletResponse resp) throws IOException, Exception{
		List<Proveedor> proveedor= new ArrayList<Proveedor>();
		if(proveedorFilter.getCiudad()==null || proveedorFilter.getCiudad()==""){
			 proveedor = (List<Proveedor>)proveedorRepository.findAll();		
		}else{
			 proveedor = (List<Proveedor>)proveedorRepository.findByCiudad(proveedorFilter.getCiudad());
		}
		FileInputStream inputStream = new FileInputStream(new File("C:\\softbrill\\sisadmin\\reporteExcel\\proveedor.xlsx"));								
		OPCPackage pkg = OPCPackage.open(inputStream);
		XSSFWorkbook wb = new XSSFWorkbook(pkg);								
		Sheet sheet = wb.getSheetAt(0);
		int rowIndex = 2;	
		for(Proveedor p : proveedor){
			Row row = sheet.createRow(rowIndex++);	
			Cell cell0 = row.createCell(0);	cell0.setCellValue(validarString(p.getRuc()));
			Cell cell1 = row.createCell(1);	cell1.setCellValue(validarString(p.getRazonSocial()));
			Cell cell2 = row.createCell(2);	cell2.setCellValue(validarString(p.getCiudad()));
			Cell cell3 = row.createCell(3);	cell3.setCellValue(validarString(p.getDireccion()));
			Cell cell4 = row.createCell(4);	cell4.setCellValue(validarString(p.getTelefonoUno()));
			Cell cell5 = row.createCell(5);	cell5.setCellValue(validarString(p.getRubro()));
		}
		resp.setContentType("application/octet"); resp.setHeader("Content-Disposition","attachment; filename=\"" + "excelprueba.xlsx" +"\"");
		OutputStream outputStream = null;
		try {	outputStream = resp.getOutputStream();
				wb.write(outputStream);
				//IOUtils.copy(inputStream, outputStream);
			} catch (Exception e) {	System.out.println("Error al obtener archivo "); e.printStackTrace();
			} finally {	inputStream.close(); outputStream.close();	}
		
	}
	private String validarString(String valor){		
		String val = "";		
		if(valor!=null){val=valor;}
		return  val;
	}

	@Autowired
    protected ProveedorRepository proveedorRepository;
}
