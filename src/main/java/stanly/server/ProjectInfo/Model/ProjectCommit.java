package stanly.server.ProjectInfo.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PROJECT_COMMIT")
public class ProjectCommit {
	@Id
	@Column( name = "PROJECT_ID")
	@GeneratedValue
	private Integer pid;
}
