package stanly.server.CommonView.Service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import stanly.server.CommonView.DAO.LinkedDeveloperDAO;
import stanly.server.GitProject.DAO.ProjectDAO;
import stanly.server.GitProject.Model.ProjectCommit;
import stanly.server.GitProject.Model.ProjectInfo;
import stanly.server.MetricView.DAO.ElementSearchDAO;

import com.google.gson.Gson;

@Service("LinkedDService")
@Transactional
public class LinkedDService {
	protected static final Logger logger = Logger.getLogger("LinkedDService");
	
	@Autowired
	private ProjectDAO projectDAO;

	@Autowired
	private LinkedDeveloperDAO LDevDAO;
	
	@Autowired
	private ElementSearchDAO EsearchDAO;
	
	public String getLinkedDevList(String ProjectName,int NSleft)
	{
		Gson gson = new Gson();
		ProjectInfo pinfo = projectDAO.getProjectInfo(ProjectName);
		ProjectCommit commit = projectDAO.getLastCommit(pinfo);
		return gson.toJson(LDevDAO.getLinkedDeveloper(pinfo, EsearchDAO.getElementNode(commit, NSleft)));
	}
	
}
