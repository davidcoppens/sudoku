package sudoku;

import java.util.Arrays;

import nl.concipit.sudoku.model.SudokuCell;
import nl.concipit.sudoku.model.SudokuSegment;

import org.junit.Assert;
import org.junit.Test;

public class SudokuSegmentTest {
	private static final int WIDTH = 2;
	private static final int HEIGHT = 3;
	
	@Test
	public void testGetMissingValuesAll() {
		SudokuSegment segment = new SudokuSegment(WIDTH, HEIGHT);
		Assert.assertEquals(Arrays.asList(1,2,3,4,5,6), segment.getMissingValues());
	}
	
	@Test
	public void testGetMissingValuesNone() {
		SudokuSegment segment = new SudokuSegment(WIDTH, HEIGHT);
		for (int row = 0; row < HEIGHT; row++) {
			for (int col = 0; col < WIDTH; col++) {
				int value = (row * WIDTH) + col + 1;
				segment.setCell(col, row, new SudokuCell(value));
			}
		}
		Assert.assertEquals(Arrays.asList(), segment.getMissingValues());
	}
	
	@Test
	public void testSegmentSize() {
		Assert.assertEquals(WIDTH, new SudokuSegment(WIDTH, HEIGHT).getWidth());
		Assert.assertEquals(HEIGHT, new SudokuSegment(WIDTH, HEIGHT).getHeight());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetCellOutOfBounds() {
		new SudokuSegment(WIDTH, HEIGHT).setCell(-1, -1, new SudokuCell());
		Assert.fail();
	}
	
	@Test
	public void testGetCell() {
		SudokuSegment segment = new SudokuSegment(WIDTH, HEIGHT);
		SudokuCell cell = new SudokuCell();
		segment.setCell(0, 0, cell);
		Assert.assertEquals(cell, segment.getCell(0, 0));
	}
	
	@Test
	public void testGetCellOutOfBound() {
		SudokuSegment segment = new SudokuSegment(WIDTH, HEIGHT);
		Assert.assertNull(segment.getCell(-1, -1));
		Assert.assertNull(segment.getCell(-1, 0));
		Assert.assertNull(segment.getCell(0, -1));
		Assert.assertNull(segment.getCell(WIDTH + 1, HEIGHT + 1));
	}
}
