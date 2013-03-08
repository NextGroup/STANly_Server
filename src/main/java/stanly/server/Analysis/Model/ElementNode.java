package stanly.server.Analysis.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import stanly.server.Analysis.Model.Type.NodeType;
import stanly.server.GitProject.Model.ProjectCommit;

@Entity
@Table(name = "ElementNode")
public class ElementNode {
	@Id
	@Column( name = "ElementID")
	@GeneratedValue
	private Integer EID;

	@Column(name = "Name")
	private String Name;
	
	@Column(name="ParentName")
	private String ParetnName;
	
	@Column(name = "NSLeft")
	private int NSLeft;
	
	@Column(name = "NSRight")
	private int NSRight;
	
	@ManyToOne( targetEntity  = stanly.server.GitProject.Model.ProjectCommit.class)
	@JoinColumn(name = "COMMITID", nullable = false)
	private ProjectCommit commit;
	
	private NodeType type;

	
	public ElementNode(String name, String paretnName, int nSLeft, int nSRight,
			 NodeType type) {
		super();
		Name = name;
		ParetnName = paretnName;
		NSLeft = nSLeft;
		NSRight = nSRight;
		this.type = type;
	}

	public ElementNode()
	{
		
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getParetnName() {
		return ParetnName;
	}

	public void setParetnName(String paretnName) {
		ParetnName = paretnName;
	}

	public int getNSLeft() {
		return NSLeft;
	}

	public void setNSLeft(int nSLeft) {
		NSLeft = nSLeft;
	}

	public int getNSRight() {
		return NSRight;
	}

	public void setNSRight(int nSRight) {
		NSRight = nSRight;
	}

	@Column(name="TYPE") 
	@Enumerated(EnumType.STRING)
	public NodeType getType() {
		return type;
	}

	public void setType(NodeType type) {
		this.type = type;
	}

	
	public Integer getEID() {
		return EID;
	}

	public ProjectCommit getCommit() {
		return commit;
	}

	public void setCommit(ProjectCommit commit) {
		this.commit = commit;
	}

	@Override
	public String toString() {
		return "ElementNode [EID=" + EID + ", Name=" + Name + ", ParetnName="
				+ ParetnName + ", NSLeft=" + NSLeft + ", NSRight=" + NSRight
				+ ", commit=" + commit + ", type=" + type + "]";
	}
	
	
}
