package stanly.server.DiffDevloper.Service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import stanly.server.DiffDevloper.DAO.DiffDevDAO;
import stanly.server.GitProject.DAO.ProjectDAO;
import stanly.server.GitProject.Model.ProjectCommit;

import com.google.gson.Gson;

@Service("DiffDevService")
@Transactional
public class DiffDevService {
	protected static final Logger logger = Logger.getLogger("DiffDevService");
	
	@Autowired
	private DiffDevDAO diffDAO;
	@Autowired
	private ProjectDAO projectDAO;
	
	public String getDiffDev(String name)
	{
		ProjectCommit commit = projectDAO.getLastCommit(projectDAO.getProjectInfo(name));
		Gson gson = new Gson();
		
		return gson.toJson(diffDAO.getDiffDevList(commit));
		
	}
}
