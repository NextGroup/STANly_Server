package stanly.server.Analysis.DAO;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import stanly.server.Analysis.Model.StaticAnalysis.StaticAnalysisMetric;
import stanly.server.Analysis.Model.StaticAnalysis.Type.StaticAnalysisType;
import stanly.server.GitProject.Model.ProjectCommit;

@Repository
@Transactional
public class StaticAnalysisDAO {
	protected static final Logger logger = Logger.getLogger("StaticAnalysisDAO");
	
	/**
	 * 하이버네이트 연결을 위한 세션을 만들어주는 팩토리 객체 
	 */
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	public StaticAnalysisMetric insertSAMetric(StaticAnalysisType type, String sourcePath,
			Integer line, Integer left, String message,ProjectCommit commit){
		
		StaticAnalysisMetric saMetric = null;
		try{

			Session session = sessionFactory.getCurrentSession();
			saMetric = new StaticAnalysisMetric(type,sourcePath,line,left,message,commit);
			session.save(saMetric);
		
		}catch(Exception e)
		{
			logger.error(e.getMessage());
			return null;
		}
		return saMetric;
		
	}
}
