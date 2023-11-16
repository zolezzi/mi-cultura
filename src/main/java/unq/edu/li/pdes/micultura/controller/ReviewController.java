package unq.edu.li.pdes.micultura.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import unq.edu.li.pdes.micultura.dto.ReviewDTO;
import unq.edu.li.pdes.micultura.service.impl.ReviewServiceImpl;

@RestController("review")
@Api(value = "Review Controller")
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewServiceImpl service;
	
    @ApiOperation(
            value = "Service that return a Review",
            notes = "This service return a Review by the ID",
            nickname = "findById",
            response = ReviewDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded.", response = ReviewDTO.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error.", response = ReviewDTO.class) })
    @ApiImplicitParam(name = "Authorization",required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @GetMapping(
            value = "/find-by-id/{id}",
            produces = { "application/json" }
    )
	public ReviewDTO findById(@PathVariable("id") Long id) {
		return service.findById(id);
	}
}
