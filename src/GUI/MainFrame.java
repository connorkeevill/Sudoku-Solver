package GUI;

import sudoku.SudokuBoard;
import sudoku.SudokuSolver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Frame containing a sudoku panel and a solve button.
 */
public class MainFrame extends JFrame {
	SudokuBoardPanel boardPanel;

	public MainFrame()
	{
		super("Sudoku Solver");

		int panelSize = 500;

		// Create the panel which holds the board
		boardPanel = new SudokuBoardPanel(panelSize);
		add(boardPanel);

		// Create the solve button
		JButton solveButton = new JButton("Solve");
		solveButton.setFont(new Font(null, Font.PLAIN, 32));
		solveButton.addActionListener(this::solveSudoku);
		solveButton.setPreferredSize(new Dimension(panelSize, 50));
		solveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(solveButton);

		// Set the frame properties
		getContentPane().setPreferredSize(new Dimension(panelSize, panelSize + 50));
		pack();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Event handler that solves the sudoku.
	 * @param e the event.
	 */
	private void solveSudoku(ActionEvent e)
	{
		String layout = boardPanel.getLayoutString();
		SudokuBoard board = new SudokuBoard(layout);

		SudokuSolver solver = new SudokuSolver(board);
		solver.solve();

		SudokuBoard solved = solver.getBoard();

		MainFrame solvedFrame = new MainFrame();
		solvedFrame.boardPanel.setBoard(solved);
	}
}
