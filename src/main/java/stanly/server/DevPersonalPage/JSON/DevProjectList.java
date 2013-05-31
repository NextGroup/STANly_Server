package stanly.server.DevPersonalPage.JSON;

import java.util.ArrayList;
import java.util.List;

public class DevProjectList {

	private List<DevProjectInfo> list;

	public DevProjectList()
	{
		list = new ArrayList<DevProjectInfo>();
	}
	public List<DevProjectInfo> getList()
	{
		return list;
	}
	public DevProjectList addInfo(DevProjectInfo pinfo)
	{
		list.add(pinfo);
		return this;
	}
}
