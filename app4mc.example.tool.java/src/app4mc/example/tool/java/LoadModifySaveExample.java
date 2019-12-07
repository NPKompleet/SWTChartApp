/**
 ********************************************************************************
 * Copyright (c) 2018 Robert Bosch GmbH.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Robert Bosch GmbH - initial API and implementation
 ********************************************************************************
 */

package app4mc.example.tool.java;

import java.io.File;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.app4mc.amalthea.model.Amalthea;
import org.eclipse.app4mc.amalthea.model.AmaltheaFactory;
import org.eclipse.app4mc.amalthea.model.Label;
import org.eclipse.app4mc.amalthea.model.PeriodicStimulus;
import org.eclipse.app4mc.amalthea.model.Stimulus;
import org.eclipse.app4mc.amalthea.model.Tag;
import org.eclipse.app4mc.amalthea.model.Task;
import org.eclipse.app4mc.amalthea.model.Time;
import org.eclipse.app4mc.amalthea.model.io.AmaltheaLoader;
import org.eclipse.app4mc.amalthea.model.io.AmaltheaWriter;
import org.eclipse.app4mc.amalthea.model.util.ModelUtil;
import org.eclipse.app4mc.amalthea.model.util.SoftwareUtil;

public class LoadModifySaveExample {

	public LoadModifySaveExample() {

	}

	public static void main(String[] args) {

		// example: absolute path
		// final File inputFile = new File("d:/temp/democar.amxmi");
		// final File outputFile = new File("d:/temp/democar_1.amxmi");

		// example: relative path
		final File inputFile = new File("model-input/democar.amxmi");
		final File outputFile = new File("model-output/LoadModifySave/democar_1.amxmi");

		// ***** Load *****

		Amalthea model = AmaltheaLoader.loadFromFile(inputFile);

		if (model == null) {
			System.out.println("Error: No model loaded!");
			return;
		}

		// ***** Modify *****

		final AmaltheaFactory fac = AmaltheaFactory.eINSTANCE;

		Tag tag = fac.createTag();
		tag.setName("The new tag!");
		ModelUtil.getOrCreateCommonElements(model).getTags().add(tag);

		// ***** Save *****

		AmaltheaWriter.writeToFile(model, outputFile);

		System.out.println("done");
		generateChartData();
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
			
			lLabels.stream().findFirst().get().getSize().getNumberBytes();
			BigInteger labelSize = lLabels.stream().map(x -> x.getSize().getValue())
				.reduce(BigInteger.ZERO, (BigInteger a, BigInteger b) -> a.add(b) );
			taskListMap.put(task.getName(), labelSize);
			System.out.println(labelSize);
			System.out.println(task.getName());
		}
		return taskListMap;
	}

}
