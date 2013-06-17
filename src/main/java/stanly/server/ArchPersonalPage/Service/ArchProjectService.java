package stanly.server.ArchPersonalPage.Service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import stanly.server.Analysis.Model.Metric.Rate.MetricRate;
import stanly.server.ArchPersonalPage.JSON.ArchProjectInfo;
import stanly.server.ArchPersonalPage.JSON.ArchProjectList;
import stanly.server.DevPersonalPage.DAO.DevCommitterDAO;
import stanly.server.DevPersonalPage.JSON.DevProjectList;
import stanly.server.GitProject.DAO.ProjectDAO;
import stanly.server.GitProject.Model.ProjectInfo;
import stanly.server.GitProject.Model.committer.ProjectCommitter;

@Service("ArchProjectService")
@Transactional
public class ArchProjectService {
	protected static final Logger logger = Logger.getLogger("ArchProjectService");
	@Autowired
	private ProjectDAO projectDAO;
	
	@Autowired
	private DevCommitterDAO committerDao;
	
	public ArchProjectList getCommitterProject(String Committer){
		ArchProjectList list = new ArchProjectList();
		
		for(ProjectCommitter project: committerDao.getCommitterProject(Committer))
		{
			ArchProjectInfo info = new ArchProjectInfo();
			ProjectInfo pinfo = project.getPInfo();
			info.setPollutionRank(MetricRate.ChangeRate(pinfo.getFAT_RANK()), MetricRate.ChangeRate(pinfo.getCoplingRANK()), MetricRate.ChangeRate(pinfo.getName_RANK()), MetricRate.ChangeRate(pinfo.getBasic_RANK()));
			info.setPRank(MetricRate.ChangeRate((pinfo.getFAT_RANK()+pinfo.getBasic_RANK()+pinfo.getCoplingRANK()+pinfo.getName_RANK())/4));
			info.setNewRank(MetricRate.ChangeRate(pinfo.getCP_RANK()));
			info.setPName(pinfo.getName()).setStartDay(pinfo.getFirstDate().getYear()+1900, pinfo.getFirstDate().getMonth()+1, pinfo.getFirstDate().getDate());
			info.setLastDay(pinfo.getLastDate().getYear()+1900, pinfo.getLastDate().getMonth()+1, pinfo.getLastDate().getDate());
			list.addInfo(info);
		}
		
		return list;
	}
}
