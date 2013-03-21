package stanly.server.MetricView.Json.DoT;

public class CompositionNode {
	private String id;
	private String label;

	private String subgraph;
	private String type;
	
	
	public CompositionNode(String id, String label, String subgraph, String type) {
		super();
		this.id = id;
		this.label = label;
		this.subgraph = subgraph;
		this.type = type;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getSubgraph() {
		return subgraph;
	}
	public void setSubgraph(String subgraph) {
		this.subgraph = subgraph;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	@Override
	public String toString() {
		String Data;
		if(subgraph != null)
			Data = id+"[label= \\\"" + label + "\\\", subgraph=\\\"" + subgraph
				+ "\\\", type=\\\"" + type + "\\\"];";
		else
			Data  = id+"[label= \\\"" + label + "\\\"" + ", type=\\\"" + type + "\\\"];";
		return Data;
	}
	
}
