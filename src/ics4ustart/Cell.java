package ics4ustart;

public class Cell {
	private CellState state;
	
	
	public Cell(CellState cs) {
		state = cs;
		
	}
	

	
	public void setState (CellState cs){
		state = cs;
	}
	
	public CellState getState() {
		return state;
	}

	/**
	 *prints "X", "O", or"-"
	 */
	public String toString() {
		switch (state) {
		case P1:
			return "X";
		case P2:
			return "O";
		case EMPTY:
			return "-";
		default:
			return "-";
		}
	}
}
