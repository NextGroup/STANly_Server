package stanly.server.detailPollution.JSON;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class FATDetail {
	private String name;
	private HashMap<String, Integer> data;

	
	public FATDetail(String name)
	{
		this.name=name;
		data = new HashMap<String, Integer>();
		
	}
	
	public FATDetail setMethod(int method)
	{
		data.put("Methods", method);
		return this;
	}
	public FATDetail setField(int Field)
	{
		data.put("Fileds", Field);
		return this;
	}
	public FATDetail setELOC(int ELOC)
	{
		data.put("ELOC", ELOC);
		return this;
	}
	public FATDetail setCC(int CC)
	{
		data.put("Number of Branch Statement", CC);
		return this;
	}

}
