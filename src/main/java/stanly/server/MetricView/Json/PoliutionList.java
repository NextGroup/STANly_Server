package stanly.server.MetricView.Json;

import java.util.ArrayList;

public class PoliutionList {

	private ArrayList<PollutionValue> pollution;

	public PoliutionList() {
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
