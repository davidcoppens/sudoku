package nl.concipit.sudoku.model;

import java.util.Arrays;

import nl.concipit.sudoku.model.SudokuCell;
import nl.concipit.sudoku.model.SudokuSegment;

import org.junit.Assert;
import org.junit.Test;

public class SudokuSegmentTest {
	private static final int SIZE = 3;

	@Test
	public void testGetMissingValuesAll() {
		SudokuSegment segment = new SudokuSegment(SIZE);
		Assert.assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9),
				segment.getMissingValues());
	}

	@Test
	public void testGetMissingValuesNone() {
		SudokuSegment segment = new SudokuSegment(SIZE);
		fillSegment(segment);
		Assert.assertEquals(Arrays.asList(), segment.getMissingValues());
	}

	@Test
	public void testIsCompleteEmpty() {
		SudokuSegment segment = new SudokuSegment(SIZE);
		Assert.assertEquals(false, segment.isComplete());
	}

	@Test
	public void testIsCompleteFull() {
		SudokuSegment segment = new SudokuSegment(SIZE);
		fillSegment(segment);
		Assert.assertEquals(true, segment.isComplete());
	}
	
	@Test
	public void testIsCompleteSomeFilled() {
		SudokuSegment segment = new SudokuSegment(SIZE);
		segment.setCell(0, 0, new SudokuCell(4));
		Assert.assertEquals(false, segment.isComplete());
	}

	@Test
	public void testSegmentSize() {
		Assert.assertEquals(SIZE, new SudokuSegment(SIZE).getSize());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetCellOutOfBounds() {
		new SudokuSegment(SIZE).setCell(-1, -1, new SudokuCell());
		Assert.fail();
	}

	@Test
	public void testGetCell() {
		SudokuSegment segment = new SudokuSegment(SIZE);
		SudokuCell cell = new SudokuCell();
		segment.setCell(0, 0, cell);
		Assert.assertEquals(cell, segment.getCell(0, 0));
	}

	@Test
	public void testGetCellOutOfBound() {
		SudokuSegment segment = new SudokuSegment(SIZE);
		Assert.assertNull(segment.getCell(-1, -1));
		Assert.assertNull(segment.getCell(-1, 0));
		Assert.assertNull(segment.getCell(0, -1));
		Assert.assertNull(segment.getCell(SIZE + 1, SIZE + 1));
	}

	/**
	 * Fills the segment with all values
	 * 
	 * @param segment
	 *            Segment to fill
	 */
	private void fillSegment(SudokuSegment segment) {
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				int value = (row * SIZE) + col + 1;
				segment.setCell(col, row, new SudokuCell(value));
			}
		}
	}
}
