package stanly.server.Analysis.Model.Metric;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import stanly.server.Analysis.Model.ProjectElementNode;
import stanly.server.Analysis.Model.Metric.Rate.MetricRate;
import stanly.server.Analysis.Model.Type.NodeType;
/**
 * 패키지와 관련된 매트릭 정보를 정하는 클래스이다.
 * ElementNodeMetric을 상속받아 구현하고 있다.
 * 하이버네이트의 기본적인 상속 맵핑 방식중 Table per subclass를 이용하였다.
 * @author Karuana
 *
 */
@Entity
@Table(name = "PackageMetric")
@PrimaryKeyJoinColumn(name="EMID")
public class PackageMetric extends ElementNodeMetric{

	@Column(name = "NumberOfMethods")	
	private int NumberOfMethods;//Number of Method
	@Column(name = "NumberOfClasses")	
	private int NumberOfClasses;// inner class
	@Column(name = "NumberOfClass")	
	private int NumberOfClass;	// unit class + inner class
	@Column(name = "NumberOfAbstract")	
	private int NumberOfAbstract;//abstract class
	@Column(name = "NumberOfFields")	
	private int NumberOfFields;
	@Column(name = "Units")	
	private int	Units;//Num Of Class
	@Column(name = "TotalCC")	
	private int TotalCC;
	@Column(name = "LOC", nullable = false)	
	private int LOC;
	@Column(name = "Fat", nullable = false)	
	private int Fat;
	@Column(name = "ACDPerUnit", nullable = false)	
	private float ACDPerUnit;
	@Column(name = "AfferentCoupling", nullable = false)	
	private int AfferentCoupling;
	@Column(name = "EfferentCoupling", nullable = false)	
	private int EfferentCoupling;	
	@Column(name = "Abstractness", nullable = false)	
	private float Abstractness;
	@Column(name = "Instability", nullable = false)	
	private float Instability;
	@Column(name = "Distance", nullable = false)	
	private float Distance;
	//private float WMC;
	@Column(name = "totalDIT", nullable = false)	
	private int totalDIT;
	@Column(name = "totalNOC", nullable = false)	
	private int totalNOC;
	@Column(name = "totalCBO", nullable = false)	
	private int totalCBO;
	@Column(name = "totalRFC", nullable = false)	
	private int totalRFC;
	@Column(name = "totalLCOM", nullable = false)	
	private int totalLCOM;
	
	
	public PackageMetric() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PackageMetric(ProjectElementNode node, NodeType type) {
		super(node, type);
		// TODO Auto-generated constructor stub
	}
	public PackageMetric(NodeType type) {
		super(type);
		// TODO Auto-generated constructor stub
	}
	public int getNumberOfMethods() {
		return NumberOfMethods;
	}
	public void addNumberOfMethods(int numberOfMethods) {
		NumberOfMethods += numberOfMethods;
	}
	public int getNumberOfClasses() {
		return NumberOfClasses;
	}
	public void addNumberOfClasses(int numberOfClasses) {
		NumberOfClasses += numberOfClasses;
	}
	public int getNumberOfAbstract() {
		return NumberOfAbstract;
	}
	public void addNumberOfAbstract(int numberOfAbstract) {
		NumberOfAbstract += numberOfAbstract;
	}
	public int getNumberOfClass() {
		return NumberOfClass;
	}
	public void addNumberOfClass(int numberOfClass) {
		NumberOfClass += numberOfClass;
	}
	public int getNumberOfFields() {
		return NumberOfFields;
	}
	public void addNumberOfFields(int numberOfFields) {
		NumberOfFields += numberOfFields;
	}
	
	public int getUnits() {
		return Units;
	}
	public void addUnits(int units) {
		Units += units;
	}
	public float getClassesPerClass() {
		return NumberOfClass == 0 ? 0 : (float)NumberOfClasses / (float)NumberOfClass;//ClassesPerClass;
	}
	
	public int getTotalDIT() {
		return totalDIT;
	}
	public void setTotalDIT(int totalDIT) {
		this.totalDIT = totalDIT;
	}
	public int getTotalNOC() {
		return totalNOC;
	}
	public void setTotalNOC(int totalNOC) {
		this.totalNOC = totalNOC;
	}
	public int getTotalLCOM() {
		return totalLCOM;
	}
	public void setTotalLCOM(int totalLCOM) {
		this.totalLCOM = totalLCOM;
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
	public void setNumberOfAbstract(int numberOfAbstract) {
		NumberOfAbstract = numberOfAbstract;
	}
	public void setNumberOfFields(int numberOfFields) {
		NumberOfFields = numberOfFields;
	}
	public void setUnits(int units) {
		Units = units;
	}
	public void setTotalCC(int totalCC) {
		TotalCC = totalCC;
	}
	public void setTotalCBO(int totalCBO) {
		this.totalCBO = totalCBO;
	}
	public void setTotalRFC(int totalRFC) {
		this.totalRFC = totalRFC;
	}
	public float getMethodsPerClass() {
		return NumberOfClass == 0 ? 0 : (float)NumberOfMethods / (float)NumberOfClass;
	}

	public float getFieldsPerClass() {
		return NumberOfClass == 0 ? 0 : (float)NumberOfFields / (float)NumberOfClass;
	}
	public int getLOC() {
		return LOC;
	}
	public void setLOC(int lOC) {
		LOC = lOC;
	}
	public void addLOC(int lOC) {
		LOC += lOC;
	}
	public float getELOCPerUnit() {
		return Units == 0 ? 0 : (float)LOC / (float)Units;
	}	
	public float getAverageCC() {
		return NumberOfMethods == 0 ? 0 : (float)TotalCC / (float)NumberOfMethods;
	}
	public int getTotalCC() {
		return TotalCC;
	}
	public void addCC(int cC) {
		TotalCC += cC;
	}
	public int getFat() {
		return Fat;
	}
	public void setFat(int fAT) {
		Fat = fAT;
	}
	public float getACDPerUnit() {
		return ACDPerUnit;
	}
	public void setACDPerUnit(float aCDPerUnit) {
		ACDPerUnit = aCDPerUnit;
	}
	public int getAfferentCoupling() {
		return AfferentCoupling;
	}
	public void setAfferentCoupling(int afferentCoupling) {
		AfferentCoupling = afferentCoupling;
	}
	public int getEfferentCoupling() {
		return EfferentCoupling;
	}
	public void setEfferentCoupling(int efferentCoupling) {
		EfferentCoupling = efferentCoupling;
	}
	public float getAbstractness() {
		return Abstractness;
	}
	public void setAbstractness(float abstractness) {
		Abstractness = abstractness;
	}
	public float getInstability() {
		return Instability;
	}
	public void setInstability(float instability) {
		Instability = instability;
	}
	public float getDistance() {
		return Distance;
	}
	public void setDistance(float distance) {
		Distance = distance;
	}
	public float getWMC() {
		return NumberOfClass == 0 ? 0 : (float)TotalCC / (float)NumberOfClass;
	}
	//public void setWMC(float wMC) {
	//	WMC = wMC;
	//}
	public float getDIT() {
		return (float)totalDIT / (float)this.NumberOfClass;
	}
	public void addDIT(int dIT) {
		totalDIT += dIT;
	}
	public float getNOC() {
		return (float)totalNOC / (float)this.NumberOfClass;
	}
	public void addNOC(int nOC) {
		totalNOC += nOC;
	}
	public int getTotalCBO() {
		return totalCBO;
	}
	public float getAverageCBO() {
		return NumberOfClass == 0 ? 0 : (float)totalCBO / (float)NumberOfClass;
	}
	public void addCBO(int cBO) {
		totalCBO += cBO;
	}
	
	public float getAverageRFC() {
		return NumberOfClass == 0 ? 0 : (float)totalRFC / (float)NumberOfClass;
	}
	public int getTotalRFC() {
		return totalRFC;
	}
	public void addRFC(int rFC) {
		totalRFC += rFC;
	}
	public float getAverageLCOM() {
		return NumberOfClass == 0 ? 0 : (float)totalLCOM / (float)NumberOfClass;
	}
	public int getLCOM() {
		return totalLCOM;
	}
	public void addLCOM(int lCOM) {
		totalLCOM += lCOM;
	}
	public void setLCOM(int lCOM) {
		totalLCOM = lCOM;
	}
	
	@Override
	public void setRate()
	{
		if(Units<40)
			fatRate = MetricRate.A_RATE;
		else if(Units<60)
			fatRate = MetricRate.B_RATE;
		else 
			fatRate = MetricRate.C_RATE;
		
		if(this.getDistance()<0.5)
			CPRate = MetricRate.A_RATE;
		else if(this.getDistance()<0.8)
			CPRate = MetricRate.B_RATE;
		else
			CPRate = MetricRate.C_RATE;
		
		if(this.getFat()>240)
			CouplingRate = MetricRate.F_RATE;
		else if(this.getFat()>120)
			CouplingRate	 = MetricRate.C_RATE;
		else if(this.getFat()>60)
			CouplingRate	 = MetricRate.B_RATE;
		else 
			CouplingRate	 = MetricRate.A_RATE;
	}
}
