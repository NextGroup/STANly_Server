package stanly.server.ArchPersonalPage.JSON;

import stanly.server.DevPersonalPage.JSON.DevProjectInfo;

public class ArchProjectInfo extends DevProjectInfo {
	private String prank5; // 추가된 체인
	
	public void setNewRank(String rank)
	{
		prank5=rank;
	}
}
