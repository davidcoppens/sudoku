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
     * @return True if the list of possible values for the cell was changed. If
     *         the cell already contains a value, the method does not change the
     *         value and as such, returns false.
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

            int preState = cellValues.size();

            // perform simple basic checks on column, row and segment
            performBasicChecks(grid, column, row, cellValues);

            if (cellValues.size() > 1) {
                performCrossChecks(grid, column, row, cellValues);
            }

            if (cellValues.size() == 1) {
                cell = null;
                grid.setCell(column, row, new SudokuCell(cellValues.get(0)));
                cellValues.clear();
            }
            valueFound = grid.getCell(column, row).getValue() != null
                    || preState != cellValues.size();
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
     * @param cellValues
     *            possible values for the cell
     */
    private void performBasicChecks(SudokuGrid grid, int column, int row,
            List<Integer> cellValues) {
        // exclude values in the same column, row or segment
        cellValues.removeAll(grid.getValuesInColumn(column));
        cellValues.removeAll(grid.getValuesInRow(row));
        cellValues.retainAll(grid.getMissingInSegment(column, row));
    }

    private void performCrossChecks(SudokuGrid grid, int column, int row,
            List<Integer> cellValues) {
        // determine overlapping values in all rows
        List<Integer> rows = grid.getRowsInSegment(row);
        List<Integer> columns = grid.getColumnsInSegment(column);

        // create a list of possible values, which we will use to determine
        // whether or not
        // this cell is the only one that can have a particular value when
        // compared to the
        // other cells in the same segment
        List<Integer> exclusiveVals = new ArrayList<Integer>();
        exclusiveVals.addAll(cellValues);
        List<Integer> otherCellOverlap = GridUtils.getValueList(grid
                .getGridSize());
        
        boolean conclusionPossible = true;
        // iterate the cells in the segment
        for (Integer i : columns) {
            for (Integer j : rows) {
                if (conclusionPossible && (i != column || j != row)) {

                    SudokuCell otherCell = grid.getCell(i, j);

                    // only interested in cells without value, for which we
                    // already
                    // have a list of
                    // possible values determined
                    if (otherCell.getValue() == null
                            && possibleValues.containsKey(otherCell)) {

                        // collect conjunction of possible values for other
                        // cells
                        otherCellOverlap.retainAll(possibleValues
                                .get(otherCell));
                    }
                    
                    // we cannot draw a conclusion without taking all cells into 
                    // account
                    conclusionPossible = otherCell.getValue() != null || possibleValues.containsKey(otherCell);
                }
            }
        }

        // remove all overlapping values
        exclusiveVals.removeAll(otherCellOverlap);

        // if there is now only 1 value left in the exclusiveVals list, this
        // means that exactly one of the possible values for the cell CANNOT be
        // the value for any other cell in the same segment (since it was not
        // in the list of possible values for any of them).
        // As such, this cell must get this value and we can update the list of
        // possible values to reflect this.
        // If there are more than 1 values left, we cannot draw any conclusion
        if (conclusionPossible && exclusiveVals.size() == 1) {
            // only keep the exclusive value
            cellValues.retainAll(exclusiveVals);
        }
    }
}
