package stanly.server.Analysis.Model.Relation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import stanly.server.Analysis.Model.Relation.Type.NodeRelationType;
import stanly.server.GitProject.Model.ProjectCommit;

/**
 * @author Karuana
 * 노드간의 관계를 저장하기 위한 객체 // 단순히 화면에 표시를 위해서만 사용, Composition은 다른 테이블을 이용할 
 */
@Entity
@Table(name = "NodeRelation")
public class NodeRelation {
	/**
	 * 테이블을 관리하기 위한 ID 값 
	 */
	@Id
	@Column( name = "NRID" , nullable = false)
	@GeneratedValue
	private Integer NRID;
	
	/**
	 *	시작 노드의 이름 
	 *  자바의 패키지 형태로 저장된다.
	 *  example) com.swmaestro.3rd
	 *   
	 */
	@Column(name = "SRCName" , nullable = false)
	private String SrcName;
	/**
	 *	끝 노드의 이름 
	 *  자바의 패키지 형태로 저장된다.
	 *  example) com.swmaestro.3rd
	 *   
	 */
	@Column(name = "TARName" , nullable = false)
	private String TarName;

	/**
	 *  어떠한 시점의 정보인지 나타내는 정보
	 *  우리는 기본적으로 Git의 커밋을 바탕으로 관리하기 때문에 ProjectCommit을 가르킨다. 
	 */
	@ManyToOne( targetEntity  = stanly.server.GitProject.Model.ProjectCommit.class)
	@JoinColumn(name = "COMMITID", nullable = false)
	private ProjectCommit commit;
	
	/**
	 * 노드의 참조 관계가 어떠한 형태인지 나타내는 값이다. 
	 */
	private NodeRelationType type;


	public NodeRelation()
	{
		
	}
	
	public NodeRelation(String srcName, String tarName, ProjectCommit commit,
			NodeRelationType type) {
		super();
		SrcName = srcName;
		TarName = tarName;
		this.commit = commit;
		this.type = type;
	}

	public NodeRelation(String srcName, String tarName,
			 NodeRelationType type)
	{
		super();
		SrcName = srcName;
		TarName = tarName;
		this.type = type;
	}
	public NodeRelation(Integer nRID, String srcName, String tarName,
			ProjectCommit commit, NodeRelationType type) {
		super();
		NRID = nRID;
		SrcName = srcName;
		TarName = tarName;
		this.commit = commit;
		this.type = type;
	}

	public Integer getNRID() {
		return NRID;
	}

	public void setNRID(Integer nRID) {
		NRID = nRID;
	}

	public String getSrcName() {
		return SrcName;
	}

	public void setSrcName(String srcName) {
		SrcName = srcName;
	}

	public String getTarName() {
		return TarName;
	}

	public void setTarName(String tarName) {
		TarName = tarName;
	}
	
	@Column(name="TYPE") 
	@Enumerated(EnumType.STRING)
	public NodeRelationType getType() {
		return type;
	}

	public void setType(NodeRelationType type) {
		this.type = type;
	}

	public ProjectCommit getCommit() {
		return commit;
	}

	public void setCommit(ProjectCommit commit) {
		this.commit = commit;
	}
	
	
	
}
