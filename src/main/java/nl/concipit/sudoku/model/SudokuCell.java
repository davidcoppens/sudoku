package nl.concipit.sudoku.model;

/**
 * Representation of one Sudoku Cell
 * 
 * @author dcoppens
 *
 */
public class SudokuCell {
    private Integer value = null;
    private int column;
    private int row;

    /**
     * Constructor for a cell with value
     * 
     * @param column
     *            Column in the grid
     * @param row
     *            row in the grid
     * @param value
     *            Value of the cell
     */
    public SudokuCell(int column, int row, Integer value) {
        this.value = value;
        this.column = column;
        this.row = row;
    }

    /**
     * Constructor with a cell without a value
     * 
     * @param column
     *            Column in the grid
     * @param row
     *            row in the grid
     */
    public SudokuCell(int column, int row) {
        this(column, row, null);
    }

    /**
     * Returns the value of the cell. <br/>
     * <br/>
     * If the cell does not have a value, null is returned
     * 
     * @return Value of the cell, or null if undefined
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Sets the value of the cell.
     * 
     * @param value
     *            The value
     */
    public void setValue(Integer value) {
        this.value = value;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    @Override
    public String toString() {
        String val = " ";
        if (value != null) {
            val = value.toString();
        }
        return String.format("[%s]", val);
    }
}
