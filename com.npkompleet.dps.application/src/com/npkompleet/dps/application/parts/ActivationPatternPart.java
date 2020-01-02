package com.npkompleet.dps.application.parts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtchart.Chart;
import org.eclipse.swtchart.ILineSeries;
import org.eclipse.swtchart.ISeries;
import org.eclipse.swtchart.ISeries.SeriesType;
import org.eclipse.swtchart.ISeriesSet;

import com.npkompleet.dps.application.util.ChartDataSingleton;
import com.npkompleet.dps.application.util.Constants;

public class ActivationPatternPart {
	Chart chart;
	ChartDataSingleton chartData = ChartDataSingleton.getInstance();
	long periodLCM;
	Color[] colorList = new Color[] { new Color(Display.getDefault(), 255, 0, 0), // Red
			new Color(Display.getDefault(), 255, 255, 0), // Yellow
			new Color(Display.getDefault(), 0, 0, 255), // Blue
			new Color(Display.getDefault(), 0, 255, 0) // Green
	};

	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		chart = new Chart(parent, SWT.NONE);
		chart.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		// set titles
		chart.getTitle().setText("Activation Patterns");
		chart.getAxisSet().getXAxis(0).getTitle().setText("Period");
		chart.getAxisSet().getYAxis(0).getTitle().setText("Number");
	}

	@Focus
	public void onFocus() {

	}

	// tracks the active part and draw chart if needed
	@Inject
	@Optional
	public void receiveActivePart(@Named(IServiceConstants.ACTIVE_PART) MPart activePart) {
		if (activePart != null && activePart.getLabel().equals(Constants.ACTIVATION_PATTERN_LABEL)) {
			System.out.println("Active part changed " + activePart.getLabel());
			if (chart != null) {
				ChartDataSingleton chartData = ChartDataSingleton.getInstance();
				if (chartData.isHasNewModelLoaded()) {
					ISeries[] series = chart.getSeriesSet().getSeries();
					ISeriesSet seriesSet = chart.getSeriesSet();
					if (series.length > 0) {
						for (ISeries s : series) {
							seriesSet.deleteSeries(s.getId());
						}
					}
					drawChart();
					chartData.setHasActivationPatternDrawn(true);
					if (chartData.isHasLabelSizeDrawn()) {
						chartData.setHasNewModelLoaded(false);
					}
				}
			}
		}
	}

	private void drawChart() {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				createChart();
			}
		});
	}

	private void createChart() {
		LinkedHashMap<String, BigInteger> dataMap = (LinkedHashMap<String, BigInteger>) chartData
				.getActivationPatternData();
		if (dataMap == null) {
			MessageDialog.openInformation(new Shell(), "No File Found", "No File loaded to generate chart");
			return;
		}
		int[] holder = dataMap.values().stream().mapToInt(BigInteger::intValue).toArray();
		periodLCM = ChartDataSingleton.lcm_of_periods(holder);
		int index = 0;
		for (String task : dataMap.keySet()) {
			ILineSeries lineSeries = (ILineSeries) chart.getSeriesSet().createSeries(SeriesType.LINE, task);
			int taskPeriod = dataMap.get(task).intValue();
			List<Double> xValues = new ArrayList<>();
			List<Double> yValues = new ArrayList<>();
			double x = 0;
			double y = 0;
			for (int i = 0; i < periodLCM / taskPeriod; i++) {
				xValues.add(x);
				yValues.add(y);
				xValues.add(x);
				y++;
				yValues.add(y);
				x += taskPeriod;
			}
			xValues.add(Double.valueOf(periodLCM));
			yValues.add(y);

			lineSeries.setXSeries(xValues.stream().mapToDouble(Double::doubleValue).toArray());
			lineSeries.setYSeries(yValues.stream().mapToDouble(Double::doubleValue).toArray());
			lineSeries.setLineColor(colorList[index % colorList.length]);
			index++;

		}
		chart.getAxisSet().adjustRange();
	}

}
