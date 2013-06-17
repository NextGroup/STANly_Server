package stanly.server.DiffDevloper.JSON;

import java.util.ArrayList;

public class DiffDevList {
	private ArrayList<DiffDevloperInfo> list;
	
	public DiffDevList()
	{
		list = new ArrayList<DiffDevloperInfo>();
	}
	
	public void add(DiffDevloperInfo d)
	{
		list.add(d);
	}
	
}
