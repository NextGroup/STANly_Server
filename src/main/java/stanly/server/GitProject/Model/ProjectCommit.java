package stanly.server.GitProject.Model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import stanly.server.Analysis.Model.ElementNode;

@Entity
@Table(name = "PROJECT_COMMIT")
public class ProjectCommit {
	@Id
	@Column( name = "COMMITID")
	@GeneratedValue
	private Long Commitid;
	
	@Column(name = "UpdateDate")
	private Date UpdateDate;
	@Column(name = "MESSAGE")
	private String Message;
	@Column(name = "AUTHOR")
	private String Author;
	
	@ManyToOne( targetEntity  = stanly.server.GitProject.Model.ProjectInfo.class)
	@JoinColumn(name = "PROJECT_ID", nullable = false)
	private ProjectInfo PInfo;
	
	public ProjectCommit(Date updateDate, String message, String author) {
		super();
		UpdateDate = updateDate;
		Message = message;
		Author = author;
	}

	public ProjectCommit(){
		
	}
	public Long getCommitid() {
		return Commitid;
	}


	public Date getUpdateDate() {
		return UpdateDate;
	}

	public void setUpdateDate(Date updateDate) {
		UpdateDate = updateDate;
	}

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		Author = author;
	}

	public ProjectInfo getProjectInfo() {
		return PInfo;
	}

	public void setProjectInfo(ProjectInfo projectInfo) {
		PInfo = projectInfo;
	}

	public String getMessage() {
		return Message;
	}

	public ProjectInfo getPInfo() {
		return PInfo;
	}

	public void setPInfo(ProjectInfo pInfo) {
		PInfo = pInfo;
	}

	@Override
	public String toString() {
		return "ProjectCommit [Commitid=" + Commitid + ", UpdateDate="
				+ UpdateDate + ", Message=" + Message + ", Author=" + Author
				+ ", PInfo=" + PInfo + "]";
	}
	

	
}
