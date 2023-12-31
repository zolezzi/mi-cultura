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

import unq.edu.li.pdes.micultura.dto.PlaceDTO;
import unq.edu.li.pdes.micultura.service.impl.PlaceServiceImpl;
import unq.edu.li.pdes.micultura.vo.PlaceVO;
import unq.edu.li.pdes.micultura.vo.ReviewVO;

@RunWith(MockitoJUnitRunner.class)
public class PlaceControllerTest {

	private static final Long ID = 1L;
	private static final Long ID_PLACE = 1L;
	private static final Long ID_USER = 1L;

	@Mock
	private PlaceServiceImpl service;
	
	@Mock
	private PlaceDTO placeDto;
	
	@Mock
	private ReviewVO reviewVO;
	
	@Mock
	private PlaceVO placeVO;
	
	@InjectMocks
	private PlaceController controller;
	
	@Before
	public void setUp(){
		when(service.findById(ID)).thenReturn(placeDto);
		when(service.save(any(PlaceVO.class), eq(ID_USER), eq(ID_PLACE))).thenReturn(placeDto);
		when(service.update(eq(reviewVO), eq(ID_USER), eq(ID))).thenReturn(placeDto);
		when(service.findAll()).thenReturn(List.of(placeDto));
	}
	
	@Test
	public void testfindByIdThenReturnAPlaceDTO(){
		assertThat(controller.findById(ID), is(placeDto));
		verify(service).findById(eq(ID));
	}
	
	@Test
	public void testsaveThenReturnPlaceDTO(){
		var placeVO = new PlaceVO();
		var response = controller.save(placeVO, ID_USER, ID_PLACE);
		assertNotNull(response.toString());
		assertNotNull(response.hashCode());
		verify(service).save(eq(placeVO), eq(ID_USER), eq(ID_PLACE));
	}
	
	@Test
	public void testupdateThenReturnPlaceDTO(){
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
		assertThat(controller.findAll(), is(List.of(placeDto)));
		verify(service).findAll();
	}
}
