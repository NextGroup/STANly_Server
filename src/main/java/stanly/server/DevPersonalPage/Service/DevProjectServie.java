package stanly.server.DevPersonalPage.Service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import stanly.server.DevPersonalPage.DAO.DevCommitterDAO;
import stanly.server.DevPersonalPage.JSON.DevProjectList;
import stanly.server.GitProject.DAO.ProjectDAO;

@Service("DevProjectServie")
@Transactional
public class DevProjectServie {
	
	protected static final Logger logger = Logger.getLogger("DevProjectServie");
	@Autowired
	private ProjectDAO projectDAO;
	
	@Autowired
	private DevCommitterDAO committerDao;
	
	public DevProjectList getCommitterProject(){
		return null;
	}
}
