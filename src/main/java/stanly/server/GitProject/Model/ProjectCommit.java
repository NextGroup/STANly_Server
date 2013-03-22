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

import stanly.server.Analysis.Model.ProjectElementNode;

/**
 * @author Karuana
 *	프로젝트의 커밋을 나타내는 객체이다. 
 */
@Entity
@Table(name = "PROJECT_COMMIT")
public class ProjectCommit {
	/**
	 * 	커밋 ID를 나타낸다. 이값은 하이버네이트에 의하여 자동으로 설정된다.
	 */
	@Id
	@Column( name = "COMMITID")
	@GeneratedValue
	private Long Commitid;
	
	/**
	 *  Commit이 업데이트된 날짜를 의미한다. 
	 */
	@Column(name = "UpdateDate")
	private Date UpdateDate;
	/**
	 * Commit 설정시 남긴 메시지를 의미한다. 
	 */
	@Column(name = "MESSAGE")
	private String Message;
	/**
	 * commit을 올린 작성자 정보를 나타낸다. 
	 */
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
