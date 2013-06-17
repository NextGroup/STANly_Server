package stanly.server.detailPollution.JSON;

import java.util.ArrayList;

import stanly.server.detailPollution.JSON.StaticAnalysis.StaticAnalysisData;

public class StaticAnalysisDetail {
	private String name;
	private ArrayList<StaticAnalysisData> data;
	
	public StaticAnalysisDetail(String Name)
	{
		name=Name;
		data = new ArrayList<StaticAnalysisData>();
	}
	
	public void addStaticData(int line, String message)
	{
		data.add(new StaticAnalysisData(line,message));
	}
}
