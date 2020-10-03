package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

final public class LibraryMember extends Person implements Serializable {
	private String memberId;
	private Address address;
	private List<CheckOut> checkOuts;
	
	public LibraryMember(String memberId, String fname, String lname, String tel,Address add) {
		super(fname,lname, tel);
		this.memberId = memberId;		
		this.address = add;
		checkOuts = new ArrayList<CheckOut>();
	}
	
	
	public String getMemberId() {
		return memberId;
	}

	public Address getAddress() {
		return address;
	}
	public void addCheckOut(CheckOut co) {
		checkOuts.add(co);
	}
	
	public void checkIn(int checkOutID) {
		for(CheckOut co : checkOuts) {
			if(co.getCheckOutID() == checkOutID) {
				co.setReturnedDate();
				co.checkIn();
				co.setFineAmount();
			}
		}
	}
	
	public List<CheckOut> getCheckOuts() {
		return checkOuts;
	}
	
	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + 
				", " + getTelephone() + " " + getAddress();
	}

	private static final long serialVersionUID = -2226197306790714013L;
}
