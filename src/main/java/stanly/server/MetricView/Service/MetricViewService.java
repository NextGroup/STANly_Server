package stanly.server.MetricView.Service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stanly.server.Analysis.DAO.RelationDAO;
import stanly.server.Analysis.Model.ProjectElementNode;
import stanly.server.Analysis.Model.Relation.NodeRelation;
import stanly.server.GitProject.DAO.ProjectDAO;
import stanly.server.GitProject.Model.ProjectCommit;
import stanly.server.GitProject.Model.ProjectInfo;
import stanly.server.MetricView.DAO.ElementSearchDAO;
import stanly.server.MetricView.Json.Relation;
import stanly.server.MetricView.Json.RelationList;
import stanly.server.MetricView.Json.TreeElement;

import com.google.gson.Gson;

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
	@Autowired
	private ElementSearchDAO EsearchDAO;
	
	private String sprite(String path)
	{
		String[]  arr =  path.split(".");
		
		return arr[arr.length-1];
	}
	
	/**
	 * Relation , 페이징 처리가 필요할 것 같다. 너무 데이터가 많이 오는 것 아닌가? 
	 * @param projectName
	 * @param SrcName
	 * @return
	 */
	public String getRelationWithSrc(String projectName, String SrcName)
	{
		logger.info("Relation Calc");
		ProjectCommit commit = projectDAO.getLastCommit(projectDAO.getProjectInfo(projectName));
		List<NodeRelation> nodeR = relationDao.getSrcLikeRelation(commit, SrcName);
		Gson gson = new Gson();
		RelationList rList = new RelationList();
	
		for(int i=0;i<nodeR.size();i++)
		{
			NodeRelation node = nodeR.get(i);
			Relation rel = new Relation(node.getSrcName(), node.getTarName(), node.getType().name());
			rList.addRelation(rel);
		}
		return gson.toJson(rList);
	}
	
	public String gatRelationWithTar(String projectName,String TarName)
	{
		ProjectCommit commit = projectDAO.getLastCommit(projectDAO.getProjectInfo(projectName));
		List<NodeRelation> nodeR = relationDao.getTarLikeRelation(commit, TarName);
		Gson gson = new Gson();
		RelationList rList = new RelationList();
	
		for(int i=0;i<nodeR.size();i++)
		{
			NodeRelation node = nodeR.get(i);
			Relation rel = new Relation(node.getSrcName(), node.getTarName(), node.getType().name());
			rList.addRelation(rel);
		}
		return gson.toJson(rList);
	}
	
	public String getRelation(String projectName, String SrcName, String TarName)
	{
		
		ProjectCommit commit = projectDAO.getLastCommit(projectDAO.getProjectInfo(projectName));
		List<NodeRelation> nodeR = relationDao.getLikeRelation(commit,SrcName, TarName);
		Gson gson = new Gson();
		RelationList rList = new RelationList();
	
		for(int i=0;i<nodeR.size();i++)
		{
			NodeRelation node = nodeR.get(i);
			Relation rel = new Relation(node.getSrcName(), node.getTarName(), node.getType().name());
			rList.addRelation(rel);
		}
		return gson.toJson(rList);
	}
	
	/**
	 *  NSLeft  == nodeID 로 사용할 예
	 *  ID의 자식을 반환한다. 
	 * @param projectName
	 * @param nodeID
	 * @return
	 */
	public String getTreeNode(String projectName, int nodeID){ 
		ProjectInfo info = projectDAO.getProjectInfo(projectName);
		ProjectCommit commit = projectDAO.getLastCommit(info);
		ArrayList<TreeElement> Elements = new ArrayList<TreeElement>();
		logger.info("Tree Init");
		if(nodeID == 0)
		{
			ProjectElementNode node = EsearchDAO.getProjectNode(commit);
			if(node==null)
				logger.info("Null Log");
			
			TreeElement projectNode = new TreeElement(info.getName(),node.getNSLeft() ,node.getNSRight());
			projectNode.setAttrID(Integer.toString(1));
	
			projectNode.setRel(node.getType().name());
			Elements.add(projectNode);
		}
		else
		{
			ProjectElementNode node= EsearchDAO.getElementNode(commit, nodeID);
			
			List<ProjectElementNode> nodelist = EsearchDAO.getChildNode(node.getName(), commit);
			for(int i=0;i<nodelist.size();i++)
			{
				TreeElement projectNode = new TreeElement(sprite(nodelist.get(i).getName()),nodelist.get(i).getNSLeft() ,nodelist.get(i).getNSRight());
				projectNode.setAttrID(Integer.toString(nodelist.get(i).getNSLeft()));
				projectNode.setRel(nodelist.get(i).getType().name());
				Elements.add(projectNode);
			}
		}
		Gson gosn = new Gson();
		
		return gosn.toJson(Elements);
	}
	
	

}
