package sudoku;

import nl.concipit.sudoku.SudokuGridBuilder;
import nl.concipit.sudoku.exception.IllegalGridInputException;
import nl.concipit.sudoku.model.SudokuGrid;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class SudokuGridBuilderTest {
    @Test(expected = IllegalGridInputException.class)
    public void testEmptyInput() throws IllegalGridInputException {
        SudokuGridBuilder.buildGrid(IOUtils.toInputStream(""));
        Assert.fail();
    }

    @Test(expected = IllegalGridInputException.class)
    public void testNullInput() throws IllegalGridInputException {
        SudokuGridBuilder.buildGrid(null);
        Assert.fail();
    }

    @Test
    public void testValidInputSingleLineEmpty()
            throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(";1;|;;3|;;\n;;|;;|;;\n;;|;;|;;"));
        Assert.assertEquals(9, grid.getGridSize());
        Assert.assertEquals(Integer.valueOf(1), grid.getCell(1, 0).getValue());
        Assert.assertEquals(Integer.valueOf(3), grid.getCell(5, 0).getValue());
    }

    @Test
    public void testValidInputMultiLineEmpty() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(";1;|;;3|;;\n3;2;1|6;5;4|;;\n;;|;;|;;"));
        Assert.assertEquals(9, grid.getGridSize());
        Assert.assertEquals(Integer.valueOf(1), grid.getCell(1, 0).getValue());
        Assert.assertEquals(Integer.valueOf(3), grid.getCell(5, 0).getValue());
        Assert.assertEquals(Integer.valueOf(4), grid.getCell(5, 1).getValue());
        Assert.assertEquals(Integer.valueOf(3), grid.getCell(0, 1).getValue());
    }

    @Test(expected = IllegalGridInputException.class)
    public void testInvalidLineGridWidthLast() throws IllegalGridInputException {
        SudokuGridBuilder.buildGrid(IOUtils.toInputStream(";1;|;3"));
        Assert.fail();
    }

    @Test(expected = IllegalGridInputException.class)
    public void testInvalidLineGridWidthFirst()
            throws IllegalGridInputException {
        SudokuGridBuilder.buildGrid(IOUtils.toInputStream(";1|;3;1"));
        Assert.fail();
    }

    @Test(expected = IllegalGridInputException.class)
    public void testInvalidLineGridMulti() throws IllegalGridInputException {
        SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(";1;|;3;|;;;\n;;;|1;3;|;;;\n1;3|1;"));
        Assert.fail();
    }

    @Test(expected = IllegalGridInputException.class)
    public void testInvalidSegmentCount() throws IllegalGridInputException {
        SudokuGridBuilder.buildGrid(IOUtils.toInputStream(";1;3;3;\n;;;|1;3"));
        Assert.fail();
    }

    @Test
    public void testSingleSegments() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(";1;3;2;\n;;;1;3\n;;;;\n;;;;\n;;;;"));
        Assert.assertEquals(Integer.valueOf(3), grid.getCell(2, 0).getValue());
        Assert.assertEquals(Integer.valueOf(1), grid.getCell(3, 1).getValue());
        Assert.assertNull(grid.getCell(1, 1).getValue());
    }

    @Test
    public void testIgnoreWhiteLines() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("\n1;2;3\n\n2;3;1\n;;\n\n"));
        Assert.assertEquals(3, grid.getGridSize());
        Assert.assertEquals(3, grid.getSegmentSize());
    }

    @Test
    public void testWhiteSpaceTrimmed() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("\n   1; 2     ; 3 \n 2 ; 3 ;       1 \n ; ;\n\n"));
        Assert.assertEquals(3, grid.getGridSize());
        Assert.assertEquals(3, grid.getSegmentSize());
        Assert.assertEquals(Integer.valueOf(1), grid.getCell(2, 1).getValue());
    }

    @Test(expected = IllegalGridInputException.class)
    public void testIllegalCellValue() throws IllegalGridInputException {
        SudokuGridBuilder.buildGrid(IOUtils.toInputStream("1;2;10"));
        Assert.fail();
    }

    @Test(expected = IllegalGridInputException.class)
    public void testDoubleValueInRow() throws IllegalGridInputException {
        SudokuGridBuilder.buildGrid(IOUtils.toInputStream("1;2;1"));
        Assert.fail();
    }

    @Test(expected = IllegalGridInputException.class)
    public void testDoubleValueInColumn() throws IllegalGridInputException {
        SudokuGridBuilder.buildGrid(IOUtils.toInputStream("1;2;3\n1;3;2"));
        Assert.fail();
    }
}
