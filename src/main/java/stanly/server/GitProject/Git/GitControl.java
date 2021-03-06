package stanly.server.GitProject.Git;

import java.io.File;
import java.io.IOException;
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
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

import com.jcraft.jsch.Logger;

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
    		int a=10;
		return git.log(); 
		
    }
    
    
    public RevWalk getCommitData() throws MissingObjectException, IncorrectObjectTypeException, IOException
    {
    		RevWalk walk = new RevWalk(localRepo);
		ObjectId rootId = localRepo.resolve("HEAD");
		if(rootId!=null)
		{
			RevCommit t = walk.parseCommit(rootId);
			walk.markStart(t);
		
			walk.sort(RevSort.COMMIT_TIME_DESC, true);
			walk.sort(RevSort.REVERSE, true);
			return walk;
		}
		
		return null;
    }

    
    public List<DiffEntry> getDiffList(RevCommit old, RevCommit now) throws IncorrectObjectTypeException, IOException, GitAPIException
    {
		ObjectReader reader = localRepo.newObjectReader();
		
		CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
		if(old!=null)
		{
			oldTreeIter.reset(reader, old.getTree().getId());
			System.out.println("Old: "+old.getCommitTime());
			System.out.println("Old: "+now.getCommitTime());
		}
		
		CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
		newTreeIter.reset(reader, now.getTree().getId());
		
	
    		return git.diff()
                    .setNewTree(newTreeIter)
                    .setOldTree(oldTreeIter).setShowNameAndStatusOnly(false)
                    .call();
	
    }
    
}
