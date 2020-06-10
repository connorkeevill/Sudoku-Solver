public class SudokuSolver {
	private SudokuBoard board;

	public SudokuSolver(SudokuBoard board)
	{
		this.board = board;
	}

	/**
	 * Solves the board.
	 */
	public void solve()
	{
		recursiveSolve(0);
	}

	/**
	 * Solves the sudoku board recursively.
	 * @param startPoint the square we've solved up to.
	 * @return boolean to allow backtracking.
	 */
	private boolean recursiveSolve(int startPoint)
	{
		int square = getIndexOfNextFreeSquare(startPoint);

		// If we're at the final square and the board is valid then we're done.
		if(square == 81 && board.isValid())
		{
			return true;
		}

		for(int numberToTry = 1; numberToTry <= 9; numberToTry++)
		{
			board.setValueAt(square, numberToTry);

			if(!board.isValid())
			{
				continue;
			}

			if(recursiveSolve(square))
			{
				return true;
			}
		}

		board.setValueAt(square, 0);
		return false;
	}

	/**
	 * Returns the board.
	 * @return SudokuBoard.
	 */
	public SudokuBoard getBoard() {
		return board;
	}

	/**
	 * Finds the closest free square at or after the given square.
	 * @param square the square to look at.
	 * @return the closest free square.
	 */
	private int getIndexOfNextFreeSquare(int square) {
		while(square < 81 &&!board.isSquareEmpty(square))
		{
			square++;
		}

		return square;
	}
}
