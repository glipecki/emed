package eu.anmore.emed;

import java.util.List;

/**
 * Dto object to send list of string.
 * 
 * @author mmiedzinski
 */
public class ListOfStringDto {

	public static ListOfStringDto valueOf(final List<String> stringList) {
		final ListOfStringDto listOfStringDto = new ListOfStringDto();
		listOfStringDto.setStringList(stringList);
		return listOfStringDto;
	}

	public List<String> getStringList() {
		return stringList;
	}

	public void setStringList(final List<String> stringList) {
		this.stringList = stringList;
	}

	private List<String> stringList;
}
