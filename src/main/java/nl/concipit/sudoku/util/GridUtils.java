package nl.concipit.sudoku.util;

import java.util.ArrayList;
import java.util.List;

/**
 * General utilities
 * 
 * @author dcoppens
 *
 */
public class GridUtils {
	/**
	 * Hide constructor for utility class
	 */
	private GridUtils() {
		// nothing
	}
	
	/**
	 * Creates an ordered list of integers with all values from 1 up to and including the specified
	 * maxValue
	 * 
	 * @param maxValue Maximum integer in the list
	 * @return Ordered list of integers i: 1 <= i <= maxValue
	 */
	public static List<Integer> getValueList(int maxValue) {
		List<Integer> result = new ArrayList<Integer>();
		// init list with all values
		for (int i = 1; i <= maxValue; i++) {
			result.add(Integer.valueOf(i));
		}
		return result;
	}
}
