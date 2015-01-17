package eu.anmore.utils;

import java.util.Date;
import java.util.GregorianCalendar;

public final class DateUtil {

	public static boolean isOtherDay(final Date datea, final Date dateb) {
		return !isSameDay(datea, dateb);
	}

	public static boolean isSameDay(final Date datea, final Date dateb) {
		if (datea == null) {
			return false;
		} else {
			return datea.getYear() == dateb.getYear() && datea.getMonth() == dateb.getMonth()
					&& datea.getDate() == dateb.getDate();
		}
	}

	public static Date getAsDayStart(final Date date) {
		return new GregorianCalendar(1900 + date.getYear(), date.getMonth(), date.getDate()).getTime();
	}

}
