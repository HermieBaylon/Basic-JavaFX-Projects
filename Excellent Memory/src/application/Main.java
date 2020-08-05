/**
 * JavaFX project
 * Excellent Memory
 */
package application;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * This program allows a user to write down random words
 * (randomly produced by someone else or by the user themselves) and tests
 * the memory of the user by remembering the words correctly
 * @author Hermie Baylon
 * @version 5 August 2020
 */
public class Main extends Application {
	private Stage window;
	private Scene input;
	private Scene menu;
	private int difficulty;
	private static ArrayList<String> list = new ArrayList<String>();;
	private int nWord = 1;
	private Button play;
	private int restartDiff = 0;
	private int currentWord = 1;

	@Override
	public void start(Stage primaryStage) {
		window = primaryStage;
		window.setTitle("Excellent Memory");
		switchToMenu();
		window.show();
	}

	/**
	 * Displays window where user can type in random words
	 */
	private void switchToInputScene() {
		Label title = new Label("Please type in words below");
		title.setStyle("-fx-font: 35 arial;");
		title.setPrefSize(600, 100);
		title.setAlignment(Pos.CENTER);
		String message = "";
		if (difficulty == 0) {
			message = "You are done! Let's Play!";
		} else {
			message = "Enter Word Number "+nWord;
		}
		Label remaining = new Label("Words Remaining: "+difficulty);
		Label direction = new Label(message);
		TextField field = new TextField();

		field.setPrefSize(600, 100);
		field.setStyle("-fx-font: 40 arial;");
		direction.setStyle("-fx-font: 20 arial;");
		direction.setPrefSize(500, 0);

		VBox components = new VBox(20);
		HBox options = new HBox(30);
		Button next = new Button("Next Word");
		Button restart = new Button("Restart");
		Button menu = new Button("Menu");
		play = new Button("Play");
		remaining.setPrefSize(150, 50);
		next.setPrefSize(100, 50);
		menu.setPrefSize(100, 50);
		play.setPrefSize(100, 50);
		play.setDisable(true);
		restart.setPrefSize(100, 50);

		options.getChildren().addAll(remaining, next, restart, menu, play);

		next.setOnAction(e -> {
			String word = field.getText();
			list.add(word);
			difficulty--;
			nWord++;
			restartDiff++;
			switchToInputScene();
			if (difficulty == 0) {
				play.setDisable(false);
			}
		});
		menu.setOnAction(e -> {
			difficulty = difficulty + restartDiff;
			nWord = 1;
			list.clear();
			restartDiff = 0;
			switchToMenu();

		});
		restart.setOnAction(e -> {
			restartForMenu();
			switchToInputScene();
		});
		play.setOnAction(e -> {
			switchToPlay();
		});

		if (difficulty == 0) {
			next.setDisable(true);
		}

		components.getChildren().addAll(title, direction, field, options);
		components.setPadding(new Insets(20, 20, 20, 20));

		input = new Scene(components, 1280, 620);
		window.setScene(input);
	}

	/**
	 * Dislays window where user has to remember all the words
	 */
	private void switchToPlay() {

		Label title = new Label("Try to remember the words");
		title.setStyle("-fx-font: 20 arial;");
		title.setPrefSize(600, 100);
		title.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		pane.setTop(title);

		VBox blanks1 = new VBox(20);
		VBox checked1 = new VBox(20);
		VBox solCol1 = new VBox(20);
		HBox columnOne = new HBox(10);

		VBox blanks2 = new VBox(20);
		VBox checked2 = new VBox(20);
		VBox solCol2 = new VBox(20);
		HBox columnTwo = new HBox(10);

		VBox blanks3 = new VBox(20);
		VBox checked3 = new VBox(20);
		VBox solCol3 = new VBox(20);
		HBox columnThree = new HBox(10);

		ArrayList<TextField> textFields = new ArrayList<TextField>();
		ArrayList<Label> labels = new ArrayList<Label>();
		ArrayList<Label> solutions = new ArrayList<Label>();

		if (nWord >= 10) {
			fillColumns(textFields, blanks1, labels, checked1, solutions, solCol1);
		}
		if (nWord >= 10) {
			fillColumns(textFields, blanks2, labels, checked2, solutions, solCol2);
		}
		if (nWord >= 10) {
			fillColumns(textFields, blanks3, labels, checked3, solutions, solCol3);
		}
		

		columnOne.getChildren().addAll(blanks1, checked1, solCol1);
		columnTwo.getChildren().addAll(blanks2, checked2, solCol2);
		columnThree.getChildren().addAll(blanks3, checked3, solCol3);
		HBox entireCenter = new HBox(20);
		entireCenter.getChildren().addAll(columnOne, columnTwo, columnThree);

		Button menus = new Button("Menu");
		Button check = new Button("Check");
		Button solution = new Button("Solution");

		HBox buttons = new HBox(10);
		buttons.getChildren().addAll(menus, check, solution);
		buttons.setAlignment(Pos.CENTER);
		pane.setBottom(buttons);
		pane.setCenter(entireCenter);

		menus.setOnAction(e -> {
			restartForMenu();
			switchToMenu();
		});
		check.setOnAction(e -> {
			switchToCheck(textFields, labels);
		});
		solution.setOnAction(e -> {
			displaySolution(list, solutions);
		});
		menu = new Scene(pane, 1280, 620);
		window.setScene(menu);
	}

	/**
	 * Organizes columns of blank textfields, right or wrong labels, and
	 * the answer key.
	 * @param textArray - textfields where the user types their answers
	 * @param textVBox - the component that keeps textArray together
	 * @param labelArray - labels that tells whether or not the user is correct
	 * @param checkVBox - compoenent that keeps labelArray together
	 * @param solutionArray - labels that stores all the correct answers
	 * @param solutionVBox - component that keeps solutionArray together
	 */
	private void fillColumns(ArrayList<TextField> textArray, 
			VBox textVBox, 
			ArrayList<Label> labelArray,
			VBox checkVBox,
			ArrayList<Label> solutionArray,
			VBox solutionVBox) {
		for (int i = 1; i <= 10; i++) {
			TextField field = new TextField();
			field.setPromptText("Enter word number "+currentWord);
			textArray.add(field);
			textVBox.getChildren().add(field);

			Label label = new Label();
			label.setStyle("-fx-font: 21 arial;");
			labelArray.add(label);
			checkVBox.getChildren().add(label);

			Label answer = new Label("");
			answer.setStyle("-fx-font: 21 arial;");
			solutionArray.add(answer);
			solutionVBox.getChildren().add(answer);
			nWord--;
			currentWord++;
		}
	}

	/**
	 * Displays the entire solution
	 * @param list - contains all the correct answers
	 * @param solutions - the labels that will display the correct answers
	 */
	private void displaySolution(ArrayList<String> list, ArrayList<Label> solutions) {
		int max = list.size();
		for (int i = 0; i < max; i++) {
			solutions.get(i).setText(list.get(i));
		}
	}

	/**
	 * Checks to see if the recent answer given is correct
	 * @param t - textfields that contains the user's answers
	 * @param l - the labels that displays whether the answer is correct or not
	 */
	private void switchToCheck(ArrayList<TextField> t, ArrayList<Label> l) {
		try {
			int max = t.size();
			for (int i = 0; i < max; i++) {
				if (t.get(i).getText().equals(list.get(i))) {
					l.get(i).setText("CORRECT!");
					l.get(i).setTextFill(Color.GREEN);
				} else if (t.get(i).getText().equals("")) {
					l.get(i).setText("");
				} else {
					l.get(i).setText("WRONG!");
					l.get(i).setTextFill(Color.RED);

				}
			}
		} catch (Exception e) {

		}
	}

	/**
	 * restarts relevant datas when going back to the menu
	 */
	private void restartForMenu() {
		difficulty = difficulty + restartDiff;
		nWord = 1;
		list.clear();
		restartDiff = 0;
		currentWord = 1;
	}

	/**
	 * displays menu scene
	 */
	private void switchToMenu() {
		String i = "                           ";
		String j = i + "           ";
		Label title = new Label(j+"HOW MANY WORDS \n"+i+"WOULD YOU LIKE TO REMEMBER?");
		title.setStyle("-fx-font: 35 arial;");
		title.setPrefSize(1000, 100);
		title.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		pane.setTop(title);
		GridPane options = new GridPane();
		options.setPadding(new Insets(10, 10, 10, 10));
		options.setVgap(50);
		options.setHgap(50);

		Button ten = new Button("10");
		Button twenty = new Button("20");
		Button thirty = new Button("30");

		ten.setOnAction(e -> {
			difficulty = 10;
			switchToInputScene();
		});
		twenty.setOnAction(e -> {
			difficulty = 20;
			switchToInputScene();
		});
		thirty.setOnAction(e -> {
			difficulty = 30;
			switchToInputScene();
		});

		GridPane.setConstraints(ten, 0, 0);
		GridPane.setConstraints(twenty, 1, 0);
		GridPane.setConstraints(thirty, 2, 0);

		ten.setStyle("-fx-font: 70 arial;");
		twenty.setStyle("-fx-font: 70 arial;");
		thirty.setStyle("-fx-font: 70 arial;");

		options.getChildren().addAll(ten, twenty, thirty);
		options.setAlignment(Pos.CENTER);

		pane.setCenter(options);
		menu = new Scene(pane, 1280, 620);
		window.setScene(menu);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
