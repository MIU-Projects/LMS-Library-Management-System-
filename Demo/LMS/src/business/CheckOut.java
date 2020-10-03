package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.time.temporal.ChronoUnit;

/**
 *
 */
final public class CheckOut implements Serializable {

	private static final long serialVersionUID = 6110690276685962829L;
	private int checkOutID;
	private BookCopy bookCopy;
	private LocalDate dueDate;
	private LocalDate returnedDate;
	private double fineAmount;
	private boolean isCheckedIn;

	CheckOut(int checkOutID, LocalDate dueDate, BookCopy bookCopy) {
		this.dueDate = dueDate;
		this.bookCopy = bookCopy;
		this.checkOutID = checkOutID;
		this.isCheckedIn = false;
	}

	void setReturnedDate() {
		this.returnedDate = LocalDate.now();
	}

	public LocalDate getReturnedDate() {
		return returnedDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public double getFineAmount() {
		return fineAmount;
	}

	public boolean getCheckInStatus() {
		return isCheckedIn;
	}

	void setFineAmount() {
		long lateDayInterval = ChronoUnit.DAYS.between(dueDate, returnedDate);
		if (lateDayInterval <= 0) {
			this.fineAmount = 0;
		} else {
			this.fineAmount = lateDayInterval * 2;
		}

	}

	void checkIn() {
		bookCopy.checkIn();
		this.isCheckedIn = true;
	}

	public BookCopy getCheckOutBook() {
		return bookCopy;
	}

	public int getCheckOutID() {
		return checkOutID;
	}

	public String getBookCopyID() {
		return bookCopy.getBookID();
	}
	
	public String toString() {
		return "BookID :" + bookCopy.getBookID() + " IsCheckedIn:" + isCheckedIn + " due Date :" + dueDate + " fineAmount :" + fineAmount;
	}

}
