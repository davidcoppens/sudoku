package nl.concipit.sudoku.solver;

import java.util.List;

import nl.concipit.sudoku.model.SudokuCell;
import nl.concipit.sudoku.model.SudokuGrid;
import nl.concipit.sudoku.util.GridUtils;

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
            // start out with no value
            Integer value = null;

            // perform simple basic checks on column, row and segment
            value = performBasicChecks(grid, column, row);

            if (value == null) {
                value = performCrossChecks(grid, column, row);
            }

            if (value != null) {
                cell = null;
                grid.setCell(column, row, new SudokuCell(value));
            }
            valueFound = value != null;
        }
        return valueFound;

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
     * @return Found value, or null if no value was found using this check
     */
    private Integer performBasicChecks(SudokuGrid grid, int column, int row) {
        Integer value = null;
        // check whether this column misses exactly 1 value
        List<Integer> missingInColumn = grid.getMissingInColumn(column);
        if (missingInColumn.size() == 1) {
            value = missingInColumn.get(0);
        }

        // check whether this row misses exactly 1 value
        if (value == null) {
            List<Integer> missingInRow = grid.getMissingInRow(row);

            if (missingInRow.size() == 1) {
                value = missingInRow.get(0);
            }
        }

        // check whether this segment misses exactly 1 value
        if (value == null) {
            List<Integer> missingInSegment = grid.getMissingInSegment(column,
                    row);
            if (missingInSegment.size() == 1) {
                value = missingInSegment.get(0);
            }
        }
        return value;
    }

    private Integer performCrossChecks(SudokuGrid grid, int column, int row) {
        // determine overlapping values in all rows
        List<Integer> rows = grid.getOtherRowsInSegment(row);
        List<Integer> overlappingValues = GridUtils.getValueList(grid
                .getGridSize());
        for (Integer otherRow : rows) {
            // keep values that are in this row, as well as all other rows so
            // far
            overlappingValues.retainAll(grid.getValuesInRow(otherRow));
        }

        List<Integer> columns = grid.getOtherColumnsInSegment(column);
        for (Integer otherCol : columns) {
            // keep values that are in this column, as well as all other columns
            // so far
            overlappingValues.retainAll(grid.getValuesInColumn(otherCol));
        }

        overlappingValues.retainAll(grid.getMissingInSegment(column, row));
        if (overlappingValues.size() == 1) {
            return overlappingValues.get(0);
        }

        return null;
    }
}
