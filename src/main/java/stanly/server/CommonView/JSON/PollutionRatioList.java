package stanly.server.CommonView.JSON;

import java.util.ArrayList;
import java.util.List;

public class PollutionRatioList {

	private ArrayList<PollutionRatio> list;

	public PollutionRatioList() {
		super();
		list = new ArrayList<PollutionRatio>();
	}
	//빨주노초 순으로 입력되어야 함 
	public PollutionRatioList insertRatio(String ratio,int Count)
	{
		list.add(new PollutionRatio(ratio,Count));
		return this;
	}
	
	public List getList()
	{
		return list;
	}
	
}
