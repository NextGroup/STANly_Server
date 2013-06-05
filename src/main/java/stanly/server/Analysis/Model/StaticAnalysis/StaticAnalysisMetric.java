package stanly.server.Analysis.Model.StaticAnalysis;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import stanly.server.Analysis.Model.StaticAnalysis.Type.StaticAnalysisType;
import stanly.server.GitProject.Model.ProjectCommit;
@Entity
@Table(name = "StaticAnalysisMetric")
public class StaticAnalysisMetric {

	@Id
	@Column( name = "SAID" , nullable = false)
	@GeneratedValue
	private Integer SAID;
	
	@Column(name="TYPE") 
	@Enumerated(EnumType.STRING)
	private StaticAnalysisType type;
	
	@Column(name="SOURCE")
	private String sourcePath;
	
	@Column(name="LINE")
	private Integer Line;
	
	@Column(name="MESSAGE")
	private String message;
	
	@ManyToOne( targetEntity  = stanly.server.GitProject.Model.ProjectCommit.class)
	@JoinColumn(name = "COMMITID", nullable = false)
	private ProjectCommit commit;
	
	@Column(name="NSLeft")
	private Integer NSLeft;
	
	
	public StaticAnalysisMetric(StaticAnalysisType type, String sourcePath,
			Integer line,Integer left, String message,ProjectCommit commit) {
		super();
		this.type = type;
		this.sourcePath = sourcePath;
		Line = line;
		this.message = message;
		this.commit=commit;
		this.NSLeft=left;
	}

	public Integer getSAID() {
		return SAID;
	}

	public StaticAnalysisType getType() {
		return type;
	}

	public String getSourcePath() {
		return sourcePath;
	}

	public Integer getLine() {
		return Line;
	}

	public String getMessage() {
		return message;
	}

	public ProjectCommit getCommit() {
		return commit;
	}

	public Integer getNSLeft() {
		return NSLeft;
	}
	
	
	
	
}
