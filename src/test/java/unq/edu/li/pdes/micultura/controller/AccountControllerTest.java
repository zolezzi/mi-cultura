package unq.edu.li.pdes.micultura.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
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

import unq.edu.li.pdes.micultura.dto.AccountDTO;
import unq.edu.li.pdes.micultura.service.impl.AccountServiceImpl;
import unq.edu.li.pdes.micultura.vo.AccountVO;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

	private static final Long ID = 1L;
	private static final Long ID_USER = 1L;

	@Mock
	private AccountServiceImpl service;
	
	@Mock
	private AccountDTO accountDto;
	
	@InjectMocks
	private AccountController controller;
	
	@Before
	public void setUp(){
		when(service.findById(ID)).thenReturn(accountDto);
		when(service.save(any())).thenReturn(accountDto);
		when(service.update(accountDto, ID_USER, ID)).thenReturn(accountDto);
		when(service.findAll()).thenReturn(List.of(accountDto));
	}
	
	@Test
	public void testfindByIdThenReturnAnAccountDTO(){
		assertThat(controller.findById(ID), is(accountDto));
		verify(service).findById(eq(ID));
	}
	
	@Test
	public void testsaveThenReturnAccountDTO(){
		var accountVO = new AccountVO();
		var response = controller.save(accountVO);
		assertNotNull(response.toString());
		assertNotNull(response.hashCode());
		verify(service).save(eq(accountVO));
	}
	
	@Test
	public void testupdateThenReturnAccountDTO(){
		var response = controller.update(accountDto, ID_USER, ID);
		assertNotNull(response.toString());
		assertNotNull(response.hashCode());
		verify(service).update(eq(accountDto), eq(ID_USER), eq(ID));
	}
	
	@Test
	public void testDeleteAccountThenReturnBasicResponse() throws Exception{
		var response = controller.deleteById(ID_USER, ID);
		assertThat(response.getMessage(),is(notNullValue()));    
		assertThat(response.getError(),is(Boolean.FALSE));
		assertNotNull(response.toString());
		assertNotNull(response.hashCode());
		verify(service).deleteById(eq(ID_USER), eq(ID));
	}
	
	@Test
	public void testFindAllAccounts(){
		assertThat(controller.findAll(), is(List.of(accountDto)));
		verify(service).findAll();
	}
}
