package stanly.server.CommonView.JSON;

public class PollutionRatio {
	private String key;
	private int y;
	public PollutionRatio(String key, int y) {
		super();
		this.key = key;
		this.y = y;
	}
	public String getKey() {
		return key;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
