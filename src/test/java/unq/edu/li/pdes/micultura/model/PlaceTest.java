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
public class PlaceTest {

	private static final Long ID = 1L;
	private static final Long OTHER_ID = 2L;
	private static final String NAME = "https://www.cultura.gob.ar/api/v2.0/organismos/27/?format=api";
	private static final String URL = "http://test.com";
	private static final String LINK = "https://www.cultura.gob.ar/institucional/organismos/museos/comision-nacional-de-la-manzana-de-las-luces/";
	private static final String ADDRESS = "Perú 294, Ciudad de Buenos Aires";
	private static final String PHONE_NUMBER = "+54 (011) 4342-9930 / 6973";
	private static final String DESCRIPTION = "El Complejo Hist&oacute;rico Cultural Manzana de las Luces depende del Ministerio de Cultura";
	private static final String EMAIL = "EMAIL@GOB.COM.AR";
	private static final String PROVINCE = "CABA";
	private static final String DEPENDS_ON = "Ministerio de Cultura de la Nación";
	private static final String INSTITUTE_NAME = PlaceType.INSTITUTE.name();
	private static final PlaceType INSTITUTE = PlaceType.INSTITUTE;
	private Place place;
	private Place otherPlace;
	
	@Before
	public void setUp() {
		otherPlace = new Place();
		otherPlace.setAddress(ADDRESS);
		otherPlace.setDependsOn(DEPENDS_ON);
		otherPlace.setDescription(DESCRIPTION);
		otherPlace.setEmail(EMAIL);
		otherPlace.setId(OTHER_ID);
		otherPlace.setLink(LINK);
		otherPlace.setName(NAME);
		otherPlace.setPlaceType(INSTITUTE);
		otherPlace.setPhoneNumber(PHONE_NUMBER);
		otherPlace.setProvince(PROVINCE);
		otherPlace.setUrl(URL);
		place = new Place();
		place.setAddress(ADDRESS);
		place.setDependsOn(DEPENDS_ON);
		place.setDescription(DESCRIPTION);
		place.setEmail(EMAIL);
		place.setId(ID);
		place.setLink(LINK);
		place.setName(NAME);
		place.setPlaceType(INSTITUTE);
		place.setPhoneNumber(PHONE_NUMBER);
		place.setProvince(PROVINCE);
		place.setUrl(URL);
	}
	
	@Test
	public void testAccessors(){
		assertThat(place.getId(), is(ID));
		assertThat(place.getPlaceType().name(), is(INSTITUTE_NAME));
		assertThat(place.getAddress(), is(ADDRESS));
		assertThat(place.getDependsOn(), is(DEPENDS_ON));
		assertThat(place.getDescription(), is(DESCRIPTION));
		assertThat(place.getEmail(), is(EMAIL));
		assertThat(place.getLink(), is(LINK));
		assertThat(place.getPhoneNumber(), is(PHONE_NUMBER));
		assertThat(place.getProvince(), is(PROVINCE));
		assertThat(place.getUrl(), is(URL));
		assertNotNull(place.toString());
		assertNotNull(place.hashCode());
		assertFalse(place.equals(otherPlace));
	}
}
