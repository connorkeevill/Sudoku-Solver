/**
 * The main object for the board. Represents a sudoku board as an array.
 */
public class SudokuBoard {
	int[] board;

	public SudokuBoard() {
		board = new int[81];
	}

	public SudokuBoard(int[] initialLayout)
	{
		board = initialLayout;
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
