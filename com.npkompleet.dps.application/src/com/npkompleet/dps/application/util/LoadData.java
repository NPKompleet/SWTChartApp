/**
 * 
 */

package com.npkompleet.dps.application.util;

import java.io.File;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.app4mc.amalthea.model.Amalthea;
import org.eclipse.app4mc.amalthea.model.Label;
import org.eclipse.app4mc.amalthea.model.PeriodicStimulus;
import org.eclipse.app4mc.amalthea.model.Stimulus;
import org.eclipse.app4mc.amalthea.model.Task;
import org.eclipse.app4mc.amalthea.model.Time;
import org.eclipse.app4mc.amalthea.model.TimeUnit;
import org.eclipse.app4mc.amalthea.model.io.AmaltheaLoader;
import org.eclipse.app4mc.amalthea.model.util.SoftwareUtil;

/**
 * This class is used to read from App4MC model files and generate the data
 * points that will be used to plot the bar and line charts
 */
public class LoadData {

	public LoadData() {

	}

	/**
	 * Computes the label sizes of tasks in an App4MC model
	 * 
	 * @param path the file path of the App4MC model
	 * @return a {@link LinkedHashMap} of tasks and their computed label sizes
	 */
	public static Map<String, BigInteger> generateLabelSizeData(String path) {
		Map<String, BigInteger> taskListMap = new LinkedHashMap<>();

		final File inputFile = new File(path);

		Amalthea model = AmaltheaLoader.loadFromFile(inputFile);

		if (model == null) {
			System.out.println("Error: No model loaded!");
			return null;
		}

		List<Task> taskList = model.getSwModel().getTasks();
		for (Task task : taskList) {
			Set<Label> lLabels = SoftwareUtil.getAccessedLabelSet(task, null);

			BigInteger labelSize = lLabels.stream().map(x -> x.getSize().getValue()).reduce(BigInteger.ZERO,
					(BigInteger a, BigInteger b) -> a.add(b));
			taskListMap.put(task.getName(), labelSize);
			System.out.println(labelSize);
			System.out.println(task.getName());
		}
		return taskListMap;
	}

	/**
	 * Gets a collection of tasks with periodic stimulus
	 * 
	 * @param path the file path of the App4MC model
	 * @return a {@link LinkedHashMap} of each task and its period
	 */
	public static Map<String, BigInteger> generateActivationTimeData(String path) {
		Map<String, BigInteger> taskListMap = new LinkedHashMap<>();

		final File inputFile = new File(path);

		Amalthea model = AmaltheaLoader.loadFromFile(inputFile);

		if (model == null) {
			System.out.println("Error: No model loaded!");
			return null;
		}

		List<Task> taskList = model.getSwModel().getTasks();
		for (Task task : taskList) {
			Stimulus stimuli = task.getStimuli().get(0);
			if (stimuli instanceof PeriodicStimulus) {
				Time period = ((PeriodicStimulus) stimuli).getRecurrence();
				period = alignPeriodToMS(period);
				System.out.println("Period " + period);
				taskListMap.put(task.getName(), period.getValue());
			}
		}
		return taskListMap;
	}

	public static Time alignPeriodToMS(Time period) {
		switch (period.getUnit().getName()) {
		case "s":
			period.setValue(period.getValue().multiply(BigInteger.valueOf(1000)));
			period.setUnit(TimeUnit.MS);
			break;
		case "us":
			period.setValue(period.getValue().divide(BigInteger.valueOf(1000)));
			period.setUnit(TimeUnit.MS);
			break;
		case "ns":
			period.setValue(period.getValue().divide(BigInteger.valueOf(1000000)));
			period.setUnit(TimeUnit.MS);
			break;
		case "ps":
			period.setValue(period.getValue().divide(BigInteger.valueOf(1000000000)));
			period.setUnit(TimeUnit.MS);
			break;
		default:
			break;
		}
		return period;
	}
}
