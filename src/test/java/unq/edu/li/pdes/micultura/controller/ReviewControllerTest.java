package unq.edu.li.pdes.micultura.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
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

import unq.edu.li.pdes.micultura.dto.AccountReviewDetailsDTO;
import unq.edu.li.pdes.micultura.dto.ReviewDTO;
import unq.edu.li.pdes.micultura.service.impl.ReviewServiceImpl;
import unq.edu.li.pdes.micultura.vo.PlaceVO;
import unq.edu.li.pdes.micultura.vo.ReviewVO;

@RunWith(MockitoJUnitRunner.class)
public class ReviewControllerTest {

	private static final Long REVIEW_ID = 1L;
	private static final Long PLACE_ID = 1L;
	private static final Long EVENT_ID = 2L;
	private static final Long ACCOUNT_ID = 1L;
	
	@Mock
	private ReviewServiceImpl service;
	
	@Mock
	private ReviewDTO reviewDto;
	
	@Mock
	private ReviewVO reviewVO;
	
	@Mock
	private PlaceVO placeVO;
	
	@Mock
	private AccountReviewDetailsDTO accountReviewDetailsDto;
	
	@InjectMocks
	private ReviewController controller;
	
	@Before
	public void setUp(){
		when(service.findById(REVIEW_ID)).thenReturn(reviewDto);
		when(service.getReviewByPlaceAndAccount(PLACE_ID, ACCOUNT_ID)).thenReturn(reviewDto);
		when(service.getReviewByEventAndAccount(EVENT_ID, ACCOUNT_ID)).thenReturn(reviewDto);
		when(service.findAll()).thenReturn(List.of(accountReviewDetailsDto));
	}
	
	@Test
	public void testfindByIdThenReturnAReviewDTO(){
		assertThat(controller.findById(REVIEW_ID), is(reviewDto));
		verify(service).findById(eq(REVIEW_ID));
	}
	
	@Test
	public void testgetReviewByPlaceAndAccountThenReturnAReviewDTO(){
		assertThat(controller.getReview(PLACE_ID, ACCOUNT_ID), is(reviewDto));
		verify(service).getReviewByPlaceAndAccount(eq(PLACE_ID), eq(ACCOUNT_ID));
	}

	@Test
	public void testgetReviewByEventAndAccountThenReturnAReviewDTO(){
		assertThat(controller.getReviewEvent(EVENT_ID, ACCOUNT_ID), is(reviewDto));
		verify(service).getReviewByEventAndAccount(eq(EVENT_ID), eq(ACCOUNT_ID));
	}
	
	@Test
	public void testFindAllReviews(){
		assertThat(controller.findAll(), is(List.of(accountReviewDetailsDto)));
		verify(service).findAll();
	}
}
