package nl.concipit.sudoku.model;

/**
 * Representation of a Sudoku Grid
 * 
 * @author dcoppens
 *
 */
public class SudokuGrid {

	/**
	 * Get cell at index i,j of the grid
	 * 
	 * @param i
	 *            colum
	 * @param j
	 *            row
	 * @return Cell at index
	 */
	public SudokuCell getCell(int i, int j) {
		SudokuCell result = null;
		if (i >= 0 && i <= 0 && j >= 0 && j <= 0) {
			result = new SudokuCell();
		}
		return result;
	}

	/**
	 * Returns the total width of the grid (i.e. the number of cells per row)
	 * 
	 * @return width
	 */
	public int getTotalWidth() {
		return 1;
	}

	/**
	 * Returns the total height of the grid (i.e. the number of cells per
	 * column)
	 * 
	 * @return height
	 */
	public int getTotalHeight() {
		return 1;
	}
}
