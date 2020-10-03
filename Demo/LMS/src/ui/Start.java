package ui;

import java.util.Collections;
import java.util.List;

import business.ControllerInterface;
import business.SystemController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Start extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	private static Stage primStage = null;

	static String themeValue = "library.css";

	public static Stage primStage() {
		return primStage;
	}

	public static class Colors {
		static Color green = Color.web("#034220");
		static Color red = Color.FIREBRICK;
	}

	private static Stage[] allWindows = { LoginWindow.INSTANCE, AllMembersWindow.INSTANCE, AllBooksWindow.INSTANCE,
			BothWindow.INSTANCE };

	public static void hideAllWindows() {
		primStage.hide();
		for (Stage st : allWindows) {
			st.hide();
		}
	}

	@Override
	public void start(Stage primaryStage) {
		primStage = primaryStage;

		primStage.setResizable(false);
		primStage.setTitle("Main Page");

		VBox topContainer = new VBox();
		topContainer.setId("top-container");
		MenuBar mainMenu = new MenuBar();
		VBox imageHolder = new VBox();
		Image image = new Image("ui/library.jfif", 400, 300, false, false);

		// simply displays in ImageView the image as is
		ImageView iv = new ImageView();
		iv.setImage(image);
		imageHolder.getChildren().add(iv);
		imageHolder.setAlignment(Pos.CENTER);
		HBox splashBox = new HBox();
		Label splashLabel = new Label("The Library System");
		splashLabel.setFont(Font.font("Trajan Pro", FontWeight.BOLD, 30));
		splashBox.getChildren().add(splashLabel);
		splashBox.setAlignment(Pos.CENTER);

		// Login button
		final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
		final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;";

		Button buttonLogIn = new Button("Login");
		buttonLogIn.setPrefWidth(100);
		buttonLogIn.setStyle(IDLE_BUTTON_STYLE);
		buttonLogIn.setOnMouseEntered(e -> buttonLogIn.setStyle(HOVERED_BUTTON_STYLE));
		buttonLogIn.setOnMouseExited(e -> buttonLogIn.setStyle(IDLE_BUTTON_STYLE));

		Menu optionsMenu = new Menu("Options");

		buttonLogIn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				hideAllWindows();
				if (!LoginWindow.INSTANCE.isInitialized()) {
					LoginWindow.INSTANCE.init();
				}
				LoginWindow.INSTANCE.clear();
				LoginWindow.INSTANCE.show();
			}
		});

		// end of Login button

		// Horizontal box for menu LogInButton
		HBox hbox = new HBox(mainMenu, buttonLogIn);
		HBox.setHgrow(mainMenu, Priority.ALWAYS);
		HBox.setHgrow(buttonLogIn, Priority.NEVER);

		BorderPane layout = new BorderPane();
		layout.setTop(hbox);

		topContainer.getChildren().add(layout);
		topContainer.getChildren().add(splashBox);
		topContainer.getChildren().add(imageHolder);

		// CREATE SCENE
		Scene scene = new Scene(topContainer, 450, 500);

		// CREATE AND ADD MENU ITEMS
		MenuItem theme = new MenuItem("Change Theme");
		theme.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (scene.getStylesheets().get(0).compareTo(
						"file:/C:/Users/yetad/eclipse-workspace/finalProjectForPresentation/bin/ui/library.css") == 0) {
					scene.getStylesheets().add(getClass().getResource("library2.css").toExternalForm());
					scene.getStylesheets().set(0,
							"file:/C:/Users/yetad/eclipse-workspace/FinalProject/bin/ui/library2.css");
					themeValue = "library2.css";
				}

				else if (scene.getStylesheets().get(0).compareTo(
						"file:/C:/Users/yetad/eclipse-workspace/finalProjectForPresentation/bin/ui/library2.css") == 0) {
					scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
					scene.getStylesheets().set(0,
							"file:/C:/Users/yetad/eclipse-workspace/FinalProject/bin/ui/library.css");
					themeValue = "library.css";
				}

			}
		});

		// ADD MENU ITEMS TO OPTION MENU
		optionsMenu.getItems().addAll(theme);

		// ADD OPTION MENU TO MAIN MENU
		mainMenu.getMenus().addAll(optionsMenu);

		primaryStage.setScene(scene);
		// primaryStage.setResizable(false);
		scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
		primaryStage.show();
	}

}
