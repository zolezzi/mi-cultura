package unq.edu.li.pdes.micultura.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
}
