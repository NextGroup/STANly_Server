package stanly.server.CommonView.JSON;

import stanly.server.Analysis.Model.Type.NodeType;

public class CriticalRisk {
	private 	String Name;
	private String Rank;
	private NodeType Type;
	public CriticalRisk(String name, String rank, NodeType type) {
		super();
		Name = name;
		Rank = rank;
		Type = type;
	}
	public String getName() {
		return Name;
	}
	public String getRank() {
		return Rank;
	}
	public NodeType getType() {
		return Type;
	}
	
}
