package eu.anmore.utils;

import java.util.HashMap;
import java.util.Map;

public class StringStringMapBuilder {

	public static StringStringMapBuilder of(final String key, final String value) {
		return new StringStringMapBuilder(key, value);
	}

	public Map<String, String> get() {
		return map;
	}

	public StringStringMapBuilder entry(final String key, final String value) {
		map.put(key, value);
		return this;
	}

	private StringStringMapBuilder(final String key, final String value) {
		map = new HashMap<String, String>();
		map.put(key, value);
	}

	private final HashMap<String, String> map;

}
