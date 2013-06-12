package stanly.server.CommonView.JSON;

import java.util.ArrayList;

public class SAList {
	private ArrayList<StaticanalysisEntry> list;

	public SAList()
	{
		list = new ArrayList<StaticanalysisEntry>();
	}
	
	public void add(String name,String rate)
	{
		list.add(new StaticanalysisEntry(name,rate));
	}
}
