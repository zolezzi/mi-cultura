package unq.edu.li.pdes.micultura.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserTest {
	private static final Long ID = 1L;
	private static final String EMAIL = "admin@gmail.com";
	private static final String PASSWORD = "admin";
	
	private User user;
	
	private User otherUser;
	
	@Before
	public void setUp(){
		otherUser = new User();
		otherUser.setEmail(EMAIL);
		otherUser.setPassword(PASSWORD);
		user = new User();
		user.setId(ID);
		user.setEmail(EMAIL);
		user.setPassword(PASSWORD);
	}
	
	@Test
	public void testAccessors(){
		assertThat(user.getId(), is(ID));
		assertThat(user.getEmail(), is(EMAIL));
		assertThat(user.getUsername(), is(EMAIL));
		assertThat(user.getPassword(), is(PASSWORD));
		assertThat(user.isAccountNonExpired(), is(Boolean.TRUE));
		assertThat(user.isAccountNonLocked(), is(Boolean.TRUE));
		assertThat(user.isCredentialsNonExpired(), is(Boolean.TRUE));
		assertThat(user.isEnabled(), is(Boolean.TRUE));
		assertThat(user.getAuthorities(), is(Collections.emptyList()));
		assertNotNull(user.toString());
		assertNotNull(user.hashCode());
		assertFalse(user.equals(otherUser));
	}
}
