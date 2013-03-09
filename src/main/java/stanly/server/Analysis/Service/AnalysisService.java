package stanly.server.Analysis.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import stanly.server.Analysis.DAO.ElementDAO;
import stanly.server.Analysis.Model.*;
import stanly.server.Analysis.Model.Type.NodeType;
import stanly.server.GitProject.Model.ProjectCommit;

@Service("analysisService")
@Transactional
public class AnalysisService {

	protected static final Logger logger = Logger.getLogger("AnalysisService");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Autowired
	private ElementDAO nodeDao;
	
	public ProjectElementNode insertElement(String name, String paretnName, int nSLeft, int nSRight, NodeType type)
	{
		ProjectElementNode node = new ProjectElementNode(name,paretnName,nSLeft,nSRight,type);
		nodeDao.insertElement(node);
	
		return node;
	}
	public ProjectElementNode createElement(String name, String paretnName, int nSLeft, int nSRight, NodeType type)
	{
		ProjectElementNode node = new ProjectElementNode(name,paretnName,nSLeft,nSRight,type);	
		return node;
	}
	public ProjectElementNode InsertElement(ProjectElementNode e)
	{
		return nodeDao.insertElement(e);
	}
	public boolean AddNode(ProjectCommit commit)
	{
		
		//Test Case 만들기    
		try{


			ArrayList<ProjectElementNode> list = new ArrayList<ProjectElementNode>();
			list.add(new ProjectElementNode("stanly.server", "NONE", 1, 6,NodeType.PACKAGE));
			list.add(new ProjectElementNode("stanly.server.Analysis", "stanly.server", 2, 3,NodeType.PACKAGE));
			list.add(new ProjectElementNode("stanly.server.GitProject", "stanly.server", 4, 5,NodeType.PACKAGE));
			// Data Save
			for(int i=0;i<list.size();i++)
			{
				list.get(i).setCommit(commit);
				nodeDao.insertElement(list.get(i));
			}

		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}
	public List<ProjectElementNode> getTree(ProjectCommit CommitID)
	{
		return nodeDao.getNodeTree(CommitID);
	}
	public ProjectElementNode getNode(ProjectCommit commitID, String projectName)
	{
		return nodeDao.getNode(commitID, projectName);
	}
	
	
	@Async
	public Future<String> analysisNode(ProjectCommit commit)
	{
		try{
			logger.info("ProjectInfo insert");
			
			
	

		}catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		return new AsyncResult<String>("완료 ㅎㅎ");
	}
}
