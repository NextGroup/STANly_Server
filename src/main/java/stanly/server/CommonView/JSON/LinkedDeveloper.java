package stanly.server.CommonView.JSON;

import java.util.ArrayList;

public class LinkedDeveloper {
	private int Count;
	private ArrayList<String> list;
	public LinkedDeveloper()
	{
		Count=0;
		list = new ArrayList<String>();
	}
	public void add(String s)
	{
		list.add(s);
		Count++;
	}
}
