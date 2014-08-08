package sudoku;

import nl.concipit.sudoku.SudokuGridBuilder;
import nl.concipit.sudoku.exception.IllegalGridInputException;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class SudokuGridBuilderTest {
	@Test(expected=IllegalGridInputException.class)
	public void testEmptyInput() throws IllegalGridInputException {
		SudokuGridBuilder.buildGrid(IOUtils.toInputStream(""));
		Assert.fail();
	}
}
