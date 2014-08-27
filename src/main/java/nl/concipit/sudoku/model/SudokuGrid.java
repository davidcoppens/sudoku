package nl.concipit.sudoku.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.concipit.sudoku.util.GridUtils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Representation of a Sudoku Grid
 * 
 * @author dcoppens
 *
 */
public class SudokuGrid {
    /** Grid size */
    private int gridSize;

    /** Segment size */
    private int segmentSize;

    /** Cells */
    private SudokuCell[][] cells;

    /** Segments */
    private SudokuSegment[][] segments;

    /**
     * Constructor
     * 
     * @param gridSize
     *            Size of the grid: gridSize x gridSize
     * @param height
     *            number of rows in the grid
     * @param segmentSize
     *            Size of segments
     */
    public SudokuGrid(int gridSize, int segmentSize) {
        this.gridSize = gridSize;
        this.segmentSize = segmentSize;

        // initialize the grid with empty cells
        resetGrid();
    }

    /**
     * Get cell at index i,j of the grid
     * 
     * @param i
     *            column
     * @param j
     *            row
     * @return Cell at index
     */
    public SudokuCell getCell(int i, int j) {
        SudokuCell result = null;
        if (i >= 0 && i < gridSize && j >= 0 && j < gridSize) {
            result = cells[i][j];
        }
        return result;
    }

    /**
     * Set cell at index i,j of the grid
     * 
     * @param i
     *            column
     * @param j
     *            row
     * @param cell
     *            cell to set
     */
    public void setCell(int i, int j, SudokuCell cell) {
        cells[i][j] = cell;

        getSegment(i, j).setCell(i % segmentSize, j % segmentSize, cell);
    }

    /**
     * Returns the size of the grid (grid has dimension size x size)
     * 
     * @return Size
     */
    public int getGridSize() {
        return gridSize;
    }

    /**
     * Returns Sudoku Segment containing the cell indicated by the specified
     * index.
     * 
     * A sudoku grid is made up of a number of segments, each of which contains
     * an array cells.
     * 
     * @param i
     * @param j
     * @return the segment
     */
    public SudokuSegment getSegment(int i, int j) {
        int segmentX = i / segmentSize;
        int segmentY = j / segmentSize;

        return segments[segmentX][segmentY];
    }

    /**
     * Returns the segment size
     * 
     * @return segment size
     */
    public int getSegmentSize() {
        return segmentSize;
    }

    /**
     * Checks whether the specified row is complete (i.e. all integers i: 1 < i
     * <= gridSize are represented)
     * 
     * @param row
     *            number of row to check
     * @return true if the row is complete, false otherwise
     */
    public boolean isCompleteRow(int row) {
        Map<Integer, Integer> valueMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < gridSize; i++) {
            if (cells[i][row].getValue() != null) {
                valueMap.put(cells[i][row].getValue(), i);
            }
        }
        return valueMap.size() == gridSize;
    }

    /**
     * Checks whether the specified column is complete (i.e. all integers i : 1
     * < i <= gridSize are represented)
     * 
     * 
     * @param column
     *            column to check
     * @return true if the column is complete, false otherwise
     */
    public boolean isCompleteColumn(int column) {
        Map<Integer, Integer> valueMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < gridSize; i++) {
            if (cells[column][i].getValue() != null) {
                valueMap.put(cells[column][i].getValue(), i);
            }
        }
        return valueMap.size() == gridSize;
    }

    /**
     * Returns ordered list of numbers missing in the specified row
     * 
     * @param row
     *            row
     * @return List of numbers missing in the row (ordered ascending)
     */
    public List<Integer> getMissingInRow(int row) {
        List<Integer> result = GridUtils.getValueList(gridSize);

        // remove set values
        for (int i = 0; i < gridSize; i++) {
            SudokuCell cell = cells[i][row];
            if (cell != null && cell.getValue() != null) {
                result.remove(cell.getValue());
            }
        }
        return result;
    }

    /**
     * Returns a list of values contained in the specified row.
     * 
     * @param row
     *            Row
     * @return List of values in the row
     */
    public List<Integer> getValuesInRow(int row) {
        List<Integer> missing = getMissingInRow(row);

        return invertList(missing);
    }

    /**
     * Returns a list of values contained in the specified column.
     * 
     * @param column
     *            Column
     * @return List of values in the column
     */
    public List<Integer> getValuesInColumn(int column) {
        List<Integer> missing = getMissingInColumn(column);

        return invertList(missing);
    }

    /**
     * Returns ordered list of numbers missing in the specified column
     * 
     * @param column
     *            column
     * @return List of numbers missing in the column (ordered ascending)
     */
    public List<Integer> getMissingInColumn(int column) {
        List<Integer> result = GridUtils.getValueList(gridSize);

        // remove set values
        for (int i = 0; i < gridSize; i++) {
            SudokuCell cell = cells[column][i];
            if (cell != null && cell.getValue() != null) {
                result.remove(cell.getValue());
            }
        }
        return result;
    }

    /**
     * Returns list of numbers missing in the segment containing the cell with
     * specified coordinates
     * 
     * @param column
     *            column
     * @param row
     *            row
     * @return List of missing numbers ordered ascending
     */
    public List<Integer> getMissingInSegment(int column, int row) {
        // get the segment
        SudokuSegment segment = getSegment(column, row);
        return segment.getMissingValues();
    }

    /**
     * Returns the number of segments per row and column of the grid
     * 
     * @return Number of segments
     */
    public int getNumberOfSegments() {
        return gridSize / segmentSize;
    }

    /**
     * Returns a list containing row indices of the rows in the segments that
     * contain the specified row.
     * 
     * @param row
     *            Row
     * @return List of row indices
     */
    public List<Integer> getRowsInSegment(int row) {
        return getIndices(row);
    }

    /**
     * Returns a list containing column indices of the columns in the segments
     * that contain the specified column.
     *
     * @param column
     *            Column
     * @return List of column indices
     */
    public List<Integer> getColumnsInSegment(int column) {
        return getIndices(column);
    }

    /**
     * Verifies whether the grid is complete;
     * 
     * @return true if grid is complete, false otherwise
     */
    public boolean isComplete() {
        boolean isComplete = true;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                isComplete = isComplete && getMissingInSegment(i,j).isEmpty();
            }
        }
        return isComplete;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder ();
        // each cell occupies 3 each line has one start and end char
        // in between cells there is a marker
        int lineLength = (gridSize * 3) + (gridSize - 1) + 2;
        builder.append(StringUtils.repeat("*", lineLength)).append(
                IOUtils.LINE_SEPARATOR);

        for (int row = 0; row < gridSize; row++) {
            // pocess row
            rowToString(builder, lineLength, row);
        }

        builder.append(StringUtils.repeat("*", lineLength)).append(
                IOUtils.LINE_SEPARATOR);

        return builder.toString();
    }

    /**
     * Add string representation of the row to the string builder
     * @param builder string builder
     * @param lineLength length of line
     * @param row row
     */
    private void rowToString(StringBuilder builder, int lineLength, int row) {
        builder.append("*");

        for (int column = 0; column < gridSize; column++) {
            cellToString(builder, row, column);
        }
        builder.append("*").append(IOUtils.LINE_SEPARATOR);

        if ((row + 1) > 0 && (row + 1) < (gridSize - 1)
                && (row + 1) % segmentSize == 0) {
            builder.append(StringUtils.repeat("*", lineLength)).append(
                    IOUtils.LINE_SEPARATOR);

        } else if (row + 1 < gridSize) {
            builder.append("*")
                    .append(StringUtils.repeat("-", lineLength - 2))
                    .append("*").append(IOUtils.LINE_SEPARATOR);
        }
    }

    /**
     * Add string representation of the cell to the string builder
     * @param builder Builder
     * @param row Index of cell
     * @param column Index of cell
     */
    private void cellToString(StringBuilder builder, int row, int column) {
        builder.append(cells[column][row].toString()
                .replaceAll("\\[", " ").replaceAll("\\]", " "));

        // segment or column marker
        int nextCol = column + 1;
        if (nextCol > 0 && nextCol < (gridSize - 1)
                && nextCol % segmentSize == 0) {
            builder.append("*");
        } else if (nextCol > 0 && nextCol < gridSize) {
            builder.append("|");
        }
    }

    /**
     * Returns a list with the (row or column) indices that are equal to or next
     * to the specified index, but within the same segment. <br/>
     * <br/>
     * Since Sudoku grid is square, this implementation works for both rows as
     * well as columns.
     * 
     * @param index
     *            Index
     * @return List of indices adjacent to the specified index within the same
     *         segment
     */
    private List<Integer> getIndices(int index) {
        // boundary check
        if (index < 0 || index >= gridSize) {
            throw new IllegalArgumentException();
        }

        // create result list for the indices
        List<Integer> result = new ArrayList<Integer>(segmentSize - 1);

        // fill the result list
        int startRow = segmentSize * (index / segmentSize);
        for (int i = startRow; i < startRow + segmentSize; i++) {
            result.add(new Integer(i));
        }
        return result;
    }

    /**
     * Creates and returns a list containing all integers 0 < i <= gridSize that
     * are NOT contained in the supplied list
     * 
     * @param valueList
     *            List of values
     * @return Inverted list of values
     */
    private List<Integer> invertList(List<Integer> valueList) {
        List<Integer> result = new ArrayList<Integer>();

        for (int i = 1; i <= gridSize; i++) {
            if (!valueList.contains(i)) {
                result.add(new Integer(i));
            }
        }

        return result;
    }

    /**
     * Resets the grid to the specified dimensions filled with empty cells.
     */
    private void resetGrid() {
        // init cells
        this.cells = new SudokuCell[gridSize][gridSize];

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                this.cells[i][j] = new SudokuCell();
            }
        }

        // init segments
        int noSegments = gridSize / segmentSize;
        this.segments = new SudokuSegment[noSegments][noSegments];
        for (int i = 0; i < noSegments; i++) {
            for (int j = 0; j < noSegments; j++) {
                this.segments[i][j] = new SudokuSegment(segmentSize);
            }
        }
    }
}
