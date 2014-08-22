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
                .toInputStream(";|;\n;|;\n;|;"));
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
    public void testValuesInRowNone() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(";;\n;;\n;;"));
        Assert.assertEquals(Arrays.asList(), grid.getValuesInRow(0));
        Assert.assertEquals(Arrays.asList(), grid.getValuesInRow(1));
        Assert.assertEquals(Arrays.asList(), grid.getValuesInRow(2));
    }

    @Test
    public void testValuesInRowAll() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;2;3\n2;3;1\n3;1;2"));
        Assert.assertEquals(Arrays.asList(1,2,3), grid.getValuesInRow(0));
        Assert.assertEquals(Arrays.asList(1,2,3), grid.getValuesInRow(1));
        Assert.assertEquals(Arrays.asList(1,2,3), grid.getValuesInRow(2));
    }

    @Test
    public void testValuesInRowSome() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;;3\n2;3;1\n;1;"));
        Assert.assertEquals(Arrays.asList(1,3), grid.getValuesInRow(0));
        Assert.assertEquals(Arrays.asList(1,2,3), grid.getValuesInRow(1));
        Assert.assertEquals(Arrays.asList(1), grid.getValuesInRow(2));
    }
    

    @Test
    public void testValuesInColumnNone() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(";;\n;;\n;;"));
        Assert.assertEquals(Arrays.asList(), grid.getValuesInColumn(0));
        Assert.assertEquals(Arrays.asList(), grid.getValuesInColumn(1));
        Assert.assertEquals(Arrays.asList(), grid.getValuesInColumn(2));
    }

    @Test
    public void testValuesInColumnAll() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;2;3\n2;3;1\n3;1;2"));
        Assert.assertEquals(Arrays.asList(1,2,3), grid.getValuesInColumn(0));
        Assert.assertEquals(Arrays.asList(1,2,3), grid.getValuesInColumn(1));
        Assert.assertEquals(Arrays.asList(1,2,3), grid.getValuesInColumn(2));
    }

    @Test
    public void testValuesInColumnSome() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;;3\n2;3;\n;1;"));
        Assert.assertEquals(Arrays.asList(1,2), grid.getValuesInColumn(0));
        Assert.assertEquals(Arrays.asList(1,3), grid.getValuesInColumn(1));
        Assert.assertEquals(Arrays.asList(3), grid.getValuesInColumn(2));
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
                .toInputStream("1;2;3\n4;5;6\n7;8;9"));
        Assert.assertEquals(Arrays.asList(), grid.getMissingInSegment(0, 0));
    }
    
    @Test
    public void testGetOtherRowsInSegment() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;2;3\n2;3;1\n3;1;2"));
        Assert.assertEquals(Arrays.asList(0,2), grid.getOtherRowsInSegment(1));
    }
    
    @Test
    public void testGetOtherRowsInSegmentFirst() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;2;3\n2;3;1\n3;1;2"));
        Assert.assertEquals(Arrays.asList(1,2), grid.getOtherRowsInSegment(0));
    }
    
    @Test
    public void testGetOtherRowsInSegmentLast() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;2;3\n2;3;1\n3;1;2"));
        Assert.assertEquals(Arrays.asList(0,1), grid.getOtherRowsInSegment(2));
    }    
    
    @Test(expected=IllegalArgumentException.class)
    public void testGetOtherRowsInSegmentUpperBound() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;2;3\n2;3;1\n3;1;2"));
        grid.getOtherRowsInSegment(3);
        Assert.fail();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testGetOtherRowsInSegmentLowerBound() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;2;3\n2;3;1\n3;1;2"));
        grid.getOtherRowsInSegment(-1);
        Assert.fail();
    }    
 
    @Test
    public void testGetOtherColumnInSegment() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;2;3\n2;3;1\n3;1;2"));
        Assert.assertEquals(Arrays.asList(0,2), grid.getOtherColumnsInSegment(1));
    }
    
    @Test
    public void testGetOtherColumnsInSegmentFirst() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;2;3\n2;3;1\n3;1;2"));
        Assert.assertEquals(Arrays.asList(1,2), grid.getOtherColumnsInSegment(0));
    }
    
    @Test
    public void testGetOtherColumnsInSegmentLast() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;2;3\n2;3;1\n3;1;2"));
        Assert.assertEquals(Arrays.asList(0,1), grid.getOtherColumnsInSegment(2));
    }    
    
    @Test(expected=IllegalArgumentException.class)
    public void testGetOtherColumnsInSegmentUpperBound() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;2;3\n2;3;1\n3;1;2"));
        grid.getOtherColumnsInSegment(3);
        Assert.fail();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testGetOtherColumnsInSegmentLowerBound() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;2;3\n2;3;1\n3;1;2"));
        grid.getOtherColumnsInSegment(-1);
        Assert.fail();
    } 
    @Test
    public void testMissingInSegmentAll() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(";;\n;;\n;;"));
        Assert.assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9),
                grid.getMissingInSegment(0, 0));
    }

    @Test
    public void testMissingInSegmentSome() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;;3\n;5;6\n;8;"));
        Assert.assertEquals(Arrays.asList(2, 4, 7, 9),
                grid.getMissingInSegment(0, 0));
    }

    @Test
    public void testGridComplete() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;2;3\n4;5;6\n7;8;9"));
        Assert.assertTrue(grid.isComplete());
    }

    @Test
    public void testToStringOneSegment() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;2;3\n2;3;1\n3;1;2"));
        StringBuffer builder = new StringBuffer();
        builder.append("*************").append(
                IOUtils.LINE_SEPARATOR);

        builder.append("* 1 | 2 | 3 *").append(IOUtils.LINE_SEPARATOR);
        builder.append("*-----------*").append(IOUtils.LINE_SEPARATOR);        
        builder.append("* 2 | 3 | 1 *").append(IOUtils.LINE_SEPARATOR);
        builder.append("*-----------*").append(IOUtils.LINE_SEPARATOR);
        builder.append("* 3 | 1 | 2 *").append(IOUtils.LINE_SEPARATOR);
        builder.append("*************").append(IOUtils.LINE_SEPARATOR);
         
        Assert.assertEquals(builder.toString(), grid.toString());
    }
    
    @Test
    public void testToStringMultiSegment() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;2|3;4\n;|1;2\n3;1|;\n;|;"));
        StringBuffer builder = new StringBuffer();
        builder.append("*****************").append(
                IOUtils.LINE_SEPARATOR);

        builder.append("* 1 | 2 * 3 | 4 *").append(IOUtils.LINE_SEPARATOR);
        builder.append("*---------------*").append(IOUtils.LINE_SEPARATOR);
        builder.append("*   |   * 1 | 2 *").append(IOUtils.LINE_SEPARATOR);
        builder.append("*****************").append(IOUtils.LINE_SEPARATOR);
        builder.append("* 3 | 1 *   |   *").append(IOUtils.LINE_SEPARATOR);
        builder.append("*---------------*").append(IOUtils.LINE_SEPARATOR);
        builder.append("*   |   *   |   *").append(IOUtils.LINE_SEPARATOR);
        builder.append("*****************").append(
                IOUtils.LINE_SEPARATOR);
        
        Assert.assertEquals(builder.toString(), grid.toString());
    }
}
