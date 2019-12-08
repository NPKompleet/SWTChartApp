package com.npkompleet.dps.application.util;

import java.math.BigInteger;
import java.util.Map;


public class ChartDataSingleton {
	private static ChartDataSingleton instance;
	 
	private LoadData loadData;
	
	private String filePath;
	
	private ChartDataSingleton() {
		
	}
	
	public static synchronized ChartDataSingleton getInstance() {
		if(instance == null) {
			instance = new ChartDataSingleton();
		}
		return instance;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public Map<String, BigInteger> getLabelSizeData(){
		if (filePath == null || filePath.equals("")) return null;
		loadData = new LoadData();
		return loadData.generateLabelSizeData(filePath);
	}

}
