package stanly.server.GitProject.Model;

import java.util.Date;
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
	
	// 최신 랭크 
	@Column(name = "FAT_RANK")
	private int FAT_RANK;
	
	@Column(name = "CO_RANK")
	private int CoplingRANK;
	
	@Column(name = "CP_RANK")
	private int CP_RANK;
	
	@Column(name = "NAME_RANK")
	private int Name_RANK;
	
	@Column(name = "BASIC_RANK")
	private int Basic_RANK;
	
	@Column(name = "FirstDate")
	private Date FirstDate;
	
	@Column(name = "LastDate")
	private Date LastDate;
	
	public ProjectInfo(String uRL, String location, String name, Date first) {
		super();
		URL = uRL;
		Location = location;
		this.name = name;
		FAT_RANK=0;
		CoplingRANK=0;
		CP_RANK=0;
		Name_RANK=0;
		Basic_RANK=0;
		FirstDate=first;
		LastDate=first;
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
	

	public int getFAT_RANK() {
		return FAT_RANK;
	}

	public void setFAT_RANK(int fAT_RANK) {
		FAT_RANK = fAT_RANK;
	}

	public int getCoplingRANK() {
		return CoplingRANK;
	}

	public void setCoplingRANK(int coplingRANK) {
		CoplingRANK = coplingRANK;
	}

	public int getCP_RANK() {
		return CP_RANK;
	}

	public void setCP_RANK(int cP_RANK) {
		CP_RANK = cP_RANK;
	}

	public int getName_RANK() {
		return Name_RANK;
	}

	public void setName_RANK(int name_RANK) {
		Name_RANK = name_RANK;
	}

	public int getBasic_RANK() {
		return Basic_RANK;
	}

	public void setBasic_RANK(int basic_RANK) {
		Basic_RANK = basic_RANK;
	}

	public Date getLastDate() {
		return LastDate;
	}

	public void setLastDate(Date lastDate) {
		LastDate = lastDate;
	}

	public Date getFirstDate() {
		return FirstDate;
	}

	@Override
	public String toString() {
		return "ProjectInfo [pid=" + pid + ", URL=" + URL + ", Location="
				+ Location + ", name=" + name 
				+ "]";
	}
	
}
