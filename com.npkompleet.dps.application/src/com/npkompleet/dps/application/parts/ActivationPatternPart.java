package com.npkompleet.dps.application.parts;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swtchart.Chart;

public class ActivationPatternPart {
	@PostConstruct
    public void createControls(Composite parent) {
        parent.setLayout(new GridLayout(2, false));
        
        Chart chart = new Chart(parent, SWT.NONE);
        chart.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
        // set titles
        chart.getTitle().setText("Activation Patterns");
        chart.getAxisSet().getXAxis(0).getTitle().setText("Period");
        chart.getAxisSet().getYAxis(0).getTitle().setText("Number");
    }

    @Focus
    public void onFocus() {
        
    }

}
