package stanly.server.MetricView.Json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

import stanly.server.MetricView.Json.SortComparator.PollutionChartComp;

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
		int[] arr = new int[14];
		HashMap<String, Integer> PollutionName = new HashMap<String,Integer>();

		
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
		PollutionName.put("Number of Top Level Classes",arr[0]);	
		PollutionName.put("Number of Methods",arr[1]);			
		PollutionName.put("Number of Fields",arr[2]);			
		PollutionName.put("Estimated Lines of Code",arr[3]);		
		PollutionName.put("Cyclomatic Complexity",arr[4]);			
		PollutionName.put("Fat",arr[5]);							
		PollutionName.put("Tangled",arr[6]);						
		PollutionName.put("Component Dependency",arr[7]);			
		PollutionName.put("Distance",arr[8]);						
		PollutionName.put("Average Absolute Distance",arr[9]);		
		PollutionName.put("Weighted Methods per Class",arr[10]);		
		PollutionName.put("Depth of Inheritance Tree",arr[11]);
		PollutionName.put("Coupling between Objects",arr[12]);		
		PollutionName.put("Response for a Class",arr[13]);
		
		PollutionChartComp sortPollution = new PollutionChartComp(PollutionName);
		 TreeMap< String , Integer > sorted_map = new TreeMap< String , Integer >( sortPollution );

		 sorted_map.putAll(PollutionName);

		
		PollutionChart chart = new PollutionChart();
	      for ( String key : sorted_map.keySet( ) )
	      {
	    	  	chart.add(key, sorted_map.get(key));
	      }
		
		return chart;
	}
	
	private String getMetricRank(int A,int B,int C,int D)// A =1번 랭크, B 2번 랭크, 3 1번 갯수, 4 2번 갯
	{
		return 	(A == 1) ? "F" : ((B==1)||(C>10) ? "C": ((D>10) ? "B":"A") ) ;
	}
	
	private String getRank(String[] Arr)
	{
		int ACount=0, AMax=0, BCount=0,BMax=0;
		
		for(int i=0;i<pollution.size();i++)
		{
			PollutionValue obj = pollution.get(i);
			if(obj.getMetric().contentEquals(Arr[0]))
			{
				if((AMax==0)&&obj.getType().contentEquals("Risk"))
					AMax=1;
				ACount++;
			}
			else if(obj.getMetric().contentEquals(Arr[1]))
			{
				if((BMax!=1)&&obj.getType().contentEquals("Risk"))
					BMax=1;
				BCount++;
			}
		}
		return getMetricRank(AMax,BMax,ACount,BCount);
	}
	
	public String getFatRank()
	{
		String[] Temp = {"Cyclomatic Complexity","Estimated Lines of Code (ELOC)"};
		return getRank(Temp);
	}
	public String getAbstractRank()
	{
		String[] Temp = {"Depth of Inheritance Tree","Distance"};
		return getRank(Temp);
	}
	public String getCoplingRank()
	{
		String[] Temp = {"Tangled","Fat"};
		return getRank(Temp);
	}
}
