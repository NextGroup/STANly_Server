package stanly.server.CommonView.JSON;

public class StaticanalysisEntry {
	private String type;
	private String rate;

	public StaticanalysisEntry(String type, String rate) {
		super();
		this.type = type;
		this.rate = rate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}	
	
}
