package unq.edu.li.pdes.micultura.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AccountVO {

	@JsonProperty("firstname")
	private String firstname;
	
	@JsonProperty("lastname")
	private String lastname;
	
	@JsonProperty("dni")
	private String dni;
	
	@JsonProperty("imagePath")
	private String imagePath;
	
	@JsonProperty("address")
	private String address;
	
	@JsonProperty("phoneNumber")
	private String phoneNumber;
	
	@JsonProperty("role")
	private String role;
}
