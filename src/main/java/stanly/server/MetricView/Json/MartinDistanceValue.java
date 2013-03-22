package stanly.server.MetricView.Json;

public class MartinDistanceValue {
	private String name;
	private float abstractness;
	private float instability;
	private float Distance;
	private int size;
	
	public MartinDistanceValue(String name, float abstractness,
			float instability, float distance, int size) {
		super();
		this.name = name;
		this.abstractness = abstractness;
		this.instability = instability;
		Distance = distance;
		this.size = size;
	}

	MartinDistanceValue(String name,float abstractness,float instability,int size)
	{
		this.name = name;
		this.abstractness = abstractness;
		this.instability = instability;
		this.size = size;
	}
}
