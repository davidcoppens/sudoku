package nl.concipit.sudoku.model;


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
	
	/** Cells */
	private SudokuCell[][] cells;

	/**
	 * Constructor
	 * 
	 * @param width
	 *            number of columns in the grid
	 * @param height
	 *            number of rows in the grid
	 */
	public SudokuGrid(int width, int height) {
		this.width = width;
		this.height = height;
		
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
	 * @param i  column
	 * @param j row
	 * @param cell cell to set
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
	 * A sudoku grid is made up of a number of segments, each of which contains an
	 * array cells.
	 * 
	 * @param i
	 * @param j
	 * @return the segment
	 */
	public SudokuSegment getSegment(int i, int j) {
		return new SudokuSegment();
	}
	
	/**
	 * Resets the grid to the specified dimensions filled with empty cells.
	 * 
	 * @param width Width of the grid
	 * @param height Height of the grid
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

}
