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
import stanly.server.Analysis.Model.Type.NodeType;



@Entity
@Table(name="ElementNodeMetric")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ElementNodeMetric {
	
	@Id
	@Column( name = "EMID" , nullable = false)
	@GeneratedValue
	private Integer EMID;
	
	@OneToOne( targetEntity  = ProjectElementNode.class)
	@JoinColumn(name = "ElementID", nullable = false)
	private ProjectElementNode element;
	
	private NodeType type;
	
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

}
