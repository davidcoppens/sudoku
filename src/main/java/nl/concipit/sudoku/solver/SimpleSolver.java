package nl.concipit.sudoku.solver;

import java.util.List;

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
        boolean isStable = false;

        // continue as long as the grid is not complete and our algorithm was
        // able to find at least one new value
        while (!grid.isComplete() && !isStable) {
            isStable = true;
            for (int row = 0; row < grid.getGridSize(); row++) {
                for (int column = 0; column < grid.getGridSize(); column++) {
                    // process one cell
                    boolean cellValueFound = processCell(grid, column, row);

                    // check whether the grid changed
                    isStable = isStable && !cellValueFound;
                }
            }
        }
        return !isStable;
    }

    /**
     * {@inheritDoc}
     */
    public SudokuGrid getResult() {
        return null;
    }

    /**
     * Find value for the specified cell of the grid.
     * 
     * @param grid
     *            The grid
     * @param column
     *            Column index of the cell
     * @param row
     *            Row index of the cell
     * @return True if the value for the cell was changed. If the cell already
     *         contains a value, the method does not change the value and as
     *         such, returns false.
     */
    private boolean processCell(SudokuGrid grid, int column, int row) {
        SudokuCell cell = grid.getCell(column, row);
        boolean valueFound = false;
        if (cell.getValue() == null) {
            // check whether there is exactly 1 value missing in
            // row, column or segment of the cell
            Integer value = null;

            List<Integer> missingInColumn = grid.getMissingInColumn(column);
            if (missingInColumn.size() == 1) {
                value = missingInColumn.get(0);
            }

            if (value == null) {
                List<Integer> missingInRow = grid.getMissingInRow(row);

                if (missingInRow.size() == 1) {
                    value = missingInRow.get(0);
                }
            }

            if (value == null) {
                List<Integer> missingInSegment = grid.getMissingInSegment(
                        column, row);
                if (missingInSegment.size() == 1) {
                    value = missingInSegment.get(0);
                }
            }

            if (value != null) {
                cell = null;
                grid.setCell(column, row, new SudokuCell(value));
            }

            valueFound = value != null;

        }
        return valueFound;

    }
}
