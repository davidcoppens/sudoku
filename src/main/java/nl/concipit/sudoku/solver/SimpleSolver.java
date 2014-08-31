package nl.concipit.sudoku.solver;

import nl.concipit.sudoku.model.SudokuCell;
import nl.concipit.sudoku.model.SudokuGrid;

/**
 * Simple single-threaded solver based on row and column completeness.
 * 
 * @author dcoppens
 *
 */
public class SimpleSolver implements Solver {

    /**
     * {@inheritDoc}
     */
    public boolean solve(SudokuGrid grid) {
        boolean solved = false;

        if (grid.isComplete()) {
            solved = true;
        } else {
            SudokuCell cell = getEmptyCell(grid);
            Integer trialValue = 1;
            while (!solved && trialValue <= grid.getGridSize()) {
                if (isLegalValue(grid, cell, trialValue)) {
                    cell.setValue(trialValue);
                    if (solve(grid)) {
                        solved = true;
                    } else {
                        cell.setValue(null);
                    }
                }
                trialValue++;
            }
        }

        return solved;
    }

    /**
     * {@inheritDoc}
     */
    public SudokuGrid getResult() {
        return null;
    }

    /**
     * Find an empty cell in the grid
     * 
     * @param grid
     *            Sudoku grid
     * @return Empty cell or null if none found
     */
    private SudokuCell getEmptyCell(SudokuGrid grid) {
        for (int i = 0; i < grid.getGridSize(); i++) {
            for (int j = 0; j < grid.getGridSize(); j++) {
                SudokuCell cell = grid.getCell(i, j);
                if (cell.getValue() == null) {
                    return cell;
                }
            }
        }
        return null;
    }

    /**
     * Performs an easy basic check on the defined cell; <br/>
     * If the row, column or segment of the cell misses exactly 1 value, we know
     * that this value must be given to the cell and we are done.
     * 
     * @param grid
     *            Grid
     * @param column
     *            Column
     * @param row
     *            Row
     * @param cellValues
     *            possible values for the cell
     * @return true if the value is legal false otherwise
     */
    private boolean isLegalValue(SudokuGrid grid, SudokuCell cell, Integer value) {
        // exclude values in the same column, row or segment
        return !grid.getValuesInRow(cell.getRow()).contains(value)
                && !grid.getValuesInColumn(cell.getColumn()).contains(value)
                && grid.getSegment(cell.getColumn(), cell.getRow())
                        .getMissingValues().contains(value);
    }
}
