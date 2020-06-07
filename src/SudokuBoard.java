public class SudokuBoard {
	int[] board;

	public SudokuBoard() {
		board = new int[81];
	}

	public SudokuBoard(int[] initialLayout)
	{
		board = initialLayout;
	}
}
