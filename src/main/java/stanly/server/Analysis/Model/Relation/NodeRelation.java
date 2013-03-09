package stanly.server.Analysis.Model.Relation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import stanly.server.Analysis.Model.Relation.Type.NodeRelationType;

@Entity
@Table(name = "NodeRelation")
public class NodeRelation {
	@Id
	@Column( name = "NRID" , nullable = false)
	@GeneratedValue
	private Integer NRID;
	@Column(name = "SRCName" , nullable = false)
	private String SrcName;
	@Column(name = "TARName" , nullable = false)
	private String TarName;
	
	private NodeRelationType type;

	public Integer getNRID() {
		return NRID;
	}

	public void setNRID(Integer nRID) {
		NRID = nRID;
	}

	public String getSrcName() {
		return SrcName;
	}

	public void setSrcName(String srcName) {
		SrcName = srcName;
	}

	public String getTarName() {
		return TarName;
	}

	public void setTarName(String tarName) {
		TarName = tarName;
	}
	
	@Column(name="TYPE") 
	@Enumerated(EnumType.STRING)
	public NodeRelationType getType() {
		return type;
	}

	public void setType(NodeRelationType type) {
		this.type = type;
	}
	
	
	
}
