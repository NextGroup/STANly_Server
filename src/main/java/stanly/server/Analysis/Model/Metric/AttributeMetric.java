package stanly.server.Analysis.Model.Metric;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import stanly.server.Analysis.Model.ProjectElementNode;
import stanly.server.Analysis.Model.Type.NodeType;

/**
 * 클래스의 필드와 관련된 매트릭 정보를 정하는 클래스이다.
 * ElementNodeMetric을 상속받아 구현하고 있다.
 * 하이버네이트의 기본적인 상속 맵핑 방식중 Table per subclass를 이용하였다.
 * @author Karuana
 *
 */
@Entity
@Table(name = "AttributeMetric")
@PrimaryKeyJoinColumn(name="EMID")
public class AttributeMetric extends ElementNodeMetric{
	

	/**
	 * 인스트럭션 수
	 * Count Metrics
	 */
	@Column(name = "Instructions")
	private int Instructions;
	/**
	 * 예상 줄 수 ELOC = Estimated Lines of Code
	 * Count Metrics
	 */
	@Column(name = "ELOC")
	private int ELOC;
	/**
	 * Cyclomatic Complexity 
	 * Complexity Metrics
	 */
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
