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

import unq.edu.li.pdes.micultura.dto.EventDTO;
import unq.edu.li.pdes.micultura.service.impl.EventServiceImpl;
import unq.edu.li.pdes.micultura.vo.EventVO;
import unq.edu.li.pdes.micultura.vo.ReviewVO;

@RunWith(MockitoJUnitRunner.class)
public class EventControllerTest {

	private static final Long ID = 1L;
	private static final Long ID_EVENT = 1L;
	private static final Long ID_USER = 1L;
	
	@Mock
	private EventServiceImpl service;
	
	@Mock
	private EventDTO eventDto;
	
	@Mock
	private ReviewVO reviewVO;
	
	@Mock
	private EventVO eventVO;
	
	@InjectMocks
	private EventController controller;
	
	@Before
	public void setUp(){
		when(service.findById(ID)).thenReturn(eventDto);
		when(service.save(any(EventVO.class), eq(ID_USER), eq(ID_EVENT))).thenReturn(eventDto);
		when(service.update(eq(reviewVO), eq(ID_USER), eq(ID))).thenReturn(eventDto);
		when(service.findAll()).thenReturn(List.of(eventDto));
	}
	
	@Test
	public void testfindByIdThenReturnAEventDTO(){
		assertThat(controller.findById(ID), is(eventDto));
		verify(service).findById(eq(ID));
	}
	
	@Test
	public void testsaveThenReturnEventDTO(){
		var eventVO = new EventVO();
		var response = controller.save(eventVO, ID_USER, ID_EVENT);
		assertNotNull(response.toString());
		assertNotNull(response.hashCode());
		verify(service).save(eq(eventVO), eq(ID_USER), eq(ID_EVENT));
	}
	
	@Test
	public void testupdateThenReturnEventDTO(){
		var response = controller.update(reviewVO, ID_USER, ID);
		assertNotNull(response.toString());
		assertNotNull(response.hashCode());
		verify(service).update(eq(reviewVO), eq(ID_USER), eq(ID));
	}
	
	@Test
	public void testDeleteAccountThenReturnBasicResponse() throws Exception{
		var response = controller.removeFavorite(ID_USER, ID);
		assertThat(response.getMessage(),is(notNullValue()));    
		assertThat(response.getError(),is(Boolean.FALSE));
		assertNotNull(response.toString());
		assertNotNull(response.hashCode());
		verify(service).removeFavorite(eq(ID_USER), eq(ID));
	}
	
	@Test
	public void testFindAllAccounts(){
		assertThat(controller.findAll(), is(List.of(eventDto)));
		verify(service).findAll();
	}
}
