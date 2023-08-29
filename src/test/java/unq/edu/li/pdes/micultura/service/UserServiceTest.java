package unq.edu.li.pdes.micultura.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
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

import unq.edu.li.pdes.micultura.dto.AccountDTO;
import unq.edu.li.pdes.micultura.dto.UserDTO;
import unq.edu.li.pdes.micultura.exception.MiCulturaException;
import unq.edu.li.pdes.micultura.exception.UserCreateException;
import unq.edu.li.pdes.micultura.exception.UserNotFoundException;
import unq.edu.li.pdes.micultura.mapper.Mapper;
import unq.edu.li.pdes.micultura.model.Account;
import unq.edu.li.pdes.micultura.model.AccountRole;
import unq.edu.li.pdes.micultura.model.User;
import unq.edu.li.pdes.micultura.repository.AccountRepository;
import unq.edu.li.pdes.micultura.repository.UserRepository;
import unq.edu.li.pdes.micultura.service.impl.AccountServiceImpl;
import unq.edu.li.pdes.micultura.service.impl.UserServiceImpl;
import unq.edu.li.pdes.micultura.utils.EncodeAndDecodeCrypt;
import unq.edu.li.pdes.micultura.utils.TokenUtils;
import unq.edu.li.pdes.micultura.vo.AccountVO;
import unq.edu.li.pdes.micultura.vo.UserLoginVO;
import unq.edu.li.pdes.micultura.vo.UserVO;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	private static final String EMAIL = "admin@gmail.com";
	private static final String EMAIL_UPDATE = "admin_NEW@gmail.com";
	private static final String PASSWORD = "HolaAdmin123!";
	private static final String USER_NOT_FOUND = "USER2";
	private static final String TOKEN_VALUE = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY3NTMwMTAyNCwibmFtZSI6ImFkbWluIn0.1nWH5XPt71NJ3cC4nzrvXPLr31DnXg5iUUFg0dyemHbhYirBI4IMbR0KH_iNnt0x_dAwNWZco66w8XCOXIut9g";
	private static final String DNI = "35000111";
	private static final String FIRST_NAME = "TEST";
	private static final String LAST_NAME = "TEST";
	private static final String ACCOUNT_ROLE = "ADMIN";
	private static final String ACCOUNT_ROLE_DESCRIPTION = "ADMIN";
	private static final String EMAIL_CREATE = "admin2@gmail.com";
	private static final AccountRole ROLE_ADMIN = AccountRole.ADMIN;
	private static final Long ACCOUNT_ID = 1L;
	private static final Long USER_ID = 2L;
	private static final Long ID_USER_NOT_FOUND = 10L;
	private static final Long USER_ADMIN_ID = 11L;
	private static final Long ID_USER_DELETE = 12L;
	private static final Long ID_USER_UPDATE = 20L;
	
	@Mock
	private User user;
	
	@Mock
	private User userAdmin;
	
	@Mock
	private User userCreate;
	
	@Mock
	private User userDelete;
	
	@Mock
	private Account account;
	
	@Mock
	private Account accountDelete;
	
	@Mock
	private AccountDTO accountDto;
	
	@Mock
	private UserDTO userDto;
	
	@Mock
	private UserVO userVO;
	
	@Mock
	private UserRepository repository;
	
	@Mock
	private AccountRepository accountRepository;
	
	@Mock
	private AuthenticationManager authenticationManager;
	
	@Mock
	private AccountServiceImpl accountService;
	
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
		service = new UserServiceImpl(repository, accountRepository, authenticationManager, accountService, mapper, tokenUtil, encodeAndDecodeCrypt);
		when(repository.findOneByEmail(eq(EMAIL))).thenReturn(Optional.of(user));
		when(user.getUsername()).thenReturn(EMAIL);
		when(tokenUtil.createToken(eq(EMAIL))).thenReturn(TOKEN_VALUE);
		when(mapper.map(any(), eq(User.class))).thenReturn(user);
		when(user.getEmail()).thenReturn(EMAIL_CREATE);
		when(user.getPassword()).thenReturn(PASSWORD);
		when(user.getAccount()).thenReturn(account);
		when(account.getFirstname()).thenReturn(FIRST_NAME);
		when(account.getLastname()).thenReturn(LAST_NAME);
		when(account.getAccountRole()).thenReturn(ROLE_ADMIN);
		when(repository.findOneByEmail(EMAIL_CREATE)).thenReturn(Optional.empty());
		when(accountService.createAccountByUser(any())).thenReturn(account);
		when(mapper.map(any(), eq(UserDTO.class))).thenReturn(userDto);
		when(userDto.getEmail()).thenReturn(EMAIL_CREATE);
		when(userDto.getPassword()).thenReturn(PASSWORD);
		when(userDto.getAccount()).thenReturn(accountDto);
		when(accountDto.getId()).thenReturn(ACCOUNT_ID);
		when(accountDto.getDni()).thenReturn(DNI);
		when(accountDto.getFirstname()).thenReturn(FIRST_NAME);
		when(accountDto.getLastname()).thenReturn(LAST_NAME);
		when(accountDto.getRole()).thenReturn(ACCOUNT_ROLE);
		when(accountDto.getRoleDescripton()).thenReturn(ACCOUNT_ROLE_DESCRIPTION);
		when(mapper.map(eq(userVO), eq(User.class))).thenReturn(userCreate);
		when(userCreate.getEmail()).thenReturn(EMAIL);
		when(userCreate.getPassword()).thenReturn(PASSWORD);
		when(userVO.getPassword()).thenReturn(PASSWORD);
		when(userVO.getRepeatPassword()).thenReturn(PASSWORD);
		when(repository.findById(USER_ID)).thenReturn(Optional.of(user));
		when(repository.findById(ID_USER_NOT_FOUND)).thenReturn(Optional.empty());
		when(repository.findById(USER_ADMIN_ID)).thenReturn(Optional.of(userAdmin));
		when(userAdmin.isAdmin()).thenReturn(Boolean.TRUE);
		when(repository.findById(ID_USER_DELETE)).thenReturn(Optional.of(userDelete));
		when(userDelete.getAccount()).thenReturn(accountDelete);
		when(repository.findById(ID_USER_UPDATE)).thenReturn(Optional.of(user));
		when(repository.findAll()).thenReturn(List.of(user));
		when(mapper.mapList(anyList(), eq(UserDTO.class))).thenReturn(List.of(userDto));
	}
	
	@Test
	public void testLoginUserValidThenReturnJWTResponseWithUsernameAndToken(){
		var user = new UserLoginVO();
		user.setEmail(EMAIL);
		user.setPassword(PASSWORD);
		var jwtResponseDTO = service.login(user);
	    assertThat(jwtResponseDTO.getEmail(), is(EMAIL));
	    assertThat(jwtResponseDTO.getToken(), is(TOKEN_VALUE));
	    assertThat(jwtResponseDTO.getFirstname(), is(FIRST_NAME));
	    assertThat(jwtResponseDTO.getLastname(), is(LAST_NAME));
	    assertThat(jwtResponseDTO.getRole(), is(ACCOUNT_ROLE));
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
	public void testCreateUserWithAccountValidThenReturnANewUserWithAnAccount(){
		var account = createAccountVO();
		var user = new UserVO();
		user.setEmail(EMAIL_CREATE);
		user.setPassword(PASSWORD);
		user.setRepeatPassword(PASSWORD);
		user.setAccount(account);
		var userDTO = service.create(user);
	    assertThat(userDTO.getEmail(), is(EMAIL_CREATE));
	    assertThat(userDTO.getPassword(), is(PASSWORD));
	    assertThat(userDTO.getAccount().getId(), is(ACCOUNT_ID));
	    assertThat(userDTO.getAccount().getDni(), is(DNI));
	    assertThat(userDTO.getAccount().getFirstname(), is(FIRST_NAME));
	    assertThat(userDTO.getAccount().getLastname(), is(LAST_NAME));
	    assertThat(userDTO.getAccount().getRole(), is(ACCOUNT_ROLE));
	    assertThat(userDTO.getAccount().getRoleDescripton(), is(ACCOUNT_ROLE_DESCRIPTION));
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
	
	@Test
	public void testFindUserByIdThenReturnAnUser(){
		var userResult = service.findById(USER_ID);
	    assertThat(userResult, is(userDto));
	    verify(repository).findById(eq(USER_ID));
	}
	
	@Test
	public void testFindUserByIdAndNotFoundThenReturnException(){
		ex.expect(UserNotFoundException.class);
		ex.expectMessage(String.format("User not found with id:%s ", ID_USER_NOT_FOUND) );
		service.findById(ID_USER_NOT_FOUND);
		verify(repository, never()).findById(eq(ID_USER_NOT_FOUND));
	}
	
	@Test
	public void testDeleteUserById() throws Exception{
		service.deleteById(ID_USER_DELETE, USER_ADMIN_ID);
	    verify(repository).delete(any(User.class));
	    verify(accountRepository).delete(any(Account.class));
	}
	
	@Test
	public void testDeleteUseByIdWithoutUserNotAdminReThenReturnException() throws Exception{
		ex.expect(MiCulturaException.class);
		ex.expectMessage(String.format("Error Unauthorized permission user: %s ", ID_USER_DELETE));
		service.deleteById(ID_USER_DELETE, ID_USER_DELETE);
	    verify(repository).delete(any(User.class));
	}
	
	@Test
	public void testUpdateUser(){
		assertThat(userDto.getEmail(), is(EMAIL_CREATE));
		userDto.setEmail(EMAIL_UPDATE);
	    assertThat(service.update(userDto, ID_USER_UPDATE), is(userDto));
	    verify(repository).save(eq(user));
	    verify(repository, times(1)).save(user);
	}
	
	@Test
	public void testUpdateUserAndNotFoundThenReturnException(){
		ex.expect(UserNotFoundException.class);
		ex.expectMessage(String.format("User not found with id:%s ", ID_USER_NOT_FOUND));
		service.update(userDto, ID_USER_NOT_FOUND);
	    verify(repository, never()).findById(eq(ID_USER_NOT_FOUND));
	    verify(repository, never()).save(eq(user));
	}
	
	@Test
	public void testFindAllUsersWithElements(){
	    assertThat(service.findAll(), is(List.of(userDto)));
	    verify(repository).findAll();
	}
	
	private AccountVO createAccountVO() {
		var account = new AccountVO();
		account.setDni(DNI);
		account.setFirstname(FIRST_NAME);
		account.setLastname(LAST_NAME);
		account.setRole(ACCOUNT_ROLE);
		return account;
	}
}
