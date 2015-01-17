package eu.anmore.emed.patient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.anmore.emed.helpers.ObjectsCloner;

/**
 * Patient query.
 * 
 * @author mmiedzinski
 */
public final class PatientsQuery {

	public static PatientsQueryBuilder getBuilder() {
		return PatientsQueryBuilder.getBuilder();
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getSurname() {
		return surname;
	}

	public String getPesel() {
		return pesel;
	}

	public Date getBirthday() {
		return ObjectsCloner.cloneDate(birthday);
	}

	public Patient.Sex getSex() {
		return sex;
	}

	public List<String> getOrderByStatments() {
		return orderby;
	}

	/**
	 * {@link PatientsQuery} builder.
	 * 
	 * @author mmiedzinski
	 */
	public static final class PatientsQueryBuilder {

		private static PatientsQueryBuilder getBuilder() {
			return new PatientsQueryBuilder();
		}

		public PatientsQueryBuilder idEquals(final long id) {
			patientQuery.id = id;
			return this;
		}

		public PatientsQueryBuilder firstNameEquals(final String firstname) {
			patientQuery.firstName = firstname;
			return this;
		}

		public PatientsQueryBuilder surnameEquals(final String surname) {
			patientQuery.surname = surname;
			return this;
		}

		public PatientsQueryBuilder peselEquals(final String pesel) {
			patientQuery.pesel = pesel;
			return this;
		}

		public PatientsQueryBuilder birthdayEquals(final Date birthday) {
			patientQuery.birthday = birthday;
			return this;
		}

		public PatientsQueryBuilder sexEquals(final Patient.Sex sex) {
			patientQuery.sex = sex;
			return this;
		}

		public PatientsQueryBuilder orderBy(final String columnName) {
			if (patientQuery.orderby == null) {
				patientQuery.orderby = new ArrayList<String>();
			}
			patientQuery.orderby.add(columnName);
			return this;
		}

		public PatientsQuery build() {
			return patientQuery;
		}

		private PatientsQueryBuilder() {
			patientQuery = new PatientsQuery();
		}

		private final PatientsQuery patientQuery;
	}

	private Long id;

	private String firstName;

	private String surname;

	private String pesel;

	private Date birthday;

	private Patient.Sex sex;

	private List<String> orderby;
}
