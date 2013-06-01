package stanly.server.DevPersonalPage.Service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import stanly.server.Analysis.Model.Metric.Rate.MetricRate;
import stanly.server.DevPersonalPage.DAO.DevCommitterDAO;
import stanly.server.DevPersonalPage.JSON.DevProjectInfo;
import stanly.server.DevPersonalPage.JSON.DevProjectList;
import stanly.server.GitProject.DAO.ProjectDAO;
import stanly.server.GitProject.Model.ProjectInfo;
import stanly.server.GitProject.Model.committer.ProjectCommitter;

@Service("DevProjectServie")
@Transactional
public class DevProjectServie {
	
	protected static final Logger logger = Logger.getLogger("DevProjectServie");
	@Autowired
	private ProjectDAO projectDAO;
	
	@Autowired
	private DevCommitterDAO committerDao;
	
	public DevProjectList getCommitterProject(String Committer){
		DevProjectList list = new DevProjectList();
		
		for(ProjectCommitter project: committerDao.getCommitterProject(Committer))
		{
			DevProjectInfo info = new DevProjectInfo();
			ProjectInfo pinfo = project.getPInfo();
			info.setPollutionRank(MetricRate.ChangeRate(pinfo.getFAT_RANK()), MetricRate.ChangeRate(pinfo.getCoplingRANK()), MetricRate.ChangeRate(pinfo.getName_RANK()), MetricRate.ChangeRate(pinfo.getBasic_RANK()));
			info.setPRank(MetricRate.ChangeRate((pinfo.getFAT_RANK()+pinfo.getBasic_RANK()+pinfo.getCoplingRANK()+pinfo.getName_RANK())/4));
			info.setPName(pinfo.getName()).setStartDay(pinfo.getFirstDate().getYear()+1900, pinfo.getFirstDate().getMonth()+1, pinfo.getFirstDate().getDate());
			info.setStartDay(pinfo.getLastDate().getYear()+1900, pinfo.getLastDate().getMonth()+1, pinfo.getLastDate().getDate());
			list.addInfo(info);
		}
		
		return list;
	}
}
