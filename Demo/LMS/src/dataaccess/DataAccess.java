package dataaccess;

import java.util.HashMap;
import java.util.List;

import business.Book;
import business.CheckOut;
import business.LibraryMember;
import dataaccess.DataAccessFacade.StorageType;

public interface DataAccess {
	public HashMap<String, User> readUserMap();

	public void saveNewUser(User user);

	public HashMap<String, Book> readBooksMap();

	public HashMap<String, LibraryMember> readMemberMap();

	public void saveNewMember(LibraryMember member);

	public void saveNewBook(Book book);

	public void resetEveryThing();
}
