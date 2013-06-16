package stanly.server.PollutionView.Service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import stanly.server.Analysis.Model.StaticAnalysis.Type.StaticAnalysisType;
import stanly.server.GitProject.DAO.ProjectDAO;
import stanly.server.GitProject.Model.ProjectCommit;
import stanly.server.PollutionView.DAO.PollutionViewDAO;

import com.google.gson.Gson;

@Service("PollutionViewService")
@Transactional
public class PollutionViewService {
	protected static final Logger logger = Logger.getLogger("PollutionViewService");

	@Autowired
	private ProjectDAO projectDAO;
	
	@Autowired
	private PollutionViewDAO pollutionDAO;
	
	public String getFatList(String projectName)
	{
		Gson gson  = new Gson();
		ProjectCommit commit = projectDAO.getLastCommit(projectDAO.getProjectInfo(projectName));
		return gson.toJson(pollutionDAO.getFatList(commit));
	}
	
	public String getCouplingList(String projectName)
	{
		Gson gson  = new Gson();
		ProjectCommit commit = projectDAO.getLastCommit(projectDAO.getProjectInfo(projectName));
		return gson.toJson(pollutionDAO.getCOList(commit));
	}
	
	public String getCPList(String projectName)
	{
		Gson gson  = new Gson();
		ProjectCommit commit = projectDAO.getLastCommit(projectDAO.getProjectInfo(projectName));
		return gson.toJson(pollutionDAO.getCPList(commit));
	}
	
	public String getSAList(String projectName)
	{
		Gson gson  = new Gson();
		ProjectCommit commit = projectDAO.getLastCommit(projectDAO.getProjectInfo(projectName));
		return gson.toJson(pollutionDAO.getSAList(commit));
	}
	
	
	/**
	 * @param projectName
	 * @param Rank 0==B랭크 이하, 1==C랭크 이하, 2== F랭크 이하, 기타 F
	 * @return
	 */
	public String getTotalSAListBasic(String projectName, int Rank)
	{
		Gson gson  = new Gson();
		ProjectCommit commit = projectDAO.getLastCommit(projectDAO.getProjectInfo(projectName));
		return gson.toJson(pollutionDAO.getSATotalList(commit,StaticAnalysisType.BASIC,Rank));
	}
	
	public String getTotalSAListNaming(String projectName, int Rank)
	{
		Gson gson  = new Gson();
		ProjectCommit commit = projectDAO.getLastCommit(projectDAO.getProjectInfo(projectName));
		return gson.toJson(pollutionDAO.getSATotalList(commit,StaticAnalysisType.NAMING,Rank));
	}
}
