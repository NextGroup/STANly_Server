package stanly.server.GitProject.json;

/**
 * @author Karuana
 *	프로젝트의 상태를 나타내는 Json 객체이다. 
 */
public class ProjectStateJSON {
	/**
	 * 프로젝트 상태를 저장한다.
	 */
	private String state;

	public ProjectStateJSON(String state) {
		super();
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
