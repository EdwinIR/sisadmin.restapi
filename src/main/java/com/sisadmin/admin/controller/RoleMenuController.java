package com.sisadmin.admin.controller;



import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sisadmin.admin.dto.MenuDto;
import com.sisadmin.entity.Menu;
import com.sisadmin.entity.Role;
import com.sisadmin.entity.RoleMenu;
import com.sisadmin.service.repository.MenuService;
import com.sisadmin.service.repository.RoleMenuService;


@Controller
public class RoleMenuController {

	@Autowired
	protected RoleMenuService roleMenuService;
	
	@Autowired
	protected MenuService menuService;
	
	protected Role role;
	
	
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/menu/list")
	public @ResponseBody List<MenuDto> getAll() {
		List<Menu> menues = (List<Menu>)menuService.findAll();		
		//List<RoleMenu> roleMenulist = (List<RoleMenu>)roleMenuService.findAll();	
		List<MenuDto> menuDtos = new ArrayList<MenuDto>();
		for (Menu menu : menues) {
			menuDtos.add(convertToDto(menu,null));
		}
		return menuDtos;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/loadRoleMenu")
	public @ResponseBody List<MenuDto> getRoleMenu(@RequestBody Role role) {
		List<Menu> menues = (List<Menu>)menuService.findAll();		
		List<RoleMenu> roleMenulist = findListRoleMenu(role.getCodRol());	
		List<MenuDto> menuDtos = new ArrayList<MenuDto>();
		for (Menu menu : menues) {
			menuDtos.add(convertToDto(menu,roleMenulist));
		}
		return menuDtos;
	}
	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/rolemenu/filter")
	public @ResponseBody List<RoleMenu> findMenues(@RequestBody Role role){
		List<RoleMenu> listRoleMenu =  roleMenuService.findByCodRoleMenu(role.getCodRol());
		return listRoleMenu;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/menu/listRoleMenu")
	public @ResponseBody List<RoleMenu> findListRoleMenu(@RequestParam(value="codRol") String codRol){
		List<RoleMenu> listRoleMenu =  roleMenuService.findByCodRoleMenu(codRol);
		return listRoleMenu;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/api/v1/saveListRoleMenu")
	public @ResponseBody String saveMenu(@RequestBody List<MenuDto> menuDtos, HttpServletRequest request) {
		try {
			List<RoleMenu> roleMenulist = findListRoleMenu(role.getCodRol());
			roleMenuService.delete(roleMenulist);
			List<RoleMenu> roleMenu = new ArrayList<RoleMenu>();
			
			for (MenuDto menuDto : menuDtos) {
				if(menuDto.isSeleccionado()==true){
					RoleMenu roleMenuActive = new RoleMenu();
					roleMenuActive.setCodOpcionMenu(menuDto.getCodOpcionMenu());
					roleMenuActive.setCodRol(role.getCodRol());
					roleMenu.add(roleMenuActive);
				}
			}
			roleMenuService.save(roleMenu);
			
		} catch (Exception e) {/*
			System.out.println("Error al grabar role " + role.getCodRol());*/
			e.printStackTrace();
			// throw new RoleInfoException();
		}
		
		return "OK";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/api/v1/setRole")
	public @ResponseBody String setRole(@RequestBody Role role, HttpServletRequest request) {
		try {
			this.role = role;
			
		} catch (Exception e) {
			System.out.println("Error al setear variable role con " + role.getCodRol());
			e.printStackTrace();
		}
		
		return "OK";
	}

	
	
	
	
	
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/menu/listCodPadre")
	public @ResponseBody List<Menu> findListMenu(@RequestParam(value="codMenuPadre") String codMenuPadre){
		List<Menu> listMenu = menuService.findByCodPadre(codMenuPadre);
		return listMenu;
	}
	
	
	private MenuDto convertToDto(Menu in, List<RoleMenu> roleMenuList) {
		MenuDto menuDto = new MenuDto();
		menuDto.setCodOpcionMenu(in.getCodOpcionMenu());
		menuDto.setCodOpcionMenuPadre(in.getCodOpcionMenuPadre());
		menuDto.setDescripcion(in.getDescripcion());
		menuDto.setSeleccionado(false);
		for (RoleMenu roleMenu : roleMenuList) {
			if(roleMenu.getCodOpcionMenu().trim().equalsIgnoreCase(in.getCodOpcionMenu().trim())){
				menuDto.setSeleccionado(true);
				break;
			};
		}
		return menuDto;
	}
	
	
	
}
