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

import unq.edu.li.pdes.micultura.dto.EventDTO;
import unq.edu.li.pdes.micultura.exception.EventNotFoundException;
import unq.edu.li.pdes.micultura.exception.MiCulturaException;
import unq.edu.li.pdes.micultura.mapper.Mapper;
import unq.edu.li.pdes.micultura.model.Account;
import unq.edu.li.pdes.micultura.model.AccountInterestEvent;
import unq.edu.li.pdes.micultura.model.AccountReviewEvent;
import unq.edu.li.pdes.micultura.model.Event;
import unq.edu.li.pdes.micultura.model.EventType;
import unq.edu.li.pdes.micultura.model.Review;
import unq.edu.li.pdes.micultura.model.User;
import unq.edu.li.pdes.micultura.repository.AccountInterestEventRepository;
import unq.edu.li.pdes.micultura.repository.AccountRepository;
import unq.edu.li.pdes.micultura.repository.AccountReviewEventRepository;
import unq.edu.li.pdes.micultura.repository.EventRepository;
import unq.edu.li.pdes.micultura.repository.ReviewRepository;
import unq.edu.li.pdes.micultura.repository.UserRepository;
import unq.edu.li.pdes.micultura.service.impl.EventServiceImpl;
import unq.edu.li.pdes.micultura.vo.EventVO;
import unq.edu.li.pdes.micultura.vo.ReviewVO;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {

	private static final Long ID = 1L;
	private static final Long USER_ID = 5L;
	private static final Long DELETE_ACCOUNT_ID = 4L;
	private static final Long FAVORITE_ACCOUNT_ID = 6L;
	private static final Long ID_ACCOUNT_NOT_FOUND_DELETE = -4L;
	private static final Long ID_ACCOUNT_NOT_FOUND_FAVORITE = -6L;
	private static final Long USER_UPDATE_ID = 15L;
	private static final Long ID_EVENT_DELETE = 2L;
	private static final Long ID_EVENT_UPDATE = 3L;
	private static final Long EVENT_FAVORITE_ID = 30L;
	private static final Long ID_EVENT_NOT_FOUND = 10L;
	private static final Long ID_USER_NOT_FOUND = 0L;
	private static final Long ACCOUNT_UPDATE_ID = 20L;
	private static final Integer TOTAL_SCORE = 5;
	private static final String URL = "http://test.com";
	private static final String LINK = "https://www.cultura.gob.ar/institucional/organismos/museos/comision-nacional-de-la-manzana-de-las-luces/";
	private static final String TITLE = "Perú 294, Ciudad de Buenos Aires";
	private static final String SUBTITLE = "Perú 294, Ciudad de Buenos Aires";
	private static final String BODY = "Perú 294, Ciudad de Buenos Aires";
	private static final String DEPENDS_ON = "Ministerio de Cultura de la Nación";
	private static final String EMAIL = "EMAIL@GOB.COM.AR";
	private static final String IMAGE = "+54 (011) 4342-9930 / 6973";
	private static final String STATE = "El Complejo Hist&oacute;rico Cultural Manzana de las Luces depende del Ministerio de Cultura";
	private static final String FROM_DATE = "CABA";
	private static final String TO_DATE = "Ministerio de Cultura de la Nación";
	private static final String EMAIL_UPDATE = "EMAIL_UPDATE@GOB.COM.AR";
	private static final String ANNOUNCEMENT_NAME = EventType.ANNOUNCEMENT.name();
	
	@Mock
	private Event event;
	
	@Mock
	private Event eventAnew;
	
	@Mock
	private Event eventDelete;
	
	@Mock
	private Event eventFavorite;
	
	@Mock
	private Account account;
	
	@Mock
	private Account accountDelete;
	
	@Mock
	private Account accountFavorite;
	
	@Mock
	private Account accountUpdate;
	
	@Mock
	private AccountInterestEvent accountInterestEvent;
	
	@Mock
	private AccountReviewEvent accountReviewEvent;
	
	@Mock
	private User user;
	
	@Mock
	private User userUpdate;
	
	@Mock
	private EventDTO eventDto;
	
	@Mock
	private EventVO eventVO;
	
	@Mock
	private Review review;
	
	@Mock
	private ReviewVO reviewVO;
	
	@Mock
	private EventRepository repository;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private AccountRepository accountRepository;
	
	@Mock
	private AccountInterestEventRepository accountInterestEventRepository;
	
	@Mock
	private AccountReviewEventRepository accountReviewEventRepository;
	
	@Mock
	private ReviewRepository reviewRepository;
	
	@Mock
	private Mapper mapper;	
	
	@Rule
	public ExpectedException ex = ExpectedException.none();
	
	@InjectMocks
	private EventServiceImpl service;
	
	@Before
	public void setUp(){
		service = new EventServiceImpl(repository, userRepository, accountRepository, accountInterestEventRepository, accountReviewEventRepository, reviewRepository, mapper);
		when(repository.findByEventId(ID)).thenReturn(Optional.of(event));
		when(repository.findByEventId(EVENT_FAVORITE_ID)).thenReturn(Optional.of(eventFavorite));
		when(repository.findByEventId(ID_EVENT_DELETE)).thenReturn(Optional.of(eventDelete));
		when(repository.findByEventId(ID_EVENT_NOT_FOUND)).thenReturn(Optional.empty());
		when(mapper.map(any(), eq(EventDTO.class))).thenReturn(eventDto);
		when(userRepository.findById(ID_USER_NOT_FOUND)).thenReturn(Optional.empty());
		when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
		when(userRepository.findById(USER_UPDATE_ID)).thenReturn(Optional.of(userUpdate));
		when(accountRepository.findById(DELETE_ACCOUNT_ID)).thenReturn(Optional.of(accountDelete));
		when(accountRepository.findById(FAVORITE_ACCOUNT_ID)).thenReturn(Optional.of(accountFavorite));
		when(accountDelete.getId()).thenReturn(DELETE_ACCOUNT_ID);
		when(eventDelete.getId()).thenReturn(ID_EVENT_DELETE);
		when(event.getId()).thenReturn(ID);
		when(eventFavorite.getId()).thenReturn(EVENT_FAVORITE_ID);
		when(accountFavorite.getId()).thenReturn(FAVORITE_ACCOUNT_ID);
		when(user.getAccount()).thenReturn(account);
		when(account.getId()).thenReturn(ID);
		when(userUpdate.getAccount()).thenReturn(accountUpdate);
		when(accountUpdate.getId()).thenReturn(ACCOUNT_UPDATE_ID);
		when(accountInterestEventRepository.findByEventIdAndAccountId(ID_EVENT_DELETE, DELETE_ACCOUNT_ID)).thenReturn(accountInterestEvent);
		when(accountInterestEventRepository.findOneEventIdAndAccountId(EVENT_FAVORITE_ID, FAVORITE_ACCOUNT_ID)).thenReturn(Optional.of(accountInterestEvent));
		when(event.getEmail()).thenReturn(EMAIL);
		when(repository.findAll()).thenReturn(List.of(event));
		when(accountInterestEvent.getEvent()).thenReturn(eventFavorite);
		when(mapper.mapList(anyList(), eq(EventDTO.class))).thenReturn(List.of(eventDto));
	}
	
	@Test
	public void testFindEventByIdThenReturnAPlace(){
		var placeResult = service.findById(ID);
	    assertThat(placeResult, is(eventDto));
	    verify(repository).findByEventId(eq(ID));
	}
	
	@Test
	public void testFindEventByIdAndNotFoundThenReturnException(){
		ex.expect(EventNotFoundException.class);
		ex.expectMessage(String.format("Event not found with id:%s", ID_EVENT_NOT_FOUND) );
		service.findById(ID_EVENT_NOT_FOUND);
		verify(repository, never()).findById(eq(ID_EVENT_NOT_FOUND));
	}
	
	@Test
	public void testRemoveFavoriteEventByIdAndAccountId() throws Exception{
		service.removeFavorite(DELETE_ACCOUNT_ID, ID_EVENT_DELETE);
	    verify(accountInterestEventRepository).delete(any(AccountInterestEvent.class));
	}
	
	@Test
	public void testRemoveFavoriteEventByIdWithoutAccountExistsThenReturnException() throws Exception{
		ex.expect(MiCulturaException.class);
		ex.expectMessage(String.format("No found account:%s", ID_ACCOUNT_NOT_FOUND_DELETE));
		service.removeFavorite(ID_ACCOUNT_NOT_FOUND_DELETE, ID_EVENT_DELETE);
	}
	
	
	@Test
	public void testFavoriteEventWithoutAccountExistsThenReturnException(){
		ex.expect(MiCulturaException.class);
		ex.expectMessage(String.format("No found account:%s", ID_ACCOUNT_NOT_FOUND_FAVORITE));
		var newEventVO = createEventVO();
		service.favorite(newEventVO, ID_ACCOUNT_NOT_FOUND_FAVORITE, ID);
	    verify(accountInterestEventRepository).delete(eq(accountInterestEvent));
	}
	
	@Test
	public void testFavoriteEventAndPersitNewAccountInterestEvent(){
		var newEventVO = createEventVO();
	    assertThat(service.favorite(newEventVO, FAVORITE_ACCOUNT_ID, EVENT_FAVORITE_ID), is(eventDto));
	    verify(accountInterestEventRepository).save(eq(accountInterestEvent));
	}
	
	@Test
	public void testFavoriteEvent(){
		var newEventVO = createEventVO();
	    assertThat(service.favorite(newEventVO, FAVORITE_ACCOUNT_ID, ID), is(eventDto));
	    verify(accountInterestEventRepository).save(any());
	}
	
	@Test
	public void testSaveEventWithoutUserExistsThenReturnException(){
		ex.expect(MiCulturaException.class);
		ex.expectMessage(String.format("No found user:%s", ID_USER_NOT_FOUND));
		var eventNewVO = createEventVO();
		service.save(eventNewVO, ID_USER_NOT_FOUND, ID);
	}
	
	@Test
	public void testSaveaNewEventThatIsNotInTheDataBase(){
		var newEventVO = createEventVO();
		when(mapper.map(eq(newEventVO), eq(Event.class))).thenReturn(eventAnew);
		when(repository.save(eventAnew)).thenReturn(eventAnew);
		when(eventAnew.getId()).thenReturn(ID_EVENT_NOT_FOUND);
		when(accountInterestEventRepository.findOneEventIdAndAccountId(ID_EVENT_NOT_FOUND, ID)).thenReturn(Optional.empty());
		when(mapper.map(eq(eventAnew), eq(EventDTO.class))).thenReturn(eventDto);
	    assertThat(service.save(newEventVO, USER_ID, ID_EVENT_NOT_FOUND), is(eventDto));
	    verify(accountInterestEventRepository).save(any());
	}
	
	@Test
	public void testSaveaNewEventThatIsInTheDataBase(){
		var newPlaceVO = createEventVO();
		when(event.getId()).thenReturn(ID);
		when(accountInterestEventRepository.findOneEventIdAndAccountId(ID, ID)).thenReturn(Optional.empty());
		when(mapper.map(eq(event), eq(EventDTO.class))).thenReturn(eventDto);
	    assertThat(service.save(newPlaceVO, USER_ID, ID), is(eventDto));
	    verify(accountInterestEventRepository).save(any());
	}
	
	@Test
	public void testUpdateEvent(){
		ex.expect(MiCulturaException.class);
		ex.expectMessage(String.format("No found user:%s", ID_USER_NOT_FOUND));
	    assertThat(service.update(reviewVO, ID_USER_NOT_FOUND, ID_EVENT_UPDATE), is(eventDto));
	    verify(accountReviewEventRepository).save(eq(accountReviewEvent));
	    verify(accountReviewEventRepository, never()).save(accountReviewEvent);
	}
	
	@Test
	public void testUpdateEventWithoutReviewAndCreateRelationshipAndSaveNewObject(){
		assertThat(event.getEmail(), is(EMAIL));
		event.setEmail(EMAIL_UPDATE);
		when(accountReviewEventRepository.findOneByEventIdAndAccountId(ID, ID)).thenReturn(Optional.empty());
		when(mapper.map(reviewVO, Review.class)).thenReturn(review);
		when(reviewRepository.save(review)).thenReturn(review);
	    assertThat(service.update(reviewVO, USER_ID, ID), is(eventDto));
	    verify(reviewRepository).save(eq(review));
	    verify(accountReviewEventRepository).save(any());
	    verify(accountReviewEventRepository, times(1)).save(any());
	}
	
	@Test
	public void testUpdateEventWithReviewInTheDatabase(){
		assertThat(event.getEmail(), is(EMAIL));
		event.setEmail(EMAIL_UPDATE);
		when(accountReviewEventRepository.findOneByEventIdAndAccountId(ID, ACCOUNT_UPDATE_ID)).thenReturn(Optional.of(accountReviewEvent));
		when(accountReviewEvent.getReview()).thenReturn(review);
	    assertThat(service.update(reviewVO, USER_UPDATE_ID, ID), is(eventDto));
	    verify(accountReviewEventRepository).save(any());
	    verify(accountReviewEventRepository, times(1)).save(any());
	}

	@Test
	public void testFindAllEventsByUserIdWithoutUserExistsInTheDatabase(){
		ex.expect(MiCulturaException.class);
		ex.expectMessage(String.format("No found user:%s", ID_USER_NOT_FOUND));
		service.findAllByUserId(ID_USER_NOT_FOUND);
	    verify(accountInterestEventRepository, never()).findAllEventsByAccountId(any());
	}
	
	@Test
	public void testFindAllEventsByUserIdWithElements(){
		when(accountInterestEventRepository.findAllEventsByAccountId(ID)).thenReturn(List.of(accountInterestEvent));
	    assertThat(service.findAllByUserId(USER_ID), is(List.of(eventDto)));
	    verify(accountInterestEventRepository).findAllEventsByAccountId(ID);
	}
	
	@Test
	public void testFindAllEventsWithElements(){
	    assertThat(service.findAll(), is(List.of(eventDto)));
	    verify(repository).findAll();
	}
	
	@Test
	public void testGetTotalReviewScoreWithValueTotalScore(){
		BigDecimal scoreTotal = new BigDecimal(TOTAL_SCORE);
		when(accountReviewEventRepository.getTotalReviewScore(ID)).thenReturn(scoreTotal);
	    assertThat(service.getTotalReviewScore(ID), is(scoreTotal));
	    verify(accountReviewEventRepository).getTotalReviewScore(ID);
	}
	
	@Test
	public void testGetTotalReviewScoreWithValueTotalScoreIsNull(){
		BigDecimal scoreTotal = null;
		when(accountReviewEventRepository.getTotalReviewScore(ID)).thenReturn(scoreTotal);
	    assertThat(service.getTotalReviewScore(ID), is(BigDecimal.ZERO));
	    verify(accountReviewEventRepository).getTotalReviewScore(ID);
	}
	
	private EventVO createEventVO() {
		var eventVO = new EventVO();
		eventVO.setBody(BODY);
		eventVO.setDependsOn(DEPENDS_ON);
		eventVO.setLink(EMAIL);
		eventVO.setLink(LINK);
		eventVO.setEventId(ID);
		eventVO.setEventType(ANNOUNCEMENT_NAME);
		eventVO.setFromDate(FROM_DATE);
		eventVO.setImage(IMAGE);
		eventVO.setUrl(URL);
		eventVO.setState(STATE);
		eventVO.setSubTitle(SUBTITLE);
		eventVO.setTitle(TITLE);
		eventVO.setToDate(TO_DATE);
		return eventVO;
	}
}
