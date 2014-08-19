package nl.concipit.sudoku.model;

/**
 * Representation of one Sudoku Cell
 * 
 * @author dcoppens
 *
 */
public class SudokuCell {
    private Integer value = null;

    /**
     * Constructor for a cell with value
     * 
     * @param value
     *            Value of the cell
     */
    public SudokuCell(Integer value) {
        this.value = value;
    }

    /**
     * Constructor with a cell without a value
     */
    public SudokuCell() {
        //
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
    
    @Override
    public String toString() {
        String val = " ";
        if (value != null) {
            val = value.toString();
        }
        return String.format("[%s]", val);        
    }
}
