package com.npkompleet.dps.application.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;

@Creatable
public class ValueFileReader {
	@Inject
	ChartData chartData;
	
	public ValueFileReader() {
		
	}
	
	public ChartData parseFile(String filePath) {
		try {
			FileInputStream reader = new FileInputStream(filePath);
			BufferedReader bReader = new BufferedReader(new InputStreamReader(reader));
			
			int line = 0;
			String strLine;
			while ((strLine = bReader.readLine()) != null) {
				line++;
				switch(line) {
				case 1:
					chartData.setTitle(strLine.split(",")[1].trim());
					break;
				case 2:
					chartData.setType(strLine.split(",")[1].trim());
					break;
				case 3:
					// When on line 3, split the line using the commas as separators,
					// get rid of the first element, remove any leading or trailing
					// white space and convert the values into an array
					String[] xvalues = new String [strLine.split(",").length - 1];
					Stream.of(strLine.split(","))
					.skip(1)
					.map(x -> x.trim())
					.collect(Collectors.toList())
					.toArray(xvalues);
					
					chartData.setxValues(xvalues);
					break;
				case 4:
					// See case 3 above. Same process but the values are first converted
					// into Doubles before putting them in one array
					 
					double[] yvalues = Stream.of(strLine.split(","))
					.skip(1)
					.map(x -> x.trim())
					.map(x -> Double.parseDouble(x))
					.mapToDouble(Double :: doubleValue)
					.toArray();
					
					chartData.setyValues(yvalues);
					break;					
				case 5:
					chartData.setxLabel(strLine.split(",")[1].trim());
					break;
				case 6:
					chartData.setyLabel(strLine.split(",")[1].trim());
					break;
				}
			}
			reader.close();
			bReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return chartData;
	}

}
