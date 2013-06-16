package stanly.server.detailPollution.JSON;

import java.util.ArrayList;
import java.util.HashMap;

public class CouplingDetail {
	private String name;
	private HashMap<String, Integer> data;


	public CouplingDetail(String name)
	{
		this.name=name;
		data = new HashMap<String, Integer>();

	}
	
}
