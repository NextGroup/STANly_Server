package stanly.server.GitProject.json;

public class ResultData {
	private boolean result;
	private String projectName;
	
	


	public ResultData(boolean result, String projectName) {
		super();
		this.result = result;
		this.projectName = projectName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
}
