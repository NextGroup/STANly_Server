package stanly.server.Analysis.Model.Metric;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import stanly.server.Analysis.Model.ProjectElementNode;
import stanly.server.Analysis.Model.Type.NodeType;

/**
 * Metric 부분은 나중에 전부 손봐야할 가능성이 있음, 확장이 힘들다.
 * @author Karuana
 *
 */
@Entity
@Table(name = "AttributeMetric")
@PrimaryKeyJoinColumn(name="EMID")
public class AttributeMetric extends ElementNodeMetric{
	
	@Column(name = "Instructions")
	private int Instructions;
	@Column(name = "ELOC")
	private int ELOC;
	@Column(name = "CC")
	private int CC;
	

	public AttributeMetric() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AttributeMetric(ProjectElementNode node, NodeType type) {
		super(node, type);
		// TODO Auto-generated constructor stub
	}

	public AttributeMetric(NodeType type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	public AttributeMetric(int instructions, int eLOC, int cC, ProjectElementNode node, NodeType type) {
		super(node, type);
		Instructions = instructions;
		ELOC = eLOC;
		CC = cC;
	}
	
	public int getInstructions() {
		return Instructions;
	}
	public void setInstructions(int instructions) {
		Instructions = instructions;
	}
	public int getELOC() {
		return ELOC;
	}
	public void setELOC(int eLOC) {
		ELOC = eLOC;
	}
	public int getCC() {
		return CC;
	}
	public void setCC(int cC) {
		CC = cC;
	}
}
