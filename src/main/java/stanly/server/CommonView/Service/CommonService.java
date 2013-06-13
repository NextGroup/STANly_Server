package stanly.server.CommonView.Service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import stanly.server.CommonView.DAO.RankDAO;
import stanly.server.GitProject.DAO.ProjectDAO;
import stanly.server.GitProject.Model.ProjectCommit;

import com.google.gson.Gson;

@Service("commonService")
@Transactional
public class CommonService {
	protected static final Logger logger = Logger.getLogger("AnalysisService");
	
	@Autowired
	private RankDAO RankDAO;
	
	@Autowired
	private ProjectDAO projectDAO;
	
	public String getCriticalRisk(String projectName)
	{
		ProjectCommit commit = projectDAO.getLastCommit(projectDAO.getProjectInfo(projectName));
		Gson gson = new Gson();
		
		return gson.toJson(RankDAO.getCriticalRiskList(commit));
		
	}
	
	public String getPollutionRisk(String projectName)
	{
		ProjectCommit commit = projectDAO.getLastCommit(projectDAO.getProjectInfo(projectName));
		Gson gson = new Gson();
		
		return gson.toJson(RankDAO.getPollutionList(commit));
	}
	
	public String getFatRatioRank(String projectName)
	{
		ProjectCommit commit = projectDAO.getLastCommit(projectDAO.getProjectInfo(projectName));
		Gson gson = new Gson();
		
		return gson.toJson(RankDAO.getFatRank(commit));
	}
	public String getCpRatioRank(String projectName)
	{
		ProjectCommit commit = projectDAO.getLastCommit(projectDAO.getProjectInfo(projectName));
		Gson gson = new Gson();
		
		return gson.toJson(RankDAO.getCpRank(commit));
	}
	public String getCouplingRatioRank(String projectName)
	{
		ProjectCommit commit = projectDAO.getLastCommit(projectDAO.getProjectInfo(projectName));
		Gson gson = new Gson();
		
		return gson.toJson(RankDAO.getCouplingRank(commit));
	}
}
