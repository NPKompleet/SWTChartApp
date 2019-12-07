package com.npkompleet.dps.application.parts;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtchart.Chart;
import org.eclipse.swtchart.IAxis;
import org.eclipse.swtchart.IBarSeries;
import org.eclipse.swtchart.ISeries.SeriesType;

import com.npkompleet.dps.application.util.LoadData;

public class LabelSizePart {

	@Inject
	LoadData loadData;

	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		Chart chart = new Chart(parent, SWT.NONE);

		// set titles
		chart.getTitle().setText("Label Sizes");
		chart.getAxisSet().getXAxis(0).getTitle().setText("Tasks");
		chart.getAxisSet().getYAxis(0).getTitle().setText("Size");

		IBarSeries barSeries = (IBarSeries) chart.getSeriesSet().createSeries(SeriesType.BAR, "bar series");

		LinkedHashMap<String, BigInteger> dataMap = (LinkedHashMap<String, BigInteger>) loadData.generateChartData();
		System.out.println(dataMap.size());
		
		String[] xValues = new String[dataMap.size()];
		dataMap.keySet().toArray(xValues);
		double[] yValues = dataMap.values().stream().map(x -> x.doubleValue()).mapToDouble(Double::doubleValue)
				.toArray();
		barSeries.setYSeries(yValues);
		Color color = new Color(Display.getDefault(), 0, 0, 180);
		barSeries.setBarColor(color);
		IAxis xAxis = chart.getAxisSet().getXAxis(0);
		xAxis.setCategorySeries(xValues);
		xAxis.enableCategory(true);
		
		chart.getAxisSet().adjustRange();

		chart.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
	}

	@Focus
	public void onFocus() {

	}

}
