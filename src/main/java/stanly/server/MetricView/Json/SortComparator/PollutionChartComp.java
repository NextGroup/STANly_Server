package stanly.server.MetricView.Json.SortComparator;

import java.util.Comparator;
import java.util.Map;

public class PollutionChartComp implements Comparator< Object >
{
	private Map<String,Integer> base;
	
	public PollutionChartComp(Map<String,Integer> b)
	{
		base = b;
	}

	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		 if ( ( Integer ) base.get( o1 ) < ( Integer ) base.get( o2 ) )
	        {
	            return 1;
	        }
	        else if ( base.get( o1 ) == base.get( o2 ) )
	        {
	            return 0;
	        }
	        else
	        {
	            return - 1;
	        }
	}
}
