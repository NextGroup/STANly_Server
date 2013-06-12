package stanly.server.CommonView.JSON;

import java.util.ArrayList;

import stanly.server.Analysis.Model.Type.NodeType;

public class CriticalRiskList {
	private ArrayList<CriticalRisk> list;
	public CriticalRiskList()
	{
		list = new ArrayList<CriticalRisk>();
	}
	private String RateChange(int i)
	{
		return (i==1) ? "A":((i==2) ? "B" : ((i==3) ? "C":"F"));
	}
	public void addData(String name, int rate, NodeType Type)
	{
		list.add(new CriticalRisk(name,RateChange(rate),Type));
	}
}
