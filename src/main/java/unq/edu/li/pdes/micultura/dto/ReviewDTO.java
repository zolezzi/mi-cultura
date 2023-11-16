package unq.edu.li.pdes.micultura.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ReviewDTO {

	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("comments")
	private String comments;
	
	@JsonProperty("score")
	private Double score;
}
