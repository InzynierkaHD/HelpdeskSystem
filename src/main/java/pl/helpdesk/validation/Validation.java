package pl.helpdesk.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Validation {
	/**
	 * Waliduje adres email.
	 * 
	 * @param email Ciąg znaków do spradzenia.
	 * @return True, jeżeli email jest poprawny. W przeciwnym wypadku false.
	 */
	public static boolean emailValidate(String email) {

		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(email);

		boolean matchFound = m.matches();

		if (matchFound) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Waliduje nazwy zawierające jedynie litery, włączając polskie znaki.
	 * 
	 * @param name Ciąg znaków do alidacji.
	 * @return True, jeżeli ciąg znaków jest poprawny. W przeciwnym wypadku false.
	 */
	public static boolean nameValidate(String name) {

		String illegal_regex = "[^a-zA-ZąćęłńóśźżĄĘŁŃÓŚŹŻ]";
		Pattern p = Pattern.compile(illegal_regex);
		Matcher m = p.matcher(name);

		if (m.find()) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean phoneValidate(String phone) {
		String illegal_regex = "[^0-9]";
		Pattern p = Pattern.compile(illegal_regex);
		Matcher m = p.matcher(phone);

		if (m.find()) {
			return false;
		} else {
			return true;
		}
	}
}
