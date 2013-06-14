package stanly.server.Analysis.Model.Metric;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import stanly.server.Analysis.Model.ProjectElementNode;
import stanly.server.Analysis.Model.Metric.Rate.MetricRate;
import stanly.server.Analysis.Model.Type.NodeType;
/**
 * 라이브러리와 관련된 매트릭 정보를 정하는 클래스이다.
 * ElementNodeMetric을 상속받아 구현하고 있다.
 * 하이버네이트의 기본적인 상속 맵핑 방식중 Table per subclass를 이용하였다.
 * @author Karuana
 *
 */
@Entity
@Table(name = "LibraryMetric")
@PrimaryKeyJoinColumn(name="EMID")
public class LibraryMetric extends ElementNodeMetric{
	@Column(name = "Unit")
	private int Unit;
	@Column(name = "Packages")
	private int Packages;
	@Column(name = "NumberOfClass")
	private int NumberOfClass;	// unit class + inner class
	@Column(name = "FatPackages")
	private int FatPackages;
	public int getUnit() {
		return Unit;
	}
	public void setUnit(int unit) {
		Unit = unit;
	}
	public float getTotalDistance() {
		return totalDistance;
	}
	public void setTotalDistance(float totalDistance) {
		this.totalDistance = totalDistance;
	}
	public float getTotalDistanceAbsolute() {
		return totalDistanceAbsolute;
	}
	public void setTotalDistanceAbsolute(float totalDistanceAbsolute) {
		this.totalDistanceAbsolute = totalDistanceAbsolute;
	}
	public int getTotalWMC() {
		return totalWMC;
	}
	public void setTotalWMC(int totalWMC) {
		this.totalWMC = totalWMC;
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
	public void setPackages(int packages) {
		Packages = packages;
	}
	public void setNumberOfClass(int numberOfClass) {
		NumberOfClass = numberOfClass;
	}
	public void setFatPackages(int fatPackages) {
		FatPackages = fatPackages;
	}
	public void setFatUnits(int fatUnits) {
		FatUnits = fatUnits;
	}
	public void setTotalCBO(int totalCBO) {
		this.totalCBO = totalCBO;
	}
	public void setTotalRFC(int totalRFC) {
		this.totalRFC = totalRFC;
	}
	@Column(name = "FatUnits", nullable = false)
	private int FatUnits;
	@Column(name = "Tangled", nullable = false)
	private float Tangled;
	@Column(name = "ACDPackage", nullable = false)
	private float ACDPackage;
	@Column(name = "ACDUnit", nullable = false)
	private float ACDUnit;
	@Column(name = "totalDistance", nullable = false)
	private float totalDistance;
	@Column(name = "totalDistanceAbsolute", nullable = false)
	private float totalDistanceAbsolute;
	@Column(name = "totalWMC", nullable = false)
	private int totalWMC;
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
	
	public LibraryMetric() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LibraryMetric(ProjectElementNode node, NodeType type) {
		super(node, type);
		// TODO Auto-generated constructor stub
	}
	public LibraryMetric(NodeType type) {
		super(type);
		// TODO Auto-generated constructor stub
	}
	public int getPackages() {
		return Packages;
	}
	public void addPackages(int packages) {
		Packages += packages;
	}
	public float getUnitPerPackage() {
		return Packages == 0 ? 0 : (float)Unit / (float)Packages;
	}
	public int getUnits()
	{
		return Unit;
	}
	public void addUnits(int unit) {
		Unit += unit;
	}	
	public int getFatPackages() {
		return FatPackages;
	}
	public void addFatPackages(int fatPackages) {
		FatPackages += fatPackages;
	}
	public int getFatUnits() {
		return FatUnits;
	}
	public void addFatUnits(int fatUnits) {
		FatUnits += fatUnits;
	}
	public float getTangled() {
		return Tangled;
	}
	public void setTangled(float tangled) {
		Tangled = tangled;
	}
	public float getACDPackage() {
		return ACDPackage;
	}
	public void setACDPackage(float aCDPackage) {
		ACDPackage = aCDPackage;
	}
	public float getACDUnit() {
		return ACDUnit;
	}
	public void setACDUnit(float aCDUnit) {
		ACDUnit = aCDUnit;
	}
	public float getDistance() {
		return Packages == 0 ? 0 : totalDistance / (float)Packages;
	}
	public void addDistance(float distance) {
		totalDistance += distance;
	}
	public float getDistanceAbsolute() {
		return Packages == 0 ? 0 : totalDistanceAbsolute / (float)Packages;
	}
	public void addDistanceAbsolute(float distanceAbsolute) {
		totalDistanceAbsolute += distanceAbsolute;
	}
	public float getWMC() {
		return (float)totalWMC / (float)NumberOfClass;
	}
	public void addWMC(int wMC) {
		totalWMC += wMC;
	}
	public float getDIT() {
		return (float)totalDIT / (float)NumberOfClass;
	}
	public void addDIT(int dIT) {
		totalDIT += dIT;
	}
	public float getNOC() {
		return (float)totalNOC / (float)NumberOfClass;
	}
	public void addNOC(float nOC) {
		totalNOC += nOC;
	}
	public float getAverageCBO() {
		return NumberOfClass == 0 ? 0 : (float)totalCBO / (float)NumberOfClass;
	}
	public int getTotalCBO() {
		return totalCBO;
	}
	public void addCBO(float cBO) {
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
	public int getNumberOfClass() {
		return NumberOfClass;
	}
	public void addNumberOfClass(int numberOfClass) {
		NumberOfClass += numberOfClass;
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
		
		fatRate = MetricRate.NO_RATE;
		CPRate = MetricRate.NO_RATE;
		if(this.getTangled()>0)
			TangleRate = CouplingRate = MetricRate.F_RATE;
		else 
			TangleRate = CouplingRate = MetricRate.A_RATE;
		TotalRate =  CouplingRate;
	}
}
