/**
 * JavaFX Project
 * The Circle Game
 */
package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * The Circle Game programs the game called "Nim".
 * Player 1 can select as many circles as he/she wants, but only 
 * in one row per turn. Same goes for player 2. Both players
 * take turns selecting circles. Whoever takes the last circle,
 * losses.
 * @author Hermie Baylon
 * @version 20 July, 2020
 */
public class Main extends Application {
	Stage window;
	VBox entireLayout;
	private Circle c1, c2, c3, c4, c5, c6, c7, c8, c9,
	c10, c11, c12, c13, c14, c15;
	Label title;
	Button confirm;
	Button newGame;
	int counter;
	Circle[] array1;
	Circle[] array2;
	Circle[] array3;
	int max;
	int pre1;
	int pre2;
	int pre3;

	@Override
	public void start(Stage primaryStage) {
		window = primaryStage;
		window.setTitle("The Circle Game");

		initializeCircles();
		entireLayout = new VBox(15);
		setButtons();
		entireLayout.getChildren().add(title);
		createStack(array1);
		createStack(array2);
		createStack(array3);
		addButtons(confirm, newGame);
		
		// Background Color
		BackgroundFill background_fill = new BackgroundFill(Color.GRAY, null, null);
		Background background = new Background(background_fill); 
		entireLayout.setBackground(background);

		Scene scene = new Scene(entireLayout, 900, 500);
		window.setScene(scene);
		window.show();
	}

	/**
	 * Creates a row of circles
	 * @param c is an array of circles
	 */
	private void createStack(Circle ... c) {
		counter = 0;
		HBox stack = new HBox(20);
		HBox buttons = new HBox();
		Button plus = new Button("+");
		Button minus = new Button("-");
		buttons.getChildren().addAll(plus, minus);
		stack.getChildren().add(buttons);
		for (Circle circle : c) {
			stack.getChildren().add(circle);
		}
		entireLayout.getChildren().add(stack);

		plus.setOnAction(e -> {
			int newCounter = 0;
			for (Circle circle: c) {
				if (circle.getStrokeWidth() == 5) {
					newCounter++;
				}
			}
			counter = newCounter;
			
			max = c.length;
			counter++;
			if (counter <= max) {
				for (int i = 0; i < counter; i++) {
					c[i].setStroke(Color.GREEN);
					c[i].setStrokeWidth(5);
				}
			}
		});
		minus.setOnAction(e -> {
			int min = 0;
			for (Circle circle: c) {
				if (circle.getFill() == Color.WHITE) {
					min++;
				}
			}
			
			counter--;
			if (counter >= min) {
				c[counter].setStrokeWidth(0);
			}
		});
		confirm.setOnAction(e -> {
			if (max == 3) {
				deleteFrom(array1);
			} else if (max == 5) {
				deleteFrom(array2);
			} else if (max == 7) {
				deleteFrom(array3);
			}
		});
	}

	/**
	 * deletes circles from @param array
	 */
	private void deleteFrom(Circle[] array) {
		for (Circle circle : array) {
			if (circle.getStrokeWidth() == 5) {
				circle.setFill(Color.GRAY);
			}
		}
	}

	/**
	 * Initializes all circles that is necessary for the program
	 */
	private void initializeCircles() {
		c1 = new Circle(30);
		c2 = new Circle(30);
		c3 = new Circle(30);
		c4 = new Circle(30);
		c5 = new Circle(30);
		c6 = new Circle(30);
		c7 = new Circle(30);
		c8 = new Circle(30);
		c9 = new Circle(30);
		c10 = new Circle(30);
		c11 = new Circle(30);
		c12 = new Circle(30);
		c13 = new Circle(30);
		c14 = new Circle(30);
		c15 = new Circle(30);

		array1 = new Circle[]{c1, c2, c3};
		array2 = new Circle[]{c4, c5, c6, c7, c8};
		array3 = new Circle[]{c9, c10, c11, c12, c13, c14, c15};
	}

	/**
	 * Initializes and organizes all the necessary buttons
	 */
	private void setButtons() {
		title = new Label("The Circle Game");
		confirm = new Button("CONFIRM");
		title.setStyle("-fx-font: 50 arial;");
		title.setPrefSize(600, 100);
		title.setAlignment(Pos.CENTER);
		confirm.setStyle("-fx-font: 15 arial;");
		confirm.setPrefSize(100, 60);
		newGame = new Button("New Game");
		newGame.setStyle("-fx-font: 15 arial;");
		newGame.setPrefSize(100, 60);
		
		newGame.setOnAction(e -> {
			c1.setFill(Color.BLACK);
			c2.setFill(Color.BLACK);
			c3.setFill(Color.BLACK);
			c4.setFill(Color.BLACK);
			c5.setFill(Color.BLACK);
			c6.setFill(Color.BLACK);
			c7.setFill(Color.BLACK);
			c8.setFill(Color.BLACK);
			c9.setFill(Color.BLACK);
			c10.setFill(Color.BLACK);
			c11.setFill(Color.BLACK);
			c12.setFill(Color.BLACK);
			c13.setFill(Color.BLACK);
			c14.setFill(Color.BLACK);
			c15.setFill(Color.BLACK);
			
			c1.setStrokeWidth(0);
			c2.setStrokeWidth(0);
			c3.setStrokeWidth(0);
			c4.setStrokeWidth(0);
			c5.setStrokeWidth(0);
			c6.setStrokeWidth(0);
			c7.setStrokeWidth(0);
			c8.setStrokeWidth(0);
			c9.setStrokeWidth(0);
			c10.setStrokeWidth(0);
			c11.setStrokeWidth(0);
			c12.setStrokeWidth(0);
			c13.setStrokeWidth(0);
			c14.setStrokeWidth(0);
			c15.setStrokeWidth(0);
			
			counter = 0;
		});
	}

	/**
	 * Add the buttons to the playing scene
	 * @param buttons - the array that contains all buttons
	 */
	private void addButtons(Button ... buttons ) {
		HBox allButtons = new HBox(10);
		for (Button b : buttons) {
			allButtons.getChildren().add(b);
		}
		entireLayout.getChildren().add(allButtons);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
