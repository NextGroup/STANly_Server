package stanly.server.Analysis.Model.Metric.Rate;

public class MetricRate {
	public final static int A_RATE = 1;
	public final static int B_RATE = 2;
	public final static int C_RATE = 3;
	public final static int F_RATE = 4;
	public final static int NO_RATE = 5;
	private MetricRate(){}
	public static String ChangeRate(int a)
	{
		String Temp =null;
		switch(a)
		{
			case MetricRate.A_RATE: 
				Temp = "A";
				break;
			case MetricRate.B_RATE:
				Temp = "B"; 
				break;
			case MetricRate.C_RATE:
				Temp = "C";
				break;
			case MetricRate.F_RATE:
				Temp = "F";
				break;
		}
		return Temp;
	}
}
