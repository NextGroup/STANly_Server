package stanly.server.DevPersonalPage.JSON;

public class DevProjectInfo {
	private String pname;// = 'Example Project';
	private String prank;// = 'A'; // total pollution rank
	private int fyear;// = 2012;
	private int fmonth;// = 4;
	private int fday;// = 5;
	private int lyear;// = 2013;
	private int lmonth;// = 3;
	private int lday;// = 10;

	private String prank1;// = 'a';   // fat pollution
	private String prank2;// = 'b';   // coupling pollution
	private String prank3;// = 'c';   // naming pollution
	private String prank4;// = 'f';   // basic pollution

	
	public DevProjectInfo()
	{
		
	}
	public DevProjectInfo setPName(String name)
	{
		this.pname=name;
		return this;
	}
	public DevProjectInfo setPRank(String rank)
	{
		this.prank=rank;
		return this;
	}
	public DevProjectInfo setPollutionRank(String rank1, String rank2, String rank3, String rank4)
	{
		this.prank1=rank1;
		this.prank2=rank2;
		this.prank3=rank3;
		this.prank4=rank4;
		return this;
	}
	
	public DevProjectInfo setStartDay(int year,int month,int day)
	{
		this.fyear=year;
		this.fmonth=month;
		this.fday=day;
		return this;
	}
	
	public DevProjectInfo setLastDay(int year,int month,int day)
	{
		this.lyear=year;
		this.lmonth=month;
		this.lday=day;
		return this;
	}
}
