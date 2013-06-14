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
	
	public void add(String name, String R, int C)
	{
		if(!map.containsKey(name))
			map.put(name, new ArrayList<PollutionRatio>());
	
		map.get(name).add(new PollutionRatio(R,C));
		
	}
}
