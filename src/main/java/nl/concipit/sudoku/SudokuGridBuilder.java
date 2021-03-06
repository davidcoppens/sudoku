package nl.concipit.sudoku;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import nl.concipit.sudoku.exception.IllegalGridInputException;
import nl.concipit.sudoku.model.SudokuCell;
import nl.concipit.sudoku.model.SudokuGrid;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Builder for sudoku grids based on input.
 * 
 * @author dcoppens
 *
 */
public class SudokuGridBuilder {

    private static final String CELL_DELIMITER = ";";
    private static final String SEGMENT_DELIMITER = "\\|";

    /**
     * Private constructor
     */
    private SudokuGridBuilder() {
        //
    }

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

            // prepare the grid definition
            prepareGridDefinition(lines);

            int row = 0;
            for (String line : lines) {
                if (grid == null) {
                    grid = initializeGrid(lines.size(), line);
                }

                // process the line
                processLine(grid, lines.size(), row, line);
                row++;
            }

            return grid;

        } catch (IOException e) {
            throw new IllegalGridInputException(e);
        }

    }

    /**
     * Creates an empty grid with the specified grid height and grid width
     * determined by the supplied line
     * 
     * @param gridSize
     *            Height of the grid
     * @param line
     *            One line of the grid to be created
     * @return Grid with specified height and width as determined based on the
     *         line
     */
    private static SudokuGrid initializeGrid(int gridSize, String line) {
        SudokuGrid grid;
        // init the grid
        String[] segments = line.split(SEGMENT_DELIMITER, -1);  
        String[] cells = segments[0].split(CELL_DELIMITER, -1);

        grid = new SudokuGrid(cells.length * segments.length,
                cells.length);
        return grid;
    }

    /**
     * Process one line of a grid definition
     * 
     * @param grid
     *            Sudoku grid to which to add the processed line; if this is the
     *            first line, the grid is null
     * @param gridHeight
     *            Height of the sudoku grid
     * @param row
     *            row of the sudoku grid to which this line corresponds
     * @param line
     *            the line to process
     * @throws IllegalGridInputException
     *             thrown if the line leads to an invalid sudoku grid
     */
    private static void processLine(SudokuGrid grid, int gridHeight, int row,
            String line) throws IllegalGridInputException {

        String[] segments = line.split(SEGMENT_DELIMITER);
        int column = 0;
        for (String segment : segments) {

            String[] cells = segment.split(CELL_DELIMITER, -1);

            if (cells.length * grid.getNumberOfSegments() != grid.getGridSize()) {
                throw new IllegalGridInputException();
            }
            if (cells.length != grid.getSegmentSize()) {
                throw new IllegalGridInputException();
            }

            for (String cell : cells) {
                processCell(cell, column, row, grid);
                column++;
            }
        }
    }

    /**
     * Process cell definition
     * 
     * @param cell
     *            String representation of a cell
     * @param column
     *            Column to which to add cell
     * @param row
     *            Row to which to add cell
     * @param grid
     *            Grid to which to add cell
     * @throws IllegalGridInputException
     *             Thrown if the cell definition is illegal
     */
    private static void processCell(String cell, int column, int row,
            SudokuGrid grid) throws IllegalGridInputException {
        String trimmedCell = cell.trim();
        if (StringUtils.isEmpty(trimmedCell)) {
            grid.setCell(column, row, new SudokuCell(column, row));
        } else {

            Integer cellValue = Integer.parseInt(trimmedCell);

            // check whether this is a possible value
            if (cellValue <= 0 || cellValue > (grid.getSegmentSize() * grid.getSegmentSize())) {
                throw new IllegalGridInputException();
            }

            // check whether this value is already in the row
            if ((cellValue < grid.getGridSize() && !grid.getMissingInRow(row).contains(cellValue))
                    || (cellValue < grid.getGridSize()) && !grid.getMissingInColumn(column).contains(cellValue)) {
                throw new IllegalGridInputException();
            }
            grid.setCell(column, row, new SudokuCell(column, row, cellValue));
        }

    }

    /**
     * Prepares the list of lines representing a sudoku grid for further
     * processing;
     * 
     * empty lines will be removed;
     * 
     * @param lines
     *            Lines to be processed; empty lines will be removed
     * @throws IllegalGridInputException
     *             Thrown if input list is empty
     */
    private static void prepareGridDefinition(List<String> lines)
            throws IllegalGridInputException {
        if (lines.isEmpty()) {
            throw new IllegalGridInputException();
        }

        // first, let's remove any empty lines, since they are ignored
        Iterator<String> it = lines.iterator();
        while (it.hasNext()) {
            String line = it.next();
            if (StringUtils.isEmpty(line)) {
                it.remove();
            }
        }
    }
}
