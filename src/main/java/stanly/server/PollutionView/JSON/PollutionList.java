package stanly.server.PollutionView.JSON;

import java.util.ArrayList;
import java.util.HashMap;

import stanly.server.CommonView.JSON.PollutionRatio;

public class PollutionList {
	private HashMap<String,ArrayList<PollutionRatio>> map;
	
	public PollutionList()
	{
		map = new HashMap<String,ArrayList<PollutionRatio>>();
		
	}
	
	public void add(String name, int R, int C)
	{
		if(!map.containsKey(name))
		{
			ArrayList<PollutionRatio> list = new ArrayList<PollutionRatio>();
			
			list.add(new PollutionRatio("A",0));
			list.add(new PollutionRatio("B",0));
			list.add(new PollutionRatio("C",0));
			list.add(new PollutionRatio("F",0));
			map.put(name, list);
		}
	
		map.get(name).get(R).setY(C);
		
	}
}
