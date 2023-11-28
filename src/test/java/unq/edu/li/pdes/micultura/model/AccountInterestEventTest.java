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
public class AccountInterestEventTest {

	private static final Long ID = 1L;
	private static final Long OTHER_ID = 2L;
	private static final Long ACCOUNT_ID = 3L;
	private static final Long OTHER_ACCOUNT_ID = 4L;
	private static final Long EVENT_ID = 5L;
	private static final Long OTHER_EVENT_ID = 6L;
	private AccountInterestEvent accountInterestEvent;
	private AccountInterestEvent otherAccountInterestEvent;
	private Event event;
	private Event otherEvent;
	private Account account;
	private Account otherAccount;
	
	@Before
	public void setUp() {
		account = new Account();
		account.setId(ACCOUNT_ID);
		event = new Event();
		event.setId(EVENT_ID);
		otherAccount = new Account();
		otherAccount.setId(OTHER_ACCOUNT_ID);
		otherEvent = new Event();
		otherEvent.setId(OTHER_EVENT_ID);
		accountInterestEvent = new AccountInterestEvent();
		accountInterestEvent.setId(ID);
		accountInterestEvent.setAccount(account);
		accountInterestEvent.setEvent(event);
		accountInterestEvent.setIsFavorite(Boolean.TRUE);
		otherAccountInterestEvent = new AccountInterestEvent();
		otherAccountInterestEvent.setId(OTHER_ID);
		otherAccountInterestEvent.setAccount(otherAccount);
		otherAccountInterestEvent.setEvent(otherEvent);
	}
	
	@Test
	public void testAccessors(){
		assertThat(accountInterestEvent.getId(), is(ID));
		assertThat(accountInterestEvent.getAccount().getId(), is(ACCOUNT_ID));
		assertThat(accountInterestEvent.getEvent().getId(), is(EVENT_ID));
		assertTrue(accountInterestEvent.getIsFavorite());
		assertNotNull(accountInterestEvent.toString());
		assertNotNull(accountInterestEvent.hashCode());
		assertFalse(accountInterestEvent.equals(otherAccountInterestEvent));
	}
}
