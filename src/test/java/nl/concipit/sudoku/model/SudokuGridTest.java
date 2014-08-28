package nl.concipit.sudoku.model;

import java.util.Arrays;

import nl.concipit.sudoku.SudokuGridBuilder;
import nl.concipit.sudoku.exception.IllegalGridInputException;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class SudokuGridTest {

    private static final String SIMPLE_GRID = "1;2;3\n2;3;1\n3;1;2";
    private static final String EMPTY_GRID = ";;\n;;\n;;";
    private static final int GRID_SIZE = 9;
    private static final int SEGMENT_SIZE = 3;

    @Test
    public void testSelectValidCellBounds() {
        // class under test
        SudokuGrid grid = new SudokuGrid(GRID_SIZE, SEGMENT_SIZE);

        SudokuCell cell = grid.getCell(0, 0);
        Assert.assertNotNull(cell);
        Assert.assertNotNull(grid.getCell(grid.getGridSize() - 1,
                grid.getGridSize() - 1));
    }

    @Test
    public void testAllCells() {
        SudokuGrid grid = new SudokuGrid(GRID_SIZE, SEGMENT_SIZE);

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                Assert.assertNotNull(grid.getCell(i, j));
            }
        }
    }

    @Test
    public void testSelectSegment() {
        SudokuGrid grid = new SudokuGrid(GRID_SIZE, SEGMENT_SIZE);

        Assert.assertNotNull(grid.getSegment(0, 0));
    }

    @Test
    public void testSelectInvalidCellUpperBound() {
        SudokuGrid grid = new SudokuGrid(GRID_SIZE, SEGMENT_SIZE);

        int size = grid.getGridSize();
        SudokuCell cell = grid.getCell(size, size);
        Assert.assertNull(cell);
    }

    @Test
    public void testGridDimensions() {
        SudokuGrid grid = new SudokuGrid(GRID_SIZE, SEGMENT_SIZE);

        Assert.assertEquals(GRID_SIZE, grid.getGridSize());
    }

    @Test
    public void testGetSegmentSize() {
        SudokuGrid grid = new SudokuGrid(GRID_SIZE, SEGMENT_SIZE);

        Assert.assertEquals(SEGMENT_SIZE, grid.getSegmentSize());
    }

    @Test
    public void testSelectInvalidCellLowerBound() {
        SudokuGrid grid = new SudokuGrid(GRID_SIZE, SEGMENT_SIZE);

        Assert.assertNull(grid.getCell(-1, -1));
    }

    @Test
    public void testSelectInvalidCellMixedBound() {
        SudokuGrid grid = new SudokuGrid(GRID_SIZE, SEGMENT_SIZE);

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
                .toInputStream(EMPTY_GRID));
        Assert.assertEquals(1, grid.getNumberOfSegments());

        grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(";|;\n;|;\n;|;"));
        Assert.assertEquals(2, grid.getNumberOfSegments());
    }

    @Test
    public void testSetCell() {
        SudokuGrid grid = new SudokuGrid(GRID_SIZE, SEGMENT_SIZE);

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
                .toInputStream("1;3;2\n2;1;3\n3;2;1"));
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
                .toInputStream(EMPTY_GRID));
        Assert.assertEquals(Arrays.asList(), grid.getValuesInRow(0));
        Assert.assertEquals(Arrays.asList(), grid.getValuesInRow(1));
        Assert.assertEquals(Arrays.asList(), grid.getValuesInRow(2));
    }

    @Test
    public void testValuesInRowAll() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(SIMPLE_GRID));
        Assert.assertEquals(Arrays.asList(1, 2, 3), grid.getValuesInRow(0));
        Assert.assertEquals(Arrays.asList(1, 2, 3), grid.getValuesInRow(1));
        Assert.assertEquals(Arrays.asList(1, 2, 3), grid.getValuesInRow(2));
    }

    @Test
    public void testValuesInRowSome() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;;3\n2;3;1\n;1;"));
        Assert.assertEquals(Arrays.asList(1, 3), grid.getValuesInRow(0));
        Assert.assertEquals(Arrays.asList(1, 2, 3), grid.getValuesInRow(1));
        Assert.assertEquals(Arrays.asList(1), grid.getValuesInRow(2));
    }

    @Test
    public void testValuesInColumnNone() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(EMPTY_GRID));
        Assert.assertEquals(Arrays.asList(), grid.getValuesInColumn(0));
        Assert.assertEquals(Arrays.asList(), grid.getValuesInColumn(1));
        Assert.assertEquals(Arrays.asList(), grid.getValuesInColumn(2));
    }

    @Test
    public void testValuesInColumnAll() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(SIMPLE_GRID));
        Assert.assertEquals(Arrays.asList(1, 2, 3), grid.getValuesInColumn(0));
        Assert.assertEquals(Arrays.asList(1, 2, 3), grid.getValuesInColumn(1));
        Assert.assertEquals(Arrays.asList(1, 2, 3), grid.getValuesInColumn(2));
    }

    @Test
    public void testValuesInColumnSome() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;;3\n2;3;\n;1;"));
        Assert.assertEquals(Arrays.asList(1, 2), grid.getValuesInColumn(0));
        Assert.assertEquals(Arrays.asList(1, 3), grid.getValuesInColumn(1));
        Assert.assertEquals(Arrays.asList(3), grid.getValuesInColumn(2));
    }

    @Test
    public void testMissingInRowAll() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(EMPTY_GRID));
        Assert.assertEquals(Arrays.asList(1, 2, 3), grid.getMissingInRow(0));
        Assert.assertEquals(Arrays.asList(1, 2, 3), grid.getMissingInRow(1));
        Assert.assertEquals(Arrays.asList(1, 2, 3), grid.getMissingInRow(2));
    }

    @Test
    public void testMissingInRowNone() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(SIMPLE_GRID));
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
                .toInputStream(EMPTY_GRID));
        Assert.assertEquals(Arrays.asList(1, 2, 3), grid.getMissingInColumn(0));
        Assert.assertEquals(Arrays.asList(1, 2, 3), grid.getMissingInColumn(1));
        Assert.assertEquals(Arrays.asList(1, 2, 3), grid.getMissingInColumn(2));
    }

    @Test
    public void testMissingInColumnNone() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(SIMPLE_GRID));
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
    public void testGetRowsInSegment() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(SIMPLE_GRID));
        Assert.assertEquals(Arrays.asList(0, 1, 2), grid.getRowsInSegment(1));
    }

    @Test
    public void testGetRowsInSegmentFirst() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(SIMPLE_GRID));
        Assert.assertEquals(Arrays.asList(0, 1, 2), grid.getRowsInSegment(0));
    }

    @Test
    public void testGetRowsInSegmentLast() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(SIMPLE_GRID));
        Assert.assertEquals(Arrays.asList(0, 1, 2), grid.getRowsInSegment(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetRowsInSegmentUpperBound()
            throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(SIMPLE_GRID));
        grid.getRowsInSegment(3);
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetRowsInSegmentLowerBound()
            throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(SIMPLE_GRID));
        grid.getRowsInSegment(-1);
        Assert.fail();
    }

    @Test
    public void testGetColumnInSegment() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(SIMPLE_GRID));
        Assert.assertEquals(Arrays.asList(0, 1, 2), grid.getColumnsInSegment(1));
    }

    @Test
    public void testGetColumnsInSegmentFirst() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(SIMPLE_GRID));
        Assert.assertEquals(Arrays.asList(0, 1, 2), grid.getColumnsInSegment(0));
    }

    @Test
    public void testGetColumnsInSegmentLast() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(SIMPLE_GRID));
        Assert.assertEquals(Arrays.asList(0, 1, 2), grid.getColumnsInSegment(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetColumnsInSegmentUpperBound()
            throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(SIMPLE_GRID));
        grid.getColumnsInSegment(3);
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetColumnsInSegmentLowerBound()
            throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(SIMPLE_GRID));
        grid.getColumnsInSegment(-1);
        Assert.fail();
    }

    @Test
    public void testMissingInSegmentAll() throws IllegalGridInputException {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(EMPTY_GRID));
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
                .toInputStream(SIMPLE_GRID));
        StringBuilder builder = new StringBuilder();
        builder.append("*************").append(IOUtils.LINE_SEPARATOR);

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
        StringBuilder builder = new StringBuilder();
        String starBorder = "*****************";
        builder.append(starBorder).append(IOUtils.LINE_SEPARATOR);

        builder.append("* 1 | 2 * 3 | 4 *").append(IOUtils.LINE_SEPARATOR);
        builder.append("*---------------*").append(IOUtils.LINE_SEPARATOR);
        builder.append("*   |   * 1 | 2 *").append(IOUtils.LINE_SEPARATOR);
        builder.append(starBorder).append(IOUtils.LINE_SEPARATOR);
        builder.append("* 3 | 1 *   |   *").append(IOUtils.LINE_SEPARATOR);
        builder.append("*---------------*").append(IOUtils.LINE_SEPARATOR);
        builder.append("*   |   *   |   *").append(IOUtils.LINE_SEPARATOR);
        builder.append(starBorder).append(IOUtils.LINE_SEPARATOR);

        Assert.assertEquals(builder.toString(), grid.toString());
    }
}
