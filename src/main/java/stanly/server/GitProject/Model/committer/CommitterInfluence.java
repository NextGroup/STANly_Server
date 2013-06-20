package stanly.server.GitProject.Model.committer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import stanly.server.Analysis.Model.ProjectElementNode;
import stanly.server.GitProject.Model.ProjectInfo;

@Entity
@Table(name = "COMMITTER_INFLUENCE")
public class CommitterInfluence {
	@Id
	@Column( name = "ID")
	@GeneratedValue
	private Long id;
	
	@Column(name = "COMMITTER")
	private String committer;
	
	@Column(name = "INFLUENCE")
	private String influenceClass;
	
	
	@ManyToOne( targetEntity  = stanly.server.GitProject.Model.ProjectInfo.class)
	@JoinColumn(name = "PROJECT_ID", nullable = false)
	private ProjectInfo PInfo;

	public CommitterInfluence()
	{
		
	}
	
	public CommitterInfluence(String committer, String influenceClass,
			ProjectInfo pInfo) {
		super();
		this.committer = committer;
		this.influenceClass = influenceClass;
		PInfo = pInfo;
	}

	public String getCommitter() {
		return committer;
	}

	public String getInfluenceClass() {
		return influenceClass;
	}

	public ProjectInfo getPInfo() {
		return PInfo;
	}
	
	
	public void setPInfo(ProjectInfo pInfo) {
		PInfo = pInfo;
	}
	
	
}
