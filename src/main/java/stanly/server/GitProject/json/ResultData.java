package stanly.server.GitProject.json;

/**
 * @author Karuana
 * clone이나 analysis의 결과를 리턴하는 Json 객체이다.
 */
public class ResultData {
	/**
	 * 작업의 결과를 리턴한다.
	 */
	private boolean result;
	/**
	 * 프로젝트 명을 저장한다.
	 */
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
