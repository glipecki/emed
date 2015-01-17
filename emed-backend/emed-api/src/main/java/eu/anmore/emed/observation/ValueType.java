package eu.anmore.emed.observation;

/**
 * Typ wartości obserwacji.
 * 
 * @author glipecki
 * 
 */
public enum ValueType {

	/**
	 * Liczba zmiennoprzecinkowa.
	 */
	NUMERIC,

	/**
	 * Parametr z wartością należącą do dziedziny.
	 * <p>
	 * Dostępna jest lista prawidłowych wartości wraz z mapowaniem na etykiety.
	 * </p>
	 */
	DOMAIN

}
