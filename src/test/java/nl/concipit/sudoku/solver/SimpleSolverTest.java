package nl.concipit.sudoku.solver;

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
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;2;\n4;5;6\n7;8;9"));
        Solver simpleSolver = new SimpleSolver();

        Assert.assertTrue(simpleSolver.solve(grid));
    }

    @Test
    public void testThreeStars() throws Exception {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream("1;6;9|;8;|;;\n" + ";;|;;1|5;;\n"
                        + "5;4;|;9;2|6;;\n" + ";;|1;;|;;3\n" + ";;|;;|7;;|\n"
                        + ";1;|5;7;|;4;\n" + "9;2;|;;|;7;\n" + ";3;6|;;7|1;2;\n"
                        + "7;;|;;|4;5;6"));
        Solver simpleSolver = new SimpleSolver();

        Assert.assertTrue(simpleSolver.solve(grid));

    }

    @Test
    public void testGridMedior() throws Exception {
        SudokuGrid grid = SudokuGridBuilder.buildGrid(IOUtils
                .toInputStream(";;|;;|6;;2\n" + ";4;6|;;|;;\n" + ";;|;9;|;1;\n"
                        + ";;|;3;|8;;\n" + ";2;9|;;4|;;|\n" + ";;|7;2;8|;;4\n"
                        + ";3;4|;;1|2;9;\n" + "9;5;|;;|;;1\n" + ";7;1|;;|;;"));
        Solver simpleSolver = new SimpleSolver();

        Assert.assertTrue(simpleSolver.solve(grid));
    }
}
