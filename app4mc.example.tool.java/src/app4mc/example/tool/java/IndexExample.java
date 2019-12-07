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
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.app4mc.amalthea.model.Amalthea;
import org.eclipse.app4mc.amalthea.model.AmaltheaFactory;
import org.eclipse.app4mc.amalthea.model.AmaltheaIndex;
import org.eclipse.app4mc.amalthea.model.IReferable;
import  org.eclipse.app4mc.amalthea.model.Label;
import org.eclipse.app4mc.amalthea.model.Tag;
import org.eclipse.app4mc.amalthea.model.io.AmaltheaLoader;
import org.eclipse.app4mc.amalthea.model.util.ModelUtil;
import org.eclipse.emf.ecore.EObject;

public class IndexExample {

	public static void main(String[] args) {

		// example: absolute path
		// final File inputFile = new File("d:/temp/democar.amxmi");

		// example: relative path
		final File inputFile = new File("model-input/democar.amxmi");

		// ***** Load *****

		Amalthea model = AmaltheaLoader.loadFromFile(inputFile);

		if (model == null) {
			System.out.println("Error: No model loaded!");
			return;
		}
		
		// ***** Modify *****

		final AmaltheaFactory fac = AmaltheaFactory.eINSTANCE;

		for (int i = 0; i < 3; i++) {
			Tag tag = fac.createTag();
			tag.setName("Tx");
			ModelUtil.getOrCreateCommonElements(model).getTags().add(tag);
			
			Label lab = fac.createLabel();
			lab.setName("LabZ");
			ModelUtil.getOrCreateSwModel(model).getLabels().add(lab);
		}
		
		// ***** Dump index info *****
		

		System.out.println("\n\n*** Index 1: Basic info ***\n");
				
		AmaltheaIndex.dumpAdapterInfo(model, 1, null); // basic info
	
		
		System.out.println("\n\n*** Index 2: Cross reference map ***\n");
		
		AmaltheaIndex.dumpAdapterInfo(model, 2, null); // cross reference map
		

		System.out.println("\n\n*** Index 3: Name index ***\n");
		
		AmaltheaIndex.dumpAdapterInfo(model, 3, null); // name index

		
		// ***** Use index 1: Get referring objects *****

		String nameOfLabel = "APedSensor1Voltage";
		

		System.out.println("\n\n*** Objects referring to " + nameOfLabel + " ***\n");

		Set<Label> list = AmaltheaIndex.getElements(model, nameOfLabel, Label.class);
		for (Label label : list) {
			Collection<EObject> result = AmaltheaIndex.getReferringObjects(label);
			
			System.out.println(result);
		}

		
		// ***** Use index 2: Get name conflicts (ID) *****
		
		System.out.println("\n\n*** ID Conflicts ***\n");
		
		List<Set<IReferable>> conflicts = AmaltheaIndex.getObjectsWithConflictingNames(model);
		System.out.println(conflicts);
		
		
		System.out.println();
		System.out.println("done");
	}

}
