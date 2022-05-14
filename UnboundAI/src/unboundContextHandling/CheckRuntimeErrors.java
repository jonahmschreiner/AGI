package unboundContextHandling;
import java.util.*;
import java.io.*;
import unboundStruct.*;
public class CheckRuntimeErrors {
	public static List<Instruction> pull(Exception e, Context contextIn){
		List<Instruction> runtimeErrs = new ArrayList<Instruction>();
		Exception f = new Exception();
		f.initCause(e);
		String causeString = f.getCause().toString();
		Scanner scanCause = new Scanner(causeString);
		String cause = scanCause.next();
		scanCause.close();
		cause = cause.substring(0, cause.length() - 1);
		//Find existing compiled files in bin
		File dir = new File("/home/agi/Desktop/eclipse/UnboundAI/bin");
		String[] files = dir.list();
		List<String> fileNames = new ArrayList<String>();
		for (int i = 0; i < files.length; i++) {
			File file = new File("/home/agi/Desktop/eclipse/UnboundAI/bin/" + files[i]);
			if (!file.isDirectory() && files[i].endsWith(".java") && !files[i].equals("GoalEvalResult.java")) {
				fileNames.add(files[i]); 
			}
		}
		for (int i = 0; i < e.getStackTrace().length; i++) {
			String parentClass = e.getStackTrace()[i].getClassName();
			int lineNum = e.getStackTrace()[i].getLineNumber();
			if (fileNames.contains(parentClass + ".java")) {
				Instruction err = new Instruction(parentClass, lineNum, cause);
				runtimeErrs.add(err);
			}
		}
		return runtimeErrs;
	}
}
