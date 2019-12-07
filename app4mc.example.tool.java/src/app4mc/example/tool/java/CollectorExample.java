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
import java.util.List;

import org.eclipse.app4mc.amalthea.model.Amalthea;
import org.eclipse.app4mc.amalthea.model.AmaltheaFactory;
import org.eclipse.app4mc.amalthea.model.CallGraphItem;
import org.eclipse.app4mc.amalthea.model.Label;
import org.eclipse.app4mc.amalthea.model.LabelAccess;
import org.eclipse.app4mc.amalthea.model.LabelAccessEnum;
import org.eclipse.app4mc.amalthea.model.Runnable;
import org.eclipse.app4mc.amalthea.model.RunnableCall;
import org.eclipse.app4mc.amalthea.model.SWModel;
import org.eclipse.app4mc.amalthea.model.io.AmaltheaWriter;
import org.eclipse.app4mc.amalthea.model.util.SoftwareUtil;

public class CollectorExample {

	public static void main(String[] args) {

		final File outputFile = new File("model-output/Collector/CollectorModel.amxmi");
		
		// ***** Create model *****
		
		Amalthea model = AmaltheaFactory.eINSTANCE.createAmalthea();
		SWModel sw = AmaltheaFactory.eINSTANCE.createSWModel();
		model.setSwModel(sw);
		Runnable run = addNewRunnable(sw, "Runner-1");

		addNewLabelAccess(sw, run, "Label A", LabelAccessEnum.READ);
		addNewLabelAccess(sw, run, "Label B", LabelAccessEnum.READ);
		addNewLabelAccess(sw, run, "Label C", LabelAccessEnum.WRITE);
		addNewLabelAccess(sw, run, "Label D", LabelAccessEnum.WRITE);

		addNewRunnableCall(sw, run, "Service A");
		addNewRunnableCall(sw, run, "Service B");

		// ***** Save model *****

		AmaltheaWriter.writeToFile(model, outputFile);
		
		System.out.println("done");
		
		// ***** Some tests *****

		List<CallGraphItem> items1 = SoftwareUtil.collectCallGraphItems(run.getCallGraph());

		System.out.println("\nAll items:");
		for (CallGraphItem item : items1) {
			System.out.println(" - " + item.toString());
		}
		
		List<CallGraphItem> items2 = SoftwareUtil.collectCallGraphItems(run.getCallGraph(), null,
				a -> a instanceof LabelAccess && ((LabelAccess) a).getAccess() == LabelAccessEnum.READ);

		System.out.println("\nLabel accesses (read):");
		for (CallGraphItem item : items2) {
			System.out.println(" - " + item.toString());
		}
		
		List<LabelAccess> items3 = SoftwareUtil.collectCallGraphItems(run.getCallGraph(), null, LabelAccess.class);
		
		System.out.println("\nLabel accesses:");
		for (LabelAccess item : items3) {
			System.out.println(" - " + item.eClass().getName() + " " + item.getData().getName());
		}
		

		List<LabelAccess> items4 = SoftwareUtil.collectCallGraphItems(run.getCallGraph(), null, LabelAccess.class, a -> isWritingLabelAccess(a));

		System.out.println("\nLabel accesses (write):");
		for (LabelAccess item : items4) {
			System.out.println(" - " + item.eClass().getName() + " " + item.getData().getName());
		}
		
	}

	private static boolean isWritingLabelAccess(LabelAccess access) {
		return access.getAccess() == LabelAccessEnum.WRITE;
	}

	private static LabelAccess addNewLabelAccess(SWModel sw, Runnable r, String labelName, LabelAccessEnum rw) {
		Label l = addNewLabel(sw, labelName);

		LabelAccess la = AmaltheaFactory.eINSTANCE.createLabelAccess();
		la.setData(l);
		la.setAccess(rw);
		r.getRunnableItems().add(la);

		return la;
	}

	private static Label addNewLabel(SWModel sw, String labelName) {
		Label lab = AmaltheaFactory.eINSTANCE.createLabel();
		lab.setName(labelName);
		sw.getLabels().add(lab);

		return lab;
	}

	private static RunnableCall addNewRunnableCall(SWModel sw, Runnable r, String runnableName) {
		Runnable r2 = addNewRunnable(sw, runnableName);

		RunnableCall rc = AmaltheaFactory.eINSTANCE.createRunnableCall();
		rc.setRunnable(r2);
		r.getRunnableItems().add(rc);

		return rc;
	}

	private static Runnable addNewRunnable(SWModel sw, String runnableName) {
		Runnable run = AmaltheaFactory.eINSTANCE.createRunnable();
		run.setName(runnableName);
		sw.getRunnables().add(run);

		return run;
	}

}
