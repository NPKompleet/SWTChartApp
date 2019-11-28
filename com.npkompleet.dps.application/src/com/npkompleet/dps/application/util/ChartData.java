package com.npkompleet.dps.application.util;

import org.eclipse.e4.core.di.annotations.Creatable;

/**
 * A class that holds the important information needed to construct 
 * an SWTChart line or bar chart
 * @author Phenomenon
 *
 */
@Creatable
public class ChartData {
	private String title;
	private String type;
	private String[] xValues;
	private double[] yValues;
	private String xLabel;
	private String yLabel;

	public ChartData() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getxValues() {
		return xValues;
	}

	public void setxValues(String[] xValues) {
		this.xValues = xValues;
	}

	public double[] getyValues() {
		return yValues;
	}

	public void setyValues(double[] yValues) {
		this.yValues = yValues;
	}

	public String getxLabel() {
		return xLabel;
	}

	public void setxLabel(String xLabel) {
		this.xLabel = xLabel;
	}

	public String getyLabel() {
		return yLabel;
	}

	public void setyLabel(String yLabel) {
		this.yLabel = yLabel;
	}
	

}
