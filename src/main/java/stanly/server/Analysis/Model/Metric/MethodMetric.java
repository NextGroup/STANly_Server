 package stanly.server.Analysis.Model.Metric;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import stanly.server.Analysis.Model.ProjectElementNode;
import stanly.server.Analysis.Model.Metric.Rate.MetricRate;
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
	@Override
	public void setRate()
	{
		UnitsRate =MetricRate.NO_RATE;
		ELOCRate = MetricRate.NO_RATE;
		NOMRate = MetricRate.NO_RATE;
		NOFRate = MetricRate.NO_RATE;
		CCRate = MetricRate.NO_RATE;
		TangleRate = MetricRate.NO_RATE;
		NoRRate	= MetricRate.NO_RATE;
		DRate = MetricRate.NO_RATE;
		DITRate = MetricRate.NO_RATE;
		
		fatRate = MetricRate.NO_RATE;
		CPRate = MetricRate.NO_RATE;
		int CCRate = (CC<=10) ? MetricRate.A_RATE: ((CC<=15) ? MetricRate.B_RATE:(CC<=20) ? MetricRate.C_RATE:MetricRate.F_RATE);
		int ELOCRate  = (LOC<=60) ? MetricRate.A_RATE: ((LOC<=120) ? MetricRate.B_RATE:MetricRate.C_RATE);
		
		fatRate = (int) Math.ceil((CCRate+ELOCRate)/2.0f);
			
		CPRate = MetricRate.NO_RATE;
		CouplingRate = MetricRate.NO_RATE;
		
		TotalRate = fatRate;
	}
}
