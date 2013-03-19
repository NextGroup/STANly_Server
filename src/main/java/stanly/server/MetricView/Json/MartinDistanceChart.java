package stanly.server.MetricView.Json;

import java.util.ArrayList;

public class MartinDistanceChart {
	private ArrayList<MetricValue> metricDistance;
	
	public MartinDistanceChart()
	{
		metricDistance = new ArrayList<MetricValue>();
	}
	//Package Metric
	public boolean 	addAbstractness(float e) {
			return metricDistance.add(new MetricValue("Abstractness",e));
	}
	public boolean 	addInstability(float e) {
		
			return metricDistance.add(new MetricValue("Instability",e));
	}
		
	public boolean 	addDistance(float e) {
		
			return metricDistance.add(new MetricValue("Distance",e));
	}
	public boolean 	addLOC(float e) {
			return metricDistance.add(new MetricValue("LOC",e));
	}
	
	public ArrayList<MetricValue> getMetricDistance() {
		return metricDistance;
	}
}
