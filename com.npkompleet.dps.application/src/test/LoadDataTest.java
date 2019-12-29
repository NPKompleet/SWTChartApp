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

import com.npkompleet.dps.application.util.LoadData;

public class LoadDataTest {

	LoadData loadData;
	String filePath;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		loadData = new LoadData();
		filePath = System.getProperty("user.dir").concat("\\model-input\\democar.amxmi");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGenerateLabelSizeData() {
		Map<String, BigInteger> taskListMap = new LinkedHashMap<>();
		taskListMap.put("Task_10MS", new BigInteger(toByteArray(760)));
		taskListMap.put("Task_20MS", new BigInteger(toByteArray(24)));
		taskListMap.put("Task_5MS", new BigInteger(toByteArray(256)));
		assertEquals(taskListMap, loadData.generateLabelSizeData(filePath));
	}

	@Test
	public void testGenerateLabelSizeDataWithNoPath() {
		String filePath = "";
		assertEquals(null, loadData.generateLabelSizeData(filePath));
	}

	@Test
	public void testGenerateActivationTimeData() {
		Map<String, BigInteger> taskListMap = new LinkedHashMap<>();
		taskListMap.put("Task_10MS", new BigInteger(toByteArray(10)));
		taskListMap.put("Task_20MS", new BigInteger(toByteArray(20)));
		taskListMap.put("Task_5MS", new BigInteger(toByteArray(5)));
		assertEquals(taskListMap, loadData.generateActivationTimeData(filePath));
	}

	@Test
	public void testGenerateActivationTimeDataWithNoPath() {
		String filePath = "";
		assertEquals(null, loadData.generateActivationTimeData(filePath));
	}

	byte[] toByteArray(int value) {
		return ByteBuffer.allocate(4).putInt(value).array();
	}
}
