package ics4ustart;

import java.util.Random;

public class Board {
	private Cell[][] board;
	private int rows;
	private int cols;

	public Board(int aRows, int aCols) {
		board = new Cell[aRows][aCols];
		rows = aRows;
		cols = aCols;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				board[i][j] = new Cell(CellState.EMPTY); // no color
			}
		}
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public void dropPeice(int col, int player) {

		if (player == 1) {
			board[nextAvalibleRowInCol(col)][col].setState(CellState.P1);
		} else if (player == 2) {
			board[nextAvalibleRowInCol(col)][col].setState(CellState.P2);
		}

	}

	private int nextAvalibleRowInCol(int col) {
		boolean emptySpotFound = false;
		int rowPosition = rows - 1;

		while (!emptySpotFound) {
			// if row is full then the index will go to -1 wich will cause a error when it
			// gets -1 index of the list. If the row is full the indexes will keep counting
			// past 0 but this will stop it and just return -1 so I know the column is full
			// if this method return -1 for the row

			if (rowPosition < 0) {
				return -1;
			} else {
				if (board[rowPosition][col].getState() == CellState.EMPTY) {
					emptySpotFound = true;

				} else {
					rowPosition--;
				}

			}

		}
		return rowPosition;

	}

	public boolean isValid(int x) {
		boolean valid = false;
		// checks column input to see if in range of the board columns
		if (x >= 0 && x < cols) {
			// check to see if the column is full. If its not then the peice can be placed
			// in the next spot in the column
			if (nextAvalibleRowInCol(x) != -1) {
				valid = true;
			}
		}

		return valid;
	}

	public void display() {
		System.out.println("BOARD");
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				System.out.printf("%s ", board[i][j]);
			}
			System.out.println();
		}
	}

	/*
	 * determines if the game state should be over. If either the board is full or a
	 * player connected 4 pieces.
	 */
	public boolean gameOver() {
		boolean gameOver = false;
		// checks to see if player got 4 horizontally in a row
		int horizontalCounterP1 = 0;
		int horizontalCounterP2 = 0;
		for (int i = 0; i < rows; i++) {
			horizontalCounterP1 = 0;
			horizontalCounterP2 = 0;
			for (int j = 0; j < cols; j++) {
				if (board[i][j].getState() == CellState.P1) {
					horizontalCounterP1++;
				} else {
					horizontalCounterP1 = 0;
				}
				if (board[i][j].getState() == CellState.P2) {
					horizontalCounterP2++;
				} else {
					horizontalCounterP2 = 0;
				}

				if (horizontalCounterP2 >= 4) {
					gameOver = true;
					System.out.println("GAME OVER Player 2 WINS!");
					break;
				}
				if (horizontalCounterP1 >= 4) {
					gameOver = true;
					System.out.println("GAME OVER Player 1 WINS!");
					break;

				}
			}
		}
		// to check to see if any player got a vertical 4 in a row
		int verticalCounterP1 = 0;
		int verticalCounterP2 = 0;
		for (int i = 0; i < cols; i++) {
			verticalCounterP1 = 0;
			verticalCounterP2 = 0;
			for (int j = 0; j < rows; j++) {
				if (board[j][i].getState() == CellState.P1) {
					verticalCounterP1++;

				} else {
					verticalCounterP1 = 0;
				}
				if (board[j][i].getState() == CellState.P2) {
					verticalCounterP2++;
				} else {
					verticalCounterP2 = 0;
				}

				if (verticalCounterP1 >= 4) {
					gameOver = true;
					System.out.println("GAME OVER Player 1 WINS!");
					break;
				}
				if (verticalCounterP2 >= 4) {
					gameOver = true;
					System.out.println("GAME OVER Player 2 WINS!");
					break;

				}
			}
		}
//both of these only check the diagonals from the left side to the right side 
		int diagonalCounterP1 = 0;
		int diagonalCounterP2 = 0;
		for (int k = 0; k < board.length; k++) {
			diagonalCounterP1 = 0;
			diagonalCounterP2 = 0;
			for (int j = 0; j <= k; j++) {
				int i = k - j;
				if (board[i][j].getState() == CellState.P1) {
					diagonalCounterP1++;

				} else {
					diagonalCounterP1 = 0;
				}
				if (board[j][i].getState() == CellState.P2) {
					diagonalCounterP2++;
				} else {
					diagonalCounterP2 = 0;
				}

				if (diagonalCounterP1 >= 4) {
					gameOver = true;
					System.out.println("GAME OVER Player 1 WINS!");
					break;
				}
				if (diagonalCounterP2 >= 4) {
					gameOver = true;
					System.out.println("GAME OVER Player 2 WINS!");
					break;

				}

			}

		}
		for (int k = board[0].length - 2; k >= 0; k--) {
			diagonalCounterP1 = 0;
			diagonalCounterP2 = 0;
			for (int j = 0; j <= k; j++) {
				int i = k - j;
				System.out.print(board[board[0].length - j - 1][board[0].length - i - 1] + " ");
				if (board[i][j].getState() == CellState.P1) {
					diagonalCounterP1++;

				} else {
					diagonalCounterP1 = 0;
				}
				if (board[j][i].getState() == CellState.P2) {
					diagonalCounterP2++;
				} else {
					diagonalCounterP2 = 0;
				}

				if (diagonalCounterP1 >= 4) {
					gameOver = true;
					System.out.println("GAME OVER Player 1 WINS!");
					break;
				}
				if (diagonalCounterP2 >= 4) {
					gameOver = true;
					System.out.println("GAME OVER Player 2 WINS!");
					break;

				}

			}
			System.out.println("");

		}
		return gameOver;
	}
}