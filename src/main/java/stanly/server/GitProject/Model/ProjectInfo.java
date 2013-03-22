package stanly.server.GitProject.Model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Karuana
 *	프로젝트의 정보를 나타내는 객체이다.
 */
@Entity
@Table(name = "PROJECT_INFO")
public class ProjectInfo {

	/**
	 *  프로젝트 아이디 값 이값은 하이버네이트에의하여 자동으로 관리된다. 
	 */
	@Id
	@Column( name = "PROJECT_ID")
	@GeneratedValue
	private Integer pid;
	
	/**
	 * 프로젝트의 Git URL 정보를 나타낸다. 
	 */
	@Column(name = "URL")
	private String URL; 

	/**
	 * 해당 프로젝트가 저장된 로컬 위치를 나타낸다.
	 */
	@Column(name = "Location")
	private String Location;
	
	/**
	 * 프로젝트의 명을 나타낸다.
	 */
	@Column(name = "PROJECT_NAME")
	private String name;
	


	public ProjectInfo(String uRL, String location, String name) {
		super();
		URL = uRL;
		Location = location;
		this.name = name;
	}

	public ProjectInfo()
	{
		
	}
	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ProjectInfo [pid=" + pid + ", URL=" + URL + ", Location="
				+ Location + ", name=" + name 
				+ "]";
	}
	
}
