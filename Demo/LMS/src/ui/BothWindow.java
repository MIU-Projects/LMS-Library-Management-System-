package ui;

import java.nio.file.ClosedDirectoryStreamException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import business.BookCopy;
import business.CheckOut;
import business.ClassesFactory;
import business.ControllerInterface;
import business.SystemController;
import business.Validator;
import dataaccess.Auth;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BothWindow extends Stage implements LibWindow {
	public static final BothWindow INSTANCE = new BothWindow();

	private Text messageBar = new Text();

	private boolean isInitialized = false;

	public boolean isInitialized() {
		return isInitialized;
	}

	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	/* This class is a singleton */
	private BothWindow() {

	}

	public void init() {

		Tab tab1 = new Tab("CREATE AN EMPLOYEE ACCOUNT");
		Tab tab2 = new Tab("ADD A NEW BOOK");
		Tab tab3 = new Tab("ADD A COPY OF A BOOK");
		Tab tab4 = new Tab("ADD NEW MAMBER");
		Tab tab5 = new Tab("CHECKOUT A BOOK");
		Tab tab6 = new Tab("CHECKIN A BOOK");
		Tab tab7 = new Tab("LIBRARY RECORD");

		tab1.setClosable(false);
		tab2.setClosable(false);
		tab3.setClosable(false);
		tab4.setClosable(false);
		tab5.setClosable(false);
		tab6.setClosable(false);
		tab7.setClosable(false);

		TabPane theTab = new TabPane();
		theTab.setMaxHeight(900);

		if (SystemController.currentAuth.equals(Auth.BOTH)) {
			theTab.getTabs().add(tab1);
			theTab.getTabs().add(tab2);
			theTab.getTabs().add(tab3);
			theTab.getTabs().add(tab4);
			theTab.getTabs().add(tab5);
			theTab.getTabs().add(tab6);
			theTab.getTabs().add(tab7);
		}

		if (SystemController.currentAuth.equals(Auth.LIBRARIAN)) {
			theTab.getTabs().add(tab5);
			theTab.getTabs().add(tab6);
		}

		if (SystemController.currentAuth.equals(Auth.ADMIN)) {

			theTab.getTabs().add(tab1);
			theTab.getTabs().add(tab2);
			theTab.getTabs().add(tab3);
			theTab.getTabs().add(tab4);
			theTab.getTabs().add(tab7);

		}

		GridPane grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(5, 25, 25, 5));

		grid.add(theTab, 0, 0, 2, 3);

		///////////////////// for CREATE AN EMPLOYEE ACCOUNT ///////////////////

		GridPane gridForNewAccount = new GridPane();
		gridForNewAccount.setAlignment(Pos.CENTER);
		gridForNewAccount.setVgap(10);
		gridForNewAccount.setHgap(5);

		Label labelUserName = new Label("User Name");
		Label labelPassWord1 = new Label("PassWord");
		Label labelPassWord2 = new Label("Conform PassWord");
		Label labelAuthorizationLevel = new Label("Authorization Level");
		labelAuthorizationLevel.setPrefWidth(170);

		TextField textFieldUserName = new TextField();
		PasswordField passWordFieldPassWord1 = new PasswordField();
		PasswordField passWordFieldPassWord2 = new PasswordField();
		//TextField textFieldAuthorizationLevel = new TextField();
		ComboBox<Auth> textFieldAuthorizationLevel = new ComboBox<Auth>();
		textFieldAuthorizationLevel.getItems().addAll(Auth.ADMIN, Auth.BOTH, Auth.LIBRARIAN);
		
		
		textFieldAuthorizationLevel.setPrefWidth(300);

		Button create = new Button("Create Account");
		create.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				if (passWordFieldPassWord1.getText().compareTo(passWordFieldPassWord2.getText()) != 0) {

					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("ALERT!!");
					alert.setHeaderText("The Two Passwords Must Match!");
					alert.showAndWait();
				}

				else {

					if (Validator.emptyString(textFieldUserName.getText()).error
							&& Validator.emptyString(passWordFieldPassWord1.getText()).error
							&& Validator.emptyString(passWordFieldPassWord2.getText()).error) {

						ClassesFactory.of().addUser(textFieldUserName.getText(), passWordFieldPassWord1.getText(),
								textFieldAuthorizationLevel.getValue());

						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("INFORMATION");
						alert.setHeaderText("User Sucessfully Created");
						alert.showAndWait();

						textFieldUserName.clear();
						passWordFieldPassWord1.clear();
						passWordFieldPassWord2.clear();
						textFieldAuthorizationLevel.getSelectionModel().clearSelection();

					} else {

						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("ALERT!!");
						if ((Validator.emptyString(textFieldUserName.getText()).message
								+ Validator.emptyString(passWordFieldPassWord1.getText()).message
								+ Validator.emptyString(passWordFieldPassWord2.getText()).message).isEmpty()) {
							alert.setHeaderText("Empty Fields Not Allowed");
						} else {
							alert.setHeaderText(Validator.emptyString(textFieldUserName.getText()).message
									+ Validator.emptyString(passWordFieldPassWord1.getText()).message
									+ Validator.emptyString(passWordFieldPassWord2.getText()).message);
						}
						alert.showAndWait();
					}

				}
			}
		});

		gridForNewAccount.add(labelUserName, 0, 5);
		gridForNewAccount.add(labelPassWord1, 0, 6);
		gridForNewAccount.add(labelPassWord2, 0, 7);
		gridForNewAccount.add(labelAuthorizationLevel, 0, 8);

		gridForNewAccount.add(textFieldUserName, 1, 5);
		gridForNewAccount.add(passWordFieldPassWord1, 1, 6);
		gridForNewAccount.add(passWordFieldPassWord2, 1, 7);
		gridForNewAccount.add(textFieldAuthorizationLevel, 1, 8);

		gridForNewAccount.add(create, 1, 10);

		tab1.setContent(gridForNewAccount);

		////////////////// end of CREATE AN EMPLOYEE ACCOUNT /////////////////////////

		///////////////////////////// for ADD NEW BOOK /////////////////////////////

		GridPane gridForAddNewBook = new GridPane();
		gridForAddNewBook.setAlignment(Pos.CENTER);
		gridForAddNewBook.setVgap(10);
		gridForAddNewBook.setHgap(5);

		Label labelTitle = new Label("Book Title");
		Label labelISBN = new Label("ISBN");
		Label labelNoOfCopies = new Label("Number Of Copies");

		Label labelAuthorFirstName = new Label("Author's First Name");
		Label labelAuthorLastName = new Label("Author's Last Name");
		Label labelAuthorPhoneNumber = new Label("Author's Phone Number");
		Label labelAuthorCredentials = new Label("Author's Credentials");
		Label labelAuthorBio = new Label("Author's Biography");
		labelAuthorBio.setPrefWidth(170);

		TextField textFieldTitle = new TextField();
		TextField textFieldISBN = new TextField();
		TextField textFieldNoOfCopies = new TextField();

		TextField textFieldAuthorFirstName = new TextField();
		TextField textFieldAuthorLastName = new TextField();
		TextField textFieldAuthorPhoneNumber = new TextField();
		TextField textFieldAuthorCredentials = new TextField();
		TextArea textAreaAuthorBio = new TextArea();
		textAreaAuthorBio.setMaxWidth(300);
		textAreaAuthorBio.setWrapText(true);

		Button addBook = new Button("Add Book");
		addBook.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				if (Validator.emptyBookTitle(textFieldTitle.getText()).error

						&& Validator.isbnValidator(textFieldISBN.getText()).error
						&& Validator.numberValidator(textFieldNoOfCopies.getText()).error
						&& Validator.firstNameValidator(textFieldAuthorFirstName.getText()).error
						&& Validator.lastNameValidator(textFieldAuthorLastName.getText()).error
						&& Validator.phoneNumberValidator(textFieldAuthorPhoneNumber.getText()).error

						&& Validator.emptyAuthorsCredentials(textFieldAuthorCredentials.getText()).error
						&& Validator.emptyAuthorsBio(textAreaAuthorBio.getText()).error) {

					ClassesFactory.of().addNewBook(textFieldISBN.getText(), textFieldTitle.getText(),
							Integer.parseInt(textFieldNoOfCopies.getText()), textFieldAuthorFirstName.getText(),
							textFieldAuthorLastName.getText(), textFieldAuthorPhoneNumber.getText(),
							textAreaAuthorBio.getText(), textFieldAuthorCredentials.getText());

					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("INFORMATION");
					alert.setHeaderText("A New Book Sucessfully Added");
					alert.showAndWait();

					textFieldTitle.clear();
					textFieldISBN.clear();
					textFieldNoOfCopies.clear();
					textFieldAuthorFirstName.clear();
					textFieldAuthorLastName.clear();
					textFieldAuthorPhoneNumber.clear();
					textFieldAuthorCredentials.clear();
					textAreaAuthorBio.clear();
				}

				else {

					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("ALERT!!");

					alert.setHeaderText(Validator.emptyBookTitle(textFieldTitle.getText()).message
							+ Validator.isbnValidator(textFieldISBN.getText()).message
							+ Validator.numberValidator(textFieldNoOfCopies.getText()).message
							+ Validator.firstNameValidator(textFieldAuthorFirstName.getText()).message
							+ Validator.lastNameValidator(textFieldAuthorLastName.getText()).message
							+ Validator.phoneNumberValidator(textFieldAuthorPhoneNumber.getText()).message
							+ Validator.emptyAuthorsCredentials(textFieldAuthorCredentials.getText()).message
							+ Validator.emptyAuthorsBio(textAreaAuthorBio.getText()).message

					);

					alert.showAndWait();
				}

			}
		});

		gridForAddNewBook.add(labelTitle, 0, 3);
		gridForAddNewBook.add(labelISBN, 0, 4);
		gridForAddNewBook.add(labelNoOfCopies, 0, 5);

		gridForAddNewBook.add(labelAuthorFirstName, 0, 6);
		gridForAddNewBook.add(labelAuthorLastName, 0, 7);
		gridForAddNewBook.add(labelAuthorPhoneNumber, 0, 8);
		gridForAddNewBook.add(labelAuthorCredentials, 0, 9);
		gridForAddNewBook.add(labelAuthorBio, 0, 10);

		gridForAddNewBook.add(textFieldTitle, 1, 3, 1, 1);
		gridForAddNewBook.add(textFieldISBN, 1, 4);
		gridForAddNewBook.add(textFieldNoOfCopies, 1, 5);

		gridForAddNewBook.add(textFieldAuthorFirstName, 1, 6);
		gridForAddNewBook.add(textFieldAuthorLastName, 1, 7);
		gridForAddNewBook.add(textFieldAuthorPhoneNumber, 1, 8);
		gridForAddNewBook.add(textFieldAuthorCredentials, 1, 9);
		gridForAddNewBook.add(textAreaAuthorBio, 1, 10, 1, 9);

		gridForAddNewBook.add(addBook, 1, 21);

		tab2.setContent(gridForAddNewBook);

		///////////////////////////// end of ADD NEW BOOK /////////////////////////////

		///////////////////////////// for ADD NEW COPY /////////////////////////////

		GridPane gridForAddNewCopy = new GridPane();
		gridForAddNewCopy.setAlignment(Pos.CENTER);
		gridForAddNewCopy.setVgap(10);
		gridForAddNewCopy.setHgap(5);

		Label labelISBN2 = new Label("ISBN");
		Label labelNoOfCopies2 = new Label("Number Of Copies");
		labelNoOfCopies2.setPrefWidth(170);

		TextField textFieldISBN2 = new TextField();
		TextField textFieldNoOfCopies2 = new TextField();
		textFieldNoOfCopies2.setPrefWidth(300);

		Button addNewCopy = new Button("Add New Copy");
		addNewCopy.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				boolean result = ClassesFactory.of().addBook(textFieldISBN2.getText(),
						Integer.parseInt(textFieldNoOfCopies2.getText()));

				if (result == false) {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("ALERT!!");
					alert.setHeaderText(
							"This Book (ISBN) Doesn't exist in the system. You Need To Enter It As a New Book");
					alert.showAndWait();
				}

				else {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("INFORMATION!!");
					alert.setHeaderText("A New Book Copy Sucessfully Added");
					alert.showAndWait();

					textFieldISBN2.clear();
					textFieldNoOfCopies2.clear();
				}

			}
		});

		gridForAddNewCopy.add(labelISBN2, 0, 3);
		gridForAddNewCopy.add(labelNoOfCopies2, 0, 4);

		gridForAddNewCopy.add(textFieldISBN2, 1, 3);
		gridForAddNewCopy.add(textFieldNoOfCopies2, 1, 4);

		gridForAddNewCopy.add(addNewCopy, 1, 6);

		tab3.setContent(gridForAddNewCopy);

		///////////////////////////// end of ADD NEW COPY /////////////////////////////

		///////////////////////////// for ADD NEW MAMBER /////////////////////////////

		GridPane gridForAddNewMember = new GridPane();
		gridForAddNewMember.setAlignment(Pos.CENTER);
		gridForAddNewMember.setVgap(10);
		gridForAddNewMember.setHgap(5);

		Label labelMemberID = new Label("Member ID");
		Label labelMemberFirstName = new Label("First Name");
		Label labelLastName = new Label("Last Name");
		Label labelMemberPhoneNumber = new Label("Phone Number");
		labelMemberPhoneNumber.setPrefWidth(170);

		Label labelMemberStreet = new Label("           Street");
		Label labelMemberCity = new Label("           City");
		Label labelMemberZip = new Label("           Zip");
		Label labelMemberState = new Label("           State");

		TextField textFieldMemberID = new TextField();
		TextField textFieldMemberFirstName = new TextField();
		TextField textFieldMemberLastName = new TextField();
		TextField textFieldMemberPhoneNumber = new TextField();

		TextField textFieldMemberStreet = new TextField();
		TextField textFieldMemberCity = new TextField();
		TextField textFieldMemberZip = new TextField();
		TextField textFieldMemberState = new TextField();
		textFieldMemberState.setPrefWidth(300);

		Button addMember = new Button("Add Member");
		addMember.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				ClassesFactory.of().addLibraryMember(textFieldMemberID.getText(), textFieldMemberFirstName.getText(),
						textFieldMemberLastName.getText(), textFieldMemberPhoneNumber.getText(),
						textFieldMemberStreet.getText(), textFieldMemberCity.getText(), textFieldMemberState.getText(),
						textFieldMemberZip.getText());

				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("INFORMATION!!");
				alert.setHeaderText("A New Member Sucessfully Added");
				alert.showAndWait();

				textFieldMemberID.clear();
				textFieldMemberFirstName.clear();
				textFieldMemberLastName.clear();
				textFieldMemberPhoneNumber.clear();
				textFieldMemberStreet.clear();
				textFieldMemberCity.clear();
				textFieldMemberZip.clear();
				textFieldMemberState.clear();

			}
		});

		gridForAddNewMember.add(labelMemberID, 0, 3);
		gridForAddNewMember.add(labelMemberFirstName, 0, 4);
		gridForAddNewMember.add(labelLastName, 0, 5);
		gridForAddNewMember.add(labelMemberPhoneNumber, 0, 6);

		gridForAddNewMember.add(labelMemberStreet, 0, 9);
		gridForAddNewMember.add(labelMemberCity, 0, 10);
		gridForAddNewMember.add(labelMemberZip, 0, 11);
		gridForAddNewMember.add(labelMemberState, 0, 12);

		gridForAddNewMember.add(textFieldMemberID, 1, 3);
		gridForAddNewMember.add(textFieldMemberFirstName, 1, 4);
		gridForAddNewMember.add(textFieldMemberLastName, 1, 5);
		gridForAddNewMember.add(textFieldMemberPhoneNumber, 1, 6);

		gridForAddNewMember.add(textFieldMemberStreet, 1, 9);
		gridForAddNewMember.add(textFieldMemberCity, 1, 10);
		gridForAddNewMember.add(textFieldMemberZip, 1, 11);
		gridForAddNewMember.add(textFieldMemberState, 1, 12);

		gridForAddNewMember.add(addMember, 1, 14);

		tab4.setContent(gridForAddNewMember);

		////////////////////////// end of ADD NEW MEMBER ////////////////////////////

		///////////////////////////// for CHECK OUT /////////////////////////////

		GridPane gridForCheckOut = new GridPane();
		gridForCheckOut.setAlignment(Pos.CENTER);
		gridForCheckOut.setVgap(10);
		gridForCheckOut.setHgap(5);

		TableView<FactoryClass> table = new TableView<FactoryClass>();
		table.setEditable(true);

		List<BookCopy> theBookList = ClassesFactory.of().getAvailableBooks();
		List<FactoryClass> incomingList = new ArrayList<FactoryClass>();

		for (BookCopy b : theBookList) {

			incomingList.add(new FactoryClass(b.getBookID(), b.getBook().getTitle(), false));

		}

		ObservableList<FactoryClass> list = FXCollections.observableArrayList(incomingList);

		TableColumn<FactoryClass, String> bookID = new TableColumn<FactoryClass, String>("Book ID");
		bookID.setEditable(false);
		bookID.setPrefWidth(150);
		TableColumn<FactoryClass, String> TitleCol = new TableColumn<FactoryClass, String>("Book Title");
		TitleCol.setEditable(false);
		TitleCol.setPrefWidth(300);
		TableColumn<FactoryClass, String> isCheckedOut = new TableColumn<FactoryClass, String>("Checked Out");
		isCheckedOut.setPrefWidth(100);

		bookID.setCellValueFactory(new PropertyValueFactory<FactoryClass, String>("ID")); // change
		TitleCol.setCellValueFactory(new PropertyValueFactory<FactoryClass, String>("title")); // change
		isCheckedOut.setCellValueFactory(new PropertyValueFactory<FactoryClass, String>("isCheckedOut")); // change

		table.getColumns().add(bookID);
		table.getColumns().add(TitleCol);
		table.getColumns().add(isCheckedOut);

		table.setItems(list);

		Button checkOut = new Button("CHECK OUT");
		Label lebelCheckOutMemberId = new Label("Member ID");
		TextField textFieldCheckOutMemberId = new TextField();
		lebelCheckOutMemberId.setPrefWidth(144);

		checkOut.setOnAction(new EventHandler<ActionEvent>() {
			// System.out.println("the result is");
			@Override
			public void handle(ActionEvent e) {
				List<String> theListToReturn = new ArrayList<String>();

				for (FactoryClass f : table.getItems()) {
					if (f.getIsCheckedOut().isSelected()) {
						theListToReturn.add(f.getID());
					}
				}
				boolean theResult = ClassesFactory.of().checkOutBook(theListToReturn,
						textFieldCheckOutMemberId.getText());
				System.out.println("the result is" + theResult);

				if (theResult == false) {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("ALERT!!");
					alert.setHeaderText("There Is No Record of A Member With This Member ID");
					alert.showAndWait();
				}

				else {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("INFORMATION!!");
					alert.setHeaderText("CheckOut Sucessfull");
					alert.showAndWait();

					List<BookCopy> refreshedBookList = ClassesFactory.of().getAvailableBooks();
					List<FactoryClass> refreshedincomingList = new ArrayList<FactoryClass>();

					for (BookCopy b : refreshedBookList) {

						refreshedincomingList.add(new FactoryClass(b.getBookID(), b.getBook().getTitle(), false));
					}

					ObservableList<FactoryClass> refreshedlist = FXCollections
							.observableArrayList(refreshedincomingList);
					table.setItems(refreshedlist);
				}

			}

		});

		gridForCheckOut.add(lebelCheckOutMemberId, 0, 3);
		gridForCheckOut.add(textFieldCheckOutMemberId, 1, 3);
		gridForCheckOut.add(table, 0, 5, 2, 1);
		gridForCheckOut.add(checkOut, 0, 7);
		tab5.setContent(gridForCheckOut);

		///////////////////////////// end of CHECK OUT /////////////////////////////

		///////////////////////////// for CHECK IN /////////////////////////////

		GridPane gridForCheckIn = new GridPane();
		gridForCheckIn.setAlignment(Pos.CENTER);
		gridForCheckIn.setVgap(10);
		gridForCheckIn.setHgap(5);

		TableView<FactoryClass> table2 = new TableView<FactoryClass>();
		table2.setEditable(true);

		Label lebelCheckInMemberId = new Label("Member ID");
		TextField textFieldCheckInMemberId = new TextField();

		
		TableColumn<FactoryClass, String> checkOutID2 = new TableColumn<FactoryClass, String>("Check Out ID");
		checkOutID2.setEditable(false);
		checkOutID2.setPrefWidth(150);
		
		
		TableColumn<FactoryClass, String> bookID2 = new TableColumn<FactoryClass, String>("Book ID");
		bookID2.setEditable(false);
		bookID2.setPrefWidth(150);
		TableColumn<FactoryClass, String> TitleCol2 = new TableColumn<FactoryClass, String>("Book Title");
		TitleCol2.setEditable(false);
		TitleCol2.setPrefWidth(300);
		TableColumn<FactoryClass, String> isCheckedIn2 = new TableColumn<FactoryClass, String>("Checked In");
		isCheckedIn2.setPrefWidth(100);

		checkOutID2.setCellValueFactory(new PropertyValueFactory<FactoryClass, String>("checkOutID")); // change
		bookID2.setCellValueFactory(new PropertyValueFactory<FactoryClass, String>("ID")); // change
		TitleCol2.setCellValueFactory(new PropertyValueFactory<FactoryClass, String>("title")); // change
		isCheckedIn2.setCellValueFactory(new PropertyValueFactory<FactoryClass, String>("isCheckedOut")); // change

		
		table2.getColumns().add(checkOutID2);
		table2.getColumns().add(bookID2);
		table2.getColumns().add(TitleCol2);
		table2.getColumns().add(isCheckedIn2);

		// Create Button
		Button checkIn = new Button("CHECK IN");

		// Action Listener
		checkIn.setOnAction(new EventHandler<ActionEvent>() {
			List<Integer> theListToReturn2 = new ArrayList<Integer>();

			@Override
			public void handle(ActionEvent e) {

				// Refresh ChedkIn Page
				for (FactoryClass f : table2.getItems()) {
					if (f.getIsCheckedOut().isSelected()) {
						theListToReturn2.add(f.getCheckOutID());
					}
				}

				ClassesFactory.of().checkInBook(theListToReturn2);

				List<CheckOut> theCheckOutList2 = ClassesFactory.of()
						.getMemberCheckedOutBooks(textFieldCheckInMemberId.getText());
				List<FactoryClass> incomingList2 = new ArrayList<FactoryClass>();

				for (CheckOut b : theCheckOutList2) {
					incomingList2.add(new FactoryClass(b.getBookCopyID(), b.getCheckOutBook().getBook().getTitle(),
							false, b.getCheckOutID()));
				}

				ObservableList<FactoryClass> list2 = FXCollections.observableArrayList(incomingList2);

				// Populate The Table
				table2.setItems(list2);

				// Alert message
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("INFORMATION!!");
				alert.setHeaderText("CheckIn Sucessfull");
				alert.showAndWait();

				// Refresh Check Out Page
				List<BookCopy> refreshedBookList = ClassesFactory.of().getAvailableBooks();
				List<FactoryClass> refreshedincomingList = new ArrayList<FactoryClass>();

				for (BookCopy b : refreshedBookList) {

					refreshedincomingList.add(new FactoryClass(b.getBookID(), b.getBook().getTitle(), false));

				}

				ObservableList<FactoryClass> refreshedlist = FXCollections.observableArrayList(refreshedincomingList);
				table.setItems(refreshedlist);

			}
		});

		Button buttonGetMemberCheckouts = new Button("Get Member Checkouts");

		buttonGetMemberCheckouts.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				List<CheckOut> refreshedBookList2 = ClassesFactory.of()
						.getMemberCheckedOutBooks(textFieldCheckInMemberId.getText());
				List<FactoryClass> refreshedincomingList2 = new ArrayList<FactoryClass>();

				for (CheckOut b : refreshedBookList2) {
					refreshedincomingList2.add(new FactoryClass(b.getBookCopyID(), b.getCheckOutBook().getBook().getTitle(),
							false, b.getCheckOutID()));
				}

				ObservableList<FactoryClass> refreshedlist2 = FXCollections.observableArrayList(refreshedincomingList2);
				table2.setItems(refreshedlist2);

			}
		});

		gridForCheckIn.add(lebelCheckInMemberId, 0, 3);
		gridForCheckIn.add(textFieldCheckInMemberId, 1, 3);
		gridForCheckIn.add(buttonGetMemberCheckouts, 0, 5);

		gridForCheckIn.add(table2, 0, 7, 2, 1);
		gridForCheckIn.add(checkIn, 0, 9);
		tab6.setContent(gridForCheckIn);

		///////////////////////////// end of CHECK IN /////////////////////////////

		///////////////////////////// for report /////////////////////////////

		GridPane gridForReport = new GridPane();
		gridForReport.setAlignment(Pos.CENTER);
		gridForReport.setVgap(10);
		gridForReport.setHgap(5);

		Button bookIds = new Button("All Book Ids");
		bookIds.setPrefSize(200, 200);
		bookIds.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (!AllBooksWindow.INSTANCE.isInitialized()) {
					AllBooksWindow.INSTANCE.init();
				}
				ControllerInterface ci = new SystemController();
				List<String> ids = ci.allBookIds();
				Collections.sort(ids);
				StringBuilder sb = new StringBuilder();
				for (String s : ids) {
					sb.append(s + "\n");
				}
				AllBooksWindow.INSTANCE.setData(sb.toString());
				AllBooksWindow.INSTANCE.showAndWait();
			}
		});

		Button memberIds = new Button("All Member Ids");
		memberIds.setPrefSize(200, 200);
		memberIds.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (!AllMembersWindow.INSTANCE.isInitialized()) {
					AllMembersWindow.INSTANCE.init();
				}
				ControllerInterface ci = new SystemController();
				List<String> ids = ci.allMemberIds();
				Collections.sort(ids);
				StringBuilder sb = new StringBuilder();
				for (String s : ids) {
					sb.append(s + "\n");
				}
				AllMembersWindow.INSTANCE.setData(sb.toString());
				AllMembersWindow.INSTANCE.showAndWait();
			}
		});

		Button clearAllRecords = new Button("Delete All System Records");
		clearAllRecords.setPrefSize(200, 200);
		clearAllRecords.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				//ClassesFactory.of().resetEveryThing();
			}
		});

		gridForReport.add(memberIds, 0, 0);
		gridForReport.add(bookIds, 1, 0);
		gridForReport.add(clearAllRecords, 2, 0);

		tab7.setContent(gridForReport);

		///////////////////////////// end report /////////////////////////////

		Button backBtn = new Button("Log Out");
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Start.hideAllWindows();
				Start.primStage().show();
			}
		});

		HBox hBack = new HBox(10);
		hBack.setAlignment(Pos.BOTTOM_CENTER);

		messageBar.setText(SystemController.currentUserName);
		messageBar.setFill(Color.YELLOW);
		messageBar.setFont(Font.font("Verdana", 25));

		hBack.getChildren().add(messageBar);
		hBack.getChildren().add(backBtn);
		grid.add(hBack, 0, 10);

		Scene scene = new Scene(grid, 1000, 650);
		scene.getStylesheets().add(getClass().getResource(Start.themeValue).toExternalForm());
		setScene(scene);
	}
}
