package stanly.server.GitProject.Git.FileName;

import java.util.regex.Pattern;

public class FilenameCutter {
	
	private FilenameCutter(){
		
	}
	public static boolean IsJavaFile(String fileName)
	{
		
		String testString = "*.java";

	    // convert user input string to regular e-pression
	    String testPattern = testString.replaceAll("\\.", "\\\\.");
	    testPattern = testPattern.replaceAll("\\*", ".*");
	    testPattern = testPattern.replaceAll("\\?", ".");
	    // 완벽일치가 아니라 도중에 나와도 일치한다고 판정하고 싶으면 아래 코드를 주석처리할 것
	    testPattern = "^" + testPattern + "$";
	   
	                    // 대소문자 구분하지 않으려면 lowercase를 testPattern과 fileName에 적용하기

		if (Pattern.matches(testPattern, fileName)) {
				return true;
		}
		else {
			return false;
			}
		
	}
	
	public static String GetFileName(String FileName)
	{
		String[] names = FileName.replaceAll("\\\\","/").split("/");
		return names[names.length-1].split("\\.")[0];	
	}
}
