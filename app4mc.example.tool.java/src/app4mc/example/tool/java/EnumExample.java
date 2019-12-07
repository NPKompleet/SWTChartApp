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

import java.util.Set;

import org.eclipse.app4mc.amalthea.model.ASILType;
import org.eclipse.app4mc.amalthea.model.Amalthea;
import org.eclipse.app4mc.amalthea.model.AmaltheaFactory;
import org.eclipse.app4mc.amalthea.model.AmaltheaIndex;
import org.eclipse.app4mc.amalthea.model.EnumMode;
import org.eclipse.app4mc.amalthea.model.ModeLabel;
import org.eclipse.app4mc.amalthea.model.Label;
import org.eclipse.app4mc.amalthea.model.LabelAccess;
import org.eclipse.app4mc.amalthea.model.LabelAccessEnum;
import org.eclipse.app4mc.amalthea.model.Mode;
import org.eclipse.app4mc.amalthea.model.ModeLiteral;
import org.eclipse.app4mc.amalthea.model.Runnable;
import org.eclipse.app4mc.amalthea.model.RunnableCall;
import org.eclipse.app4mc.amalthea.model.SWModel;

public class EnumExample {

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Amalthea model = AmaltheaFactory.eINSTANCE.createAmalthea();
		SWModel sw = AmaltheaFactory.eINSTANCE.createSWModel();
		model.setSwModel(sw);
		Runnable run = addNewRunnable(sw, "Runner-1");

		// ***** How to handle enumerations *****

		if (run.getAsilLevel() == ASILType.A)
			System.out.println("ASIL level A !");

		switch (run.getAsilLevel()) {
		case _UNDEFINED_:
		case QM:
		case A:
		case B:
		case C:
		case D:
			break;

		default:
			break;
		}

		ASILType.values();

		// ***** How to handle modes *****

		EnumMode mode = addNewMode(sw, "Shape");
		addNewModeLiteral(mode, "_undefined_");
		addNewModeLiteral(mode, "Rectangle");
		addNewModeLiteral(mode, "Circle");
		addNewModeLiteral(mode, "Line");
		
		ModeLabel modeLabel = AmaltheaFactory.eINSTANCE.createModeLabel();
		modeLabel.setName("myShape");
		sw.getModeLabels().add(modeLabel);
		
		modeLabel.setMode(mode);
		modeLabel.setInitialValue(mode.getLiterals().get(0).getName());

		Set<? extends Mode> modeSet = AmaltheaIndex.getElements(model, "Shape", Mode.class);
		
		
//		if (modeLabel.getInitialValue() == sw.getModeLiteral("Shape::Line"))
		
		mode.getLiterals();
		
		
		// ***** LABEL ACCESS *****
		
		addNewLabelAccess(sw, run, "Label A", LabelAccessEnum.READ);
		addNewLabelAccess(sw, run, "Label B", LabelAccessEnum.READ);
		addNewLabelAccess(sw, run, "Label C", LabelAccessEnum.WRITE);
		addNewLabelAccess(sw, run, "Label D", LabelAccessEnum.WRITE);

		addNewRunnableCall(sw, run, "Service A");
		addNewRunnableCall(sw, run, "Service B");


		System.out.println("done");
	}

	private static ModeLiteral addNewModeLiteral(EnumMode m, String literalName) {
		ModeLiteral lit = AmaltheaFactory.eINSTANCE.createModeLiteral();
		lit.setName(literalName);
		m.getLiterals().add(lit);

		return lit;
	}

	private static EnumMode addNewMode(SWModel sw, String modeName) {
		EnumMode m = AmaltheaFactory.eINSTANCE.createEnumMode();
		m.setName(modeName);
		sw.getModes().add(m);

		return m;
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
