package stanly.server.Analysis.Model.Metric;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import stanly.server.Analysis.Model.ProjectElementNode;
import stanly.server.Analysis.Model.Metric.Rate.MetricRate;
import stanly.server.Analysis.Model.Type.NodeType;
import stanly.server.GitProject.Model.ProjectCommit;



/**
 * @author Karuana
 *	Metric정보들을 컨트롤하는 최상위 클래스이다. 여기에서는 매트릭 정보를 가지고 있지 않고
 *  어떠한 노드와 연결되는지 노드의 타입이 무엇인지만 정의되어있다. 
 */
@Entity
@Table(name="ElementNodeMetric")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ElementNodeMetric {
	
	/**
	 * id 값으로 자동으로 하이버네이트가 지정하게 된다.
	 */
	@Id
	@Column( name = "EMID" , nullable = false)
	@GeneratedValue
	private Integer EMID;
	
	/**
	 *  어떠한 노드가 가지고 있는지 나타낸다.
	 */
	@OneToOne( targetEntity  = ProjectElementNode.class)
	@JoinColumn(name = "ElementID", nullable = false)
	private ProjectElementNode element;
	

	/**
	 * 어떠한 타입에 매트릭인지 나타낸다.
	 */
	private NodeType type;
	
	@Column(name = "FATRate")
	protected int fatRate; // 카운
	@Column(name = "CPRate")
	protected int CPRate; //추상
	@Column(name = "CoRate")
	protected int CouplingRate;

	@Column(name = "TotalRate")
	protected int TotalRate;
	
	public ElementNodeMetric()
	{
		
	}
	
	public ElementNodeMetric(NodeType type) {
		super();
		this.type = type;
		
	}
	public ElementNodeMetric(ProjectElementNode node, NodeType type) {
		super();
		this.type = type;
		this.element=node;

		
	}


	public Integer getEMID() {
		return EMID;
	}

	@Column(name="TYPE") 
	@Enumerated(EnumType.STRING)
	public NodeType getType() {
		return type;
	}

	public ProjectElementNode getElement() {
		return element;
	}

	public void setElement(ProjectElementNode element) {
		this.element = element;

	}

	public int getFatRate() {
		return fatRate;
	}

	public void setFatRate(int fatRate) {
		this.fatRate = fatRate;
	}

	public int getCPRate() {
		return CPRate;
	}

	public void setCPRate(int cPRate) {
		CPRate = cPRate;
	}

	public int getCouplingRate() {
		return CouplingRate;
	}

	public void setCouplingRate(int couplingRate) {
		CouplingRate = couplingRate;
	}
	
	public int getTotalRate() {
		return TotalRate;
	}

	public void setTotalRate(int totalRate) {
		TotalRate = totalRate;
	}

	public abstract void setRate();
	
}
