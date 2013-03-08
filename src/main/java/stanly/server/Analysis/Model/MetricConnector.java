package stanly.server.Analysis.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MetricConnector")
public class MetricConnector {
	@Id
	@Column( name = "MCID")
	@GeneratedValue
	private Integer MCID;
	
	
}
