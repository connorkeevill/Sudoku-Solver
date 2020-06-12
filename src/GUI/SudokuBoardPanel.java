package GUI;

import sudoku.SudokuBoard;

import javax.swing.*;
import java.awt.*;

/**
 * Panel which makes a sudoku board.
 */
public class SudokuBoardPanel extends JPanel {
	SudokuSquareTextField[] board;

	public SudokuBoardPanel(int size)
	{
		board = new SudokuSquareTextField[81];

		setBackground(Color.WHITE);
		createSudokuGrid();

		setPreferredSize(new Dimension(size, size));
		setLayout(new GridLayout(9, 9, 15, 15));
	}

	/**
	 * Creates the grid of SudokuSquareTextField()s.
	 */
	private void createSudokuGrid()
	{
		for(int square = 0; square < board.length; square++)
		{
			SudokuSquareTextField newSquare = new SudokuSquareTextField();

			add(newSquare);
			board[square] = newSquare;
		}
	}

	/**
	 * Gets the layout string of the board based on each of the text boxes.
	 * @return the layout string.
	 */
	public String getLayoutString() {
		StringBuilder output = new StringBuilder();

		for(SudokuSquareTextField square : board)
		{
			output.append(square.getText());
			output.append(" ");
		}

		return output.toString();
	}

	/**
	 * Sets the board layout to that of the given board.
	 * @param board the board whose layout to use.
	 */
	public void setBoard(SudokuBoard board)
	{
		String layout = board.getLayoutString();
		for(int square = 0; square < this.board.length; square++)
		{
			this.board[square].setText(Character.toString(layout.charAt(square * 2)));
		}
	}
}
