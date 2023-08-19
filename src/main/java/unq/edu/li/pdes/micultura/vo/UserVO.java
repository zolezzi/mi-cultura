package unq.edu.li.pdes.micultura.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserVO {

	@JsonProperty("email")
	private String email;
	
	@JsonProperty("password")
	private String password;
	
	@JsonProperty("repeatPassword")
	private String repeatPassword;
	
	@JsonProperty("account")
	private AccountVO account;
}
