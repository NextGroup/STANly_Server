package stanly.server.MetricView.Json;

import java.util.ArrayList;

public class MartinMetricList {
	private ArrayList<MetricValue> metricList;
	
	public MartinMetricList()
	{
		metricList = new ArrayList<MetricValue>();
	}
	//Package Metric
	public boolean 	addDistance(float e) {
			return metricList.add(new MetricValue("Distance",e));
	}
	public boolean 	addAbstractness(float e) {
			return metricList.add(new MetricValue("Abstractness",e));
	}
	public boolean 	addInstability(float e) {
		
			return metricList.add(new MetricValue("Instability",e));
	}
		
	public boolean 	addEfferentCoupling(float e) {
		
			return metricList.add(new MetricValue("EfferentCoupling",e));
	}
	public boolean 	addAfferentCoupling(float e) {
			return metricList.add(new MetricValue("AfferentCoupling",e));
	}
	
	public ArrayList<MetricValue> getMetricList() {
		return metricList;
	}
	
	
	
	
}
