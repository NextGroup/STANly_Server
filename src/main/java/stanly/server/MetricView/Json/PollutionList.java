package stanly.server.MetricView.Json;

import java.util.ArrayList;

public class PollutionList {

	private ArrayList<PollutionValue> pollution;

	public PollutionList() {
		super();
		pollution = new ArrayList<PollutionValue>();
	}
	
	public void addPollution(PollutionValue p)
	{
		pollution.add(p);
	}

	public ArrayList<PollutionValue> getPollution() {
		return pollution;
	}

	
}
