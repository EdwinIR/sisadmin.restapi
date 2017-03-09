package com.sisadmin.admin.dto;

public class LoginCredentials {
	private String userName;
	private String password;
	private String token;
	private String userDescription;
	private String userType;
	private String userRole;
	private String companyIdentification;
	private Integer almacenId;
	
	public LoginCredentials() {
	}
	
	public LoginCredentials(String userName, String password, String token,
			String userDescription, String userType, String userRole, String companyIdentification, Integer almacenId) {
		super();
		this.userName = userName;
		this.password = password;
		this.token = token;
		this.userDescription = userDescription;
		this.userType = userType;
		this.userRole = userRole;
		this.companyIdentification = companyIdentification;
		this.almacenId = almacenId;
	}
	
	
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUserDescription() {
		return userDescription;
	}
	public void setUserDescription(String userDescription) {
		this.userDescription = userDescription;
	}
	
	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getCompanyIdentification() {
		return companyIdentification;
	}

	public void setCompanyIdentification(String companyIdentification) {
		this.companyIdentification = companyIdentification;
	}

	public Integer getAlmacenId() {
		return almacenId;
	}

	public void setAlmacenId(Integer almacenId) {
		this.almacenId = almacenId;
	}


	
}
