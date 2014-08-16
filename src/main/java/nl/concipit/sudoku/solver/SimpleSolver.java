package nl.concipit.sudoku.solver;

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
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public SudokuGrid getResult() {
        return null;
    }

}
