package stanly.server.PollutionView.JSON;

import java.util.ArrayList;

public class PollutionList {
	private ArrayList<PollutionSet> list;
	
	public PollutionList()
	{
		list = new ArrayList<PollutionSet>();
	}
	
	public void add(String name, int a,int b,int c, int f)
	{
		PollutionSet p = new PollutionSet(name);
		p.add("A", a);
		p.add("B", b);
		p.add("C", c);
		p.add("F", f);
		list.add(p);
	}
}
