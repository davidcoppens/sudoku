package sudoku;

import nl.concipit.sudoku.model.SudokuCell;

import org.junit.Assert;
import org.junit.Test;

public class SudokuCellTest {

	@Test
	public void testValue() {
		SudokuCell cell = new SudokuCell(5);
		Assert.assertEquals(new Integer(5), cell.getValue());
	}
	
	@Test 
	public void testDefaultConstructor() {
		SudokuCell cell = new SudokuCell();
		Assert.assertNull(cell.getValue());
	}

}
