package com.npkompleet.dps.application.parts;

import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.ui.di.Focus;
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
import org.eclipse.swtchart.IAxis;
import org.eclipse.swtchart.IBarSeries;
import org.eclipse.swtchart.ILineSeries;
import org.eclipse.swtchart.ISeries.SeriesType;

import com.npkompleet.dps.application.util.ChartData;
import com.npkompleet.dps.application.util.ValueFileReader;

@Creatable
public class ChartPart {
	private Text text;
    private Chart chart;
    
    @Inject
    private ChartData chartData;
    @Inject
    private ValueFileReader fileReader;

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
            	// Open a file dialog that reads only JSON files
                FileDialog dialog = new FileDialog(new Shell(SWT.CENTER), SWT.OPEN);
                dialog.setFilterExtensions(new String[] {"*.txt"});
                dialog.open();
                String filePath = dialog.getFilterPath().concat("\\").concat(dialog.getFileName());
                text.setText(filePath);
                
                chartData = fileReader.parseFile(filePath);
                
                // set titles
                chart.getTitle().setText(chartData.getTitle());
                chart.getAxisSet().getXAxis(0).getTitle().setText(chartData.getxLabel());
                chart.getAxisSet().getYAxis(0).getTitle().setText(chartData.getyLabel());

                
                // Check if it is a line chart and if so covert the x-axis values of
                // the chart to double array format instead of a string array
                if (chartData.getType().equals("line")) {
                	// create line series
                    ILineSeries lineSeries = (ILineSeries) chart.getSeriesSet()
                        .createSeries(SeriesType.LINE, "line series");
                    double[] xSeries;
                    
                	xSeries = Stream.of(chartData.getxValues())
                			.map(x -> Double.parseDouble(x))
        					.mapToDouble(Double :: doubleValue)
        					.toArray();
                	lineSeries.setXSeries(xSeries);
                    lineSeries.setYSeries(chartData.getyValues());
                }
                
                else if (chartData.getType().equals("bar")) {
                	// create bar series
                	IBarSeries barSeries = (IBarSeries) chart.getSeriesSet()
                	    .createSeries(SeriesType.BAR, "bar series");
                	barSeries.setYSeries(chartData.getyValues());
                	IAxis xAxis = chart.getAxisSet().getXAxis(0);
                	xAxis.setCategorySeries(chartData.getxValues());
                	xAxis.enableCategory(true);
                }
                
                chart.redraw();
                chart.getAxisSet().adjustRange();
                
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
