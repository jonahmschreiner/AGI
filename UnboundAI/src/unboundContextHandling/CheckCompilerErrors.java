package unboundContextHandling;
import java.io.File;
import java.io.FileWriter;
import java.util.*;
import unboundStruct.*;
public class CheckCompilerErrors {
	public static List<Instruction> pull(Context contextIn) throws Exception{
		File compErrFile = new File("/home/agi/Desktop/eclipse/UnboundAI/bin/errorFile.txt");
		Scanner scan = new Scanner(compErrFile);
		scan.useDelimiter("/home/agi/Desktop/eclipse/UnboundAI/bin/");
		List<Instruction> errors = new ArrayList<Instruction>();
		while (scan.hasNext()) {
			String errorString = scan.next();
			Scanner erScan = new Scanner(errorString);
			erScan.useDelimiter(" ");
			String errStr = erScan.next();
			String parentClass = errStr.substring(0, errStr.length() - 9);
			int lineNumber = Integer.valueOf(errStr.substring(errStr.length() - 3, errStr.length() - 1));
			erScan.close();		
			File compFile = new File("/home/agi/Desktop/eclipse/UnboundAI/bin/" + parentClass + ".java");
			Scanner compScan = new Scanner(compFile);
			for (int i = 0; i < lineNumber - 1; i++) {
				compScan.nextLine();
			}
			String instruction = compScan.nextLine();
			compScan.close();
			Instruction error = new Instruction(parentClass, lineNumber, instruction);
			errors.add(error);			
		}
		scan.close();		
		FileWriter eraser = new FileWriter(compErrFile);
		eraser.write("");
		eraser.close();
		return errors;		
	}
}
