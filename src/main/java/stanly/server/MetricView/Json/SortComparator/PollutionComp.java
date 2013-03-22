package stanly.server.MetricView.Json.SortComparator;

import java.util.Comparator;

import stanly.server.MetricView.Json.PollutionValue;

public class PollutionComp implements Comparator<PollutionValue>{

	@Override
	public int compare(PollutionValue o1, PollutionValue o2) {
		// TODO Auto-generated method stub
		if(o1.getValue()>o2.getValue())
			return 1;
		else if(o1.getValue()<o2.getValue())
			return -1;
		else
			return 0;
	}

}
