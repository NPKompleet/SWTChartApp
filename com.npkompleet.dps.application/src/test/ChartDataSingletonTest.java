package test;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.npkompleet.dps.application.util.ChartDataSingleton;

public class ChartDataSingletonTest {

	ChartDataSingleton chartData;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		chartData = ChartDataSingleton.getInstance();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetLabelSizeData() {
		String filePath = "E:/Git2/SWTChartApp/com.npkompleet.dps.application/model-input/democar.amxmi";
		chartData.setFilePath(filePath);
		// Cover the getter method
		chartData.getFilePath();
		Map<String, BigInteger> taskListMap = new LinkedHashMap<>();
		taskListMap.put("Task_10MS", new BigInteger(toByteArray(760)));
		taskListMap.put("Task_20MS", new BigInteger(toByteArray(24)));
		taskListMap.put("Task_5MS", new BigInteger(toByteArray(256)));
		assertEquals(taskListMap, chartData.getLabelSizeData());
	}

	@Test
	public void testGetLabelSizeDataWithNoPath() {
		String filePath = "";
		chartData.setFilePath(filePath);
		assertEquals(null, chartData.getLabelSizeData());
	}

	@Test
	public void testGetActivationPatternData() {
		String filePath = "E:/Git2/SWTChartApp/com.npkompleet.dps.application/model-input/democar.amxmi";
		chartData.setFilePath(filePath);
		Map<String, BigInteger> taskListMap = new LinkedHashMap<>();
		taskListMap.put("Task_10MS", new BigInteger(toByteArray(10)));
		taskListMap.put("Task_20MS", new BigInteger(toByteArray(20)));
		taskListMap.put("Task_5MS", new BigInteger(toByteArray(5)));
		assertEquals(taskListMap, chartData.getActivationPatternData());
	}

	@Test
	public void testGetActivationPatternDataWithNoPath() {
		String filePath = "";
		chartData.setFilePath(filePath);
		assertEquals(null, chartData.getActivationPatternData());
	}

	byte[] toByteArray(int value) {
		return ByteBuffer.allocate(4).putInt(value).array();
	}

	@Test
	public void testLcmOfArrayElements() {
		String filePath = "E:/Git2/SWTChartApp/com.npkompleet.dps.application/model-input/democar.amxmi";
		chartData.setFilePath(filePath);
		LinkedHashMap<String, BigInteger> dataMap = (LinkedHashMap<String, BigInteger>) chartData
				.getActivationPatternData();
		int[] holder = dataMap.values().stream().mapToInt(BigInteger::intValue).toArray();
		assertEquals(20, ChartDataSingleton.lcm_of_array_elements(holder));
	}

}
