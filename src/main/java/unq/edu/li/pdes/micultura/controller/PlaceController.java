package unq.edu.li.pdes.micultura.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import unq.edu.li.pdes.micultura.controller.response.BasicResponse;
import unq.edu.li.pdes.micultura.dto.PlaceDTO;
import unq.edu.li.pdes.micultura.service.impl.PlaceServiceImpl;
import unq.edu.li.pdes.micultura.vo.PlaceVO;
import unq.edu.li.pdes.micultura.vo.ReviewVO;

@RestController("place")
@Api(value = "Place Controller")
@RequestMapping("/api/place")
@RequiredArgsConstructor
public class PlaceController {

	private final PlaceServiceImpl service;
	
    @ApiOperation(
            value = "Service that return a Place",
            notes = "This service return a Place by the ID",
            nickname = "findById",
            response = PlaceDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded.", response = PlaceDTO.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error.", response = PlaceDTO.class) })
    @ApiImplicitParam(name = "Authorization",required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @GetMapping(
            value = "/find-by-id/{id}",
            produces = { "application/json" }
    )
	public PlaceDTO findById(@PathVariable("id") Long id) {
		return service.findById(id);
	}
    
    @ApiOperation(
            value = "This service save a Place",
            notes = "Service that return PlaceDTO with saved object Place",
            nickname = "save",
            response = PlaceDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded.", response = PlaceDTO.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error.", response = PlaceDTO.class) })
    @ApiImplicitParam(name = "Authorization",required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @PostMapping(
            value = "/save/{userId}/{placeId}",
            produces = { "application/json" }
    )
    public PlaceDTO save(@RequestBody PlaceVO placeVO,  @PathVariable("userId") Long userId, @PathVariable("placeId") Long placeId){
        return service.save(placeVO, userId, placeId);
    }
    
    @ApiOperation(
            value = "This service update a Place",
            notes = "Update a Place, if it doesn't find it throw an exception",
            nickname = "update",
            response = PlaceDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded.", response = PlaceDTO.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error.", response = PlaceDTO.class) })
    @ApiImplicitParam(name = "Authorization",required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @PutMapping(
            value = "/update/{userId}/{placeId}",
            produces = { "application/json" }
    )
    public PlaceDTO update(@RequestBody ReviewVO review, @PathVariable("userId") Long userId, @PathVariable("placeId") Long placeId){
        return service.update(review, userId, placeId);
    }
    
    @ApiOperation(
            value = "This service delete a place",
            notes = "Delete a place, if it doesn't find it throw an exception",
            nickname = "removeFavorite",
            response = BasicResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded.", response = BasicResponse.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error.", response = BasicResponse.class) })
    @ApiImplicitParam(name = "Authorization",required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @DeleteMapping(
            value = "/delete/{accountId}/{placeId}",
            produces = { "application/json" }
    )
    public BasicResponse removeFavorite(@PathVariable("accountId") Long accountId, @PathVariable("placeId") Long placeId) throws Exception{
    	service.removeFavorite(accountId, placeId);
        return new BasicResponse("Successfully deleted", Boolean.FALSE);
    }
    
	@ApiOperation(
            value = "Service that returns all places by user",
            notes = "This service returns all places load by user",
            nickname = "findAllByUserId",
            response = PlaceDTO.class, 
            responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded.", response = PlaceDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error.", response = PlaceDTO.class, responseContainer = "List") })
    @ApiImplicitParam(name = "Authorization",required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @GetMapping(
            value = "/find-all-by-user-id/{userId}",
            produces = { "application/json" }
    )
    public List<PlaceDTO> findAllByUserId(@PathVariable("userId") Long userId){
        return service.findAllByUserId(userId);
    }
	
	@ApiOperation(
            value = "Service that returns all places",
            notes = "This service returns all places load",
            nickname = "findAll",
            response = PlaceDTO.class, 
            responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded.", response = PlaceDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error.", response = PlaceDTO.class, responseContainer = "List") })
    @ApiImplicitParam(name = "Authorization",required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @GetMapping(
            value = "/find-all",
            produces = { "application/json" }
    )
    public List<PlaceDTO> findAll(){
        return service.findAll();
    }
	
	@ApiOperation(
            value = "Service that returns all value score",
            notes = "This service returns all value score",
            nickname = "getTotalReviewScore",
            response = BigDecimal.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The requesvt has succeeded.", response = BigDecimal.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error.", response = BigDecimal.class) })
    @ApiImplicitParam(name = "Authorization",required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @GetMapping(
            value = "/total-review-score/{placeId}",
            produces = { "application/json" }
    )
    public BigDecimal getTotalReviewScore(@PathVariable("placeId") Long placeId){
        return service.getTotalReviewScore(placeId);
    }
	
	@ApiOperation(
            value = "This service update a Place",
            notes = "Update a Place, if it doesn't find it throw an exception",
            nickname = "favorite",
            response = PlaceDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded.", response = PlaceDTO.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error.", response = PlaceDTO.class) })
    @ApiImplicitParam(name = "Authorization",required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @PutMapping(
            value = "/favorite/{accountId}/{placeId}",
            produces = { "application/json" }
    )
    public PlaceDTO update(@PathVariable("accountId") Long accountId, @PathVariable("placeId") Long placeId){
        return service.favorite(accountId, placeId);
    }
}
