package stanly.server.detailPollution.Service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import stanly.server.Analysis.Model.StaticAnalysis.Type.StaticAnalysisType;
import stanly.server.GitProject.DAO.ProjectDAO;
import stanly.server.GitProject.Model.ProjectCommit;
import stanly.server.MetricView.DAO.ElementSearchDAO;
import stanly.server.detailPollution.DAO.DetailViewDAO;

import com.google.gson.Gson;

@Service("DetailViewService")
@Transactional
public class DetailViewService {
	protected static final Logger logger = Logger.getLogger("DetailViewService");

	@Autowired
	private ProjectDAO projectDAO;
	
	@Autowired
	private DetailViewDAO detailviewDAO;
	
	@Autowired
	private ElementSearchDAO ESDAO;

	public String getCommonDetail(String name, int NSleft)
	{
		Gson gson = new Gson();
		ProjectCommit commit = projectDAO.getLastCommit(projectDAO.getProjectInfo(name));
		
		return gson.toJson(detailviewDAO.getCommonDetail(commit, ESDAO.getElementNode(commit, NSleft)));
		
	}
	
	public String getFatDetailView(String name,int NSleft)
	{
		Gson gson = new Gson();
		ProjectCommit commit = projectDAO.getLastCommit(projectDAO.getProjectInfo(name));
		
		return gson.toJson(detailviewDAO.getFATDetail(commit, ESDAO.getElementNode(commit, NSleft)));

	}

	public String getSourceFile(String name,int NSleft)
	{
		Gson gson = new Gson();
		ProjectCommit commit = projectDAO.getLastCommit(projectDAO.getProjectInfo(name));

		return gson.toJson(detailviewDAO.getJavaSource(commit, ESDAO.getElementNode(commit, NSleft)));
	}
	
	public String getStaticAnalysisDetail(String name, int NSleft, int Type)
	{
		Gson gson = new Gson();
		ProjectCommit commit = projectDAO.getLastCommit(projectDAO.getProjectInfo(name));
		StaticAnalysisType sType = (Type==0) ? StaticAnalysisType.BASIC: StaticAnalysisType.NAMING;
		
		return gson.toJson(detailviewDAO.getStaticAnalysis(commit, ESDAO.getElementNode(commit, NSleft),sType));
		
	}

}
