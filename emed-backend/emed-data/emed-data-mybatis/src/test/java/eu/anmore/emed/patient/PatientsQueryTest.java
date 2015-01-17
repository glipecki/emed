package eu.anmore.emed.patient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link PatientsQuery}.
 * 
 * @author mmiedzinski
 */
public class PatientsQueryTest {

	@Before
	public void setUp() {
		queryBuilder = PatientsQuery.getBuilder();
	}

	@Test
	public void shouldBuildQuery() {
		// given

		// when
		final PatientsQuery query = queryBuilder.firstNameEquals(SAMPLE_FIRSTNAME).surnameEquals(SAMPLE_SURNAME)
				.peselEquals(SAMPLE_PESEL).idEquals(SAMPLE_ID).birthdayEquals(SAMPLE_BIRTHDAY).sexEquals(SAMPLE_SEX)
				.build();

		// then
		assertEquals(SAMPLE_FIRSTNAME, query.getFirstName());
		assertEquals(SAMPLE_SURNAME, query.getSurname());
		assertEquals(SAMPLE_PESEL, query.getPesel());
		assertEquals(SAMPLE_ID, query.getId());
		assertEquals(SAMPLE_BIRTHDAY, query.getBirthday());
		assertEquals(SAMPLE_SEX, query.getSex());
	}

	@Test
	public void shouldBuildQueryWithOrderBySection() {
		// given
		final int expectedOrderBySize = 2;

		// when
		final PatientsQuery query = queryBuilder.orderBy(PatientEntity.FIRSTNAME_COLUMN_NAME)
				.orderBy(PatientEntity.PESEL_COLUMN_NAME).build();

		// then
		assertNotNull(query.getOrderByStatments());
		assertEquals(expectedOrderBySize, query.getOrderByStatments().size());
		assertEquals(PatientEntity.FIRSTNAME_COLUMN_NAME, query.getOrderByStatments().get(0));
		assertEquals(PatientEntity.PESEL_COLUMN_NAME, query.getOrderByStatments().get(1));
	}

	private static final Long SAMPLE_ID = 3L;

	private static final String SAMPLE_FIRSTNAME = "firstname";

	private static final String SAMPLE_SURNAME = "surname";

	private static final String SAMPLE_PESEL = "12345678909";

	private static final Patient.Sex SAMPLE_SEX = Patient.Sex.MAN;

	private static final Date SAMPLE_BIRTHDAY = new Date();

	private PatientsQuery.PatientsQueryBuilder queryBuilder;
}
