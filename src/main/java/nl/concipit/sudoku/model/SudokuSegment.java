package nl.concipit.sudoku.model;

import java.util.List;

import nl.concipit.sudoku.util.GridUtils;

public class SudokuSegment {

    private int width;
    private int height;
    private SudokuCell[][] cells;

    /**
     * Creates a sudoku segment with the specified dimensions:
     * 
     * width x height;
     * 
     * e.g. width: 3, height: 3
     * 
     * x ; x ; x x ; x ; x x ; x ; x
     * 
     * @param width
     *            Width of the segment
     * @param height
     *            Height of the segment
     */
    public SudokuSegment(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new SudokuCell[width][height];
    }

    /**
     * Return width of the segment
     * 
     * @return Width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Return height of the segment
     * 
     * @return Height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns list of values not contained in the cells in this segment
     * 
     * @return List ordered ascending
     */
    public List<Integer> getMissingValues() {
        List<Integer> values = GridUtils.getValueList(width * height);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                SudokuCell cell = cells[col][row];
                if (cell != null && cell.getValue() != null) {
                    values.remove(cell.getValue());
                }
            }
        }
        return values;
    }

    /**
     * Set cell at index column, row of the segment
     * 
     * @param column
     *            Column
     * @param row
     *            Row
     * @param cell
     *            Cell
     */
    public void setCell(int column, int row, SudokuCell cell) {
        if (column < 0 || column >= width || row < 0 || row >= height) {
            throw new IllegalArgumentException();
        }
        cells[column][row] = cell;
    }

    /**
     * Returns the cell at the specified column, row index of the segment
     * 
     * @param column
     *            Column
     * @param row
     *            Row
     * @return Sudoku cell
     */
    public SudokuCell getCell(int column, int row) {
        SudokuCell result = null;
        if (column >= 0 && column < width && row >= 0 && row < height) {
            result = cells[column][row];
        }
        return result;
    }

    /**
     * Returns true if the segment contains all required values
     * 
     * @return True if segment contains all values, false otherwise
     */
    public boolean isComplete() {
        return getMissingValues().isEmpty();
    }

}
