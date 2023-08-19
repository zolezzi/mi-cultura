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
public class AccountTest {

	private static final Long ID = 1L;
	private static final String DNI = "35000111";
	private static final String FIRST_NAME = "TEST";
	private static final String LAST_NAME = "TEST";
	private static final String ACCOUNT_ROLE = "ADMIN";
	private static final String ACCOUNT_ROLE_DESCRIPTION = "ADMIN";
	private static final Long OTHER_ID = 10L;
	private static final AccountRole ADMIN_ROLE = AccountRole.ADMIN;
	
	private Account account;
	
	private Account otherAccount;
	
	@Before
	public void setUp() {
		otherAccount = new Account();
		otherAccount.setId(OTHER_ID);
		otherAccount.setDni(DNI);
		otherAccount.setFirstname(ACCOUNT_ROLE);
		account = new Account();
		account.setId(ID);
		account.setDni(DNI);
		account.setFirstname(FIRST_NAME);
		account.setLastname(LAST_NAME);
		account.setAccountRole(ADMIN_ROLE);
	}
	
	@Test
	public void testAccessors(){
		assertThat(account.getId(), is(ID));
		assertThat(account.getAccountRole().name(), is(ACCOUNT_ROLE_DESCRIPTION));
		assertThat(account.getFirstname(), is(FIRST_NAME));
		assertThat(account.getLastname(), is(LAST_NAME));
		assertNotNull(account.toString());
		assertNotNull(account.hashCode());
		assertFalse(account.equals(otherAccount));
	}
}
