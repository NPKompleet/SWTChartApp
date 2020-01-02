package com.npkompleet.dps.application.parts;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.npkompleet.dps.application.util.ChartDataSingleton;

public class MainPart {
	private Label label;
	private Text text;

	@PostConstruct
	public void create(Composite parent) {

		parent.setLayout(new GridLayout(2, false));

		label = new Label(parent, SWT.NONE);
		label.setText("Please load the file to read from");
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));

		text = new Text(parent, SWT.BORDER);
		text.setMessage("Choose file...");
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		text.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event event) {
				ChartDataSingleton.getInstance().setFilePath(text.getText());
			}
		});

		Button button = new Button(parent, SWT.PUSH);
		button.setText("File");

		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Open a file dialog that reads only .amxmi files
				FileDialog dialog = new FileDialog(new Shell(SWT.CENTER), SWT.OPEN);
				dialog.setText("Open Model File");
				dialog.setFilterExtensions(new String[] { "*.amxmi" });
				dialog.open();
				String filePath = dialog.getFilterPath().concat("\\").concat(dialog.getFileName());
				text.setText(filePath);

			}
		});

	}

	@Focus
	void onFocus() {
		label.setFocus();
	}
}
