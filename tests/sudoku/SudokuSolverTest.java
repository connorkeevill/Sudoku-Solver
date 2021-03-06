package sudoku;

import org.junit.jupiter.api.Test;
import sudoku.SudokuBoard;
import sudoku.SudokuSolver;

import static org.junit.jupiter.api.Assertions.*;

class SudokuSolverTest {

	@Test
	void solveSolvableBoard() {
		String layout = "9 1 0 7 0 0 0 0 0 " +
						"0 3 2 6 0 9 0 8 0 " +
						"0 0 7 0 8 0 9 0 0 " +
						"0 8 6 0 3 0 1 7 0 " +
						"3 0 0 0 0 0 0 0 6 " +
						"0 5 1 0 2 0 8 4 0 " +
						"0 0 9 0 5 0 3 0 0 " +
						"0 2 0 3 0 1 4 9 0 " +
						"0 0 0 0 0 2 0 6 1 ";

		SudokuSolver solver = new SudokuSolver(new SudokuBoard(layout));
		solver.solve();

		SudokuBoard solved = solver.getBoard();

		assertTrue(solved.isValidAndComplete());
	}
}