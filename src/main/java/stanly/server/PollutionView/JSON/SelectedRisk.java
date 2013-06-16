package stanly.server.PollutionView.JSON;

public class SelectedRisk {
	private int nsleft;
	private String type;
	private String rank;
	private String RiskName;
	private String Domain;
	private String DomainName;
	private int LinkedPerson;
	public SelectedRisk(int Nsleft, String type, String rank, String riskName,
			String domain, String domainName, int linkedPerson) {
		super();
		nsleft = Nsleft;
		this.type = type;
		this.rank = rank;
		RiskName = riskName;
		Domain = domain;
		DomainName = domainName;
		LinkedPerson = linkedPerson;
	}
	public String getRank() {
		return rank;
	}
	public SelectedRisk(int Nsleft, String Rank)
	{
		nsleft = Nsleft ;
		rank = Rank;
	}
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRiskName() {
		return RiskName;
	}
	public void setRiskName(String riskName) {
		RiskName = riskName;
	}
	public String getDomain() {
		return Domain;
	}
	public void setDomain(String domain) {
		Domain = domain;
	}
	public String getDomainName() {
		return DomainName;
	}
	public void setDomainName(String domainName) {
		DomainName = domainName;
	}
	public int getLinkedPerson() {
		return LinkedPerson;
	}
	public void setLinkedPerson(int linkedPerson) {
		LinkedPerson = linkedPerson;
	}
	@Override
	public String toString() {
		return "SelectedRisk [nsleft=" + nsleft + ", type=" + type + ", rank="
				+ rank + ", RiskName=" + RiskName + ", Domain=" + Domain
				+ ", DomainName=" + DomainName + ", LinkedPerson="
				+ LinkedPerson + "]";
	}
	
}
