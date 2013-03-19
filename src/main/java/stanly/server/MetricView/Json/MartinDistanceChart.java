package stanly.server.MetricView.Json;

import java.util.ArrayList;

public class MartinDistanceChart {
	private ArrayList<MartinDistanceValue> metricDistance;
	
	public MartinDistanceChart()
	{
		metricDistance = new ArrayList<MartinDistanceValue>();
	}
	//Package Metric
	public boolean addPackage(String packageName,float abstractness,float instability,int size) {
		return metricDistance.add(new MartinDistanceValue(packageName,abstractness,instability,size));		
	}
}
