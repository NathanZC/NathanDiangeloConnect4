package ics4ustart;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Connect4GUIDrover extends Application {

	private int clickedButton = -1;

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

		Button buttonArray[] = new Button[cols];

		for (int i = 0; i < cols; i++) {
			buttonArray[i] = new Button("Input");
			buttonArray[i].setMinWidth(cellWidth);
			buttonArray[i].setMaxWidth(cellWidth);
			buttonArray[i].setId("" + i);
			gridPane.add(buttonArray[i], i, 0);
		}

		for (Button b : buttonArray) {
			System.out.println(b);
			b.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {

					int column = 0;
					boolean pOneTurn = true;
					boolean ptwoTurn = false;

					board.display();
					if (pOneTurn) {
						System.out.println("Player 1 turn");
						column = Integer.valueOf(b.getId());
						// to fix invalid inputs

						board.dropPeice(column, 1);
						repaintCanvas(gc, board);
						// to toggle turns
						pOneTurn = false;
						ptwoTurn = true;
					} else if (ptwoTurn) {

						column = Integer.valueOf(b.getId());

						board.dropPeice(column, 2);
						repaintCanvas(gc, board);
						// to toggle turns
						pOneTurn = true;
						ptwoTurn = false;
					}

				}

			});
		}

		VBox b = new VBox();
		b.setPadding(new Insets(20.0, 20.0, 20.0, 350.0));

		box.getChildren().addAll(gridPane, canvas, b);
		stage.setScene(new Scene(box, 800, 800));
		stage.setResizable(false);

		stage.show();
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
