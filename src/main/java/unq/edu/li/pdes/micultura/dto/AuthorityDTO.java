package unq.edu.li.pdes.micultura.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AuthorityDTO {

	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("position")
	private String position;
	
	@JsonProperty("email")
	private String email;
}
