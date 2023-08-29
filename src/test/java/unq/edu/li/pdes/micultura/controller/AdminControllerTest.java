package unq.edu.li.pdes.micultura.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import unq.edu.li.pdes.micultura.dto.UserDTO;
import unq.edu.li.pdes.micultura.service.impl.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class AdminControllerTest {

	private static final Long ID = 1L;
	private static final Long ID_USER = 1L;

	@Mock
	private UserServiceImpl service;
	
	@Mock
	private UserDTO userDto;
	
	@InjectMocks
	private AdminController controller;
	
	@Before
	public void setUp(){
		when(service.findById(ID)).thenReturn(userDto);
		when(service.update(userDto, ID_USER)).thenReturn(userDto);
		when(service.findAll()).thenReturn(List.of(userDto));
	}
	
	@Test
	public void testfindByIdThenReturnAnUserDTO(){
		assertThat(controller.findById(ID), is(userDto));
		verify(service).findById(eq(ID));
	}
	
	@Test
	public void testupdateThenReturnUserDTO(){
		var response = controller.update(userDto, ID_USER);
		assertNotNull(response.toString());
		assertNotNull(response.hashCode());
		verify(service).update(eq(userDto), eq(ID_USER));
	}
	
	@Test
	public void testDeleteUserThenReturnBasicResponse() throws Exception{
		var response = controller.deleteById(ID_USER, ID);
		assertThat(response.getMessage(),is(notNullValue()));    
		assertThat(response.getError(),is(Boolean.FALSE));
		assertNotNull(response.toString());
		assertNotNull(response.hashCode());
		verify(service).deleteById(eq(ID_USER), eq(ID));
	}
	
	@Test
	public void testFindAllUsers(){
		assertThat(controller.findAll(), is(List.of(userDto)));
		verify(service).findAll();
	}
}
