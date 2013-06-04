package stanly.server.GitProject.Model.committer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import stanly.server.GitProject.Model.ProjectInfo;

@Entity
@Table(name = "PROJECT_COMMITTER")
public class ProjectCommitter {
	
	@Id
	@Column( name = "ID")
	@GeneratedValue
	private Integer id;
	
	@Column(name = "COMMITTER")
	private String committer;

	@ManyToOne( targetEntity  = stanly.server.GitProject.Model.ProjectInfo.class)
	@JoinColumn(name = "PROJECT_ID", nullable = false)
	private ProjectInfo PInfo;

	public ProjectCommitter(){
		
	}
	public ProjectCommitter(String committer, ProjectInfo pInfo) {
		super();
		this.committer = committer.toLowerCase();
		PInfo = pInfo;
	}

	public String getCommitter() {
		return committer;
	}

	public ProjectInfo getPInfo() {
		return PInfo;
	}
	public void setPInfo(ProjectInfo pInfo) {
		PInfo = pInfo;
	}
	@Override
	public String toString() {
		return "ProjectCommitter [id=" + id + ", committer=" + committer
				+ ", PInfo=" + PInfo + "]";
	}

	
	
	
		
}
