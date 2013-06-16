package stanly.server.detailPollution.JSON;

import java.util.HashMap;

public class CommonDetail {
	private HashMap<String,Integer> data;
	
	public CommonDetail()
	{
		data = new HashMap<String,Integer>();
	}
	
	public void addPackageSet(int units, int cc, int eloc)
	{
		if(data.isEmpty())
		{
			data.put("Units", units);
			data.put("Number of branch statement", cc);
			data.put("ELOC", eloc);
		}
	}
	public void addPackage(int units, int cc, int eloc)
	{
		if(data.isEmpty())
		{
			data.put("Units", units);
			data.put("Number of branch statement", cc);
			data.put("ELOC", eloc);
		}
	}
	public void addClass(int inner, int method, int field,int eloc,int fat)
	{
		if(data.isEmpty())
		{
			data.put("Inner Classes", inner);
			data.put("Methods", method);
			data.put("Field", field);
			data.put("Number of Relations", fat);
			data.put("ELOC", eloc);
		}
	}
}
