package stanly.server.detailPollution.JSON;

import java.util.HashMap;

public class ChainReDetail {
	private String name;
	private HashMap<String, Float> data;
	public ChainReDetail(String name)
	{
		this.name=name;
		data = new HashMap<String, Float>();
	}
	
	public ChainReDetail setDistance(float distance)
	{
		data.put("Balancing Abstractness", distance);
		return this;
	}
	public ChainReDetail setDIT(int DIT)
	{
		data.put("Depth of Inheritance Tree", (float)DIT);
		return this;
	}
}
