package unq.edu.li.pdes.micultura.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import unq.edu.li.pdes.micultura.dto.JwtResponseDTO;
import unq.edu.li.pdes.micultura.dto.UserDTO;
import unq.edu.li.pdes.micultura.service.impl.UserServiceImpl;
import unq.edu.li.pdes.micultura.vo.UserLoginVO;
import unq.edu.li.pdes.micultura.vo.UserVO;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

	private static final String EMAIL = "admin@gmail.com";
	private static final String PASSWORD = "admin";
	
	@Mock
	private UserServiceImpl service;
	
	@Mock
	private JwtResponseDTO jwtResponseDto;
	
	@Mock
	private UserDTO userDto;
	
	@InjectMocks
	private UserController controller;
	
	@Before
	public void setUp(){
		when(service.login(any())).thenReturn(jwtResponseDto);
		when(service.create(any())).thenReturn(userDto);
	}
	
	@Test
	public void testLoginUserThenReturnJwtResponseDTOWithToken(){
		var user = new UserLoginVO();
		user.setEmail(EMAIL);
		user.setPassword(PASSWORD);
		assertThat(controller.login(user), is(jwtResponseDto));
		verify(service).login(any());
	}
	
	@Test
	public void testCreateUserThenReturnANewUserDTO(){
		var user = new UserVO();
		user.setEmail(EMAIL);
		user.setPassword(PASSWORD);
		user.setRepeatPassword(PASSWORD);
		assertThat(controller.create(user), is(userDto));
		verify(service).create(any());
	}
}
