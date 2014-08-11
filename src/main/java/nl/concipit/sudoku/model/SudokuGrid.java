package nl.concipit.sudoku.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Representation of a Sudoku Grid
 * 
 * @author dcoppens
 *
 */
public class SudokuGrid {
	/** Grid width: number of columns */
	private int width;
	/** Grid height: numbers of rows */
	private int height;
	/** Segment size */
	private int segmentSize;

	/** Cells */
	private SudokuCell[][] cells;

	/**
	 * Constructor
	 * 
	 * @param width
	 *            number of columns in the grid
	 * @param height
	 *            number of rows in the grid
	 * @param segmentSize
	 *            Size of segments
	 */
	public SudokuGrid(int width, int height, int segmentSize) {
		this.width = width;
		this.height = height;
		this.segmentSize = segmentSize;

		// initialize the grid with empty cells
		resetGrid(width, height);
	}

	/**
	 * Get cell at index i,j of the grid
	 * 
	 * @param i
	 *            column
	 * @param j
	 *            row
	 * @return Cell at index
	 */
	public SudokuCell getCell(int i, int j) {
		SudokuCell result = null;
		if (i >= 0 && i < width && j >= 0 && j < height) {
			result = cells[i][j];
		}
		return result;
	}

	/**
	 * Set cell at index i,j of the grid
	 * 
	 * @param i
	 *            column
	 * @param j
	 *            row
	 * @param cell
	 *            cell to set
	 */
	public void setCell(int i, int j, SudokuCell cell) {
		cells[i][j] = cell;
	}

	/**
	 * Returns the total width of the grid (i.e. the number of cells per row)
	 * 
	 * @return width
	 */
	public int getTotalWidth() {
		return width;
	}

	/**
	 * Returns the total height of the grid (i.e. the number of cells per
	 * column)
	 * 
	 * @return height
	 */
	public int getTotalHeight() {
		return height;
	}

	/**
	 * Returns Sudoku Segment indicated by the specified index.
	 * 
	 * A sudoku grid is made up of a number of segments, each of which contains
	 * an array cells.
	 * 
	 * @param i
	 * @param j
	 * @return the segment
	 */
	public SudokuSegment getSegment(int i, int j) {
		return new SudokuSegment();
	}

	/**
	 * Returns the segment size
	 * 
	 * @return segment size
	 */
	public int getSegmentSize() {
		return segmentSize;
	}

	/**
	 * Resets the grid to the specified dimensions filled with empty cells.
	 * 
	 * @param width
	 *            Width of the grid
	 * @param height
	 *            Height of the grid
	 */
	private void resetGrid(int width, int height) {
		// init cells
		this.cells = new SudokuCell[width][height];

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				this.cells[i][j] = new SudokuCell();
			}
		}
	}

	/**
	 * Checks whether the specified row is complete (i.e. all integers i: 1 < i
	 * <= gridWidth are represented)
	 * 
	 * @param row
	 *            number of row to check
	 * @return true if the row is complete, false otherwise
	 */
	public boolean isCompleteRow(int row) {
		Map<Integer, Integer> valueMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < width; i++) {
			if (cells[i][row].getValue() != null) {
				valueMap.put(cells[i][row].getValue(), i);
			}
		}
		return valueMap.size() == width;
	}

	/**
	 * Checks whether the specified column is complete (i.e. all integers i : 1
	 * < i <= gridHeight are represented)
	 * 
	 * 
	 * @param column
	 *            column to check
	 * @return true if the column is complete, false otherwise
	 */
	public boolean isCompleteColumn(int column) {
		Map<Integer, Integer> valueMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < height; i++) {
			if (cells[column][i].getValue() != null) {
				valueMap.put(cells[column][i].getValue(), i);
			}
		}
		return valueMap.size() == height;
	}

}
