package stanly.server.PollutionView.Service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
