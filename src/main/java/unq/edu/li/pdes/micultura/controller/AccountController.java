package unq.edu.li.pdes.micultura.controller;

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
import unq.edu.li.pdes.micultura.dto.AccountDTO;
import unq.edu.li.pdes.micultura.service.impl.AccountServiceImpl;
import unq.edu.li.pdes.micultura.vo.AccountVO;

@RestController("account")
@Api(value = "Account Controller")
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

	private final AccountServiceImpl service;
	
    @ApiOperation(
            value = "Service that return a Account",
            notes = "This service return a Account by the ID",
            nickname = "findById",
            response = AccountDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded.", response = AccountDTO.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error.", response = AccountDTO.class) })
    @ApiImplicitParam(name = "Authorization",required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @GetMapping(
            value = "/find-by-id/{id}",
            produces = { "application/json" }
    )
	public AccountDTO findById(@PathVariable("id") Long id) {
		return service.findById(id);
	}
    
    @ApiOperation(
            value = "This service save a Account",
            notes = "Service that return AccountDTO with saved object Account",
            nickname = "save",
            response = AccountDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded.", response = AccountDTO.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error.", response = AccountDTO.class) })
    @ApiImplicitParam(name = "Authorization",required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @PostMapping(
            value = "/save",
            produces = { "application/json" }
    )
    public AccountDTO save(@RequestBody AccountVO accountVO){
        return service.save(accountVO);
    }
    
    @ApiOperation(
            value = "This service update a Account",
            notes = "Update a Account, if it doesn't find it throw an exception",
            nickname = "update",
            response = AccountDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded.", response = AccountDTO.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error.", response = AccountDTO.class) })
    @ApiImplicitParam(name = "Authorization",required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @PutMapping(
            value = "/update/{userId}/{id}",
            produces = { "application/json" }
    )
    public AccountDTO update(@RequestBody AccountDTO account, @PathVariable("userId") Long userId, @PathVariable("id") Long id){
        return service.update(account, userId, id);
    }
    
    @ApiOperation(
            value = "This service delete a Account",
            notes = "Delete a Account, if it doesn't find it throw an exception",
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
            value = "Service that returns all Accounts",
            notes = "This service returns all Accounts load",
            nickname = "findAll",
            response = AccountDTO.class, 
            responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The request has succeeded.", response = AccountDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error.", response = AccountDTO.class, responseContainer = "List") })
    @ApiImplicitParam(name = "Authorization",required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @GetMapping(
            value = "/find-all",
            produces = { "application/json" }
    )
    public List<AccountDTO> findAll(){
        return service.findAll();
    }
}
