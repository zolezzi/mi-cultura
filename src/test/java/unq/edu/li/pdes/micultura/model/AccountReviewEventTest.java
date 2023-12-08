package unq.edu.li.pdes.micultura.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AccountReviewEventTest {

	private static final Long ID = 1L;
	private static final Long OTHER_ID = 2L;
	private static final Long ACCOUNT_ID = 3L;
	private static final Long OTHER_ACCOUNT_ID = 4L;
	private static final Long EVENT_ID = 5L;
	private static final Long OTHER_EVENT_ID = 6L;
	private static final Long REVIEW_ID = 7L;
	private static final Long OTHER_REVIEW_ID = 8L;
	private AccountReviewEvent accountReviewEvent;
	private AccountReviewEvent otherAccountReviewEvent;
	private Event event;
	private Event otherEvent;
	private Review review;
	private Review otherReview;
	private Account account;
	private Account otherAccount;
	
	@Before
	public void setUp() {
		account = new Account();
		account.setId(ACCOUNT_ID);
		event = new Event();
		event.setId(EVENT_ID);
		review = new Review();
		review.setId(REVIEW_ID);
		otherAccount = new Account();
		otherAccount.setId(OTHER_ACCOUNT_ID);
		otherEvent = new Event();
		otherEvent.setId(OTHER_EVENT_ID);
		otherReview = new Review();
		otherReview.setId(OTHER_REVIEW_ID);
		accountReviewEvent = new AccountReviewEvent();
		accountReviewEvent.setId(ID);
		accountReviewEvent.setAccount(account);
		accountReviewEvent.setEvent(event);
		accountReviewEvent.setReview(review);
		otherAccountReviewEvent = new AccountReviewEvent();
		otherAccountReviewEvent.setId(OTHER_ID);
		otherAccountReviewEvent.setAccount(otherAccount);
		otherAccountReviewEvent.setEvent(otherEvent);
		otherAccountReviewEvent.setReview(otherReview);
	}
	
	@Test
	public void testAccessors(){
		assertThat(accountReviewEvent.getId(), is(ID));
		assertThat(accountReviewEvent.getAccount().getId(), is(ACCOUNT_ID));
		assertThat(accountReviewEvent.getEvent().getId(), is(EVENT_ID));
		assertThat(accountReviewEvent.getReview().getId(), is(REVIEW_ID));
		assertNotNull(accountReviewEvent.toString());
		assertNotNull(accountReviewEvent.hashCode());
		assertFalse(accountReviewEvent.equals(otherAccountReviewEvent));
	}
}
