package nl.concipit.sudoku;

import java.io.InputStream;

import nl.concipit.sudoku.exception.IllegalGridInputException;
import nl.concipit.sudoku.model.SudokuGrid;

/**
 * Builder for sudoku grids based on input.
 * 
 * @author dcoppens
 *
 */
public class SudokuGridBuilder {

	/**
	 * Constructs a SudokuGrid by reading the input stream;
	 * <br/>
	 * If the input stream does not contain a grid definition in the
	 * expected format an exception is thrown.
	 * <br/>
	 * Format example for 6 x 2 grid with four segments:
	 * <br/>
	 * cell;cell;cell|cell;cell;cell\n
	 * cell;cell;cell|cell;cell;cell\n
	 * 
	 * @param inputStream Input
	 * @throws IllegalGridInputException thrown if the input is not in the expected format
	 */
	public static SudokuGrid buildGrid(InputStream inputStream) throws IllegalGridInputException {
		throw new IllegalGridInputException();
	}
	
}
