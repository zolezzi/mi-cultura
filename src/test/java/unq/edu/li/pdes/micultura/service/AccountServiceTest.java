package unq.edu.li.pdes.micultura.service;

import static org.hamcrest.CoreMatchers.is;
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

import unq.edu.li.pdes.micultura.dto.AccountDTO;
import unq.edu.li.pdes.micultura.exception.AccountNotFoundException;
import unq.edu.li.pdes.micultura.exception.MiCulturaException;
import unq.edu.li.pdes.micultura.mapper.Mapper;
import unq.edu.li.pdes.micultura.model.Account;
import unq.edu.li.pdes.micultura.model.User;
import unq.edu.li.pdes.micultura.repository.AccountRepository;
import unq.edu.li.pdes.micultura.repository.UserRepository;
import unq.edu.li.pdes.micultura.service.impl.AccountServiceImpl;
import unq.edu.li.pdes.micultura.vo.AccountVO;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

	private static final Long ID = 1L;
	private static final Long USER_ADMIN_ID = 5L;
	private static final Long ID_USER_DELETE = 2L;
	private static final Long ID_USER_UPDATE = 3L;
	private static final Long ID_ACCOUNT_DELETE = 2L;
	private static final Long ID_ACCOUNT_NOT_FOUND = 10L;
	private static final Long ID_USER_NOT_FOUND_DELETE = 10L;
	private static final Long ID_USER_NOT_FOUND = 0L;
	private static final Long ID_ACCOUNT_NEW = 3L;
	private static final Long ID_ACCOUNT_UPDATE = 4L;
	private static final Long ID_USER_UPDATE_OTHER_ACCOUNT = 6L;
	private static final String DNI = "35000111";
	private static final String FIRST_NAME = "TEST";
	private static final String LAST_NAME = "TEST";
	private static final String ACCOUNT_ROLE = "ADMIN";
	private static final String ID_DNI_NOT_FOUND = "0000001";
	private static final String FIRST_NAME_UPDATE = "TEST2";
	
	@Mock
	private Account account;
	
	@Mock
	private Account otherAccount;
	
	@Mock
	private User userAdmin;
	
	@Mock
	private User user;
	
	@Mock
	private User otherUser;
	
	@Mock
	private AccountDTO accountDto;
	
	@Mock
	private AccountRepository repository;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private Mapper mapper;
	
	@Rule
	public ExpectedException ex = ExpectedException.none();
	
	@InjectMocks
	private AccountServiceImpl service;
	
	@Before
	public void setUp(){
		service = new AccountServiceImpl(repository, userRepository, mapper);
		when(repository.findById(ID)).thenReturn(Optional.of(account));
		when(repository.findById(ID_ACCOUNT_UPDATE)).thenReturn(Optional.of(account));
		when(mapper.map(any(), eq(Account.class))).thenReturn(account);
		when(mapper.map(any(), eq(AccountDTO.class))).thenReturn(accountDto);
		when(userRepository.findById(ID_USER_DELETE)).thenReturn(Optional.of(user));
		when(userRepository.findById(ID_USER_UPDATE)).thenReturn(Optional.of(user));
		when(userRepository.findById(ID_USER_UPDATE_OTHER_ACCOUNT)).thenReturn(Optional.of(otherUser));
		when(user.isAdmin()).thenReturn(Boolean.FALSE);
		when(user.getAccount()).thenReturn(account);
		when(otherUser.getAccount()).thenReturn(otherAccount);
		when(account.getFirstname()).thenReturn(FIRST_NAME);
		when(repository.findById(ID_ACCOUNT_DELETE)).thenReturn(Optional.of(account));
		when(userRepository.findById(USER_ADMIN_ID)).thenReturn(Optional.of(userAdmin));
		when(userAdmin.isAdmin()).thenReturn(Boolean.TRUE);
		when(userRepository.findOneByAccount(any())).thenReturn(Optional.empty());
		when(userRepository.findOneByAccount(eq(account))).thenReturn(Optional.of(user));
		when(repository.findOneByDni(DNI)).thenReturn(Optional.of(account));
		when(repository.findOneByDni(ID_DNI_NOT_FOUND)).thenReturn(Optional.empty());
		when(repository.findAllByAccountRoles(anyList())).thenReturn(List.of(account));
		when(mapper.mapList(anyList(), eq(AccountDTO.class))).thenReturn(List.of(accountDto));
		when(repository.save(account)).thenReturn(account);
	}
	
	@Test
	public void testFindAccountByIdThenReturnAnAccount(){
		var accountResult = service.findById(ID);
	    assertThat(accountResult, is(accountDto));
	    verify(repository).findById(eq(ID));
	}
	
	@Test
	public void testFindAccountByIdAndNotFoundThenReturnException(){
		ex.expect(AccountNotFoundException.class);
		ex.expectMessage(String.format("Account not found with id:%s", ID_ACCOUNT_NOT_FOUND) );
		service.findById(ID_ACCOUNT_NOT_FOUND);
		verify(repository, never()).findById(eq(ID_ACCOUNT_NOT_FOUND));
	}
	
	@Test
	public void testDeleteAccountById() throws Exception{
		service.deleteById(USER_ADMIN_ID, ID_ACCOUNT_DELETE);
	    verify(repository).delete(any(Account.class));
	}
	
	@Test
	public void testDeleteAccountByIdWithoutUserExistsThenReturnException() throws Exception{
		ex.expect(MiCulturaException.class);
		ex.expectMessage(String.format("No found user:%s", ID_USER_NOT_FOUND_DELETE));
		service.deleteById(ID_USER_NOT_FOUND_DELETE, ID_ACCOUNT_DELETE);
	}
	
	@Test
	public void testDeleteAccountByIdWithoutRelatedUserNotAdminReThenReturnException() throws Exception{
		ex.expect(MiCulturaException.class);
		ex.expectMessage(String.format("Error Unauthorized permission user: %s ", ID_USER_DELETE));
		service.deleteById(ID_USER_DELETE, ID_ACCOUNT_DELETE);
	    verify(repository).delete(any(Account.class));
	}
	
	@Test
	public void testDeleteAccountByIdWithoutRelatedUserIsAdminReThenReturnException() throws Exception{
		ex.expect(MiCulturaException.class);
		ex.expectMessage(String.format("No found user:%s", ID_USER_NOT_FOUND));
		service.deleteById(ID_USER_NOT_FOUND, ID_ACCOUNT_DELETE);
	    verify(repository).delete(any(Account.class));
	}
	
	@Test
	public void testSaveAccount(){
		var accountVO = new AccountVO();
		accountVO.setFirstname(FIRST_NAME);
		accountVO.setLastname(LAST_NAME);
		accountVO.setDni(ID_DNI_NOT_FOUND);
		accountVO.setRole(ACCOUNT_ROLE);
	    assertThat(service.save(accountVO), is(accountDto));
	    verify(repository).save(eq(account));
	    verify(repository).findOneByDni(eq(ID_DNI_NOT_FOUND));
	}
	
	@Test
	public void testSaveAccountAndNotFoundDniThenReturnException(){
		ex.expect(MiCulturaException.class);
		ex.expectMessage(String.format("error there is already an account registered with this DNI: %s ", DNI));
		var accountVO = new AccountVO();
		accountVO.setFirstname(FIRST_NAME);
		accountVO.setLastname(LAST_NAME);
		accountVO.setDni(DNI);
		accountVO.setRole(ACCOUNT_ROLE);
	    assertThat(service.save(accountVO), is(accountDto));
	    verify(repository).save(eq(account));
	    verify(repository).findById(eq(ID_ACCOUNT_NEW));
	}
	
	@Test
	public void testUpdateAccount(){
		assertThat(account.getFirstname(), is(FIRST_NAME));
		account.setFirstname(FIRST_NAME_UPDATE);
	    assertThat(service.update(accountDto, ID_USER_UPDATE, ID_ACCOUNT_UPDATE), is(accountDto));
	    verify(repository).save(eq(account));
	    verify(repository, times(1)).save(account);
	}
	
	@Test
	public void testUpdateAccountAndNotFoundThenReturnException(){
		ex.expect(AccountNotFoundException.class);
		ex.expectMessage(String.format("Account not found with id:%s ", ID_ACCOUNT_NOT_FOUND));
		service.update(accountDto, ID_USER_NOT_FOUND, ID_ACCOUNT_NOT_FOUND);
	    verify(repository, never()).findById(eq(ID_ACCOUNT_NOT_FOUND));
	    verify(repository, never()).save(eq(account));
	}

	@Test
	public void testUpdateAccountByIdAndNotFoundThenReturnException(){
		ex.expect(MiCulturaException.class);
		ex.expectMessage(String.format("No found user:%s", ID_USER_NOT_FOUND));
		service.update(accountDto, ID_USER_NOT_FOUND, ID_ACCOUNT_UPDATE);
		verify(repository, never()).findById(eq(ID_USER_NOT_FOUND));
		verify(repository, never()).save(eq(account));
	}
	
	@Test
	public void testUpdateAccountWithNoFindUserByIdThenReturnException(){
		ex.expect(MiCulturaException.class);
		ex.expectMessage(String.format("No found account with id :%s, for user with id:%s ",ID_ACCOUNT_UPDATE, ID_USER_UPDATE_OTHER_ACCOUNT));
		service.update(accountDto, ID_USER_UPDATE_OTHER_ACCOUNT, ID_ACCOUNT_UPDATE);
		verify(repository, never()).findById(eq(ID_ACCOUNT_UPDATE));
		verify(userRepository, never()).findById(eq(ID_USER_UPDATE_OTHER_ACCOUNT));
		verify(repository, never()).save(eq(account));
	}
	
	@Test
	public void testFindAllAcountsWithElements(){
	    assertThat(service.findAll(), is(List.of(accountDto)));
	    verify(repository).findAllByAccountRoles(anyList());
	}
	
	@Test
	public void testcreateAccount(){
		var accountVO = new AccountVO();
		accountVO.setFirstname(FIRST_NAME);
		accountVO.setLastname(LAST_NAME);
		accountVO.setDni(ID_DNI_NOT_FOUND);
		accountVO.setRole(ACCOUNT_ROLE);
	    assertThat(service.createAccountByUser(accountVO), is(account));
	    verify(repository).save(eq(account));
	    verify(repository).findOneByDni(eq(ID_DNI_NOT_FOUND));
	}
	
	@Test
	public void testCreateAccountAndNotFoundDNIThenReturnException(){
		ex.expect(MiCulturaException.class);
		ex.expectMessage(String.format("error there is already an account registered with this DNI: %s ", DNI));
		var accountVO = new AccountVO();
		accountVO.setFirstname(FIRST_NAME);
		accountVO.setLastname(LAST_NAME);
		accountVO.setDni(DNI);
		accountVO.setRole(ACCOUNT_ROLE);
		service.createAccountByUser(accountVO);
		verify(repository, never()).save(eq(account));
	}
	
}
