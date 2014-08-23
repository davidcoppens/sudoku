package nl.concipit.sudoku.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<SudokuCell, List<Integer>> possibleValues;   
    
    /**
     * {@inheritDoc}
     */
    public boolean solve(SudokuGrid grid) {
        boolean isStable = false;
        possibleValues = new HashMap<SudokuCell, List<Integer>>();

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
            // get list of possible values
            List<Integer> cellValues;
            if (possibleValues.containsKey(cell)) {
                cellValues = possibleValues.get(cell);
            } else {
                cellValues = new ArrayList<Integer>();
                cellValues.addAll(GridUtils.getValueList(grid.getGridSize()));
                possibleValues.put(cell, cellValues);
            }

            // perform simple basic checks on column, row and segment
            performBasicChecks(grid, column, row, cellValues);

            if (cellValues.size() > 1) {
                performCrossChecks(grid, column, row, cellValues);
            } else {
                cell = null;
                grid.setCell(column, row, new SudokuCell(cellValues.get(0)));
            }
            valueFound = grid.getCell(column, row).getValue() != null;
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
     * @param cellValues possible values for the cell
     */
    private void performBasicChecks(SudokuGrid grid, int column, int row, List<Integer> cellValues) {
        // exclude values in the same column, row or segment
        cellValues.removeAll(grid.getValuesInColumn(column));
        cellValues.removeAll(grid.getValuesInRow(row));
        cellValues.retainAll(grid.getMissingInSegment(column, row));
    }

    private void performCrossChecks(SudokuGrid grid, int column, int row, List<Integer> cellValues) {
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
    }
}
