package unq.edu.li.pdes.micultura.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import unq.edu.li.pdes.micultura.dto.UserDTO;
import unq.edu.li.pdes.micultura.exception.UserCreateException;
import unq.edu.li.pdes.micultura.mapper.Mapper;
import unq.edu.li.pdes.micultura.model.User;
import unq.edu.li.pdes.micultura.repository.UserRepository;
import unq.edu.li.pdes.micultura.service.impl.UserServiceImpl;
import unq.edu.li.pdes.micultura.utils.EncodeAndDecodeCrypt;
import unq.edu.li.pdes.micultura.utils.TokenUtils;
import unq.edu.li.pdes.micultura.vo.UserLoginVO;
import unq.edu.li.pdes.micultura.vo.UserVO;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	private static final String EMAIL = "admin@gmail.com";
	private static final String PASSWORD = "HolaAdmin123!";
	private static final String USER_NOT_FOUND = "USER2";
	private static final String TOKEN_VALUE = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY3NTMwMTAyNCwibmFtZSI6ImFkbWluIn0.1nWH5XPt71NJ3cC4nzrvXPLr31DnXg5iUUFg0dyemHbhYirBI4IMbR0KH_iNnt0x_dAwNWZco66w8XCOXIut9g";
	private static final String EMAIL_CREATE = "admin2@gmail.com";
	
	@Mock
	private User user;
	
	@Mock
	private User userCreate;
	
	@Mock
	private UserDTO userDto;
	
	@Mock
	private UserVO userVO;
	
	@Mock
	private UserRepository repository;
	
	@Mock
	private AuthenticationManager authenticationManager;
	
    
	@Mock
	private TokenUtils tokenUtil;
	
	@Mock
	private Mapper mapper;
	
	@Mock
	private EncodeAndDecodeCrypt encodeAndDecodeCrypt;
	
	@Rule
	public ExpectedException ex = ExpectedException.none();
	
	@InjectMocks
	private UserServiceImpl service;
	
	@Before
	public void setUp(){
		service = new UserServiceImpl(repository, authenticationManager, mapper, tokenUtil, encodeAndDecodeCrypt);
		
		when(repository.findOneByEmail(eq(EMAIL))).thenReturn(Optional.of(user));
		when(user.getUsername()).thenReturn(EMAIL);
		when(tokenUtil.createToken(eq(EMAIL))).thenReturn(TOKEN_VALUE);
		when(mapper.map(any(), eq(User.class))).thenReturn(user);
		when(user.getEmail()).thenReturn(EMAIL_CREATE);
		when(user.getPassword()).thenReturn(PASSWORD);
		when(repository.findOneByEmail(EMAIL_CREATE)).thenReturn(Optional.empty());
		when(mapper.map(any(), eq(UserDTO.class))).thenReturn(userDto);
		when(userDto.getEmail()).thenReturn(EMAIL_CREATE);
		when(userDto.getPassword()).thenReturn(PASSWORD);
		when(mapper.map(eq(userVO), eq(User.class))).thenReturn(userCreate);
		when(userCreate.getEmail()).thenReturn(EMAIL);
		when(userCreate.getPassword()).thenReturn(PASSWORD);
		when(userVO.getPassword()).thenReturn(PASSWORD);
		when(userVO.getRepeatPassword()).thenReturn(PASSWORD);
	}
	
	@Test
	public void testLoginUserValidThenReturnJWTResponseWithUsernameAndToken(){
		var user = new UserLoginVO();
		user.setEmail(EMAIL);
		user.setPassword(PASSWORD);
		var jwtResponseDTO = service.login(user);
	    assertThat(jwtResponseDTO.getEmail(), is(EMAIL));
	    assertThat(jwtResponseDTO.getToken(), is(TOKEN_VALUE));
		assertNotNull(jwtResponseDTO.toString());
		assertNotNull(jwtResponseDTO.hashCode());
	    verify(repository).findOneByEmail(eq(EMAIL));
	    verify(authenticationManager).authenticate(any());
	}
	
	@Test
	public void testLoginUserNotFoundThenReturnThenReturnException(){
		ex.expect(UsernameNotFoundException.class);
		ex.expectMessage("No found user:USER2");
		var user = new UserLoginVO();
		user.setEmail(USER_NOT_FOUND);
		user.setPassword(PASSWORD);
		service.login(user);
		verify(repository, never()).findOneByEmail(eq(USER_NOT_FOUND));
	}
	
	@Test
	public void testCreateUserValidThenReturnANewUser(){
		var user = new UserVO();
		user.setEmail(EMAIL_CREATE);
		user.setPassword(PASSWORD);
		user.setRepeatPassword(PASSWORD);
		var userDTO = service.create(user);
	    assertThat(userDTO.getEmail(), is(EMAIL_CREATE));
	    assertThat(userDTO.getPassword(), is(PASSWORD));
		assertNotNull(userDTO.toString());
		assertNotNull(userDTO.hashCode());
	}
	
	@Test
	public void testCreateUserWithUserExistInDatabaseThenReturnThenReturnException(){
		ex.expect(UserCreateException.class);
		ex.expectMessage("Error register user resolved errors: [There is already a registered user with this email:admin@gmail.com ] ");
		service.create(userVO);
		verify(repository).findOneByEmail(eq(EMAIL_CREATE));
	}
}
