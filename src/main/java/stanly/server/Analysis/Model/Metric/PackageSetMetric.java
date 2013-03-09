package stanly.server.Analysis.Model.Metric;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import stanly.server.Analysis.Model.ProjectElementNode;
import stanly.server.Analysis.Model.Type.NodeType;

@Entity
@Table(name = "PackageSetMetric")
@PrimaryKeyJoinColumn(name="EMID")
public class PackageSetMetric extends ElementNodeMetric{
	@Column(name = "NumberOfMethods", nullable = false)	
	private int NumberOfMethods;//Number of Method
	@Column(name = "NumberOfPackages", nullable = false)	
	private int NumberOfPackages;//Number of Packages
	@Column(name = "NumberOfClasses", nullable = false)	
	private int NumberOfClasses;// inner class
	@Column(name = "NumberOfClass", nullable = false)	
	private int NumberOfClass;	// unit class + inner class
	@Column(name = "NumberOfFields", nullable = false)	
	private int NumberOfFields;
	//private int LOC;
	@Column(name = "totalELOC", nullable = false)	
	private int totalELOC;
	@Column(name = "totalUnit", nullable = false)	
	private int totalUnit;
	@Column(name = "TotalCC", nullable = false)	
	private int TotalCC;
	@Column(name = "Fat", nullable = false)	
	private int Fat;
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

	
}
