package stanly.server.MetricView.Json;

public class MartinDistanceValue {
	private String name;
	private float abstractness;
	private float instability;
	private int size;
	
	MartinDistanceValue(String name,float abstractness,float instability,int size)
	{
		this.name = name;
		this.abstractness = abstractness;
		this.instability = instability;
		this.size = size;
	}
}
