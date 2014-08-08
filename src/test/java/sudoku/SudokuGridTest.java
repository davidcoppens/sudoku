package sudoku;

import nl.concipit.sudoku.model.SudokuCell;
import nl.concipit.sudoku.model.SudokuGrid;

import org.junit.Assert;
import org.junit.Test;


public class SudokuGridTest {

	private static final int GRID_WIDTH = 9;
	private static final int GRID_HEIGHT = 9;
	
	// class under test
	private final SudokuGrid grid = new SudokuGrid(GRID_WIDTH, GRID_HEIGHT);
	
	@Test
	public void testSelectValidCellBounds() {
		SudokuCell cell = grid.getCell(0, 0);
		Assert.assertNotNull(cell);
		Assert.assertNotNull(grid.getCell(grid.getTotalWidth() - 1, grid.getTotalHeight() - 1));
	}
	
	@Test
	public void testAllCells() {
		for (int i = 0; i < GRID_WIDTH; i++) {
			for (int j = 0; j < GRID_HEIGHT; j++) {
				Assert.assertNotNull(grid.getCell(i, j));
			}
		}
	}
	
	@Test
	public void testSelectSegment() {
		Assert.assertNotNull(grid.getSegment(0, 0));
	}
	
	@Test 
	public void testSelectInvalidCellUpperBound() {
		int sudokuWidth = grid.getTotalWidth();
		int sudokuHeight = grid.getTotalHeight();
		SudokuCell cell = grid.getCell(sudokuWidth, sudokuHeight);
		Assert.assertNull(cell);
	}
	
	@Test
	public void testGridDimensions() {
		Assert.assertEquals(GRID_WIDTH, grid.getTotalWidth());
		Assert.assertEquals(GRID_HEIGHT, grid.getTotalHeight());
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
	
	@Test
	public void testSetCell() {
		SudokuCell cell = new SudokuCell();
		grid.setCell(0, 0, cell);
		Assert.assertEquals(cell, grid.getCell(0, 0));
	}
}
