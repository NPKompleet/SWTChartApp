package com.npkompleet.dps.application.util;

import java.math.BigInteger;
import java.util.Map;

public class ChartDataSingleton {
	private static ChartDataSingleton instance;

	private String filePath;
	private boolean hasActivationPatternDrawn = false;
	private boolean hasNewModelLoaded = false;
	private boolean hasLabelSizeDrawn = false;

	private ChartDataSingleton() {

	}
	
	public static synchronized ChartDataSingleton getInstance() {
		if (instance == null) {
			instance = new ChartDataSingleton();
		}
		return instance;
	}

	public boolean isHasNewModelLoaded() {
		return hasNewModelLoaded;
	}

	public void setHasNewModelLoaded(boolean hasNewModelLoaded) {
		this.hasNewModelLoaded = hasNewModelLoaded;
	}
	
	public boolean isHasLabelSizeDrawn() {
		return hasLabelSizeDrawn;
	}

	public void setHasLabelSizeDrawn(boolean hasLabelSizeDrawn) {
		this.hasLabelSizeDrawn = hasLabelSizeDrawn;
	}

	public boolean isHasActivationPatternDrawn() {
		return hasActivationPatternDrawn;
	}

	public void setHasActivationPatternDrawn(boolean hasActivationPatternDrawn) {
		this.hasActivationPatternDrawn = hasActivationPatternDrawn;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Map<String, BigInteger> getLabelSizeData() {
		if (filePath == null || filePath.equals(""))
			return null;
		return LoadData.generateLabelSizeData(filePath);
	}

	public Map<String, BigInteger> getActivationPatternData() {
		if (filePath == null || filePath.equals(""))
			return null;
		return LoadData.generateActivationTimeData(filePath);
	}

	/**
	 * This code was adapted from the GeeksForGeeks website.
	 * 
	 * @param task_period_array
	 * @return lowest common multiple (LCM) of the integers in the
	 *         task_period_array.
	 * @see <a href=
	 *      "https://www.geeksforgeeks.org/lcm-of-given-array-elements/">GeekForGeeks</a>
	 */
	public static long lcm_of_periods(int[] task_period_array) {
		long lcm_of_items = 1;
		int divisor = 2;

		while (true) {
			int counter = 0;
			boolean divisible = false;

			for (int i = 0; i < task_period_array.length; i++) {

				// lcm_of_items (n1, n2, ... 0) = 0.
				// For negative number we convert into
				// positive and calculate lcm_of_items.

				if (task_period_array[i] == 0) {
					return 0;
				} else if (task_period_array[i] < 0) {
					task_period_array[i] = task_period_array[i] * (-1);
				}
				if (task_period_array[i] == 1) {
					counter++;
				}

				// Divide task_period_array by divisor if complete
				// division i.e. without remainder then replace
				// number with quotient; used for find next factor
				if (task_period_array[i] % divisor == 0) {
					divisible = true;
					task_period_array[i] = task_period_array[i] / divisor;
				}
			}

			// If divisor able to completely divide any number
			// from array multiply with lcm_of_items
			// and store into lcm_of_items and continue
			// to same divisor for next factor finding.
			// else increment divisor
			if (divisible) {
				lcm_of_items = lcm_of_items * divisor;
			} else {
				divisor++;
			}

			// Check if all task_period_array is 1 indicate
			// we found all factors and terminate while loop.
			if (counter == task_period_array.length) {
				return lcm_of_items;
			}
		}
	}
}
