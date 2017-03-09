package com.sisadmin.admin.controller;




public class RoleController {  }
	
	/**
	@Autowired
	protected RoleRepository roleRepository;
	
	@Autowired
	protected MenuRepository menuRepository;
	
	@Autowired
	protected RoleMenuRepository roleMenuRepository;
	
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/role")
	public @ResponseBody Role get(@RequestParam(value="id") String id) {
		Role tmpRole = roleRepository.findOne(id);
		if (tmpRole != null) {
			return tmpRole;
		} else {
			System.out.println("Error role no encontrado " + id);
			throw new EntityNotFoundException();
		}
	}
	

	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/role")
	public @ResponseBody String saveRole(@RequestBody Role role, HttpServletRequest request){
		Role tmpRole = roleRepository.findOne(role.getCodRol());
		try {
		if (tmpRole != null) {
			tmpRole.setDescripcion(role.getDescripcion());
			tmpRole.setIsActive(role.getIsActive());
			roleRepository.save(tmpRole);
		} else {
			tmpRole = new Role();
			tmpRole.setCodRol(role.getCodRol());
			tmpRole.setDescripcion(role.getDescripcion());
			tmpRole.setIsActive(role.getIsActive());
			roleRepository.save(tmpRole);
		}
		} catch (Exception e) {
			System.out.println("Error al grabar role " + role.getCodRol());
			e.printStackTrace();
			//throw new RoleInfoException();
		}
		return "OK";
			
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/role")
	public @ResponseBody String deleteRole(@RequestParam(value="id") String id) {
		Role tmpRole = roleRepository.findOne(id);
		if (tmpRole != null) {
			//TODO borra solo si no tiene asignado ningun menu. 
			roleRepository.delete(tmpRole);
		} else {
			throw new EntityNotFoundException();
		}
		return "OK";
	}
	
	
	@RequestMapping(method=RequestMethod.POST, value="/api/v1/role/menu")
	public void saveRoleMenu(@RequestBody RoleMenu roleMenu) {
		RoleMenuPK roleMenuPK = new RoleMenuPK();
		roleMenuPK.setCodRol(roleMenu.getCodRol());
		roleMenuPK.setCodOpcionMenu(roleMenu.getCodOpcionMenu());
		
		RoleMenu tmpRoleMenu = roleMenuRepository.findOne(roleMenuPK);
		
		if (tmpRoleMenu != null) {
			System.out.println("Error asociacion Rol - menu ya existe" + roleMenu.getCodRol() + " " + roleMenu.getCodOpcionMenu());
			
		} else {
			roleMenuRepository.save(tmpRoleMenu);
		}
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/api/v1/role/menu")
	public void deleteRoleMenu(@RequestBody RoleMenu roleMenu) {
		RoleMenuPK roleMenuPK = new RoleMenuPK();
		roleMenuPK.setCodRol(roleMenu.getCodRol());
		roleMenuPK.setCodOpcionMenu(roleMenu.getCodOpcionMenu());
		
		RoleMenu tmpRoleMenu = roleMenuRepository.findOne(roleMenuPK);
		
		if (tmpRoleMenu != null) {
			
			roleMenuRepository.delete(tmpRoleMenu);
		} else {
			System.out.println("Error asociacion Rol - Menu no existe " + roleMenu.getCodRol() + " " + roleMenu.getCodOpcionMenu());
		}
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/role/menu")
	public @ResponseBody List<RoleMenu> getRoleMenuAll() {
		return (List<RoleMenu>) roleMenuRepository.findAll();
	}
	**/
	
	/************************ LOGIN CONTROLLER ***************************************
	public User validateUserDB(LoginCredentials login) {
		try {
			User usr = new User();
			usr.setCodUsuario(login.getUserName());
			usr.setTipoUsuario(login.getUserType());
			usr.setIsActive("Y");
			usr.setNombre("Cima It");
			if (usr == null) {
				System.out.println("Usuario :" + login.getUserName() + " no encontrado.");
				return null;
			} else {
				//TODO encriptar password
				//if (usr.getPassword().equals(login.getPassword()) && usr.getIsActive().equals("Y")) {
				if(true){
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
	}**/
	
	/**************************************   USER CONTROLLER  *****************************************************
	@RequestMapping(method=RequestMethod.GET, value="/api/v1/user")
			public UserInfo getUser( @RequestParam(value="id") String id, @RequestParam(value="type") String type) {
				User tmpUser = userRepository.findByRucAndCodUsuarioAndTipoUsuario(globalConfig.getGlobalId(), id, type);
				UserInfo userInfo = new UserInfo();
				//remover dato 
				if (tmpUser != null) { 
					userInfo.setCodUsuario(tmpUser.getCodUsuario());
					userInfo.setEmail(tmpUser.getEmail());
					userInfo.setIsActive(tmpUser.getIsActive());
					userInfo.setNombre(tmpUser.getNombre());
					userInfo.setRuc(tmpUser.getRuc());
					userInfo.setRucEmpresa(tmpUser.getRucEmpresa());
					userInfo.setTipoUsuario(tmpUser.getTipoUsuario());
					//capturo los correos de empresa
					
					CustomerPK custPK = new CustomerPK();
					custPK.setRuc(globalConfig.getGlobalId());
					custPK.setRucCliente(userInfo.getRucEmpresa().trim());
					Customer cust = customerRepository.findOne(custPK);
					
					if (cust == null) throw new EntityNotFoundException();
					
					userInfo.setEmails(cust.getEmail());
					
				}
				else {
					throw new EntityNotFoundException();
				}
				return userInfo;
			}
			
			
			@RequestMapping(method=RequestMethod.GET, value="/api/v1/user/types")
			public @ResponseBody List<UserTypeInfo> getUserTypes() {
			
				List<UserTypeInfo> lUserTypes = new ArrayList<UserTypeInfo>();
				List<General> lGeneral = generalRepository.findByCodTabla("1");
				for (General general : lGeneral) {
					UserTypeInfo idType = new UserTypeInfo();
					idType.setCodigo(general.getCodUnico());
					idType.setDescripcion(general.getDescripcion());
					lUserTypes.add(idType);
				}
				return lUserTypes;
			}	
				List<UserTypeInfo> lUserTypes = new ArrayList<UserTypeInfo>();
				List<General> lGeneral = generalRepository.findByCodTabla("1");
				//List<General> lGeneral = (List<General>)generalRepository.findAll();
				for (General general : lGeneral) {
					IdentificationTypeInfo idType = new IdentificationTypeInfo();
					idType.setCodigo(general.getValue());
					idType.setDescripcion(general.getDescripcion());
					lUserTypes.add(idType)
				
				UserTypeInfo idType1 = new UserTypeInfo();
				idType1.setCodigo("E");
				idType1.setDescripcion("EMPLEADO");
				UserTypeInfo idType2 = new UserTypeInfo();
				idType2.setCodigo("C");
				idType2.setDescripcion("CLIENTE");
				UserTypeInfo idType3 = new UserTypeInfo();
				idType3.setCodigo("P");
				idType3.setDescripcion("PROVEEDOR");
				lUserTypes.add(idType1);
				lUserTypes.add(idType2);
				lUserTypes.add(idType3);
				return lUserTypes;
			}
			
		
			@RequestMapping(method=RequestMethod.POST,value="/api/v1/user/login")
			public @ResponseBody LoginCredentials get(@RequestBody LoginCredentials login, HttpServletRequest request) {
				String token ="";
				String inUserName = login.getUserName();
				String inPassword = login.getPassword();
				if (inUserName == null && inPassword == null) {
					throw new UnAuthorizedException();
				} else {
					if (inUserName != null && inPassword != null ) {
						User user = validateUserDB(login);
						if (user != null) {
							try {
								token = authHelper.createJsonWebToken(inUserName, request.getRemoteHost()+"(" + request.getRemoteAddr()+")",1L);
							} catch (JOSEException e) {
								e.printStackTrace();
							}
							
							String role = null;
							if (login.getUserType().equals("E")) {
								UserRole usrRole = userRoleRepository.findByRucAndCodUsuarioAndIsActive(globalConfig.getGlobalId(), inUserName, "Y");
								if (usrRole != null) role = usrRole.getCodRol();
							}
							return new LoginCredentials(inUserName, "", token, user.getNombre(),login.getUserType(), role);
						} else {
							throw new UnAuthorizedException();
						}
					} else { 
						throw new UnAuthorizedException();
					}
				}
		    }
		
			
			public User validateUserDB(LoginCredentials login) {
				try {
					User usr = userRepository.findByRucAndCodUsuarioAndTipoUsuario(globalConfig.getGlobalId(), login.getUserName(), login.getUserType());
					if (usr == null) {
						System.out.println("Usuario :" + login.getUserName() + " no encontrado.");
						return null;
					} else {
						//TODO encriptar password
						if (usr.getPassword().equals(login.getPassword()) && usr.getIsActive().equals("Y")) {
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
	**/

	/**************************************   BITACORA CONTROLLER  *****************************************************
			@RequestMapping(method=RequestMethod.GET, value="/api/v1/documentLog/list")
			@Transactional
			public @ResponseBody List<DocumentSearchDto> getAll(){
					List<DocumentLog> logs = (List<DocumentLog>) documentLogRepository.findAll();
					List<DocumentSearchDto> logDtos = new ArrayList<DocumentSearchDto>();
					for (DocumentLog log : logs) {
								logDtos.add(convertToDto(log));
					}
					return logDtos;		
			}
			
			@RequestMapping(method=RequestMethod.GET, value="/api/v1/documentLog")
			@Transactional
			public @ResponseBody List<DocumentDto> get(@RequestParam(value="documentId") String documentId){
					List<DocumentDto> logDtos = new ArrayList<DocumentDto>();
					DocumentDto dto = new DocumentDto();
					Document result = documentRepository.findOneDocument(documentId);
					List<DocumentLog> docLog = (List<DocumentLog>)documentLogRepository.findAll();
					for (int i= 0; i<docLog.size(); i++){
							if (docLog.get(i).getDocument().getId() == result.getId()){
									dto.setMessage(docLog.get(i).getMsg());
									dto.setState(docLog.get(i).getState());
									dto.setLogIssueDate(docLog.get(i).getDttm());
									dto.setLegalNumber(docLog.get(i).getDocument().getLegalNumber());
									dto.setCustomer(docLog.get(i).getDocument().getCustomer().getName());
									dto.setDocumentTypeCode(docLog.get(i).getDocument().getDocumentType().getDescr());
									logDtos.add(dto);	
							}
					}
					return logDtos;
			}
			
			DocumentDto documentTypeLog = new DocumentDto();
			documentTypeLog.setState(docLog.getState());
			documentTypeLog.setMessage(docLog.getMsg());
			documentTypeLog.setLogIssueDate(docLog.getDttm());
			documentTypeLog.setLegalNumber(docLog.getDocument().getLegalNumber());
			documentTypeLog.setCustomer(docLog.getDocument().getCustomer().getName());
			documentTypeLog.setDocumentTypeCode(docLog.getDocument().getDocumentType().getDescr());
			documentTypeLog.setId_document(docLog.getDocument().getId());
			return documentTypeLog;
			
			private DocumentSearchDto convertToDto(DocumentLog docLog) {
					DocumentSearchDto docDto = new DocumentSearchDto();
					docDto.setMessage(docLog.getMsg());
					docDto.setStatus(docLog.getState());
					docDto.setIssueDate(docLog.getDttm());
					if(docLog.getDocument()!= null)  docDto.setLegalNumber(docLog.getDocument().getLegalNumber());
					if(docLog.getDocument() != null && docLog.getDocument().getCustomer() != null) docDto.setCustomerName(docLog.getDocument().getCustomer().getName());
					if(docLog.getDocument() != null && docLog.getDocument().getDocumentType() != null)    docDto.setDocumentTypeCode(docLog.getDocument().getDocumentType().getDescr());
					return docDto;
			}
		
			
	**/
	
	
	/**************************************   COMBO CONTROLLER  *****************************************************

		@RequestMapping(method=RequestMethod.GET, value="/api/v1/combo/frequency/list")
		public @ResponseBody List<IdentificationTypeInfo> getFrequency() {
			
			List <IdentificationTypeInfo> lperiodicity = new ArrayList<IdentificationTypeInfo>();
			
			List<General> lGeneral = generalRepository.findByCodTabla("3");		
			for (General general : lGeneral) {
				IdentificationTypeInfo idType = new IdentificationTypeInfo();
				idType.setCodigo(general.getValue());
				idType.setDescripcion(general.getDescripcion());
				
				lperiodicity.add(idType);
			
			}
			
			return lperiodicity;
			
		}
	
		@RequestMapping(method=RequestMethod.GET, value="/api/v1/combo/customer/list/emitter")
		public @ResponseBody List<CustomerComboDto> getCustomerByEmitter(){
					List<Customer> customers = (List<Customer>) customerRepository.findByIdentification(globalConfig.GlobalId);
					List<CustomerComboDto> customerDtos = new ArrayList<CustomerComboDto>();
					for (Customer customer : customers) {
										customerDtos.add(convertToDto(customer));
					}
					return customerDtos;
		}
	
	
		**/
	
	
	
	
	/**************************************   DOCUMENT CONTROLLER  *****************************************************
		@RequestMapping(method=RequestMethod.GET, value="/api/v1/document/download", produces="application/octet")
		public void getFile(@RequestParam(value="documentTypeCode") String documentTypeCode, @RequestParam(value="legalNumber") String legalNumber, HttpServletResponse resp) throws IOException {
			
			String emitter = "20565812948";//obtenerlo del gloal config (archivo propertie)
			String pathAuth = "C://mario//invoice//repository//04-authorized//";//obtener el path de la tabla de propiedades mediante el ruc el cual se obtedra del archivo de propiedades
			String fileName= emitter + "-" + documentTypeCode + "-" + legalNumber;
			String absoluteFileName = pathAuth + fileName;
			
			System.out.println("Download de archivo : " + fileName);
			resp.setContentType("application/octet");
			resp.setHeader("Content-Disposition","attachment; filename=\"" + "mario.pdf" +"\"");
			InputStream inputStream = null;
			OutputStream outputStream = null;
			try {
					inputStream = new FileInputStream(new File(absoluteFileName));
					outputStream = resp.getOutputStream();
					IOUtils.copy(inputStream, outputStream);
			} catch (Exception e) {
					System.out.println("Error al obtener archivo " + fileName);
					e.printStackTrace();
			} finally {
					inputStream.close();
					outputStream.close();
			}				
		}

	
	
	
	**/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	