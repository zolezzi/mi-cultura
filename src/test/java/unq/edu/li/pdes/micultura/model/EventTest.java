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
public class EventTest {

	private static final Long ID = 1L;
	private static final Long OTHER_ID = 2L;
	private static final Long EVENT_ID = 1L;
	private static final Long EVENT_OTHER_ID = 2L;
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
	private static final String ANNOUNCEMENT_NAME = EventType.ANNOUNCEMENT.name();
	private static final EventType ANNOUNCEMENT = EventType.ANNOUNCEMENT;
	private Event event;
	private Event otherEvent;
	
	@Before
	public void setUp() {
		otherEvent = new Event();
		otherEvent.setBody(BODY);;
		otherEvent.setDependsOn(DEPENDS_ON);
		otherEvent.setFromDate(FROM_DATE);
		otherEvent.setEmail(EMAIL);
		otherEvent.setId(OTHER_ID);
		otherEvent.setLink(LINK);
		otherEvent.setImage(IMAGE);
		otherEvent.setEventType(ANNOUNCEMENT);
		otherEvent.setState(STATE);
		otherEvent.setSubTitle(SUBTITLE);
		otherEvent.setUrl(URL);
		otherEvent.setToDate(TO_DATE);
		otherEvent.setTitle(TITLE);
		otherEvent.setId(OTHER_ID);
		otherEvent.setEventId(EVENT_OTHER_ID);
		event = new Event();
		event.setBody(BODY);;
		event.setDependsOn(DEPENDS_ON);
		event.setFromDate(FROM_DATE);
		event.setEmail(EMAIL);
		event.setId(OTHER_ID);
		event.setLink(LINK);
		event.setImage(IMAGE);
		event.setEventType(ANNOUNCEMENT);
		event.setState(STATE);
		event.setSubTitle(SUBTITLE);
		event.setUrl(URL);
		event.setToDate(TO_DATE);
		event.setTitle(TITLE);
		event.setId(ID);
		event.setEventId(EVENT_ID);
	}
	
	@Test
	public void testAccessors(){
		assertThat(event.getId(), is(ID));
		assertThat(event.getEventType().name(), is(ANNOUNCEMENT_NAME));
		assertThat(event.getBody(), is(BODY));
		assertThat(event.getDependsOn(), is(DEPENDS_ON));
		assertThat(event.getFromDate(), is(FROM_DATE));
		assertThat(event.getEmail(), is(EMAIL));
		assertThat(event.getLink(), is(LINK));
		assertThat(event.getImage(), is(IMAGE));
		assertThat(event.getState(), is(STATE));
		assertThat(event.getSubTitle(), is(SUBTITLE));
		assertThat(event.getToDate(), is(TO_DATE));
		assertThat(event.getTitle(), is(TITLE));
		assertThat(event.getUrl(), is(URL));
		assertThat(event.getEventId(), is(EVENT_ID));
		assertNotNull(event.toString());
		assertNotNull(event.hashCode());
		assertFalse(event.equals(otherEvent));
	}
}
