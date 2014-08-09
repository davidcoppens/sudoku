package nl.concipit.sudoku;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import nl.concipit.sudoku.exception.IllegalGridInputException;
import nl.concipit.sudoku.model.SudokuCell;
import nl.concipit.sudoku.model.SudokuGrid;

import org.apache.commons.io.IOUtils;

/**
 * Builder for sudoku grids based on input.
 * 
 * @author dcoppens
 *
 */
public class SudokuGridBuilder {

	/**
	 * Constructs a SudokuGrid by reading the input stream; <br/>
	 * If the input stream does not contain a grid definition in the expected
	 * format an exception is thrown. <br/>
	 * Format example for 6 x 2 grid with four segments: <br/>
	 * cell;cell;cell|cell;cell;cell\n cell;cell;cell|cell;cell;cell\n
	 * 
	 * @param inputStream
	 *            Input
	 * @throws IllegalGridInputException
	 *             thrown if the input is not in the expected format
	 */
	public static SudokuGrid buildGrid(InputStream inputStream)
			throws IllegalGridInputException {
		try {
			SudokuGrid grid = null;
			if (inputStream == null) {
				throw new IllegalGridInputException();
			}
			List<String> lines = IOUtils.readLines(inputStream);

			if (lines.isEmpty()) {
				throw new IllegalGridInputException();
			}
		
			int row = 0;
			for (String line : lines) {
				String[] segments = line.split("\\|");
				int column = 0;
				for (String segment : segments) {

					String[] cells = segment.split(";", -1);

					if (grid == null) {
						grid = new SudokuGrid(cells.length * segments.length,
								lines.size(), cells.length);
					} else {
						if (cells.length * segments.length != grid.getTotalWidth()) {
							throw new IllegalGridInputException();
						}
						if (cells.length != grid.getSegmentSize()) {
							throw new IllegalGridInputException();
						}
					}

					for (String cell : cells) {
						if (cell != null && !"".equals(cell)) {
							grid.setCell(column, row,
									new SudokuCell(Integer.parseInt(cell)));
						} else {
							grid.setCell(column, row, new SudokuCell());
						}
						column++;
					}
				}
				row++;
			}

			return grid;

		} catch (IOException e) {
			throw new IllegalGridInputException(e);
		}

	}
}
