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
public class AccountReviewPlaceTest {

	private static final Long ID = 1L;
	private static final Long OTHER_ID = 2L;
	private static final Long ACCOUNT_ID = 3L;
	private static final Long OTHER_ACCOUNT_ID = 4L;
	private static final Long PLACE_ID = 5L;
	private static final Long OTHER_PLACE_ID = 6L;
	private static final Long REVIEW_ID = 7L;
	private static final Long OTHER_REVIEW_ID = 8L;
	private AccountReviewPlace accountReviewPlace;
	private AccountReviewPlace otherAccountReviewPlace;
	private Place place;
	private Place otherPlace;
	private Review review;
	private Review otherReview;
	private Account account;
	private Account otherAccount;
	
	@Before
	public void setUp() {
		account = new Account();
		account.setId(ACCOUNT_ID);
		place = new Place();
		place.setId(PLACE_ID);
		review = new Review();
		review.setId(REVIEW_ID);
		otherAccount = new Account();
		otherAccount.setId(OTHER_ACCOUNT_ID);
		otherPlace = new Place();
		otherPlace.setId(OTHER_PLACE_ID);
		otherReview = new Review();
		otherReview.setId(OTHER_REVIEW_ID);
		accountReviewPlace = new AccountReviewPlace();
		accountReviewPlace.setId(ID);
		accountReviewPlace.setAccount(account);
		accountReviewPlace.setPlace(place);
		accountReviewPlace.setReview(review);
		otherAccountReviewPlace = new AccountReviewPlace();
		otherAccountReviewPlace.setId(OTHER_ID);
		otherAccountReviewPlace.setAccount(otherAccount);
		otherAccountReviewPlace.setPlace(otherPlace);
		otherAccountReviewPlace.setReview(otherReview);
	}
	
	@Test
	public void testAccessors(){
		assertThat(accountReviewPlace.getId(), is(ID));
		assertThat(accountReviewPlace.getAccount().getId(), is(ACCOUNT_ID));
		assertThat(accountReviewPlace.getPlace().getId(), is(PLACE_ID));
		assertThat(accountReviewPlace.getReview().getId(), is(REVIEW_ID));
		assertNotNull(accountReviewPlace.toString());
		assertNotNull(accountReviewPlace.hashCode());
		assertFalse(accountReviewPlace.equals(otherAccountReviewPlace));
	}
}
