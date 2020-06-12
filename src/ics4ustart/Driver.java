package ics4ustart;

import java.util.Scanner;
import java.util.ArrayList;


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

		// Setup constants for the Board
		final int ROWS = 6;
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
			while ((!board.gameOver()) && !(board.getAllPossibleMoves().size() == 0)) {
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
			if (!board.gameOver()) {
				System.out.println("TIE GAME!");
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
			while (!board.gameOver() && !(board.getAllPossibleMoves().size() == 0)) {

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
					board.dropPeice(bestMove(board), 2);
					// to toggle turns
					pOneTurn = true;
					ptwoTurn = false;
					board.display();
				}

			}
			if (board.checkWinner().equalsIgnoreCase("P1")) {
				System.out.println("Player X wins");
			} else if (board.checkWinner().equalsIgnoreCase("P2")) {
				System.out.println("Player O wins");
			} else {
				System.out.println("Game is a tie");
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

		int column = 0;
		Scanner in = new Scanner(System.in);

		System.out.print("Which Column (1-7) :");
		column = Integer.parseInt(in.nextLine().trim());

		// -1 so the first index of the columns will be 0 so if they input 1 it will be
		// 0
		// wich is 1st index of the array of columns
		return column - 1;

	}

	// Initialize vars

	public static int bestMove(Board board) {
		ArrayList<Integer> possibleMoves = board.getAllPossibleMoves();
		int score = 0;
		int bestScore = Integer.MIN_VALUE;
		int move = 0;
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		System.out.println("AI is Thinking");
		for (int i = 0; i < possibleMoves.size(); i++) {
			board.dropPeice(possibleMoves.get(i), 2);
			// the 2nd input in the miximax function is the depth. The higher the depth the
			// harder the ai will be to beat but the longer it will take to think.
			
			score = minimax(board,7, false, alpha, beta);
			board.undoMove(possibleMoves.get(i), 2);
			System.out.println(score);
			if (score > bestScore) {
				bestScore = score;
				move = possibleMoves.get(i);
			}

		}
		return move;
	}

	
	
	

	
	
	public static int minimax(Board board, int depth, boolean isMaximizing, int alpha, int beta) {
		// TODO make recursive function using min max to return best possible move given
		// the state of the board,
		ArrayList<Integer> possibleMoves = board.getAllPossibleMoves();
		int score = 0;
		// if terminal state
		if ((board.gameOver() || (board.getAllPossibleMoves().size() == 0) || depth == 0)) {
			if (board.checkWinner().equalsIgnoreCase("P1")) {
				return (-10000000);
			} else if (board.checkWinner().equalsIgnoreCase("P2")) {
				return (10000000);
			}
			// no moves left (tie)
			else if (board.getAllPossibleMoves().size() == 0) {
				return (0);
			}
			// could create a function to evalute the current state of the board for when
			// the depth is maxed out so the ai can know what moves are better going into the
			// future.
			else {
				return (0);
			}
		} else {
			if (isMaximizing) {
				int bestScore = Integer.MIN_VALUE;
				for (int i = 0; i < possibleMoves.size(); i++) {
					board.dropPeice(possibleMoves.get(i), 2);
					score = minimax(board, depth - 1, false, alpha, beta);
					board.undoMove(possibleMoves.get(i), 2);
					bestScore = Math.max(score,bestScore);
					alpha = Math.max(alpha ,bestScore);
					if (beta <= alpha) {
						break;
					}

				}
				return (bestScore);
			} else {
				int bestScore = Integer.MAX_VALUE;
				for (int i = 0; i < possibleMoves.size(); i++) {
					board.dropPeice(possibleMoves.get(i), 1);
					score = minimax(board, depth - 1, true,alpha,beta);
					board.undoMove(possibleMoves.get(i), 1);
					bestScore = Math.min(score,bestScore);
					beta = Math.min(beta ,bestScore);
					if (beta <= alpha) {
						break;
					}

				}
				return (bestScore);
			}

		}

	}

}