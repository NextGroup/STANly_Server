package stanly.server.Analysis.Model.Metric;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import stanly.server.Analysis.Model.ProjectElementNode;
import stanly.server.Analysis.Model.Type.NodeType;

@Entity
@Table(name = "MethodMetric")
@PrimaryKeyJoinColumn(name="EMID")
public class MethodMetric extends ElementNodeMetric{
	@Column(name = "LOC", nullable = false)	
	private int LOC;
	@Column(name = "CC", nullable = false)
	private int CC;
	
	public MethodMetric() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MethodMetric(ProjectElementNode node, NodeType type) {
		super(node, type);
		// TODO Auto-generated constructor stub
	}
	public MethodMetric(NodeType type) {
		super(type);
		// TODO Auto-generated constructor stub
	}
	public int getLOC() {
		return LOC;
	}
	public void setLOC(int lOC) {
		LOC = lOC;
	}
	public int getCC() {
		return CC == 0 ? 1 : CC;
	}
	public void setCC(int cc){
		CC = cc;
	}

}
