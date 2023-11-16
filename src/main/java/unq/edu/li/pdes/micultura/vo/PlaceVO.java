package unq.edu.li.pdes.micultura.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PlaceVO {

	@JsonProperty("placeId")
	private Long placeId;
	
	@JsonProperty("url")
	private String url;
	
	@JsonProperty("link")
	private String link;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("address")
	private String address;
	
	@JsonProperty("phoneNumber")
	private String phoneNumber;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("province")
	private String province;
	
	@JsonProperty("dependsOn")
	private String dependsOn;
	
	@JsonProperty("placeType")
	private String placeType;
	
	@JsonProperty("placeTypeDescription")
	private String placeTypeDescription;
	
	@JsonProperty("authorityName")
	private String authorityName;
	
	@JsonProperty("authorityPosition")
	private String authorityPosition;
	
	@JsonProperty("authorityEmail")
	private String authorityEmail;
}
