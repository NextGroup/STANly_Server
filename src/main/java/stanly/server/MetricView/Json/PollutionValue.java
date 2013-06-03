package stanly.server.MetricView.Json;

public class PollutionValue extends MetricValue{
	private String Artifact;
	private String Type;
	public PollutionValue(String artifact,String metric, float value, int type) {
		super(metric, value);
		// TODO Auto-generated constructor stub
		Artifact = artifact;
		Type = (type == 0)  ? "Warning":"Risk";
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getArtifact() {
		return Artifact;
	}
	public void setArtifact(String artifact) {
		Artifact = artifact;
	}
	

}
