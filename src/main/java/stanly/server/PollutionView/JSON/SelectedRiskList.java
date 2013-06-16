package stanly.server.PollutionView.JSON;

import java.util.ArrayList;

public class SelectedRiskList {

	private ArrayList<SelectedRisk> list;
	
	public SelectedRiskList()
	{
		list = new ArrayList<SelectedRisk>();
	}
	
	public void add(int left, String type, String rank, String riskName,
			String domain, String domainName, int linkedPerson)
	{
		list.add(new SelectedRisk(left, type,rank,riskName,domain,domainName,linkedPerson));
	}
	public void add(SelectedRisk risk)
	{
		list.add(risk);
	}
}
