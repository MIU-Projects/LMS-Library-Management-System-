package business;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class ClassesFactory {
	private static ControllerInterface controller = new SystemController();
	private static DataAccess dataAccess = new DataAccessFacade();
	
	public static ControllerInterface of() {
		return controller;
	}
	
	public static DataAccess dataAccess() {
		return dataAccess;
	}
}