package ics4ustart;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Connect4GUIDrover extends Application {
	Scene scene1, scene2;
	private int clickedButton = -1;
	private int turn = 0;

	private final int rows = 6;
	private final int cols = 7;

	private final int canvasHeight = 600;
	private final int canvasWidth = 700;

	private final double cellHeight = (double) canvasHeight / rows;
	private final double cellWidth = (double) canvasWidth / cols;

	@Override
	public void start(Stage stage) throws Exception {

		Board board = new Board(rows, cols);

		stage.setTitle("Connect 4 - Diangelo and Nathan");

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(20.0, 0.0, 20.0, 0.0));

		VBox box = new VBox();
		box.setPadding(new Insets(20.0, 20.0, 20.0, 50.0));

		Canvas canvas = new Canvas(canvasWidth, canvasHeight);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		drawBoard(gc, board);
		
		Text title = new Text("Red Wins");
		Text title2 = new Text("Blue Wins");
		Font font = new Font("Arial", 30);
		

		Button buttonArray[] = new Button[cols];

		for (int i = 0; i < cols; i++) {
			buttonArray[i] = new Button("Input");
			buttonArray[i].setMinWidth(cellWidth);
			buttonArray[i].setMaxWidth(cellWidth);
			buttonArray[i].setId("" + i);
			gridPane.add(buttonArray[i], i, 0);
		}

		for (Button b : buttonArray) {

			b.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println(b);
					int column = 0;

					if (turn % 2 == 0) {

						System.out.println("Player 1 turn");
						column = Integer.valueOf(b.getId());
						// to fix invalid inputs

						board.dropPeice(column, 2);
						repaintCanvas(gc, board);
						// to toggle turns

						turn = turn + 1;

					} else if (turn % 2 != 0) {
						System.out.println("Player 2 turn");
						column = Integer.valueOf(b.getId());
						// to fix invalid inputs

						board.dropPeice(column, 1);
						repaintCanvas(gc, board);
						// to toggle turns

						turn = turn + 1;

					}
					board.display();
					if (board.gameOver()) {
						for (Button button : buttonArray) {
							button.setDisable(true);

						}
						if (board.checkWinner().equalsIgnoreCase("P1")) {
							
							title.setFont(font);
							box.getChildren().add(title);
							
							
						} else {title2.setFont(font);
						box.getChildren().add(title2);

						}

					}

				}

			});
		}

		VBox b = new VBox();
		b.setPadding(new Insets(20.0, 20.0, 20.0, 350.0));

		box.getChildren().addAll(gridPane, canvas, b);
		scene1 = (new Scene(box, 825, 825));

		stage.setScene(scene1);

		stage.setResizable(false);

		Button button2 = new Button("Play PVC");
		Button reset1 = new Button("Reset Game");
		box.getChildren().add(button2);
		box.getChildren().add(reset1);
		button2.setOnAction(e -> {

			stage.setScene(scene2);

		});
		reset1.setOnAction(e -> {

			board.resetState();
			repaintCanvas(gc, board);
			for (Button button : buttonArray) {
				button.setDisable(false);

			}
			
			
			try {
				box.getChildren().remove(title);
				box.getChildren().remove(title2);
				}
				catch(Exception e2) {
				  //  Block of code to handle errors
				}
		});

		stage.show();

		// second scene
		Label label2 = new Label("This is the second scene");

		Board board2 = new Board(rows, cols);

		Button button3 = new Button("Play PVP");
		Button button4 = new Button("Reset Game");
		button3.setOnAction(e -> stage.setScene(scene1));

		VBox layout2 = new VBox(20);
		layout2.getChildren().addAll(label2, button3);

		GridPane gridPane2 = new GridPane();
		gridPane2.setPadding(new Insets(20.0, 0.0, 20.0, 0.0));

		VBox box2 = new VBox();
		box2.setPadding(new Insets(20.0, 20.0, 20.0, 50.0));
		box2.getChildren().add(button4);

		Canvas canvas2 = new Canvas(canvasWidth, canvasHeight);
		GraphicsContext gc2 = canvas2.getGraphicsContext2D();
		drawBoard(gc2, board);

		

		Button buttonArray2[] = new Button[cols];

		for (int i = 0; i < cols; i++) {
			buttonArray2[i] = new Button("Input");
			buttonArray2[i].setMinWidth(cellWidth);
			buttonArray2[i].setMaxWidth(cellWidth);
			buttonArray2[i].setId("" + i);
			gridPane2.add(buttonArray2[i], i, 0);
		}

		for (Button b2 : buttonArray2) {

			b2.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println(b2);
					int column2 = 0;

					System.out.println("Player 1 turn");
					column2 = Integer.valueOf(b2.getId());
					// to fix invalid inputs

					board2.dropPeice(column2, 1);

					repaintCanvas(gc2, board2);

					board2.dropPeice(Driver.bestMove(board2), 2);

					repaintCanvas(gc2, board2);

					board2.display();
					if (board2.gameOver()) {
						for (Button button2 : buttonArray2) {
							button2.setDisable(true);
							

						}
						if (board2.checkWinner().equalsIgnoreCase("P1")) {
							title.setFont(font);
							box2.getChildren().add(title);
						} else {title2.setFont(font);
						box2.getChildren().add(title2);

						}

					}

				}

			});
		}

		
		button4.setOnAction(e -> {

			board2.resetState();
			repaintCanvas(gc2, board2);
			for (Button button : buttonArray2) {
				button.setDisable(false);

			}
			try {
				box2.getChildren().remove(title);
				box2.getChildren().remove(title2);
				}
				catch(Exception e2) {
				  //  Block of code to handle errors
				}

		});
		
		
		VBox b2 = new VBox();
		b2.setPadding(new Insets(20.0, 20.0, 20.0, 350.0));

		box2.getChildren().addAll(gridPane2, canvas2, b2);
		box2.getChildren().add(button3);

		scene2 = (new Scene(box2, 850, 850));
		;

//		Stage secondStage = new Stage();
//		
//        secondStage.setScene(new Scene(new HBox(4, new Label("Second window"))));
//        secondStage.show();
	}

	/**
	 * @param gc paints background
	 */
	private void drawBoard(GraphicsContext gc, Board board) {

		gc.setFill(Color.rgb(192, 192, 192));
		gc.fillRect(0, 0, canvasWidth, canvasHeight);

		gc.setFill(Color.BLACK);

		for (int i = 0; i <= rows; i++) {
			gc.strokeLine(0, i * cellHeight, canvasWidth, i * cellHeight);
		}

		for (int i = 0; i <= cols; i++) {
			gc.strokeLine(i * cellWidth, 0, i * cellWidth, canvasHeight);
		}

		int offset = 3;

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {

				if (board.getData(r, c) == 1) {
					gc.setFill(Color.RED);
					gc.fillOval(c * cellHeight, r * cellWidth, cellWidth - offset, cellHeight - offset);
				}

				if (board.getData(r, c) == 2) {
					gc.setFill(Color.BLUE);
					gc.fillOval(c * cellHeight, r * cellWidth, cellWidth - offset, cellHeight - offset);
				}

			}
		}

	}

	private void repaintCanvas(GraphicsContext gc, Board board) {
		gc.clearRect(0, 0, canvasWidth, canvasHeight);
		drawBoard(gc, board);
	}

	private static int getColumn() {

		int column = 0;
		Scanner in = new Scanner(System.in);

		System.out.print("Which Column (1-7) :");
		column = Integer.parseInt(in.nextLine().trim());

		// -1 so the first index of the columns will be 0 so if they input 1 it will be
		// 0
		// wich is 1st index of the array of columns
		return column - 1;

	}

	private void getButton(Button[] buttonArray, Board board) {

		for (Button b : buttonArray) {
			System.out.println(b);
			b.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					board.dropPeice(Integer.valueOf(b.getId()), 1);
				}

			});
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("running");
		launch(args);
	}

}
