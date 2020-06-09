/**
 * The main object for the board. Represents a sudoku board as an array, with each index representing a square in
 * the board, with 0 being the top left, and 80 being the bottom right.
 */
public class SudokuBoard {
	private int[] board;

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
			int number = Integer.parseInt(board[item]);

			guardAgainstInvalidBoardValue(number);

			this.board[item] = number;
		}

		if(!this.isValid())
		{
			this.board = new int[81];
			throw new IllegalArgumentException("Invalid layout given to SudokuBoard.");
		}
	}

	/**
	 * Sets the layout of the board based on the given input string.
	 * @param boardLayout the input layout.
	 */
	public void setBoard(String boardLayout)
	{
		this.board = new int[81];
		String[] board = boardLayout.split(" ");

		guardAgainstInvalidBoardLength(board);

		// Store the current state of the board incase the new board is invalid.
		int[] oldBoard = this.board;

		for (int item = 0; item < board.length; item++) {
			int number = Integer.parseInt(board[item]);

			guardAgainstInvalidBoardValue(number);

			this.board[item] = number;
		}

		if(!this.isValid())
		{
			this.board = oldBoard;
			throw new IllegalArgumentException("Invalid layout given to SudokuBoard.");
		}
	}

	/**
	 * Returns a string representing the board.
	 * @return string representation of the board.
	 */
	public String toString()
	{
		StringBuilder output = new StringBuilder();

		for(int square = 0; square < board.length; square++)
		{
			if(board[square] == 0)
			{
				output.append(" ");
			}
			else
			{
				output.append(board[square]);
			}

			// If we aren't at the right hand side of the board, add a column divider.
			if((square + 1) % 9 != 0)
			{
				// If we aren't at the boundary of a sub-board, add a single pipe, otherwise add a double.
				if((square + 1) % 3 != 0) {
					output.append(" | ");
				}
				else
				{
					output.append(" || ");
				}
			}
			// At the end of each row, we need to add a row divider
			else
			{
				output.append("\n");

				// These are the squares before the new line needs to be a double boundary for the
				if(square == 26 || square == 53)
				{
					output.append("===================================\n");
				}
				else if(square != board.length - 1)
				{
					output.append("-----------------------------------\n");
				}
			}
		}

		return output.toString();
	}

	/**
	 * Returns a boolean indicating whether or not the board is in a valid configuration.
	 * @return the boolean
	 */
	public boolean isValid()
	{
		for(int count = 0; count < 9; count++)
		{
			boolean countValid = validateRow(count) && validateColumn(count) && validateSubBoard(count);

			if(!countValid)
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns a boolean indicating whether or not the board is both valid and completely full.
	 * @return the boolean.
	 */
	public boolean isValidAndComplete()
	{
		// Assume that board is full and try to disprove
		boolean full = true;

		for(int square : board)
		{
			if(square == 0)
			{
				full = false;
				break;
			}
		}

		return full && isValid();
	}

	/**
	 * Sets the value of the board at the given position
	 * @param col the column of the square to modify
	 * @param row the row of the square to modify
	 * @param value the value to insert
	 */
	public void setValueAt(int col, int row, int value)
	{
		guardAgainstInvalidBoardValue(value);
		int pos = performCoordinateToIndexTranslation(col, row);

		setValueAt(pos, value);
	}

	/**
	 * Sets the value of the board at the position given relative to the start of the array.
	 * @param pos square's position in array.
	 * @param value the value to insert.
	 */
	public void setValueAt(int pos, int value)
	{
		guardAgainstIndexOutOfRange(pos);
		guardAgainstInvalidBoardValue(value);

		board[pos] = value;
	}

	/**
	 * Gets the value at the given position
	 * @param col the column of the square to get the value of
	 * @param row the row of the square to get the value of
	 * @return the value at the position
	 */
	public int getValueAt(int col, int row)
	{
		int pos = performCoordinateToIndexTranslation(col, row);

		return getValueAt(pos);
	}

	/**
	 * Gets the value at the given position relative to the start of the array
	 * @param pos the square's position in the array
	 * @return the value at the position
	 */
	public int getValueAt(int pos)
	{
		guardAgainstIndexOutOfRange(pos);

		return board[pos];
	}

	/**
	 * Indicates whether or not there is a value at the given position.
	 * @param col the column the square is in.
	 * @param row the row the square is in.
	 * @return boolean indicating whether or not there is value at the position.
	 */
	public boolean isSquareEmpty(int col, int row)
	{
		int pos = performCoordinateToIndexTranslation(col, row);

		return isSquareEmpty(pos);
	}

	/**
	 * Indicates whether or not there is a value at the given position.
	 * @param pos the square's position in the array.
	 * @return boolean indicating whether or not there is value at the position.
	 */
	public boolean isSquareEmpty(int pos)
	{
		guardAgainstIndexOutOfRange(pos);

		return board[pos] == 0;
	}

	/**
	 * Protects against a board layout of the incorrect length.
	 * @param boardLayout the board layout to verify
	 */
	private void guardAgainstInvalidBoardLength(String[] boardLayout) {
		if(boardLayout.length > 81)
		{
			throw new IllegalArgumentException("The board layout must be exactly 81 numbers. It is too long.");
		}
		if(boardLayout.length < 81)
		{
			throw new IllegalArgumentException("The board layout must be exactly 81 numbers. It is too short.");
		}
	}

	/**
	 * Protects against an index that would be out of range for the board.
	 * @param index the index to check.
	 */
	private void guardAgainstIndexOutOfRange(int index)
	{
		if(!inRange(index, 0, board.length - 1)){
			throw new IllegalArgumentException("Given index out of range.");
		}
	}

	/**
	 * Protects against a value not between 0-9 being put into the board.
	 * @param value the value to check.
	 */
	private void guardAgainstInvalidBoardValue(int value)
	{
		if(!inRange(value, 0, 9))
		{
			throw new IllegalArgumentException("Sudoku board can only contain values from 0-9.");
		}
	}

	/**
	 *  Protects against an invalid coordinate pairing.
	 * @param col the column of the coordinate.
	 * @param row the row of the coordinate.
	 */
	private void guardAgainstInvalidCoordinates(int col, int row)
	{
		if(!inRange(col, 0, 8) || !inRange(row, 0, 8))
		{
			throw new IllegalArgumentException("The given coordinates are outside the range of the board.");
		}
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

	private int performCoordinateToIndexTranslation(int col, int row)
	{
		guardAgainstInvalidCoordinates(col, row);

		return col + 9 * row;
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
