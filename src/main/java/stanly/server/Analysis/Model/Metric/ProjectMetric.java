package stanly.server.Analysis.Model.Metric;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import stanly.server.Analysis.Model.ProjectElementNode;
import stanly.server.Analysis.Model.Type.NodeType;
/**
 * 프로젝트와 관련된 매트릭 정보를 정하는 클래스이다.
 * ElementNodeMetric을 상속받아 구현하고 있다.
 * 하이버네이트의 기본적인 상속 맵핑 방식중 Table per subclass를 이용하였다.
 * @author Karuana
 *
 */
@Entity
@Table(name = "ProjectMetric")
@PrimaryKeyJoinColumn(name="EMID")
public class ProjectMetric extends ElementNodeMetric{
	@Column(name = "Libraries")	
	private int Libraries;
	@Column(name = "Fat")	
	private float Fat;
	@Column(name = "Tangled")	
	private float Tangled;
	@Column(name = "ACDLibrary")	
	private float ACDLibrary;
	
	
	
	public float getACDLibrary() {
		return ACDLibrary;
	}
	public void setACDLibrary(float aCDLibrary) {
		ACDLibrary = aCDLibrary;
	}
	public ProjectMetric() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProjectMetric(ProjectElementNode node, NodeType type) {
		super(node, type);
		// TODO Auto-generated constructor stub
	}
	public ProjectMetric(NodeType type) {
		super(type);
		// TODO Auto-generated constructor stub
	}
	public int getLibraries() {
		return Libraries;
	}
	public void setLibraries(int libraries) {
		Libraries = libraries;
	}
	public float getFat() {
		return Fat;
	}
	public void setFat(float fat) {
		Fat = fat;
	}
	public float getTangled() {
		return Tangled;
	}
	public void setTangled(float tangled) {
		Tangled = tangled;
	}
	
}
