package sudoku;

import nl.concipit.sudoku.model.SudokuCell;
import nl.concipit.sudoku.model.SudokuGrid;

import org.junit.Assert;
import org.junit.Test;


public class SudokuGridTest {

	private SudokuGrid grid  = new SudokuGrid();
	
	@Test
	public void testSelectValidCell() {
		SudokuCell cell = grid.getCell(0, 0);
		Assert.assertNotNull(cell);
	}
	
	@Test 
	public void testSelectInvalidCell() {
		int sudokuWidth = grid.getTotalWidth();
		int sudokuHeight = grid.getTotalHeight();
		SudokuCell cell = grid.getCell(sudokuWidth, sudokuHeight);
		Assert.assertNull(cell);
	}
}
