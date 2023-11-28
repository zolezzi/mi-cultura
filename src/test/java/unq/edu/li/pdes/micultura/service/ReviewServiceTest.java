package unq.edu.li.pdes.micultura.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
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

import unq.edu.li.pdes.micultura.dto.AccountReviewDetailsDTO;
import unq.edu.li.pdes.micultura.dto.ReviewDTO;
import unq.edu.li.pdes.micultura.exception.MiCulturaException;
import unq.edu.li.pdes.micultura.exception.ReviewNotFoundException;
import unq.edu.li.pdes.micultura.mapper.Mapper;
import unq.edu.li.pdes.micultura.model.AccountReviewEvent;
import unq.edu.li.pdes.micultura.model.AccountReviewPlace;
import unq.edu.li.pdes.micultura.model.Review;
import unq.edu.li.pdes.micultura.repository.AccountReviewEventRepository;
import unq.edu.li.pdes.micultura.repository.AccountReviewPlaceRepository;
import unq.edu.li.pdes.micultura.repository.ReviewRepository;
import unq.edu.li.pdes.micultura.service.impl.ReviewServiceImpl;
import unq.edu.li.pdes.micultura.vo.ReviewVO;

@RunWith(MockitoJUnitRunner.class)
public class ReviewServiceTest {

	private static final Long ID = 1L;
	private static final Long ACCOUNT_ID = 2L;
	private static final Long ID_REVIEW_NOT_FOUND = 10L;
	private static final Long PLACE_ID = 9L;
	private static final Long ID_PLACE_NOT_FOUND = -1L;
	private static final String COMMENTS = "El Complejo Hist&oacute;rico Cultural Manzana de las Luces depende del Ministerio de Cultura";
	private static final String COMMENTS_UPDATE = "EXCELENTE LUGAR";
	
	@Mock
	private Review review;
	
	@Mock
	private ReviewDTO reviewDto;
	
	@Mock
	private ReviewVO reviewVO;
	
	@Mock
	private AccountReviewDetailsDTO accountReviewDetailsDto;
	
	@Mock
	private AccountReviewPlace accountReviewPlace;
	
	@Mock
	private AccountReviewEvent accountReviewEvent;
	
	@Mock
	private ReviewRepository repository;
	
	@Mock
	private AccountReviewPlaceRepository accountReviewPlaceRepository;
	
	@Mock
	private AccountReviewEventRepository accountReviewEventRepository;
	
	@Mock
	private Mapper mapper;
	
	@Rule
	public ExpectedException ex = ExpectedException.none();
	
	@InjectMocks
	private ReviewServiceImpl service;
	
	@Before
	public void setUp(){
		service = new ReviewServiceImpl(repository, accountReviewPlaceRepository, accountReviewEventRepository, mapper);
		when(repository.findById(ID)).thenReturn(Optional.of(review));
		when(mapper.map(any(), eq(ReviewDTO.class))).thenReturn(reviewDto);
		when(review.getCommets()).thenReturn(COMMENTS);
		when(accountReviewPlaceRepository.findOneByPlaceIdAndAccountId(ID_PLACE_NOT_FOUND, ACCOUNT_ID)).thenReturn(Optional.empty());
		when(accountReviewPlaceRepository.findOneByPlaceIdAndAccountId(PLACE_ID, ACCOUNT_ID)).thenReturn(Optional.of(accountReviewPlace));
		when(accountReviewPlace.getReview()).thenReturn(review);
		when(accountReviewPlaceRepository.findAll()).thenReturn(List.of(accountReviewPlace));
		when(accountReviewEventRepository.findAll()).thenReturn(List.of(accountReviewEvent));
		when(mapper.mapList(anyList(), eq(AccountReviewDetailsDTO.class))).thenReturn(List.of(accountReviewDetailsDto));
	}
	
	@Test
	public void testFindReviewByIdThenReturnAReview(){
		var reviewResult = service.findById(ID);
	    assertThat(reviewResult, is(reviewDto));
	    verify(repository).findById(eq(ID));
	}
	
	@Test
	public void testFindReviewByIdAndNotFoundThenReturnException(){
		ex.expect(ReviewNotFoundException.class);
		ex.expectMessage(String.format("Review not found with id:%s", ID_REVIEW_NOT_FOUND) );
		service.findById(ID_REVIEW_NOT_FOUND);
		verify(repository, never()).findById(eq(ID_REVIEW_NOT_FOUND));
	}

	@Test
	public void testUpdateReviewAndUpdateNewComments(){
		assertThat(review.getCommets(), is(COMMENTS));
		review.setCommets(COMMENTS_UPDATE);
		service.update(reviewVO, ID);
		verify(repository).save(any());
	}
	
	@Test
	public void testgetReviewByPlaceAndAccountAndNotFoundAndThanReturnException(){
		ex.expect(MiCulturaException.class);
		ex.expectMessage(String.format("No found review para la cuenta:%s", ACCOUNT_ID));
		service.getReviewByPlaceAndAccount(ID_PLACE_NOT_FOUND, ACCOUNT_ID);
	}
	
	@Test
	public void testgetReviewByPlaceAndAccountThanReturn(){
		assertThat(service.getReviewByPlaceAndAccount(PLACE_ID, ACCOUNT_ID), is(reviewDto));
	}
	
	@Test
	public void testFindAllReviewsWithElements(){
	    assertThat(service.findAll(), is(List.of(accountReviewDetailsDto, accountReviewDetailsDto)));
	    verify(accountReviewPlaceRepository).findAll();
	    verify(accountReviewEventRepository).findAll();
	}
}
