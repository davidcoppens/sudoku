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
	public void testSelectInvalidCellUpperBound() {
		int sudokuWidth = grid.getTotalWidth();
		int sudokuHeight = grid.getTotalHeight();
		SudokuCell cell = grid.getCell(sudokuWidth, sudokuHeight);
		Assert.assertNull(cell);
	}
	
	@Test
	public void testSelectInvalidCellLowerBound() {
		Assert.assertNull(grid.getCell(-1, -1));
	}
	
	@Test
	public void testSelectInvalidCellMixedBound() {
		Assert.assertNull(grid.getCell(0, -1));
		Assert.assertNull(grid.getCell(-1, 0));
		Assert.assertNull(grid.getCell(0, grid.getTotalHeight()));
		Assert.assertNull(grid.getCell(grid.getTotalWidth(), 0));
		Assert.assertNull(grid.getCell(grid.getTotalWidth(), -1));
		Assert.assertNull(grid.getCell(-1, grid.getTotalHeight()));
	}
}
