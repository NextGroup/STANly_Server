package stanly.server.ArchPersonalPage.JSON;

import java.util.ArrayList;
import java.util.List;

import stanly.server.DevPersonalPage.JSON.DevProjectInfo;
import stanly.server.DevPersonalPage.JSON.DevProjectList;

public class ArchProjectList {
	private List<ArchProjectInfo> list;

	public ArchProjectList()
	{
		list = new ArrayList<ArchProjectInfo>();
	}
	public List<ArchProjectInfo> getList()
	{
		return list;
	}
	public ArchProjectList addInfo(ArchProjectInfo pinfo)
	{
		list.add(pinfo);
		return this;
	}
}
