package stanly.server.MetricView.Json;

public class MetricValue {
	private String Metric;
	private int value;
	
	
	
	public MetricValue(String metric, int value) {
		super();
		Metric = metric;
		this.value = value;
	}
	public String getMetric() {
		return Metric;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	
}
