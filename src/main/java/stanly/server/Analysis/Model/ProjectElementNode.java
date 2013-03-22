package stanly.server.Analysis.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import stanly.server.Analysis.Model.Metric.AttributeMetric;
import stanly.server.Analysis.Model.Metric.ClassMetric;
import stanly.server.Analysis.Model.Metric.ElementNodeMetric;
import stanly.server.Analysis.Model.Metric.LibraryMetric;
import stanly.server.Analysis.Model.Metric.MethodMetric;
import stanly.server.Analysis.Model.Metric.PackageMetric;
import stanly.server.Analysis.Model.Metric.PackageSetMetric;
import stanly.server.Analysis.Model.Metric.ProjectMetric;
import stanly.server.Analysis.Model.Type.NodeType;
import stanly.server.GitProject.Model.ProjectCommit;

/**
 * @author Karuana
 * 분석에 가장 핵심적인 ElementNode를 저장하는 클래스이다. 
 */
@Entity
@Table(name = "ElementNode")
public class ProjectElementNode {
	/**
	 * 기본적인 아이디 값이다.
	 * 하이버네이트가 자동으로 생성한다. 
	 */
	@Id
	@Column( name = "ElementID" , nullable = false)
	@GeneratedValue
	private Integer EID;

	/**
	 *  노드의 이름
	 *  자바의 패키지 타입 형태로 저장된다.
	 *  ex) com.sejong.AppStore
	 */
	@Column(name = "Name" , nullable = false)
	private String Name;
	
	/**
	 * 부모 노드의 이름이다.
	 * 부모가 없을 경우 기본적으로 Project라는 값이 들어간다. 
	 */
	@Column(name="ParentName", nullable = false)
	private String ParetnName;
	
	/**
	 * Nested Set Model로 디비에 트리 구조를 저장하기 위한 값  
	 *  
	 */
	@Column(name = "NSLeft", nullable = false)
	private int NSLeft;
	/**
	 * Nested Set Model로 디비에 트리 구조를 저장하기 위한 값  
	 *  
	 */
	@Column(name = "NSRight", nullable = false)
	private int NSRight;
	
	/**
	 *  Commit 시점을 저장하는 객체 
	 */
	@ManyToOne( targetEntity  = stanly.server.GitProject.Model.ProjectCommit.class)
	@JoinColumn(name = "COMMITID", nullable = false)
	private ProjectCommit commit;
	
	/**
	 * 노드에 해당하는 Metric 정보이다.
	 */
	@OneToOne(mappedBy="element")
	private ElementNodeMetric EMetric;
	
	/**
	 * 노드의 타입을 나타낸다. 
	 */
	@Column(name="TYPE") 
	@Enumerated(EnumType.STRING)
	private NodeType type;

	
	public ProjectElementNode(String name, String paretnName, int nSLeft, int nSRight,
			 NodeType type) {
		super();
		Name = name;
		ParetnName = paretnName;
		NSLeft = nSLeft;
		NSRight = nSRight;
		this.type = type;
		EMetric = null;
	}

	public ProjectElementNode()
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

	public ElementNodeMetric addElementMetric()
	{
		switch(this.type)
		{
			case PROJECT:
				this.EMetric = new ProjectMetric(type);
				break;
			case LIBRARY:
				this.EMetric = new LibraryMetric(type);
				break;
			case PACKAGE:
				this.EMetric = new PackageMetric(type);
				break;
			case PACKAGESET:
				this.EMetric = new PackageSetMetric(type);
				break;
			case CLASS:
			case ANNOTATION:
			case INTERFACE:
			case ENUM:
				this.EMetric = new ClassMetric(type);
				break;
			case FIELD:
				this.EMetric = new AttributeMetric(type);
				break;
			case METHOD:
			case CONSTRUCTOR:
				this.EMetric = new MethodMetric(type);
				break;
				
		}
		EMetric.setElement(this);
		return EMetric;
	}
	
	
	public ElementNodeMetric getEMetric() {
		return EMetric;
	}

	@Override
	public String toString() {
		return "ElementNode [EID=" + EID + ", Name=" + Name + ", ParetnName="
				+ ParetnName + ", NSLeft=" + NSLeft + ", NSRight=" + NSRight
				+ ", commit=" + commit + ", type=" + type + "]";
	}
	
	
}
