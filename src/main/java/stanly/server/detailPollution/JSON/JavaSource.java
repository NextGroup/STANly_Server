package stanly.server.detailPollution.JSON;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class JavaSource {
	private ArrayList<String> source;
	
	public JavaSource(){
		source = new ArrayList<String>();
	}
	public boolean updateSource(String src)
	{
		
		try {
			FileReader input =new FileReader(src);
			BufferedReader br = new BufferedReader(input);
			String readdata = null;
			while((readdata=br.readLine())!=null)
			{
				
				source.add(readdata.replaceAll("\\t", ""));
			}
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
