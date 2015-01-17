package eu.anmore.emed.observation;

import static java.util.Arrays.asList;

import java.util.Arrays;
import java.util.List;

/**
 * Typy parametrów.
 * 
 * @author glipecki
 * 
 */
public enum PredefinedObservationType implements ObservationType {

	/**
	 * Gasometry blood source.
	 */
	GASOMETRYSOURCE("GASOMETRYSOURCE", "Rodzaj", "Źródło krwi", asList(ObservationGroup.GASOMETRY), ValueType.DOMAIN,
			true, true) {

		@Override
		public List<String> getPossibleValues() {
			return Arrays.asList("T", "ŻSZ", "WŁ", "TP", "ŻU", "PP", "LP");
		}

	},

	/**
	 * Temperatura.
	 */
	TEMP("TEMP", "Temperatura", ObservationGroup.HEMODYNAMICS),

	/**
	 * <p>
	 * HR - heart rate - częstość pracy serca [/min] - zakres 0-200 (możliwe
	 * wartości wyższe - rzadko w przypadkach zaburzeń rytmu serca do max. 250)
	 * </p>
	 */
	HR("HR", "HR", ObservationGroup.HEMODYNAMICS),

	/**
	 * <p>
	 * Sat% - saturacja tlenem krwi [%] - wartośc od o do 100%
	 * </p>
	 */
	SAT("SAT", "SAT%", ObservationGroup.HEMODYNAMICS),

	/**
	 * <p>
	 * ABP - arterial blood pressure - ciśnieni krwi tętniczej [mmHg] -
	 * rejestruje się ciśnienie skurczowe, rozkurczowe i średnie - to ostatnie
	 * jest pokazywane na wykresie, zakres do 200mmHg.
	 * </p>
	 */
	ABP("ABP", "ABP", ObservationGroup.HEMODYNAMICS),

	/**
	 * <p>
	 * CVP - central venous pressure - ośrodkowe ciśnienie żylne [mmHg], do max
	 * 50mmHg
	 * </p>
	 */
	CVP("CVP", "CVP", ObservationGroup.HEMODYNAMICS),

	/**
	 * <p>
	 * PAP - pulmonary artery pressure - ciśnienie w tętnicy płucnej [mmHg],
	 * zakres do 100mmHg
	 * </p>
	 */
	PAP("PAP", "PAP", ObservationGroup.HEMODYNAMICS),

	/**
	 * <p>
	 * LAP - left atrial pressure - ciśnienie w lewym przedsionku [mmHg], do
	 * 50mmHg
	 * </p>
	 */
	LAP("LAP", "LAP", ObservationGroup.HEMODYNAMICS),

	/**
	 * <p>
	 * CO - cardiac output - rzut serca [l/min] - właściwie powinien być CI -
	 * cardiac index - indeks sercowy [l/min/m^2]- teraz to sobie uświadomiłem
	 * zakres od 0 do 4-6 l/min/m^2.
	 * </p>
	 */
	CO("CO", "CO", ObservationGroup.HEMODYNAMICS),

	/**
	 * pH.
	 */
	PH("PH", "pH", ObservationGroup.GASOMETRY),

	/**
	 * SAT% dla gazometrii.
	 */
	GASOMETRY_SAT("GASOMETRY_SAT", "SAT%", ObservationGroup.GASOMETRY),

	/**
	 * pCO_2.
	 */
	PCO2("PCO2", "pCO<sub>2</sub>", ObservationGroup.GASOMETRY),

	/**
	 * pO_2.
	 */
	PO2("PO2", "pO<sub>2</sub>", ObservationGroup.GASOMETRY),

	/**
	 * HCO_3.
	 */
	HCO3("HCO3", "HCO<sub>3</sub>", ObservationGroup.GASOMETRY),

	/**
	 * BE.
	 */
	BE("BE", "BE", ObservationGroup.GASOMETRY),

	/**
	 * K^+
	 */
	KPLUS("KPLUS", "K<sup>+</sup>", ObservationGroup.GASOMETRY),

	/**
	 * Na^+.
	 */
	NAPLUS("NAPLUS", "Na<sup>+</sup>", ObservationGroup.GASOMETRY),

	/**
	 * Ca^+^+
	 */
	CAPLUSPLUS("CAPLUSPLUS", "Ca<sup>++</sup>", ObservationGroup.GASOMETRY),

	/**
	 * Lac.
	 */
	LAC("LAC", "Lac", ObservationGroup.GASOMETRY),

	/**
	 * Gluc.
	 */
	GLUC("GLUC", "Gluc", ObservationGroup.GASOMETRY),

	/**
	 * Hgb.
	 */
	HGB("HGB", "Hgb", ObservationGroup.GASOMETRY),

	/**
	 * HCT.
	 */
	HCT("HCT", "HCT", ObservationGroup.GASOMETRY),

	/**
	 * Osiedzie.
	 */
	OSIERDZIE("OSIERDZIE", "Osierdzie", ObservationGroup.DRAINAGE),

	/**
	 * Opłucna lewa.
	 */
	OPLUCNALEWA("OPLUCNALEWA", "Opłucna lewa", ObservationGroup.DRAINAGE),

	/**
	 * Opłucna prawa.
	 */
	OPLUCNAPRAWA("OPLUCNAPRAWA", "Opłucna prawa", ObservationGroup.DRAINAGE),

	/**
	 * Jama brzuszna.
	 */

	JAMABRZUSZNA("JAMABRZUSZNA", "Jama brzuszna", ObservationGroup.DRAINAGE),

	/**
	 * Inne.
	 */
	DRENAZINNE("DRENAZINNE", "Inne", ObservationGroup.DRAINAGE),

	/**
	 * Koloidy.
	 */
	OSOCZE("OSOCZE", "Osocze", ObservationGroup.COLLOIDS),

	/**
	 * Koloidy.
	 */
	ME("ME", "ME", ObservationGroup.COLLOIDS),

	/**
	 * Koloidy.
	 */
	MP("MP", "MP", ObservationGroup.COLLOIDS),

	/**
	 * Diureza.
	 */
	DIURESIS("DIURESIS", "Diureza", ObservationGroup.DIURESIS),

	/**
	 * HDF.
	 */
	HDF("HDF", "HDF", ObservationGroup.DIURESIS),

	/**
	 * Stolec.
	 */
	STOOLE("STOOLE", "Stolec", "Stolec", asList(ObservationGroup.STOOLEEMESIS), ValueType.DOMAIN) {

		@Override
		public List<String> getPossibleValues() {
			return Arrays.asList("A", "AE", "E", "EI", "S", "ZES", "ZIS");
		}

	},

	/**
	 * Wymioty.
	 */
	EMESIS("EMESIS", "Wymioty", "Wymioty", asList(ObservationGroup.STOOLEEMESIS), ValueType.DOMAIN) {

		@Override
		public List<String> getPossibleValues() {
			return Arrays.asList("X");
		}

	},

	/**
	 * Koagulologia.
	 */
	FIBRYNOGEN("FIBRYNOGEN", "Fibrynogen", ObservationGroup.COAGULATIONPANEL),

	/**
	 * Koagulologia.
	 */
	WSK_PROTRO("WSK_PROTRO", "Wskaźnik protrombinowy", ObservationGroup.COAGULATIONPANEL),

	/**
	 * Koagulologia.
	 */
	INR("INR", "INR", ObservationGroup.COAGULATIONPANEL),

	/**
	 * Koagulologia.
	 */
	T_PROTRO("T_PROTRO", "Czas protrombinowy", ObservationGroup.COAGULATIONPANEL),

	/**
	 * Koagulologia.
	 */
	T_KOA_KEF("T_KOA_KEF", "Czas kaolinowo-kefalinowy", ObservationGroup.COAGULATIONPANEL),

	/**
	 * Koagulologia.
	 */
	DDIMER("DDIMER", "D-dimer", ObservationGroup.COAGULATIONPANEL),

	/**
	 * Koagulologia.
	 */
	ANTYTROMBINA("ANTYTROMBINA", "Antytrombina", ObservationGroup.COAGULATIONPANEL),

	/**
	 * Morfologia.
	 */
	WBC("WBC", "WBC", ObservationGroup.CBC),

	/**
	 * Morfologia.
	 */
	BLOOD_HCT("BLOOD_HCT", "HCT", ObservationGroup.CBC),

	/**
	 * Morfologia.
	 */
	BLOOD_HGB("BLOOD_HGB", "HGB", ObservationGroup.CBC),

	/**
	 * Morfologia.
	 */
	RBC("RBC", "RBC", ObservationGroup.CBC),

	/**
	 * Morfologia.
	 */
	MCV("MCV", "MCV", ObservationGroup.CBC),

	/**
	 * Morfologia.
	 */
	MCHC("MCHC", "MCHC", ObservationGroup.CBC),

	/**
	 * Morfologia.
	 */
	MCH("MCH", "MCH", ObservationGroup.CBC),

	/**
	 * Morfologia.
	 */
	RDWCV("RDWCV", "RDW CV", ObservationGroup.CBC),

	/**
	 * Morfologia.
	 */
	RDWSD("RDWSD", "RDW SD", ObservationGroup.CBC),

	/**
	 * Morfologia.
	 */
	PLT("PLT", "PLT", ObservationGroup.CBC),

	/**
	 * Morfologia.
	 */
	NEUTROCYTY("NEUTROCYTY", "Neutrocyty", ObservationGroup.CBC),

	/**
	 * Morfologia.
	 */
	EOZYNOCYTY("EOZYNOCYTY", "Eozynocyty", ObservationGroup.CBC),

	/**
	 * Morfologia.
	 */
	BAZOCYTY("BAZOCYTY", "Bazocyty", ObservationGroup.CBC),

	/**
	 * Morfologia.
	 */
	MONOCYTY("MONOCYTY", "Monocyty", ObservationGroup.CBC),

	/**
	 * Morfologia.
	 */
	LIMFOCYTY("LIMFOCYTY", "Limfocyty", ObservationGroup.CBC),

	/**
	 * Morfologia.
	 */
	OB("OB", "OB", ObservationGroup.CBC),

	/**
	 * Biochemia.
	 */
	ALAT("ALAT", "AlAT", "Aminotransferaza alaninowa", ObservationGroup.BLOODCHEMISTRY),

	/**
	 * Biochemia.
	 */
	ASPAT("ASPAT", "AspAT", "Aminotransferaza asparaginowa", ObservationGroup.BLOODCHEMISTRY),

	/**
	 * Biochemia.
	 */
	TP("TP", "TP", "Białko całkowite w surowicy", ObservationGroup.BLOODCHEMISTRY),

	/**
	 * Biochemia.
	 */
	TBILI("TBILI", "T BILI", ObservationGroup.BLOODCHEMISTRY),

	/**
	 * Biochemia.
	 */
	DBILI("DBILI", "D BILI", ObservationGroup.BLOODCHEMISTRY),

	/**
	 * Biochemia.
	 */
	CL("CL", "Cl", ObservationGroup.BLOODCHEMISTRY),

	/**
	 * Biochemia.
	 */
	ALP("ALP", "ALP", "Fosfataza alkaliczna", ObservationGroup.BLOODCHEMISTRY),

	/**
	 * Biochemia.
	 */
	P("P", "P", ObservationGroup.BLOODCHEMISTRY),

	/**
	 * Biochemia.
	 */
	GGT("GGT", "GGT", "Gamma-glutamylotransferaza", ObservationGroup.BLOODCHEMISTRY),

	/**
	 * Biochemia.
	 */
	GLU("GLU", "GLU", ObservationGroup.BLOODCHEMISTRY),

	/**
	 * Biochemia.
	 */
	KREATYN("KREATYN", "Kreatynina", ObservationGroup.BLOODCHEMISTRY),

	/**
	 * Biochemia.
	 */
	UREA("UREA", "UREA", ObservationGroup.BLOODCHEMISTRY),

	/**
	 * Biochemia.
	 */
	UA("UA", "UA", ObservationGroup.BLOODCHEMISTRY),

	/**
	 * Biochemia.
	 */
	CA("CA", "Ca", ObservationGroup.BLOODCHEMISTRY),

	/**
	 * Biochemia.
	 */
	K("K", "K", ObservationGroup.BLOODCHEMISTRY),

	/**
	 * Biochemia.
	 */
	NA("NA", "Na", ObservationGroup.BLOODCHEMISTRY),

	/**
	 * Biochemia.
	 */
	CREAKT("CREAKT", "Białko C-reaktywne", ObservationGroup.BLOODCHEMISTRY),

	/**
	 * Typ użytkownika.
	 */
	CUSTOM("CUSTOM", "typ użytkownika", ObservationGroup.CUSTOM),

	/**
	 * Sonda podaż.
	 */
	PROBE_IN("PROBE_IN", "Podaż", ObservationGroup.PROBEIN),

	/**
	 * Sonda zalegania.
	 */
	PROBE_OUT("PROBE_OUT", "Zalegania", ObservationGroup.PROBEOUT);

	public static Double getAsDouble(String rawValue) {
		// TODO (glipecki): check this, why json send doubles as 90,00000 ?
		if (rawValue.contains(",")) {
			rawValue = rawValue.replace(",", ".");
		}
		return Double.valueOf(rawValue);
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getVisibleName() {
		return visibleName;
	}

	@Override
	public String getFullName() {
		return fullName;
	}

	@Override
	public List<ObservationGroup> getObservationGroups() {
		return observationGroups;
	}

	@Override
	public ValueType getType() {
		return valueType;
	}

	@Override
	public String getFormat() {
		switch (getType()) {
		case DOMAIN:
			return "%s";
		case NUMERIC:
			return "%.2f";
		default:
			return "%s";
		}
	}

	public List<String> getPossibleValues() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Double getGraphRawValue(final String rawValue) {
		return getAsDouble(rawValue);
	}

	@Override
	public String getDisplayRawValue(final String rawValue) {
		return rawValue;
	}

	@Override
	public boolean isMandatory() {
		return mandatory;
	}

	public boolean isSpecial() {
		return special;
	}

	private PredefinedObservationType(final String key, final String visibleName, final String fullName,
			final ObservationGroup observationGroups) {
		this(key, visibleName, fullName, asList(observationGroups), ValueType.NUMERIC);
	}

	private PredefinedObservationType(final String key, final String visibleName,
			final ObservationGroup observationGroups) {
		this(key, visibleName, visibleName, asList(observationGroups), ValueType.NUMERIC);
	}

	private PredefinedObservationType(final String key, final String visibleName,
			final List<ObservationGroup> observationGroups) {
		this(key, visibleName, visibleName, observationGroups, ValueType.NUMERIC);
	}

	private PredefinedObservationType(final String key, final String visibleName, final String fullName,
			final List<ObservationGroup> observationGroups, final ValueType valueType) {
		this(key, visibleName, fullName, observationGroups, valueType, false, false);
	}

	private PredefinedObservationType(final String key, final String visibleName, final String fullName,
			final List<ObservationGroup> observationGroups, final ValueType valueType, final boolean mandatory,
			final boolean special) {
		this.key = key;
		this.visibleName = visibleName;
		this.fullName = fullName;
		this.observationGroups = observationGroups;
		this.valueType = valueType;
		this.mandatory = mandatory;
		this.special = special;
	}

	private final String key;

	private final String visibleName;

	private final String fullName;

	private final List<ObservationGroup> observationGroups;

	private final ValueType valueType;

	private final boolean mandatory;

	private final boolean special;

}
