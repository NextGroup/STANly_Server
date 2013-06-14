package stanly.server.CommonView.DAO;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import stanly.server.Analysis.Model.ProjectElementNode;
import stanly.server.Analysis.Model.Type.NodeType;
import stanly.server.CommonView.JSON.LinkedDeveloper;
import stanly.server.GitProject.Model.ProjectCommit;
import stanly.server.GitProject.Model.committer.CommitterInfluence;

@Repository
@Transactional
public class LinkedDeveloperDAO {

	protected static final Logger logger = Logger.getLogger("LinkedDeveloper");
	/**
	 * 하이버네이트 연결을 위한 세션을 만들어주는 팩토리 객체 
	 */
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	private String sprite(String path)
	{
		String[]  arr =  path.split("\\.");
		logger.info((arr.length>1) ?  arr[arr.length-1]:path);
		return (arr.length>1) ?  arr[arr.length-1]:path;
	}
	

	
	public LinkedDeveloper getLinkedDeveloper(ProjectCommit commit, ProjectElementNode node)
	{
		LinkedDeveloper dev = new LinkedDeveloper();
		try{
			//NSleft
			/*if(node.getType() == NodeType.CLASS || node.getType() == NodeType.INTERFACE )
			{
				Session session = sessionFactory.getCurrentSession();
				String name = sprite(node.getName());
				List nList=null;
				 Criterion projectEq = Restrictions.eq("PInfo", pinfo);
				 Criterion inclass = Restrictions.eq("influenceClass", name);
				 Criteria crit = session.createCriteria(CommitterInfluence.class);
				 crit.add(projectEq);
				 crit.add(inclass);
				 //Nested Set Model 방식으로 DB를 저장하기 때문에 Left에 따라서 정리하면 트리가 나온다.
				 nList = crit.list();
				 Iterator it = nList.iterator();
				 while(it.hasNext())
				 {
					 CommitterInfluence Temp = (CommitterInfluence)it.next();
					 dev.add(Temp.getCommitter());
					 
				 }
			}*/
			List nList=null;
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select distinct CI.committer from CommitterInfluence CI where CI.influenceClass in (select PE.singlename from ProjectElementNode PE where PE.NSLeft >= ? and PE.NSRight <= ? and PE.commit=? and (PE.type = ? or PE.type = ?))");
			query.setParameter(0, node.getNSLeft());
			query.setParameter(1, node.getNSRight());
			query.setParameter(2, commit);
			query.setParameter(3, NodeType.CLASS);
			query.setParameter(4, NodeType.INTERFACE);
			 nList = query.list();
			 Iterator it = nList.iterator();
			 while(it.hasNext())
			 {
				 String Temp = (String)it.next();
				 dev.add(Temp);
				 
			 }
			
		}catch(Exception e)
		{
			logger.error(e);
		}
		
		return dev;
	}
	
	
}
