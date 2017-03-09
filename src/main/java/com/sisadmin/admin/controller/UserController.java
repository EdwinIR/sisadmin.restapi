package com.sisadmin.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sisadmin.admin.dto.UserInfo;
import com.sisadmin.api.exception.UserInfoException;
import com.sisadmin.api.exception.UserRoleInfoException;
import com.sisadmin.entity.Cliente;
import com.sisadmin.entity.User;
import com.sisadmin.entity.UserPK;
import com.sisadmin.entity.UserRole;
import com.sisadmin.entity.UserRolePK;
import com.sisadmin.service.crypto.SisfeJowtService;
import com.sisadmin.service.repository.ClienteService;
import com.sisadmin.service.repository.TablaGeneralService;
import com.sisadmin.service.repository.UserRoleService;
import com.sisadmin.service.repository.UserService;



@Controller
public class UserController {
	
	
	@Autowired
    protected UserService userService;
	
	@Autowired
	protected UserRoleService userRoleService;
	
	@Autowired
	protected ClienteService clienteService;
	
	@Autowired
	protected SisfeJowtService authHelper;
	
	
	
	@Autowired
	protected TablaGeneralService generalService;
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/user/list")
	public @ResponseBody List<UserInfo> getUserList(@RequestParam(value="tipoUsuario") String tipoUsuario){
		List<UserInfo> lUsers = new ArrayList<UserInfo>();
		for (User user : userService.findAllBytipoUsuario(tipoUsuario)) {
			UserInfo uInfo = new UserInfo();
			uInfo.setCodUsuario(user.getCodUsuario());
			uInfo.setIsActive(user.getIsActive());
			uInfo.setNombre(user.getNombre());
			uInfo.setRuc(user.getRuc());
			uInfo.setRucEmpresa(user.getRucEmpresa());
			uInfo.setTipoUsuario(user.getTipoUsuario());
			uInfo.setPassword(user.getPassword());
			uInfo.setNewRepeatPassword(user.getPassword());
			uInfo.setNewPassword(user.getPassword());
			uInfo.setEmail(user.getEmail());
			
			/**UserRole uRole = userRoleService.findByRucAndCodUsuario(user.getRuc(), user.getCodUsuario());
			Cliente customer = clienteService.findByIdentification(user.getRucEmpresa());
			if (uRole != null) {uInfo.setRole(uRole.getCodRol());} else {uInfo.setRole("");}
			if (customer != null) {uInfo.setEmpresa(customer.getRazonSocial());} else {uInfo.setEmpresa("");}**/
			lUsers.add(uInfo);
		}
		return lUsers;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/user/list/id")
	public @ResponseBody UserInfo getUserListId(UserPK id){
			UserInfo user = (UserInfo) userService.findOne(id);
			return user;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/user")
	public @ResponseBody String saveCustomer(@RequestBody UserInfo user, HttpServletRequest request){
		
		//User tmpUser = userRepository.findByRucAndCodUsuarioAndTipoUsuario(user.getRuc(), user.getCodUsuario(), user.getTipoUsuario());
		User tmpUser = userService.findByRucAndCodUsuario(user.getRuc(), user.getCodUsuario());
			if (tmpUser != null) {
				updateUser (user, tmpUser);
				
			} else {
				newUser(user);}
				
	return "OK";
	}		
				

		
	@RequestMapping(method=RequestMethod.DELETE,value="/api/v1/user")
	public @ResponseBody String deleteUser(@RequestParam(value="emitterId") String emitterId, @RequestParam(value="id") String id , @RequestParam(value="type") String type){
		User tmpUser = userService.findByRucAndCodUsuarioAndTipoUsuario(emitterId, id, type);
		if (tmpUser != null) {
			//se borra de la tabla usuarios_roles
			UserRole uRole = userRoleService.findByRucAndCodUsuario(emitterId, id);
			if (uRole != null) userRoleService.delete(uRole);

			//se borra usuario
			userService.delete(tmpUser);
		} else throw new EntityNotFoundException();
		return "OK";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/user/role")
	public void saveUserRole(@RequestBody UserRole userRole) {
		UserRolePK userRolePK = new UserRolePK();
		userRolePK.setCodRol(userRole.getCodRol());
		userRolePK.setRuc(userRole.getRuc());
		userRolePK.setCodUsuario(userRole.getCodUsuario());
		
		UserRole tmpUserRole = userRoleService.findOne(userRolePK);
		try {
		if (tmpUserRole != null) {
			tmpUserRole.setIsActive(userRole.getIsActive());
			userRoleService.save(tmpUserRole);
		} else {
			tmpUserRole = new UserRole();
			tmpUserRole.setCodRol(userRole.getCodRol());
			tmpUserRole.setCodUsuario(userRole.getCodUsuario());
			tmpUserRole.setIsActive(userRole.getIsActive());
			tmpUserRole.setRuc(userRole.getRuc());
			userRoleService.save(tmpUserRole);
		}
		} catch (Exception e) {
			System.out.println("Error grabando UserRole");
			throw new UserRoleInfoException();
		}
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/user/role")
	public void deleteUserRole(@RequestBody UserRole userRole) {
		UserRolePK userRolePK = new UserRolePK();
		userRolePK.setCodRol(userRole.getCodRol());
		userRolePK.setRuc(userRole.getRuc());
		userRolePK.setCodUsuario(userRole.getCodUsuario());
		
		UserRole tmpUserRole = userRoleService.findOne(userRolePK);
		if (tmpUserRole != null ) {
			userRoleService.delete(tmpUserRole);
		} else {
			System.out.println("Error al eliminar asociacion usuario - role " + userRole.getCodUsuario() + " " + userRole.getCodRol());
			throw new UserRoleInfoException();
		}
	}
	
	
	
	private void newUser(UserInfo user) {
	
			User tmpUser = new User();
			tmpUser.setRuc(user.getRuc());
			tmpUser.setRucEmpresa(user.getRucEmpresa());
			tmpUser.setCodUsuario(user.getCodUsuario());
			tmpUser.setEmail(user.getEmail());
			tmpUser.setIsActive(user.getIsActive());
			tmpUser.setNombre(user.getNombre());
			tmpUser.setTipoUsuario(user.getTipoUsuario());
			tmpUser.setEmail(user.getEmail());
			
			try {
			//TODO encriptar password
			if (user.getPassword() != null && user.getNewPassword() != null)
				if (user.getPassword().trim().equals(user.getNewPassword().trim())) {
					String passIn = authHelper.encriptar(user.getPassword().trim());
					//tmpUser.setPassword(user.getPassword());
					tmpUser.setPassword(passIn);
					userService.save(tmpUser);
					
						if (user.getRole() != null ) {
							
							UserRole newRole = new UserRole();
							newRole.setCodRol(user.getRole());
							newRole.setCodUsuario(user.getCodUsuario());
							newRole.setIsActive(user.getIsActive());
							newRole.setRuc(user.getRuc());
							userRoleService.save(newRole);
							}
					} else {
					System.out.println("Error passwords distintos!.");
					throw new UserInfoException();
					}	
			}catch (Exception e){
			System.out.println("Error al grabar usuario :  " +  user.getCodUsuario());
			e.printStackTrace();
			throw new UserInfoException();
			}

		}

		private void updateUser(UserInfo user, User tmpUser) {

			if (user.getIsActive() != null) tmpUser.setIsActive(user.getIsActive());
			//if (user.getNombre() != null) tmpUser.setNombre(user.getNombre());
			tmpUser.setTipoUsuario(user.getTipoUsuario());
			tmpUser.setRucEmpresa(user.getRucEmpresa());
			tmpUser.setEmail(user.getEmail());
			//tmpUser.setNombre(user.getNombre());
	
			if (user.getCodUsuario() != null) {
		
				UserRole uRole = userRoleService.findByRucAndCodUsuario(user.getRuc(), user.getCodUsuario());
		 
				if (uRole != null) userRoleService.delete(uRole);
		
				uRole = new UserRole();
				uRole.setCodRol(user.getRole());
				uRole.setCodUsuario(user.getCodUsuario());
				uRole.setIsActive(user.getIsActive());
				uRole.setRuc(user.getRuc());
				
				userRoleService.save(uRole);
				}

				try{
				//if (user.getPassword()!= null) {
					if (tmpUser.getPassword().equals(user.getPassword())){
						tmpUser.setPassword(tmpUser.getPassword());
					} else {
						if (!user.getPassword().trim().equals(user.getNewRepeatPassword().trim())) {
						System.out.println("Los password no coinciden.");
						throw new UserInfoException();
						} else{
							String passIn = authHelper.encriptar(user.getNewPassword());
							tmpUser.setPassword(passIn);
								}
						}
				userService.save(tmpUser);
				}catch (Exception e){
					System.out.println("Error al grabar usuario :  " +  user.getCodUsuario());
					e.printStackTrace();
					throw new UserInfoException();
					}	


	
}


	
	
}
