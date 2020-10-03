package business;

public class Validator {
	public static Error firstNameValidator(String name) {
		if (name.matches(".*\\d.*") || name.isEmpty()) {
			return new Error(false, "Invalid First Name \n");
		}
		return new Error(true, "");
	}

	public static Error lastNameValidator(String name) {
		if (name.matches(".*\\d.*") || name.isEmpty()) {
			return new Error(false, "Invalid Last Name \n");
		}
		return new Error(true, "");
	}

	public static Error phoneNumberValidator(String phoneNumber) {
		if (phoneNumber.matches("\\d{10}") || phoneNumber.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")
				|| phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")
				|| phoneNumber.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}"))
			return new Error(true, "");
		else
			return new Error(false, "Invalid phone Number \n");
	}

	public static Error emptyString(String phoneNumber) {
		if (phoneNumber.isEmpty())
			return new Error(false, "");
		else
			return new Error(true, "");
	}

	public static Error emptyBookTitle(String bookTitle) {
		if (bookTitle.isEmpty())
			return new Error(false, "Empty BookTitle Field \n");
		else
			return new Error(true, "");
	}

	public static Error emptyAuthorsCredentials(String authorsCredentials) {
		if (authorsCredentials.isEmpty())
			return new Error(false, "Empty authorsCredentials Field \n");
		else
			return new Error(true, "");
	}

	public static Error emptyAuthorsBio(String authorsBio) {
		if (authorsBio.isEmpty())
			return new Error(false, "Empty authorsBio Field \n");
		else
			return new Error(true, "");
	}

	public static Error isbnValidator(String isbn) {
		if (isbn.length() != 13 || isbn.isEmpty())
			return new Error(false, "Invalid ISBN Number \n");
		else
			return new Error(true, "");
	}

	public static Error numberValidator(String number) {
		try {
			Integer.parseInt(number);
			return new Error(true, "");
		} catch (NumberFormatException nfe) {
		}
		return new Error(false, "Invalid Number of copies\n");
	}

}