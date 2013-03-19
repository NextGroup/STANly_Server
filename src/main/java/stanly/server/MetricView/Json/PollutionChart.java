package stanly.server.MetricView.Json;

import java.util.ArrayList;

public class PollutionChart {
	private ArrayList<String> PollutionName;
	private ArrayList<Integer> PollutionScore;
	public PollutionChart() {
		super();
		PollutionName = new ArrayList<String>();
		PollutionScore = new ArrayList<Integer>();
		PollutionName.add("Number of Top Level Classes");	
		PollutionName.add("Number of Methods");			
		PollutionName.add("Number of Fields");			
		PollutionName.add("Estimated Lines of Code");		
		PollutionName.add("Cyclomatic Complexity'");			
		PollutionName.add("Fat");							
		PollutionName.add("Tangled");						
		PollutionName.add("Component Dependency");			
		PollutionName.add("Distance");						
		PollutionName.add("Average Absolute Distance");		
		PollutionName.add("Weighted Methods per Class");		
		PollutionName.add("Depth of Inheritance Tree");
		PollutionName.add("Coupling between Objects");		
		PollutionName.add("Response for a Class");
	}
	
	public void add(int Count)
	{
		
		 PollutionScore.add(Count);
		
	}
	public ArrayList<String> getPollutionName() {
		return PollutionName;
	}
	public ArrayList<Integer> getPollutionScore() {
		return PollutionScore;
	}
	
	
}
