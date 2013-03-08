package stanly.server.Analysis.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import stanly.server.Analysis.DAO.ElementDAO;
import stanly.server.Analysis.Model.ElementNode;
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
	
	
	public boolean AddNode(ProjectCommit commit)
	{
		
		//Test Case 만들기    
		try{


			ArrayList<ElementNode> list = new ArrayList<ElementNode>();
			list.add(new ElementNode("stanly.server", "NONE", 1, 6,NodeType.PACKAGE));
			list.add(new ElementNode("stanly.server.Analysis", "stanly.server", 2, 3,NodeType.PACKAGE));
			list.add(new ElementNode("stanly.server.GitProject", "stanly.server", 4, 5,NodeType.PACKAGE));
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
	public List<ElementNode> getTree(ProjectCommit CommitID)
	{
		return nodeDao.getNodeTree(CommitID);
	}
	@Async
	public Future<String> analysisNode(ProjectCommit commit)
	{
		try{
			logger.info("ProjectInfo insert");
			Session session = sessionFactory.getCurrentSession();
			
			
			// Data Save
			session.save(null);

		}catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		return new AsyncResult<String>("완료 ㅎㅎ");
	}
}
