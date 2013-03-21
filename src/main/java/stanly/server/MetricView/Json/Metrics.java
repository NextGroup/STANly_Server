package stanly.server.MetricView.Json;

import java.util.ArrayList;

public class Metrics {
	private ArrayList<MetricValue> metricList;
	public Metrics()
	{
		metricList = new ArrayList<MetricValue>();
	}
	public boolean 	addValue(String name,float e) {
		return metricList.add(new MetricValue(name,e));
	}
}
