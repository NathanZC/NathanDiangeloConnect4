package ics4ustart;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Driver {

	public static void main(String[] args) throws InterruptedException {

		
		// Diangelo is not the best
		//Nathan is the worst
		// git hub is such a good invention 
		// Setup constants for the Board
		final int ROWS = 7;
		final int COLS = 7;
		// create the board
		Board board = new Board(ROWS, COLS);
		String mode = selectGameMode();

		if (mode.equals("PvP")) {
			System.out.println("Playing vs player");
			int column = 0;
			boolean pOneTurn = true;
			boolean ptwoTurn = false;
			//game loop
			while (!board.gameOver()) {
				board.display();
				if (pOneTurn) {
					System.out.println("Player 1 turn");
					column = getColumn();
					//to fix invalid inputs
					while (!board.isValid(column)) {
						System.out.println("invaid Input. Try again");
						column = getColumn();
					}
					board.dropPeice(column, 1);
					//to toggle turns
					pOneTurn = false;
					ptwoTurn = true;
				} else if (ptwoTurn) {
					System.out.println("Player 2 turn");
					column = getColumn();
					while (!board.isValid(column)) {
						System.out.println("invaid Input. Try again");
						column = getColumn();
						
					}
					board.dropPeice(column, 2);
					//to toggle turns
					pOneTurn = true;
					ptwoTurn = false;
				}

			}
			board.display();
		}

		if (mode.equals("PvC")) {
			System.out.println("Playing vs Computer");

		}

	}

	private static String selectGameMode() {
		Scanner in = new Scanner(System.in);
		System.out.println("Type 1 for Player Vs AI \nType 2 for two player mode");
		int choice = in.nextInt();
		while (!(choice == 1 || choice == 2)) {
			choice = in.nextInt();
			System.out.println("Please input valid choice");
		}
		if (choice == 1) {
			return "PvC";
		} else {
			return "PvP";
		}

	}

	private static int getColumn() {
		boolean valid = false;
		int column = 0;
		Scanner in = new Scanner(System.in);

		System.out.print("Which Column (1-7) :");
		column = Integer.parseInt(in.nextLine().trim());

		// -1 so the first index of the columns will be 0 so if they input 1 it will be 0
		// wich is 1st index of the array of columns
		return column - 1;

	}

}
