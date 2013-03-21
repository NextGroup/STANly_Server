package stanly.server.MetricView.Json;

import java.util.ArrayList;
import java.util.Arrays;

public class PollutionList {

	private ArrayList<PollutionValue> pollution;

	public PollutionList() {
		super();
		pollution = new ArrayList<PollutionValue>();
	}

	public void addPollution(String metric, String artifact, float value,int type)
	{
		pollution.add(new PollutionValue(artifact,metric,value,type));
	}

	public ArrayList<PollutionValue> getPollution() {
		return pollution;
	}

	public PollutionChart getCountPollution()
	{
		int[] arr = new int[13];
		  Arrays.fill(arr, 0);    
		for(int i=0;i<pollution.size();i++)
		{
			PollutionValue obj = pollution.get(i);
			if(obj.getMetric().contentEquals("Number of Top Level Classes"))
			{
				arr[0]++;
			}
			else if(obj.getMetric().contentEquals("Number of Methods"))
			{
				arr[1]++;
			}			
			else if(obj.getMetric().contentEquals("Number of Fields"))
			{
				arr[2]++;
			}
			else if(obj.getMetric().contentEquals("Estimated Lines of Code (ELOC)"))
			{
				arr[3]++;
			}
			else if(obj.getMetric().contentEquals("Cyclomatic Complexity"))
			{
				arr[4]++;
			}
			else if(obj.getMetric().contentEquals("Fat"))
			{
				arr[5]++;
			}
			else if(obj.getMetric().contentEquals("Tangled"))
			{
				arr[6]++;
			}
			else if(obj.getMetric().contentEquals("Average Component Dependency between Packages"))
			{
				arr[7]++;
			}			
			else if(obj.getMetric().contentEquals("Distance (D)"))
			{
				arr[8]++;
			}
			else if(obj.getMetric().contentEquals("Average Absolute Distance"))
			{
				arr[9]++;
			}
			else if(obj.getMetric().contentEquals("Weighted Methods per Class"))
			{
				arr[10]++;
			}
			else if(obj.getMetric().contentEquals("Depth of Inheritance Tree"))
			{
				arr[11]++;
			}
			else if(obj.getMetric().contentEquals("Coupling between Objects"))
			{
				arr[12]++;
			}
			else if(obj.getMetric().contentEquals("Response for a Class"))
			{
				arr[13]++;
			}
			
		}
		PollutionChart chart = new PollutionChart();
		for(int i=0;i<arr.length;i++)
			chart.add(arr[i]);
		
		return chart;
	}
	
}
