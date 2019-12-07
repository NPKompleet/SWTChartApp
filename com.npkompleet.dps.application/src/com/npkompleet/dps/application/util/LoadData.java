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
import org.eclipse.app4mc.amalthea.model.io.AmaltheaLoader;
import org.eclipse.app4mc.amalthea.model.util.SoftwareUtil;

public class LoadData {

	public LoadData() {

	}

	
	public static Map<String, BigInteger> generateChartData() {
		Map<String, BigInteger> taskListMap = new LinkedHashMap<>();
		
		final File inputFile = new File("model-input/democar.amxmi");

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
				System.out.println("Period " + period);
			}
			Set<Label> lLabels = SoftwareUtil.getAccessedLabelSet(task, null);
			
			BigInteger labelSize = lLabels.stream().map(x -> x.getSize().getValue())
				.reduce(BigInteger.ZERO, (BigInteger a, BigInteger b) -> a.add(b) );
			taskListMap.put(task.getName(), labelSize);
			System.out.println(labelSize);
			System.out.println(task.getName());
		}
		return taskListMap;
	}

}
