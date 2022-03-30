package br.com.mfsdevsystems.dscatalog.dto;

import br.com.mfsdevsystems.dscatalog.services.validation.UserInsertValid;

@UserInsertValid
public class UserInsertDTO extends UserDTO {
	
	private static final long serialVersionUID = 1L;
	
	private String password ;
	
	public UserInsertDTO() {
		
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password ) {
		this.password = password ;
	}
	
}
