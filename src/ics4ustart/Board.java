package ics4ustart;

import java.util.ArrayList;

/**
 * Connect 4 with two modes. PVP and PVC. The goal of the game is to connect 4
 * of your pieces in a row. The first player to do so wins.
 * 
 * 
 * @author Cox.N and DaSilva.D
 *
 */

public class Board {
	public Cell[][] board;
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

	/**
	 * @return rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * @return cols
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * drops the piece where the player chose
	 * 
	 * @param col    column on board
	 * @param player P1 or P2
	 */
	public void dropPeice(int col, int player) {

		if (player == 1) {
			board[nextAvalibleRowInCol(col)][col].setState(CellState.P1);
		} else if (player == 2) {
			board[nextAvalibleRowInCol(col)][col].setState(CellState.P2);
		}

	}

	/**
	 * @param col column
	 * @return row position
	 */
	public int nextAvalibleRowInCol(int col) {
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

	/**
	 * Checks if users input of column is valid
	 * 
	 * @param x column number
	 * @return true if valid else false
	 */
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

	
	public void setState(int row,int col, CellState state ) {
		// TODO takes the state of the board and clones another version of it with the same state but made from different memory.
		
		board[row][col].setState(state); 

	}

	public ArrayList<Integer> getAllPossibleMoves() {
		ArrayList<Integer> possibles = new ArrayList<Integer>();
		for(int i = 0; i <board[1].length;i++) {
			if(board[0][i].getState() == CellState.EMPTY) {
				possibles.add(i);
			}
		}
		return possibles;
	}
	
	/**
	 * Display Board
	 */
	public void display() {
		System.out.println("BOARD");
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				System.out.printf("%s ", board[i][j]);
			}
			System.out.println();
		}
	}

	/**
	 * determines if the game state should be over. If either the board is full or a
	 * player connected 4 pieces.
	 * 
	 * @return True if game is over
	 */
	public String checkWinner() {
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

					return("P2");
				}
				if (horizontalCounterP1 >= 4) {
					return("P1");


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
					return("P1");

				}
				if (verticalCounterP2 >= 4) {
					return("P2");


				}
			}
		}
//both of these only check the diagonals from the left side to the right side (Bottom left to top right)
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
				if (board[i][j].getState() == CellState.P2) {
					diagonalCounterP2++;
				} else {
					diagonalCounterP2 = 0;
				}

				if (diagonalCounterP1 >= 4) {
					return("P1");

				}
				if (diagonalCounterP2 >= 4) {
					return("P2");


				}

			}

		}
		for (int k = board[0].length - 2; k >= 0; k--) {
			diagonalCounterP1 = 0;
			diagonalCounterP2 = 0;
			for (int j = 0; j <= k; j++) {
				int i = k - j;

				if (board[board[0].length - j - 1][board[0].length - i - 1].getState() == CellState.P1) {

					diagonalCounterP1++;

				} else {
					diagonalCounterP1 = 0;
				}
				if (board[board[0].length - j - 1][board[0].length - i - 1].getState() == CellState.P2) {
					diagonalCounterP2++;
				} else {
					diagonalCounterP2 = 0;
				}

				if (diagonalCounterP1 >= 4) {
					return("P1");

				}
				if (diagonalCounterP2 >= 4) {
					return("P2");


				}

			}

		}

		// reverse diagonal (Bottom right to top left)

		for (int i = board.length - 1; i > 0; i--) {
			diagonalCounterP1 = 0;
			diagonalCounterP2 = 0;

			for (int j = 0, x = i; x <= board.length - 1; j++, x++) {

				if (board[x][j].getState() == CellState.P1) {
					diagonalCounterP1++;

				} else {
					diagonalCounterP1 = 0;
				}
				if (board[x][i].getState() == CellState.P2) {
					diagonalCounterP2++;
				} else {
					diagonalCounterP2 = 0;
				}

				if (diagonalCounterP1 >= 4) {
					return("P1");

				}
				if (diagonalCounterP2 >= 4) {
					return("P2");

				}
			}

		}

		for (int i = 0; i <= board.length - 1; i++) {
			diagonalCounterP1 = 0;
			diagonalCounterP2 = 0;

			for (int j = 0, z = i; z <= board.length - 1; j++, z++) {

				if (board[j][z].getState() == CellState.P1) {
					diagonalCounterP1++;

				} else {
					diagonalCounterP1 = 0;
				}
				if (board[j][z].getState() == CellState.P2) {
					diagonalCounterP2++;
				} else {
					diagonalCounterP2 = 0;
				}

				if (diagonalCounterP1 >= 4) {
					return("P1");

				}
				if (diagonalCounterP2 >= 4) {
					return("P2");

				}
			}

		}
		return("NONE");

	}
	
	
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
					//System.out.println("GAME OVER Player 2 WINS!");
					break;
				}
				if (horizontalCounterP1 >= 4) {
					gameOver = true;
					//System.out.println("GAME OVER Player 1 WINS!");
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
					//System.out.println("GAME OVER Player 1 WINS!");
					break;
				}
				if (verticalCounterP2 >= 4) {
					gameOver = true;
					//System.out.println("GAME OVER Player 2 WINS!");
					break;

				}
			}
		}
//both of these only check the diagonals from the left side to the right side (Bottom left to top right)
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
				if (board[i][j].getState() == CellState.P2) {
					diagonalCounterP2++;
				} else {
					diagonalCounterP2 = 0;
				}

				if (diagonalCounterP1 >= 4) {
					gameOver = true;
					//System.out.println("GAME OVER Player 1 WINS!");
					break;
				}
				if (diagonalCounterP2 >= 4) {
					gameOver = true;
					//System.out.println("GAME OVER Player 2 WINS!");
					break;

				}

			}

		}
		for (int k = board[0].length - 2; k >= 0; k--) {
			diagonalCounterP1 = 0;
			diagonalCounterP2 = 0;
			for (int j = 0; j <= k; j++) {
				int i = k - j;

				if (board[board[0].length - j - 1][board[0].length - i - 1].getState() == CellState.P1) {

					diagonalCounterP1++;

				} else {
					diagonalCounterP1 = 0;
				}
				if (board[board[0].length - j - 1][board[0].length - i - 1].getState() == CellState.P2) {
					diagonalCounterP2++;
				} else {
					diagonalCounterP2 = 0;
				}

				if (diagonalCounterP1 >= 4) {
					gameOver = true;
					//System.out.println("GAME OVER Player 1 WINS!");
					break;
				}
				if (diagonalCounterP2 >= 4) {
					gameOver = true;
					//System.out.println("GAME OVER Player 2 WINS!");
					break;

				}

			}

		}

		// reverse diagonal (Bottom right to top left)

		for (int i = board.length - 1; i > 0; i--) {
			diagonalCounterP1 = 0;
			diagonalCounterP2 = 0;

			for (int j = 0, x = i; x <= board.length - 1; j++, x++) {

				if (board[x][j].getState() == CellState.P1) {
					diagonalCounterP1++;

				} else {
					diagonalCounterP1 = 0;
				}
				if (board[x][i].getState() == CellState.P2) {
					diagonalCounterP2++;
				} else {
					diagonalCounterP2 = 0;
				}

				if (diagonalCounterP1 >= 4) {
					gameOver = true;
					//System.out.println("GAME OVER Player 1 WINS!");
					break;
				}
				if (diagonalCounterP2 >= 4) {
					gameOver = true;
					//System.out.println("GAME OVER Player 2 WINS!");
					break;

				}
			}

		}

		for (int i = 0; i <= board.length - 1; i++) {
			diagonalCounterP1 = 0;
			diagonalCounterP2 = 0;

			for (int j = 0, z = i; z <= board.length - 1; j++, z++) {

				if (board[j][z].getState() == CellState.P1) {
					diagonalCounterP1++;

				} else {
					diagonalCounterP1 = 0;
				}
				if (board[j][z].getState() == CellState.P2) {
					diagonalCounterP2++;
				} else {
					diagonalCounterP2 = 0;
				}

				if (diagonalCounterP1 >= 4) {
					gameOver = true;
					//System.out.println("GAME OVER Player 1 WINS!");
					break;
				}
				if (diagonalCounterP2 >= 4) {
					gameOver = true;
					//System.out.println("GAME OVER Player 2 WINS!");
					break;

				}
			}

		}

		return gameOver;
	}

	public void undoMove(int col, int player) {

		if (player == 1) {
			board[nextAvalibleRowInCol(col)+1][col].setState(CellState.EMPTY);
		} else if (player == 2) {
			board[nextAvalibleRowInCol(col)+1][col].setState(CellState.EMPTY);
		}

	}
	

}
