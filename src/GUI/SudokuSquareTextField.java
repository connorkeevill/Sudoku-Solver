package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Text field for each sqaure on the board.
 */
public class SudokuSquareTextField extends JTextField
{
	public SudokuSquareTextField()
	{
		Font font = new Font(null, Font.PLAIN, 32);

		setHorizontalAlignment(CENTER);
		setFont(font);
		setText("0");

		attachFocusEvent();
	}

	/**
	 * Adds the focus event handler to the text field.
	 */
	private void attachFocusEvent()
	{
		addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				SudokuSquareTextField thisBox = (SudokuSquareTextField)e.getComponent();
				thisBox.selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {

			}
		});
	}
}
