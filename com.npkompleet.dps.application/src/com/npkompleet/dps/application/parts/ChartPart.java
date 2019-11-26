package com.npkompleet.dps.application.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swtchart.Chart;


public class ChartPart {
	private Text text;
    private Chart chart;
    

    @PostConstruct
    public void createControls(Composite parent) {
        parent.setLayout(new GridLayout(2, false));

        text = new Text(parent, SWT.BORDER);
        text.setMessage("Choose file...");
        text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        Button button = new Button(parent, SWT.PUSH);
        button.setText("File");
        
        button.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                text.setText("button clicked");
                FileDialog dialog = new FileDialog(new Shell(SWT.CENTER), SWT.OPEN);
                dialog.open();
                
            }
        });

        
        chart = new Chart(parent, SWT.NONE);
        chart.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));


    }

    @Focus
    public void onFocus() {
        text.setFocus();
        
    }

}
