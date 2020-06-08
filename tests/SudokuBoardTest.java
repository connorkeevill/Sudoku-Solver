import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardTest {
	// Example test boards here are taken from https://codegolf.stackexchange.com/questions/41523/sudoku-compression and
	// http://www.opensky.ca/~jdhildeb/software/sudokugen/

	/**
	 * Test that the board can successfully validate a valid layout
	 */
	@Test
	void correctlyValidateValidBoard()
	{
		String boardLayout = "9 7 3 5 8 1 4 2 6 " +
							 "5 2 6 4 7 3 1 9 8 " +
							 "1 8 4 2 9 6 7 5 3 " +
							 "2 4 7 8 6 5 3 1 9 " +
							 "3 9 8 1 2 4 6 7 5 " +
							 "6 5 1 7 3 9 8 4 2 " +
							 "8 1 9 3 4 2 5 6 7 " +
							 "7 6 5 9 1 8 2 3 4 " +
							 "4 3 2 6 5 7 9 8 1 ";

		SudokuBoard board = new SudokuBoard(boardLayout);

		assertTrue(board.isValid());
	}

	/**
	 * Test that the SudokuBoard is correctly created when instantiated by checking the output string
	 */
	@Test
	void createBoardFromValidString()
	{
		String boardLayout = "9 7 3 5 8 1 4 2 6 " +
							 "5 2 6 4 7 3 1 9 8 " +
							 "1 8 4 2 9 6 7 5 3 " +
							 "2 4 7 8 6 5 3 1 9 " +
							 "3 9 8 1 2 4 6 7 5 " +
							 "6 5 1 7 3 9 8 4 2 " +
							 "8 1 9 3 4 2 5 6 7 " +
							 "7 6 5 9 1 8 2 3 4 " +
							 "4 3 2 6 5 7 9 8 1 ";

		SudokuBoard board = new SudokuBoard(boardLayout);

		String expected = "9 | 7 | 3 || 5 | 8 | 1 || 4 | 2 | 6\n" +
						  "-----------------------------------\n" +
						  "5 | 2 | 6 || 4 | 7 | 3 || 1 | 9 | 8\n" +
						  "-----------------------------------\n" +
						  "1 | 8 | 4 || 2 | 9 | 6 || 7 | 5 | 3\n" +
						  "===================================\n" +
						  "2 | 4 | 7 || 8 | 6 | 5 || 3 | 1 | 9\n" +
						  "-----------------------------------\n" +
						  "3 | 9 | 8 || 1 | 2 | 4 || 6 | 7 | 5\n" +
						  "-----------------------------------\n" +
						  "6 | 5 | 1 || 7 | 3 | 9 || 8 | 4 | 2\n" +
						  "===================================\n" +
						  "8 | 1 | 9 || 3 | 4 | 2 || 5 | 6 | 7\n" +
						  "-----------------------------------\n" +
						  "7 | 6 | 5 || 9 | 1 | 8 || 2 | 3 | 4\n" +
						  "-----------------------------------\n" +
						  "4 | 3 | 2 || 6 | 5 | 7 || 9 | 8 | 1\n";

		assertEquals(expected, board.toString());
	}

	/**
	 * Test that a board can be created without an initial layout
	 */
	@Test
	void createEmptyBoard()
	{

		SudokuBoard board = new SudokuBoard();

		String expected = "  |   |   ||   |   |   ||   |   |  \n" +
						  "-----------------------------------\n" +
						  "  |   |   ||   |   |   ||   |   |  \n" +
						  "-----------------------------------\n" +
						  "  |   |   ||   |   |   ||   |   |  \n" +
						  "===================================\n" +
						  "  |   |   ||   |   |   ||   |   |  \n" +
						  "-----------------------------------\n" +
						  "  |   |   ||   |   |   ||   |   |  \n" +
						  "-----------------------------------\n" +
						  "  |   |   ||   |   |   ||   |   |  \n" +
						  "===================================\n" +
						  "  |   |   ||   |   |   ||   |   |  \n" +
						  "-----------------------------------\n" +
						  "  |   |   ||   |   |   ||   |   |  \n" +
						  "-----------------------------------\n" +
						  "  |   |   ||   |   |   ||   |   |  \n";

		assertEquals(expected, board.toString());
	}

	/**
	 * Test that the board will throw an exception should it be created with an invalid layout.
	 */
	@Test
	void rejectBoardWithInvalidLayout()
	{
		String boardLayout = "1 1 1 1 1 1 1 1 1 " +
							 "5 2 6 4 7 3 1 9 8 " +
							 "1 8 4 2 9 6 7 5 3 " +
							 "2 4 7 8 6 5 3 1 9 " +
							 "3 9 8 1 2 4 6 7 5 " +
							 "6 5 1 7 3 9 8 4 2 " +
							 "8 1 9 3 4 2 5 6 7 " +
							 "7 6 5 9 1 8 2 3 4 " +
							 "4 3 2 6 5 7 9 8 1 ";

		Exception exception = assertThrows(IllegalArgumentException.class, () -> new SudokuBoard(boardLayout));
		assertEquals("Invalid layout given to SudokuBoard.", exception.getMessage());
	}

	/**
	 * Test that the board will throw an exception should it be created with values outside of the
	 */
	@Test
	void rejectBoardWithOutOfRangeSquares()
	{
		String boardLayout = "-1 7 3 5 8 1 4 2 6 " +
							 "5 2 6 4 7 3 1 9 8 " +
							 "1 8 4 2 9 6 7 5 3 " +
							 "2 4 7 8 6 5 3 1 9 " +
							 "3 9 8 1 2 4 6 7 5 " +
							 "6 5 1 7 3 9 8 4 2 " +
							 "8 1 9 3 4 2 5 6 7 " +
							 "7 6 5 9 1 8 2 0 4 " +
							 "4 3 2 6 5 7 9 8 1 ";

		Exception exception = assertThrows(IllegalArgumentException.class, () -> new SudokuBoard(boardLayout));
		assertEquals("Sudoku board can only contain values from 0-9.", exception.getMessage());
	}

	/**
	 * Test that an exception is thrown when a boars is created with a layout of the wrong length
	 */
	@Test
	void rejectBoardWithIncorrectLength()
	{
		String boardLayoutTooLong = "9 7 3 5 8 1 4 2 6 " +
							 "5 2 6 4 7 3 1 9 8 " +
							 "1 8 4 2 9 6 7 5 3 " +
							 "2 4 7 8 6 5 3 1 9 " +
							 "3 9 8 1 2 4 6 7 5 " +
							 "6 5 1 7 3 9 8 4 2 " +
							 "8 1 9 3 4 2 5 6 7 " +
							 "7 6 5 9 1 8 2 3 4 " +
							 "4 3 2 6 5 7 9 8 1 " +
							 "2 5 3 6 7 8 4 9 1 ";

		String boardLayoutTooShort = "9 7 3 5 8 1 4 2 6 " +
							 "5 2 6 4 7 3 1 9 8 " +
							 "1 8 4 2 9 6 7 5 3 " +
							 "2 4 7 8 6 5 3 1 9 " +
							 "3 9 8 1 2 4 6 7 5 " +
							 "6 5 1 7 3 9 8 4 2 " +
							 "8 1 9 3 4 2 5 6 7 " +
							 "7 6 5 9 1 8 2 3 4 ";
		Exception exception;

		exception = assertThrows(IllegalArgumentException.class, () -> new SudokuBoard(boardLayoutTooLong));
		assertEquals("The board layout must be exactly 81 numbers. It is too long.", exception.getMessage());

		exception = assertThrows(IllegalArgumentException.class, () -> new SudokuBoard(boardLayoutTooShort));
		assertEquals("The board layout must be exactly 81 numbers. It is too short.", exception.getMessage());
	}

	/**
	 * Test that an exception is thrown when a board layout containing non-numerical characters is given.
	 */
	@Test
	void rejectBoardLayoutContainingText()
	{
		String boardLayout = "t e s t t e s t t " +
							 "e s t t e s t t e " +
							 "s t t e s t t e s " +
							 "t t e s t t e s t " +
							 "t e s t t e s t t " +
							 "e s t t e s t t e " +
							 "s t t e s t t e s " +
							 "t t e s t t e s t " +
							 "t e s t t e s t t";

		assertThrows(NumberFormatException.class, () -> new SudokuBoard(boardLayout));
	}
}