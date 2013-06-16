package stanly.server.detailPollution.DAO;

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
import stanly.server.Analysis.Model.Metric.ClassMetric;
import stanly.server.Analysis.Model.Metric.PackageMetric;
import stanly.server.Analysis.Model.Metric.PackageSetMetric;
import stanly.server.Analysis.Model.StaticAnalysis.Type.StaticAnalysisType;
import stanly.server.Analysis.Model.Type.NodeType;
import stanly.server.GitProject.Model.ProjectCommit;
import stanly.server.detailPollution.JSON.CommonDetail;
import stanly.server.detailPollution.JSON.FATDetail;
import stanly.server.detailPollution.JSON.JavaSource;
import stanly.server.detailPollution.JSON.StaticAnalysisDetail;

@Repository
@Transactional
public class DetailViewDAO {
	protected static final Logger logger = Logger.getLogger("DetailViewDAO");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	public CommonDetail getCommonDetail(ProjectCommit commit,ProjectElementNode element)
	{
		CommonDetail common = new CommonDetail();
		
		switch(element.getType())
		{
			case CLASS:
				ClassMetric cm = (ClassMetric)element.getEMetric();
				common.addClass(cm.getClasses(), cm.getMethods(), cm.getFields(), cm.getLOC(), cm.getFat());
				break;
			case PACKAGE:
				PackageMetric pm = (PackageMetric)element.getEMetric();
				common.addPackage(pm.getUnits(), pm.getTotalCC(), (int)pm.getELOCPerUnit());
				break;
			case PACKAGESET:
				PackageSetMetric psm = (PackageSetMetric)element.getEMetric();
				common.addPackageSet(psm.getTotalUnit(), psm.getTotalCC(), psm.getTotalELOC());
				break;
		}
		return common;
	}
	
	public FATDetail getFATDetail(ProjectCommit commit,ProjectElementNode element)
	{
		FATDetail fat = new FATDetail(element.getName());
		if(element.getType() == NodeType.CLASS)
		{
			ClassMetric cm = (ClassMetric)element.getEMetric();
			fat.setCC((int)cm.getWMC()).setELOC(cm.getLOC()).setField(cm.getFields()).setMethod(cm.getMethods());
	
		}
		return fat;
	}
	public JavaSource getJavaSource(ProjectCommit commit,ProjectElementNode element)
	{
		JavaSource js = new JavaSource();
	
		if(element.getType() == NodeType.CLASS)
		{
			ClassMetric cm = (ClassMetric)element.getEMetric();
			js.updateSource(cm.getSRC());
		}
		return js;
	}
	
	public StaticAnalysisDetail getStaticAnalysis(ProjectCommit commit,ProjectElementNode element,StaticAnalysisType type)
	{
		StaticAnalysisDetail saDetail = new StaticAnalysisDetail(element.getName());
		
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select sa.Line, sa.message from StaticAnalysisMetric sa where sa.commit = ? and sa.NSLeft = ? and sa.type = ?");
		query.setParameter(0, commit);
		query.setParameter(1, element.getNSLeft());
		query.setParameter(2, type);
		List list = query.list();
		Iterator it = list.iterator();
		while(it.hasNext())
		{
			Object[] obj = (Object[])it.next();
			saDetail.addStaticData((Integer)obj[0], (String)obj[1]);
		}
		return saDetail;
	}
}
