package com.sisadmin.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nimbusds.jose.JOSEException;
import com.sisadmin.admin.dto.LoginCredentials;
import com.sisadmin.entity.User;
import com.sisadmin.entity.UserRole;
import com.sisadmin.service.crypto.SisfeJowtService;
import com.sisadmin.service.repository.ClienteService;
import com.sisadmin.service.repository.EmisorActual;
import com.sisadmin.service.repository.UserRoleService;
import com.sisadmin.service.repository.UserService;

@Controller
public class LoginController {

	
	@Autowired
	protected SisfeJowtService authHelper;
	
	@Autowired
	protected UserService userService;
	
	@Autowired
	protected UserRoleService userRoleService;
	
	@Autowired
	protected EmisorActual emisorActual;
	
	@Autowired
	protected ClienteService clienteService;
	
	
	@RequestMapping(method=RequestMethod.POST,value="/api/v1/user/login")
	public @ResponseBody LoginCredentials get(@RequestBody LoginCredentials login, HttpServletRequest request, HttpServletResponse response) {
		
		String ruc = emisorActual.getEmisorActual().getRuc();
		
		/**if (login.getUserType().equals("E")){
			ruc = globalConfig.getGlobalId();
		} else if (login.getUserType().equals("C")){
			Customer cust = customerService.findByIdentification(login.getCompanyIdentification());
			ruc = cust.getEmitter().getIdentification();
			
		}*/
		
		
		String token ="";
		String inUserName = login.getUserName();
		String inPassword = login.getPassword();
		Integer almacenId = login.getAlmacenId();
		if (inUserName == null && inPassword == null) {
			throw new RuntimeException();
		} else {
			
			if (inUserName != null && inPassword != null ) {
				User user = validateUserDB(login, ruc);
				if (user != null) {
					try {
						token = authHelper.createJsonWebToken(inUserName, request.getRemoteHost()+"(" + request.getRemoteAddr()+")",1L);
					} catch (JOSEException e) {						
						e.printStackTrace();
					}
					//si login es de un empleado devuelvo su rol
					String role = null;
					//UserRole usrRole = userRoleService.findByRucAndCodUsuarioAndIsActive(login.getCompanyIdentification(), inUserName, "Y");
					UserRole usrRole = userRoleService.findByRucAndCodUsuarioAndIsActive(ruc, inUserName, "Y");
					role = usrRole.getCodRol();					
					//return new LoginCredentials(inUserName, "", token, user.getNombre(),login.getUserType(), role, login.getCompanyIdentification());
					return new LoginCredentials(inUserName, "", token, user.getNombre(),login.getUserType(), role, ruc, almacenId);
				} else {
					try {
							response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"No Autorizado");
							throw new RuntimeException();
					} catch(Exception e) {e.printStackTrace();throw new RuntimeException();}		
				}
			} else { 
				throw new RuntimeException();
			}
		}
    }
	


	public User validateUserDB(LoginCredentials login, String ruc) {
		try {
			//User usr = userService.findByRucAndCodUsuario(login.getCompanyIdentification(), login.getUserName());
			User usr = userService.findByRucAndCodUsuario(ruc, login.getUserName());
			if (usr == null) {
				System.out.println("Usuario :" + login.getUserName() + " no encontrado.");
				return null;
			} else {
				String passIn = authHelper.encriptar(login.getPassword());				
				if (usr.getPassword().equals(passIn) && usr.getIsActive().equals("Y")) {
					return usr;
				} else { 
					System.err.println("password incorrecto o usuario inactivo : " + login.getUserName());
					return null;
				}
			}
	    }catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	
	
	
	
}
