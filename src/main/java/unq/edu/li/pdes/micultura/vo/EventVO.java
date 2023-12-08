package unq.edu.li.pdes.micultura.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class EventVO {

	@JsonProperty("eventId")
	private Long eventId;
	
	@JsonProperty("url")
	private String url;
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("subTitle")
	private String subTitle;
	
	@JsonProperty("body")
	private String body;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("dependsOn")
	private String dependsOn;
	
	@JsonProperty("link")
	private String link;
	
	@JsonProperty("image")
	private String image;
	
	@JsonProperty("state")
	private String state;
	
	@JsonProperty("fromDate")
	private String fromDate;
	
	@JsonProperty("toDate")
	private String toDate;
	
	@JsonProperty("eventType")
	private String eventType;
}
