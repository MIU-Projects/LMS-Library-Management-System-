package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 *
 */
final public class Book implements Serializable {
	
	private static final long serialVersionUID = 6110690276685962829L;
	private List<BookCopy> copies;
	private Author author;
	private String isbn;
	private String title;
	private String credentials;
	public Book(String isbn, String title, Author author , int noOfCopies , String credentials) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.credentials = credentials;
		copies = new ArrayList<BookCopy>();
		for(int i = 1 ; i <=noOfCopies ; i++) {
			copies.add(new BookCopy(this , i));
		}
	}

	public String getIsbn() {
		return isbn;
	}
	
	public String getCredentials() {
		return credentials;
	}
	
	public String getTitle() {
		return title;
	}
	
	public List<Integer> getCopyNums() {
		List<Integer> retVal = new ArrayList<>();
		for(BookCopy c : copies) {
			retVal.add(c.getCopyNum());
		}
		return retVal;
		
	}
	
	void checkOut(String bookID) {
		for(BookCopy b: copies) {
			if(b.getBookID().equals(bookID)) {
				b.checkOut();
				return;
			}
		}
	}
	
	void checkIn(String bookID) {
		for(BookCopy b: copies) {
			if(b.getBookID().equals(bookID)) {
				b.checkIn();
				return;
			}
		}
	}
	
	@Override
	public boolean equals(Object ob) {
		if(ob == null) return false;
		if(ob.getClass() != getClass()) return false;
		Book b = (Book)ob;
		return b.isbn.equals(isbn);
	}

	public boolean isAvailable() {
		if(copies == null) {
			return false;
		}
		for(BookCopy bc : copies) {
			if(bc.isAvailable() == true) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Title:" + title + " ISBN: " + isbn + " No Of Copies: " + getNumCopies() + ", Available Copies: " + getAvailableCopies();
	}
	
	public int getNumCopies() {
		return copies.size();
	}
	
	public List<BookCopy> getCopies() {
		return copies;
	}
	
	public Author getAuthor() {
		return author;
	}
	
	public BookCopy getCopy(int copyNum) {
		for(BookCopy c : copies) {
			if(copyNum == c.getCopyNum()) {
				return c;
			}
		}
		return null;
	}
	
	public int getAvailableCopies() {	
		int i = 0;
		for(BookCopy bc : copies) {
		   if(bc.isAvailable()) {
			   i++;
		   }
		}
		 return i;
	}
}
