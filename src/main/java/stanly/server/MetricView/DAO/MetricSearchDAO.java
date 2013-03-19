package stanly.server.MetricView.DAO;

import java.util.ArrayList;
import java.util.Stack;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import stanly.server.Analysis.Model.ProjectElementNode;
import stanly.server.Analysis.Model.Metric.ClassMetric;
import stanly.server.Analysis.Model.Metric.PackageMetric;
import stanly.server.Analysis.Model.Type.NodeType;
import stanly.server.GitProject.Model.ProjectCommit;
import stanly.server.GitProject.Model.ProjectInfo;
import stanly.server.MetricView.Json.CodeSizeValue;
import stanly.server.MetricView.Json.MartinMetricList;
import stanly.server.MetricView.Json.PollutionList;

@Repository
@Transactional
public class MetricSearchDAO {
	protected static final Logger logger = Logger.getLogger("MetricDAO - Search");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	private String sprite(String path)
	{
		String[]  arr =  path.split("\\.");
		logger.info((arr.length>1) ?  arr[arr.length-1]:path);
		return (arr.length>1) ?  arr[arr.length-1]:path;
	}
	
	private ProjectElementNode getElementNodeByType(ProjectCommit commit, NodeType type)
	{		
		ProjectElementNode  rootNode =null;
		try{
			Session session = sessionFactory.getCurrentSession();
		
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
			Criterion CommitEq = Restrictions.eq("commit", commit);
			Criterion projectEq = Restrictions.eq("NSLeft", 1); //NSLeft == 1 이면 프로젝트 노
			Criteria crit = session.createCriteria(ProjectElementNode.class);
			crit.add(CommitEq);
			crit.add(projectEq);
			rootNode = (ProjectElementNode) crit.uniqueResult();
	
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return null;
		}
	
		return rootNode;
	}
	
	
	/**
	 * Martin 벨류를 계산하는 로직 ㅇ
	 * @param commit
	 * @param NSLeft
	 * @return
	 */
	public MartinMetricList getMertinValue(ProjectCommit commit, int NSLeft)
	{
		MartinMetricList mertin = new MartinMetricList();
		
		try{
			Session session = sessionFactory.getCurrentSession();
		
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
			Criterion CommitEq = Restrictions.eq("commit", commit);
			Criterion projectEq = Restrictions.eq("NSLeft", NSLeft); //NSLeft == 1 이면 프로젝트 노
			Criteria crit = session.createCriteria(ProjectElementNode.class);
			crit.add(CommitEq);
			crit.add(projectEq);
			ProjectElementNode targetNode = (ProjectElementNode) crit.uniqueResult();
			NodeType type = targetNode.getType();
			
			switch(type)
			{
		
				case PACKAGE:
					PackageMetric metric  =(PackageMetric) targetNode.getEMetric();
					if(!mertin.addAbstractness(metric.getAbstractness()))
						throw new Exception("Abstractness Get Error");
					if(!mertin.addAfferentCoupling(metric.getAfferentCoupling()))
						throw new Exception("AffeterntCoupling Get Error");
					if(!mertin.addDistance(metric.getDistance()))
						throw new Exception("Distance Get Error");
					if(!mertin.addEfferentCoupling(metric.getEfferentCoupling()))
						throw new Exception("EfferentCoupling Get Error");
					if(!mertin.addInstability(metric.getInstability()))
						throw new Exception("Instability Get Error");
					break;
				default:
					throw new Exception("Type Error");
			}	
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return mertin;
		}
		return mertin;
	}
	
	
	
	/**
	 * 선택한 라이브러리의 NSleft를 넘겨준다.
	 * @param commit
	 * @param NSleft
	 * @param NSRight
	 * @return
	 */
	public CodeSizeValue getCodeSize(ProjectCommit commit,int NSLeft,int NSRight)
	{
		CodeSizeValue codesize = null;
		
		try{
			ProjectInfo info = commit.getPInfo();
			Session session = sessionFactory.getCurrentSession();
		
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
			Criterion CommitEq = Restrictions.eq("commit", commit);
			Criterion Left = Restrictions.ge("NSLeft", new Integer(NSLeft));
			Criterion Right = Restrictions.le("NSRight", new Integer(NSRight));
			Criterion NType =Restrictions.or(Restrictions.eq("type", NodeType.LIBRARY), Restrictions.eq("type", NodeType.PACKAGE));
			Criterion NType2 =Restrictions.or(Restrictions.eq("type", NodeType.PACKAGESET), Restrictions.eq("type", NodeType.CLASS));
			Criterion NType3 =Restrictions.or(Restrictions.eq("type", NodeType.ENUM), Restrictions.eq("type", NodeType.INTERFACE));
			Criterion orType = Restrictions.or(Restrictions.or(NType,NType2),NType3);
			
			Criteria crit = session.createCriteria(ProjectElementNode.class);
			crit.add(CommitEq);
			crit.add(Left);
			crit.add(Right);
			crit.add(orType);

			crit.addOrder(Order.asc("NSLeft"));
			
			ArrayList<ProjectElementNode> treeList = (ArrayList<ProjectElementNode>) crit.list();
			
			ProjectElementNode StartNode = treeList.get(0);
			
				codesize =  
						new CodeSizeValue((StartNode.getType() == NodeType.LIBRARY) ? " ":sprite(StartNode.getName()),StartNode.getNSLeft(),StartNode.getNSRight()) ;
				Stack<CodeSizeValue> depth = new Stack<CodeSizeValue>();
				depth.push(codesize);// 최상위 부모 노드를 넣는다. 
				int NStriger = 1;
				for(int i=1;i<treeList.size();i++)
				{
					ProjectElementNode node = treeList.get(i);
					
					if(node.getNSLeft()>NStriger)
					{
						//Stack에 들어잇는 애들 비우기 
						while(depth.peek().getNSRight()<node.getNSLeft())
							depth.pop();
						CodeSizeValue value = null;
						switch(node.getType())
						{
							case PACKAGESET:
							case PACKAGE:
								value = new CodeSizeValue(sprite(node.getName()),node.getNSLeft(),node.getNSRight());
								depth.peek().addChildNode(value);
								depth.push(value);
								break;
							case CLASS:
							case INTERFACE:
							case ENUM:
								ClassMetric metric = (ClassMetric) node.getEMetric();
								value = new CodeSizeValue(sprite(node.getName()),metric.getLOC(),node.getNSLeft(),node.getNSRight());
								depth.peek().addChildNode(value);
								NStriger=node.getNSRight();
								break;
						}
					}				
				}
			
				
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return codesize;
		}
		
		return codesize;
	}
	
	
	public PollutionList getPollutionList(ProjectCommit commit, int NSLeft,int NSRight)
	{
		PollutionList pollution = new PollutionList();
		try{
			Session session = sessionFactory.getCurrentSession();
		
			//쿼리에 테이블 명이 아닌 클래스명을 써야 한다.
			Criterion CommitEq = Restrictions.eq("commit", commit);
			Criterion Left = Restrictions.ge("NSLeft", new Integer(NSLeft+1));
			Criterion Right = Restrictions.le("NSRight", new Integer(NSRight));
			
			Criteria crit = session.createCriteria(ProjectElementNode.class);

			crit.add(CommitEq);
			crit.add(Left);
			crit.add(Right);
			ProjectElementNode targetNode = (ProjectElementNode) crit.uniqueResult();
			NodeType type = targetNode.getType();
			
			switch(type)
			{
				case LIBRARY:
				case PACKAGESET:
				case PACKAGE:
				case CLASS:
				case ENUM:
				case METHOD:
				case INTERFACE:
				case CONSTRUCTOR:
				case ANNOTATION:
				case FIELD:
					break;
				default:
					throw new Exception("Type Error");
			}	
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return pollution;
		}
		
		
		return pollution;
		
	}
	
	
}
