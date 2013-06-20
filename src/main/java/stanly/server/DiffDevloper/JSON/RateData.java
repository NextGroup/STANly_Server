package stanly.server.DiffDevloper.JSON;

public class RateData {
	private int fat;
	private int cprate;
	private int corate;
	private int fatCount;
	private int cpCount;
	private int coCount;
	private int commit;
	
	public RateData()
	{
		commit = cpCount = coCount = fatCount = fat = cprate =corate =0;
	}
	
	public int getCommit() {
		return commit;
	}

	public void setCommit(int commit) {
		this.commit = commit;
	}

	public void addFat(int f)
	{
		fat= (fat> f) ? fat:f;
		fatCount++;
	}
	public void addCprate(int cp)
	{
		cprate = (cprate > cp) ? cprate: cp;
		cpCount++;
	}
	public void addCorate(int co)
	{
		corate = (corate > co ) ? corate: co;
		coCount++;
	}

	public int getFat()
	{
		return Math.round(fat);
	}
	public int getCprate()
	{
		return Math.round(cprate);
	}
	public int getCorate()
	{
		return Math.round(corate);
	}
}
