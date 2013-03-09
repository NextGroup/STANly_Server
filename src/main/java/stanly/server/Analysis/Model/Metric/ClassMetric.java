package stanly.server.Analysis.Model.Metric;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import stanly.server.Analysis.Model.ProjectElementNode;
import stanly.server.Analysis.Model.Type.NodeType;

@Entity
@Table(name = "ClassMetric")
@PrimaryKeyJoinColumn(name="EMID")
public class ClassMetric extends ElementNodeMetric {

	
	@Column(name = "Classes", nullable = false)
	private int Classes;
	@Column(name = "Methods", nullable = false)
	private int Methods;
	@Column(name = "Fields", nullable = false)
	private int Fields;
	@Column(name = "LOC", nullable = false)
	private int LOC;
	@Column(name = "Fat", nullable = false)
	private int Fat;
	@Column(name = "AfferentCoupling", nullable = false)
	private int AfferentCoupling;
	@Column(name = "EfferentCoupling", nullable = false)
	private int EfferentCoupling;
	@Column(name = "WMC", nullable = false)
	private float WMC;
	@Column(name = "DIT", nullable = false)
	private int DIT;
	@Column(name = "NOC", nullable = false)
	private int NOC;
	@Column(name = "CBO", nullable = false)
	private int CBO;
	@Column(name = "RFC", nullable = false)
	private float RFC;
	@Column(name = "LCOM", nullable = false)
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
}
