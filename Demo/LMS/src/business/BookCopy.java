package business;

import java.io.Serializable;

import javafx.scene.control.CheckBox;

/**
 * Immutable class
 */
final public class BookCopy implements Serializable {
	
	private static final long serialVersionUID = -63976228084869815L;
	private String bookID;
	private Book book;
	private int copyNum;
	private boolean isAvailable;
	
	BookCopy(Book book, int copyNum) {
		this.book = book;
		this.copyNum = copyNum;
		this.bookID = book.getIsbn() + "/" + copyNum;
		this.isAvailable = true;
	}
	
	BookCopy(Book book, int copyNum , boolean isAvailable) {
		this.book = book;
		this.copyNum = copyNum;
		this.isAvailable = isAvailable;
		this.bookID = book.getIsbn() + "-" + copyNum;
	}
	
	
	public boolean isAvailable() {
		return isAvailable;
	}
    
	public void checkOut() {
		this.isAvailable = false;
	}
	
	public void checkIn() {
		this.isAvailable = true;
	}
	public int getCopyNum() {
		return copyNum;
	}
	
	public String getBookID() {
		return bookID;
	}
	
	public Book getBook() {
		return book;
	}
	
	public void changeAvailability() {
		isAvailable = !isAvailable;
	}
	
	@Override
	public boolean equals(Object ob) {
		if(ob == null) return false;
		if(!(ob instanceof BookCopy)) return false;
		BookCopy copy = (BookCopy)ob;
		return copy.book.getIsbn().equals(book.getIsbn()) && copy.copyNum == copyNum;
	}
	
	@Override
	public String toString() {
		return "Title : " + book.getTitle() + " ISBN : " + book.getIsbn() + " Copy Number: " + copyNum;
	}
	
}
