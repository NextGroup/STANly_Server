package stanly.server.MetricView.Json;

public class Relation {
	private String Sources;
	private String Relations;
	private String Targets;
	
	
	
	public Relation(String sources, String targets, String relations ) {
		super();
		Sources = sources;
		Relations = relations;
		Targets = targets;
	}
	public String getSources() {
		return Sources;
	}
	public void setSources(String sources) {
		Sources = sources;
	}
	public String getRelations() {
		return Relations;
	}
	public void setRelations(String relations) {
		Relations = relations;
	}
	public String getTargets() {
		return Targets;
	}
	public void setTargets(String targets) {
		Targets = targets;
	}
	
}
