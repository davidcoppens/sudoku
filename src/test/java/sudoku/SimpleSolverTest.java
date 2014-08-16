package sudoku;

import nl.concipit.sudoku.SudokuGridBuilder;
import nl.concipit.sudoku.model.SudokuGrid;
import nl.concipit.sudoku.solver.SimpleSolver;
import nl.concipit.sudoku.solver.Solver;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test cases for {@link SimpleSolver}
 * 
 * @author dcoppens
 *
 */
public class SimpleSolverTest {
    
    @Test
    public void testGetGridNoSolution() {
        Solver simpleSolver = new SimpleSolver();
               
        Assert.assertNull(simpleSolver.getResult());
    }
    
    @Test
    public void testGridSimple() throws Exception {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils.toInputStream("1;2;3\n4;5;6\n7;8;9"));
        Solver simpleSolver = new SimpleSolver();
        
        Assert.assertTrue(simpleSolver.solve(grid));
    }
}
