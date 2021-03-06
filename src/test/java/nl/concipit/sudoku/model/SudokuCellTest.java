package nl.concipit.sudoku.model;

import nl.concipit.sudoku.model.SudokuCell;

import org.junit.Assert;
import org.junit.Test;

public class SudokuCellTest {

    @Test
    public void testValue() {
        SudokuCell cell = new SudokuCell(0, 0, 5);
        Assert.assertEquals(new Integer(5), cell.getValue());
    }

    @Test
    public void testDefaultConstructor() {
        SudokuCell cell = new SudokuCell(0, 0);
        Assert.assertNull(cell.getValue());
    }

    @Test
    public void testToStringNull() {
        SudokuCell cell = new SudokuCell(0, 0);
        Assert.assertEquals("[ ]", cell.toString());
    }

    @Test
    public void testToStringValue() {
        SudokuCell cell = new SudokuCell(0, 0, new Integer(2));
        Assert.assertEquals("[2]", cell.toString());
    }
}
