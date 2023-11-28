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
public class ReviewTest {

	private static final Long ID = 1L;
	private static final Long OTHER_ID = 2L;
	private static final String COMMENTS = "El Complejo Hist&oacute;rico Cultural Manzana de las Luces depende del Ministerio de Cultura";
	private static final Double SCORE = 2.5d;
	
	private Review review;
	private Review otherReview;
	
	@Before
	public void setUp() {
		review = new Review();
		review.setId(ID);
		review.setCommets(COMMENTS);
		review.setScore(SCORE);
		otherReview = new Review();
		otherReview.setId(OTHER_ID);
		otherReview.setCommets(COMMENTS);
		otherReview.setScore(SCORE);
	}
	
	@Test
	public void testAccessors(){
		assertThat(review.getId(), is(ID));
		assertThat(review.getCommets(), is(COMMENTS));
		assertThat(review.getScore(), is(SCORE));
		assertNotNull(review.toString());
		assertNotNull(review.hashCode());
		assertFalse(review.equals(otherReview));
	}
}
