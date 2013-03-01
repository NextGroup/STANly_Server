package stanly.server.sample.Data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PERSON")
public class Person implements Serializable{

	private static final long serialVersionUID = -5527566248002296042L;
	
	@Id
	@Column( name = "ID")
	@GeneratedValue
	private Integer id;
	
	@Column(name = "FIRST_NAME")
	private String firstname;
	
	@Column(name ="LAST_NAME")
	private String Lastname;
	
	@Column(name = "MONEY")
	private Double money;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return Lastname;
	}

	public void setLastname(String lastname) {
		Lastname = lastname;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	
}
