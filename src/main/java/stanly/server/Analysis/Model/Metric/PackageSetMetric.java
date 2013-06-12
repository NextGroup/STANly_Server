package stanly.server.Analysis.Model.Metric;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import stanly.server.Analysis.Model.ProjectElementNode;
import stanly.server.Analysis.Model.Metric.Rate.MetricRate;
import stanly.server.Analysis.Model.Type.NodeType;
/**
 * 패키지셋과 관련된 매트릭 정보를 정하는 클래스이다.
 * ElementNodeMetric을 상속받아 구현하고 있다.
 * 하이버네이트의 기본적인 상속 맵핑 방식중 Table per subclass를 이용하였다.
 * @author Karuana
 *
 */
@Entity
@Table(name = "PackageSetMetric")
@PrimaryKeyJoinColumn(name="EMID")
public class PackageSetMetric extends ElementNodeMetric{
	@Column(name = "NumberOfMethods")	
	private int NumberOfMethods;//Number of Method
	@Column(name = "NumberOfPackages")	
	private int NumberOfPackages;//Number of Packages
	@Column(name = "NumberOfClasses")	
	private int NumberOfClasses;// inner class
	@Column(name = "NumberOfClass")	
	private int NumberOfClass;	// unit class + inner class
	@Column(name = "NumberOfFields")	
	private int NumberOfFields;
	//private int LOC;
	@Column(name = "totalELOC")	
	private int totalELOC;
	@Column(name = "totalUnit")	
	private int totalUnit;
	@Column(name = "TotalCC", nullable = false)	
	private int TotalCC;
	@Column(name = "Fat", nullable = false)	
	private int Fat;
	public int getNumberOfPackages() {
		return NumberOfPackages;
	}

	public void setNumberOfPackages(int numberOfPackages) {
		NumberOfPackages = numberOfPackages;
	}

	public void setNumberOfMethods(int numberOfMethods) {
		NumberOfMethods = numberOfMethods;
	}

	public void setNumberOfClasses(int numberOfClasses) {
		NumberOfClasses = numberOfClasses;
	}

	public void setNumberOfClass(int numberOfClass) {
		NumberOfClass = numberOfClass;
	}

	public void setNumberOfFields(int numberOfFields) {
		NumberOfFields = numberOfFields;
	}

	public void setTotalELOC(int totalELOC) {
		this.totalELOC = totalELOC;
	}

	public void setTotalUnit(int totalUnit) {
		this.totalUnit = totalUnit;
	}

	public void setTotalCC(int totalCC) {
		TotalCC = totalCC;
	}


	@Column(name = "Tangled", nullable = false)	
	private float Tangled;
	
	
	
	
	public PackageSetMetric() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PackageSetMetric(ProjectElementNode node, NodeType type) {
		super(node, type);
		// TODO Auto-generated constructor stub
	}

	public PackageSetMetric(NodeType type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	public float getClassesPerClass() {
		return NumberOfClass == 0 ? 0 : (float)NumberOfClasses / (float)NumberOfClass;//ClassesPerClass;
	}
	
	public float getMethodsPerClass() {
		return NumberOfClass == 0 ? 0 : (float)NumberOfMethods / (float)NumberOfClass;
	}

	public float getFieldsPerClass() {
		return NumberOfClass == 0 ? 0 : (float)NumberOfFields / (float)NumberOfClass;
	}

	
	public float getAverageCC() {
		return NumberOfMethods == 0 ? 0 : (float)TotalCC / (float)NumberOfMethods;
	}
	public int getTotalCC() {
		return TotalCC;
	}

	public int getFat() {
		return Fat;
	}
	public void setFat(float fAT) {
		Fat = (int) fAT;
	}
	public float getTangled() {
		return Tangled;
	}
	public void setTangled(float tangled) {
		Tangled = tangled;
	}
	public int getNumberOfMethods() {
		return NumberOfMethods;
	}

	public int getNumberOfPakcages() {
		return NumberOfPackages;
	}

	public int getNumberOfClasses() {
		return NumberOfClasses;
	}

	public int getNumberOfClass() {
		return NumberOfClass;
	}
	
	public int getNumberOfFields() {
		return NumberOfFields;
	}

	public float getAverageELOC() {
		return (float)totalELOC / (float)totalUnit;
	}
	public int getTotalELOC() {
		return totalELOC;
	}

	public int getTotalUnit() {
		return totalUnit;
	}
	@Override
	public void setRate()
	{
		fatRate = MetricRate.NO_RATE;
		CPRate = MetricRate.NO_RATE;
		if(this.getTangled()>0)
			CouplingRate = MetricRate.F_RATE;
		else 
			CouplingRate = MetricRate.A_RATE;
		
		TotalRate = (CouplingRate);
	}
	
}
