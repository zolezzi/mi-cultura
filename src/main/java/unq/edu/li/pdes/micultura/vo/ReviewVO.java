package unq.edu.li.pdes.micultura.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ReviewVO {

	@JsonProperty("comments")
	private String comments;
	
	@JsonProperty("score")
	private Double score;
}
