package stanly.server.GitProject.Model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PROJECT_INFO")
public class ProjectInfo {

	@Id
	@Column( name = "PROJECT_ID")
	@GeneratedValue
	private Integer pid;
	
	@Column(name = "URL")
	private String URL;

	@Column(name = "Location")
	private String Location;
	
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
