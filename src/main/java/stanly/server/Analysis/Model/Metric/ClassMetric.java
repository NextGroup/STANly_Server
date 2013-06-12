package stanly.server.Analysis.Model.Metric;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import stanly.server.Analysis.Model.ProjectElementNode;
import stanly.server.Analysis.Model.Metric.Rate.MetricRate;
import stanly.server.Analysis.Model.Type.NodeType;
/**
 * 클래스와 관련된 매트릭 정보를 정하는 클래스이다.
 * ElementNodeMetric을 상속받아 구현하고 있다.
 * 하이버네이트의 기본적인 상속 맵핑 방식중 Table per subclass를 이용하였다.
 * @author Karuana
 *
 */
@Entity
@Table(name = "ClassMetric")
@PrimaryKeyJoinColumn(name="EMID")
public class ClassMetric extends ElementNodeMetric {

	
	/**
	 * Count Metrics
	 */
	@Column(name = "Classes")
	private int Classes;
	/**
	 * 메소드의 수
	 * Count Metrics
	 */
	@Column(name = "Methods")
	private int Methods;
	/**
	 * 필드의 수 
	 * Count Metrics
	 */
	@Column(name = "Fields")
	private int Fields;
	/**
	 *  라인 오브 코드 (Line Of Code)
	 *  Count Metrics
	 */
	@Column(name = "LOC")
	private int LOC;
	/**
	 * 
	 * 	Complexity Metrics
	 */
	@Column(name = "Fat")
	private int Fat;
	/**
	 * 
	 * Robert C. Martin Metrics
	 */
	@Column(name = "AfferentCoupling")
	private int AfferentCoupling;
	/**
	 * 
	 * Robert C. Martin Metrics
	 */
	@Column(name = "EfferentCoupling")
	private int EfferentCoupling;
	/**
	 * 
	 * Chidamber & Kemerer Metrics
	 */
	@Column(name = "WMC")
	private float WMC;
	/**
	 * Weighted Methods per Class 
	 * Chidamber & Kemerer Metrics
	 */
	@Column(name = "DIT")
	private int DIT;
	/**
	 * 
	 * Chidamber & Kemerer Metrics
	 */
	@Column(name = "NOC")
	private int NOC;
	/**
	 * 
	 * Chidamber & Kemerer Metrics
	 */
	@Column(name = "CBO")
	private int CBO;
	/**
	 * 
	 * Chidamber & Kemerer Metrics
	 */
	@Column(name = "RFC")
	private float RFC;
	/**
	 * 
	 * Chidamber & Kemerer Metrics
	 */
	@Column(name = "LCOM")
	private int LCOM;
	
	
	public ClassMetric()
	{
		
	}
	public ClassMetric(ProjectElementNode node, NodeType type)
	{
		super(node,type);
	}
	public ClassMetric(int classes, int methods, int fields, int lOC, int fat,
			int afferentCoupling, int efferentCoupling, float wMC, int dIT,
			int nOC, int cBO, float rFC, int lCOM,ProjectElementNode node, NodeType type) {
		super(node,type);
		Classes = classes;
		Methods = methods;
		Fields = fields;
		LOC = lOC;
		Fat = fat;
		AfferentCoupling = afferentCoupling;
		EfferentCoupling = efferentCoupling;
		WMC = wMC;
		DIT = dIT;
		NOC = nOC;
		CBO = cBO;
		RFC = rFC;
		LCOM = lCOM;
	}
	public ClassMetric(NodeType type) {
		super(type);
		// TODO Auto-generated constructor stub
	}
	public int getClasses() {
		return Classes;
	}
	public void setClasses(int classes) {
		Classes = classes;
	}
	public void setMethods(int methods) {
		Methods = methods;
	}
	public void setFields(int fields) {
		Fields = fields;
	}
	public void setWMC(float wMC) {
		WMC = wMC;
	}
	public void setCBO(int cBO) {
		CBO = cBO;
	}
	public void setLCOM(int lCOM) {
		LCOM = lCOM;
	}
	public int getMethods() {
		return Methods;
	}
	public int getFields() {
		return Fields;
	}
	public int getLOC() {
		return LOC;
	}
	public void setLOC(int lOC) {
		LOC = lOC;
	}
	public int getFat() {
		return Fat;
	}
	public void setFat(int fat) {
		Fat = fat;
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
	public float getWMC() {
		return WMC;
	}
	public int getDIT() {
		return DIT;
	}
	public void setDIT(int dIT) {
		DIT = dIT;
	}
	public int getNOC() {
		return NOC;
	}
	public void setNOC(int nOC) {
		NOC = nOC;
	}
	public float getCBO() {
		return CBO;
	}
	public float getRFC() {
		return RFC;
	}
	public void setRFC(float rFC) {
		RFC = rFC;
	}
	public int getLCOM() {
		return LCOM;
	}
	
	@Override
	public void setRate()
	{
		int methods = (Methods<50) ? MetricRate.A_RATE: ((Methods<100)? MetricRate.C_RATE:MetricRate.F_RATE);
		int fields = (Fields<20) ? MetricRate.A_RATE:((Fields<40) ? MetricRate.C_RATE: MetricRate.F_RATE);
		int eloc= (LOC<300) ? MetricRate.A_RATE : ((LOC<400) ? MetricRate.C_RATE: MetricRate.F_RATE);
		
		fatRate = (int) Math.ceil((methods+fields+eloc)/3.0f);
		
		if(DIT>5)
			CPRate = MetricRate.A_RATE;
		else if(DIT>7)
			CPRate = MetricRate.B_RATE;
		else 
			CPRate = MetricRate.F_RATE;
		
		int cboRate = (CBO<25) ? MetricRate.A_RATE: MetricRate.C_RATE;
		int rfcRate = (RFC<100) ? MetricRate.A_RATE: MetricRate.C_RATE;
		int fRate = (Fat<60) ? MetricRate.A_RATE: ((Fat<120) ? MetricRate.B_RATE: MetricRate.C_RATE);
		CouplingRate = (cboRate+rfcRate+fRate)/3;
		
		TotalRate = (fatRate +CPRate+ CouplingRate)/3;
	}
}
