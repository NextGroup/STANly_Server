package stanly.server.MetricView.Json;

public class PollutionValue extends MetricValue{
	private String Artifact;
	public PollutionValue(String artifact,String metric, int value) {
		super(metric, value);
		// TODO Auto-generated constructor stub
		Artifact = artifact;
	}
	public String getArtifact() {
		return Artifact;
	}
	public void setArtifact(String artifact) {
		Artifact = artifact;
	}
	

}
