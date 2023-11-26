package unq.edu.li.pdes.micultura.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AccountReviewDetailsDTO {

	@JsonProperty("accountId")
	private Long accountId;
	
	@JsonProperty("firstname")
	private String firstname;
	
	@JsonProperty("lastname")
	private String lastname;
	
	@JsonProperty("dni")
	private String dni;
	
	@JsonProperty("reviewId")
	private Long reviewId;
	
	@JsonProperty("comments")
	private String comments;
	
	@JsonProperty("score")
	private Double score;
	
	@JsonProperty("placeName")
	private String placeName;
	
	@JsonProperty("placeId")
	private Long placeId;
	
	@JsonProperty("placeType")
	private String placeType;
	
	@JsonProperty("placeTypeDescription")
	private String placeTypeDescription;
	
	@JsonProperty("eventId")
	private Long eventId;
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("eventType")
	private String eventType;
	
	@JsonProperty("eventTypeDescription")
	private String eventTypeDescription;
}
