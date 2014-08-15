package nl.concipit.sudoku.model;

import java.util.List;

import nl.concipit.sudoku.util.GridUtils;

public class SudokuSegment {

    private int size;
    private SudokuCell[][] cells;

    /**
     * Creates a sudoku segment with the specified dimensions:
     * 
     * size x size;
     * 
     * e.g. width: 3, height: 3
     * 
     * x ; x ; x x ; x ; x x ; x ; x
     * 
     * @param size
     *            Size of the segment
     */
    public SudokuSegment(int size) {
        this.size = size;
        this.cells = new SudokuCell[size][size];
    }

    /**
     * Return size of the segment
     * 
     * @return Size
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns list of values not contained in the cells in this segment
     * 
     * @return List ordered ascending
     */
    public List<Integer> getMissingValues() {
        List<Integer> values = GridUtils.getValueList(size * size);
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
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
        if (column < 0 || column >= size || row < 0 || row >= size) {
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
        if (column >= 0 && column < size && row >= 0 && row < size) {
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
