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

import java.math.BigDecimal;
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

import unq.edu.li.pdes.micultura.dto.PlaceDTO;
import unq.edu.li.pdes.micultura.exception.MiCulturaException;
import unq.edu.li.pdes.micultura.exception.PlaceNotFoundException;
import unq.edu.li.pdes.micultura.mapper.Mapper;
import unq.edu.li.pdes.micultura.model.Account;
import unq.edu.li.pdes.micultura.model.AccountInterestPlace;
import unq.edu.li.pdes.micultura.model.AccountReviewPlace;
import unq.edu.li.pdes.micultura.model.Place;
import unq.edu.li.pdes.micultura.model.PlaceType;
import unq.edu.li.pdes.micultura.model.Review;
import unq.edu.li.pdes.micultura.model.User;
import unq.edu.li.pdes.micultura.repository.AccountInterestPlaceRepository;
import unq.edu.li.pdes.micultura.repository.AccountRepository;
import unq.edu.li.pdes.micultura.repository.AccountReviewPlaceRepository;
import unq.edu.li.pdes.micultura.repository.PlaceRepository;
import unq.edu.li.pdes.micultura.repository.ReviewRepository;
import unq.edu.li.pdes.micultura.repository.UserRepository;
import unq.edu.li.pdes.micultura.service.impl.PlaceServiceImpl;
import unq.edu.li.pdes.micultura.vo.PlaceVO;
import unq.edu.li.pdes.micultura.vo.ReviewVO;

@RunWith(MockitoJUnitRunner.class)
public class PlaceServiceTest {

	private static final Long ID = 1L;
	private static final Long USER_ID = 5L;
	private static final Long DELETE_ACCOUNT_ID = 4L;
	private static final Long FAVORITE_ACCOUNT_ID = 6L;
	private static final Long ID_ACCOUNT_NOT_FOUND_DELETE = -4L;
	private static final Long ID_ACCOUNT_NOT_FOUND_FAVORITE = -6L;
	private static final Long USER_UPDATE_ID = 15L;
	private static final Long ID_PLACE_DELETE = 2L;
	private static final Long ID_PLACE_UPDATE = 3L;
	private static final Long ID_PLACE_NOT_FOUND = 10L;
	private static final Long ID_USER_NOT_FOUND = 0L;
	private static final Long ACCOUNT_UPDATE_ID = 20L;
	private static final Integer TOTAL_SCORE = 5;
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
	private Place placeAnew;
	
	@Mock
	private Place placeDelete;
	
	@Mock
	private Account account;
	
	@Mock
	private Account accountDelete;
	
	@Mock
	private Account accountFavorite;
	
	@Mock
	private Account accountUpdate;
	
	@Mock
	private AccountInterestPlace accountInterestPlace;
	
	@Mock
	private AccountReviewPlace accountReviewPlace;
	
	@Mock
	private User user;
	
	@Mock
	private User userUpdate;
	
	@Mock
	private PlaceDTO placeDto;
	
	@Mock
	private PlaceVO placeVO;
	
	@Mock
	private Review review;
	
	@Mock
	private ReviewVO reviewVO;
	
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
		when(repository.findByPlaceId(ID_PLACE_DELETE)).thenReturn(Optional.of(placeDelete));
		when(repository.findByPlaceId(ID_PLACE_NOT_FOUND)).thenReturn(Optional.empty());
		when(mapper.map(any(), eq(PlaceDTO.class))).thenReturn(placeDto);
		when(userRepository.findById(ID_USER_NOT_FOUND)).thenReturn(Optional.empty());
		when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
		when(userRepository.findById(USER_UPDATE_ID)).thenReturn(Optional.of(userUpdate));
		when(accountRepository.findById(DELETE_ACCOUNT_ID)).thenReturn(Optional.of(accountDelete));
		when(accountRepository.findById(FAVORITE_ACCOUNT_ID)).thenReturn(Optional.of(accountFavorite));
		when(accountDelete.getId()).thenReturn(DELETE_ACCOUNT_ID);
		when(placeDelete.getId()).thenReturn(ID_PLACE_DELETE);
		when(place.getId()).thenReturn(ID);
		when(accountFavorite.getId()).thenReturn(FAVORITE_ACCOUNT_ID);
		when(user.getAccount()).thenReturn(account);
		when(account.getId()).thenReturn(ID);
		when(userUpdate.getAccount()).thenReturn(accountUpdate);
		when(accountUpdate.getId()).thenReturn(ACCOUNT_UPDATE_ID);
		when(accountInterestPlaceRepository.findByPlaceIdAndAccountId(ID_PLACE_DELETE, DELETE_ACCOUNT_ID)).thenReturn(accountInterestPlace);
		//when(accountInterestPlaceRepository.findByPlaceIdAndAccountId(ID, FAVORITE_ACCOUNT_ID)).thenReturn(accountInterestPlace);
		when(place.getEmail()).thenReturn(EMAIL);
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
	
	@Test
	public void testRemoveFavoritePlaceByIdAndAccountId() throws Exception{
		service.removeFavorite(DELETE_ACCOUNT_ID, ID_PLACE_DELETE);
	    verify(accountInterestPlaceRepository).delete(any(AccountInterestPlace.class));
	}
	
	@Test
	public void testRemoveFavoritePlaceByIdWithoutAccountExistsThenReturnException() throws Exception{
		ex.expect(MiCulturaException.class);
		ex.expectMessage(String.format("No found account:%s", ID_ACCOUNT_NOT_FOUND_DELETE));
		service.removeFavorite(ID_ACCOUNT_NOT_FOUND_DELETE, ID_PLACE_DELETE);
	}
	
	
	@Test
	public void testFavoritePlaceWithoutAccountExistsThenReturnException(){
		ex.expect(MiCulturaException.class);
		ex.expectMessage(String.format("No found account:%s", ID_ACCOUNT_NOT_FOUND_FAVORITE));
		service.favorite(createPlaceVO(), ID_ACCOUNT_NOT_FOUND_FAVORITE, ID);
	    verify(accountInterestPlaceRepository).delete(eq(accountInterestPlace));
	}
	
	@Test
	public void testFavoritePlace(){
	    assertThat(service.favorite(createPlaceVO(), FAVORITE_ACCOUNT_ID, ID), is(placeDto));
	    verify(accountInterestPlaceRepository).save(any());
	}
	
	@Test
	public void testSavePlaceWithoutUserExistsThenReturnException(){
		ex.expect(MiCulturaException.class);
		ex.expectMessage(String.format("No found user:%s", ID_USER_NOT_FOUND));
		var placeNewVO = createPlaceVO();
		service.save(placeNewVO, ID_USER_NOT_FOUND, ID);
	}
	
	@Test
	public void testSaveaNewPlaceThatIsNotInTheDataBase(){
		var newPlaceVO = createPlaceVO();
		when(mapper.map(eq(newPlaceVO), eq(Place.class))).thenReturn(placeAnew);
		when(repository.save(placeAnew)).thenReturn(placeAnew);
		when(placeAnew.getId()).thenReturn(ID_PLACE_NOT_FOUND);
		when(accountInterestPlaceRepository.findOnePlaceIdAndAccountId(ID_PLACE_NOT_FOUND, ID)).thenReturn(Optional.empty());
		when(mapper.map(eq(placeAnew), eq(PlaceDTO.class))).thenReturn(placeDto);
	    assertThat(service.save(newPlaceVO, USER_ID, ID_PLACE_NOT_FOUND), is(placeDto));
	    verify(accountInterestPlaceRepository).save(any());
	}
	
	@Test
	public void testSaveaNewPlaceThatIsInTheDataBase(){
		var newPlaceVO = createPlaceVO();
		when(place.getId()).thenReturn(ID);
		when(accountInterestPlaceRepository.findOnePlaceIdAndAccountId(ID, ID)).thenReturn(Optional.empty());
		when(mapper.map(eq(place), eq(PlaceDTO.class))).thenReturn(placeDto);
	    assertThat(service.save(newPlaceVO, USER_ID, ID), is(placeDto));
	    verify(accountInterestPlaceRepository).save(any());
	}
	
	@Test
	public void testUpdatePlace(){
		ex.expect(MiCulturaException.class);
		ex.expectMessage(String.format("No found user:%s", ID_USER_NOT_FOUND));
	    assertThat(service.update(reviewVO, ID_USER_NOT_FOUND, ID_PLACE_UPDATE), is(placeDto));
	    verify(accountReviewPlaceRepository).save(eq(accountReviewPlace));
	    verify(accountReviewPlaceRepository, never()).save(accountReviewPlace);
	}
	
	@Test
	public void testUpdatePlaceWithoutReviewAndCreateRelationshipAndSaveNewObject(){
		assertThat(place.getEmail(), is(EMAIL));
		place.setEmail(EMAIL_UPDATE);
		when(accountReviewPlaceRepository.findOneByPlaceIdAndAccountId(ID, ID)).thenReturn(Optional.empty());
		when(mapper.map(reviewVO, Review.class)).thenReturn(review);
		when(reviewRepository.save(review)).thenReturn(review);
	    assertThat(service.update(reviewVO, USER_ID, ID), is(placeDto));
	    verify(reviewRepository).save(eq(review));
	    verify(accountReviewPlaceRepository).save(any());
	    verify(accountReviewPlaceRepository, times(1)).save(any());
	}
	
	@Test
	public void testUpdatePlaceWithReviewInTheDatabase(){
		assertThat(place.getEmail(), is(EMAIL));
		place.setEmail(EMAIL_UPDATE);
		when(accountReviewPlaceRepository.findOneByPlaceIdAndAccountId(ID, ACCOUNT_UPDATE_ID)).thenReturn(Optional.of(accountReviewPlace));
		when(accountReviewPlace.getReview()).thenReturn(review);
	    assertThat(service.update(reviewVO, USER_UPDATE_ID, ID), is(placeDto));
	    verify(accountReviewPlaceRepository).save(any());
	    verify(accountReviewPlaceRepository, times(1)).save(any());
	}

	@Test
	public void testFindAllPlacesByUserIdWithoutUserExistsInTheDatabase(){
		ex.expect(MiCulturaException.class);
		ex.expectMessage(String.format("No found user:%s", ID_USER_NOT_FOUND));
		service.findAllByUserId(ID_USER_NOT_FOUND);
	    verify(accountInterestPlaceRepository, never()).findAllPlacesByAccountId(any());
	}
	
	@Test
	public void testFindAllPlacesByUserIdWithElements(){
		when(accountInterestPlaceRepository.findAllPlacesByAccountId(ID)).thenReturn(List.of(accountInterestPlace));
	    assertThat(service.findAllByUserId(USER_ID), is(List.of(placeDto)));
	    verify(accountInterestPlaceRepository).findAllPlacesByAccountId(ID);
	}
	
	@Test
	public void testFindAllPlacesWithElements(){
	    assertThat(service.findAll(), is(List.of(placeDto)));
	    verify(repository).findAll();
	}
	
	@Test
	public void testGetTotalReviewScoreWithValueTotalScore(){
		BigDecimal scoreTotal = new BigDecimal(TOTAL_SCORE);
		when(accountReviewPlaceRepository.getTotalReviewScore(ID)).thenReturn(scoreTotal);
	    assertThat(service.getTotalReviewScore(ID), is(scoreTotal));
	    verify(accountReviewPlaceRepository).getTotalReviewScore(ID);
	}
	
	@Test
	public void testGetTotalReviewScoreWithValueTotalScoreIsNull(){
		BigDecimal scoreTotal = null;
		when(accountReviewPlaceRepository.getTotalReviewScore(ID)).thenReturn(scoreTotal);
	    assertThat(service.getTotalReviewScore(ID), is(BigDecimal.ZERO));
	    verify(accountReviewPlaceRepository).getTotalReviewScore(ID);
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
