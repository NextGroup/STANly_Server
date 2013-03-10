package stanly.server.GitProject.Git;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.DetachedHeadException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;

public class GitControl {
	private static final String DefaultPath = "../GitProject/Project_";
    private String localPath, remotePath;
    private Repository localRepo;
    private Git git;

    public GitControl(String remote,  String ProjectName) throws IOException
    {
  
    	  
    	    			remotePath = remote;
    	    			StringTokenizer Stk = new StringTokenizer(remotePath,"/",false);
    	    			localPath =DefaultPath+ProjectName;
				localRepo = new FileRepository(localPath + "/.git");
				git = new Git(localRepo); 
	
    	        
    }
    
    public void Clone() throws InvalidRemoteException, TransportException, GitAPIException
    {
        Git.cloneRepository() 
        .setURI(remotePath)
        .setDirectory(new File(localPath))
        .call();  
       
    }
    
    public void Pull() throws WrongRepositoryStateException, InvalidConfigurationException, DetachedHeadException, InvalidRemoteException, CanceledException, RefNotFoundException, NoHeadException, TransportException, GitAPIException
    {
        git.pull()
        .call();
    }
    public LogCommand getCommand(){
		return git.log(); 
    }
    
    
}
