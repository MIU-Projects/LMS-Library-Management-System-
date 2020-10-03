package ui;

import javafx.scene.control.CheckBox;

public class FactoryClass {

	public String ID = "";
	public int checkOutID;
	public String title = "";
	public CheckBox isCheckedOut;

	public FactoryClass(String ID, String Title, boolean isCheckedOut) {
		// TODO Auto-generated constructor stub

		this.ID = ID;
		this.title = Title;
		this.isCheckedOut = new CheckBox();
		//this.checkOutID = checkOutID;
	}
	
	public FactoryClass(String ID, String Title, boolean isCheckedOut, int checkOutID) {
		// TODO Auto-generated constructor stub

		this.ID = ID;
		this.title = Title;
		this.isCheckedOut = new CheckBox();
		this.checkOutID = checkOutID;
	}

	public String getID() { 
		return ID;
	}

	public String getTitle() {
		return title;
	}

	public CheckBox getIsCheckedOut() {
		return isCheckedOut;
	}

	public int getCheckOutID() {
		return checkOutID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public void setTitle(String title) {
		title = title;
	}

	public void setIsCheckedOut(CheckBox isCheckedOut) {
		this.isCheckedOut = isCheckedOut;
	}

	public void setCheckOutID(int checkOutID) {
		this.checkOutID = checkOutID;
	}

}
