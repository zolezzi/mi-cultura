package unq.edu.li.pdes.micultura.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AccountInterestPlaceTest {

	private static final Long ID = 1L;
	private static final Long OTHER_ID = 2L;
	private static final Long ACCOUNT_ID = 3L;
	private static final Long OTHER_ACCOUNT_ID = 4L;
	private static final Long PLACE_ID = 5L;
	private static final Long OTHER_PLACE_ID = 6L;
	private AccountInterestPlace accountInterestPlace;
	private AccountInterestPlace otherAccountInterestPlace;
	private Place place;
	private Place otherPlace;
	private Account account;
	private Account otherAccount;
	
	@Before
	public void setUp() {
		account = new Account();
		account.setId(ACCOUNT_ID);
		place = new Place();
		place.setId(PLACE_ID);
		otherAccount = new Account();
		otherAccount.setId(OTHER_ACCOUNT_ID);
		otherPlace = new Place();
		otherPlace.setId(OTHER_PLACE_ID);
		accountInterestPlace = new AccountInterestPlace();
		accountInterestPlace.setId(ID);
		accountInterestPlace.setAccount(account);
		accountInterestPlace.setPlace(place);
		accountInterestPlace.setIsFavorite(Boolean.TRUE);
		otherAccountInterestPlace = new AccountInterestPlace();
		otherAccountInterestPlace.setId(OTHER_ID);
		otherAccountInterestPlace.setAccount(otherAccount);
		otherAccountInterestPlace.setPlace(otherPlace);
	}
	
	@Test
	public void testAccessors(){
		assertThat(accountInterestPlace.getId(), is(ID));
		assertThat(accountInterestPlace.getAccount().getId(), is(ACCOUNT_ID));
		assertThat(accountInterestPlace.getPlace().getId(), is(PLACE_ID));
		assertTrue(accountInterestPlace.getIsFavorite());
		assertNotNull(accountInterestPlace.toString());
		assertNotNull(accountInterestPlace.hashCode());
		assertFalse(accountInterestPlace.equals(otherAccountInterestPlace));
	}
}
