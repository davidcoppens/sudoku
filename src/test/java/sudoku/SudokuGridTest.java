package sudoku;

import java.util.Arrays;

import nl.concipit.sudoku.SudokuGridBuilder;
import nl.concipit.sudoku.exception.IllegalGridInputException;
import nl.concipit.sudoku.model.SudokuCell;
import nl.concipit.sudoku.model.SudokuGrid;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class SudokuGridTest {

    private static final int GRID_SIZE = 9;
    private static final int SEGMENT_SIZE = 3;

    // class under test
    private final SudokuGrid grid = new SudokuGrid(GRID_SIZE, SEGMENT_SIZE);

    @Test
    public void testSelectValidCellBounds() {
        SudokuCell cell = grid.getCell(0, 0);
        Assert.assertNotNull(cell);
        Assert.assertNotNull(grid.getCell(grid.getGridSize() - 1,
                grid.getGridSize() - 1));
    }

    @Test
    public void testAllCells() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
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
        int size = grid.getGridSize();
        SudokuCell cell = grid.getCell(size, size);
        Assert.assertNull(cell);
    }

    @Test
    public void testGridDimensions() {
        Assert.assertEquals(GRID_SIZE, grid.getGridSize());
    }

    @Test
    public void testGetSegmentSize() {
        Assert.assertEquals(SEGMENT_SIZE, grid.getSegmentSize());
    }

    @Test
    public void testSelectInvalidCellLowerBound() {
        Assert.assertNull(grid.getCell(-1, -1));
    }

    @Test
    public void testSelectInvalidCellMixedBound() {
        Assert.assertNull(grid.getCell(0, -1));
        Assert.assertNull(grid.getCell(-1, 0));
        Assert.assertNull(grid.getCell(0, grid.getGridSize()));
        Assert.assertNull(grid.getCell(grid.getGridSize(), 0));
        Assert.assertNull(grid.getCell(grid.getGridSize(), -1));
        Assert.assertNull(grid.getCell(-1, grid.getGridSize()));
    }

    @Test
    public void testGetNumberOfSegments() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(";;\n;;\n;;"));
        Assert.assertEquals(1, grid.getNumberOfSegments());
        
        grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(";;|;;|;;\n;;|;;|;;\n;;|;;|;;"));
        Assert.assertEquals(2, grid.getNumberOfSegments());        
    }

    @Test
    public void testSetCell() {
        SudokuCell cell = new SudokuCell();
        grid.setCell(0, 0, cell);
        Assert.assertEquals(cell, grid.getCell(0, 0));
    }

    @Test
    public void testCompleteRow() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;2;3"));
        Assert.assertTrue(grid.isCompleteRow(0));
    }

    @Test
    public void testIncompleteRow() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;;3\n;;\n;; "));
        Assert.assertFalse(grid.isCompleteRow(0));
    }

    @Test
    public void testCompleteColumn() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;2;3\n2;3;1\n3;1;2"));
        Assert.assertTrue(grid.isCompleteColumn(0));
    }

    @Test
    public void testIncompleteColumn() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;2;3\n2;3;1\n;1;2"));
        Assert.assertFalse(grid.isCompleteColumn(0));
    }

    @Test
    public void testMissingInRowAll() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(";;\n;;\n;;"));
        Assert.assertEquals(Arrays.asList(1, 2, 3), grid.getMissingInRow(0));
        Assert.assertEquals(Arrays.asList(1, 2, 3), grid.getMissingInRow(1));
        Assert.assertEquals(Arrays.asList(1, 2, 3), grid.getMissingInRow(2));
    }

    @Test
    public void testMissingInRowNone() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;2;3\n2;3;1\n3;1;2"));
        Assert.assertEquals(Arrays.asList(), grid.getMissingInRow(0));
        Assert.assertEquals(Arrays.asList(), grid.getMissingInRow(1));
        Assert.assertEquals(Arrays.asList(), grid.getMissingInRow(2));
    }

    @Test
    public void testMissingInRowSome() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;;3\n2;3;1\n;1;"));
        Assert.assertEquals(Arrays.asList(2), grid.getMissingInRow(0));
        Assert.assertEquals(Arrays.asList(), grid.getMissingInRow(1));
        Assert.assertEquals(Arrays.asList(2, 3), grid.getMissingInRow(2));
    }

    @Test
    public void testMissingInColumnAll() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(";;\n;;\n;;"));
        Assert.assertEquals(Arrays.asList(1, 2, 3), grid.getMissingInColumn(0));
        Assert.assertEquals(Arrays.asList(1, 2, 3), grid.getMissingInColumn(1));
        Assert.assertEquals(Arrays.asList(1, 2, 3), grid.getMissingInColumn(2));
    }

    @Test
    public void testMissingInColumnNone() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;2;3\n2;3;1\n3;1;2"));
        Assert.assertEquals(Arrays.asList(), grid.getMissingInColumn(0));
        Assert.assertEquals(Arrays.asList(), grid.getMissingInColumn(1));
        Assert.assertEquals(Arrays.asList(), grid.getMissingInColumn(2));
    }

    @Test
    public void testMissingInColumnSome() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;2;3\n;3;\n3;1;"));
        Assert.assertEquals(Arrays.asList(2), grid.getMissingInColumn(0));
        Assert.assertEquals(Arrays.asList(), grid.getMissingInColumn(1));
        Assert.assertEquals(Arrays.asList(1, 2), grid.getMissingInColumn(2));
    }

    @Test
    public void testMissingInSegmentNone() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;2;3\n2;3;1\n3;1;2"));
        Assert.assertEquals(Arrays.asList(), grid.getMissingInSegment(0, 0));
    }
}
