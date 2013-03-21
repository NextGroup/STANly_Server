package stanly.server.MetricView.Json.DoT;

import java.util.ArrayList;

public class CompositionView {

	private ArrayList<CompositionNode> node;
	private ArrayList<CompositionRelation> relation;
	private String id;
	public CompositionView(String id) {
		super();
		this.id = id;
		node = new ArrayList<CompositionNode>();
		relation = new ArrayList<CompositionRelation>();
	}
	
	public void addNode(CompositionNode n)
	{
		node.add(n);	
	}
	
	public void addRelation(CompositionRelation r)
	{
		relation.add(r);
	}
	
	@Override
	public String toString() {
		String Data = "digraph { ";
		for(int i=0;i<node.size();i++)
			Data+=node.get(i).toString();
		for(int i=0;i<relation.size();i++)
			Data+=relation.get(i).toString();
		
		Data+="}";
		return Data;
	}
}
