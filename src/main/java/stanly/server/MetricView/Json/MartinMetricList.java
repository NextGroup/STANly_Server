package stanly.server.MetricView.Json;

import java.util.ArrayList;

public class MartinMetricList {
	private ArrayList<MetricValue> metricList;
	
	public MartinMetricList()
	{
		metricList = new ArrayList<MetricValue>();
	}

	public boolean 	addDistance(MetricValue e) {
		if(e.getMetric()=="Distance")
			return metricList.add(e);
		else
			return false;
	}
	public boolean 	addAbstractness(MetricValue e) {
		if(e.getMetric()=="Abstractness")
			return metricList.add(e);
		else
			return false;
	}
	public boolean 	addInstability(MetricValue e) {
		if(e.getMetric()=="Instability")
			return metricList.add(e);
		else
			return false;
	}
	public boolean 	addEfferentCoupling(MetricValue e) {
		if(e.getMetric()=="EfferentCoupling")
			return metricList.add(e);
		else
			return false;
	}
	public boolean 	addAfferentCoupling(MetricValue e) {
		if(e.getMetric()=="AfferentCoupling")
			return metricList.add(e);
		else
			return false;
	}
	
	public ArrayList<MetricValue> getMetricList() {
		return metricList;
	}
	
	
	
	
}
