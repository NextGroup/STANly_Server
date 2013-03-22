package stanly.server.Analysis.Model.Metric;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import stanly.server.Analysis.Model.ProjectElementNode;
import stanly.server.Analysis.Model.Type.NodeType;
/**
 * 메소드와 관련된 매트릭 정보를 정하는 클래스이다.
 * ElementNodeMetric을 상속받아 구현하고 있다.
 * 하이버네이트의 기본적인 상속 맵핑 방식중 Table per subclass를 이용하였다.
 * @author Karuana
 *
 */
@Entity
@Table(name = "MethodMetric")
@PrimaryKeyJoinColumn(name="EMID")
public class MethodMetric extends ElementNodeMetric{
	@Column(name = "LOC")	
	private int LOC;
	@Column(name = "CC")
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
