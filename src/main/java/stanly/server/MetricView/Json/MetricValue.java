package stanly.server.MetricView.Json;

public class MetricValue {
	private String Metric;
	private float value;
	
	
	
	public MetricValue(String metric, float value) {
		super();
		Metric = metric;
		this.value = value;
	}
	public String getMetric() {
		return Metric;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	
	
}
