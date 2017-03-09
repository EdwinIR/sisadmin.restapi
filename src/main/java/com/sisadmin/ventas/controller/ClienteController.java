package com.sisadmin.ventas.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

import com.sisadmin.entity.Cliente;
import com.sisadmin.entity.ClienteSucursal;
import com.sisadmin.repository.ClienteRepository;
import com.sisadmin.repository.ClienteSucursalRepository;
import com.sisadmin.ventas.dto.ClienteDto;
import com.sisadmin.ventas.dto.ClienteFilter;

/**
 * ABMControllerCliente
 * @author ciro
 *
 */
@Controller
public class ClienteController {

	public static Date f= new Date();
	public static long h=f.getTime();
	public static Timestamp fh= new Timestamp(h);
	
	
	/**Clientes**/
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/cliente/list")
	public @ResponseBody List<ClienteDto> todosClientes(){
		List<ClienteDto> clienteDtos = new ArrayList<ClienteDto>();
		List<Cliente> clientes = (List<Cliente>) clienteRepository.findAll();
		for(Cliente cliente : clientes){
			clienteDtos.add(leer(cliente));
		}
		return clienteDtos;		
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/cliente")	   
	public @ResponseBody String borrarCliente(@RequestParam(value="id") Long id){
				//clienteRepository.delete(id);
				return "Ok";				
	}	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/cliente/add")
	public @ResponseBody String agregarCliente(@RequestBody ClienteDto clienteDto){
		Cliente cliente = new Cliente();
		cliente.setId(UUID.randomUUID().toString());
		cliente.setIdentificador(clienteDto.getIdentificador());
		cliente.setRazonSocial(clienteDto.getRazonSocial());
		cliente.setRubro(clienteDto.getRubro());
		cliente.setEmail(clienteDto.getEmail());
		cliente.setFechaAlta(clienteDto.getFechaAlta());		
		clienteRepository.save(cliente);
				return "OK";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/cliente/update")
	public @ResponseBody String actualizarCliente(@RequestBody ClienteDto clienteDto){		
		Cliente cliente = clienteRepository.findOne(clienteDto.getId());
			cliente.setIdentificador(clienteDto.getIdentificador());
			cliente.setSearchKey(clienteDto.getIdentificador());
			cliente.setRazonSocial(clienteDto.getRazonSocial());
			cliente.setRubro(clienteDto.getRubro());
			cliente.setEmail(clienteDto.getEmail());
			cliente.setFechaAlta(clienteDto.getFechaAlta());		
		clienteRepository.save(cliente);
				return "OK";
	}
	
	/**convertToEntity**/
	private Cliente convertToCliente(ClienteDto clienteDto){
		Date fecha = new Date(); 
		long hora = fecha.getTime();
		Timestamp fechahora= new Timestamp(hora);
		Cliente cliente = new Cliente();
		/*Cabecera del cliente*/
		//cliente.setIdSa(clienteDto.getIdSa());
		cliente.setIdentificador(clienteDto.getIdentificador());
		cliente.setRazonSocial(clienteDto.getRazonSocial());
		cliente.setRubro(clienteDto.getRubro());
		cliente.setEmail(clienteDto.getEmail());
		cliente.setFechaAlta(clienteDto.getFechaAlta());
		cliente.setFechaCreacion(fechahora);
		cliente.setUsuarioCreacion("usuarioCreador");
		/*ClienteDetalle es ClienteSucursal*/
		/*for(ClienteSucursal clienteSucursal : clienteDto.getDetalles()){
			clienteSucursal.setCliente(cliente);
			cliente.getDetalleClientes().add(clienteSucursal);
		}*/
		
		return cliente;
	}
	/**convertToDto**/
	private ClienteDto leer(Cliente cliente){
		ClienteDto clienteDto = new ClienteDto();
		/*Cabecera*/
		clienteDto.setId(cliente.getId());
		clienteDto.setIdentificador(cliente.getIdentificador());
		clienteDto.setRazonSocial(cliente.getRazonSocial());
		clienteDto.setRubro(cliente.getRubro());
		clienteDto.setEmail(cliente.getEmail());
		clienteDto.setFechaAlta(cliente.getFechaAlta());
		/*Detalles*/
		List<ClienteSucursal> s=(List<ClienteSucursal>)clienteSucursalRepository.findClienteSucursalByClienteId(cliente.getIdSa());
		if(s!=null){
			//clienteDto.setDetalles(s);
		}
		return clienteDto;
	}
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/reporteclientes/filter", produces="application/octet")
	@Transactional
	public void reporteCliente( @RequestBody ClienteFilter clienteFilter, HttpServletResponse resp) throws IOException, Exception{
		List<Cliente> cliente = new ArrayList<Cliente>();
		if(clienteFilter.getRegion()==null ||clienteFilter.getRegion()==""){
			cliente=(List<Cliente>)clienteRepository.findAll();
		}else{
			cliente = (List<Cliente>)clienteRepository.findByRegion(clienteFilter.getRegion());		
		}
		 		
		FileInputStream inputStream = new FileInputStream(new File("C:\\softbrill\\sisadmin\\reporteExcel\\clientes.xlsx"));								
		OPCPackage pkg = OPCPackage.open(inputStream);
		XSSFWorkbook wb = new XSSFWorkbook(pkg);								
		Sheet sheet = wb.getSheetAt(0);
		int rowIndex = 2;	
		for(Cliente c : cliente){
			Row row = sheet.createRow(rowIndex++);	
			Cell cell0 = row.createCell(0);	cell0.setCellValue(validarString(c.getIdentificador()));
			Cell cell1 = row.createCell(1);cell1.setCellValue(validarString(c.getRazonSocial()));
			Cell cell2 = row.createCell(2);	cell2.setCellValue(validarString(c.getEmail()));
			Cell cell3 = row.createCell(3);	cell3.setCellValue(fechaString(c.getFechaAlta()));
			Cell cell4 = row.createCell(4);	cell4.setCellValue(validarString(c.getRubro()));
					
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
	private String fechaString(Date date){
		String retorno = "";
		if(date != null) return date.toString();
		return retorno;
	}
	
	@Autowired
	private ClienteRepository clienteRepository;	
	@Autowired
	private ClienteSucursalRepository clienteSucursalRepository;	
}
