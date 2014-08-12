package sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.concipit.sudoku.util.GridUtils;

import org.junit.Assert;
import org.junit.Test;

public class GridUtilsTest {
	@Test
	public void testCreateValueList() {
		Assert.assertEquals(Arrays.asList(1,2,3,4), GridUtils.getValueList(4));
	}
	
	@Test
	public void testCreateEmptyList() {
		Assert.assertEquals(Arrays.asList(), GridUtils.getValueList(0));
		Assert.assertEquals(Arrays.asList(), GridUtils.getValueList(-1));
	}
	
	@Test
	public void testCreateLargeValueList() {
		int largeValue = 250000;
		List<Integer> expected = new ArrayList<Integer>(largeValue);
		for (int i = 1; i < largeValue + 1; i++) {
			expected.add(i);
		}
		Assert.assertEquals(expected, GridUtils.getValueList(largeValue));
	}
}
