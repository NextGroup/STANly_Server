package stanly.server.Analysis.Model.Relation;

import javax.persistence.Entity;
import javax.persistence.Table;

import stanly.server.Analysis.Model.Relation.Type.NodeRelationType;

public class NodeRelation {
	private Integer NRID;
	private String SrcName;
	private String TarName;
	private NodeRelationType type;
	
}
