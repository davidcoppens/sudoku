package nl.concipit.sudoku.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import nl.concipit.sudoku.util.GridUtils;

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
     * Verifies whether the grid is complete;
     * 
     * @return true if grid is complete, false otherwise
     */
    public boolean isComplete() {
        boolean isComplete = true;
        int noSegments = getNumberOfSegments();
        for (int i = 0; i < noSegments; i++) {
            for (int j = 0; j < noSegments; j++) {
                isComplete = isComplete && getMissingInSegment(i, j).isEmpty();
            }
        }
        return isComplete;
    }

    @Override
    public String toString() {
        StringBuffer builder = new StringBuffer();
        // each cell occupies 3; each line has one start and end char; 
        // in between cells there is a marker
        int lineLength = (gridSize * 3) + (gridSize - 1) + 2;
        builder.append(StringUtils.repeat("*", lineLength)).append(
                IOUtils.LINE_SEPARATOR);

        for (int row = 0; row < gridSize; row++) {
            builder.append("*");

            for (int column = 0; column < gridSize; column++) {

                builder.append(cells[column][row].toString().replaceAll("\\[", " ").replaceAll("\\]"," "));
                
                // segment or column marker
                int nextCol = column + 1;
                if (nextCol > 0 && nextCol < (gridSize - 1) && nextCol % segmentSize == 0) {
                    builder.append("*");
                } else if (nextCol > 0 && nextCol < gridSize ) {
                    builder.append("|");
                }
            }
            builder.append("*").append(IOUtils.LINE_SEPARATOR);
            
            if ((row + 1) > 0 && (row + 1) < (gridSize - 1) && (row + 1) % segmentSize == 0) {
                builder.append(StringUtils.repeat("*", lineLength)).append(IOUtils.LINE_SEPARATOR);
                    
            }
            else  if (row + 1 < gridSize) {
                builder.append("*").append(StringUtils.repeat("-", lineLength - 2)).append("*").append(IOUtils.LINE_SEPARATOR);
            }
        }

        builder.append(StringUtils.repeat("*", lineLength)).append(
                IOUtils.LINE_SEPARATOR);

        return builder.toString();
    }
}
