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

import stanly.server.Analysis.Model.Relation.Type.NodeRelationLevel;
import stanly.server.Analysis.Model.Relation.Type.NodeRelationType;
import stanly.server.GitProject.Model.ProjectCommit;

/**
 * @author Karuana
 *	Composition 구성 정보를 저장하는 데이터, 이를 바탕으로 Composition을 그릴 수 있도록 변경할 
 */
@Entity
@Table(name = "NodeComposition")
public class NodeComposition {
	@Id
	@Column( name = "NCID" , nullable = false)
	@GeneratedValue
	private Integer NCID;
	
	@ManyToOne( targetEntity  = stanly.server.GitProject.Model.ProjectCommit.class)
	@JoinColumn(name = "COMMITID", nullable = false)
	private ProjectCommit commit;
	
	@Column(name = "SRCID" , nullable = false)
	private int SrcID;
	
	@Column(name = "TARID" , nullable = false)
	private int TarID;
	
	@Column(name = "Count" , nullable = false)
	private int Count;

	
	/**
	 * 노드의 참조 관계를 대표할 수 있는 것을 나타내는 것 
	 */
	@Column(name="TYPE") 
	@Enumerated(EnumType.STRING)
	private NodeRelationType type;
	
	@Column(name="Level") 
	@Enumerated(EnumType.STRING)
	private NodeRelationLevel level;
	






	/**
	 * @param commit 커밋 시
	 * @param srcID 아이디 (NS Left)
	 * @param tarID 타겟 아이디(NS Right)
	 * @param count 카운트 
	 * @param type 릴레이션을 대표하는 타입 
	 * @param level 컴포지션의 수준 
	 */
	public NodeComposition(ProjectCommit commit, int srcID, int tarID,
			int count, NodeRelationType type, NodeRelationLevel level) {
		super();
		this.commit = commit;
		SrcID = srcID;
		TarID = tarID;
		Count = count;
		this.type = type;
		this.level = level;
	}


	public NodeRelationType getType() {
		return type;
	}

	
	public NodeRelationLevel getLevel() {
		return level;
	}


	public ProjectCommit getCommit() {
		return commit;
	}



	public void setCommit(ProjectCommit commit) {
		this.commit = commit;
	}



	public Integer getNCID() {
		return NCID;
	}



	public int getSrcID() {
		return SrcID;
	}



	public int getTarID() {
		return TarID;
	}



	public int getCount() {
		return Count;
	}
	

	
}
