package stanly.server.MetricView.Json;

import java.util.ArrayList;

public class PollutionList {

	private ArrayList<PollutionValue> pollution;

	public PollutionList() {
		super();
		pollution = new ArrayList<PollutionValue>();
	}

	public void addPollution(String artifact,String metric, int value)
	{
		pollution.add(new PollutionValue(artifact,metric,value));
	}

	public ArrayList<PollutionValue> getPollution() {
		return pollution;
	}

	
}
