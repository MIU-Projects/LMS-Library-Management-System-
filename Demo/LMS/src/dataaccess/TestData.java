package dataaccess;

import java.util.ArrayList;
import java.util.List;

import business.Book;
import business.BookCopy;
import business.CheckOut;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;

/**
 * This class loads data into the data repository and also sets up the storage
 * units that are used in the application. The main method in this class must be
 * run once (and only once) before the rest of the application can work
 * properly. It will create three serialized objects in the dataaccess.storage
 * folder.
 * 
 *
 */
public class TestData {

	ControllerInterface ci = new SystemController();

	public static void main(String[] args) {
		TestData td = new TestData();
		DataAccess da = new DataAccessFacade();
		
		// USER ///////////////////////////////////////////
		System.out.println("User Data");
		System.out.println("______________________________________________________");
		td.userData();
		List<User> userList = td.getUsers();
		for (User u : userList) {
			System.out.print("UserID :" + u.getId() + " ");
			System.out.print("Password :" + u.getPassword() + " ");
			System.out.println("AuthorizationLevel :" + u.getAuthorization() + " ");
		}

		
		
		// Book //////////////////////////////////////////////////
		System.out.println("Book Data");
		System.out.println("______________________________________________________");
		td.bookData();
		List<Book> bookList = td.getBooks();
		for (Book u : bookList) {
			System.out.print("Book Isbn :" + u.getIsbn() + " ");
			System.out.print("Book Copies Count :" + u.getNumCopies() + " ");
			System.out.print("Book Credentials:" + u.getCredentials() + " ");
			System.out.println("Book Title :" + u.getTitle() + " ");
		}
//
//		
//		
		// LIBRARYMEMBER //////////////////////////////////////
		System.out.println("LibraryMember Data");
		System.out.println("______________________________________________________");
		td.libraryMemberData();
		List<LibraryMember> libraryMemberList = td.getLibraryMembers();
		for (LibraryMember u : libraryMemberList) {
			System.out.print("LibraryMemberID :" + u.getMemberId() + " ");
			System.out.print("FirstName :" + u.getFirstName() + " ");
			System.out.print("LastName :" + u.getLastName() + " ");
			System.out.print("Address :" + u.getAddress());
			System.out.println("CheckOuts :" + u.getCheckOuts());
		}
//
//		
//		
		// CHECKOUT //////////////////////////////////////////////////
		System.out.println("CHECK OUT DATA");
		System.out.println("______________________________________________________");
		System.out.println("Available Books");
		List<BookCopy> availableBooks = td.getAvailableBookCopys();
		for (BookCopy u : availableBooks) {
			System.out.print("BookID :" + u.getBookID() + " ");
			System.out.print("CopyNum :" + u.getCopyNum() + " ");
			System.out.print("BookAvailable :" + u.isAvailable() + " ");
			System.out.print("BookTitle :" + u.getBook().getTitle() + " ");
			System.out.print("Book ISBN :" + u.getBook().getIsbn() + " ");
			System.out.println("Author First Name :" + u.getBook().getAuthor().getFirstName() + " ");
		}
//		
		System.out.println("CheckOut Books");
		List<String> bookIDs = new ArrayList<String>();
		bookIDs.add("48-56882/2");
		bookIDs.add("the new book123456/2");
		td.checkOutBook(bookIDs, "1000");
		System.out.println("after CheckOut");
		System.out.println("__________________________");
		List<BookCopy> bookCopyData2 = td.getAllBookCopys();
		for (BookCopy u : bookCopyData2) {
			System.out.print("BookID :" + u.getBookID() + " ");
			System.out.print("CopyNum :" + u.getCopyNum() + " ");
			System.out.print("BookAvailable :" + u.isAvailable() + " ");
			System.out.print("BookTitle :" + u.getBook().getTitle() + " ");
			System.out.print("Book ISBN :" + u.getBook().getIsbn() + " ");
			System.out.println("Author First Name :" + u.getBook().getAuthor().getFirstName() + " ");
		}
//		
//		
//		
		// CHECKIN //////////////////////////////////////////////////
		System.out.println("CHECK IN BOOKS");
		System.out.println("______________________________________________________");
		System.out.println("CheckOut By Members");
		List<CheckOut> checkOutRecords = td.getMemberCheckedOutBooks("1000");
		for(CheckOut co : checkOutRecords) {
			System.out.print("CheckOutID:" + co.getCheckOutID());
			System.out.print(" BookID : " +co.getBookCopyID());
			System.out.print(" CheckInStatus : " +co.getCheckInStatus());
			System.out.print(" FineAmount : " +co.getFineAmount());
			System.out.print(" DueDate : " +co.getDueDate());
			System.out.println(" ReturnedDate : " +co.getReturnedDate());
		}
//		
		System.out.println("CheckIn Book By Member");
		List<Integer> checkOutIDs = new ArrayList<Integer>();
		checkOutIDs.add(13);
		checkOutIDs.add(14);
		td.checkInBook(checkOutIDs);
		System.out.println("List of Books after CheckIn");
		System.out.println("__________________________");
		System.out.println();
		List<BookCopy> bookCopyData3 = td.getAllBookCopys();
		for (BookCopy u : bookCopyData3) {
			System.out.print("BookID :" + u.getBookID() + " ");
			System.out.print("CopyNum :" + u.getCopyNum() + " ");
			System.out.print("BookAvailable :" + u.isAvailable() + " ");
			System.out.print("BookTitle :" + u.getBook().getTitle() + " ");
			System.out.print("Book ISBN :" + u.getBook().getIsbn() + " ");
			System.out.println("Author First Name :" + u.getBook().getAuthor().getFirstName() + " ");
		}
//		
		System.out.println("List of CheckOut After Checked IN");
		System.out.println("__________________________");
		List<CheckOut> checkOutRecordss = td.getMemberCheckedOutBooks("1000");
		for(CheckOut co : checkOutRecordss) {
			System.out.print("CheckOutID:" + co.getCheckOutID());
			System.out.print("BookID : " +co.getBookCopyID());
			System.out.print("CheckInStatus : " +co.getCheckInStatus());
			System.out.print("FineAmount : " +co.getFineAmount());
			System.out.print("DueDate : " +co.getDueDate());
			System.out.print("ReturnedDate : " +co.getReturnedDate());
		}
	}

	public void userData() {
		ci.addUser("", "", Auth.BOTH);
	}

	public List<User> getUsers() {
		return ci.getUsers();
	}

	public void bookData() {
//		ci.addNewBook("23-11451", "The Big Fish", 2, "Rodney", "Erickson", "09323555478",
//				"Rodney Erickson is a content marketing professional at HubSpot, an inbound marketing and sales platform that helps companies attract visitors, convert leads, and close customers. Previously, Rodney worked as a marketing manager for a tech software startup. He graduated with honors from Columbia University with a dual degree in Business Administration and Creative Writing" , "credentials");
//		ci.addNewBook("28-12331", "Antartica", 2, "Mark", "Gallion", "093255478",
//				"As a venture capitalist and an executive at several start-ups, Mark Gallion has different versions of his bio all over the internet. You can imagine some are more formal than others. But when it comes to his Twitter bio, he carefully phrased his information in a way that helps him connect with his audience -- specifically, through the use of humor", "credentials");
//		ci.addNewBook("99-22223", "Thinking Java", 2, "tadewos", "teklemariam", "093255478", "hee he is", "credentials");
//		ci.addNewBook("48-56882", "Jimmy's First Day of School", 1, "tadewos", "teklemariam", "093255478", "hee he is", "credentials");

	}

	public List<Book> getBooks() {
		return ci.getBooks();
	}

	public List<LibraryMember> getLibraryMembers() {
		return ci.getLibraryMembers();
	}

	public void libraryMemberData() {
		ci.addLibraryMember("1000", "Andy", "Rogers", "641-223-2211", "2000 N.", "FairField", "IOWA", "52557");
		ci.addLibraryMember("1001", "Drew", "Stevens", "702-998-2414", "2000 N.", "FairField", "IOWA", "52557");
		ci.addLibraryMember("1002", "Drew", "Stevens", "702-998-2414", "2000 N.", "FairField", "IOWA", "52557");
		ci.addLibraryMember("1003", "Sarah", "Eagleton", "451-234-8811", "2000 N.", "FairField", "IOWA", "52557");
		ci.addLibraryMember("1004", "Ricardo", "Montalbahn", "641-472-2871", "2000 N.", "FairField", "IOWA", "52557");
	}

	public List<BookCopy> getAvailableBookCopys() {
		return ci.getAvailableBooks();
	}

	public List<BookCopy> getAllBookCopys() {
		return ci.getAllBooks();
	}

	public void checkOutBook(List<String> bookIDs, String memberID) {
		ci.checkOutBook(bookIDs, memberID);
	}

	public List<CheckOut> getMemberCheckedOutBooks(String memberID){
		return ci.getMemberCheckedOutBooks(memberID);
	}
	
	public void checkInBook(List<Integer> bookIDs) {
		ci.checkInBook(bookIDs);
	}
}
