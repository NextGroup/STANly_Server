package stanly.server.GitProject.Git;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepository;

import stanly.server.GitProject.Model.ProjectCommit;
import stanly.server.GitProject.Model.ProjectInfo;

/**
 * @author Karuana
 *	JGit을 기반으로 git을 컨트롤하는 객체이다. 
 */
public class GitControl {
	/**
	 * Git Project가 저장되는 기본 저장소 
	 */
	private static final String DefaultPath = "../GitProject/Project_";
    /**
     *	프로젝트의 로컬 위치와 원격 저장소의 주소  
     */
    private String localPath, remotePath;
    /**
     * 로컬 레파지토리를 관리하는 객체 
     */
    private Repository localRepo;
    /**
     * Jgit 컨트롤 객체 
     */
    private Git git;

    public GitControl(String remote,  String ProjectName) throws IOException
    {
  
    	  
    	    			remotePath = remote;
    	    			StringTokenizer Stk = new StringTokenizer(remotePath,"/",false);
    	    			localPath =DefaultPath+ProjectName;
				localRepo = new FileRepository(localPath + "/.git");
				git = new Git(localRepo); 
	
    	        
    }
    
    /**
     * 정해진 위치에 clone 받는다. 
     * @return 로컬 주소를 리턴한다. 
     * @throws InvalidRemoteException
     * @throws TransportException
     * @throws GitAPIException
     */
    public String Clone() throws InvalidRemoteException, TransportException, GitAPIException
    {
        Git.cloneRepository() 
        .setURI(remotePath)
        .setDirectory(new File(localPath))
        .call();  
        return localPath;
       
    }
    
    /**
     * 레파지토리에 데이터를 클론받는다.
     * @throws WrongRepositoryStateException
     * @throws InvalidConfigurationException
     * @throws DetachedHeadException
     * @throws InvalidRemoteException
     * @throws CanceledException
     * @throws RefNotFoundException
     * @throws NoHeadException
     * @throws TransportException
     * @throws GitAPIException
     */
    public void Pull() throws WrongRepositoryStateException, InvalidConfigurationException, DetachedHeadException, InvalidRemoteException, CanceledException, RefNotFoundException, NoHeadException, TransportException, GitAPIException
    {
        git.pull()
        .call();
    }
    /**
     * commit 정보를 가져올 수 있는 Command 객체를 가져온다. 
     * @return
     */
    public LogCommand getCommand(){
		return git.log(); 
    }
    

    
}
