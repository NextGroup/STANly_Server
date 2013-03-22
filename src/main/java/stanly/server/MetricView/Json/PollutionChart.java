package stanly.server.MetricView.Json;

import java.util.ArrayList;

public class PollutionChart {
	private ArrayList<String> PollutionName;
	private ArrayList<Integer> PollutionScore;
	public PollutionChart() {
		super();
		PollutionName = new ArrayList<String>();
		PollutionScore = new ArrayList<Integer>();

	}
	
	public void add(String name, int Count)
	{
		PollutionName.add(name);
		PollutionScore.add(Count);
		
	}
	public ArrayList<String> getPollutionName() {
		return PollutionName;
	}
	public ArrayList<Integer> getPollutionScore() {
		return PollutionScore;
	}
	
	
}
