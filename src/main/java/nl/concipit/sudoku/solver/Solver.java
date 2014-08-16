package nl.concipit.sudoku.solver;

import nl.concipit.sudoku.model.SudokuGrid;

/**
 * Interface to be implemented by Sudoku-solving algorithms.
 * 
 * @author dcoppens
 *
 */
public interface Solver {
    /**
     * Solve the provided Sudoku
     * 
     * @param grid
     *            Grid defining the Sudoku
     * @return True if the algorithm was able to solve the Sudoku, false
     *         otherwise
     */
    boolean solve(SudokuGrid grid);
    
    /**
     * Returns the last solved Sudoku grid.
     * 
     * @return Result grid, or null if no grid was solved
     */
    SudokuGrid getResult();
}
