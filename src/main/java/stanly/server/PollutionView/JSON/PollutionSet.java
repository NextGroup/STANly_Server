package stanly.server.PollutionView.JSON;

import java.util.ArrayList;

import stanly.server.CommonView.JSON.PollutionRatio;

public class PollutionSet {
	private String Name;
	private ArrayList<PollutionRatio> Ratio;
	public PollutionSet(String name) {
		super();
		Name = name;
		Ratio = new ArrayList<PollutionRatio>();
	}
	public void add(String R,int C)
	{
		Ratio.add(new PollutionRatio(R,C));
	}
}
