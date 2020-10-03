package business;

import java.util.List;

import business.Book;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public interface ControllerInterface {
	public Auth login(String id, String password) throws LoginException;

	public List<String> allMemberIds();

	public List<String> allBookIds();

	// look up Tables
	public List<User> getUsers();

	public List<Book> getBooks();

	public List<BookCopy> getAvailableBooks();

	public List<BookCopy> getAllBooks();

	public List<LibraryMember> getLibraryMembers();

	public void addNewBook(String isbn, String title, int noOfCopies, String firstName, String lastName,
			String telephoneNumber, String bio, String credentials);

	public boolean addBook(String isbn, int noOfCopies);

	public void addLibraryMember(String memberId, String fname, String lname, String tel, String street, String city,
			String state, String zip);

	public void addUser(String id, String pass, Auth auth);

	public boolean isMemberAvailable(int id);

	// used for checkout
	public boolean checkOutBook(List<String> bookIDs, String memberID);
	public List<CheckOut> getMemberCheckedOutBooks(String memberID);

	// used for checkIn
	//public boolean checkInBook(List<String> bookIDs);
	public boolean checkInBook(List<Integer> checkOutIDs);
	
	//admin tasks
	public void resetEveryThing();

}
