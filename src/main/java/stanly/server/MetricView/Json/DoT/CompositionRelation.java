package stanly.server.MetricView.Json.DoT;

public class CompositionRelation {
	private String SourceId;
	private String TargetId;
	private int label;
	private boolean tangled;
	public CompositionRelation(String sourceId, String targetId, int label) {
		super();
		SourceId = sourceId;
		TargetId = targetId;
		this.label = label;
		tangled=false;
	}
	public String getSourceId() {
		return SourceId;
	}
	public void setSourceId(String sourceId) {
		SourceId = sourceId;
	}
	public String getTargetId() {
		return TargetId;
	}
	public void setTargetId(String targetId) {
		TargetId = targetId;
	}
	public int getLabel() {
		return label;
	}
	public void setLabel(int label) {
		this.label = label;
	}
	
	public boolean isTangled() {
		return tangled;
	}
	public void setTangled(boolean tangled) {
		this.tangled = tangled;
	}
	@Override
	public String toString() {
		return (tangled) ? SourceId+"->"+TargetId+"[label=\\\"" + label + "\\\" tangled=\\\"true\\\"];" : SourceId+"->"+TargetId+"[label=\\\"" + label + "\\\"];";
	}
	
	
}
