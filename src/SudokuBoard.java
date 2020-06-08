/**
 * The main object for the board. Represents a sudoku board as an array, with each index representing a square in
 * the board, with 0 being the top left, and 80 being the bottom right.
 */
public class SudokuBoard {
	int[] board;

	public SudokuBoard() {
		board = new int[81];
	}

	/**
	 * Initialises the board using a string containing the values of each square, separated by spaces. This string is
	 * converted into the array used to represent the board.
	 * @param boardLayout the string containing the layout of the Sudoku board
	 */
	public SudokuBoard(String boardLayout)
	{
		this.board = new int[81];
		String[] board = boardLayout.split(" ");

		guardAgainstInvalidBoardLength(board);

		for(int item = 0; item < board.length; item++)
		{
			this.board[item] = Integer.parseInt(board[item]);
		}
	}

	private void guardAgainstInvalidBoardLength(String[] board) {
		if(board.length > 81)
		{
			throw new IllegalArgumentException("The board layout must be exactly 81 numbers. It is too long.");
		}
		if(board.length < 81)
		{
			throw new IllegalArgumentException("The board layout must be exactly 81 numbers. It is too short.");
		}
	}

	public String toString()
	{
		return  "";
	}

	public boolean isValid()
	{
		return false;
	}

	/**
	 * Returns a boolean indicating whether or not the given row contains a valid set of numbers.
	 * @param row the row to verify.
	 * @return boolean representing the validity of the row.
	 */
	private boolean validateRow(int row)
	{
		if(!inRange(row, 0, 8))
		{
			throw new IndexOutOfBoundsException("There are only 9 rows in the board.");
		}

		int rowStartPoint = row * 9;
		NumberConstraint rowValidity = new NumberConstraint();

		for(int rowItem = 0; rowItem < 9; rowItem++)
		{
			if(board[rowStartPoint + rowItem] != 0)
			{
				rowValidity.add(board[rowStartPoint + rowItem]);
			}
		}

		return rowValidity.isValid();
	}

	/**
	 * Returns a boolean indicating whether or not the given column contains a valid set of numbers.
	 * @param col the column to validate.
	 * @return boolean indicating whether or not the column is valid.
	 */
	private boolean validateColumn(int col)
	{
		if(!inRange(col, 0, 8))
		{
			throw new IndexOutOfBoundsException("There are only 9 columns");
		}

		NumberConstraint colValidity = new NumberConstraint();

		for(int colItem = 0; colItem < 54; colItem += 9)
		{
			if(board[col + colItem] != 0)
			{
				colValidity.add(board[col + colItem]);
			}
		}

		return colValidity.isValid();
	}

	/**
	 * Returns a boolean indicating whether or no the given sub-board contains a valid set of numbers.
	 * @param subBoard the sub-board to validate; 0 is top left, and moving left to right, 8 is bottom right.
	 * @return boolean indicating whether or now the sub-board is valid.
	 */
	private boolean validateSubBoard(int subBoard)
	{
		if(!inRange(subBoard, 0, 8))
		{
			throw new IndexOutOfBoundsException("There are only 9 sub-boards");
		}

		// This formula maps the numbers 0-8 onto the starting points (i.e. top left square) of each sub-board in the array.
		// 0 -> 0, 1 -> 3, 2 -> 6, 3 -> 27, 4 -> 30, ..., 7 -> 57, 8 -> 60
		int startingPoint = (subBoard / 3) * 27 + (subBoard % 3) * 3;

		NumberConstraint subBoardValidity = new NumberConstraint();

		// Outer loop loops through the rows of the sub-board, each separated by 9 in the array.
		for(int subBoardRow = startingPoint; subBoardRow < startingPoint + 27; subBoardRow += 9)
		{
			// Inner loop loops through the columns of each row of the sub-board.
			for(int subBoardCol = subBoardRow; subBoardCol < subBoardRow + 3; subBoardCol += 1)
			{
				if(board[subBoardCol] != 0)
				{
					subBoardValidity.add(board[subBoardCol]);
				}
			}
		}

		return subBoardValidity.isValid();
	}

	/**
	 * Returns a boolean indicating whether or not the given value is in the interval [lower, upper]
	 * @param value the value to test
	 * @param lower the lower bound of the interval
	 * @param upper the upper bound of the interval
	 * @return whether or not value is in the interval.
	 */
	private boolean inRange(int value, int lower, int upper)
	{
		if(upper <= lower)
		{
			throw new IllegalArgumentException("Bounds lower and upper aren't of the form lower < upper. Ensure that" +
					" the interval is properly defined.");
		}

		return value >= lower && value <= upper;
	}

	/**
	 * Inner class to represent the numbers in a row/column/sub-board. Allows easy validation of the entire board.
	 */
	private class NumberConstraint
	{
		// Keep track of all the numbers in the row/col/sub-board with an array that counts the appearances of the number at each index.
		int[] numbers = new int[9];

		/**
		 * Adds 1 to the count for the given number.
		 * @param number the number to count.
		 */
		public void add(int number)
		{
			// Subtract one as we're using an array here to track each number in the row/col/sub-board.
			number--;

			if(!inRange(number, 0, 8))
			{
				throw new IndexOutOfBoundsException("Sudoku boards only contain numbers 1 - 9.");
			}

			numbers[number]++;
		}

		/**
		 * Returns a boolean indicating whether or not the row/col/sub-board is valid, i.e. has at most 1 occurance of
		 * each number.
		 * @return boolean representing validity.
		 */
		public boolean isValid()
		{
			for (int number : numbers)
			{
				if(number > 1)
				{
					return false;
				}
			}

			return true;
		}
	}
}
