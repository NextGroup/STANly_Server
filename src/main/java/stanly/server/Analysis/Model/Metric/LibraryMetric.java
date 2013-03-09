package stanly.server.Analysis.Model.Metric;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import stanly.server.Analysis.Model.ElementNode;
import stanly.server.Analysis.Model.Type.NodeType;

@Entity
@Table(name = "LibraryMetric")
@PrimaryKeyJoinColumn(name="EMID")
public class LibraryMetric extends ElementNodeMetric{
	@Column(name = "Unit", nullable = false)
	private int Unit;
	@Column(name = "Packages", nullable = false)
	private int Packages;
	@Column(name = "NumberOfClass", nullable = false)
	private int NumberOfClass;	// unit class + inner class
	@Column(name = "FatPackages", nullable = false)
	private int FatPackages;
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
	public LibraryMetric(ElementNode node, NodeType type) {
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
}
