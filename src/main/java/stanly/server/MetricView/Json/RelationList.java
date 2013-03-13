package stanly.server.MetricView.Json;

import java.util.ArrayList;

public class RelationList {
	private ArrayList<Relation> relation;

	public RelationList()
	{
		relation = new ArrayList<Relation>();
	}
	public ArrayList<Relation> getRelation() {
		return relation;
	}
	public void addRelation(Relation r)
	{
		relation.add(r);
	}
	
}
