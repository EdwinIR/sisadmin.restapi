package com.sisadmin.admin.dto;

import com.sisadmin.entity.User;




public class UserInfo extends User {

	private String role ;
	private String newPassword;
	private String newRepeatPassword;
	private String empresa;
	

	public String getNewRepeatPassword() {
		return newRepeatPassword;
	}

	public void setNewRepeatPassword(String newRepeatpassword) {
		this.newRepeatPassword = newRepeatpassword;
	}

	public String emails;
	
	
	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	
	
	
}
