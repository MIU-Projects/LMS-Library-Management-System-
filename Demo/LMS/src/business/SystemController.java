package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {

	public static Auth currentAuth = null;
	public static String currentUserName = null;
	private static DataAccess dbAccessInstance = DataAccessFacade.getInstance();

	public SystemController() {
	};

	public Auth login(String id, String password) throws LoginException {
		HashMap<String, User> map = dbAccessInstance.readUserMap();
		if (!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if (!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentUserName = id;
		return currentAuth = map.get(id).getAuthorization();

	}

	@Override
	public List<String> allMemberIds() {
		List<String> retval = new ArrayList<>();
		retval.addAll(dbAccessInstance.readMemberMap().keySet());
		return retval;
	}

	@Override
	public List<String> allBookIds() {
		List<String> retval = new ArrayList<>();
		retval.addAll(dbAccessInstance.readBooksMap().keySet());
		return retval;
	}

	public void addUser(String id, String pass, Auth auth) {
		User user = new User(id, pass, auth);
		dbAccessInstance.saveNewUser(user);
	}

	public List<User> getUsers() {
		List<User> userList = new ArrayList<User>();
		HashMap<String, User> usersMap = dbAccessInstance.readUserMap();
		Iterator<String> it = usersMap.keySet().iterator();
		while (it.hasNext()) {
			userList.add(usersMap.get(it.next()));
		}

		return userList;
	}

	public void addLibraryMember(String memberId, String fname, String lname, String tel, String street, String city,
			String state, String zip) {
		Address ad = new Address(street, city, state, zip);
		LibraryMember lm = new LibraryMember(memberId, fname, lname, tel, ad);
		dbAccessInstance.saveNewMember(lm);
	}

	public List<LibraryMember> getLibraryMembers() {
		List<LibraryMember> libraryMembersList = new ArrayList<LibraryMember>();
		HashMap<String, LibraryMember> libraryMembersMap = dbAccessInstance.readMemberMap();
		Iterator<String> it = libraryMembersMap.keySet().iterator();
		while (it.hasNext()) {
			libraryMembersList.add(libraryMembersMap.get(it.next()));
		}

		return libraryMembersList;
	}

	public void addNewBook(String isbn, String title, int noOfCopies, String firstName, String lastName,
			String telephoneNumber, String bio, String credentials) {
		Author author = new Author(firstName, lastName, telephoneNumber, bio);
		Book b = new Book(isbn, title, author, noOfCopies, credentials);
		dbAccessInstance.saveNewBook(b);
	}

	public boolean addBook(String isbn, int noOfCopies) {
		Book b = dbAccessInstance.readBooksMap().get(isbn);
		if (b == null) {
			return false;
		} else {
			int previousNoOfCopies = b.getNumCopies();
			Book nb = new Book(b.getIsbn(), b.getTitle(), b.getAuthor(), previousNoOfCopies + noOfCopies,
					b.getCredentials());
			dbAccessInstance.saveNewBook(nb);
			return true;
		}
	}

	public boolean isMemberAvailable(int id) {
		return dbAccessInstance.readMemberMap().get(id) != null ? true : false;
	}

	public List<BookCopy> getAvailableBooks() {
		List<BookCopy> availableBookCopyList = new ArrayList<BookCopy>();
		HashMap<String, Book> booksMap = dbAccessInstance.readBooksMap();
		Iterator<String> it = booksMap.keySet().iterator();
		while (it.hasNext()) {
			Book b = booksMap.get(it.next());
			for (BookCopy bc : b.getCopies()) {
				if (bc.isAvailable() == true) {
					availableBookCopyList.add(bc);
				}
			}
		}

		return availableBookCopyList;
	}

	public List<BookCopy> getAllBooks() {
		List<BookCopy> availableBookCopyList = new ArrayList<BookCopy>();
		HashMap<String, Book> booksMap = dbAccessInstance.readBooksMap();
		Iterator<String> it = booksMap.keySet().iterator();
		while (it.hasNext()) {
			Book b = booksMap.get(it.next());
			for (BookCopy bc : b.getCopies()) {
				availableBookCopyList.add(bc);
			}
		}

		return availableBookCopyList;
	}

	public List<Book> getBooks() {
		List<Book> booksList = new ArrayList<Book>();
		HashMap<String, Book> booksMap = dbAccessInstance.readBooksMap();
		Iterator<String> it = booksMap.keySet().iterator();
		while (it.hasNext()) {
			booksList.add(booksMap.get(it.next()));
		}

		return booksList;
	}

//	public List<BookCopy> getMemberCheckedOutBooks(String memberID) {
//
//		LibraryMember lm = dbAccessInstance.readMemberMap().get(memberID);
//		List<BookCopy> bookCopyList = new ArrayList<BookCopy>();
//
//		for (CheckOut c : lm.getCheckOuts()) {
//			if (c.getCheckInStatus() == false) {
//				bookCopyList.add(c.getCheckOutBook());
//			}
//		}
//		return bookCopyList;
//	}
	
	public List<CheckOut> getMemberCheckedOutBooks(String memberID) {

		LibraryMember lm = dbAccessInstance.readMemberMap().get(memberID);
		List<CheckOut> checkOutList = new ArrayList<CheckOut>();

		for (CheckOut c : lm.getCheckOuts()) {
			if (c.getCheckInStatus() == false) {
				checkOutList.add(c);
			}
		}
		return checkOutList;
	}

	public boolean checkOutBook(List<String> bookIDs, String memberID) {

		try {
			// Incorporate the change on Book.
			HashMap<String, Book> booksMap = dbAccessInstance.readBooksMap();
			HashMap<String, LibraryMember> libraryMemberMap = dbAccessInstance.readMemberMap();
			LibraryMember member = libraryMemberMap.get(memberID);
			Set<String> ks = libraryMemberMap.keySet();
			int maxCheckOutID = 0;
			for (String s : ks) {
				LibraryMember mem = libraryMemberMap.get(s);
				for (CheckOut co : mem.getCheckOuts()) {
					if (co.getCheckOutID() > maxCheckOutID) {
						maxCheckOutID = co.getCheckOutID();
					}
				}
			}
			LocalDate dueDate = LocalDate.now().plusDays(30);
			for (String bookID : bookIDs) {
				Book b = booksMap.get(bookID.substring(0, bookID.indexOf("/")));
				for (BookCopy bc : b.getCopies()) {
					if (bc.getBookID().equals(bookID)) {
						b.checkOut(bookID);
						member.addCheckOut(new CheckOut(++maxCheckOutID, dueDate, bc));
						dbAccessInstance.saveNewBook(b);
						dbAccessInstance.saveNewMember(member);
					}
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}

	}

//	public boolean checkInBook(List<String> bookIDs) {
//		try {
//			// Incorporate the change on Book.
//			HashMap<String, Book> booksMap = dbAccessInstance.readBooksMap();
//			HashMap<String, LibraryMember> libraryMemberMap = dbAccessInstance.readMemberMap();
//			Book b;
//			Set<String> ks = libraryMemberMap.keySet();
//			for (String bookID : bookIDs) {
//				for (String s : ks) {
//					LibraryMember lm = libraryMemberMap.get(s);
//					for (CheckOut checkOut : lm.getCheckOuts()) {
//						if (checkOut.getBookCopyID().equals(checkOut.getBookCopyID())) {
//							lm.checkIn(bookID);
//							dbAccessInstance.saveNewMember(lm);
//						}
//					}
//				}
//			}
//
//			for (String bookID : bookIDs) {
//				b = booksMap.get(bookID.substring(0, bookID.indexOf("/")));
//				for (BookCopy bc : b.getCopies()) {
//					if (bc.getBookID().equals(bookID)) {
//						b.checkIn(bookID);
//						dbAccessInstance.saveNewBook(b);
//					}
//				}
//
//			}
//			return true;
//		} catch (Exception ex) {
//			return false;
//		}
//
//	}
	
	public boolean checkInBook(List<Integer> checkOutIDs) {
		try {
			// Incorporate the change on Book.
			HashMap<String, Book> booksMap = dbAccessInstance.readBooksMap();
			HashMap<String, LibraryMember> libraryMemberMap = dbAccessInstance.readMemberMap();
			Book b;
			Set<String> ks = libraryMemberMap.keySet();
			List<String> bookIDs = new ArrayList<String>();
			for (Integer checkOutID : checkOutIDs) {
				for (String s : ks) {
					LibraryMember lm = libraryMemberMap.get(s);
					for (CheckOut checkOut : lm.getCheckOuts()) {
						if (checkOut.getCheckOutID() == checkOutID) {
							lm.checkIn(checkOutID);
							bookIDs.add(checkOut.getBookCopyID());
							dbAccessInstance.saveNewMember(lm);
						}
					}
				}
			}
            
			
			for (String bookID : bookIDs) {
				b = booksMap.get(bookID.substring(0, bookID.indexOf("/")));
				for (BookCopy bc : b.getCopies()) {
					if (bc.getBookID().equals(bookID)) {
						b.checkIn(bookID);
						dbAccessInstance.saveNewBook(b);
					}
				}

			}
			return true;
		} catch (Exception ex) {
			return false;
		}

	}
	
	public void resetEveryThing() {
		dbAccessInstance.resetEveryThing();
	}
}
