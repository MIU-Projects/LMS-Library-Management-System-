package dataaccess;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import business.Book;
import business.BookCopy;
import business.CheckOut;
import business.LibraryMember;
import dataaccess.DataAccessFacade.StorageType;

public class DataAccessFacade implements DataAccess {

	enum StorageType {
		BOOKS, MEMBERS, USERS;
	}

	public DataAccessFacade() {
	};

	public static DataAccessFacade getInstance() {
		return new DataAccessFacade();
	}

	public static final String OUTPUT_DIR = System.getProperty("user.dir") + "\\src\\dataaccess\\storage";
	public static final String DATE_PATTERN = "MM/dd/yyyy";

	@SuppressWarnings("unchecked")
	public HashMap<String, Book> readBooksMap() {

		return ((HashMap<String, Book>) readFromStorage(StorageType.BOOKS) == null) ? new HashMap<String, Book>()
				: (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
	}

	public void saveNewBook(Book book) {
		HashMap<String, Book> books;
		if (!(book == null)) {
			if (readBooksMap() == null) {
				books = new HashMap<String, Book>();
			} else {
				books = readBooksMap();
			}
			String bookISBN = book.getIsbn();
			books.put(bookISBN, book);
			saveToStorage(StorageType.BOOKS, books);
		}
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, User> readUserMap() {
		return (HashMap<String, User>) readFromStorage(StorageType.USERS);
	}

	public void saveNewUser(User user) {
		if (!(user == null)) {
			HashMap<String, User> users = readUserMap();
			String userId = user.getId();
			users.put(userId, user);
			saveToStorage(StorageType.USERS, users);
		}
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, LibraryMember> readMemberMap() {
		return (HashMap<String, LibraryMember>) readFromStorage(StorageType.MEMBERS);
	}

	public void saveNewMember(LibraryMember member) {
		HashMap<String, LibraryMember> libraryMembers;
		if (!(member == null)) {
			if (readMemberMap() == null) {
				libraryMembers = new HashMap<String, LibraryMember>();
			} else {
				libraryMembers = readMemberMap();
			}
			String memberId = member.getMemberId();
			libraryMembers.put(memberId, member);
			saveToStorage(StorageType.MEMBERS, libraryMembers);
		}
	}

	static void loadBookMap(List<Book> bookList) {
		HashMap<String, Book> books = new HashMap<String, Book>();
		bookList.forEach(book -> books.put(book.getIsbn(), book));
		saveToStorage(StorageType.BOOKS, books);
	}

	static void loadUserMap(List<User> userList) {
		HashMap<String, User> users = new HashMap<String, User>();
		userList.forEach(user -> users.put(user.getId(), user));
		saveToStorage(StorageType.USERS, users);
	}

	static void loadMemberMap(List<LibraryMember> memberList) {
		HashMap<String, LibraryMember> members = new HashMap<String, LibraryMember>();
		memberList.forEach(member -> members.put(member.getMemberId(), member));
		saveToStorage(StorageType.MEMBERS, members);
	}

	public void resetEveryThing() {

		HashMap<String, User> emptyUserMap = new HashMap<String, User>();
		HashMap<String, Book> emptyBookMap = new HashMap<String, Book>();
		HashMap<String, LibraryMember> emptyLibraryMemberMap = new HashMap<String, LibraryMember>();

		List<User> initialAdmin = new ArrayList<User>();
		initialAdmin.add(new User("super", "super", Auth.BOTH));
		loadUserMap(initialAdmin);
		saveToStorage(StorageType.USERS, emptyUserMap);
		saveToStorage(StorageType.BOOKS, emptyBookMap);
		saveToStorage(StorageType.MEMBERS, emptyLibraryMemberMap);
	}

	static void saveToStorage(StorageType type, Object ob) {
		ObjectOutputStream out = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(ob);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
				}
			}
		}
	}

	static Object readFromStorage(StorageType type) {
		ObjectInputStream in = null;
		Object retVal = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			in = new ObjectInputStream(Files.newInputStream(path));
			retVal = in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
		return retVal;
	}

	final static class Pair<S, T> implements Serializable {

		S first;
		T second;

		Pair(S s, T t) {
			first = s;
			second = t;
		}

		@Override
		public boolean equals(Object ob) {
			if (ob == null)
				return false;
			if (this == ob)
				return true;
			if (ob.getClass() != getClass())
				return false;
			@SuppressWarnings("unchecked")
			Pair<S, T> p = (Pair<S, T>) ob;
			return p.first.equals(first) && p.second.equals(second);
		}

		@Override
		public int hashCode() {
			return first.hashCode() + 5 * second.hashCode();
		}

		@Override
		public String toString() {
			return "(" + first.toString() + ", " + second.toString() + ")";
		}

		private static final long serialVersionUID = 5399827794066637059L;
	}

}
