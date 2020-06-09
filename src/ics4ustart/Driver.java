package ics4ustart;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * Connect 4 with two modes. PVP and PVC. The goal of the game is to connect 4
 * of your pieces in a row. The first player to do so wins.
 * 
 * 
 * @author Cox.N and DaSilva.D
 *
 */
public class Driver {

	public static void main(String[] args) throws InterruptedException {

		Random rand = new Random();

		// Setup constants for the Board
		final int ROWS = 7;
		final int COLS = 7;
		// create the board
		Board board = new Board(ROWS, COLS);
		

		
		
		
		String mode = selectGameMode();
		
		// Player VS player mode
		if (mode.equals("PvP")) {
			System.out.println("Playing vs player");
			int column = 0;
			boolean pOneTurn = true;
			boolean ptwoTurn = false;
			// game loop
			while (!board.gameOver()) {
				board.display();
				if (pOneTurn) {
					System.out.println("Player 1 turn");
					column = getColumn();
					// to fix invalid inputs
					while (!board.isValid(column)) {
						System.out.println("invaid Input. Try again");
						column = getColumn();
					}
					board.dropPeice(column, 1);
					// to toggle turns
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
					// to toggle turns
					pOneTurn = true;
					ptwoTurn = false;
				}

			}
			board.display();
		}

		// Player VS AI mode
		if (mode.equals("PvC")) {
			System.out.println("Playing vs Computer");
			int column = 0;
			boolean pOneTurn = true;
			boolean ptwoTurn = false;
			// game loop
			while (!board.gameOver()) {
				
				if (pOneTurn) {
					System.out.println("Your Turn");
					column = getColumn();
					// to fix invalid inputs
					while (!board.isValid(column)) {
						System.out.println("invaid Input. Try again");
						column = getColumn();
					}
					board.dropPeice(column, 1);
					// to toggle turns
					pOneTurn = false;
					ptwoTurn = true;
					board.display();
				} else if (ptwoTurn) {
					System.out.println("AI's Turn");
					column = rand.nextInt(7);
					board.dropPeice(bestMove(board), 2);
					// to toggle turns
					pOneTurn = true;
					ptwoTurn = false;
				}

			}

		}

	}

	/**
	 * Select game mode (PVP or PVC)
	 * 
	 * @return
	 */
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

	/**
	 * Get Column from user to drop piece
	 * 
	 * @return column user input
	 */
	private static int getColumn() {
		boolean valid = false;
		int column = 0;
		Scanner in = new Scanner(System.in);
		
		System.out.print("Which Column (1-7) :");
		column = Integer.parseInt(in.nextLine().trim());

		// -1 so the first index of the columns will be 0 so if they input 1 it will be
		// 0
		// wich is 1st index of the array of columns
		return column - 1;

	}
	
	
	
	//Initialize vars

	public static int bestMove(Board board) {
		// TODO make recursive function using min max to return best possible move given the state of the board,
		ArrayList<Integer> possibleMoves = board.getAllPossibleMoves();
		System.out.println(possibleMoves);
		//make copy of the baord and use that in the minmax function
		Board boardCopy = board.deepCloneBoard();
		


		//check if the board copys state is still linked in memory to the original board.
		for(int i=0; i<possibleMoves.size();i++) {
			
		}
		return possibleMoves.get(0);
	}
		


	public static int minimax(Board board,int depth, boolean isMaximizing) {
		// TODO make recursive function using min max to return best possible move given the state of the board,
		ArrayList<Integer> possibleMoves = board.getAllPossibleMoves();
		return possibleMoves.get(0);
	}
	
	

}
