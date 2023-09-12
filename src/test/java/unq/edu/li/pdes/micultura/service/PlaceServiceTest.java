package unq.edu.li.pdes.micultura.service;

import static org.hamcrest.CoreMatchers.is;
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

import unq.edu.li.pdes.micultura.dto.PlaceDTO;
import unq.edu.li.pdes.micultura.exception.MiCulturaException;
import unq.edu.li.pdes.micultura.exception.PlaceNotFoundException;
import unq.edu.li.pdes.micultura.mapper.Mapper;
import unq.edu.li.pdes.micultura.model.Place;
import unq.edu.li.pdes.micultura.model.User;
import unq.edu.li.pdes.micultura.repository.PlaceRepository;
import unq.edu.li.pdes.micultura.repository.UserRepository;
import unq.edu.li.pdes.micultura.service.impl.PlaceServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class PlaceServiceTest {

	private static final Long ID = 1L;
	private static final Long USER_ADMIN_ID = 5L;
	private static final Long ID_PLACE_DELETE = 2L;
	private static final Long ID_USER_DELETE = 2L;
	private static final Long ID_PLACE_NOT_FOUND = 10L;
	private static final Long ID_USER_NOT_FOUND = 0L;
	private static final Long ID_USER_NOT_FOUND_DELETE = 10L;
	
	@Mock
	private Place place;
	
	@Mock
	private User user;
	
	@Mock
	private User userAdmin;
	
	@Mock
	private PlaceDTO placeDto;
	
	@Mock
	private PlaceRepository repository;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private Mapper mapper;	
	
	@Rule
	public ExpectedException ex = ExpectedException.none();
	
	@InjectMocks
	private PlaceServiceImpl service;
	
	@Before
	public void setUp(){
		service = new PlaceServiceImpl(repository, userRepository, mapper);
		when(repository.findById(ID)).thenReturn(Optional.of(place));
		when(mapper.map(any(), eq(PlaceDTO.class))).thenReturn(placeDto);
		when(userRepository.findById(ID_USER_DELETE)).thenReturn(Optional.of(user));
		when(user.isAdmin()).thenReturn(Boolean.FALSE);
		when(repository.findById(ID_PLACE_DELETE)).thenReturn(Optional.of(place));
		when(userRepository.findById(USER_ADMIN_ID)).thenReturn(Optional.of(userAdmin));
		when(userAdmin.isAdmin()).thenReturn(Boolean.TRUE);
	}
	
	@Test
	public void testFindPlaceByIdThenReturnAPlace(){
		var placeResult = service.findById(ID);
	    assertThat(placeResult, is(placeDto));
	    verify(repository).findById(eq(ID));
	}
	
	@Test
	public void testFindPlaceByIdAndNotFoundThenReturnException(){
		ex.expect(PlaceNotFoundException.class);
		ex.expectMessage(String.format("Place not found with id:%s", ID_PLACE_NOT_FOUND) );
		service.findById(ID_PLACE_NOT_FOUND);
		verify(repository, never()).findById(eq(ID_PLACE_NOT_FOUND));
	}
	
	@Test
	public void testDeletePlaceById() throws Exception{
		service.deleteById(USER_ADMIN_ID, ID_PLACE_DELETE);
	    verify(repository).delete(any(Place.class));
	}
	
	@Test
	public void testDeletePlaceByIdWithoutUserExistsThenReturnException() throws Exception{
		ex.expect(MiCulturaException.class);
		ex.expectMessage(String.format("No found user:%s", ID_USER_NOT_FOUND_DELETE));
		service.deleteById(ID_USER_NOT_FOUND_DELETE, ID_PLACE_DELETE);
	}
	
	@Test
	public void testDeletePlaceByIdWithoutRelatedUserNotAdminReThenReturnException() throws Exception{
		ex.expect(MiCulturaException.class);
		ex.expectMessage(String.format("Error Unauthorized permission user: %s ", ID_USER_DELETE));
		service.deleteById(ID_USER_DELETE, ID_PLACE_DELETE);
	    verify(repository).delete(any(Place.class));
	}
	
	@Test
	public void testDeletePlaceByIdWithoutRelatedUserIsAdminReThenReturnException() throws Exception{
		ex.expect(MiCulturaException.class);
		ex.expectMessage(String.format("No found user:%s", ID_USER_NOT_FOUND));
		service.deleteById(ID_USER_NOT_FOUND, ID_PLACE_DELETE);
	    verify(repository).delete(any(Place.class));
	}
}
