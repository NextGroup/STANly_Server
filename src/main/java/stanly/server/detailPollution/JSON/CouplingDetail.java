package stanly.server.detailPollution.JSON;

import java.util.ArrayList;
import java.util.HashMap;

public class CouplingDetail {
	private String name;
	private HashMap<String, Float> data;
	public CouplingDetail(String name)
	{
		this.name=name;
		data = new HashMap<String, Float>();

	}
	
	public CouplingDetail setFat(float fat)
	{
		data.put("Number of Relation", fat);
		return this;
	}
	public CouplingDetail setTangle(float tangle)
	{
		data.put("Tangled", tangle);
		return this;
	}
	
}
