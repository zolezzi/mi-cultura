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
import unq.edu.li.pdes.micultura.dto.PlaceDTO;
import unq.edu.li.pdes.micultura.exception.AccountNotFoundException;
import unq.edu.li.pdes.micultura.exception.MiCulturaException;
import unq.edu.li.pdes.micultura.exception.PlaceNotFoundException;
import unq.edu.li.pdes.micultura.mapper.Mapper;
import unq.edu.li.pdes.micultura.model.Place;
import unq.edu.li.pdes.micultura.model.PlaceType;
import unq.edu.li.pdes.micultura.model.User;
import unq.edu.li.pdes.micultura.repository.AccountInterestPlaceRepository;
import unq.edu.li.pdes.micultura.repository.AccountRepository;
import unq.edu.li.pdes.micultura.repository.AccountReviewPlaceRepository;
import unq.edu.li.pdes.micultura.repository.PlaceRepository;
import unq.edu.li.pdes.micultura.repository.ReviewRepository;
import unq.edu.li.pdes.micultura.repository.UserRepository;
import unq.edu.li.pdes.micultura.service.impl.PlaceServiceImpl;
import unq.edu.li.pdes.micultura.vo.PlaceVO;

@RunWith(MockitoJUnitRunner.class)
public class PlaceServiceTest {

	private static final Long ID = 1L;
	private static final Long USER_ADMIN_ID = 5L;
	private static final Long ID_USER = 15L;
	private static final Long ID_PLACE_DELETE = 2L;
	private static final Long ID_USER_DELETE = 2L;
	private static final Long ID_PLACE_UPDATE = 3L;
	private static final Long ID_PLACE_NOT_FOUND = 10L;
	private static final Long ID_USER_NOT_FOUND = 0L;
	private static final Long ID_USER_NOT_FOUND_DELETE = 10L;
	private static final String NAME = "https://www.cultura.gob.ar/api/v2.0/organismos/27/?format=api";
	private static final String URL = "http://test.com";
	private static final String LINK = "https://www.cultura.gob.ar/institucional/organismos/museos/comision-nacional-de-la-manzana-de-las-luces/";
	private static final String ADDRESS = "Perú 294, Ciudad de Buenos Aires";
	private static final String PHONE_NUMBER = "+54 (011) 4342-9930 / 6973";
	private static final String DESCRIPTION = "El Complejo Hist&oacute;rico Cultural Manzana de las Luces depende del Ministerio de Cultura";
	private static final String EMAIL = "EMAIL@GOB.COM.AR";
	private static final String PROVINCE = "CABA";
	private static final String DEPENDS_ON = "Ministerio de Cultura de la Nación";
	private static final String INSTITUTE_NAME = PlaceType.INSTITUTE.name();
	private static final String EMAIL_UPDATE = "EMAIL_UPDATE@GOB.COM.AR";
	
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
	private AccountRepository accountRepository;
	
	@Mock
	private AccountInterestPlaceRepository accountInterestPlaceRepository;
	
	@Mock
	private AccountReviewPlaceRepository accountReviewPlaceRepository;
	
	@Mock
	private ReviewRepository reviewRepository;
	
	@Mock
	private Mapper mapper;	
	
	@Rule
	public ExpectedException ex = ExpectedException.none();
	
	@InjectMocks
	private PlaceServiceImpl service;
	
	@Before
	public void setUp(){
		service = new PlaceServiceImpl(repository, userRepository, accountRepository, accountInterestPlaceRepository, accountReviewPlaceRepository, reviewRepository, mapper);
		when(repository.findByPlaceId(ID)).thenReturn(Optional.of(place));
		when(mapper.map(any(), eq(PlaceDTO.class))).thenReturn(placeDto);
		//when(userRepository.findById(ID_USER)).thenReturn(Optional.of(user));
		//when(userRepository.findById(ID_USER_DELETE)).thenReturn(Optional.of(user));
		//when(user.isAdmin()).thenReturn(Boolean.FALSE);
		//when(repository.findById(ID_PLACE_DELETE)).thenReturn(Optional.of(place));
		//when(userRepository.findById(USER_ADMIN_ID)).thenReturn(Optional.of(userAdmin));
		//when(userAdmin.isAdmin()).thenReturn(Boolean.TRUE);
		//when(mapper.map(any(), eq(Place.class))).thenReturn(place);
		//when(place.getEmail()).thenReturn(EMAIL);
		//when(repository.findById(ID_PLACE_UPDATE)).thenReturn(Optional.of(place));
		//when(repository.findById(ID_PLACE_NOT_FOUND)).thenReturn(Optional.empty());
		when(repository.findAll()).thenReturn(List.of(place));
		when(mapper.mapList(anyList(), eq(PlaceDTO.class))).thenReturn(List.of(placeDto));
	}
	
	@Test
	public void testFindPlaceByIdThenReturnAPlace(){
		var placeResult = service.findById(ID);
	    assertThat(placeResult, is(placeDto));
	    verify(repository).findByPlaceId(eq(ID));
	}
	
	@Test
	public void testFindPlaceByIdAndNotFoundThenReturnException(){
		ex.expect(PlaceNotFoundException.class);
		ex.expectMessage(String.format("Place not found with id:%s", ID_PLACE_NOT_FOUND) );
		service.findById(ID_PLACE_NOT_FOUND);
		verify(repository, never()).findById(eq(ID_PLACE_NOT_FOUND));
	}
	
//	@Test
//	public void testDeletePlaceById() throws Exception{
//		service.deleteById(USER_ADMIN_ID, ID_PLACE_DELETE);
//	    verify(repository).delete(any(Place.class));
//	}
//	
//	@Test
//	public void testDeletePlaceByIdWithoutUserExistsThenReturnException() throws Exception{
//		ex.expect(MiCulturaException.class);
//		ex.expectMessage(String.format("No found user:%s", ID_USER_NOT_FOUND_DELETE));
//		service.deleteById(ID_USER_NOT_FOUND_DELETE, ID_PLACE_DELETE);
//	}
//	
//	@Test
//	public void testDeletePlaceByIdWithoutRelatedUserNotAdminReThenReturnException() throws Exception{
//		ex.expect(MiCulturaException.class);
//		ex.expectMessage(String.format("Error Unauthorized permission user: %s ", ID_USER_DELETE));
//		service.deleteById(ID_USER_DELETE, ID_PLACE_DELETE);
//	    verify(repository).delete(any(Place.class));
//	}
//	
//	@Test
//	public void testDeletePlaceByIdWithoutRelatedUserIsAdminReThenReturnException() throws Exception{
//		ex.expect(MiCulturaException.class);
//		ex.expectMessage(String.format("No found user:%s", ID_USER_NOT_FOUND));
//		service.deleteById(ID_USER_NOT_FOUND, ID_PLACE_DELETE);
//	    verify(repository).delete(any(Place.class));
//	}
//	
//	@Test
//	public void testSavePlace(){
//		var accountVO = createPlaceVO();
//	    assertThat(service.save(accountVO), is(placeDto));
//	    verify(repository).save(eq(place));
//	}
//	
//	@Test
//	public void testUpdatePlace(){
//		assertThat(place.getEmail(), is(EMAIL));
//		place.setEmail(EMAIL_UPDATE);
//	    assertThat(service.update(placeDto, USER_ADMIN_ID, ID_PLACE_UPDATE), is(placeDto));
//	    verify(repository).save(eq(place));
//	    verify(repository, times(1)).save(place);
//	}
//	
//	@Test
//	public void testUpdatePlaceByIdAndNotFoundThenReturnException(){
//		ex.expect(PlaceNotFoundException.class);
//		ex.expectMessage(String.format("Place not found with id:%s ", ID_PLACE_NOT_FOUND));
//		service.update(placeDto, USER_ADMIN_ID, ID_PLACE_NOT_FOUND);
//	    verify(repository, never()).findById(eq(ID_PLACE_NOT_FOUND));
//	    verify(repository, never()).save(eq(place));
//	}
//
//	@Test
//	public void testUpdatePlaceByIdAndNotFoundUserThenReturnException(){
//		ex.expect(MiCulturaException.class);
//		ex.expectMessage(String.format("No found user:%s", ID_USER_NOT_FOUND));
//		service.update(placeDto, ID_USER_NOT_FOUND, ID_PLACE_UPDATE);
//		verify(repository, never()).findById(eq(ID_USER_NOT_FOUND));
//		verify(repository, never()).save(eq(place));
//	}
//	
//	@Test
//	public void testUpdatPlaceWithNoFindUserByIdThenReturnException(){
//		ex.expect(MiCulturaException.class);
//		ex.expectMessage(String.format("Error Unauthorized permission user: %s ", ID_USER));
//		service.update(placeDto, ID_USER, ID_PLACE_UPDATE);
//		verify(repository, never()).findById(eq(ID_PLACE_UPDATE));
//		verify(userRepository, never()).findById(eq(ID_USER));
//		verify(repository, never()).save(eq(place));
//	}
//	
	@Test
	public void testFindAllPlacesWithElements(){
	    assertThat(service.findAll(), is(List.of(placeDto)));
	    verify(repository).findAll();
	}
	
	private PlaceVO createPlaceVO() {
		var placeVO = new PlaceVO();
		placeVO.setAddress(ADDRESS);
		placeVO.setDependsOn(DEPENDS_ON);
		placeVO.setDescription(DESCRIPTION);
		placeVO.setLink(EMAIL);
		placeVO.setLink(LINK);
		placeVO.setPhoneNumber(PHONE_NUMBER);
		placeVO.setPlaceTypeDescription(INSTITUTE_NAME);
		placeVO.setProvince(PROVINCE);
		placeVO.setUrl(URL);
		placeVO.setName(NAME);
		return placeVO;
	}
}
