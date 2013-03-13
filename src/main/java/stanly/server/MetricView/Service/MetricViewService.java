package stanly.server.MetricView.Service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stanly.server.Analysis.DAO.RelationDAO;
import stanly.server.GitProject.DAO.ProjectDAO;
import stanly.server.GitProject.Model.ProjectCommit;

/**
 * @author Karuana
 *
 */
@Service("metricViewService")
public class MetricViewService {
	protected static final Logger logger = Logger.getLogger("MetricViewService");
	@Autowired
	private ProjectDAO projectDAO;
	@Autowired
	private RelationDAO relationDao;
	
	private String getRelationWithSrc(String ProjectName, String SrcName)
	{
		ProjectCommit commit = projectDAO.getLastCommit(projectDAO.getProjectInfo(ProjectName));
		relationDao.getSrcLikeRelation(commit, SrcName);
		return null;
	}
}
