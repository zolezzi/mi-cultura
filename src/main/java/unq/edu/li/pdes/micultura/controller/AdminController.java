package unq.edu.li.pdes.micultura.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import unq.edu.li.pdes.micultura.dto.UserDTO;
import unq.edu.li.pdes.micultura.service.impl.UserServiceImpl;

@RestController("admin")
@Api(value = "Admin Controller")
@RequestMapping("/api")
@RequiredArgsConstructor
public class AdminController {

	private final UserServiceImpl service;
	
    @ApiOperation(
            value = "Service that return a User",
            notes = "This service return a User by the ID",
            nickname = "findById",
            response = UserDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded.", response = UserDTO.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error.", response = UserDTO.class) })
    @ApiImplicitParam(name = "Authorization",required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @GetMapping(
            value = "/find-by-id/{id}",
            produces = { "application/json" }
    )
	public UserDTO findById(@PathVariable("id") Long id) {
		return service.findById(id);
	}
    
    @ApiOperation(
            value = "This service update a User",
            notes = "Update a User, if it doesn't find it throw an exception",
            nickname = "update",
            response = UserDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded.", response = UserDTO.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error.", response = UserDTO.class) })
    @ApiImplicitParam(name = "Authorization",required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @PutMapping(
            value = "/update/{userId}",
            produces = { "application/json" }
    )
    public UserDTO update(@RequestBody UserDTO user, @PathVariable("userId") Long userId){
        return service.update(user, userId);
    }
    
    @ApiOperation(
            value = "This service delete a User",
            notes = "Delete a User, if it doesn't find it throw an exception",
            nickname = "deleteById",
            response = BasicResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded.", response = BasicResponse.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error.", response = BasicResponse.class) })
    @ApiImplicitParam(name = "Authorization",required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @DeleteMapping(
            value = "/delete/{userId}/{id}",
            produces = { "application/json" }
    )
    public BasicResponse deleteById(@PathVariable("userId") Long userId, @PathVariable("id") Long id) throws Exception{
    	service.deleteById(userId, id);
        return new BasicResponse("Successfully deleted", Boolean.FALSE);
    }
	
	@ApiOperation(
            value = "Service that returns all users",
            notes = "This service returns all users load",
            nickname = "findAll",
            response = UserDTO.class, 
            responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded.", response = UserDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error.", response = UserDTO.class, responseContainer = "List") })
    @ApiImplicitParam(name = "Authorization",required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @GetMapping(
            value = "/find-all",
            produces = { "application/json" }
    )
    public List<UserDTO> findAll(){
        return service.findAll();
    }
}
