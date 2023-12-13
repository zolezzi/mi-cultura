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
import unq.edu.li.pdes.micultura.dto.EventDTO;
import unq.edu.li.pdes.micultura.service.impl.EventServiceImpl;
import unq.edu.li.pdes.micultura.vo.EventVO;
import unq.edu.li.pdes.micultura.vo.ReviewVO;

@RestController("event")
@Api(value = "Event Controller")
@RequestMapping("/api/event")
@RequiredArgsConstructor
public class EventController {

	private final EventServiceImpl service;
	
    @ApiOperation(
            value = "Service that return a Event",
            notes = "This service return a Event by the ID",
            nickname = "findById",
            response = EventDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded.", response = EventDTO.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error.", response = EventDTO.class) })
    @ApiImplicitParam(name = "Authorization",required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @GetMapping(
            value = "/find-by-id/{id}",
            produces = { "application/json" }
    )
	public EventDTO findById(@PathVariable("id") Long id) {
		return service.findById(id);
	}
    
    @ApiOperation(
            value = "This service save a Event",
            notes = "Service that return EventDTO with saved object Event",
            nickname = "save",
            response = EventDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded.", response = EventDTO.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error.", response = EventDTO.class) })
    @ApiImplicitParam(name = "Authorization",required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @PostMapping(
            value = "/save/{userId}/{eventId}",
            produces = { "application/json" }
    )
    public EventDTO save(@RequestBody EventVO eventVO,  @PathVariable("userId") Long userId, @PathVariable("eventId") Long eventId){
        return service.save(eventVO, userId, eventId);
    }
    
    @ApiOperation(
            value = "This service update a Event",
            notes = "Update a Event, if it doesn't find it throw an exception",
            nickname = "update",
            response = EventDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded.", response = EventDTO.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error.", response = EventDTO.class) })
    @ApiImplicitParam(name = "Authorization",required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @PutMapping(
            value = "/update/{userId}/{eventId}",
            produces = { "application/json" }
    )
    public EventDTO update(@RequestBody ReviewVO review, @PathVariable("userId") Long userId, @PathVariable("eventId") Long eventId){
        return service.update(review, userId, eventId);
    }
    
    @ApiOperation(
            value = "This service delete a event",
            notes = "Delete a event, if it doesn't find it throw an exception",
            nickname = "removeFavorite",
            response = BasicResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded.", response = BasicResponse.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error.", response = BasicResponse.class) })
    @ApiImplicitParam(name = "Authorization",required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @DeleteMapping(
            value = "/delete/{accountId}/{eventId}",
            produces = { "application/json" }
    )
    public BasicResponse removeFavorite(@PathVariable("accountId") Long accountId, @PathVariable("eventId") Long eventId) throws Exception{
    	service.removeFavorite(accountId, eventId);
        return new BasicResponse("Successfully deleted", Boolean.FALSE);
    }
    
	@ApiOperation(
            value = "Service that returns all events by user",
            notes = "This service returns all events load by user",
            nickname = "findAllByUserId",
            response = EventDTO.class, 
            responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded.", response = EventDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error.", response = EventDTO.class, responseContainer = "List") })
    @ApiImplicitParam(name = "Authorization",required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @GetMapping(
            value = "/find-all-by-user-id/{userId}",
            produces = { "application/json" }
    )
    public List<EventDTO> findAllByUserId(@PathVariable("userId") Long userId){
        return service.findAllByUserId(userId);
    }
	
	@ApiOperation(
            value = "Service that returns all events",
            notes = "This service returns all events load",
            nickname = "findAll",
            response = EventDTO.class, 
            responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded.", response = EventDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error.", response = EventDTO.class, responseContainer = "List") })
    @ApiImplicitParam(name = "Authorization",required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @GetMapping(
            value = "/find-all",
            produces = { "application/json" }
    )
    public List<EventDTO> findAll(){
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
            value = "/total-review-score/{eventId}",
            produces = { "application/json" }
    )
    public BigDecimal getTotalReviewScore(@PathVariable("eventId") Long eventId){
        return service.getTotalReviewScore(eventId);
    }
	
	@ApiOperation(
            value = "This service update a Event",
            notes = "Update a Event, if it doesn't find it throw an exception",
            nickname = "favorite",
            response = EventDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded.", response = EventDTO.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error.", response = EventDTO.class) })
    @ApiImplicitParam(name = "Authorization",required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @PutMapping(
            value = "/favorite/{accountId}/{eventId}",
            produces = { "application/json" }
    )
    public EventDTO update(@RequestBody EventVO eventVO, @PathVariable("accountId") Long accountId, @PathVariable("eventId") Long eventId){
        return service.favorite(eventVO, accountId, eventId);
    }
}
